package business.converters;

import data_transfert_objects.UtilisateurDTO;
import modele.Utilisateur;


/**
 * Created by Dennis on 27/09/2016, pasted from Thibault.
 * Convert a data transfert object Utilisateur to a data object
 */
public class UtilisateurToUtilisateurDTOConverter {
    /**
     * Convert an Utilisateur to an UtilisateurDTO
     *
     * @return the converted utilisateur
     */
    public static UtilisateurDTO convert(Utilisateur utilisateurServer) {
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
        utilisateurDTO.setNom(utilisateurServer.getPseudo());
        utilisateurDTO.setEmail(utilisateurServer.getMail());
        return utilisateurDTO;
    }
}
