package business;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * Created by Thibault on 06/10/16.
 * Main class for the client
 */
public class Application implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
//        applicationContext.register(Configuration.class);
//        Client client = (Client) applicationContext.getBean("client");

        applicationContext.close();
    }

}