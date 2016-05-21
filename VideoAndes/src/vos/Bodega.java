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
public class Bodega extends Almacenamiento{

	//// Atributos

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
	@JsonProperty(value="tienePlataforma")
	private boolean tienePlataforma;

	/**
	 * Largo de la bodega en metros
	 */
	@JsonProperty(value="separacionColumnas")
	private int separacionColumnas;

	/**
	 * Método constructor de la clase bodega
	 * <b>post: </b> Crea la bodega con los valores que entran como parámetro
	 * @param id - Id de la bodega.
	 * @param largo - Largo de la bodega
	 * @param ancho - ancho de la bodega
	 */
	public Bodega(int id, @JsonProperty(value="largo")int largo,
			@JsonProperty(value="ancho") int ancho, @JsonProperty(value="tienePlataforma")boolean tienePlataforma,
				@JsonProperty(value="separacionColumnas") int separacionColumnas) {
		super(id);
		this.largo = largo;
		this.ancho = ancho;
		this.tienePlataforma=tienePlataforma;
		this.separacionColumnas=separacionColumnas;
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
	 * Método getter del atributo tienePlataforma
	 * @return true si la bodega tiene plataforma, false de lo contrario
	 */
	public boolean getTienePlataforma() {
		return tienePlataforma;
	}

	/**
	 * Método setter del atributo tienePlataforma <b>post: </b> El tienePlataforma de la bodega ha sido
	 * cambiado con el valor que entra como parámetro
	 * @param tienePlataforma - true si la bodega tiene plataforma, false de lo contrario.
	 */
	public void setTienePlataforma(boolean tienePlataforma) {
		this.tienePlataforma = tienePlataforma;
	}

	
	/**
	 * Método getter del atributo separacionColumnas
	 * @return separacionColumnas de la bodega
	 */
	public int getSeparacionColumnas() {
		return separacionColumnas;
	}

	/**
	 * Método setter del atributo separacionColumnas <b>post: </b> El separacionColumnas de la bodega ha sido
	 * cambiado con el valor que entra como parámetro
	 * @param separacionColumnas - separacionColumnas de la bodega
	 */
	public void setSeparacionColumnas(int separacionColumnas) {
		this.separacionColumnas = separacionColumnas;
	}

}
