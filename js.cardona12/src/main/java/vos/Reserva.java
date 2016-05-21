package vos;
import java.util.Date;
import org.codehaus.jackson.annotate.*;

/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class Reserva
{
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
	@JsonProperty(value="id_muelle")
	public float id_muelle;
	@JsonProperty(value="fechaInicial")
	public Date fechaInicial;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public Reserva(@JsonProperty(value="fechaInicial")Date fechaInicial,@JsonProperty(value="id") float id, 
			@JsonProperty(value="id_barco")float id_barco, 
			@JsonProperty(value="id_muelle")float id_muelle) {
		super();
		this.fechaInicial = fechaInicial;
		this.id = id;
		this.id_barco = id_barco;
		this.id_muelle = id_muelle;
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

	public float getId_muelle() {
		return id_muelle;
	}

	public void setId_muelle(float id_muelle) {
		this.id_muelle = id_muelle;
	}

	public Date getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}
}

