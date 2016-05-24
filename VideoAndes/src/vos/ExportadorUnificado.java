package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ExportadorUnificado {
	@JsonProperty(value="nombre")
	private String nombre;
	@JsonProperty(value="costo")
	private double costo;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public ExportadorUnificado(@JsonProperty(value="nombre")String nombre,
			@JsonProperty(value="costo")double costo) {
		super();
		this.nombre = nombre;
		this.costo = costo;
	}
	
}
