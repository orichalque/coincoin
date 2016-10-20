package business;

import common.CommonVariables;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Thibault on 30/09/16.
 * Main class
 */
public class Application {

    private static final Logger LOGGER = Logger.getLogger("Server logs");

    /**
     * Private constructor : this class must not be instanciated
     */
    private Application() {

    }

    /**
     * Main class
     * @param args
     */
    public static void main(String[] args) {
        Registry registry;

        try {

            registry = LocateRegistry.createRegistry(CommonVariables.PORT);
            LOGGER.info("Launching the registry");
            ServeurVente serveurVente = new ServeurVente();
            registry.rebind("serveur", serveurVente);

            LOGGER.info("Server successfully launched");
            serveurVente.run();

        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, "Cannot bind the server with RMI", e);
        }/* catch (AlreadyBoundException e) {
            LOGGER.log(Level.WARNING, "Server already bound", e);
        }*/
    }
}
