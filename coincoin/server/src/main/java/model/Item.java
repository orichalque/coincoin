package model;

/**
 * Created by E104607D on 27/09/16.
 */
public class Item {
    private String nom;
    private String description;
    private double prix;
    private EtatItem etatItem;

    public Item(String nom) {
        this.nom = nom;
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

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public EtatItem getEtatItem() {
        return etatItem;
    }

    public void setEtatItem(EtatItem etatItem) {
        this.etatItem = etatItem;
    }
}
