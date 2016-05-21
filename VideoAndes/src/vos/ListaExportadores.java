package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaExportadores {

	/**
	 * List con los exportadores
	 */
	@JsonProperty(value="exportadores")
	private List<Exportador> exportadores;
	
	/**
	 * Constructor de la clase ListaExportadors
	 * @param exportadores - exportadores para agregar al arreglo de la clase
	 */
	public ListaExportadores( @JsonProperty(value="exportadores")List<Exportador> exportadores){
		this.exportadores = exportadores;
	}

	/**
	 * Método que retorna la lista de exportadores
	 * @return  List - List con los exportadores
	 */
	public List<Exportador> getExportadores() {
		return exportadores;
	}

	/**
	 * Método que asigna la lista de exportadores que entra como parametro
	 * @param  exportadores - List con los exportadores ha agregar
	 */
	public void setExportador(List<Exportador> exportadores) {
		this.exportadores = exportadores;
	}
	
}
