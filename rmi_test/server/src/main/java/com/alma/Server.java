package com.alma;

import commons.CommonVariables;
import modele.Information;
import modele.InformationImpl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Logger;

/**
 * Created by E104607D on 23/09/16.
 */
public class Server {
    private static final Logger LOGGER = Logger.getLogger("Logger");
    private static final String CLASS_NAME = "Server";
    private static final String MAIN_NAME = "Main";

    private Server() {

    }

    public static void main(String[] args) throws RemoteException {
        Registry registry;
        try {

            registry = LocateRegistry.createRegistry(CommonVariables.PORT);
            LOGGER.info("Mise en place du security manager");

            Information information = new InformationImpl();

            registry.bind("info", information);

            LOGGER.info("Serveur lancé");

        } catch (RemoteException e) {
            LOGGER.throwing(CLASS_NAME, MAIN_NAME, e);
            LOGGER.warning(e.getMessage());
        } catch (AlreadyBoundException e) {
            LOGGER.throwing(CLASS_NAME, MAIN_NAME, e);
            LOGGER.warning(e.getMessage());
        }
    }
}
