package vos;

import org.codehaus.jackson.annotate.*;
/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class Bodega extends AreaDeAlmacenamiento
{
	/**
	 * 
	 */
	@JsonProperty(value="area")
	public int area;

		/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="plataformaExterna")
	public boolean plataformaExterna;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="separacionCol")
	public int separacionCol;
	
	@JsonProperty(value="costo")
	public static int costo;
	
	@JsonProperty(value="estado")
	private static int estado;
	
	@JsonProperty(value="deshabilitado")
	private static int deshabilitado;
	
	@JsonProperty(value="tipo")
	private static String tipo;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="cuartosFrios")
	public boolean cuartosFrios;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	@JsonProperty(value="id")
	public int id;

	public Bodega(@JsonProperty(value="area") int area,@JsonProperty(value="plataformaExterna") boolean plataformaExterna,@JsonProperty(value="separacionCol") int separacionCol,
			@JsonProperty(value="cuartosFrios")boolean cuartosFrios,@JsonProperty(value="id") int id) {
		super(id,costo, estado, tipo, deshabilitado);
		this.area = area;
		this.plataformaExterna = plataformaExterna;
		this.separacionCol = separacionCol;
		this.cuartosFrios = cuartosFrios;
		this.id = id;
	}
	public Bodega(@JsonProperty(value="id")int id)
	{	super(id,costo, estado, tipo, deshabilitado);
		this.id =id;
	}


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
	 * @return the costo
	 */
	public int getCosto() {
		return costo;
	}
	/**
	 * @param costo the costo to set
	 */
	public void setCosto(int costo) {
		Bodega.costo = costo;
	}
	/**
	 * @return the estado
	 */
	public int getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(int estado) {
		Bodega.estado = estado;
	}
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		Bodega.tipo = tipo;
	}
	
	public boolean isPlataformaExterna() {
		return plataformaExterna;
	}

	public void setPlataformaExterna(boolean plataformaExterna) {
		this.plataformaExterna = plataformaExterna;
	}

	public int getSeparacionCol() {
		return separacionCol;
	}

	public void setSeparacionCol(int separacionCol) {
		this.separacionCol = separacionCol;
	}

	public boolean isCuartosFrios() {
		return cuartosFrios;
	}

	public void setCuartosFrios(boolean cuartosFrios) {
		this.cuartosFrios = cuartosFrios;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	

}

