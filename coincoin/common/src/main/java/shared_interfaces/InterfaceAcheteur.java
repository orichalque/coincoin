package shared_interfaces;

/**
 * Created by Dennis on 27/09/16.
 * Contient les méthodes des acheteurs, connu par les 2 bords.
 */
public interface InterfaceAcheteur {

    void nouvelle_soumission(String nouvelItemDTO);
    void objet_vendu(String nomAcheteur);
    void nouveau_prix(double prix);

}
