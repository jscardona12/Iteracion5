package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaCargas {
	/**
	 * List con los cargas
	 */
	@JsonProperty(value="cargas")
	private List<Carga> cargas;
	
	/**
	 * Constructor de la clase ListaCargas
	 * @param cargas - cargas para agregar al arreglo de la clase
	 */
	public ListaCargas( @JsonProperty(value="cargas")List<Carga> cargas){
		this.cargas = cargas;
	}

	/**
	 * Método que retorna la lista de cargas
	 * @return  List - List con los cargas
	 */
	public List<Carga> getCargas() {
		return cargas;
	}

	/**
	 * Método que asigna la lista de cargas que entra como parametro
	 * @param  cargas - List con los cargas ha agregar
	 */
	public void setCarga(List<Carga> cargas) {
		this.cargas = cargas;
	}
}
