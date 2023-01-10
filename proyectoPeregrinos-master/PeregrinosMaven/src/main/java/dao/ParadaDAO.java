/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexionBD.ConexPeregrino;
import entidades.Carnet;
import entidades.Parada;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author gabof
 */
public class ParadaDAO implements operacionesCRUD <Parada>{
    //aplicacion de patron singleton con la asignacion del objeto estatico y el metodo singleperegrino
    Connection conex;
    private static ParadaDAO p;

	public ParadaDAO(Connection conex) {
		if (this.conex == null)
			this.conex = conex;
	}
        
        public static ParadaDAO singleParada(Connection conex) {
		if(p==null) {
		p=new ParadaDAO(conex);
		return p;
		}
		
		return p;
	}
        
    @Override
    public boolean insertarConID(Parada p) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public long insertarSinID(Parada p) {
        long ret = -1;

        String consultaInsertStr = "insert into parada(nombre,region) values (?,?)";
        try {
            if (this.conex == null || this.conex.isClosed()) {
                conex = ConexPeregrino.establecerConexion();
            }
            PreparedStatement pstmt = conex.prepareStatement(consultaInsertStr);
            pstmt.setString(1, p.getNombre());
            //vaya agradecido que estoy con stackoverflow
            pstmt.setString(2, String.valueOf(p.getRegion()));
            int resultadoInsercion = pstmt.executeUpdate();
            //si se cumple la condicion hacemos un if para hacer el select
            if (resultadoInsercion == 1) {
                String consultaSelect = "SELECT id FROM parada WHERE nombre=? AND region=? ";
                PreparedStatement pstmt2 = conex.prepareStatement(consultaSelect);
                pstmt2.setString(1, p.getNombre());
                pstmt2.setString(2, String.valueOf(p.getRegion()));
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
    public Parada buscarPorID(long id) {
        //he tenido que crear un objeto para cada una de las fk , para pasar ese objeto "entero"(solo con el id)como parametro valido
        //he logrado eliminar el nullde sus claves foraneas!
        Parada p = new Parada();
        String select = "select * from carnet where id=?";
        try {
            if (this.conex == null || this.conex.isClosed()) {
                this.conex = ConexPeregrino.establecerConexion();
            }
            PreparedStatement pstmt = conex.prepareStatement(select);
            pstmt.setLong(1, id);
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                long idp = result.getLong("id");
                String nombre=result.getString("nombre");
                String region=result.getString("region");
                char reg=region.charAt(0);
                p.setId(idp);
                p.setNombre(nombre);
                p.setRegion(reg);
            }
            System.out.println("el resultado de tu consulta es:" + p.toString());
            conex.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    @Override
    public Collection<Parada> buscarTodos() {
        //importarte que la coleccion este declarada fuera
      	List<Parada> colecc = new ArrayList<Parada>();
		String select="select * from parada";
		Statement pstmt;
		try {
			if (this.conex == null || this.conex.isClosed())
			this.conex = ConexPeregrino.establecerConexion();
			pstmt = conex.createStatement();
			ResultSet result = pstmt.executeQuery(select);
			while(result.next()) {
				long id_p=result.getLong("id");
				String nom_p=result.getString("nombre");
				String reg=result.getString("region");
				Parada p=new Parada();
                                p.setId(id_p);
                                p.setNombre(nom_p);
                                p.setRegion(reg.charAt(0));
				//esto tambien es para comprobar que el metodo funciona 
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
    public boolean modificar(Parada elemento) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean eliminar(Parada elemento) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
