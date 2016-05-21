package vos;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class Arribo {
	//--------------------------
	//ATRIBUTOS
	//--------------------------

	/**
	 * Atributo entero que modela la fecha del arribo
	 */
	@JsonProperty(value="fecha")
	private Date fecha;
	/**
	 * Atributo entero que modela el buque del arribo
	 */
	@JsonProperty(value="idBuque")
	private int id_buque;
	/**
	 * Atributo entero que modela el buque del arribo
	 */
	@JsonProperty(value="idMuelle")
	private int id_muelle;
	/**
	 * Atributo entero que modela el buque del arribo
	 */
	@JsonProperty(value="idOrigen")
	private Integer id_origen;

	//--------------------------
	//CONSTRUCTOR
	//--------------------------
	/**
	 * Constructor de la clase abstracta
	 * @param nombre nombre de la persona
	 * @param id id de la persona
	 * @param esNatural boolean diciendo si es persona natural o no
	 */
	public Arribo(@JsonProperty(value="fecha")Date fecha, @JsonProperty(value="idBuque")int id_buque,
			@JsonProperty(value="idMuelle")int id_muelle,@JsonProperty(value="idOrigen")Integer id_origen){
		super();
		this.fecha=fecha;
		this.id_buque=id_buque;
		this.id_muelle=id_muelle;
		this.id_origen=id_origen;
	}
	//--------------------------
	//METODOS
	//--------------------------
	/**
	 * getter idmuelle
	 * @return
	 */
	public int getIdMuelle() {
		return id_muelle;
	}
	/**
	 * setter id muelle
	 * @param id_muelle
	 */
	public void setIdMuelle(int id_muelle) {
		this.id_muelle = id_muelle;
	}
	/**
	 * getter origen
	 * @return
	 */
	public int getIdOrigen() {
		return id_origen;
	}
	/**
	 * setter origen
	 * @param id_origen
	 */
	public void setIdOrigen(int id_origen) {
		this.id_origen = id_origen;
	}
	/**
	 * Metodo para obtener el fecha
	 * @return fecha
	 */
	public Date getFecha(){
		return fecha;
	}
	/**
	 * Metodo para modificar el nombre
	 * @param nombre 
	 */
	public void setFecha(Date fecha){
		this.fecha=fecha;
	}
	/**
	 * Metodo para obtener el id del buque
	 * @return id_buque
	 */
	public int getIdBuque(){
		return id_buque;
	}
	/**
	 * Metodo para modificar el id buque
	 * @param nombre 
	 */
	public void setIdBuque(int id_buque){
		this.id_buque=id_buque;
	}

}
