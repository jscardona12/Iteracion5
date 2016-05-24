package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Exportador {
	
	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="rut")
	private int rut;
	
	@JsonProperty(value="costo")
	private int costo;

	public Exportador(@JsonProperty(value="id")int id,@JsonProperty(value="rut") int rut,
			@JsonProperty(value="costo") int costo) {
		super();
		this.id = id;
		this.rut = rut;
		this.costo = costo;
	}

	public int getCosto() {
		return costo;
	}

	public void setCosto(int costo) {
		this.costo = costo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRut() {
		return rut;
	}

	public void setRut(int rut) {
		this.rut = rut;
	}
	
	
}
