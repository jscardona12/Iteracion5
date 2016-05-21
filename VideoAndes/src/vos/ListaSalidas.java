package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaSalidas {
	/**
	 * List con los salidas
	 */
	@JsonProperty(value="salidas")
	private List<Salida> salidas;
	
	/**
	 * Constructor de la clase ListaSalidas
	 * @param salidas - salidas para agregar al arreglo de la clase
	 */
	public ListaSalidas( @JsonProperty(value="salidas")List<Salida> salidas){
		this.salidas = salidas;
	}

	/**
	 * Método que retorna la lista de salidas
	 * @return  List - List con los salidas
	 */
	public List<Salida> getSalidas() {
		return salidas;
	}

	/**
	 * Método que asigna la lista de salidas que entra como parametro
	 * @param  salidas - List con los salidas ha agregar
	 */
	public void setSalida(List<Salida> salidas) {
		this.salidas = salidas;
	}
}
