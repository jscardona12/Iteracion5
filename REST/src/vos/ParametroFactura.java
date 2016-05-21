package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ParametroFactura {
	@JsonProperty(value="id_buque")
	private int id_buque;
	
	@JsonProperty(value="fecha")
	private String fecha;

	public ParametroFactura(@JsonProperty(value="id_buque")int id_buque,
			@JsonProperty(value="fecha") String fecha) {
		super();
		this.id_buque = id_buque;
		this.fecha = fecha;
	}

	public int getId_buque() {
		return id_buque;
	}

	public void setId_buque(int id_buque) {
		this.id_buque = id_buque;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	
}
