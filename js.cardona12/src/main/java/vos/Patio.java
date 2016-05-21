package vos;
import org.codehaus.jackson.annotate.*;

/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class Patio extends AreaDeAlmacenamiento
{
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="area")
	public int area;
	
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
	@JsonProperty(value="tipoCarga")
	public String tipoCarga;
	
	/**
	 * @return the area
	 */
	public int getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(int area) {
		this.area = area;
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
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="deshabilitado")
	private static int deshabilitado;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public Patio(@JsonProperty(value="area")int area, @JsonProperty(value="tipoCarga")String tipoCarga, @JsonProperty(value="id") int id){
		super(id,costo,estado,tipo, deshabilitado);
		this.area = area;
		this.tipoCarga = tipoCarga;
		this.id = id;
	}

}

