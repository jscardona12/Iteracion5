package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaMovimientoAlmacen {
	/**
	 * List con los arribos
	 */
	@JsonProperty(value="movimientos")
	private List<MovimientoAlmacen> movimientos;
	
	/**
	 * Constructor de la clase ListaArribos
	 * @param arribos - arribos para agregar al arreglo de la clase
	 */
	public ListaMovimientoAlmacen( @JsonProperty(value="arribos")List<MovimientoAlmacen> ma){
		this.movimientos = ma;
	}

	/**
	 * Método que retorna la lista de arribos
	 * @return  List - List con los arribos
	 */
	public List<MovimientoAlmacen> getArribos() {
		return movimientos;
	}

	/**
	 * Método que asigna la lista de arribos que entra como parametro
	 * @param  arribos - List con los arribos ha agregar
	 */
	public void setArribo(List<MovimientoAlmacen> ma) {
		this.movimientos = ma;
	}
}
