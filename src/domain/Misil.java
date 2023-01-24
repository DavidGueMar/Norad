package domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Random;

public class Misil extends Thread {
    private int id;
    private Sincroniza sincro;
    private BufferedReader flujoE;
    Resultado resultado;

    public Misil(int id, BufferedReader flujoE, Resultado resultado, Sincroniza sincro) {
        this.id = id;
        this.sincro = sincro;
        this.flujoE = flujoE;
        this.resultado = resultado;
        this.start();
    }

    public void run() {
        Random aleatorio = new Random();
        String comando;
        int acierto;
        try {
            System.out.println("Misil " + id + " armándose");
            Thread.sleep(aleatorio.nextInt(1000) + 200);
            sincro.misilArmado(id);
            sincro.esperaLanzamiento();
            //doble verificación
            comando = flujoE.readLine();
            if (comando.contains("atacar")) {
                System.out.println("Misil " + id + " lanzado");
                Thread.sleep(aleatorio.nextInt(1000) + 200);
                acierto = aleatorio.nextInt(10);
                if (acierto > 3) {
                    resultado.contarAcierto();
                }
            } else {
                System.out.println(id + " fallo verificación");

                sincro.misilTerminado();

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}