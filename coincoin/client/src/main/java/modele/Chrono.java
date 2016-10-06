package modele;

import business.Client;

import java.rmi.RemoteException;

/**
 * Created by Dennis on 27/09/16.
 */
public class Chrono extends Thread {
    private Client client;

    public Chrono(Client client) {
        this.client = client;
    }

    public void run() {
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            client.temps_ecoule();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
