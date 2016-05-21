package vos;
import org.codehaus.jackson.annotate.*;

/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class CuartoFrio extends Bodega
{
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="area")
	private int area;

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
	 * @return the ancho
	 */
	public int getAncho() {
		return ancho;
	}

	/**
	 * @param ancho the ancho to set
	 */
	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	/**
	 * @return the largo
	 */
	public int getLargo() {
		return largo;
	}

	/**
	 * @param largo the largo to set
	 */
	public void setLargo(int largo) {
		this.largo = largo;
	}

	/**
	 * @return the alto
	 */
	public int getAlto() {
		return alto;
	}

	/**
	 * @param alto the alto to set
	 */
	public void setAlto(int alto) {
		this.alto = alto;
	}

	/**
	 * @return the porcentajeBodega
	 */
	public double getPorcentajeBodega() {
		return porcentajeBodega;
	}

	/**
	 * @param porcentajeBodega the porcentajeBodega to set
	 */
	public void setPorcentajeBodega(double porcentajeBodega) {
		this.porcentajeBodega = porcentajeBodega;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="ancho")
	private int ancho;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="largo")
	private int largo;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="alto")
	private int alto;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="porcentajeBodega")
	private double porcentajeBodega;

	public CuartoFrio(@JsonProperty(value="id") int id, @JsonProperty(value="area")int area, @JsonProperty(value="ancho")int ancho, @JsonProperty(value="largo")int largo, @JsonProperty(value="alto")int alto, @JsonProperty(value="porcentajeBodega")double porcentajeBodega) {
		super(id);
		this.area = area;
		this.ancho = ancho;
		this.largo = largo;
		this.alto = alto;
		this.porcentajeBodega = porcentajeBodega;
	}
	
	


}

