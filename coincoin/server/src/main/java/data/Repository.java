package data;

import model.Item;

import java.util.List;

/**
 * Created by Thibault on 27/09/16.
 * Interface defining repository methods
 * Can be used to create a file database or a real sql/nosql database
 */
public interface Repository {

    public Item getItem(String name);

    public List<Item> getItems();
}
