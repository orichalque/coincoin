package data;

import business.converters.ItemDTOToItemConverter;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.io.Files;
import common.CommonVariables;
import data_transfert_objects.ItemDTO;
import model.ItemServer;
import utils.JsonUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Thibault on 27/09/16.
 * File database with data loaded when the object is built.
 */
public class RepositoryImpl implements Repository{

    private static final Logger LOGGER = Logger.getAnonymousLogger();

    private List<ItemServer> itemServers;

    /**
     * Constructor of the File database
     * Read the file and deserialize the ItemServer objects
     */
    public RepositoryImpl() {
        itemServers = new ArrayList<ItemServer>();
        try {
            File file;
            URL url = getClass().getClassLoader().getResource(CommonVariables.DATA_BASE_NAME);

            if (url != null) {
                 file = new File(url.getFile());
            } else {
                throw new IOException("The database file can not be found");
            }

            String fileContent = Files.toString(file, Charset.defaultCharset());

            List<ItemDTO> itemDTOs = JsonUtil.deserializeListFromString(fileContent, ItemDTO.class);

            //Add all the item into the *database*
            for (ItemDTO itemDTO : itemDTOs) {
                itemServers.add(ItemDTOToItemConverter.convert(itemDTO));
            }

        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Cannot create the database", e);
        }
    }

    /**
     * Return the corresponding item in the database
     * @param name the name
     * @return the corresponding ItemServer
     */
    public ItemServer getItem(final String name) {
        return Iterables.tryFind(itemServers, new Predicate<ItemServer>() {
            public boolean apply(ItemServer input) {
                return input.getNom().equals(name);
            }
        }).orNull();
    }

    /**
     * Return the full database
     * @return the list of itemServers
     */
    public List<ItemServer> getItemServers() {
        return itemServers;
    }
}
