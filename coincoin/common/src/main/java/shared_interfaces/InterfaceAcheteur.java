package shared_interfaces;

import java.rmi.Remote;

/**
 * Created by Dennis on 27/09/16.
 * Contient les méthodes des acheteurs, connu par les 2 bords.
 */
public interface InterfaceAcheteur extends Remote {

    void nouvelle_soumission(String nouvelItemDTO);
    void objet_vendu(String nomAcheteur);
    void nouveau_prix(double prix);

}
