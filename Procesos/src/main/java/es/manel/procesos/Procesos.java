/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package es.manel.procesos;

import java.io.File;

/**
 * Lanza un proceso por cada vocal para contarlas en el fichero.
 *
 * @author manelfernandez
 */
public class Procesos {

    public static void main(String[] args) {
        File fichero = new File("vocales.txt");
        if (!fichero.exists()) {
            System.out.println("No se encuentra el fichero " + fichero.getAbsolutePath());
            return;
        }

        CrearProcesoVocales p1 = new CrearProcesoVocales('a', fichero);
        CrearProcesoVocales p2 = new CrearProcesoVocales('e', fichero);
        CrearProcesoVocales p3 = new CrearProcesoVocales('i', fichero);
        CrearProcesoVocales p4 = new CrearProcesoVocales('o', fichero);
        CrearProcesoVocales p5 = new CrearProcesoVocales('u', fichero);

        try {
            p1.getP().waitFor();
            p2.getP().waitFor();
            p3.getP().waitFor();
            p4.getP().waitFor();
            p5.getP().waitFor();
        } catch (InterruptedException ex) {
            System.getLogger(Procesos.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}
