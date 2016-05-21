package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaArribos {
	/**
	 * List con los arribos
	 */
	@JsonProperty(value="arribos")
	private List<Arribo> arribos;
	
	/**
	 * Constructor de la clase ListaArribos
	 * @param arribos - arribos para agregar al arreglo de la clase
	 */
	public ListaArribos( @JsonProperty(value="arribos")List<Arribo> arribos){
		this.arribos = arribos;
	}

	/**
	 * Método que retorna la lista de arribos
	 * @return  List - List con los arribos
	 */
	public List<Arribo> getArribos() {
		return arribos;
	}

	/**
	 * Método que asigna la lista de arribos que entra como parametro
	 * @param  arribos - List con los arribos ha agregar
	 */
	public void setArribo(List<Arribo> arribos) {
		this.arribos = arribos;
	}
}
