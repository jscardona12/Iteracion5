package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class RegistroBuque {
	
	@JsonProperty(value="id_usuario")
	private int id_usuario;
	
	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="nombre_capitan")
	private String nombre_capitan;
	
	@JsonProperty(value="fecha_salida")
	private String fecha_salida;
	
	@JsonProperty(value="pais_origen")
	private String pais_origen;
	
	@JsonProperty(value="pais_destino")
	private String pais_destino;
	
	@JsonProperty(value="fecha_arribo")
	private String fecha_arribo;
	
	@JsonProperty(value="puerto_origen")
	private String puerto_origen;
	
	@JsonProperty(value="puerto_arribo")
	private String puerto_arribo;
	
	@JsonProperty(value="ciudad_origen")
	private String ciudad_origen;
	
	@JsonProperty(value="ciudad_arribo")
	private String ciudad_arribo;
	
	@JsonProperty(value="id_buque")
	private int id_buque;
	
	@JsonProperty(value="tipo_carga")
	private String tipo_carga;

	public RegistroBuque(
			@JsonProperty(value="id_usuario") int id_usuario,
			@JsonProperty(value="id")int id, 
			@JsonProperty(value="nombre_capitan")String nombre_capitan, 
			@JsonProperty(value="fecha_salida")String fecha_salida, 
			@JsonProperty(value="pais_origen")String pais_origen, 
			@JsonProperty(value="pais_destino")String pais_destino,
			@JsonProperty(value="fecha_arribo")String fecha_arribo,
			@JsonProperty(value="puerto_origen")String puerto_origen, 
			@JsonProperty(value="puerto_arribo")String puerto_arribo,
			@JsonProperty(value="ciudad_origen")String ciudad_origen,
			@JsonProperty(value="ciudad_arribo")String ciudad_arribo,
			@JsonProperty(value="id_buque") int id_buque,
			@JsonProperty(value="tipo_carga")String tipo_carga) {
		super();
		this.id_usuario = id_usuario;
		this.id = id;
		this.nombre_capitan = nombre_capitan;
		this.fecha_salida = fecha_salida;
		this.pais_origen = pais_origen;
		this.pais_destino = pais_destino;
		this.fecha_arribo = fecha_arribo;
		this.puerto_origen = puerto_origen;
		this.puerto_arribo = puerto_arribo;
		this.ciudad_origen = ciudad_origen;
		this.ciudad_arribo = ciudad_arribo;
		this.id_buque = id_buque;
		this.tipo_carga = tipo_carga;
	}

	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}

	public int getId_buque() {
		return id_buque;
	}

	public void setId_buque(int id_buque) {
		this.id_buque = id_buque;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre_capitan() {
		return nombre_capitan;
	}

	public void setNombre_capitan(String nombre_capitan) {
		this.nombre_capitan = nombre_capitan;
	}

	public String getFecha_salida() {
		return fecha_salida;
	}

	public void setFecha_salida(String fecha_salida) {
		this.fecha_salida = fecha_salida;
	}

	public String getPais_origen() {
		return pais_origen;
	}

	public void setPais_origen(String pais_origen) {
		this.pais_origen = pais_origen;
	}

	public String getPais_destino() {
		return pais_destino;
	}

	public void setPais_destino(String pais_destino) {
		this.pais_destino = pais_destino;
	}

	public String getFecha_arribo() {
		return fecha_arribo;
	}

	public void setFecha_arribo(String fecha_arribo) {
		this.fecha_arribo = fecha_arribo;
	}

	public String getPuerto_origen() {
		return puerto_origen;
	}

	public void setPuerto_origen(String puerto_origen) {
		this.puerto_origen = puerto_origen;
	}

	public String getPuerto_arribo() {
		return puerto_arribo;
	}

	public void setPuerto_arribo(String puerto_arribo) {
		this.puerto_arribo = puerto_arribo;
	}

	public String getCiudad_origen() {
		return ciudad_origen;
	}

	public void setCiudad_origen(String ciudad_origen) {
		this.ciudad_origen = ciudad_origen;
	}

	public String getCiudad_arribo() {
		return ciudad_arribo;
	}

	public void setCiudad_arribo(String ciudad_arribo) {
		this.ciudad_arribo = ciudad_arribo;
	}

	public String getTipo_carga() {
		return tipo_carga;
	}

	public void setTipo_carga(String tipo_carga) {
		this.tipo_carga = tipo_carga;
	}

	
}
