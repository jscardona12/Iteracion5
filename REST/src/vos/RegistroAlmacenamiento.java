package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class RegistroAlmacenamiento {
	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="fecha")
	private String fecha;
	
	@JsonProperty(value="tipo")
	private String tipo;
	
	@JsonProperty(value="id_almacenamiento")
	private int id_almacenamiento;
	
	public RegistroAlmacenamiento(@JsonProperty(value="id")int id,
			@JsonProperty(value="fecha")String fecha,
			@JsonProperty(value="tipo") String tipo,
			@JsonProperty(value="id_almacenamiento")int id_almacenamiento) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.tipo = tipo;
		this.id_almacenamiento = id_almacenamiento;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getId_almacenamiento() {
		return id_almacenamiento;
	}

	public void setId_almacenamiento(int id_almacenamiento) {
		this.id_almacenamiento = id_almacenamiento;
	}
	
	
}
