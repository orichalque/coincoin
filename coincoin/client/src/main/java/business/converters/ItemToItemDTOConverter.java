package business.converters;

import data_transfert_objects.ItemDTO;
import modele.ItemClient;

/**
 * Created by Thibault on 05/10/16.
 * Convert an Item to an ItemDTO
 */
public class ItemToItemDTOConverter {

    private ItemToItemDTOConverter() {

    }

    /**
     * Convert method
     * @param itemClient a data Item client-side
     * @return the converted ItemDTO
     */
    public static ItemDTO convert(ItemClient itemClient) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setNom(itemClient.getNom());
        itemDTO.setPrix(itemClient.getPrix());
        itemDTO.setDescription(itemClient.getDescription());

        return itemDTO;
    }
}
