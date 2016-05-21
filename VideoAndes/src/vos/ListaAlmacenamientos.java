package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaAlmacenamientos {

	/**
	 * List con los almacenamientos
	 */
	@JsonProperty(value="silos")
	private List<Silo> silos;
	
	/**
   * List con los almacenamientos
   */
  @JsonProperty(value="cobertizos")
  private List<Cobertizo> cobertizos;
  
  /**
   * List con los almacenamientos
   */
  @JsonProperty(value="bodegas")
  private List<Bodega> bodegas;
  
  /**
   * List con los almacenamientos
   */
  @JsonProperty(value="patios")
  private List<Patio> patios;
	
	/**
	 * Constructor de la clase ListaAlmacenamientos
	 * @param almacenamientos - almacenamientos para agregar al arreglo de la clase
	 */
	public ListaAlmacenamientos(List<Silo> silos, List<Cobertizo> cobertizos, List<Bodega> bodegas, List<Patio> patios){
		this.silos = silos;
		this.bodegas = bodegas;
		this.cobertizos = cobertizos;
		this.patios = patios;
	}

	/**
	 * Método que retorna la lista de almacenamientos
	 * @return  List - List con los almacenamientos
	 */
	public List<Silo> getSilos() {
		return silos;
	}

	/**
	 * Método que asigna la lista de almacenamientos que entra como parametro
	 * @param  almacenamientos - List con los almacenamientos ha agregar
	 */
	public void setSilos(List<Silo> silos) {
		this.silos = silos;
	}
	
	/**
   * Método que retorna la lista de almacenamientos
   * @return  List - List con los almacenamientos
   */
  public List<Patio> getPatios() {
    return patios;
  }

  /**
   * Método que asigna la lista de almacenamientos que entra como parametro
   * @param  almacenamientos - List con los almacenamientos ha agregar
   */
  public void setPatios(List<Patio> patios) {
    this.patios = patios;
  }
  

  /**
   * Método que retorna la lista de almacenamientos
   * @return  List - List con los almacenamientos
   */
  public List<Bodega> getBodegas() {
    return bodegas;
  }

  /**
   * Método que asigna la lista de almacenamientos que entra como parametro
   * @param  almacenamientos - List con los almacenamientos ha agregar
   */
  public void setBodegas(List<Bodega> bodegas) {
    this.bodegas = bodegas;
  }
  

  /**
   * Método que retorna la lista de almacenamientos
   * @return  List - List con los almacenamientos
   */
  public List<Cobertizo> getCobertizos() {
    return cobertizos;
  }

  /**
   * Método que asigna la lista de almacenamientos que entra como parametro
   * @param  almacenamientos - List con los almacenamientos ha agregar
   */
  public void setCobertizos(List<Cobertizo> cobertizos) {
    this.cobertizos = cobertizos;
  }
}
