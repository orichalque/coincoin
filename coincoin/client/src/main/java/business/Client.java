package business;
import business.converters.ItemDTOToItemConverter;
import business.converters.UtilisateurToUtilisateurDTOConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.CommonVariables;
import data_transfert_objects.ItemDTO;
import etats.ClientAttente;
import etats.ClientParticipant;
import etats.ClientTermine;
import etats.EtatClient;
import modele.Chrono;
import modele.ItemClient;
import modele.Utilisateur;
import shared_interfaces.InterfaceAcheteur;
import shared_interfaces.InterfaceServeurVente;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

/**
 * Created by Dennis on 27/09/16.
 *
 */
public class Client extends UnicastRemoteObject implements InterfaceAcheteur {
    //constantes
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger LOGGER = Logger.getAnonymousLogger();

    //etats
    private EtatClient etatCourant;
    private ClientAttente etatAttente = new ClientAttente(this);
    private ClientParticipant etatParticipant= new ClientParticipant(this);
    private ClientTermine etatTermine= new ClientTermine(this);


    private Utilisateur utilisateur;
    private String essaiEtatString;
    private ItemClient itemCourant;
    private InterfaceServeurVente serveurVente;


    public Client(Utilisateur utilisateur) throws RemoteException {
        super();
        this.utilisateur = utilisateur;
        this.etatCourant = etatAttente;
        this.essaiEtatString = "attente";
        try {

            Registry registry = LocateRegistry.getRegistry(CommonVariables.PORT);

            InterfaceServeurVente serveurVente = (InterfaceServeurVente) registry.lookup("connexion");

            LOGGER.info(serveurVente.toString());

            inscription();
            startChrono();
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
        }
    }

    /**
     * Marque le début d'une vente.
     * Utilisée par le serveur.
     * @param nouvelItemDTO nouvel item au format string
     */
    public void nouvelle_soumission(String nouvelItemDTO) {
        //1-Transformation du DTO en item et changement item
        itemCourant = getItemFromDTO(nouvelItemDTO);
        //2- MAJ IHM?

        //3- changer l'etatCourant en participant
        essaiEtatString="participant";
        etatCourant=etatParticipant;
        startChrono();
    }


    /**
     * Marque la fin de la vente.
     * Utilisée par le serveur.
     * @param nomAcheteur nouveau propriétaire de l'item
     */
    public void objet_vendu(String nomAcheteur) {
        //chgt etat
        essaiEtatString="attente";
        etatCourant=etatAttente;
        //MAJ IHM
    }


    /**
     * Methode de mise a jour du prix.
     * Utilisée par le serveur.
     * @param prix nouveau prix
     */
    public void nouveau_prix(double prix) {
        setPrix(prix);
        //MAJ IHM
    }

    /**
     * Return an item from a String by converting a DTO
     * @param itemAsString the user dto in String format
     * @return the corresponding ItemClient
     * @throws IOException string mal formée
     */
    private ItemClient getItemFromDTO(String itemAsString) {
        ItemClient item = null;

        try {
            item = ItemDTOToItemConverter.convert(OBJECT_MAPPER.readValue(itemAsString, ItemDTO.class));
        } catch (IOException exception) {
            LOGGER.log(Level.WARNING, "Cannot deserialize the item", exception);
        }

        return item;
    }


    //Methodes à envoyer au serveur
    /**
     * méthode pour proposer un nouveau prix.
     * Appelle la methode de renchérissement de son état courant qui appellera au besoin celle du serveur via RMI.
     * TODO à synchronized avec la fin du chrono car il ne faut surtout pas que les deux s'execute en meme temps
     * @param prix le nouveau prix
     */
    public synchronized void rencherir(double prix) throws RemoteException {
        if (prix > itemCourant.getPrix()) {
            etatCourant.rencherir(prix);
        }else{
            LOGGER.info("prix trop bas");
        }
    }

    /**
     * Inscrit l'acheteur courant aux encheres.
     * Appelle la méthode d'inscription du serveur via RMI.
     */
    public void inscription() throws RemoteException {
        try {
            serveurVente.insc_acheteur(OBJECT_MAPPER.writeValueAsString(UtilisateurToUtilisateurDTOConverter.convert(utilisateur)));
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, String.format("Cannot send the new user to the serveur"), e);
        } catch (JsonProcessingException e) {
            LOGGER.log(Level.WARNING, String.format("Cannot serialize the user "), e);
        }
    }

    /**
     * Termine la vente pour cet acheteur suite à la fin du chrono
     * Appelle la méthode de tps écoulé du serveur via RMI.
     * TODO synchronised avec rencherissement
     * @see this.rencherir
     */
    public synchronized void temps_ecoule() throws RemoteException {
        etatCourant = etatTermine;
        serveurVente.tempsEcoule(utilisateur.getPseudo());
    }

    /**
     * Lancé a l'initialisation du client pour créer un timer sur la vente.
     */
    public void startChrono() {
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            temps_ecoule();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }




    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public EtatClient getEtatCourant() {
        return etatCourant;
    }

    public void setEtatCourant(EtatClient etatCourant) {
        this.etatCourant = etatCourant;
    }


    public ItemClient getItemCourant() {
        return itemCourant;
    }

    public void setItemCourant(ItemClient itemCourant) {
        this.itemCourant = itemCourant;
    }

    public void setPrix(double nouveauPrix){
        itemCourant.setPrix(nouveauPrix);
    }

    public InterfaceServeurVente getServeurVente() {
        return serveurVente;
    }

    public void setServeurVente(InterfaceServeurVente serveurVente) {
        this.serveurVente = serveurVente;
    }
}
