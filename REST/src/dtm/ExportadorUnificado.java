package dtm;

import java.io.Serializable;

public class ExportadorUnificado implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4879862732027506412L;

	private String nombre;
	
	private double costo;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public ExportadorUnificado(String nombre, double costo) {
		super();
		this.nombre = nombre;
		this.costo = costo;
	}
	
}
