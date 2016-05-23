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
	@JsonProperty(value="estado")
	private String estado;
	@JsonProperty(value="tipo")
	private String tipo;

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
	public Almacenamiento(@JsonProperty(value="id")int id, @JsonProperty(value="tipo")String tipo,
							@JsonProperty(value="estado")String estado){
		super();
		this.id=id;
		this.tipo=tipo;
		this.estado=estado;
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
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
