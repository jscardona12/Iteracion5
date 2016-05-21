package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaConsultaBuques {
	@JsonProperty(value="entradas")
	private List<ConsultaBuque> entradas;

	public ListaConsultaBuques(@JsonProperty(value="entradas")List<ConsultaBuque> entradas) {
		super();
		this.entradas = entradas;
	}

	public List<ConsultaBuque> getEntradas() {
		return entradas;
	}

	public void setEntradas(List<ConsultaBuque> entradas) {
		this.entradas = entradas;
	}
}
