package modele;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Dennis on 30/09/16.
 */
public class ItemClientTest {
    private ItemClient item;

    @Before
    public void setUp() throws Exception {
        item = new ItemClient(10.0, "nom", "desc");
    }

    @Test
    public void emptyConstructor() {
        ItemClient item = new ItemClient();
        Assert.assertNotNull(item);
    }

    @Test
    public void testGetPrix() throws Exception {
        Assert.assertTrue(10.0 == item.getPrix());
    }

    @Test
    public void testSetPrix() throws Exception {
        item.setPrix(20.1);
        Assert.assertTrue(20.1 == item.getPrix());
    }

    @Test
    public void testGetNom() throws Exception {
        Assert.assertEquals("nom", item.getNom());
    }

    @Test
    public void testSetNom() throws Exception {
        item.setNom("new");
        Assert.assertEquals("new", item.getNom());
    }

    @Test
    public void testGetDescription() throws Exception {
        Assert.assertEquals("desc", item.getDescription());
    }

    @Test
    public void testSetDescription() throws Exception {
        item.setDescription("new");
        Assert.assertEquals("new", item.getDescription());
    }
}