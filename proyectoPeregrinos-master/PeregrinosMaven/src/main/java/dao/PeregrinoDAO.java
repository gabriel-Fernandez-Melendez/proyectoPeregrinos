/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexionBD.ConexPeregrino;
import entidades.Parada;
import entidades.Peregrino;
import entidades.Perfil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gabof
 */
public class PeregrinoDAO implements operacionesCRUD<Peregrino> {

    //aplicacion de patron singleton con la asignacion del objeto estatico y el metodo singleperegrino
    Connection conex;
    private static PeregrinoDAO p;

    public PeregrinoDAO(Connection conex) {
        if (this.conex == null) {
            this.conex = conex;
        }
    }

    public static PeregrinoDAO singlePeregrino(Connection conex) {
        if (p == null) {
            p = new PeregrinoDAO(conex);
            return p;
        }

        return p;
    }

    @Override
    public boolean insertarConID(Peregrino elemento) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public long insertarSinID(Peregrino p) {
        long ret = -1;
        //importante, incluir en esta clase el insert de los datos a la tabla credenciales
        String consultaInsertStr = "insert into peregrino(nombre,nacionalidad) values (?,?)";
        try {
            if (this.conex == null || this.conex.isClosed()) {
                conex = ConexPeregrino.establecerConexion();
            }
            PreparedStatement pstmt = conex.prepareStatement(consultaInsertStr);
            pstmt.setString(1, p.getNombre());
            pstmt.setString(2, p.getNacionalidad());
            int resultadoInsercion = pstmt.executeUpdate();
            //si se cumple la condicion hacemos un if para hacer el select
            if (resultadoInsercion == 1) {
                String consultaSelect = "SELECT id FROM peregrino WHERE nombre=? AND nacionalidad=? ";
                PreparedStatement pstmt2 = conex.prepareStatement(consultaSelect);
                pstmt2.setString(1, p.getNombre());
                pstmt2.setString(2, p.getNacionalidad());
                ResultSet result = pstmt2.executeQuery();
                while (result.next()) {
                    long id = result.getLong("id");
                    if (id != -1) //ret lleva el valor del id del peregrino antes de insertar en la tabla credenciales
                    {
                        ret = id;
                    }                   
                }
                result.close();
                pstmt2.close();
            }
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Se ha producido una SQLException:" + e.getMessage());
            e.printStackTrace();
            return -1;
        } catch (Exception e) {
            System.out.println("Se ha producido una Exception:" + e.getMessage());
            e.printStackTrace();
            return -1;
        }

        return ret;
    }

    @Override
    public Peregrino buscarPorID(long id) {
        //he tenido que crear un objeto para cada una de las fk , para pasar ese objeto "entero"(solo con el id)como parametro valido
        //he logrado eliminar el nullde sus claves foraneas!
        Peregrino p = new Peregrino();
        String select = "select * from peregrino where id=?";
        try {
            if (this.conex == null || this.conex.isClosed()) {
                this.conex = ConexPeregrino.establecerConexion();
            }
            PreparedStatement pstmt = conex.prepareStatement(select);
            pstmt.setLong(1, id);
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                long id_peregrino = result.getLong("id");
                String nombre = result.getString("nombre");
                String nacionalidad = result.getString("nacionalidad");
                p.setId(id_peregrino);
                p.setNombre(nombre);
                p.setNacionalidad(nacionalidad);
            }
            System.out.println("el resultado de tu consulta es:" + p.toString());
            conex.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    @Override
    public ArrayList<Peregrino> buscarTodos() {
       //importar que la coleccion este declarada fuera del while
        ArrayList<Peregrino> colecc = new ArrayList<Peregrino>();
        String select = "select * from peregrino";
        Statement pstmt;
        try {
            if (this.conex == null || this.conex.isClosed()) {
                this.conex = ConexPeregrino.establecerConexion();
            }
            pstmt = conex.createStatement();
            ResultSet result = pstmt.executeQuery(select);

            while (result.next()) {
                String nom_p = result.getString("nombre");
                String reg = result.getString("nacionalidad");
                long id_p = result.getLong("id");
                Peregrino p = new Peregrino();
                p.setId(id_p);
                p.setNombre(nom_p);
                p.setNacionalidad(reg);
                //esto tambien es para comprobar que el metodo funciona 
                System.out.println("el resultado de tu consulta es:" + p.toString());
                colecc.add(p);
            }

            conex.close();
        } catch (SQLException e) {
            System.out.println("exception sql");
            e.printStackTrace();
        }
        return colecc;
    }

    @Override
    public boolean modificar(Peregrino elemento) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean eliminar(Peregrino elemento) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    //este metodo unico de peregrino extrae las credenciales del usuario 
    public Collection<Peregrino> Credenciales() {
            List<Peregrino> colecc = new ArrayList<Peregrino>();
            String select = "select * from credenciales";
            Statement pstmt;
        try {
            if (this.conex == null || this.conex.isClosed()) {
                this.conex = ConexPeregrino.establecerConexion();
            }
            pstmt = conex.createStatement();           
            ResultSet result = pstmt.executeQuery(select);
            while (result.next()) {
                
                Long id_c = result.getLong("id");
                Long idper = result.getLong("id_peregrino");
                String tipo = result.getString("tipo");
                String usur = result.getString("usur");
                String pass = result.getString("pass");
                //el problema es que el  objeto en el que se almacena no puede estar declarado fuera del bucle while,por que? no lo se
                Peregrino p = new Peregrino();
                p.setId(idper);
                p.setPerfil(Perfil.valueOf(tipo));
                p.setContraseña(pass);
                p.setNombre(usur);
                //esto tambien es para comprobar que el metodo funciona
                colecc.add(p);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PeregrinoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    return colecc;
    }
    
    public static Peregrino idporcredenciales(Peregrino per){
        Peregrino peregrino= new Peregrino();
        return peregrino;
    }
    
}
