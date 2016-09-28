package business.converters;

import data_transfert_objects.ItemDTO;
import model.ItemServer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Thibault on 27/09/2016.
 * Tests for the ItemDTOToItemConverter class
 */
public class ItemDTOToItemServerConverterTest {

    private ItemServer itemServer;
    private ItemDTO itemDTO;

    @Before
    public void setup() {
        itemDTO = new ItemDTO();
        itemDTO.setDescription("description");
        itemDTO.setNom("nom");
        itemDTO.setPrix(50.0);
    }

     @Test
     public void testConvertOk() {
         itemServer = ItemDTOToItemConverter.convert(itemDTO);
         Assert.assertEquals("description", itemServer.getDescription());
         Assert.assertEquals("nom", itemServer.getNom());
         Assert.assertEquals(50.0, itemServer.getPrix(), 0.001);
     }

    @Test
    public void testConvertNullOk() {
        try {
            itemServer = ItemDTOToItemConverter.convert(null);
        } catch (Exception e) {
            Assert.fail();
        }
    }

}
