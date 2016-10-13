package business.converters;

import data_transfert_objects.UtilisateurDTO;
import model.UtilisateurServeur;

/**
 * Created by Thibault on 28/09/16.
 * Convert Utilisateur data transfert object to Server-side user
 */
public class UtilisateurDTOToUtilisateurConverter {

    /**
     * Convert an utilisateur DTO to an utilisateurServeur
     * @return the corresponding UtilisateurServeur
     */
    public static UtilisateurServeur convert(UtilisateurDTO utilisateurDTO) {
        UtilisateurServeur utilisateurServeur = new UtilisateurServeur();
        utilisateurServeur.setNom(utilisateurDTO.getNom());
        utilisateurServeur.setEmail(utilisateurDTO.getEmail());
        utilisateurServeur.setIp(utilisateurDTO.getIp());
        return utilisateurServeur;
    }
}
