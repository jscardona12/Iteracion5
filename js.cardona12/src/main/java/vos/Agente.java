package vos;


import org.codehaus.jackson.annotate.*;
/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class Agente
{
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="id")
	public float id;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="tipo")
	public String tipo;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="costoFacturado")
	public CostoFacturado costoFacturado;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public Agente(@JsonProperty(value="id")int id,@JsonProperty(value="tipo")String tipo){
		super();
		this.id = id;
		this.tipo = tipo;
				
	}
	
	public float getId()
	{
		return this.id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public CostoFacturado getCostoFacturado() {
		return costoFacturado;
	}

	public void setCostoFacturado(CostoFacturado costoFacturado) {
		this.costoFacturado = costoFacturado;
	}

	public void setId(float id) {
		this.id = id;
	}

}

