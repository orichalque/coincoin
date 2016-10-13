package business.converters;

import data_transfert_objects.ItemDTO;
import model.ItemServer;

/**
 * Created by Thibault on 30/09/16.
 * Convert a server-side item to a data transfert object item
 */
public class ItemToItemDTOConverter {
    /**
     * Convert an Item to an ItemDTO
     * @return the converted item
     */
    public static ItemDTO convert(ItemServer itemServer) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setNom(itemServer.getNom());
        itemDTO.setDescription(itemServer.getDescription());
        itemDTO.setPrix(itemServer.getPrix());
        return itemDTO;
    }
}
