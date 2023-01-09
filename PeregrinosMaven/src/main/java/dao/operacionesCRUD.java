/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.util.Collection;

/**
 *
 * @author gabof
 */
public interface operacionesCRUD<T> {
    /***
	 * Este método inserta en la tabla correspondiente de la BD peregrinos un
	 * nuevo registro
	 * 
	 * @param elemento del tipo que se quiere insertar como nuevo elemento completo
	 *                 (con ID)
	 * @return true si la inserción fue exitosa, false en caso contrario
	 */
    //IMPORTANTE este metodo seguramente no se llegue a implementar ya que el campo de id es autoincremental en la base de datos
	public boolean insertarConID(T elemento);

	/***
	 * Este método inserta en la tabla correspondiente de la BD peregrinos un
	 * nuevo registro
	 * 
	 * @param elemento del tipo que se quiere insertar como nuevo elemento completo
	 *                 (sin ID, que es autocalculado)
	 * @return id del nuevo elemento insertado si tuvo éxito, o -1 en caso contarrio
	 */
        //para esta segunda entrega implementar este metodo(SOLO INSERTAR SIN ID Y BUSCAR POR ID)
	public long insertarSinID(T elemento);

	/***
	 * Funcion que busca en la tabla correspondiente si hay un elemento cuyo id
	 * coincide con el que se pasa como parámero
	 * 
	 * @param id identificador del elemento a buscar
	 * @return el elemento si existe o null si no
	 */
        //para esta segunda entrega implementar este metodo (SOLO INSERTAR SIN ID Y BUSCAR POR ID) 
	public T buscarPorID(long id);

	/**
	 * Funcion que devuelva la coleccion de todos los elementos de un tipo
	 * 
	 * @return la coleccion de elementos que puede ser vacía
	 */
	public Collection<T> buscarTodos();
	
	public boolean modificar(T elemento);
	
//	public boolean modificarTodos(Collection<T> coleccion);
	
	public boolean eliminar(T elemento);

}
