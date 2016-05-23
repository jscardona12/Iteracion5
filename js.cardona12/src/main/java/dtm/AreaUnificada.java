package dtm;

import java.io.Serializable;

public class AreaUnificada implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 66183199026496518L;

	private String estado;
	
	private String tipo;

	public AreaUnificada(String estado, String tipo) {
		super();
		this.estado = estado;
		this.tipo = tipo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
}
