/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lectores;

import conexionBD.ConexPeregrino;
import dao.CarnetDAO;
import dao.EstanciaDAO;
import dao.ParadaDAO;
import dao.PeregrinoDAO;
import entidades.Carnet;
import entidades.Parada;
import entidades.Peregrino;
import entidades.Perfil;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import validaciones.Validadores;

/**
 *
 * @author gabof
 */
public class Lectores {

    

    //metodo obsoleto
    public static ArrayList<Parada> LectoresParadas() throws IOException {
        ArrayList<Parada> paradas = new ArrayList<Parada>();
        FileInputStream fis = null;
        DataInputStream entrada = null;
        String aux;
        try {
            fis = new FileInputStream("ficherosPeregrino\\paradas.dat");
            entrada = new DataInputStream(fis);
            while (!entrada.readBoolean()) {
                aux = entrada.toString();  //se lee  un entero del fichero                                           
                System.out.println(aux);  //se muestra en pantalla
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (EOFException e) {
            System.out.println("Fin de fichero");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (entrada != null) {
                    entrada.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return paradas;
    }

    
    //metodo obsoleto usado previo a la base de datos
    public static Peregrino Login() {
        Peregrino validador = new Peregrino();
        String nombre, contraseña;
        Scanner scan = new Scanner(System.in);
        System.out.println("introduzca el nombre de usuario: ");
        nombre = scan.nextLine();
        System.out.println("introduzca la contraseña de usuario: ");
        contraseña = scan.nextLine();
        validador.setNombre(nombre);
        validador.setContraseña(contraseña);
        validador.setPerfil(Perfil.Peregrino);
        return validador;
    }
    
    
    //login en caso de entrar como administrador de paradas 
     public static Peregrino Loginparada() {
        Peregrino validador = new Peregrino();
        String nombre, contraseña;
        Scanner scan = new Scanner(System.in);
        System.out.println("introduzca el nombre de usuario: ");
        nombre = scan.nextLine();
        System.out.println("introduzca la contraseña de usuario: ");
        contraseña = scan.nextLine();
        validador.setNombre(nombre);
        validador.setContraseña(contraseña);
        validador.setPerfil(Perfil.AdministradordeParadas);
        return validador;
    }
     
     
     //login en caso de entrar como admingeneral
      public static Peregrino LoginAdmin() {
        Peregrino validador = new Peregrino();
        String nombre, contraseña;
        Scanner scan = new Scanner(System.in);
        System.out.println("introduzca el nombre de usuario: ");
        nombre = scan.nextLine();
        System.out.println("introduzca la contraseña de usuario: ");
        contraseña = scan.nextLine();
        validador.setNombre(nombre);
        validador.setContraseña(contraseña);
        validador.setPerfil(Perfil.AdministradorGeneral);
        return validador;
    }
    
    //metodo obsoleto usado antes de la base de datos
    public static boolean validarNuevoUsuario(Peregrino p){
        boolean val =false;
        //aqui se valida si las credenciales del nuevo peregrino estan ya ingresadas o  no 
        Connection conex =ConexPeregrino.establecerConexion();
        PeregrinoDAO P=PeregrinoDAO.singlePeregrino(conex);
        ArrayList<Peregrino> colecc = new ArrayList<Peregrino>();
        colecc=(ArrayList<Peregrino>) P.Credenciales();
        for(Peregrino per:colecc){
            if(p.getContraseña().equalsIgnoreCase(per.getContraseña())&&p.getNombre().equalsIgnoreCase(per.getNombre())){
                  System.out.println("estas credenciales ya han sido registradas!,escoja unas diferentes");
                  val=false;
                  //IMPORTANTE, hay que poer return para que se pare la ejecucion del codigo en ese punto si no cuntinua hasta que sea true!
                  return val;
            }
            else{
                val=true;
            }
        }
       return val;
    }
    
    public static boolean loginBDper(Peregrino p){
        boolean val=true;
        Connection conex=ConexPeregrino.establecerConexion();
        PeregrinoDAO peregrino =PeregrinoDAO.singlePeregrino(conex);
        ArrayList<Peregrino> colecc = new ArrayList<Peregrino>();
        colecc=(ArrayList<Peregrino>) peregrino.Credenciales();
        for (Peregrino per:colecc){
            if(p.getNombre().equalsIgnoreCase(per.getNombre().toString())&&p.getContraseña().equalsIgnoreCase(per.getContraseña().toString())&& p.getPerfil()==per.getPerfil()){
                System.out.println("bienvenido peregrino");
                p.setId(per.getId());
                val=true;
                return val;
            }
            else{
                val=false;
            }
            
        }
        System.out.println("las credenciales no son validas, introduzcalas de nuevo: ");
        return val;
    }
    
     public static Peregrino loginBDparada(Peregrino p){
        Peregrino val=new Peregrino();
        Connection conex=ConexPeregrino.establecerConexion();
        PeregrinoDAO peregrino =PeregrinoDAO.singlePeregrino(conex);
        ArrayList<Peregrino> colecc = new ArrayList<Peregrino>();
        colecc=(ArrayList<Peregrino>) peregrino.Credenciales();
        for (Peregrino per:colecc){
            if(p.getNombre().equalsIgnoreCase(per.getNombre().toString())&&p.getContraseña().equalsIgnoreCase(per.getContraseña().toString())&& p.getPerfil()==per.getPerfil()){
                System.out.println("bienvenido peregrino");
                //gracias a poner val como per recojo de aqui  u n peregrino con sus datos necesarios para el correcto manejo de las funcionalidades!
                val=per;
                return val;
                
            }
                      
        }
        System.out.println("las credenciales no son validas, introduzcalas de nuevo: ");
        return val;
    }
     
     public static boolean loginBDAdmin(Peregrino p){
        boolean val=true;
        Connection conex=ConexPeregrino.establecerConexion();
        PeregrinoDAO peregrino =PeregrinoDAO.singlePeregrino(conex);
        ArrayList<Peregrino> colecc = new ArrayList<Peregrino>();
        colecc=(ArrayList<Peregrino>) peregrino.Credenciales();
        for (Peregrino per:colecc){
            if(p.getNombre().equalsIgnoreCase(per.getNombre().toString())&&p.getContraseña().equalsIgnoreCase(per.getContraseña().toString())&& p.getPerfil()==per.getPerfil()){
                System.out.println("bienvenido Administrador general");
                val=true;
                return val;
            }
            else{
                val=false;
            }
            
        }
        System.out.println("las credenciales no son validas, introduzcalas de nuevo: ");
        return val;
    }
     
     
     //a este metodo hay que incluirle la info del carnet al peregrino para poder mostrarlo
     public static void ExportarParadaADMIN(Peregrino per){
          Connection conexion=ConexPeregrino.getCon();
         if(per.getPerfil()==Perfil.AdministradordeParadas){
        
         CarnetDAO c=CarnetDAO.singleCarnet(conexion);
         Carnet carnet=c.buscarPorID(per.getId());
         //asi puedo mastrar los datos de la parada
         if(carnet != null){
             per.setCarnet(carnet);
         }
         }
         boolean validador=false;
                  LocalDate fechalocaldate;
                  LocalDate fechalocaldate2;
         System.out.println("Buenas tardes administrador , usted trabaja en la parada: "+per.getCarnet().getParada().toString());
         //aqui va el metodo que busca en un  rango de fechas concreto 
         do{
         Scanner scan= new Scanner(System.in);
         System.out.println("introduce la fecha inicial en la cual quieres realizar la exportacion");
         String fechastring=scan.nextLine();
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
         fechalocaldate = LocalDate.parse(fechastring, formatter);
         validador=Validadores.validarFechaNueva(fechalocaldate);
         }while(!validador);
         System.out.println("la fecha es valida!");
          do{
         System.out.println("introduce la fecha final en la cual quieres realizar la exportacion"); 
         Scanner scan= new Scanner(System.in);
         String fechastring2=scan.nextLine();
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
         fechalocaldate2 = LocalDate.parse(fechastring2, formatter);
         validador=Validadores.validarFechaNueva(fechalocaldate);
         }while(!validador);
          System.out.println("la fecha es valida!");
          System.out.println("el rango de fechas es "+fechalocaldate +"a"+fechalocaldate2+"y los datos del administrador son: "+per.getNombre()+per.getId()+per.getCarnet().getParada()+" es correcto?");
          validador=Validadores.leerBoolean();
          if(validador){
              System.out.println("va a comenzar la consulta de los datos aportados por el administrador");
              //aqui va el metodo con el cual hacemos la ocnsulta
              EstanciaDAO e=EstanciaDAO.singleEstancia(conexion);
              e.ExportarDatosParada(per, fechalocaldate, fechalocaldate2);
          }
     }
     
    public static Perfil tipodeperfilgeneral(){
        boolean val=false;    
        Perfil perfil;
        Scanner scan=new Scanner(System.in);
        
        int elecc;
        do{
        System.out.println("buenas administrador general, como desea operar el dia de hoy ?");
        int i=1;
        for(Perfil p: Perfil.values()){
            System.out.println(i+"-"+p.getTipodeperfil());
            i++;
        }
        elecc=scan.nextInt();
        System.out.println("el perfil que usara es el: "+Perfil.values()[elecc - 1]+" es esto correcto ? ");
        val=Validadores.leerBoolean();
        }while(!val);
        perfil=Perfil.values()[elecc - 1];
        return perfil; 
    }

    public static Parada seleccionadordeparadaGeneral(){
        ArrayList<Parada> paradas = new ArrayList<Parada>();
        boolean val=false;    
        Parada parada;
        Scanner scan=new Scanner(System.in);
        Connection conexion=ConexPeregrino.getCon();
        ParadaDAO p=ParadaDAO.singleParada(conexion);
        paradas = (ArrayList<Parada>) p.buscarTodos();
        int elecc;
        do{
        System.out.println("buenas administrador general, como desea operar el dia de hoy ?");
        int i=1;
        for(Parada par: paradas){
            System.out.println(i+"-"+par.toString());
            i++;
        }
        elecc=scan.nextInt();
        System.out.println("la parada que usara es: "+paradas.get(elecc-1)+" es esto correcto ? ");
        val=Validadores.leerBoolean();
        }while(!val);
        parada=paradas.get(elecc-1);
        System.out.println("usted va a trabajar como administrador de la parada : "+parada.toString());
        return parada; 
    }
}
