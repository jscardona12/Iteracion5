package vos;
import org.codehaus.jackson.annotate.*;

/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class Silo extends AreaDeAlmacenamiento
{
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="nombre")
	public String nombre;
	
	@JsonProperty(value="costo")
	public static int costo;
	
	@JsonProperty(value="estado")
	private static int estado;
	
	@JsonProperty(value="tipo")
	private static String tipo;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="capacidad")
	public int capacidad;
	
	@JsonProperty(value="deshabilitado")
	private static int deshabilitado;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public Silo(@JsonProperty(value="id") int id, @JsonProperty(value="nombre") String nombre, @JsonProperty(value="capacidad") int capacidad)
	{
		super(id,costo,estado, tipo, deshabilitado);
		this.capacidad = capacidad;
		this.nombre = nombre;
		
	}

}

