package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaMovimientoCarga2 {
	/**
	 * List con los arribos
	 */
	@JsonProperty(value="movimientos")
	private List<MovimientoCarga2> movimientos;
	
	/**
	 * Constructor de la clase ListaArribos
	 * @param arribos - arribos para agregar al arreglo de la clase
	 */
	public ListaMovimientoCarga2( @JsonProperty(value="arribos")List<MovimientoCarga2> mc2){
		this.movimientos = mc2;
	}

	/**
	 * Método que retorna la lista de arribos
	 * @return  List - List con los arribos
	 */
	public List<MovimientoCarga2> getArribos() {
		return movimientos;
	}

	/**
	 * Método que asigna la lista de arribos que entra como parametro
	 * @param  arribos - List con los arribos ha agregar
	 */
	public void setArribo(List<MovimientoCarga2> mc2) {
		this.movimientos = mc2;
	}
}
