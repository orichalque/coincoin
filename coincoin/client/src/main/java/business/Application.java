package business;

import modele.ItemClient;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * Created by Thibault on 06/10/16.
 * Main class for the client
 */
public class Application implements WebApplicationInitializer {

    /**
     * classes are initialized here before the deployment of the server
     * @param servletContext
     * @throws ServletException
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        Client client = Client.getInstance();
    }

}