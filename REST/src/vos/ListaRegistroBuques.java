package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaRegistroBuques {
	@JsonProperty(value="registros")
	private List<RegistroBuque> registros;

	public ListaRegistroBuques(@JsonProperty(value="registros")List<RegistroBuque> registros) {
		super();
		this.registros = registros;
	}

	public List<RegistroBuque> getRegistros() {
		return registros;
	}

	public void setRegistros(List<RegistroBuque> registros) {
		this.registros = registros;
	}
}
