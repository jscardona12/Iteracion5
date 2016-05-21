package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaIdCargas {
	/**
	 * List con los cargas
	 */
	@JsonProperty(value="cargas")
	private List<Integer> cargas;
	
	/**
	 * Constructor de la clase ListaCargas
	 * @param cargas - cargas para agregar al arreglo de la clase
	 */
	public ListaIdCargas( @JsonProperty(value="cargas")List<Integer> cargas){
		this.cargas = cargas;
	}

	/**
	 * Método que retorna la lista de cargas
	 * @return  List - List con los cargas
	 */
	public List<Integer> getCargas() {
		return cargas;
	}

	/**
	 * Método que asigna la lista de cargas que entra como parametro
	 * @param  cargas - List con los cargas ha agregar
	 */
	public void setCarga(List<Integer> cargas) {
		this.cargas = cargas;
	}
}
