package servlets;

import business.Client;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by E104607D on 04/10/16.
 */
@WebServlet(name = "bid", urlPatterns = "/bid")
public class BidServlet extends HttpServlet {

    @Autowired
    private Client client;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //TODO Get data from httpServletRequest
        //call client that call the server
        //put response in the httpServletResponse
    }




}
