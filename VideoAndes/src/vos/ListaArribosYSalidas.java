package vos;

import java.util.List;

public class ListaArribosYSalidas extends ListaArribos {
  
	private ListaSalidas salidas;
	
	public ListaArribosYSalidas(List<Arribo> arribos, List<Salida> salidas) {
		super(arribos);
		this.salidas=new ListaSalidas(salidas);
	}
	public ListaSalidas getSalidas() {
		return salidas;
	}
	public void setSalidas(ListaSalidas salidas) {
		this.salidas = salidas;
	}
}
