package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaConsultaAreas {
	@JsonProperty(value="areas")
	private List<ConsultaAreas> areas;

	public ListaConsultaAreas(@JsonProperty(value="areas")List<ConsultaAreas> areas) {
		super();
		this.areas = areas;
	}

	public List<ConsultaAreas> getAreas() {
		return areas;
	}

	public void setAreas(List<ConsultaAreas> areas) {
		this.areas = areas;
	}
}
