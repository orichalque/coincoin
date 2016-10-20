package common;

/**
 * Created by Thibault on 27/09/16.
 * Define common variables used by both Client and Server
 */
public class CommonVariables {



    private CommonVariables() { }

    public final static String DATA_BASE_NAME = "itemDataBase.json";

    public static final int TEMPS_VENTE = 15000;


    public static final int PORT = 6668;
    public static final int AMOUNT_OF_USERS = 2;
    public static final long MILLIS_TO_WAIT_BETWEEN_CHECKS = 2000;
    public static final String CONTENT_TYPE = "application/json";

}
