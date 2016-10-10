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
 * Created by E104607D on 04/10/16.
 */
@WebServlet(name = "bid", urlPatterns = "/bid")
public class BidServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getAnonymousLogger();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //var parameters = {nom:$scope.item.nom,newPrice:$scope.prixPropose}

        //call client that call the server
        Client c = Client.getInstance();

        LOGGER.info("req");
        float newPrice;
        String nomObjet = req.getParameter("nom");
        String newStringPrice = req.getParameter("newPrice");
        if (newStringPrice != null) {
            LOGGER.info(nomObjet);
            LOGGER.info(newStringPrice);
            newPrice = Float.parseFloat(newStringPrice);

            //Attention il faut que l'item soit non nul
            //TODO initialisation objet à la connexion
            //TODO comparer le nom de l'objet à l'objet courant pour éviter des erreurs
            c.nouveau_prix(newPrice);
        }


        //TODO put response in the httpServletResponse

        //1 erreur nom objet => 304 Not Modified
        //resp.sendError(304);
        //2 ok => 200
        resp.setStatus(HttpServletResponse.SC_OK);
    }




}
