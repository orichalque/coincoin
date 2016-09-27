package data;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import model.Item;

import java.util.List;

/**
 * Created by Thibault on 27/09/16.
 */
public class RepositoryImpl implements Repository{

    private List<Item> items;


    public RepositoryImpl(List<Item> items) {
        this.items = items;
    }

    public Item getItem(final String name) {
        return Iterables.tryFind(items, new Predicate<Item>() {
            public boolean apply(Item input) {
                return (input.getNom().equals(name));
            }
        }).orNull();
    }

    public List<Item> getItems() {
        return items;
    }
}
