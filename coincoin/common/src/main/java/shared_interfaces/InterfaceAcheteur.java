package shared_interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Dennis on 27/09/16.
 * Contient les méthodes des acheteurs, connu par les 2 bords.
 */
public interface InterfaceAcheteur extends Remote {

    void nouvelle_soumission(String nouvelItemDTO) throws RemoteException;

    void objet_vendu(String utilisateurDTO) throws RemoteException;

    void nouveau_prix(double prix, String winnerName) throws RemoteException;

}
