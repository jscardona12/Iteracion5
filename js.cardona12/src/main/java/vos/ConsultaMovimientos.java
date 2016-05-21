package vos;


import java.sql.Date;

import org.codehaus.jackson.annotate.*;
/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class ConsultaMovimientos
{
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="idCarga")
	public int idCarga;
	
	@JsonProperty(value="idAgente")
	public int idAgente;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="origenBarco")
	public String origenBarco;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="FechaMovBarco")
	public String FMB;
	
	@JsonProperty(value="FechaMovArea")
	public String FMA;
	
	@JsonProperty(value="DestinoArea")
	public int DA;
	
	@JsonProperty(value="DestinoBarco")
	public int DB;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public ConsultaMovimientos(@JsonProperty(value="idAgente")int idAgente,@JsonProperty(value="idCarga")int idCarga,
			@JsonProperty(value="origenBarco")String origenBarco,
			@JsonProperty(value="FechaMovBarco")String FMB,@JsonProperty(value="FechaMovArea")String FMA,
	@JsonProperty(value="DestinoArea")int DA,@JsonProperty(value="DestinoBarco")int DB){
		this.idAgente = idAgente;
		this.idCarga = idCarga;
		this.origenBarco = origenBarco;
		this.FMB = FMB;
		this.FMA = FMA;
		this.DA = DA;
		this.DB = DB;
	}

	public int getIdCarga() {
		return idCarga;
	}

	public void setIdCarga(int idCarga) {
		this.idCarga = idCarga;
	}

	public String getOrigenBarco() {
		return origenBarco;
	}

	public void setOrigenBarco(String origenBarco) {
		this.origenBarco = origenBarco;
	}

	public String getFMB() {
		return FMB;
	}

	public void setFMB(String fMB) {
		FMB = fMB;
	}

	public String getFMA() {
		return FMA;
	}

	public void setFMA(String fMA) {
		FMA = fMA;
	}

	public int getDA() {
		return DA;
	}

	public void setDA(int dA) {
		DA = dA;
	}

	public int getDB() {
		return DB;
	}

	public void setDB(int dB) {
		DB = dB;
	}
	
	
}


