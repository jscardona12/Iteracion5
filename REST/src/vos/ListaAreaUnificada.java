package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaAreaUnificada {
	@JsonProperty(value="areas")
	private List<AreaUnificada> areas;

	public ListaAreaUnificada(@JsonProperty(value="areas")List<AreaUnificada> areas) {
		super();
		this.areas = areas;
	}

	public List<AreaUnificada> getAreas() {
		return areas;
	}

	public void setAreas(List<AreaUnificada> areas) {
		this.areas = areas;
	}
}
