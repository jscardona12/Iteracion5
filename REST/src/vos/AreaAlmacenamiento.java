package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class AreaAlmacenamiento {
	@JsonProperty(value="user_id")
	private int user_id;
	
	
	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="dimension")
	private double dimension;
	
	@JsonProperty(value="estado")
	private String estado;
	
	@JsonProperty(value="tipo")
	private String tipo;

	public AreaAlmacenamiento(@JsonProperty(value="user_id") int user_id
			,@JsonProperty(value="id")int id
			,@JsonProperty(value="dimension") double dimension
			,@JsonProperty(value="estado") String estado,
			@JsonProperty(value="tipo") String tipo) {
		super();
		this.user_id = user_id;
		this.id = id;
		this.dimension = dimension;
		this.estado = estado;
		this.tipo = tipo;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getDimension() {
		return dimension;
	}

	public void setDimension(double dimension) {
		this.dimension = dimension;
	}
	
	
}
