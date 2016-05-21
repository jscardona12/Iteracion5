package vos;


import java.sql.Date;

import org.codehaus.jackson.annotate.*;
/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class ConsultaAreas
{
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	@JsonProperty(value="idCarga")
	public int idCarga;
	
	@JsonProperty(value="idArea")
	public int idArea;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="tipoArea")
	public String tipo;
	
	@JsonProperty(value="FechaEntradaArea")
	public String FEA;
	
	@JsonProperty(value="FechaSalidaArea")
	public String FES;
	
	@JsonProperty(value="estado")
	public int estado;
	
	@JsonProperty(value="costo")
	public int costo;
	
	@JsonProperty(value="deshabilitado")
	public int deshabilitado;
	
	@JsonProperty(value="capacidad")
	public int capacidad;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public ConsultaAreas(@JsonProperty(value="idArea")int idArea,@JsonProperty(value="idCarga")int idCarga,
			@JsonProperty(value="FechaEntradaArea")String FEA,@JsonProperty(value="FechaSalidaArea")String FES,@JsonProperty(value="tipoArea")String tipo,@JsonProperty(value="estado")int estado,
			@JsonProperty(value="costo")int costo,@JsonProperty(value="deshabilitado")int deshabilitado,@JsonProperty(value="capacidad")int capacidad){
		this.idArea = idArea;
		this.idCarga = idCarga;
		this.costo = costo;
		this.FEA = FEA;
		this.FES = FES;
		this.tipo = tipo;
		this.estado = estado;
		this.deshabilitado = deshabilitado;
		this.capacidad = capacidad;
	}

	public String getFES() {
		return FES;
	}

	public void setFES(String fES) {
		FES = fES;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public int getIdCarga() {
		return idCarga;
	}

	public void setIdCarga(int idCarga) {
		this.idCarga = idCarga;
	}

	public int getIdArea() {
		return idArea;
	}

	public void setIdArea(int idArea) {
		this.idArea = idArea;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getFEA() {
		return FEA;
	}

	public void setFEA(String fEA) {
		FEA = fEA;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public int getCosto() {
		return costo;
	}

	public void setCosto(int costo) {
		this.costo = costo;
	}

	public int getDeshabilitado() {
		return deshabilitado;
	}

	public void setDeshabilitado(int deshabilitado) {
		this.deshabilitado = deshabilitado;
	}

		
	
}


