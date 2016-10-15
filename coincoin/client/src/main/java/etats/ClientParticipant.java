package etats;

import business.Client;
import business.converters.UtilisateurToUtilisateurDTOConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import data_transfert_objects.UtilisateurDTO;

import java.rmi.RemoteException;
import java.util.logging.Level;

/**
 * Created by Dennis on 27/09/16.
 */
public class ClientParticipant extends EtatClient{

    public ClientParticipant(Client client) {
        super(client);
    }

    @Override
    public void action() {

    }

    @Override
    public void rencherir(double prix) throws RemoteException {
        String currentUserAsString;
        try {
            currentUserAsString = OBJECT_MAPPER.writeValueAsString(UtilisateurToUtilisateurDTOConverter.convert(client.getUtilisateur()));
            client.getServeurVente().rencherir(currentUserAsString, prix);
        } catch (JsonProcessingException e) {
            LOGGER.log(Level.WARNING, "Cannot bid on the current duck because the user is not correct", e);
        }
    }
}
