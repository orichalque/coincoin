package model;

import shared_interfaces.InterfaceAcheteur;

/**
 * Created by Thibault on 30/09/16.
 * Join a user data set with the RMI interface instance
 */
public class InterfaceAcheteurWithUser {
    UtilisateurServeur utilisateurServeur;
    InterfaceAcheteur interfaceAcheteur;

    public InterfaceAcheteurWithUser(UtilisateurServeur utilisateurServeur) {
        this.utilisateurServeur = utilisateurServeur;
    }

    public InterfaceAcheteurWithUser(UtilisateurServeur utilisateurServeur, InterfaceAcheteur interfaceAcheteur) {
        this.utilisateurServeur = utilisateurServeur;
        this.interfaceAcheteur = interfaceAcheteur;
    }

    public UtilisateurServeur getUtilisateurServeur() {
        return utilisateurServeur;
    }

    public void setUtilisateurServeur(UtilisateurServeur utilisateurServeur) {
        this.utilisateurServeur = utilisateurServeur;
    }

    public InterfaceAcheteur getInterfaceAcheteur() {
        return interfaceAcheteur;
    }

    public void setInterfaceAcheteur(InterfaceAcheteur interfaceAcheteur) {
        this.interfaceAcheteur = interfaceAcheteur;
    }
}
