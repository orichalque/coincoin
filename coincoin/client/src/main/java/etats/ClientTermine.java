package etats;

import business.Client;

import java.rmi.RemoteException;

/**
 * Created by Dennis on 27/09/16.
 */
public class ClientTermine extends EtatClient{

    public ClientTermine(Client client) {
        super(client);
    }

    @Override
    public void action() {
    }

    @Override
    public void rencherir(int prix) throws RemoteException {
        super.rencherir(prix);
        LOGGER.warning("Votre vente est terminée");
    }
}
