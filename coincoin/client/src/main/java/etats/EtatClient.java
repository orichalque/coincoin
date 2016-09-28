package etats;

import business.Client;

import java.util.logging.Logger;

/**
 * Created by Dennis on 27/09/16.
 */
public abstract class EtatClient {
    protected Client client;
    protected static final Logger LOGGER = Logger.getAnonymousLogger();
    public EtatClient(Client client) {
        this.client = client;
    }

    public abstract void action();

    public  void rencherir(int prix){
        LOGGER.warning("Impossible de renchérir");
    }
}
