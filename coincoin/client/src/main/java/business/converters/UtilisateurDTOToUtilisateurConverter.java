package business.converters;

import data_transfert_objects.UtilisateurDTO;
import modele.Utilisateur;


/**
 * Convert a data transfert object Utilisateur to a data object
 */
public class UtilisateurDTOToUtilisateurConverter {

    /**
     * Convert an UtilisateurDTO to an Utilisateur client - side
     *
     * @return the converted utilisateur
     */
    public static Utilisateur convert(UtilisateurDTO utilisateurServer) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setIp(utilisateurServer.getIp());
        utilisateur.setMail(utilisateurServer.getEmail());
        utilisateur.setPseudo(utilisateurServer.getNom());
        return utilisateur;
    }
}
