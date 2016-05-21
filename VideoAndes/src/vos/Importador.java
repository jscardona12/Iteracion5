package vos;

import org.codehaus.jackson.annotate.JsonProperty;
/**
 * Clase que representa a un importador, extiende persona
 * @author JoseDaniel
 *
 */
public class Importador extends Persona {

	//--------------------------
	//ATRIBUTOS
	//--------------------------
	/**
	 * Atributo que modela el registro aduanero del importador
	 */
	@JsonProperty(value="registroAduana")
	private String registroAduana;
	/**
	 * Atributo booleano que representa si es importador habitual o no
	 */
	@JsonProperty(value="tipoHabitual")
	private boolean tipoHabitual;
	//--------------------------
	//CONSTRUCTOR
	//--------------------------
	/**
	 * Constructor de importador
	 * @param nombre nombre del importador
	 * @param id id del importador
	 * @param esNatural booleano que representa si es persona natural o no
	 * @param registroAduana registro de aduana
	 * @param tipoHabitual booleano que representa si es tipo habitual o no
	 * @throws Exception si es natural y habitual, pues los importadores habituales no pueden ser personas naturales
	 */
	//@JsonProperty(value="nombre")String nombre, @JsonProperty(value="id")int id, @JsonProperty(value="tipoNatural") boolean esNatural
	public Importador(@JsonProperty(value="nombre")         String nombre,
	                  @JsonProperty(value="id")             int id,
	                  @JsonProperty(value="esNatural")    boolean esNatural,
	                  @JsonProperty(value="registroAduana") String registroAduana,
	                  @JsonProperty(value="tipoHabitual")   boolean tipoHabitual) throws Exception {
		super(nombre, id, esNatural);
		// TODO Auto-generated constructor stub
		if(tipoHabitual && esNatural) throw new Exception("Los importadores habituales no pueden ser personas naturales");
		this.tipoHabitual=tipoHabitual;
		this.registroAduana=registroAduana;
	}
	//--------------------------
	//METODOS
	//--------------------------
	/**
	 * Metodo para obtener el registro aduana
	 * @return registro aduana
	 */
	public String getRegistroAduana(){
		return registroAduana;
	}
	/**
	 * Metodo para modificar el registro aduana
	 * @param registro aduana 
	 */
	public void setRegistroAduana(String registroAduana){
		this.registroAduana=registroAduana;
	}
	/**
	 * Metodo saber si el importador es habitual
	 * @return tipoHabitual true si es habitual, false de lo contrario 
	 */
	public boolean esTipoHabitual(){
		return tipoHabitual;
	}
	/**
	 * Metodo para modificar el tipo
	 * @param tipoHabitual true si es habitual, false de lo contrario 
	 */
	public void setTipoHabitual(boolean tipoHabitual){
		this.tipoHabitual=tipoHabitual;
	}

}
