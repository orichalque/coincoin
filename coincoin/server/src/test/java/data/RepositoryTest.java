package data;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by E104607D on 27/09/16.
 */
public class RepositoryTest {

    private Repository repository;

    @Test
    public void testRepositoryConstructor( ) {
        repository = new RepositoryImpl();
        Assert.assertEquals(0, repository.getItems().size());
    }
}
