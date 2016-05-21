package vos;

import org.codehaus.jackson.annotate.*;
/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class AreaDeAlmacenamiento
{
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="id")
	public int id;
	
	@JsonProperty(value="costo")
	private int costo;
	
	@JsonProperty(value="estado")
	private int estado;
	
	@JsonProperty(value="deshabilitado")
	private int deshabilitado;
	
	@JsonProperty(value="tipo")
	private String tipo;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public AreaDeAlmacenamiento(@JsonProperty(value="id")int id, @JsonProperty(value="costo")int costo, @JsonProperty(value="estado")int estado, @JsonProperty(value="tipo")String tipo, @JsonProperty(value="deshabilitado")int deshabilitado){
		super();
		this.id =id;
		this.costo = costo;
		this.estado = estado;
		this.tipo = tipo;
		this.deshabilitado = deshabilitado;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the costo
	 */
	public int getCosto() {
		return costo;
	}

	/**
	 * @param costo the costo to set
	 */
	public void setCosto(int costo) {
		this.costo = costo;
	}

	/**
	 * @return the estado
	 */
	public int getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(int estado) {
		this.estado = estado;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
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

