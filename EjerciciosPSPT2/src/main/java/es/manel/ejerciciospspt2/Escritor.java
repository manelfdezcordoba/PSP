/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.manel.ejerciciospspt2;

/**
 *
 * @author manelfernandez
 */
public class Escritor extends  Thread {
    
    private String nombre;
    private String pais;
    private String capital;
    private int tiempo ; 
    private Ficheros ficheropaises;

    public Escritor(String nombre, String pais, String capital, int tiempo, Ficheros ficheropaises) {
        this.nombre = nombre;
        this.pais = pais;
        this.capital = capital;
        this.tiempo = tiempo;
        this.ficheropaises = ficheropaises;
    }
    
    
    //Un escritor , agregara un pais y su capital 
    @Override
    public void run(){
        try {
            
            Thread.sleep(tiempo);
            ficheropaises.anadirPais(nombre, pais, capital);
            
        } catch (InterruptedException ex) {
            System.getLogger(Escritor.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
    }
    
    
}
