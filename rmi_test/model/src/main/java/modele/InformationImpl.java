package modele;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by E104607D on 23/09/16.
 */
public class InformationImpl extends UnicastRemoteObject implements Information {

    public InformationImpl() throws RemoteException {
        super();
    }

    public String getInformations() throws RemoteException {
        System.out.println("Méthode appelée");
        return new String("message retour");
    }
}
