package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.util.Scanner;

import domain.Misil;
import domain.Resultado;
import domain.Sincroniza;

public class Norad {

    final static int MAX_MISILES = 10;

    public static void main(String[] args) {
        int estadistica;
        Misil [] misil = new Misil[MAX_MISILES];

        Scanner sc = new Scanner(System.in);

        //Creamos los pipes
        PipedWriter [] emisor   = new PipedWriter[MAX_MISILES];
        PipedReader [] receptor = new PipedReader[MAX_MISILES];
        PrintWriter [] flujoS   = new PrintWriter[MAX_MISILES];
        BufferedReader [] flujoE = new BufferedReader[MAX_MISILES];

        for (int i=0; i<MAX_MISILES; i++) {
            try {
                emisor[i] = new PipedWriter();
                receptor[i] = new PipedReader(emisor[i]);
                flujoS[i] = new PrintWriter(emisor[i]);
                flujoE[i] = new BufferedReader(receptor[i]);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Resultado resultado = new Resultado();
        Sincroniza sincro = new Sincroniza(MAX_MISILES);

        //Creamos los misiles
        for (int i=0; i<MAX_MISILES; i++) {
            misil[i] = new Misil(i,flujoE[i],resultado,sincro);
        }

        //Esperamos que todos los misiles estén listos
        sincro.esperarMisilesArmados();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String comando;
        System.out.println("Todos los misiles listos");
        System.out.println("Sistema Armado");
        boolean finaliza = false;
        do {
            System.out.println("Norad esperando comando >");
            comando = sc.nextLine();
            if (comando.contains("atacar")){
                sincro.lanzarMisiles();//countdown

                //doble verificación
                for (int i=0; i<MAX_MISILES; i++) {
                    flujoS[i].println("atacar");
                }
                System.out.println("Misiles lanzados");
                finaliza = true;
            }
        }while(!finaliza);
        //espera que todos los misiles terminen
        //para generar estadísticas
        sincro.esperarMisilTerminado();//countdown
        estadistica = resultado.getAciertos()*100/MAX_MISILES;
        System.out.println("El "+estadistica+"% de los misiles han acertado");

    }
}



