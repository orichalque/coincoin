package business.converters;

import data_transfert_objects.ItemDTO;
import model.Item;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Thibault on 27/09/2016.
 * Tests for the ItemDTOToItemConverter class
 */
public class ItemDTOToItemConverterTest {

    private Item item;
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
         item = ItemDTOToItemConverter.convert(itemDTO);
         Assert.assertEquals("description", item.getDescription());
         Assert.assertEquals("nom", item.getNom());
         Assert.assertEquals(50.0, item.getPrix(), 0.001);
     }

    @Test
    public void testConvertNullOk() {
        try {
            item = ItemDTOToItemConverter.convert(null);
        } catch (Exception e) {
            Assert.fail();
        }
    }

}
