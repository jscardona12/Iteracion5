package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaMovimientoCargas {
	@JsonProperty(value="movimientos")
	private List<MovimientoCarga> movimientos;

	public ListaMovimientoCargas(@JsonProperty(value="movimiento")List<MovimientoCarga> movimientos) {
		super();
		this.movimientos = movimientos;
	}

	public List<MovimientoCarga> getRegistros() {
		return movimientos;
	}

	public void setRegistros(List<MovimientoCarga> movimientos) {
		this.movimientos = movimientos;
	}
}
