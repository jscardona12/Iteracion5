package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class RegistroCarga {
	
	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="id_carga")
	private int id_carga;
	
	@JsonProperty(value="fecha")
	private String fecha;
	
	@JsonProperty(value="estado")
	private String estado;

	public RegistroCarga(@JsonProperty(value="id")int id,@JsonProperty(value="id_carga") int id_carga,@JsonProperty(value="fecha") String fecha,@JsonProperty(value="estado") String estado) {
		super();
		this.id = id;
		this.id_carga = id_carga;
		this.fecha = fecha;
		this.estado = estado;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
}
