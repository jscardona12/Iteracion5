package dtm;

import java.util.ArrayList;

public class MensajeAreas extends Mensaje {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<AreaUnificada> areas;

	public MensajeAreas(int numPuerto, String mensaje, ArrayList<AreaUnificada>areas) {
		super(numPuerto, mensaje);
		this.areas=areas;
	}

	public ArrayList<AreaUnificada> getAreas() {
		return areas;
	}

	public void setAreas(ArrayList<AreaUnificada> areas) {
		this.areas = areas;
	}
}
