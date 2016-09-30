package business.converters;

import data_transfert_objects.UtilisateurDTO;
import model.UtilisateurServeur;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Thibault on 30/09/16.
 */
public class UtilisateurDTOToUtilisateurTest {

    @Test
    public void convertTest() {
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
        utilisateurDTO.setEmail("email");
        utilisateurDTO.setNom("nom");

        UtilisateurServeur utilisateur = UtilisateurDTOToUtilisateurConverter.convert(utilisateurDTO);
        Assert.assertEquals(utilisateur.getEmail(), utilisateurDTO.getEmail());
        Assert.assertEquals(utilisateur.getNom(), utilisateurDTO.getNom());
    }
}
