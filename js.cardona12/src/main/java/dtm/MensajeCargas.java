package dtm;

import java.util.ArrayList;

public class MensajeCargas extends Mensaje{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<CargaUnificada> cargas;

	public MensajeCargas(int numPuerto, String mensaje, ArrayList<CargaUnificada>cargas) {
		super(numPuerto, mensaje);
		this.cargas=cargas;
	}

	public ArrayList<CargaUnificada> getCargas() {
		return cargas;
	}

	public void setCargas(ArrayList<CargaUnificada> cargas) {
		this.cargas = cargas;
	}

}
