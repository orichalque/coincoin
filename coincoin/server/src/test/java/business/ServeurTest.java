package business;

import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;

/**
 * Created by E104607D on 30/09/16.
 */
public class ServeurTest {
    private ServeurVente serveurVente;

    @Before
    public void setUp() throws RemoteException {
        serveurVente = new ServeurVente();
    }

    @Test
    public void test() {

    }
}
