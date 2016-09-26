package modele;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by E104607D on 23/09/16.
 */
public interface Information extends Remote {
    public String getInformations() throws RemoteException;
}
