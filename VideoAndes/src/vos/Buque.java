/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: buqueAndes
 * Autor: Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package vos;

import org.codehaus.jackson.annotate.*;

/**
 * Clase que representa una Carga
 * 
 */
public class Buque {

	public enum Deshabilitacion{
		POLITICA,AVERIA,MANTENIMIENTO;
	}
	public static final String RORO="RORO";
	public static final String PORTA_CONTENEDOR="PORTA_CONTENEDOR";
	public static final String MULTI_PROPOSITO="MULTI_PROPOSITO";
	//// Atributos
	public static final String PROCESO_CARGA = "Proceso de carga";
	public static final String CARGADO = "Cargado";
	public static final String PROCESO_DESCARGUE = "Proceso de descargue";
	public static final String DESCARGADO = "Descargado";

	/**
	 * Id del buque
	 */
	@JsonProperty(value="id")
	private int id;

	/**
	 * agenteMaritimo del buque
	 */
	@JsonProperty(value="agenteMaritimo")
	private String agenteMaritimo;

	/**
	 * registroCapitania del buque
	 */
	@JsonProperty(value="registroCapitania")
	private String registroCapitania;
	
	/**
	 * Nombre del buque
	 */
	@JsonProperty(value="nombre")
	private String nombre;
	/**
	 * Unidad del buque
	 */
	@JsonProperty(value="unidad")
	private String unidad;
	/**
	 * Tipo del buque
	 */
	@JsonProperty(value="tipo")
	private String tipo;
	/**
	 * Capacidad del buque
	 */
	@JsonProperty(value="capacidad")
	private double capacidad;
	/**
	 * Destino del buque
	 */
	@JsonProperty(value="destino")
	private String destino;

	/**
	 * Método constructor de la clase buque
	 * <b>post: </b> Crea el buque con los valores que entran como parámetro
	 * @param id2 - Id del buque.
	 * @param registroCapitania - registroCapitania del buque
	 * @param agenteMaritimo - agenteMaritimo del buque
	 */
	public Buque(@JsonProperty(value="id")int id2, @JsonProperty(value="registroCapitania")String registroCapitania,@JsonProperty(value="agenteMaritimo") String agenteMaritimo
			,@JsonProperty(value="nombre") String nombre,@JsonProperty(value="unidad") String unidad,
			@JsonProperty(value="tipo") String tipo, @JsonProperty(value="capacidad") double capacidad
			,@JsonProperty(value="destino") String destino) {
		super();
		this.id = id2;
		this.registroCapitania = registroCapitania;
		this.agenteMaritimo = agenteMaritimo;
		this.nombre = nombre;
		this.unidad = unidad;
		this.tipo=tipo;
		this.capacidad=capacidad;
		this.destino=destino;
	}
	
	/**
	 * Método getter del atributo id
	 * @return id del buque
	 */
	public int getID() {
		return id;
	}

	/**
	 * Método setter de la atributo id <b>post: </b> el id del buque
	 * ha sido cambiado con el valor que entra como parámetro
	 * @param id - nuevo id del buque.
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * Método getter del atributo registroCapitania
	 * @return registroCapitania del buque
	 */
	public String getRegistroCapitania() {
		return registroCapitania;
	}

	/**
	 * Método setter del atributo registroCapitania <b>post: </b> El registroCapitania del buque ha sido
	 * cambiado con el valor que entra como parámetro
	 * @param registroCapitania - registroCapitania del buque
	 */
	public void setRegistroCapitania(String registroCapitania) {
		this.registroCapitania = registroCapitania;
	}
	
	/**
	 * Método getter del atributo agenteMaritimo
	 * @return agenteMaritimo del buque
	 */
	public String getAgenteMaritimo() {
		return agenteMaritimo;
	}

	/**
	 * Método setter del atributo agenteMaritimo <b>post: </b> El agenteMaritimo del buque ha sido
	 * cambiado con el valor que entra como parámetro
	 * @param agenteMaritimo - agenteMaritimo del buque
	 */
	public void setAgenteMaritimo(String agenteMaritimo) {
		this.agenteMaritimo = agenteMaritimo;
	}
	
	/**
	 * Método getter del atributo agenteMaritimo
	 * @return agenteMaritimo del buque
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Método setter del atributo nombre <b>post: </b> El nombre del buque ha sido
	 * cambiado con el valor que entra como parámetro
	 * @param nombre - nombre del buque
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * Método getter del atributo unidad
	 * @return unidad del buque
	 */
	public String getUnidad() {
		return unidad;
	}

	/**
	 * Método setter del atributo unidad <b>post: </b> La unidad del buque ha sido
	 * cambiado con el valor que entra como parámetro
	 * @param unidad - unidad del buque
	 */
	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}
	/**
	 * Método getter del atributo tipo
	 * @return tipo del buque
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Método setter de la atributo id <b>post: </b> el id del buque
	 * ha sido cambiado con el valor que entra como parámetro
	 * @param id - nuevo id del buque.
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * Metodo getter de capacidad
	 * @return capacidad
	 */
	public double getCapacidad() {
		return capacidad;
	}
	/**
	 * Metodo setter de capacidad
	 * @param capacidad - la nueva capacidad del buque
	 */
	public void setCapacidad(double capacidad) {
		this.capacidad=capacidad;
	}
	/**
	 * Metodo getter de destino
	 * @param capacidad - la nueva capacidad del buque
	 */
	public String getDestino() {
		return destino;
	}
	/**
	 * Metodo setter de destino
	 * @param capacidad - la nueva capacidad del buque
	 */
	public void setCapacidad(String destino) {
		this.destino=destino;
	}


}
