package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class InfoExportador extends Exportador{
	/**
	 * atributo cantidad uso
	 */
	@JsonProperty(value="cantidadUso")
	private int cantidadUso;
	/**
	 * atributo de cargas
	 */
	@JsonProperty(value="cargas")
	private ListaCargas cargas;
	
	
	/**
	 * Constructor
	 * @param nombre
	 * @param id
	 * @param esNatural
	 * @param rut
	 * @param cantidadUso
	 */
	public InfoExportador(@JsonProperty(value="nombre")      String nombre,
						  @JsonProperty(value="id")          int id,
						  @JsonProperty(value="tipoNatural") boolean esNatural,
						  @JsonProperty(value="rut")         String rut,
						  @JsonProperty(value="cantidadUso")int cantidadUso) {
		super(nombre, id, esNatural, rut);
		this.cantidadUso=cantidadUso;

	}
	/**
	 * 
	 * @return cantidad uso
	 */
	public int getCantidadUso() {
		return cantidadUso;
	}
	/**
	 * Meotdo que modifica la cantidad de uso
	 * @param cantidadUso
	 */
	public void setCantidadUso(int cantidadUso) {
		this.cantidadUso = cantidadUso;
	}
	/**
	 * getter de cargas
	 * @return cargas
	 */
	public ListaCargas getCargas() {
		return cargas;
	}
	/**
	 * setter de cargas
	 * @param cargas
	 */
	public void setCargas(ListaCargas cargas) {
		this.cargas = cargas;
	}
}
