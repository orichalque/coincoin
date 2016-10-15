package etats;

import business.Client;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.rmi.RemoteException;
import java.util.logging.Logger;

/**
 * Created by Dennis on 27/09/16.
 */
public abstract class EtatClient {
    protected Client client;
    protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    protected static final Logger LOGGER = Logger.getAnonymousLogger();

    /**
     *
     * @param client
     */
    public EtatClient(Client client) {
        this.client = client;
    }

    /**
     *
     */
    public abstract void action();

    /**
     *
     * @param prix
     * @throws RemoteException
     */
    public void rencherir(double prix) throws RemoteException {
        LOGGER.warning("Impossible de renchérir");
    }
}
