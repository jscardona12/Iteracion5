package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class OperadorPortuario
{
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="id_puerto")
	private int id_puerto;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="nombre")
	private String nombre;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public OperadorPortuario(@JsonProperty(value="id")int i,@JsonProperty(value="id_puerto")int id_puerto, @JsonProperty(value="nombre")String n)
	{
		setId(i);
		setNombre(n);
		this.id_puerto= id_puerto;
	}

	public int getId_puerto() {
		return id_puerto;
	}

	public void setId_puerto(int id_puerto) {
		this.id_puerto = id_puerto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}

