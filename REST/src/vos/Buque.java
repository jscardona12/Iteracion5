package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Buque {
	
	@JsonProperty(value="user_id")
	private int user_id;
	
	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="tipo")
	private String tipo;
	
	@JsonProperty(value="capacidad")
	private double capacidad;
	
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="nombre_agente")
	private String nombre_agente;
	
	@JsonProperty(value="id_terminal")
	private int id_terminal;
	
	@JsonProperty(value="estado")
	private String estado;

	public Buque(@JsonProperty(value="user_id")int user_id,@JsonProperty(value="id")int id,@JsonProperty(value="tipo") String tipo,@JsonProperty(value="capacidad") double capacidad,@JsonProperty(value="nombre") String nombre,@JsonProperty(value="nombre_agente") String nombre_agente,@JsonProperty(value="id_terminal") int id_terminal,
			@JsonProperty(value="estado") String estado) {
		super();
		this.user_id = user_id;
		this.id = id;
		this.tipo = tipo;
		this.capacidad = capacidad;
		this.nombre = nombre;
		this.nombre_agente = nombre_agente;
		this.id_terminal = id_terminal;
		this.estado = estado;
		user_id = -1;
	}
	
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getNombre_agente() {
		return nombre_agente;
	}

	public void setNombre_agente(String nombre_agente) {
		this.nombre_agente = nombre_agente;
	}

	public int getId_terminal() {
		return id_terminal;
	}

	public void setId_terminal(int id_terminal) {
		this.id_terminal = id_terminal;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public double getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(double capacidad) {
		this.capacidad = capacidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreAgente() {
		return nombre_agente;
	}

	public void setNombreAgente(String nombre_agente) {
		this.nombre_agente = nombre_agente;
	}

	public int getIdTerminal() {
		return id_terminal;
	}

	public void setIdTerminal(int id_terminal) {
		this.id_terminal = id_terminal;
	}	
	
}
