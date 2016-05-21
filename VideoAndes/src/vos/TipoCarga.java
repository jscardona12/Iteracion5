package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class TipoCarga {
	
	public static final String CARGA = "Carga";
	public static final String DESCARGA = "Descarga";
	//--------------------------
	//ATRIBUTOS
	//--------------------------
	/**
	 * Atributo entero que modela el id del tipo carga
	 */
	@JsonProperty(value="idBuque")
	private int idBuque;
	/**
	 * Atributo entero que modela el TipoCarga de la persona
	 */
	@JsonProperty(value="tipoCarga")
	private String tipoCarga;
  
  /**
   * Atributo entero que modela el TipoCarga de la persona
   */
  @JsonProperty(value="esCargaRodada")
  private boolean esCargaRodada;
  
  /**
   * Atributo entero que modela el TipoCarga de la persona
   */
  @JsonProperty(value="esContenedor")
  private boolean esContenedor;

	//--------------------------
	//CONSTRUCTOR
	//--------------------------
	/**
	 * Constructor de la clase abstracta
	 * @param tipoCarga tipoCarga de la persona
	 */
	public TipoCarga(@JsonProperty(value="idBuque")int idBuque,
	                 @JsonProperty(value="tipoCarga")String tipoCarga,
	                 @JsonProperty(value="esCargaRodada")boolean esCargaRodada,
	                 @JsonProperty(value="esContenedor")boolean esContenedor){
		super();
		this.tipoCarga=tipoCarga;
		this.idBuque=idBuque;
		this.esCargaRodada=esCargaRodada;
		this.esContenedor=esContenedor;
	}
	//--------------------------
	//METODOS
	//--------------------------
	/**
	 * Metodo para obtener el TipoCarga
	 * @return TipoCarga
	 */
	public String getTipoCarga(){
		return tipoCarga;
	}
	/**
	 * Metodo para modificar el tipoCarga
	 * @param tipoCarga 
	 */
	public void setTipoCarga(String tipoCarga){
		this.tipoCarga=tipoCarga;
	}
	/**
	 * Metodo para obtener el id
	 * @return TipoCarga
	 */
	public int getIdBuque(){
		return idBuque;
	}
	/**
	 * Metodo para modificar el tipoCarga
	 * @param tipoCarga 
	 */
	public void setIdBuque(int idBuque){
		this.idBuque=idBuque;
	}

	/**
   * Metodo para obtener si es cargaRodada
   * @return esCargaRodada
   */
	public boolean esCargaRodada() {
	  return esCargaRodada;
	}
	
	/**
   * Metodo para modificar cargaRodada
   * @param esCargaRodada 
   */
	public void setCargaRodada(boolean esCargaRodada) {
    this.esCargaRodada = esCargaRodada;
  }
	
	/**
   * Metodo para obtener si es cargaRodada
   * @return esCargaRodada
   */
  public boolean esContenedor() {
    return esContenedor;
  }
  
  /**
   * Metodo para modificar cargaRodada
   * @param esCargaRodada 
   */
  public void esContenedor(boolean esContenedor) {
    this.esContenedor = esContenedor;
  }
}
