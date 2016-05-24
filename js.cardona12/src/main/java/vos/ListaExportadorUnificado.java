package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaExportadorUnificado {
	@JsonProperty(value="exportadores")
	private List<ExportadorUnificado> exportadores;

	public ListaExportadorUnificado(@JsonProperty(value="exportadores")List<ExportadorUnificado> exportadores) {
		super();
		this.exportadores = exportadores;
	}

	public List<ExportadorUnificado> getExportadores() {
		return exportadores;
	}

	public void setExportadores(List<ExportadorUnificado> exportadores) {
		this.exportadores = exportadores;
	}
	
	
}
