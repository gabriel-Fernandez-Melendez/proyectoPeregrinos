/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexionBD.ConexPeregrino;
import entidades.Estancia;
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
public class EstanciaDAO implements operacionesCRUD <Estancia>{
    //aplicacion de patron singleton con la asignacion del objeto estatico y el metodo singleperegrino
    Connection conex;
    private static EstanciaDAO e;

	public EstanciaDAO(Connection conex) {
		if (this.conex == null)
			this.conex = conex;
	}
        
        public static EstanciaDAO singleEstancia(Connection conex) {
		if(e==null) {
		e=new EstanciaDAO(conex);
		return e;
		}
		
		return e;
	}
        
    @Override
    public boolean insertarConID(Estancia elemento) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public long insertarSinID(Estancia es) {
         long ret = -1;

        String consultaInsertStr = "insert into estancias(id_peregrino,fecha,vip,nombre_parada) values (?,?,?,?)";
        try {
            if (this.conex == null || this.conex.isClosed()) {
                conex = ConexPeregrino.establecerConexion();
            }
            PreparedStatement pstmt = conex.prepareStatement(consultaInsertStr);
            pstmt.setLong(1,es.getPeregrino().getId());
            java.sql.Date fechaSQL = java.sql.Date.valueOf(es.getFecha());
            pstmt.setDate(2, fechaSQL);
            pstmt.setBoolean(3,es.isVip());
            //nota de que seguramente necesite algo  que lleve todos estos datos atravez del problama
            pstmt.setString(4, es.getParada().getNombre());
            int resultadoInsercion = pstmt.executeUpdate();
            //si se cumple la condicion hacemos un if para hacer el select
            if (resultadoInsercion == 1) {
                String consultaSelect = "SELECT id FROM estancias WHERE id_peregrino=? AND nombre_parada=? ";
                PreparedStatement pstmt2 = conex.prepareStatement(consultaSelect);
                pstmt2.setLong(1, es.getPeregrino().getId());
                pstmt2.setString(2, es.getParada().getNombre());
                ResultSet result = pstmt2.executeQuery();
                while (result.next()) {
                    long id = result.getLong("id");
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
    public Estancia buscarPorID(long id) {
      //he tenido que crear un objeto para cada una de las fk , para pasar ese objeto "entero"(solo con el id)como parametro valido
        //he logrado eliminar el nullde sus claves foraneas!
        Estancia estancia = new Estancia();
        Peregrino p=new Peregrino();
        Parada parada =new Parada();
        String select = "select * from estancias where id=?";
        try {
            if (this.conex == null || this.conex.isClosed()) {
                this.conex = ConexPeregrino.establecerConexion();
            }
            PreparedStatement pstmt = conex.prepareStatement(select);
            pstmt.setLong(1, id);
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
               //id	id_peregrino	fecha	vip	nombre_parada
                long ide = result.getLong("id");
                long idp=result.getLong("id_peregrino");
                java.sql.Date fechaSQL =result.getDate("fecha");
		LocalDate fecha=fechaSQL.toLocalDate();
                boolean vip=result.getBoolean("vip");
                String nombrep=result.getString("nombre_parada");
                estancia.setId(ide);
                //seteamos el id al peregrino para luego pasarlo a la estancia
                p.setId(idp);
                estancia.setPeregrino(p);
                estancia.setFecha(fecha);
                estancia.setVip(vip);
                //lo mismo con la parada
                parada.setNombre(nombrep);
                estancia.setParada(parada);
            }
            System.out.println("el resultado de tu consulta es:" + estancia.toString());
            conex.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return estancia;
    }

    @Override
    public Collection<Estancia> buscarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean modificar(Estancia elemento) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean eliminar(Estancia elemento) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    //todo este metodo hay que cambiarlo para que funcione con la base de datos
    public void ExportarDatosParada(Peregrino per){
        Estancia estancia = new Estancia();
        Peregrino p=new Peregrino();
        Parada parada =new Parada();
        String select = "select * from estancias where nombre_parada=? and fecha between ? and ?";
        try {
            if (this.conex == null || this.conex.isClosed()) {
                this.conex = ConexPeregrino.establecerConexion();
            }
            PreparedStatement pstmt = conex.prepareStatement(select);
            pstmt.setString(1, per.getCarnet().getParada().toString());
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
               //id	id_peregrino	fecha	vip	nombre_parada
                long ide = result.getLong("id");
                long idp=result.getLong("id_peregrino");
                java.sql.Date fechaSQL =result.getDate("fecha");
		LocalDate fecha=fechaSQL.toLocalDate();
                boolean vip=result.getBoolean("vip");
                String nombrep=result.getString("nombre_parada");
                estancia.setId(ide);
                //seteamos el id al peregrino para luego pasarlo a la estancia
                p.setId(idp);
                estancia.setPeregrino(p);
                estancia.setFecha(fecha);
                estancia.setVip(vip);
                //lo mismo con la parada
                parada.setNombre(nombrep);
                estancia.setParada(parada);
            }
            System.out.println("el resultado de tu consulta es:" + estancia.toString());
            conex.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
    }
    
}
