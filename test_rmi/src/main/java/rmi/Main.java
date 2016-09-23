package rmi;

import modele.Information;
import modele.InformationImpl;

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.logging.Logger;

/**
 * Created by E104607D on 23/09/16.
 */
public class Main {
    private static final Logger LOGGER = Logger.getLogger("Logger");

    public static void main(String[] args){
        try {

            LocateRegistry.createRegistry(1522);

            LOGGER.info("Mise en place du security manager");

            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }

            Information information = new InformationImpl();
            String url = String.format("rmi://%s/testRMI", InetAddress.getLocalHost().getHostAddress());
            LOGGER.info(String.format("Enregistrement de l'adresse avec l'url: %s", url));

            Naming.rebind(url, information);
            LOGGER.info("Serveur lancé");

        } catch (Exception e) {
            LOGGER.warning("Echec");
            e.printStackTrace();
        }
    }
}
