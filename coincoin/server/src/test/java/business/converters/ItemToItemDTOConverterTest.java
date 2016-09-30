package business.converters;

import data_transfert_objects.ItemDTO;
import model.ItemServer;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by E104607D on 30/09/16.
 */
public class ItemToItemDTOConverterTest {

    @Test
    public void testConvertOk() {
        ItemServer itemServer = new ItemServer();
        itemServer.setPrix(50.);
        itemServer.setDescription("desc");
        itemServer.setNom("nom");

        ItemDTO itemDTO = ItemToItemDTOConverter.convert(itemServer);

        Assert.assertEquals(itemDTO.getNom(), itemServer.getNom());
        Assert.assertEquals(itemDTO.getPrix(), itemServer.getPrix(), 0.01);
        Assert.assertEquals(itemDTO.getDescription(), itemServer.getDescription());
    }
}
