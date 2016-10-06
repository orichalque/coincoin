package business;

import model.InterfaceAcheteurWithUser;
import model.UtilisateurServeur;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;
import shared_interfaces.InterfaceAcheteur;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thibault on 30/09/16.
 */
public class ServeurTest {
    private ServeurVente serveurVente;

    @Before
    public void setUp() throws RemoteException {
        serveurVente = new ServeurVente();


    }

    @Ignore
    @Test
    public void testInitiateSell() throws RemoteException {
        List<InterfaceAcheteurWithUser> interfaceAcheteurWithUserList = new ArrayList();

        InterfaceAcheteur interfaceAcheteur = Mockito.mock(InterfaceAcheteur.class);

        UtilisateurServeur utilisateurServeur = new UtilisateurServeur();
        utilisateurServeur.setNom("nom");
        utilisateurServeur.setEmail("email");

        InterfaceAcheteurWithUser interfaceAcheteurWithUser = new InterfaceAcheteurWithUser(utilisateurServeur, interfaceAcheteur);

        interfaceAcheteurWithUserList.add(interfaceAcheteurWithUser);

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Assert.assertNull(invocationOnMock.getArguments()[0]);
                return null;
            }
        }).when(interfaceAcheteur).nouvelle_soumission(Mockito.anyString());

        try {
            ReflectionTestUtils.setField(serveurVente, "interfaceAcheteurList", interfaceAcheteurWithUserList, List.class);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }

        serveurVente.initiateSell();

        Mockito.verify(interfaceAcheteur, Mockito.times(1)).nouvelle_soumission(null);
    }
}
