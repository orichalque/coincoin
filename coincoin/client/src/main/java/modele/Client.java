package modele;
import etats.EtatClient;
import shared_interfaces.InterfaceAcheteur;

/**
 * Created by Dennis on 27/09/16.
 *
 */
public class Client implements InterfaceAcheteur {
    private Utilisateur utilisateur;
    private EtatClient etatCourant;
    private Chrono chrono;
    private Item itemCourant;

    public Client(Utilisateur utilisateur, EtatClient etatCourant, Chrono chrono) {
        this.utilisateur = utilisateur;
        this.etatCourant = etatCourant;
        this.chrono = chrono;
    }

    public void nouvelle_soumission(String nouvelItemDTO) {
        //1-Transformation du DTO en item
        //2- itemCourant = itemFromDTO
        //3- changer l'etatCourant en participant
    }


    public void objet_vendu(String nomAcheteur) {
        //chgt etat
        //MAJ IHM
    }


    public void nouveau_prix(double prix) {
        setPrix(prix);
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public EtatClient getEtatCourant() {
        return etatCourant;
    }

    public void setEtatCourant(EtatClient etatCourant) {
        this.etatCourant = etatCourant;
    }

    public Chrono getChrono() {
        return chrono;
    }

    public void setChrono(Chrono chrono) {
        this.chrono = chrono;
    }

    public Item getItemCourant() {
        return itemCourant;
    }

    public void setItemCourant(Item itemCourant) {
        this.itemCourant = itemCourant;
    }

    public void setPrix(double nouveauPrix){
        itemCourant.setPrix(nouveauPrix);
    }
}
