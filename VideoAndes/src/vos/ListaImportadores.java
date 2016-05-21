/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: ImportadorAndes
 * Autor: Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package vos;

import java.util.List;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa una arreglo de Importador
 * @author Juan
 */
public class ListaImportadores {
	
	/**
	 * List con los importadores
	 */
	@JsonProperty(value="importadores")
	private List<Importador> importadores;
	
	/**
	 * Constructor de la clase ListaImportadors
	 * @param importadores - importadores para agregar al arreglo de la clase
	 */
	public ListaImportadores( @JsonProperty(value="importadores")List<Importador> importadores){
		this.importadores = importadores;
	}

	/**
	 * Método que retorna la lista de importadores
	 * @return  List - List con los importadores
	 */
	public List<Importador> getImportadores() {
		return importadores;
	}

	/**
	 * Método que asigna la lista de importadores que entra como parametro
	 * @param  importadores - List con los importadores ha agregar
	 */
	public void setImportador(List<Importador> importadores) {
		this.importadores = importadores;
	}
	
}
