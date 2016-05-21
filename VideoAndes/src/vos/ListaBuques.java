package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaBuques {
	/**
	 * List con los buques
	 */
	@JsonProperty(value="buques")
	private List<Buque> buques;
	
	/**
	 * Constructor de la clase ListaBuques
	 * @param buques - buques para agregar al arreglo de la clase
	 */
	public ListaBuques( @JsonProperty(value="buques")List<Buque> buques){
		this.buques = buques;
	}

	/**
	 * Método que retorna la lista de buques
	 * @return  List - List con los buques
	 */
	public List<Buque> getBuquees() {
		return buques;
	}

	/**
	 * Método que asigna la lista de buques que entra como parametro
	 * @param  buques - List con los buques ha agregar
	 */
	public void setBuque(List<Buque> buques) {
		this.buques = buques;
	}
}
