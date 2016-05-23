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
public class Patio extends Almacenamiento{

	//// Atributos

	/**
	 * Largo del silo en metros
	 */
	@JsonProperty(value="largo")
	private int largo;
	
	/**
   * Largo del silo en metros
   */
  @JsonProperty(value="ancho")
  private int ancho;

	/**
	 * Método constructor de la clase silo
	 * <b>post: </b> Crea la bodega con los valores que entran como parámetro
	 * @param id - Id del silo.
	 * @param dimension - dimension del silo
	 * @param nombre - nombre del silo
	 */
	public Patio(int id, @JsonProperty(value="largo")int largo, @JsonProperty(value="ancho")int ancho) {
		super(id);
		this.largo = largo;
		this.ancho = ancho;
	}

	public Patio(String estado, String tipo) {
		super(0,tipo,estado);
	}

	/**
	 * Método getter del atributo dimension
	 * @return dimension del silo
	 */
	public int getLargo() {
		return largo;
	}

	/**
	 * Método setter del atributo dimension <b>post: </b> La dimension del silo ha sido
	 * cambiado con el valor que entra como parámetro
	 * @param dimension - dimension del silo
	 */
	public void setLargo(int dimension) {
		this.largo = dimension;
	}
	
	/**
   * Método getter del atributo dimension
   * @return dimension del silo
   */
  public int getAncho() {
    return ancho;
  }

  /**
   * Método setter del atributo dimension <b>post: </b> La dimension del silo ha sido
   * cambiado con el valor que entra como parámetro
   * @param dimension - dimension del silo
   */
  public void setAncho(int ancho) {
    this.ancho = ancho;
  }
}
