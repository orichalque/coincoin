package modele;

/**
 * Created by Dennis on 27/09/16.
 */
public class Utilisateur {
    private String pseudo;
    private String mail;

    public Utilisateur(String pseudo, String mail) {
        this.pseudo = pseudo;
        this.mail = mail;
    }

    public Utilisateur() {
        //constructeur par défaut
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
