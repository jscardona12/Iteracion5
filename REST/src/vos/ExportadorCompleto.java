package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ExportadorCompleto {
	@JsonProperty(value="id_exportador")
	private int id_exportador;
	
	@JsonProperty(value="rut")
	private int rut;
	
	@JsonProperty(value="num_usos")
	private int num_usos;
	
	@JsonProperty(value="id_carga")
	private int id_carga;
	
	@JsonProperty(value="tipo")
	private String tipo;
	
	@JsonProperty(value="peso")
	private double peso;
	
	@JsonProperty(value="volumen")
	private double volumen;

	@JsonProperty(value="id_buque")
	private int id_buque;
	
	@JsonProperty(value="id_camion")
	private int id_camion;
	
	@JsonProperty(value="id_almacenamiento")
	private int id_almacenamiento;
	
	@JsonProperty(value="id_cliente")
	private int id_cliente;

	public ExportadorCompleto(
			@JsonProperty(value="id_exportador")int id_exportador, 
			@JsonProperty(value="rut")int rut, 
			@JsonProperty(value="num_usos")int num_usos,
			@JsonProperty(value="id_carga")int id_carga,
			@JsonProperty(value="tipo")String tipo, 
			@JsonProperty(value="peso")double peso, 
			@JsonProperty(value="volumen")double volumen,
			@JsonProperty(value="id_buque")int id_buque, 
			@JsonProperty(value="id_camion")int id_camion,
			@JsonProperty(value="id_almacenamiento") int id_almacenamiento, 
			@JsonProperty(value="id_cliente")int id_cliente) {
		super();
		this.id_exportador = id_exportador;
		this.rut = rut;
		this.num_usos = num_usos;
		this.id_carga = id_carga;
		this.tipo = tipo;
		this.peso = peso;
		this.volumen = volumen;
		this.id_buque = id_buque;
		this.id_camion = id_camion;
		this.id_almacenamiento = id_almacenamiento;
		this.id_cliente = id_cliente;
	}

	public int getNum_usos() {
		return num_usos;
	}

	public void setNum_usos(int num_usos) {
		this.num_usos = num_usos;
	}

	public int getId_exportador() {
		return id_exportador;
	}

	public void setId_exportador(int id_exportador) {
		this.id_exportador = id_exportador;
	}

	public int getRut() {
		return rut;
	}

	public void setRut(int rut) {
		this.rut = rut;
	}

	public int getId_carga() {
		return id_carga;
	}

	public void setId_carga(int id_carga) {
		this.id_carga = id_carga;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public double getVolumen() {
		return volumen;
	}

	public void setVolumen(double volumen) {
		this.volumen = volumen;
	}

	public int getId_buque() {
		return id_buque;
	}

	public void setId_buque(int id_buque) {
		this.id_buque = id_buque;
	}

	public int getId_camion() {
		return id_camion;
	}

	public void setId_camion(int id_camion) {
		this.id_camion = id_camion;
	}

	public int getId_almacenamiento() {
		return id_almacenamiento;
	}

	public void setId_almacenamiento(int id_almacenamiento) {
		this.id_almacenamiento = id_almacenamiento;
	}

	public int getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}
	
	
}
