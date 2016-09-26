package com.alma;

import commons.CommonVariables;
import modele.Information;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Logger;

/**
 * Created by E104607D on 26/09/16.
 */
public class Client {
    private final static Logger LOGGER = Logger.getLogger("Client");

    public static void main(String[] args) {
        try {

            Registry registry = LocateRegistry.getRegistry(CommonVariables.PORT);

            Information information = (Information) registry.lookup("info");

            LOGGER.info(information.getInformations());

        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
        }
    }
}
