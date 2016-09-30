package modele;

/**
 * Created by Dennis on 27/09/16.
 */
public class ItemClient {
    protected double prix;
    protected String nom;
    protected String description;

    public ItemClient(double prix, String nom, String description) {
        this.prix = prix;
        this.nom = nom;
        this.description = description;
    }

    public ItemClient() {
        //Construct par defaut
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
