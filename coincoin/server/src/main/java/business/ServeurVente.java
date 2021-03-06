package business;

import business.converters.ItemToItemDTOConverter;
import business.converters.UtilisateurDTOToUtilisateurConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.CommonVariables;
import data.Repository;
import data.RepositoryImpl;
import data_transfert_objects.UtilisateurDTO;
import model.InterfaceAcheteurWithUser;
import model.ItemServer;
import model.UtilisateurServeur;
import shared_interfaces.InterfaceAcheteur;
import shared_interfaces.InterfaceServeurVente;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Thibault on 28/09/16.
 * Implémentation du serveur
 */
public class ServeurVente extends UnicastRemoteObject implements InterfaceServeurVente {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger LOGGER = Logger.getAnonymousLogger();

    private Repository repository;
    private ItemServer currentItem;


    private List<InterfaceAcheteurWithUser> interfaceAcheteurListInscris;
    private List<InterfaceAcheteurWithUser> interfaceAcheteurListSale;

    private UtilisateurServeur currentWinner;

    //true if the sale is actually over. False otherwise
    private boolean saleOver;

    //Ensure that the value is the same for every thread
    private volatile int amountOfUsers;

    /**
     * Constructor
     */
    public ServeurVente() throws RemoteException {
        super();

        amountOfUsers = 0;

        repository = new RepositoryImpl();

        interfaceAcheteurListInscris = Collections.synchronizedList(new ArrayList<>());
        interfaceAcheteurListSale = Collections.synchronizedList(new ArrayList<>());
    }

    /**
     * Starts the server
     */
    public void run() {
        boolean isOver = false;
        saleOver = true;
        //Infinite loop
        while (! isOver) {

            currentItem = repository.getRandomItem();

            if (interfaceAcheteurListInscris.size() >= CommonVariables.AMOUNT_OF_USERS) {
                LOGGER.info(String.format("Vente de l'objet %s démarrée", currentItem.getNom()));
                initiateSell();
            }

            while (!interfaceAcheteurListSale.isEmpty() || !saleOver) {
            }

        }
    }

    /**
     * Add a bidder to the waiting list for the next bid
     * @param acheteurAsString
     */
    @Override
    public synchronized void insc_acheteur(String acheteurAsString, InterfaceAcheteur interfaceAcheteur) {
        LOGGER.info(String.format("System property: %s", System.getProperty("java.rmi.server.hostname")));
        UtilisateurServeur utilisateurServeur = getUtilisateurFromDTO(acheteurAsString);

        LOGGER.info(String.format("Receiving an inscription request from the user %s", utilisateurServeur.getNom()));

        try {
            LocateRegistry.getRegistry(CommonVariables.PORT).rebind(utilisateurServeur.getNom(), interfaceAcheteur);
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, "Cannot bind the newly received user", e);
        }

        amountOfUsers++;
        LOGGER.info(String.format("Currently %s users waiting", amountOfUsers));
        if (amountOfUsers < CommonVariables.AMOUNT_OF_USERS) {
            while (amountOfUsers < CommonVariables.AMOUNT_OF_USERS) {
                //Make the user wait
                try {
                    wait();
                } catch (InterruptedException e) {
                    LOGGER.log(Level.INFO, "First Waiting interrupted", e);
                }
            }

            LOGGER.info("User added");
            //Joining the Remote object with the user server-side in a Thread-safe list
            try {
                interfaceAcheteurListInscris.add(new InterfaceAcheteurWithUser(utilisateurServeur, (InterfaceAcheteur)(LocateRegistry.getRegistry(CommonVariables.PORT).lookup(utilisateurServeur.getNom()))));
            } catch (RemoteException e) {
                LOGGER.log(Level.WARNING, String.format("Cannot bind the user %s", utilisateurServeur.getNom()), e);
            } catch (NotBoundException e) {
                LOGGER.log(Level.WARNING, String.format("The user %s has not been bound", utilisateurServeur.getNom()), e);
            }
        }else{
            if(amountOfUsers == CommonVariables.AMOUNT_OF_USERS){//dernier user requis pour commencer la vente
                LOGGER.info("User added");
                //Joining the Remote object with the user server-side in a Thread-safe list
                try {
                    interfaceAcheteurListInscris.add(new InterfaceAcheteurWithUser(utilisateurServeur, (InterfaceAcheteur)(LocateRegistry.getRegistry(CommonVariables.PORT).lookup(utilisateurServeur.getNom()))));
                } catch (RemoteException e) {
                    LOGGER.log(Level.WARNING, String.format("Cannot bind the user %s", utilisateurServeur.getNom()), e);
                } catch (NotBoundException e) {
                    LOGGER.log(Level.WARNING, String.format("The user %s has not been bound", utilisateurServeur.getNom()), e);
                }
                notifyAll();// wake the waiting users
            }else{ // wait after the starting of the sale
                LOGGER.info("attente d'un utilisateur apres commecencement");
                try {
                    wait(); // woke up thanks to the notifyAll of initiateSell
                } catch (InterruptedException e) {
                    LOGGER.log(Level.INFO, "Waiting interrupted for new session", e);
                }
                //Joining the Remote object with the user server-side in a Thread-safe list
                try {
                    interfaceAcheteurListInscris.add(new InterfaceAcheteurWithUser(utilisateurServeur, (InterfaceAcheteur)(LocateRegistry.getRegistry(CommonVariables.PORT).lookup(utilisateurServeur.getNom()))));
                } catch (RemoteException e) {
                    LOGGER.log(Level.WARNING, String.format("Cannot bind the user %s", utilisateurServeur.getNom()), e);
                } catch (NotBoundException e) {
                    LOGGER.log(Level.WARNING, String.format("The user %s has not been bound", utilisateurServeur.getNom()), e);
                }
                LOGGER.info("User added");

            }

        }



    }

    /**
     * Increase the bid on the currently sold item
     * @param acheteurAsString a string containing a DTO buyer
     * @param prix the new price
     */
    @Override
    public void rencherir(String acheteurAsString, double prix) {
        if (prix > currentItem.getPrix()) {
            currentItem.setPrix(prix);
            currentWinner = getUtilisateurFromDTO(acheteurAsString);

            LOGGER.info(String.format("%s is the current winner", currentWinner));

            modifyPrice(prix, currentWinner.getNom());
        }
    }

    /**
     * Add the current user to the users which have finished the bid
     * @param acheteur a string containing a DTO buyer
     */
    @Override
    public void tempsEcoule(String acheteur) {
        UtilisateurServeur utilisateurServeur = getUtilisateurFromDTO(acheteur);

        LOGGER.info(String.format("%s has finish the sale", utilisateurServeur.getNom()));

        //remove the buyer to the user's list
        interfaceAcheteurListSale.removeIf(interfaceAcheteurWithUser ->
                interfaceAcheteurWithUser.getUtilisateurServeur().getNom().equals(utilisateurServeur.getNom()));


        if (interfaceAcheteurListSale.isEmpty()) {
            LOGGER.info("La liste des participants est vide. LA vente actuelle est terminée");

            saleOver = true;
        }
    }

    /**
     * RMI called method deleting the user, erasing its registry, and
     * @param acheteur the current buyer
     * @throws RemoteException
     */
    @Override
    public void quitter(String acheteur) throws RemoteException {
        UtilisateurServeur utilisateurServeur = getUtilisateurFromDTO(acheteur);

        LOGGER.info(String.format("%s left Coincoin", utilisateurServeur.getNom()));

        interfaceAcheteurListSale.removeIf(interfaceAcheteurWithUser ->
                interfaceAcheteurWithUser.getUtilisateurServeur().getNom().equals(utilisateurServeur.getNom()));

        boolean deleted = interfaceAcheteurListInscris.removeIf(interfaceAcheteurWithUser ->
                interfaceAcheteurWithUser.getUtilisateurServeur().getNom().equals(utilisateurServeur.getNom()));
        if (deleted){
            amountOfUsers = interfaceAcheteurListInscris.size();
        }
        try {
            LocateRegistry.getRegistry(utilisateurServeur.getIp(), CommonVariables.PORT).unbind(utilisateurServeur.getNom());
        } catch (NotBoundException e) {
            LOGGER.log(Level.WARNING, String.format("Cannot unbing the user %s", utilisateurServeur.getNom()), e);
        }


    }

    /**
     * Notify all the buyers of the new price of the item
     * @param prix the new price
     */
    public void modifyPrice(double prix, String currentWinner) {
        interfaceAcheteurListInscris.forEach(interfaceAcheteurWithUser -> {
            try {
                interfaceAcheteurWithUser.getInterfaceAcheteur().nouveau_prix(prix, currentWinner);
            } catch (RemoteException e) {
                LOGGER.log(Level.WARNING, String.format("Can not notify the new price to the user %s", interfaceAcheteurWithUser.getUtilisateurServeur().getNom()), e);
            }
        });
    }

    /**
     * Initiate a new sell, by emptying the waiting list
     */
    public synchronized void initiateSell() {
        //FIXME sending nouvelle soumission right after adding them wont work
        saleOver = false;
        if (!interfaceAcheteurListInscris.isEmpty()) {

            LOGGER.info("Notifying users");
            //amountOfUsers = 0; commenté car on se sert désormais du nb d'users connectés
            interfaceAcheteurListSale = Collections.synchronizedList(new ArrayList<>(interfaceAcheteurListInscris));

            interfaceAcheteurListSale.forEach(interfaceAcheteurWithUser -> {
                try {
                    interfaceAcheteurWithUser.getInterfaceAcheteur().nouvelle_soumission(OBJECT_MAPPER.writeValueAsString(ItemToItemDTOConverter.convert(currentItem)));
                    LOGGER.info(String.format("%s notified", interfaceAcheteurWithUser.getUtilisateurServeur().getNom()));
                } catch (RemoteException e) {
                    LOGGER.log(Level.WARNING, String.format("Cannot send the new item to the user %s", interfaceAcheteurWithUser.getUtilisateurServeur().getNom()), e);
                } catch (JsonProcessingException e) {
                    LOGGER.log(Level.WARNING, String.format("Cannot serialize the item %s", currentItem.getNom()), e);
                }
            });
        }
        notifyAll();
    }

    /**
     * Return an utilisateur from a String by converting a DTO
     * @param utilisateurAsString the user dto
     * @return the corresponding UtilisateurServer
     */
    private UtilisateurServeur getUtilisateurFromDTO(String utilisateurAsString) {
        UtilisateurServeur utilisateur = null;
        LOGGER.info(utilisateurAsString);
        try {
            utilisateur = UtilisateurDTOToUtilisateurConverter.convert(OBJECT_MAPPER.readValue(utilisateurAsString, UtilisateurDTO.class));
        } catch (IOException exception) {
            LOGGER.log(Level.WARNING, "Cannot deserialize the User", exception);
        }

        return utilisateur;
    }
}
