/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexionBD.ConexPeregrino;
import entidades.Carnet;
import entidades.Parada;
import entidades.Peregrino;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

/**
 *
 * @author gabof
 */
public class CarnetDAO implements operacionesCRUD<Carnet> {

    //aplicacion de patron singleton con la asignacion del objeto estatico y el metodo singleperegrino
    Connection conex;
    private static CarnetDAO c;

    
    public CarnetDAO(Connection conex) {
        if (this.conex == null) {
            this.conex = conex;
        }
    }

    public static CarnetDAO singleCarnet(Connection conex) {
        if (c == null) {
            c = new CarnetDAO(conex);
            return c;
        }

        return c;
    }

    @Override
    public boolean insertarConID(Carnet elemento) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public long insertarSinID(Carnet c) {
        long ret = -1;

        String consultaInsertStr = "insert into carnet(id_peregrino,fecha_exp,distancia,n_vips,nombre_parada) values (?,?,?,?,?)";
        try {
            if (this.conex == null || this.conex.isClosed()) {
                conex = ConexPeregrino.establecerConexion();
            }
            PreparedStatement pstmt = conex.prepareStatement(consultaInsertStr);
            pstmt.setLong(1, c.getIdPeregrino());
            java.sql.Date fechaSQL = java.sql.Date.valueOf(c.getFechaExp());
            pstmt.setDate(2, fechaSQL);
            pstmt.setDouble(3, c.getDistancia());
            pstmt.setInt(4, c.getnVips());
            pstmt.setString(5, c.getParada().getNombre());
            int resultadoInsercion = pstmt.executeUpdate();
            //si se cumple la condicion hacemos un if para hacer el select
            if (resultadoInsercion == 1) {
                String consultaSelect = "SELECT id_peregrino FROM carnet WHERE fecha_exp=? AND nombre_parada=? ";
                PreparedStatement pstmt2 = conex.prepareStatement(consultaSelect);
                pstmt2.setDate(1, fechaSQL);
                pstmt2.setString(2, c.getParada().getNombre());
                ResultSet result = pstmt2.executeQuery();
                while (result.next()) {
                    long id = result.getLong("id_peregrino");
                    if (id != -1) {
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
    public Carnet buscarPorID(long id) {
        //he tenido que crear un objeto para cada una de las fk , para pasar ese objeto "entero"(solo con el id)como parametro valido
        //he logrado eliminar el nullde sus claves foraneas!
        Carnet c = new Carnet();
        Parada p = new Parada();
        String select = "select * from carnet where id_peregrino=?";
        try {
            if (this.conex == null || this.conex.isClosed()) {
                this.conex = ConexPeregrino.establecerConexion();
            }
            PreparedStatement pstmt = conex.prepareStatement(select);
            pstmt.setLong(1, id);
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                long id_peregrino = result.getLong("id_peregrino");
                //hace falta hacer esta convercion para poder pasarla al objeto
                java.sql.Date fechaSQL =result.getDate("fecha_exp");
		LocalDate fecha=fechaSQL.toLocalDate();
                double distancia=result.getDouble("distancia");
                int nvips=result.getInt("n_vips");
                String nombre_p=result.getString("nombre_parada");
                // no estaba incluido el id asi que lo agrego por utilidad y ampliar las posibilidades de la funcionalidad!
                c.setIdPeregrino(id_peregrino);
                c.setFechaExp(fecha);
                c.setDistancia(distancia);
                c.setnVips(nvips);
                //en este punto hace falta settear a la parada su nombre y pasarla al carnet
                p.setNombre(nombre_p);
                c.setParada(p);
            }
            System.out.println("el resultado de tu consulta es:" + c.toString());
            conex.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    @Override
    public Collection<Carnet> buscarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean modificar(Carnet elemento) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean eliminar(Carnet elemento) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public boolean aumentarDistancia(Long id){
        boolean val = false;
        String select = " update carnet set distancia = distancia+5.0 where id_peregrino =? ";
        try {
            if (this.conex == null || this.conex.isClosed()) {
                this.conex = ConexPeregrino.establecerConexion();
            }
            PreparedStatement pstmt = conex.prepareStatement(select);
            pstmt.setLong(1, id);
             int resultado = pstmt.executeUpdate();
             if(resultado == 1){
                 System.out.println("se registro la distancia recorrida por el peregrino y se ha sellado su carnet!");
                 val=true;
             }
            conex.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return val;
    }

}
