package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class CargaEnAlmacen {

  
  public static final String DESCARGA = "DESCARGA";
  public static final String CARGA = "CARGA";
  
	//--------------------------
	//ATRIBUTOS
	//--------------------------

	/**
	 * Atributo entero que modela el buque del arribo
	 */
	@JsonProperty(value="idCarga")
	private int idCarga;
	/**
	 * Atributo entero que modela el buque del arribo
	 */
	@JsonProperty(value="idAlmacen")
	private int idAlmacen;

	//--------------------------
	//CONSTRUCTOR
	//--------------------------
	/**
	 * Constructor de la clase abstracta
	 * @param nombre nombre de la persona
	 * @param id id de la persona
	 * @param esNatural boolean diciendo si es persona natural o no
	 */
	public CargaEnAlmacen(@JsonProperty(value="idCarga") int idCarga,
	                       @JsonProperty(value="idAlmacen") int idAlmacen){
		super();
		this.idCarga=idCarga;
		this.idAlmacen=idAlmacen;
	}
	//--------------------------
	//METODOS
	//--------------------------


	/**
	 * Metodo para obtener el id del buque
	 * @return id_buque
	 */
	public int getIdCarga(){
		return idCarga;
	}
	/**
	 * Metodo para modificar el nombre
	 * @param nombre 
	 */
	public void setCarga(int idCarga){
		this.idCarga=idCarga;
	}
	/**
	 * Metodo para obtener el id del buque
	 * @return id_buque
	 */
	public int getIdAlmacen(){
		return idAlmacen;
	}
	/**
	 * Metodo para modificar el nombre
	 * @param nombre 
	 */
	public void setIdAlmacen(int idAlmacen){
		this.idAlmacen=idAlmacen;
	}

}
