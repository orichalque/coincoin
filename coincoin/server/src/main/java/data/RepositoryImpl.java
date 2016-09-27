package data;

import com.alma.common.CommonVariables;
import com.alma.utils.FileUtil;
import com.alma.utils.JsonUtil;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.io.Files;
import model.Item;

import java.io.File;
import java.io.IOException;
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

    private List<Item> items;

    /**
     * Constructor of the File database
     * Read the file and deserialize the Item objects
     */
    public RepositoryImpl() {
        items = new ArrayList<Item>();
        try {

            ClassLoader classLoader = getClass().getClassLoader();

            File file = new File(classLoader.getResource(CommonVariables.DATA_BASE_NAME).getFile());

            String fileContent = Files.toString(file, Charset.defaultCharset());
            LOGGER.info(fileContent);
            items = JsonUtil.deserializeListFromString(fileContent, Item.class);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Cannot create the database", e);
        }
    }

    /**
     * Return the corresponding item in the database
     * @param name the name
     * @return the corresponding Item
     */
    public Item getItem(final String name) {
        return Iterables.tryFind(items, new Predicate<Item>() {
            public boolean apply(Item input) {
                return (input.getNom().equals(name));
            }
        }).orNull();
    }

    /**
     * Return the full database
     * @return the list of items
     */
    public List<Item> getItems() {
        return items;
    }
}
