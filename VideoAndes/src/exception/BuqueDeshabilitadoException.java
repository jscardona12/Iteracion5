package exception;

import java.util.ArrayList;

import vos.Carga;

public class BuqueDeshabilitadoException extends Exception{

	private ArrayList<Carga> cargasMal;
	private String m;
	public BuqueDeshabilitadoException(String mensaje, ArrayList<Carga> c){
		super(mensaje);
		cargasMal=c;
		m=mensaje;
	}
	
	public String getMessage(){
		String resp = m+"\n";
		for(Carga c:cargasMal){
			resp+=c.getID()+"\n";
		}
		return resp;
	}
}
