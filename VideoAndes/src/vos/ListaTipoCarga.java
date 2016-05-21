package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaTipoCarga {
	/**
	 * List con los tiposCarga
	 */
	@JsonProperty(value="tiposCarga")
	private List<TipoCarga> tiposCarga;
	
	/**
	 * Constructor de la clase ListaTipoCargas
	 * @param tiposCarga - tiposCarga para agregar al arreglo de la clase
	 */
	public ListaTipoCarga( @JsonProperty(value="tiposCarga")List<TipoCarga> tiposCarga){
		this.tiposCarga = tiposCarga;
	}

	/**
	 * Método que retorna la lista de tiposCarga
	 * @return  List - List con los tiposCarga
	 */
	public List<TipoCarga> getTipoCargaes() {
		return tiposCarga;
	}

	/**
	 * Método que asigna la lista de tiposCarga que entra como parametro
	 * @param  tiposCarga - List con los tiposCarga ha agregar
	 */
	public void setTipoCarga(List<TipoCarga> tiposCarga) {
		this.tiposCarga = tiposCarga;
	}
}
