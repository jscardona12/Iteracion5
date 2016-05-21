package vos;
import org.codehaus.jackson.annotate.*;

/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class Cobertizo extends AreaDeAlmacenamiento
{
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="area")
	public int area;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="tipoCarga")
	public String tipoCarga;
	
	@JsonProperty(value="costo")
	public static int costo;
	
	@JsonProperty(value="estado")
	private static int estado;
	
	@JsonProperty(value="tipo")
	private static String tipo;
	
	@JsonProperty(value="deshabilitado")
	private static int deshabilitado;

	public Cobertizo(@JsonProperty(value="id")int id, @JsonProperty(value="id")int area, @JsonProperty(value="id")String tipoCarga) {
		super(id,costo,estado,tipo, deshabilitado);
		this.area = area;
		this.tipoCarga = tipoCarga;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public String getTipoCarga() {
		return tipoCarga;
	}

	public void setTipoCarga(String tipoCarga) {
		this.tipoCarga = tipoCarga;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	

}

