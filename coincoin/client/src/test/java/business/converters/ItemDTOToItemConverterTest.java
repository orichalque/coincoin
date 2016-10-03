package business.converters;

import business.converters.ItemDTOToItemConverter;
import data_transfert_objects.ItemDTO;
import modele.ItemClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Thibault on 27/09/2016.
 * Tests for the ItemDTOToItemConverter class
 */
public class ItemDTOToItemConverterTest {

    private ItemClient itemClient;
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
        itemClient = business.converters.ItemDTOToItemConverter.convert(itemDTO);
        Assert.assertEquals("description", itemClient.getDescription());
        Assert.assertEquals("nom", itemClient.getNom());
        Assert.assertEquals(50.0, itemClient.getPrix(), 0.001);
    }

    @Test
    public void testConvertNullOk() {
        try {
            itemClient = ItemDTOToItemConverter.convert(null);
        } catch (Exception e) {
            Assert.fail();
        }
    }

}
