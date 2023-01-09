/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import validaciones.Validadores;

/**
 *
 * @author gabof
 */
public class Parada implements Serializable {

    private static final long serialVersionUID = 1132456L;
    private long id;
    private String nombre;
    private char region;
    //conexion con paradas para la tarea dos 
    public static ArrayList<Peregrino> peregrinos=new ArrayList();

    public Parada() {

    }

    public Parada(long id, String nombre, char region) {
        this.id = id;
        this.nombre = nombre;
        this.region = region;

    }

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public char getRegion() {
        return region;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setRegion(char region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "Parada: " + "id: " + id + ", nombre: " + nombre + ", region: " + region + '.';
    }

    public static Parada nuevaParada() {
        boolean validador = false;
        Parada export = new Parada();
        int id;
        String nombre;
        char reg;
        Scanner scan = new Scanner(System.in);
        do {
            System.out.println("introduce id de parada: ");
            id = scan.nextInt();
            if (id > 1 || id < 99) {
                export.setId(id);
                validador = true;
            }
        } while (!validador);
        validador = false;
        do {
            validador = false;
            System.out.println("introduce nombre de parada: ");
            nombre = scan.nextLine();
            validador = Validadores.validarNombre(nombre);
            if (validador) {
                export.setNombre(nombre);
                //puede que esta instruccion sea redundante
                validador = true;
            }
        } while (!validador);
        do {
            System.out.println("introduce letra de la regin de la parada: ");
            reg = scan.next().charAt(0);
            System.out.println("es " + reg + " correcto ?");
            validador = Validadores.leerBoolean();
            if (validador) {
                export.setRegion(reg);
                validador = true;
            }
        } while (!validador);
        return export;
    }

    public static void exportarParada(Parada parada) {
        FileOutputStream fos;
        try {
            //importante , esta linea ha de tener siempre al final un true para que pueda escribir mas objetos en su interior
            fos = new FileOutputStream("ficherosPeregrino\\paradas.dat", true);
            ObjectOutputStream objeto = new ObjectOutputStream(fos);
            objeto.writeObject((Parada)parada);
            objeto.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Parada.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Parada.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //lee las paradas (este metodo ya no se usa,ahora se leen desde la base de datos)
    public static ArrayList<Parada> LectorParadas() {
        ArrayList<Parada> paradas = new ArrayList<Parada>();

        FileInputStream fstream;
        try {
            boolean validador= true;
            fstream = new FileInputStream("ficherosPeregrino\\paradas.dat");
            ObjectInputStream ostream = new ObjectInputStream(fstream);
            Parada aux;
            while(validador){ 
                Parada obj=null;
                try {
                    obj =(Parada) ostream.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                 if(obj != null)
                     paradas.add(obj);
                 else
                     validador = false;
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Parada.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Parada.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            System.out.println("la coleccion contiene las siguientes paradas: " + paradas.toString());
            return paradas;
        }

        System.out.println("la coleccion contiene las siguientes paradas: " + paradas.toString());
        return paradas;
    }
  
    //muestra las paradas
    public static void mostrarParadas(ArrayList<Parada> paradas ){
        int i=1;
        for(Parada p: paradas){
            System.out.println(i+"- " + p.toString());
            i++;
        }
    }
    
    //no recordaba esto pero es muy util
    public static Parada DevolverParadaPeregrino(Peregrino p){
            //este metodo pasa de una coleccion de paradas a una sola parada en concreto que es la que se envia a la base de datos como inicial
        Parada parada;
         ArrayList<Parada> paradas = new ArrayList<Parada>();
         paradas=p.getParadas();
         parada=paradas.get(0);
         return parada;
    }
    
    //muestra el fichero de nacionalidades 
    public static ArrayList<String> LectoresNacionalidades() {
        ArrayList<String> nacionalidades = new ArrayList<String>();
        File fichero = null;
        FileReader lector = null;
        BufferedReader buffer = null;

        try {
            buffer = new BufferedReader(new FileReader(new File("ficherosPeregrino\\nacionalidades.txt")));
            String linea = null;
            int i = 1;
            while ((linea = buffer.readLine()) != null) {
                System.out.println(i + "- " + linea);
                nacionalidades.add(linea);
                i++;
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (buffer != null)
  try {
                buffer.close();
            } catch (IOException ioe) {
            }
        }
        return nacionalidades;
    }
}
