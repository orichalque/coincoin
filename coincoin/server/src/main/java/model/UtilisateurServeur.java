package model;

/**
 * Created by Thibault on 28/09/16.
 * Utilisateur class used on serveur side
 */
public class UtilisateurServeur {
    private String nom;
    private String email;

    public UtilisateurServeur() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
