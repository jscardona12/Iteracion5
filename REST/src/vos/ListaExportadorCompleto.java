package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaExportadorCompleto {
	@JsonProperty(value="exportadores")
	private List<ExportadorCompleto> exportadores;

	public ListaExportadorCompleto(@JsonProperty(value="exportadores")List<ExportadorCompleto> exportadores) {
		super();
		this.exportadores = exportadores;
	}

	public List<ExportadorCompleto> getExportadores() {
		return exportadores;
	}

	public void setExportadores(List<ExportadorCompleto> exportadores) {
		this.exportadores = exportadores;
	}
}
