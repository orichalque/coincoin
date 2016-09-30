package modele;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Dennis on 30/09/16.
 */
public class UtilisateurTest {
    private Utilisateur utilisateur;

    @Before
    public void setUp() throws Exception {
        utilisateur = new Utilisateur("pseudo", "mail");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetPseudo() throws Exception {
        Assert.assertEquals("pseudo", utilisateur.getPseudo());
    }

    @Test
    public void testSetPseudo() throws Exception {
        utilisateur.setPseudo("newPseudo");
        Assert.assertEquals("newPseudo", utilisateur.getPseudo());
    }

    @Test
    public void testGetMail() throws Exception {
        Assert.assertEquals("mail", utilisateur.getMail());
    }

    @Test
    public void testSetMail() throws Exception {
        utilisateur.setMail("newMail");
        Assert.assertEquals("newMail", utilisateur.getMail());
    }
}