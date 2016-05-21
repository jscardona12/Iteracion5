
package vos;
import java.util.Date;
import org.codehaus.jackson.annotate.*;

/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class Salida
{
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="fechaSalida")
	public Date fechaSalida;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="id")
	public float id;
	
	@JsonProperty(value="id_barco")
	public float id_barco;
	@JsonProperty(value="nombre_barco")
	public String nombre_barco;
	public Salida(@JsonProperty(value="fechaSalida")Date fechaSalida,@JsonProperty(value="id") float id, 
			@JsonProperty(value="id_barco")float id_barco, 
			@JsonProperty(value="nombre_barco")String nombre_barco) {
		super();
		this.fechaSalida = fechaSalida;
		this.id = id;
		this.id_barco = id_barco;
		this.nombre_barco = nombre_barco;
	}
	public Date getFechaSalida() {
		return fechaSalida;
	}
	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}
	public float getId() {
		return id;
	}
	public void setId(float id) {
		this.id = id;
	}
	public float getId_barco() {
		return id_barco;
	}
	public void setId_barco(float id_barco) {
		this.id_barco = id_barco;
	}
	public String getNombre_barco() {
		return nombre_barco;
	}
	public void setNombre_barco(String nombre_barco) {
		this.nombre_barco = nombre_barco;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	

}

