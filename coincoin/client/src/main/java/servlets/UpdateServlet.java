package servlets;

import business.Client;
import business.converters.ItemToItemDTOConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.CommonVariables;
import modele.ItemClient;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by E104607D on 05/10/16.
 */
@WebServlet(name = "upd", urlPatterns = "/update")
public class UpdateServlet extends HttpServlet {

    @Autowired
    private Client client;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("kikoo");
        ItemClient itemClient = client.getItemCourant();
        ObjectMapper objectMapper = new ObjectMapper();
        resp.setContentType(CommonVariables.CONTENT_TYPE);
        resp.getWriter().write(objectMapper.writeValueAsString(ItemToItemDTOConverter.convert(itemClient)));
    }
}
