package sample;

import java.util.Vector;
import java.util.logging.Logger;

/**
 * Created by Dennis on 04/10/16.
 */
public class Tampon{
    private int nb;
    private Vector<Integer> tab;
    private int tailleMax = 20;
    private boolean finish = false;
    private static final Logger LOGGER = Logger.getAnonymousLogger();


    public Tampon() {
        this.tab = new Vector<Integer>();
        this.nb = 0;
    }

    public boolean tamponNonVide(){
        return nb != 0;
    }

    public boolean tamponNonPlein(){
        return nb != tailleMax;
    }

    public synchronized Integer consommer() throws TerminateException {
        Integer res = null;
        if(finish && nb==0){
            throw new TerminateException("termine");
        }else{
            while(nb==0){
                if(finish){
                    throw new TerminateException("termine");
                }
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            nb--;
            res = tab.get(nb);
            tab.remove(nb);

            notify();
            //LOGGER.info("Tampon: conso de "+res);
            return res;
        }

    }

    public synchronized void produire(Integer x) {
        while (nb == tailleMax) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //LOGGER.info("Tampon: prod de " + x);
        tab.add(0, x);
        nb++;
        notify();

    }

    public void terminate() {
        finish = true;
    }

}
