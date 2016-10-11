package modele;

import business.Client;
import common.CommonVariables;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Dennis on 27/09/16.
 */
public class Chrono extends Thread {
    private static final Logger LOGGER = Logger.getAnonymousLogger();

    @Override
    public void run() {
        try {
            sleep(CommonVariables.TEMPS_VENTE);
        } catch (InterruptedException e) {
            LOGGER.log(Level.INFO, "fin du chrono", e);
        }
        try {
            Client.getInstance().temps_ecoule();
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, "Remote, temps ecoule chrono", e);
        }
    }
}
