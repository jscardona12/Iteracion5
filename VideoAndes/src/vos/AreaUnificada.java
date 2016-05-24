package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class AreaUnificada {
	@JsonProperty(value="estado")
	private String estado;
	
	@JsonProperty(value="tipo")
	private String tipo;
	
	public AreaUnificada(@JsonProperty(value="estado")String estado,
			@JsonProperty(value="tipo")String tipo){
		super();
		this.estado = estado;
		this.tipo = tipo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
}
