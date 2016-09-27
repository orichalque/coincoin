package business.converters;

import data_transfert_objects.ItemDTO;
import model.Item;

/**
 * Created by Thibault on 27/09/2016.
 * Convert a data transfert object Item to a data object
 */
public class ItemDTOToItemConverter {

    public static Item convert(ItemDTO itemDTO) {
        Item item = new Item();
        if (itemDTO != null) {
            item.setNom(itemDTO.getNom());
            item.setDescription(itemDTO.getDescription());
            item.setPrix(itemDTO.getPrix());
        }
        //TODO add item state
        item.setEtatItem(null);
        return item;
    }
}
