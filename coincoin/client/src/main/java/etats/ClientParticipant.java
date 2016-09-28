package etats;

import business.Client;

/**
 * Created by Dennis on 27/09/16.
 */
public class ClientParticipant extends EtatClient{

    public ClientParticipant(Client client) {
        super(client);
    }

    @Override
    public void action() {

    }

    @Override
    public void rencherir(int prix) {
        client.getServeurVente().rencherir(client.getUtilisateur().getPseudo(), prix);
    }
}
