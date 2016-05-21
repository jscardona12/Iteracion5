package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Usuario {

  public static final String USUARIO="USUARIO";
	public static final String ADMIN="ADMIN";
	public static final String AGENTE_PORTUARIO="OP_PORTUARIO";
	//--------------------------
	//ATRIBUTOS
	//--------------------------
	/**
	 * Atributo que modela el nombre de la persona
	 */
	@JsonProperty(value="rol")
	private int rol;
	/**
	 * Atributo entero que modela el ID de la persona
	 */

	@JsonProperty(value="id")
	private int id;
	/**
	 * Atributo booleano que dice si la persona es natural
	 */

	@JsonProperty(value="nombre")
	private String nombre;
	//--------------------------
	//CONSTRUCTOR
	//--------------------------
	/**
	 * Constructor de la clase abstracta
	 * @param nombre nombre de la persona
	 * @param id id de la persona
	 * @param esNatural boolean diciendo si es persona natural o no
	 */
	public Usuario(@JsonProperty(value="nombre")String nombre,
	               @JsonProperty(value="id")int id,
	               @JsonProperty(value="rol")int rol){
		super();
		this.nombre=nombre;
		this.id=id;
		this.rol = rol;
	}
	//--------------------------
	//METODOS
	//--------------------------
	/**
	 * Metodo para obtener el nombre
	 * @return nombre
	 */
	public String getNombre(){
		return nombre;
	}
	/**
	 * Metodo para modificar el nombre
	 * @param nombre 
	 */
	public void setNombre(String nombre){
		this.nombre=nombre;
	}
	/**
	 * Metodo para obtener el ID
	 * @return ID
	 */
	public int getID(){
		return id;
	}
	/**
	 * Metodo para modificar el nombre
	 * @param nombre 
	 */
	public void setID(int id){
		this.id=id;
	}
	/**
	 * Metodo para obtener si es natural
	 * @return true si es natural, false de lo contrario
	 */
	public int getRol(){
		return rol;
	}
	/**
	 * Metodo para modificar el nombre
	 * @param nombre 
	 */
	public void setRol(int rol){
		this.rol=rol;
	}
}
