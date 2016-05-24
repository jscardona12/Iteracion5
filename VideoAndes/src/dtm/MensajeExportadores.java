package dtm;

import java.util.ArrayList;

public class MensajeExportadores extends Mensaje {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<ExportadorUnificado> exportadores;
	
	public MensajeExportadores(int numPuerto, String mensaje, ArrayList<ExportadorUnificado> exportadores) {
		super(numPuerto, mensaje);
		this.exportadores = exportadores;
	}

	public ArrayList<ExportadorUnificado> getExportadores() {
		return exportadores;
	}

	public void setExportadores(ArrayList<ExportadorUnificado> exportadores) {
		this.exportadores = exportadores;
	}
	
	

}
