/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.manel.ejerciciospspt2;

import java.util.Random;

/**
 *
 * @author manelfernandez
 */

public class Lector extends Thread{
    private String nombre;
    private String pais;
    private Ficheros ficheroPais;
  
    
    //Creamos el constructor de nuestra clase lector
    public Lector ( String nombre, String pais, Ficheros ficheroPais){
        this.nombre = nombre;
        this.pais = pais;
        this.ficheroPais = ficheroPais;
    }
    
    
    //en el run , utilizamos la clase Fichero, en el metodo consultarCapital le agregamos el nombre del lector y el pais que queremos que lea
    //En caso de que coincida el pais nos devolvera un mensaje en la terminal , que nos dice cual es la capital , si no , nos manda excepcion 
    @Override 
    public void run(){
        
        try {
            String capital = ficheroPais.consultarCapital(nombre, pais);
            System.out.println("-"  + nombre + " la capital de " + pais + " es " + capital );
        } catch (InterruptedException ex) {
            System.getLogger(Lector.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}
