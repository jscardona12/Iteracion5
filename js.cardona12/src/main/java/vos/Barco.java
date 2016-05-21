package vos;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class Barco
{
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="nombre")
	public String nombre;

	/**
	 * Da el pto de origen
	 */
	@JsonProperty(value="puertoOrigen")
	public String puertoOrigen;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="puertoDestino")
	public String puertoDestino;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="id")
	public int id;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="capacidad")
	public int capacidad;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="nombreAgente")
	public String nombreAgente;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="registroCapitania")
	public String registroCapitania;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="tipoBarco")
	public String tipoBarco;




	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="carga")
	public int carga;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="reserva")
	public int reserva;

	@JsonProperty(value="deshabilitado")
	public int deshabilitado;


	public Barco(@JsonProperty(value="nombre")String nombre, @JsonProperty(value="puertoOrigen")String puertoOrigen, @JsonProperty(value="puertoDestino")String puertoDestino,@JsonProperty(value="id") int id, @JsonProperty(value="capacidad")int capacidad,@JsonProperty(value="nombreAgente") String nombreAgente,
			@JsonProperty(value="registroCapitania")String registroCapitania, @JsonProperty(value="tipoBarco")String tipoBarco, @JsonProperty(value="carga")int carga, @JsonProperty(value="reserva")int reserva, @JsonProperty(value="deshabilitado") int deshabilitado) 
	{
		super();
		this.nombre = nombre;
		this.puertoOrigen = puertoOrigen;
		this.puertoDestino = puertoDestino;
		this.id = id;
		this.capacidad = capacidad;
		this.nombreAgente = nombreAgente;
		this.registroCapitania = registroCapitania;
		this.tipoBarco = tipoBarco;
		this.deshabilitado = deshabilitado;

		this.carga = carga;
		this.reserva = reserva;

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPuertoOrigen() {
		return puertoOrigen;
	}

	public void setPuertoOrigen(String puertoOrigen) {
		this.puertoOrigen = puertoOrigen;
	}

	/**
	 * @return the carga
	 */
	public int getCarga() {
		return carga;
	}

	/**
	 * @param carga the carga to set
	 */
	public void setCarga(int carga) {
		this.carga = carga;
	}

	/**
	 * @return the reserva
	 */
	public int getReserva() {
		return reserva;
	}

	/**
	 * @param reserva the reserva to set
	 */
	public void setReserva(int reserva) {
		this.reserva = reserva;
	}

	public String getPuertoDestino() {
		return puertoDestino;
	}

	public void setPuertoDestino(String puertoDestino) {
		this.puertoDestino = puertoDestino;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public String getNombreAgente() {
		return nombreAgente;
	}

	public void setNombreAgente(String nombreAgente) {
		this.nombreAgente = nombreAgente;
	}

	public String getRegistroCapitania() {
		return registroCapitania;
	}

	public void setRegistroCapitania(String registroCapitania) {
		this.registroCapitania = registroCapitania;
	}

	public String getTipoBarco() {
		return tipoBarco;
	}

	public void setTipoBarco(String tipoBarco) {
		this.tipoBarco = tipoBarco;
	}

	/**
	 * @return the deshabilitado
	 */
	public int getDeshabilitado() {
		return deshabilitado;
	}

	/**
	 * @param deshabilitado the deshabilitado to set
	 */
	public void setDeshabilitado(int deshabilitado) {
		this.deshabilitado = deshabilitado;
	}

	
	
	
}

