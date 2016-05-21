package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Exportador {
	
	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="rut")
	private int rut;

	public Exportador(@JsonProperty(value="id")int id,@JsonProperty(value="rut") int rut) {
		super();
		this.id = id;
		this.rut = rut;
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
