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
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Dennis on 27/09/16.
 * Is a singleton
 */
public class Client extends UnicastRemoteObject implements InterfaceAcheteur{

    //Singleton instance
    private static Client instance;

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
    private Chrono chrono;

    private Registry registry;

    public static Client getInstance() {
        if (instance == null) {
            try {
                instance = new Client();
            } catch (RemoteException e) {
                LOGGER.log(Level.SEVERE, "Cannot instantiate the client", e);
            }
        }
        return instance;
    }

    /**
     * Constructor for the Client
     * @PreCondition The server is running
     */
    public Client() throws RemoteException {
        super();
        registry = null;
        this.etatCourant = etatAttente;
        this.essaiEtatString = "attente";
        this.chrono = new Chrono();

    }

    /**
     * Connect the client to the server
     * @param ip the ip of the server the users entered on the web page
     */
    public void connectToServer(String ip) {
        try {
            registry = LocateRegistry.getRegistry(ip, CommonVariables.PORT);
            serveurVente = (InterfaceServeurVente) registry.lookup("serveur");

            LOGGER.log(Level.INFO, "Connection to the server successful");
        } catch (NotBoundException e) {
            LOGGER.log(Level.SEVERE, "The server is not instanciated", e);
        } catch (AccessException e) {
            LOGGER.log(Level.SEVERE, "Cannot access to the server", e);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, "Not allowed to reach the server", e);
        }
    }

    /**
     * Marque le début d'une vente.
     * Utilisée par le serveur.
     * @param nouvelItemDTO nouvel item au format string
     */
    @Override
    public void nouvelle_soumission(String nouvelItemDTO) {

        itemCourant = getItemFromDTO(nouvelItemDTO);

        LOGGER.info(String.format("Receiving a new submission: %s", itemCourant.getNom()));
        //1-Transformation du DTO en item et changement item

        //2- MAJ IHM?

        //3- changer l'etatCourant en participant
        essaiEtatString="participant";
        etatCourant=etatParticipant;

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    LOGGER.info("Temps écoulé");
                    temps_ecoule();
                } catch (RemoteException e) {
                    LOGGER.log(Level.WARNING, "Remote error on the timer", e);
                }
            }
        }, 10000);
    }


    /**
     * Marque la fin de la vente.
     * Utilisée par le serveur.
     * @param nomAcheteur nouveau propriétaire de l'item
     */
    @Override
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
    @Override
    public void nouveau_prix(double prix) {
        if (itemCourant != null) {
            itemCourant.setPrix(prix);
        } else {
            itemCourant = new ItemClient(prix, "erreur init", "erreur init");
        }
        //MAJ

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
            LOGGER.log(Level.INFO, "prix trop bas");
        }
    }

    /**
     * Inscrit l'acheteur courant aux encheres.
     * Appelle la méthode d'inscription du serveur via RMI.
     */
    public void inscription() throws RemoteException {
        try {
            String serializedUser = OBJECT_MAPPER.writeValueAsString(UtilisateurToUtilisateurDTOConverter.convert(utilisateur));
            LOGGER.info(String.format("Binding %s to the rmi registry", utilisateur.getPseudo()));

            LocateRegistry.createRegistry(CommonVariables.PORT).rebind(utilisateur.getPseudo(), this);
            LOGGER.info(String.format("Client %s bound to the registry", utilisateur.getPseudo()));

            serveurVente.insc_acheteur(serializedUser);
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
        try {
            serveurVente.tempsEcoule(OBJECT_MAPPER.writeValueAsString(UtilisateurToUtilisateurDTOConverter.convert(utilisateur)));
            itemCourant = null;
        } catch (JsonProcessingException e) {
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

    public InterfaceServeurVente getServeurVente() {
        return serveurVente;
    }

    public void setServeurVente(InterfaceServeurVente serveurVente) {
        this.serveurVente = serveurVente;
    }
}
