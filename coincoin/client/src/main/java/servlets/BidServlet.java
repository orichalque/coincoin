package servlets;

import business.Client;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by Thibault & Dennis on 04/10/16.
 * Bid on the current item
 */
@WebServlet(name = "bid", urlPatterns = "/bid")
public class BidServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getAnonymousLogger();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //var parameters = {nom:$scope.item.nom,newPrice:$scope.prixPropose}

        //call client that call the server
        Client client = Client.getInstance();

        LOGGER.info("req");
        float newPrice;
        String nomObjet = req.getParameter("nom");
        String newStringPrice = req.getParameter("newPrice");
        if (newStringPrice != null) {
            LOGGER.info(nomObjet);
            LOGGER.info(newStringPrice);
            if (client.getItemCourant() != null) {
                if (nomObjet.equals(client.getItemCourant().getNom())) {
                    newPrice = Float.parseFloat(newStringPrice);
                    //Attention il faut que l'item soit non nul
                    //TODO initialisation objet à la connexion
                    client.nouveau_prix(newPrice);
                } else {
                    resp.sendError(304);
                    return;
                }
            } else {
                resp.sendError(400);//BAD REQUEST
                return;
            }
        }

        //1 erreur nom objet => 304 Not Modified
        //2 ok => 200
        resp.setStatus(HttpServletResponse.SC_OK);
    }


}
