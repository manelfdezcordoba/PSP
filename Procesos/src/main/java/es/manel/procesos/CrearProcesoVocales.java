/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.manel.procesos;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Lanza un proceso hijo que ejecuta la clase Vocales para una vocal y muestra
 * el resultado que devuelve.
 *
 * @author manelfernandez
 */
public class CrearProcesoVocales {

    private char vocal;
    private File fichero;
    private Process p;

    public CrearProcesoVocales(char vocal, File fichero) {
        this.vocal = vocal;
        this.fichero = fichero;

        String java = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        String clase = "es.manel.procesos.Vocales";
        String letra = String.valueOf(vocal);
        String archivo = fichero.getAbsolutePath();

        ProcessBuilder pb = new ProcessBuilder(java, clase, letra, archivo);

        pb.directory(new File("target/classes"));

        try {
            p = pb.start();

            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));
            System.out.println(br.readLine());
            br.close();
        } catch (IOException ex) {
            System.getLogger(CrearProcesoVocales.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    public Process getP() {
        return p;
    }
}
