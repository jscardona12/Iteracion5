package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Usuario {
	
	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="tipo")
	private String tipo;

	public Usuario(@JsonProperty(value="id")int id,@JsonProperty(value="tipo") String tipo) {
		super();
		this.id = id;
		this.tipo = tipo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
}
