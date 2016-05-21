package vos;
/**
 * Clase que representa persona, ya sea natural o juridica
 * @author JoseDaniel
 *
 */
import org.codehaus.jackson.annotate.*;
public abstract class Persona {
  
  public static final String IMPORTADOR="USUARIO";
  public static final String EXPORTADOR="ADMIN";
  public static final String GERENTE_GENERAL="GERENTE_GENERAL";
  
	//--------------------------
	//ATRIBUTOS
	//--------------------------
	/**
	 * Atributo que modela el nombre de la persona
	 */
	@JsonProperty(value="nombre")
	private String nombre;
	/**
	 * Atributo entero que modela el ID de la persona
	 */

	@JsonProperty(value="id")
	private int id;
	/**
	 * Atributo booleano que dice si la persona es natural
	 */

	@JsonProperty(value="tipoNatural")
	private boolean esNatural;
	//--------------------------
	//CONSTRUCTOR
	//--------------------------
	/**
	 * Constructor de la clase abstracta
	 * @param nombre nombre de la persona
	 * @param id id de la persona
	 * @param esNatural boolean diciendo si es persona natural o no
	 */
	public Persona(String nombre, int id, boolean esNatural){
		super();
		this.nombre=nombre;
		this.id=id;
		this.esNatural=esNatural;
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
	public boolean esNatural(){
		return esNatural;
	}
	/**
	 * Metodo para modificar el nombre
	 * @param nombre 
	 */
	public void setEsNatural(boolean esNatural){
		this.esNatural=esNatural;
	}

}
