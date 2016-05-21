package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Factura {
	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="costo")
	private double costo;
	
	@JsonProperty(value="id_cliente")
	private int id_cliente;
	
	@JsonProperty(value="fecha")
	private String fecha;

	public Factura(@JsonProperty(value="id")int id, @JsonProperty(value="costo") double costo, 
			@JsonProperty(value="id_cliente")int id_cliente,@JsonProperty(value="fecha") String fecha) {
		super();
		this.id = id;
		this.costo = costo;
		this.id_cliente = id_cliente;
		this.fecha = fecha;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public int getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}
}
