package servlets;

import business.Client;
import business.converters.ItemToItemDTOConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.CommonVariables;
import data_transfert_objects.ItemDTO;
import modele.ItemClient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Thibault on 05/10/16.
 * Servlet used to update the item. Called every 3 seconds by the web app
 */
@WebServlet(name = "upd", urlPatterns = "/update")
public class UpdateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Client client = Client.getInstance();
        ItemClient itemClient = client.getItemCourant();

        if (itemClient != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            resp.setContentType(CommonVariables.CONTENT_TYPE);
            ItemDTO itemDTO = ItemToItemDTOConverter.convert(itemClient);
            itemDTO.setWinner(client.getCurrentWinner());
            String content = objectMapper.writeValueAsString(itemDTO);
            resp.getWriter().write(content);
        }

    }
}
