package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ConsultaAreas {

	@JsonProperty(value="id_area")
	private int id_area;
	
	@JsonProperty(value="dimension")
	private double dimension;
	
	@JsonProperty(value="estado_area")
	private String estado_area;
	
	@JsonProperty(value="tipo_area")
	private String tipo_area;
	
	@JsonProperty(value="id_carga")
	private int id_carga;
	
	@JsonProperty(value="fecha")
	private String fecha;
	
	@JsonProperty(value="estado_carga")
	private String estado_carga;

	public ConsultaAreas(@JsonProperty(value="id_area") int id_area,
			@JsonProperty(value="dimension")double dimension, 
			@JsonProperty(value="estado_area")String estado_area, 
			@JsonProperty(value="tipo_area")String tipo_area,
			@JsonProperty(value="id_carga")int id_carga, 
			@JsonProperty(value="fecha")String fecha,
			@JsonProperty(value="estado_carga")String estado_carga) {
		super();
		this.id_area = id_area;
		this.dimension = dimension;
		this.estado_area = estado_area;
		this.tipo_area = tipo_area;
		this.id_carga = id_carga;
		this.fecha = fecha;
		this.estado_carga = estado_carga;
	}

	public int getId_area() {
		return id_area;
	}

	public void setId_area(int id_area) {
		this.id_area = id_area;
	}

	public double getDimension() {
		return dimension;
	}

	public void setDimension(double dimension) {
		this.dimension = dimension;
	}

	public String getEstado_area() {
		return estado_area;
	}

	public void setEstado_area(String estado_area) {
		this.estado_area = estado_area;
	}

	public String getTipo_area() {
		return tipo_area;
	}

	public void setTipo_area(String tipo_area) {
		this.tipo_area = tipo_area;
	}

	public int getId_carga() {
		return id_carga;
	}

	public void setId_carga(int id_carga) {
		this.id_carga = id_carga;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getEstado_carga() {
		return estado_carga;
	}

	public void setEstado_carga(String estado_carga) {
		this.estado_carga = estado_carga;
	}
	
	
}
