package business.converters;

import data_transfert_objects.UtilisateurDTO;
import modele.Utilisateur;


/**
 * Created by Dennis on 27/09/2016, pasted from Thibault.
 * Convert a data transfert object Utilisateur to a data object
 */
public class UtilisateurDTOToUtilisateurConverter {

    private UtilisateurDTOToUtilisateurConverter() {
    }

    public static Utilisateur convert(UtilisateurDTO utilisateurDTO) {
        Utilisateur utilisateur = new Utilisateur();
        if (utilisateurDTO != null) {
            utilisateur.setPseudo(utilisateurDTO.getNom());
            utilisateur.setMail(utilisateurDTO.getEmail());
        }

        return utilisateur;
    }
}
