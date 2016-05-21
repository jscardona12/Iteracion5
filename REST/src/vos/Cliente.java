package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Cliente {
	
	@JsonProperty(value="id")
	private int id;
	
	
	@JsonProperty(value="nombre")
	private String nombre;


	public Cliente(@JsonProperty(value="id") int id,@JsonProperty(value="nombre") String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
}
