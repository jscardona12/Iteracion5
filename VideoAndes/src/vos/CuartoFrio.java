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
public class CuartoFrio {

	//// Atributos

	/**
	 * Id de la bodega
	 */
	@JsonProperty(value="id")
	private int id;

	/**
	 * Ancho de la bodega en metros
	 */
	@JsonProperty(value="ancho")
	private int ancho;

	/**
	 * Largo de la bodega en metros
	 */
	@JsonProperty(value="largo")
	private int largo;
	/**
	 * Ancho de la bodega en metros
	 */
	@JsonProperty(value="alto")
	private boolean alto;

	/**
	 * Largo de la bodega en metros
	 */
	@JsonProperty(value="porcentajeUtilizado")
	private int porcentajeUtilizado;

	/**
	 * Método constructor de la clase bodega
	 * <b>post: </b> Crea la bodega con los valores que entran como parámetro
	 * @param id - Id de la bodega.
	 * @param largo - Largo de la bodega
	 * @param ancho - ancho de la bodega
	 */
	public CuartoFrio(@JsonProperty(value="id")int id, @JsonProperty(value="largo")int largo,
			@JsonProperty(value="ancho") int ancho, @JsonProperty(value="alto")boolean alto,
				@JsonProperty(value="porcentajeUtilizado") int porcentajeUtilizado) {
		super();
		this.id = id;
		this.largo = largo;
		this.ancho = ancho;
		this.alto=alto;
		this.porcentajeUtilizado=porcentajeUtilizado;
	}
	
	/**
	 * Método getter del atributo id
	 * @return id de la bodega
	 */
	public int getID() {
		return id;
	}

	/**
	 * Método setter de la atributo id <b>post: </b> el id de la bodega
	 * ha sido cambiado con el valor que entra como parámetro
	 * @param id - nuevo id de la bodega.
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * Método getter del atributo largo
	 * @return largo de la bodega
	 */
	public int getLargo() {
		return largo;
	}

	/**
	 * Método setter del atributo largo <b>post: </b> El largo de la bodega ha sido
	 * cambiado con el valor que entra como parámetro
	 * @param largo - largo de la bodega
	 */
	public void setLargo(int largo) {
		this.largo = largo;
	}
	
	/**
	 * Método getter del atributo ancho
	 * @return ancho de la bodega
	 */
	public int getAncho() {
		return ancho;
	}

	/**
	 * Método setter del atributo ancho <b>post: </b> El ancho de la bodega ha sido
	 * cambiado con el valor que entra como parámetro
	 * @param ancho - ancho de la bodega
	 */
	public void setAncho(int ancho) {
		this.ancho = ancho;
	}
	
	/**
	 * Método getter del atributo alto
	 * @return true si la bodega tiene plataforma, false de lo contrario
	 */
	public boolean getAlto() {
		return alto;
	}

	/**
	 * Método setter del atributo alto <b>post: </b> El alto de la bodega ha sido
	 * cambiado con el valor que entra como parámetro
	 * @param alto - true si la bodega tiene plataforma, false de lo contrario.
	 */
	public void setAlto(boolean alto) {
		this.alto = alto;
	}

	
	/**
	 * Método getter del atributo porcentajeUtilizado
	 * @return porcentajeUtilizado de la bodega
	 */
	public int getPorcentajeUtilizado() {
		return porcentajeUtilizado;
	}

	/**
	 * Método setter del atributo porcentajeUtilizado <b>post: </b> El porcentajeUtilizado de la bodega ha sido
	 * cambiado con el valor que entra como parámetro
	 * @param porcentajeUtilizado - porcentajeUtilizado de la bodega
	 */
	public void setPorcentajeUtilizado(int porcentajeUtilizado) {
		this.porcentajeUtilizado = porcentajeUtilizado;
	}

}
