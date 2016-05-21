package vos;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.HashSet;


/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class Muelle
{
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="id")
	private int id;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="estaReservado")
	private boolean estaReservado;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="estaOcupado")
	private boolean estaOcupado;

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
	public Muelle(@JsonProperty(value="id")int i, @JsonProperty(value="nombre")String n, @JsonProperty(value="estaReservado")boolean er, @JsonProperty(value="estaOcupado")boolean eo)
	{
		id = i;
		nombre = n;
		estaReservado = er;
		estaOcupado = eo;
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getNombre()
	{
		return nombre;
	}
	
	public boolean getEstaReservado()
	{
		return estaReservado;
	}
	
	public boolean getEstaOcupado()
	{
		return estaOcupado;
	}
	

}

