package business;

import model.InterfaceAcheteurWithUser;
import model.UtilisateurServeur;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import shared_interfaces.InterfaceAcheteur;

import java.rmi.RemoteException;

/**
 * Created by E104607D on 30/09/16.
 */
public class ServeurTest {
    private ServeurVente serveurVente;
    private InterfaceAcheteur interfaceAcheteur;
    private UtilisateurServeur utilisateurServeur;
    private InterfaceAcheteurWithUser interfaceAcheteurWithUser;

    @Before
    public void setUp() throws RemoteException {
        serveurVente = new ServeurVente();

        interfaceAcheteurWithUser = Mockito.mock(InterfaceAcheteurWithUser.class);
        utilisateurServeur = Mockito.mock(UtilisateurServeur.class);
        interfaceAcheteur = Mockito.mock(InterfaceAcheteur.class);

        Mockito.when(interfaceAcheteurWithUser.getUtilisateurServeur()).thenReturn(utilisateurServeur);


    }

    @Test
    public void test() {


    }
}
