package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ConsultaBuque {
	@JsonProperty(value="tipo")
	private String tipo;
	@JsonProperty(value="nombreBuque")
	private String nombreBuque;
	@JsonProperty(value="fecha")
	private String fecha;
	@JsonProperty(value="estado")
	private String estado;
	@JsonProperty(value="tipoCarga")
	private String tipoCarga;
	
	
	
	
	public ConsultaBuque(@JsonProperty(value="tipo")String tipo,
						 @JsonProperty(value="nombreBuque")String nombreBuque,
						 @JsonProperty(value="fecha")String fecha,
						 @JsonProperty(value="estado")String estado,
						 @JsonProperty(value="tipoCarga")String tipoCarga) {
		super();
		this.tipo = tipo;
		this.nombreBuque = nombreBuque;
		this.fecha = fecha;
		this.estado = estado;
		this.tipoCarga = tipoCarga;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getNombreBuque() {
		return nombreBuque;
	}
	public void setNombreBuque(String nombreBuque) {
		this.nombreBuque = nombreBuque;
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
	public String getTipoCarga() {
		return tipoCarga;
	}
	public void setTipoCarga(String tipoCarga) {
		this.tipoCarga = tipoCarga;
	}
	
}
