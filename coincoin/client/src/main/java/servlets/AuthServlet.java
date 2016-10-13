package servlets;

import business.Client;
import modele.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by Thibault on 06/10/16.
 * Authentification servlet
 */
@WebServlet(name = "auth", urlPatterns = "/authentify")
public class AuthServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Client client = Client.getInstance();
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setPseudo(req.getParameter("nom"));
        utilisateur.setMail(req.getParameter("mail"));
        utilisateur.setIp(InetAddress.getLocalHost().getHostAddress());
        client.setUtilisateur(utilisateur);
        client.connectToServer(req.getParameter("ip"));
    }
}
