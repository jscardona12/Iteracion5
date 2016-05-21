package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Exportador extends Persona {
	//--------------------------
	//ATRIBUTOS
	//--------------------------
	/**
	 * Atributo que modela el rut del exportador
	 */
	@JsonProperty(value="rut")
	private String rut;
	//--------------------------
	//CONSTRUCTOR
	//--------------------------
	/**
	 * Constructor de exportador
	 * @param nombre nombre del exportador
	 * @param id id del exportador
	 * @param esNatural booleano que representa si es persona natural o no
	 * @param rut RUT del exportador
	 */
	public Exportador(@JsonProperty(value="nombre")      String nombre,
	                  @JsonProperty(value="id")          int id,
	                  @JsonProperty(value="tipoNatural") boolean esNatural,
	                  @JsonProperty(value="rut")         String rut){
		super(nombre, id, esNatural);
		this.rut=rut;
	}
	//--------------------------
	//METODOS
	//--------------------------
	/**
	 * Metodo para obtener el rut
	 * @return rut
	 */
	public String getRUT(){
		return rut;
	}
	/**
	 * Metodo para modificar el rut
	 * @param rut
	 */
	public void setRUT(String rut){
		this.rut=rut;
	}
}
