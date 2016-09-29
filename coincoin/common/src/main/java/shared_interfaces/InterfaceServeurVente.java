package shared_interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by E104607D on 28/09/16.
 */
public interface InterfaceServeurVente extends Remote {
    /**
     * Add an acheteur to the list
     * @param acheteur
     */
    public void insc_acheteur(String acheteur) throws RemoteException;

    /**
     * bid on the currently sold item
     * @param acheteur
     * @param prix
     */
    public void rencherir(String acheteur, int prix) throws RemoteException;

    /**
     * tell the serveur that the time is over for the current acheteur
     * @param acheteur
     */
    public void tempsEcoule(String acheteur) throws RemoteException;
}
