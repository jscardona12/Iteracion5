package vos;


import java.sql.Date;

import org.codehaus.jackson.annotate.*;
/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class ConsultaBarcos
{
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="horaSalida")
	public String horaSalida;
	
	@JsonProperty(value="fechaEntrada")
	public String FE;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="fechaInicial")
	public String fechaInicial;

	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="fechaFinal")
	public String fechaFinal;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="idBarco")
	public int idBarco;
	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="nombreBarco")
	public String nombreBarco;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="tipoBarco")
	public String tipoBarco;
	
	@JsonProperty(value="tipoCarga")
	public String tipoCarga;
	
	@JsonProperty(value="orden")
	public String orden;
	 
	
	

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public ConsultaBarcos(@JsonProperty(value="fechaInicial")String fechaInicial,
			@JsonProperty(value="fechaFinal")String fechaFinal,
			@JsonProperty(value="nombreBarco")String nombreBarco,
			@JsonProperty(value="tipoBarco")String tipoBarco,
			@JsonProperty(value="tipoCarga")String tipoCarga,
			@JsonProperty(value="orden")String orden){
		this.fechaInicial = fechaInicial;
		this.fechaFinal = fechaFinal;
		this.nombreBarco = nombreBarco;
		this.tipoBarco = tipoBarco;
		this.tipoCarga = tipoCarga;
		this.orden = orden;
	}


	public String getFE() {
		return FE;
	}


	public void setFE(String fE) {
		FE = fE;
	}


	public String getOrden() {
		return orden;
	}


	public void setOrden(String orden) {
		this.orden = orden;
	}


	/**
	 * @return the horaSalida
	 */
	public String getHoraSalida() {
		return horaSalida;
	}

	/**
	 * @param horaSalida the horaSalida to set
	 */
	public void setHoraSalida(String horaSalida) {
		this.horaSalida = horaSalida;
	}

	/**
	 * @return the nombreBarco
	 */
	public String getNombreBarco() {
		return nombreBarco;
	}

	/**
	 * @param nombreBarco the nombreBarco to set
	 */
	public void setNombreBarco(String nombreBarco) {
		this.nombreBarco = nombreBarco;
	}

	/**
	 * @return the tipoBarco
	 */
	public String getTipoBarco() {
		return tipoBarco;
	}

	/**
	 * @param tipoBarco the tipoBarco to set
	 */
	public void setTipoBarco(String tipoBarco) {
		this.tipoBarco = tipoBarco;
	}

	/**
	 * @return the tipoCarga
	 */
	public String getTipoCarga() {
		return tipoCarga;
	}

	/**
	 * @param tipoCarga the tipoCarga to set
	 */
	public void setTipoCarga(String tipoCarga) {
		this.tipoCarga = tipoCarga;
	}


	/**
	 * @return the fechaInicial
	 */
	public String getFechaInicial() {
		return fechaInicial;
	}


	/**
	 * @param fechaInicial the fechaInicial to set
	 */
	public void setFechaInicial(String fechaInicial) {
		this.fechaInicial = fechaInicial;
	}


	/**
	 * @return the fechaFinal
	 */
	public String getFechaFinal() {
		return fechaFinal;
	}


	/**
	 * @param fechaFinal the fechaFinal to set
	 */
	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}


	/**
	 * @return the idBarco
	 */
	public int getIdBarco() {
		return idBarco;
	}


	/**
	 * @param idBarco the idBarco to set
	 */
	public void setIdBarco(int idBarco) {
		this.idBarco = idBarco;
	}

	
	
	
	
}


