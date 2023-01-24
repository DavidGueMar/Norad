package domain;

import java.util.concurrent.Semaphore;

public class Resultado {
    private Semaphore acceso;
    private int aciertos;

    public Resultado() {
        this.aciertos = 0;
        this.acceso = new Semaphore(1);
    }

    public void contarAcierto() {

        try {
            //sección crítica poner semáforo
            this.acceso.acquire();
            this.aciertos++;
            this.acceso.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getAciertos() {
        int datos;
        datos = 0;
        try {
            //sección crítica poner semáforo
            this.acceso.acquire();
            datos = this.aciertos;
            this.acceso.release();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return datos;
    }
}
