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
import java.rmi.RemoteException;
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

    private List<InterfaceAcheteurWithUser> interfaceAcheteurList;
    private UtilisateurServeur currentWinner;

    //Ensure that the value is the same for every thread
    private volatile int amountOfWaitingUsers;

    /**
     * Constructor
     */
    public ServeurVente() throws RemoteException {
        super();

        amountOfWaitingUsers = 0;

        repository = new RepositoryImpl();

        interfaceAcheteurList = Collections.synchronizedList(new ArrayList<>());


    }

    /**
     * Starts the server
     */
    public void run() {
        boolean isOver = false;

        //Infinite loop
        while (! isOver) {

            while (amountOfWaitingUsers < CommonVariables.AMOUNT_OF_USERS) {
                try {
                    Thread.sleep(CommonVariables.MILLIS_TO_WAIT_BETWEEN_CHECKS);
                } catch (InterruptedException e) {
                    LOGGER.log(Level.WARNING, "Unexpected error while waiting for users", e);
                }
            }

            currentItem = repository.getRandomItem();

            initiateSell();

            while (! interfaceAcheteurList.isEmpty()) { }
        }
    }

    /**
     * Add a bidder to the waiting list for the next bid
     * @param acheteurAsString
     */
    @Override
    public synchronized void insc_acheteur(String acheteurAsString) {
        UtilisateurServeur utilisateurServeur = getUtilisateurFromDTO(acheteurAsString);

        LOGGER.info(String.format("Receiving an inscription request from the user %s", utilisateurServeur.getNom()));

        //get user with rmi
        //FIXME
        InterfaceAcheteur interfaceAcheteur = null;

        try {
            amountOfWaitingUsers++;
            LOGGER.info(String.format("Currently %s users waiting", amountOfWaitingUsers));

            //Make the user wait
            interfaceAcheteur.wait();
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARNING, String.format("Cannot put the user %s in waiting state", utilisateurServeur.getNom()), e);
        }

        InterfaceAcheteurWithUser interfaceAcheteurWithUser = new InterfaceAcheteurWithUser(utilisateurServeur, null);

        //adding the user
        interfaceAcheteurList.add(interfaceAcheteurWithUser);
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

            modifyPrice(prix);
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
        interfaceAcheteurList.removeIf(interfaceAcheteurWithUser ->
                interfaceAcheteurWithUser.getUtilisateurServeur().getNom().equals(utilisateurServeur.getNom()));
    }

    /**
     * Notify all the buyers of the new price of the item
     * @param prix the new price
     */
    public void modifyPrice(double prix) {
        interfaceAcheteurList.forEach(interfaceAcheteurWithUser -> {
            try {
                interfaceAcheteurWithUser.getInterfaceAcheteur().nouveau_prix(prix);
            } catch (RemoteException e) {
                LOGGER.log(Level.WARNING, String.format("Can not notify the new price to the user %s", interfaceAcheteurWithUser.getUtilisateurServeur().getNom()), e);
            }
        });
    }

    /**
     * Initiate a new sell, by emptying the waiting list
     */
    public synchronized void initiateSell() {
        notifyAll();

        //FIXME sending nouvelle soumission right after adding them may be bad
        amountOfWaitingUsers = 0;

        interfaceAcheteurList.forEach(interfaceAcheteurWithUser -> {
            try {
                interfaceAcheteurWithUser.getInterfaceAcheteur().nouvelle_soumission(OBJECT_MAPPER.writeValueAsString(ItemToItemDTOConverter.convert(currentItem)));
            } catch (RemoteException e) {
                LOGGER.log(Level.WARNING, String.format("Cannot send the new item to the user %s", interfaceAcheteurWithUser.getUtilisateurServeur().getNom()), e);
            } catch (JsonProcessingException e) {
                LOGGER.log(Level.WARNING, String.format("Cannot serialize the item %s", currentItem.getNom()), e);
            }
        });

    }

    /**
     * Return an utilisateur from a String by converting a DTO
     * @param utilisateurAsString the user dto
     * @return the corresponding UtilisateurServer
     */
    private UtilisateurServeur getUtilisateurFromDTO(String utilisateurAsString) {
        UtilisateurServeur utilisateur = null;

        try {
            utilisateur = UtilisateurDTOToUtilisateurConverter.convert(OBJECT_MAPPER.readValue(utilisateurAsString, UtilisateurDTO.class));
        } catch (IOException exception) {
            LOGGER.log(Level.WARNING, "Cannot deserialize the User", exception);
        }

        return utilisateur;
    }
}
