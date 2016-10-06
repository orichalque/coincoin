package business;

import modele.ItemClient;
import org.springframework.context.annotation.Bean;

import java.rmi.RemoteException;

/**
 * Created by E104607D on 06/10/16.
 */
public class Configuration {

    @Bean
    public Client client() {
        Client client = null;
        try {
            client = new Client(null);
            client.setItemCourant(new ItemClient(50., "canard", "gros pénis"));
        } catch (RemoteException e) {
            e.printStackTrace();
            //FIXME
        }
        return client;
    }

}

