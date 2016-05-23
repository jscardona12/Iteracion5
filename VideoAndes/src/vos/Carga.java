/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: VideoAndes
 * Autor: Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package vos;

import org.codehaus.jackson.annotate.*;

/**
 * Clase que representa una Carga
 * 
 */
public class Carga {

	//// Atributos

	/**
	 * Id de la carga
	 */
	@JsonProperty(value="id")
	private int id;

	/**
	 * id del buque de la carga
	 */
	@JsonProperty(value="origen")
	private String origen;

	/**
	 * id exportador de la carga
	 */
	@JsonProperty(value="id_exportador")
	private int id_exportador;
	/**
	 * numero de la carga
	 */
	@JsonProperty(value="numero")
	private int numero;

	/**
	 * tipo de la carga
	 */
	@JsonProperty(value="tipo")
	private String tipo;

	/**
	 * volumen de la carga
	 */
	@JsonProperty(value="volumen")
	private double volumen;

	/**
	 * peso de la carga
	 */
	@JsonProperty(value="peso")
	private double peso;
	
	/**
	 * destino de la carga
	 */
	@JsonProperty(value="destino")
	private String destino;

	/**
	 * boolean que dice si es rodada la carga
	 */
	@JsonProperty(value="rodada")
	private boolean rodada;

	/**
	 * boolean que dice si es rodada la carga
	 */
	@JsonProperty(value="contenedor")
	private boolean contenedor;

	/**
	 * valor de la carga
	 */
	@JsonProperty(value="valor")
	private double valor;

	
	
	/**
	 * Método constructor de la clase carga
	 * <b>post: </b> Crea la carga con los valores que entran como parámetro
	 * @param id - Id de la carga.
	 * @param name - Nombre de la carga. name != null
	 * @param duration - Duración en minutos de la carga.
	 */
	public Carga(@JsonProperty(value="id")int id,
			@JsonProperty(value="origen")String origen,
			@JsonProperty(value="id_exportador") int id_exportador,
			@JsonProperty(value="numero")int numero,
			@JsonProperty(value="destino")String destino,
			@JsonProperty(value="tipo")String tipo,
			@JsonProperty(value="volumen")double volumen,
			@JsonProperty(value="peso")double peso,
			@JsonProperty(value="rodada")boolean rodada,
			@JsonProperty(value="contenedor")boolean contenedor,
			@JsonProperty(value="valor")double valor) {
		super();
		this.id = id;
		this.origen = origen;
		this.id_exportador = id_exportador;
		this.numero=numero;
		this.tipo=tipo;
		this.volumen=volumen;
		this.peso=peso;
		this.rodada=rodada;
		this.contenedor=contenedor;
		this.destino=destino;
		this.valor=valor;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	/**
	 * Método getter del atributo id_exportador
	 * @return id_exportador de la carga en minutos
	 */
	public int getExportador() {
		return id_exportador;
	}

	/**
	 * Método setter del atributo id_exportador <b>post: </b> id_exportador de la carga
	 * ha sido cambiado con el valor que entra como parámetro
	 * @param id_exportador - id_exportador de la carga.
	 */
	public void setExportador(int id_exportador) {
		this.id_exportador = id_exportador;
	}
	/**
	 * Método getter del atributo id_buque
	 * @return id_buque de la carga en minutos
	 */
	public String getOrigen() {
		return origen;
	}

	/**
	 * Método setter del atributo id_buque <b>post: </b> id_buque de la carga
	 * ha sido cambiado con el valor que entra como parámetro
	 * @param id_buque - id_exportador de la carga.
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	/**
	 * Método getter del atributo id
	 * @return id de la carga
	 */
	public int getID() {
		return id;
	}

	/**
	 * Método setter del atributo id <b>post: </b> El id de la carga ha sido
	 * cambiado con el valor que entra como parámetro
	 * @param id - Id de la carga
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * Método getter del atributo numero
	 * @return numero de la carga
	 */
	public int getNumero() {
		return numero;
	}

	/**
	 * Método setter del atributo numero <b>post: </b> El numero de la carga ha sido
	 * cambiado con el valor que entra como parámetro
	 * @param numero - numero de la carga
	 */
	public void setNumero(int numero) {
		this.numero = numero;
	}
	/**
	 * Método getter del atributo tipo
	 * @return tipo de la carga
	 */
	public String getTipoCarga() {
		return tipo;
	}

	/**
	 * Método setter del atributo tipo <b>post: </b> El tipo de la carga ha sido
	 * cambiado con el valor que entra como parámetro
	 * @param tipo - tipo de la carga
	 */
	public void setTipoCarga(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * Método getter del atributo volumen
	 * @return volumen de la carga
	 */
	public double getVolumen() {
		return volumen;
	}

	/**
	 * Método setter del atributo volumen <b>post: </b> El volumen de la carga ha sido
	 * cambiado con el valor que entra como parámetro
	 * @param volumen - volumen de la carga
	 */
	public void setVolumen(double volumen) {
		this.volumen = volumen;
	}

	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso=peso;
	}
	public boolean getRodada() {
		return rodada;
	}
	public void setRodada(boolean rodada) {
		this.rodada=rodada;
	}

	public boolean getContenedor() {
		return contenedor;
	}
	public void setContenedor(boolean contenedor) {
		this.contenedor=contenedor;
	}
	
	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino=destino;
	}


}
