package dtm;

import java.io.Serializable;

public class Mensaje implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Numero del puerto que manda el mensaje
	 */
	private int numPuerto;
	/**
	 * El mensaje
	 */
	private String mensaje;
	/**
	 * Constructor
	 * @param numPuerto
	 * @param mensaje
	 */
	public Mensaje(int numPuerto, String mensaje) {
		this.numPuerto = numPuerto;
		this.mensaje = mensaje;
	}
	
	public int getNumPuerto() {
		return numPuerto;
	}

	public void setNumPuerto(int numPuerto) {
		this.numPuerto = numPuerto;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	

	
}
