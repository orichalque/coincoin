package shared_interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by E104607D on 28/09/16.
 */
public interface InterfaceServeurVente extends Remote {
    /**
     * Add an acheteur to the list
     * @param acheteur the current buyer
     * @throws RemoteException
     */
    public void insc_acheteur(String acheteur, InterfaceAcheteur interfaceAcheteur) throws RemoteException;

    /**
     * bid on the currently sold item
     * @param acheteur current buyer
     * @param prix price the buyer chose
     * @throws RemoteException
     */
    public void rencherir(String acheteur, double prix) throws RemoteException;

    /**
     * tell the serveur that the time is over for the current acheteur
     * @param acheteur the current buyer
     * @throws RemoteException
     */
    public void tempsEcoule(String acheteur) throws RemoteException;

    /**
     * Tell the server that the current acheter left the auction house
     * @param acheteur the current buyer
     * @throws RemoteException
     */
    public void quitter(String acheteur) throws RemoteException;
}
