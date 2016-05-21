package vos;
import java.util.Set;
import java.util.HashSet;
import java.util.Date;
import org.codehaus.jackson.annotate.*;

/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class Carga
{
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="tipo")
	public String tipo;
	
	@JsonProperty(value="agente")
	public int agente;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="volumen")
	public double volumen;

	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="idCarga")
	public int id;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="costo")
	private int costo;
	
	@JsonProperty(value="equipo")
	private int equipo;
	
	@JsonProperty(value="peso")
	private int peso;
	
	@JsonProperty(value="area")
	private int area;

		public Carga(@JsonProperty(value="tipo")String tipo, @JsonProperty(value="volumen")double volumen, 
			@JsonProperty(value="idCarga")int id, @JsonProperty(value="costo")int costo, @JsonProperty(value="equipo") int equipo,
			@JsonProperty(value="peso")int peso, @JsonProperty(value="area") int area,@JsonProperty(value="agente")int agente) 
		{
		super();
		this.tipo = tipo;
		this.volumen = volumen;
		this.costo = costo;
		this.id = id;
		this.equipo = equipo;
		this.peso = peso;
		this.area = area;
		this.agente = agente;
		
	}

	public int getArea() {
			return area;
		}

		public void setArea(int area) {
			this.area = area;
		}

	public int getPeso() {
			return peso;
		}

		public void setPeso(int peso) {
			this.peso = peso;
		}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public double getVolumen() {
		return volumen;
	}

	public void setVolumen(double volumen) {
		this.volumen = volumen;
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
	 * @return the equipo
	 */
	public int getEquipo() {
		return equipo;
	}

	/**
	 * @param equipo the equipo to set
	 */
	public void setEquipo(int equipo) {
		this.equipo = equipo;
	}

	/**
	 * @return the agente
	 */
	public int getAgente() {
		return agente;
	}

	/**
	 * @param agente the agente to set
	 */
	public void setAgente(int agente) {
		this.agente = agente;
	}

	
	

	
}

