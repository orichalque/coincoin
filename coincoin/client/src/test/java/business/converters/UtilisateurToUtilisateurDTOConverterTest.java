package business.converters;

import data_transfert_objects.UtilisateurDTO;
import modele.Utilisateur;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Dennis on 03/10/16.
 */
public class UtilisateurToUtilisateurDTOConverterTest {
    private Utilisateur user;
    private UtilisateurDTO dto;

    @Before
    public void setUp() throws Exception {
        user = new Utilisateur("name", "email");

    }

    @Test
    public void testConvertOK() {
        dto = new UtilisateurDTO();
        dto.setEmail("email");
        dto.setNom("name");
        UtilisateurDTO fromdto = UtilisateurToUtilisateurDTOConverter.convert(user);
        Assert.assertEquals(dto, fromdto);
    }

    @Test
    public void testConvertNull() {
        try {
            dto = UtilisateurToUtilisateurDTOConverter.convert(null);
        } catch (Exception e) {
            Assert.fail();
        }
    }
}