/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: bodegaAndes
 * Autor: Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package vos;

import org.codehaus.jackson.annotate.*;

/**
 * Clase que representa una Carga
 * 
 */
public class Silo extends Almacenamiento{

	//// Atributos

	/**
	 * Ancho del silo en metros
	 */
	@JsonProperty(value="nombre")
	private String nombre;

	/**
	 * Largo del silo en metros
	 */
	@JsonProperty(value="capacidad")
	private int capacidad;

	/**
	 * Método constructor de la clase silo
	 * <b>post: </b> Crea la bodega con los valores que entran como parámetro
	 * @param id - Id del silo.
	 * @param capacidad - capacidad del silo
	 * @param nombre - nombre del silo
	 */
	public Silo(int id, @JsonProperty(value="capacidad")int capacidad,@JsonProperty(value="nombre") String nombre) {
		super(id);
		this.capacidad = capacidad;
		this.nombre = nombre;
	}

	/**
	 * Método getter del atributo capacidad
	 * @return capacidad del silo
	 */
	public int getCapacidad() {
		return capacidad;
	}

	/**
	 * Método setter del atributo capacidad <b>post: </b> La capacidad del silo ha sido
	 * cambiado con el valor que entra como parámetro
	 * @param capacidad - capacidad del silo
	 */
	public void setLargo(int capacidad) {
		this.capacidad = capacidad;
	}
	
	/**
	 * Método getter del atributo nombre
	 * @return ancho del silo
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Método setter del atributo nombre <b>post: </b> El nombre del silo ha sido
	 * cambiado con el valor que entra como parámetro
	 * @param nombre - nombre del silo
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


}
