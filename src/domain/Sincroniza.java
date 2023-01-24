package domain;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class Sincroniza {

    private CountDownLatch esperaPadreMisilesArmados;
    private CountDownLatch esperaMisilesLanzamiento;
    private CountDownLatch esperaMisilesTerminados;








    public Sincroniza(int maxMisiles) {
        this.esperaPadreMisilesArmados = new CountDownLatch(maxMisiles);
        this.esperaMisilesLanzamiento = new CountDownLatch(1);
        this.esperaMisilesTerminados = new CountDownLatch(maxMisiles);

    }




    public void esperarMisilesArmados() {
        try {
            this.esperaPadreMisilesArmados.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



    public void misilArmado(int id) {
        System.out.println("Misil "+id+" armádo");
        esperaPadreMisilesArmados.countDown();

    }

    public void lanzarMisiles() {
        esperaMisilesLanzamiento.countDown();

    }

    public void esperaLanzamiento() {
        try {
            esperaMisilesLanzamiento.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void misilTerminado() {
        this.esperaMisilesTerminados.countDown();

    }

    public void esperarMisilTerminado() {
        try {
            this.esperaMisilesTerminados.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
