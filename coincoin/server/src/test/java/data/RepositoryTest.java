package data;

import common.CommonVariables;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by Thibault on 27/09/16.
 * Repository tests
 */
public class RepositoryTest {

    private Repository repository;

    @Test
    public void testRepositoryEmptyConstructor( ) {
        repository = new RepositoryImpl();
        Assert.assertEquals(2, repository.getItems().size());
        Assert.assertEquals(52.2, repository.getItem("canard").getPrix(), 0.01);
        Assert.assertEquals(59.2, repository.getItem("canard colvert").getPrix(), 0.01);
    }

}
