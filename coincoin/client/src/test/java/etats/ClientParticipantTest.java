package etats;

import business.Client;
import modele.Utilisateur;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import shared_interfaces.InterfaceServeurVente;

import static org.junit.Assert.*;

/**
 * Created by Dennis on 30/09/16.
 */
public class ClientParticipantTest {

    private EtatClient etatClient;
    private Client client;
    private InterfaceServeurVente interfaceServeurVente;

    @Before
    public void setUp() throws Exception {
        interfaceServeurVente = Mockito.mock(InterfaceServeurVente.class);
        client = Mockito.mock(Client.class);
        etatClient = new ClientParticipant(client);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testAction() throws Exception {

    }

    @Test
    public void testRencherir() throws Exception {
        Mockito.when(client.getServeurVente()).thenReturn(interfaceServeurVente);
        Mockito.when(client.getUtilisateur()).thenReturn(new Utilisateur("pseudo", "mail"));

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] objects = invocationOnMock.getArguments();

                Assert.assertEquals("pseudo", (String) objects[0]);
                Assert.assertEquals(50, (int) objects[1]);
                return null;
            }
        }).when(interfaceServeurVente).rencherir(Mockito.anyString(), Mockito.anyInt());

        etatClient.rencherir(50);

        Mockito.verify(interfaceServeurVente, Mockito.times(1)).rencherir(Mockito.anyString(), Mockito.anyInt());
    }
}
