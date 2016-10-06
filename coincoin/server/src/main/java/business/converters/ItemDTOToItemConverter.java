package business.converters;

import data_transfert_objects.ItemDTO;
import model.ItemEnAttente;
import model.ItemServer;

/**
 * Created by Thibault on 27/09/2016.
 * Convert a data transfert object ItemServer to a data object
 */
public class ItemDTOToItemConverter {

    private ItemDTOToItemConverter() { }

    public static ItemServer convert(ItemDTO itemDTO) {
        ItemServer itemServer = new ItemServer();
        if (itemDTO != null) {
            itemServer.setNom(itemDTO.getNom());
            itemServer.setDescription(itemDTO.getDescription());
            itemServer.setPrix(itemDTO.getPrix());
        }

        //FIXME Singleton pour l'etat?
        itemServer.setEtatItem(new ItemEnAttente());
        return itemServer;
    }
}
