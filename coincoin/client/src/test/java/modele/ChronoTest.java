package modele;

import business.Client;
import common.CommonVariables;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.*;

/**
 * Created by Dennis on 06/10/16.
 */
public class ChronoTest {
    private Client client;
    private Chrono chrono;


    @Before
    public void setUp() throws Exception {
        client = Mockito.mock(Client.class);
        chrono = new Chrono();

    }

    @Test(timeout = CommonVariables.TEMPS_VENTE + 100)
    public void testRun() throws Exception {

        //marche mais prend du temps donc commenté, voir  org.springframework.test.util.ReflectionTestUtils;
       /* chrono.run();

        Mockito.verify(client, Mockito.times(1)).temps_ecoule();*/

    }
}