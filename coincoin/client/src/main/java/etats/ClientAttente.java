package etats;

import business.Client;

import java.rmi.RemoteException;

/**
 * Created by Dennis on 27/09/16.
 */
public class ClientAttente extends EtatClient{
    public ClientAttente(Client client) {
        super(client);
    }

    @Override
    public void action() {
    }

    @Override
    public void rencherir(double prix) throws RemoteException {
        super.rencherir(prix);
        LOGGER.warning("Vous êtes en attente");
    }
}
