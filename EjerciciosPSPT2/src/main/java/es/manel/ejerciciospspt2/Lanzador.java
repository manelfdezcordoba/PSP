/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package es.manel.ejerciciospspt2;

import java.io.File;

/**
 *
 * @author manelfernandez
 */
public class Lanzador {

    
    public static void main(String[] args) {
        
        //Busca el fichero , si no lo encuentra nos dara mensaje de error 
        File f = new File("texto.txt");
        if (!f.exists()) {
            System.out.println("No se encuentra el fichero "+ f.getAbsolutePath());
            return;
        }
        
        
        Ficheros ficheropaises = new Ficheros(f);
        //Le decimos los paises que buscaran los lectores 
    String[] paisesLec = {"Francia" , "Japon", "China", "Espana"};
    //una array de los paises que hay , el tamaño sera , de los paises que hay 
    
    Lector[] lectores = new Lector[paisesLec.length];
    
    
    Escritor[] escritores = new Escritor[3];
    //agregamos los escritores, su nombre, el pais, capital , tiempo de espera y la clase Ficheros
    escritores[0] = new Escritor("Escritor-1", "Grecia", "Atenas", 600 , ficheropaises);
    escritores[1] = new Escritor("Escritor-2", "Noruega", "Oslo", 900, ficheropaises); 
    escritores[2] = new Escritor("Escritor-3", "China", "Pekin", 1500, ficheropaises);
    
    
    //Se crea un lector con su nombre y el recurso compartido
        System.out.println("Lanzando Lectores y Escritores");
        for (int i = 0; i < lectores.length; i++) {
            lectores[i] = new Lector("Lector- " + (i+1), paisesLec[i], ficheropaises);
            // crea un hilo que ejecutara el run
            lectores[i].start();
        }
        
        //creamos otro hilo y utiliza el run 
        for (int i = 0; i < escritores.length; i++) {
            escritores[i].start();
        }

        
        //Espera a que el hilo lectores termine 
        try {
            for (int i = 0; i < lectores.length; i++) {
                lectores[i].join();
            }
            //Espera que el hilo de los escritores termine 
            for (int i = 0; i < escritores.length; i++) {
                escritores[i].join();
            }
        } catch (InterruptedException ex) {
            System.getLogger(Lanzador.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
//Se mostrara el ultimo mensaje de todo el programa gracias a los join
        System.out.println("Fin del programa");
    }

    }
    

