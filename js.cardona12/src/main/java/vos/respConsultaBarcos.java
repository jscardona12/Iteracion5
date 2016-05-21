package vos;


import java.sql.Date;

import org.codehaus.jackson.annotate.*;
/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class respConsultaBarcos
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
	

	

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public respConsultaBarcos(@JsonProperty(value="horaSalida")String horaSalida,@JsonProperty(value="fechaEntrada")String FE
			,@JsonProperty(value="nombreBarco")String nombreBarco,
			@JsonProperty(value="tipoBarco")String tipoBarco,  @JsonProperty(value="idBarco")int idBarco)
	{
		this.horaSalida = horaSalida;
		this.nombreBarco = nombreBarco;
		this.tipoBarco = tipoBarco;
		this.idBarco = idBarco;
		this.FE = FE;
	}
	

	
	public String getFE() {
		return FE;
	}


	public void setFE(String fE) {
		FE = fE;
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


