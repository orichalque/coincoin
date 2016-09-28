package business;
import business.converters.ItemDTOToItemConverter;
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

/**
 * Created by Dennis on 27/09/16.
 *
 */
public class Client extends UnicastRemoteObject implements InterfaceAcheteur {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger LOGGER = Logger.getAnonymousLogger();

    private ClientAttente etatAttente = new ClientAttente(this);
    private ClientParticipant etatParticipant= new ClientParticipant(this);
    private ClientTermine etatTermine= new ClientTermine(this);


    private Utilisateur utilisateur;
    private EtatClient etatCourant;
    private String essaiEtatString;
    private Chrono chrono;
    private ItemClient itemCourant;
    private InterfaceServeurVente serveurVente;

    public Client()throws RemoteException {
        super();
        try {

            Registry registry = LocateRegistry.getRegistry(CommonVariables.PORT);

            InterfaceServeurVente serveurVente = (InterfaceServeurVente) registry.lookup("connexion");

            LOGGER.info(serveurVente.toString());

            serveurVente.insc_acheteur("nomParDefaut");
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
        }
    }

    public Client(Utilisateur utilisateur, Chrono chrono) throws RemoteException {
        super();
        this.utilisateur = utilisateur;
        this.etatCourant = etatAttente;
        this.chrono = chrono;
        this.essaiEtatString = "attente";
        try {

            Registry registry = LocateRegistry.getRegistry(CommonVariables.PORT);

            InterfaceServeurVente serveurVente = (InterfaceServeurVente) registry.lookup("connexion");

            LOGGER.info(serveurVente.toString());

            serveurVente.insc_acheteur(utilisateur.getPseudo());
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
        }
    }

    public void nouvelle_soumission(String nouvelItemDTO) {
        //1-Transformation du DTO en item et changement item
        itemCourant = getItemFromDTO(nouvelItemDTO);
        //2- MAJ IHM?

        //3- changer l'etatCourant en participant
        essaiEtatString="participant";
        etatCourant=etatParticipant;
    }


    public void objet_vendu(String nomAcheteur) {
        //chgt etat
        essaiEtatString="attente";
        etatCourant=etatAttente;
        //MAJ IHM
    }


    public void nouveau_prix(double prix) {
        setPrix(prix);
        //MAJ IHM
    }

    /**
     * Return an item from a String by converting a DTO
     * @param itemAsString the user dto in String format
     * @return the corresponding ItemClient
     * @throws IOException
     */
    private ItemClient getItemFromDTO(String itemAsString) {
        ItemClient item = null;

        try {
            item = ItemDTOToItemConverter.convert(OBJECT_MAPPER.readValue(itemAsString, ItemDTO.class));
        } catch (IOException exception) {
            LOGGER.log(Level.WARNING, "Cannot deserialize the User", exception);
        }

        return item;
    }


    //Methodes à envoyer au serveur

    public void rencherir(int prix){
        if (prix > itemCourant.getPrix()) {
            etatCourant.rencherir(prix);
        }else{
            LOGGER.info("prix trop bas");
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

    public Chrono getChrono() {
        return chrono;
    }

    public void setChrono(Chrono chrono) {
        this.chrono = chrono;
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
