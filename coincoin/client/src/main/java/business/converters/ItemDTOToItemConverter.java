package business.converters;

import data_transfert_objects.ItemDTO;
import modele.ItemClient;


/**
 * Created by Dennis on 27/09/2016, pasted from Thibault.
 * Convert a data transfert object ItemClient to a data object
 */
public class ItemDTOToItemConverter {

    private ItemDTOToItemConverter() { }

    public static ItemClient convert(ItemDTO itemDTO) {
        ItemClient item = new ItemClient();
        if (itemDTO != null) {
            item.setNom(itemDTO.getNom());
            item.setDescription(itemDTO.getDescription());
            item.setPrix(itemDTO.getPrix());
        }

        return item;
    }
}
//test push intelliJ