package vos;
import java.util.Set;
import java.util.HashSet;
import org.codehaus.jackson.annotate.*;

/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class CostoFacturado
{
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="costo")
	public double costo;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="medioTransporte")
	public String medioTransporte;

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
	@JsonProperty(value="Almacenamientos")
	public AreaDeAlmacenamiento Almacenamientos;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="equipo")
	public Equipo equipo;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="carga")
	public Carga carga;

	public CostoFacturado(@JsonProperty(value="costo")double costo, @JsonProperty(value="medioTransporte")String medioTransporte, 
			@JsonProperty(value="id")float id,@JsonProperty(value="Almacenamientos") AreaDeAlmacenamiento almacenamientos,
			@JsonProperty(value="equipo")Equipo equipo, @JsonProperty(value="carga")Carga carga) {
		super();
		this.costo = costo;
		this.medioTransporte = medioTransporte;
		this.id = id;
		Almacenamientos = almacenamientos;
		this.equipo = equipo;
		this.carga = carga;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public String getMedioTransporte() {
		return medioTransporte;
	}

	public void setMedioTransporte(String medioTransporte) {
		this.medioTransporte = medioTransporte;
	}

	public float getId() {
		return id;
	}

	public void setId(float id) {
		this.id = id;
	}

	public AreaDeAlmacenamiento getAlmacenamientos() {
		return Almacenamientos;
	}

	public void setAlmacenamientos(AreaDeAlmacenamiento almacenamientos) {
		Almacenamientos = almacenamientos;
	}

	public Equipo getEquipo() {
		return equipo;
	}

	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}

	public Carga getCarga() {
		return carga;
	}

	public void setCarga(Carga carga) {
		this.carga = carga;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	

}

