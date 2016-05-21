package vos;
/**
 * Clase que representa persona, ya sea natural o juridica
 * @author JoseDaniel
 *
 */
import org.codehaus.jackson.annotate.*;
public abstract class Almacenamiento {
	//--------------------------
	//ATRIBUTOS
	//--------------------------
	
	/**
	 * Atributo entero que modela el ID de la persona
	 */
	@JsonProperty(value="id")
	private int id;

	//--------------------------
	//CONSTRUCTOR
	//--------------------------
	/**
	 * Constructor de la clase abstracta
	 * @param nombre nombre de la persona
	 * @param id id de la persona
	 * @param esNatural boolean diciendo si es persona natural o no
	 */
	public Almacenamiento(@JsonProperty(value="id")int id){
		super();
		this.id=id;
	}
	//--------------------------
	//METODOS
	//--------------------------
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

}
