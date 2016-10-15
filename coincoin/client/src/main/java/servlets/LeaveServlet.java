package servlets;

import business.Client;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Thibault on 15/10/2016.
 * Tell the client to leave
 */
@WebServlet(name = "leave", urlPatterns = "/leave")
public class LeaveServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Client client = Client.getInstance();
        client.leave();
    }
}
