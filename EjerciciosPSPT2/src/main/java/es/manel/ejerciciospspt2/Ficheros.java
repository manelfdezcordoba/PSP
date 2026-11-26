/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.manel.ejerciciospspt2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Semaphore;

/**
 *
 * @author manelfernandez
 */
public class Ficheros {
    public static final int MAX_LECTOR = 4;
    private File fichero;
    private Semaphore acceso;
    private Semaphore actualizar;
    private Semaphore mutex;
    private int lectoresEspera;
    
    public Ficheros(File fichero){
        this.fichero = fichero;
        this.acceso = new Semaphore(MAX_LECTOR, true);
        this.actualizar = new Semaphore(0);
        this.mutex = new Semaphore(1);
        this.lectoresEspera = 0 ;
        
    }
    //Buscamos la capital atraves del nombre del pais 
    public String consultarCapital(String nombreL, String pais)throws InterruptedException{
        String capital = null;
        //utilizaremos el boolean para avisarnos si no se ha encontrado la capital 
        boolean avisado = false;
        while(capital == null){
            //utilizamos el semaphore para darle permiso a acceder al fichero
            acceso.acquire();
            System.out.println(nombreL + " Entro a leer y busca "+ pais);
            
            capital = buscar(pais); // buscamos a traves del pais , la capital de ese pais, espera 0,4s
            Thread.sleep(400); 
            //Si no hay capital (null) un lector espera , mutex hace que espere el lector, para evitar que se sumen varios lectores a la vez
            if (capital== null) {
                mutex.acquire();
                lectoresEspera ++ ;
                mutex.release();
            }
            //acceso deja que otro lector entre 
            System.out.println(nombreL + " Ha dejado de leer");
            acceso.release();
            
            
            //Si la capital sigue siendo null , el boolean avisado , devuelve false , dandonos por consola un error y cambiando el boolean a true 
            //En actualziar se queda esperando hasta que un esritor haga un release
            if (capital == null) {
                if (!avisado) {
                System.out.println(nombreL + " no encuentra a " + pais + " y este se queda Bloqueado");
                avisado = true;
                }
                
                actualizar.acquire();
                System.out.println(nombreL + " se ha despertado y vuelve a buscar " + pais);
                }
            }
        
        
        
        return capital;
    }
    
    //Un escritor entrara a agregar un pais con su capital
    public boolean anadirPais(String nombreEsc , String pais, String capital)throws InterruptedException{
        //Esperamos que salgan todos los lectores, cuando salgan para dejar libre , empezara a escribir los paises
        boolean agregar = false;
        acceso.acquire(MAX_LECTOR);
        System.out.println(nombreEsc + " entra a escribir ");
        
        //Entra a buscar si existe el pais, en caso de que no exista , escribira un nuevo pais 
        if (buscar(pais)== null) {
            escribir(pais,capital);
            agregar = true;
            System.out.println(nombreEsc + " ha escrito un nuevo pais [" + pais + ":" + capital + "]");
           
            //En caso de qeu exista , no agregara ningun pais 
        }else{
            System.out.println(nombreEsc + " no ha agregado el pais "+ pais + " porque ya existe");
            
        }
        
        //mandamos a dormir los hilos , y devolvemos los 4 permisos 
        Thread.sleep(400);
        System.out.println(nombreEsc + " ha acabado de escribir");
        acceso.release(MAX_LECTOR);
        //Si se ha modificado el fichero texto.txt despierta a los lectores 
        if (agregar) {
            despertarLectores(nombreEsc);
        }
        return agregar;
        
    }
    
    
    private void despertarLectores(String nombreEsc) throws InterruptedException{
    //protegemos a los lectores hasya que llegan a 0 , despues cuando llegue a     
        mutex.acquire();
        if (lectoresEspera > 0) {
            //Damos permiso por cada lector bloqueado , asi despertamos a todos y el contador de lectoresEspera se pone a 0 
            System.out.println(nombreEsc + " despeirta a " + lectoresEspera + " lector");
            actualizar.release(lectoresEspera);
            lectoresEspera = 0 ;
            
        }
        mutex.release();
    }
    
    //buscamos la capital 
    private String buscar(String pais ){
        String capital = null;
        //Leemos el archivo ficheros ,hacemos un bucle hasta que deje de leer y capital deje de estar en null, 
        //este lo separa cuando encuentre ":" , dividiendolo en [pais], [capital] , nuestro string cogera la capital y nos la devolvera 
        try{
            BufferedReader br = new BufferedReader(new FileReader(fichero));
       String linea;
       while ((linea = br.readLine()) != null && capital == null){
           String[] partes = linea.split(":");
           if (partes[0].equalsIgnoreCase(pais)) {
               capital = partes[1];              
           }
       }
       br.close();            
        }catch(IOException ex){
        System.getLogger(Ficheros.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);  
        }
        return capital;
    }
    
    
    //Escribimos el pais y la capital 
    private void escribir(String pais, String capital){
        try{
            PrintWriter pw = new PrintWriter(new FileWriter(fichero,true));
            pw.println(pais + ":" + capital);
            pw.close();
            
        }catch(IOException ex){
             System.getLogger(Ficheros.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);  
       
        }
    }
}
