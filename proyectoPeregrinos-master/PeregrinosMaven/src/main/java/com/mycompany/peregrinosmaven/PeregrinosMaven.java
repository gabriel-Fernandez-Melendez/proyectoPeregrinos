package com.mycompany.peregrinosmaven;

import conexionBD.ConexPeregrino;
import dao.CarnetDAO;
import dao.EstanciaDAO;
import dao.ParadaDAO;
import dao.PeregrinoDAO;
import entidades.Carnet;
import entidades.Estancia;
import entidades.Menus;
import static entidades.Menus.TipoDeRegistro;

import entidades.Parada;
import static entidades.Parada.LectorParadas;
import static entidades.Parada.exportarParada;
import static entidades.Parada.mostrarParadas;
import static entidades.Parada.nuevaParada;
import entidades.Peregrino;
import static entidades.Peregrino.registrarPeregrino;
import static entidades.Peregrino.xmlPeregrino;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static lectores.Lectores.*;

/**
 *
 * @author gabof
 */
public class PeregrinosMaven {

    public static void main(String[] args) {

        //   inicio del menu principal y las funcionalidades del programa!
        
        
        
        while (true) {
            Menus m=new Menus();
            m.menuInicial();
        }   
    }
}

//esta conexion sirve para todos los Dao
//Connection c=ConexPeregrino.getCon();
// ArrayList<Peregrino> estancias = new ArrayList<Peregrino>();
//PeregrinoDAO p=PeregrinoDAO.singlePeregrino(c);
//estancias= (ArrayList<Peregrino>) p.Credenciales();
//for(Peregrino per:estancias){
//    System.out.println(per.toString());
//}

//}}

//ParadaDAO par=ParadaDAO.singleParada(c);
//ArrayList<Parada> estancias = new ArrayList<Parada>();
//estancias=(ArrayList<Parada>) par.buscarTodos();
//        System.out.println(estancias.toString());
//    }}

//asi indroduje los nuevos peregrinos con sus carnets
//Parada parada=new Parada(1,"Pastoral",'G');
//Carnet carnet=new Carnet(5,LocalDate.now(),0.0F,4,parada);
//Peregrino peregrino=new Peregrino(2,"john","srgfh","Venezuela",carnet);
//boolean val=false;
//Estancia estancia=new Estancia(1,LocalDate.now(),val,parada,peregrino);
//EstanciaDAO e=EstanciaDAO.singleEstancia(c);
//long id=e.insertarSinID(estancia);
//
//
//        System.out.println("el id del registro introducido es: "+id);


//esta es la forma en la cual introduje los datos de las paradas

//Connection c=ConexPeregrino.getCon();
//ParadaDAO P=new ParadaDAO(c);
//Parada p=new Parada(4,"Bar el caminante",'A');
//long id=P.insertarSinID(p);
//        System.out.println("tu id es: "+id);