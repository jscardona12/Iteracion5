package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class MovimientoCarga {
	
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
	
	@JsonProperty(value="puerto_origen")
	private String puerto_origen;
	
	@JsonProperty(value="puerto_destino")
	private String puerto_destino;

	@JsonProperty(value="id_registro")
	private int id_registro;
	
	@JsonProperty(value="fecha")
	private String fecha;
	
	@JsonProperty(value="estado")
	private String estado;
	
	@JsonProperty(value="valor")
	private double valor;
	
	public MovimientoCarga(@JsonProperty(value="id_carga") int id_carga,@JsonProperty(value="tipo") String tipo,@JsonProperty(value="peso") double peso,@JsonProperty(value="volumen") double volumen,@JsonProperty(value="id_buque") int id_buque,@JsonProperty(value="id_camion") int id_camion,
			@JsonProperty(value="id_almacenamiento") int id_almacenamiento,@JsonProperty(value="id_cliente") int id_cliente,
	@JsonProperty(value="puerto_origen") String puerto_origen, @JsonProperty(value="puerto_destino") String puerto_destino,
	@JsonProperty(value="id_registro") int id_registro,
	@JsonProperty(value="fecha")  String fecha,
	@JsonProperty(value="estado") String estado,
	@JsonProperty(value="valor")double valor){
		super();
		this.id_carga = id_carga;
		this.tipo = tipo;
		this.peso = peso;
		this.volumen = volumen;
		this.id_buque = id_buque;
		this.id_camion = id_camion;
		this.id_almacenamiento = id_almacenamiento;
		this.id_cliente = id_cliente;
		this.puerto_origen = puerto_origen;
		this.puerto_destino = puerto_destino;
		this.id_registro = id_registro;
		this.fecha = fecha;
		this.estado = estado;
		this.valor = valor;
	}

	
	public double getValor() {
		return valor;
	}


	public void setValor(double valor) {
		this.valor = valor;
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

	public String getPuerto_origen() {
		return puerto_origen;
	}

	public void setPuerto_origen(String puerto_origen) {
		this.puerto_origen = puerto_origen;
	}

	public String getPuerto_destino() {
		return puerto_destino;
	}

	public void setPuerto_destino(String puerto_destino) {
		this.puerto_destino = puerto_destino;
	}

	public int getId_registro() {
		return id_registro;
	}

	public void setId_registro(int id_registro) {
		this.id_registro = id_registro;
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
