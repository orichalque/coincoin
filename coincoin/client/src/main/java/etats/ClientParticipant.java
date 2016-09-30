package etats;

import business.Client;

import java.rmi.RemoteException;

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
        client.getServeurVente().rencherir(client.getUtilisateur().getPseudo(), prix);
    }
}
