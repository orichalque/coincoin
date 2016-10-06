package sample;

import java.util.logging.Logger;

/**
 * Created by Dennis on 04/10/16.
 */
public class Processus extends Thread {
    private int multiple;
    private Tampon prec;
    private Tampon suiv;
    private Processus pSuiv;
    private int actualNumber;
    private static final Logger LOGGER = Logger.getAnonymousLogger();


    public Processus(Tampon t, int multiple) {
        LOGGER.info("deb construct " + multiple);
        prec = t;
        this.multiple = multiple;
    }

    public void run() {
        System.out.println(multiple);
        while (true) {
            try {
                cons();
            } catch (TerminateException e) {
                if (suiv == null) {
                    LOGGER.warning("FIN DU PROC via null" + multiple);
                    break;
                } else {
                    if (suiv.tamponNonVide())
                        suiv.terminate();
                    LOGGER.warning("FIN DU PROC " + multiple);
                    break;
                }
            }


            if (actualNumber % multiple != 0) {
                //LOGGER.info("!=0");
                if (suiv == null) {
                    suiv = new Tampon();
                    pSuiv = new Processus(suiv, actualNumber);
                    pSuiv.start();
                } else {
                    //LOGGER.info("on fait suivre");
                    produire(actualNumber);
                }
            }


        }

    }


    public  void  cons() throws TerminateException {
        actualNumber = prec.consommer();
    }

    public  synchronized void produire(Integer x){
        suiv.produire(x);

    }

    public Tampon getSuiv() {
        return suiv;
    }
}
