/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.manel.procesos;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Cuenta cuantas veces aparece una vocal en un fichero.
 * Recibe la vocal y la ruta del fichero como argumentos del main.
 *
 * @author manelfernandez
 */
public class Vocales {

    public static int contarVocales(char vocal, File fichero) {
        int numVocales = 0;
        try {
            FileReader fr = new FileReader(fichero);
            int letra;
            while ((letra = fr.read()) != -1) {
                if (Character.toLowerCase((char) letra) == vocal) {
                    numVocales++;
                }
            }
            fr.close();
        } catch (IOException ex) {
            System.getLogger(Vocales.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return numVocales;
    }

    public static void main(String[] args) {
        if (args.length == 2) {
            char vocal = args[0].charAt(0);
            File fichero = new File(args[1]);
            int numVocales = contarVocales(vocal, fichero);
            System.out.println("vocal " + vocal + " -> " + numVocales);
        } else {
            System.out.println("Argumentos incorrectos");
        }
    }
}
