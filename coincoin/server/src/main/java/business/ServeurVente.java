package business;

import business.converters.UtilisateurDTOToUtilisateurConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import data.Repository;
import data.RepositoryImpl;
import data_transfert_objects.UtilisateurDTO;
import model.ItemServer;
import model.UtilisateurServeur;
import shared_interfaces.InterfaceAcheteur;
import shared_interfaces.InterfaceServeurVente;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
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

    private List<InterfaceAcheteur> interfaceAcheteurList;
    private UtilisateurServeur currentWinner;

    //TODO moniteur file d'attente avec waiting list

    /**
     * Constructor
     */
    public ServeurVente() throws RemoteException {
        super();
        repository = new RepositoryImpl();
        interfaceAcheteurList = new ArrayList<>();
    }

    public void insc_acheteur(String acheteurAsString) {
        UtilisateurServeur utilisateurServeur = getUtilisateurFromDTO(acheteurAsString);

        //TODO add monitor to put the user in waiting state while a sell is already happening
    }

    public void rencherir(String acheteurAsString, int prix) {
        if (prix > currentItem.getPrix()) {
            currentItem.setPrix(prix);
            currentWinner = getUtilisateurFromDTO(acheteurAsString);
            notifyAllNewPrice(prix);
        }
    }

    /**
     * Add the current user to the users which have finished the bid
     * @param acheteur
     */
    public void tempsEcoule(String acheteur) {

    }

    /**
     * Notify all the buyers of the new price of the item
     * @param prix the new price
     */
    public void notifyAllNewPrice(int prix) {
        interfaceAcheteurList.forEach((interfaceAcheteur) -> {
            try {
                interfaceAcheteur.nouveau_prix(prix);
            } catch (RemoteException e) {
                LOGGER.log(Level.WARNING, "erreur notifier tlm du nouveau prix", e);
            }
        });
    }

    /**
     * Initiate a new sell, by emptying the waiting list
     */
    public void initiateSell() {
        interfaceAcheteurList.clear();

    }

    /**
     * Return an utilisateur from a String by converting a DTO
     * @param utilisateurAsString the user dto
     * @return the corresponding UtilisateurServer
     * @throws IOException
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
