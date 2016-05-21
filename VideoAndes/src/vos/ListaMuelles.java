package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaMuelles {

  /**
   * List con los exportadores
   */
  @JsonProperty(value="muelles")
  private List<Muelle> muelles;
  
  /**
   * Constructor de la clase ListaExportadors
   * @param exportadores - exportadores para agregar al arreglo de la clase
   */
  public ListaMuelles(@JsonProperty(value="muelles")List<Muelle> muelles){
    this.muelles = muelles;
  }

  /**
   * Método que retorna la lista de exportadores
   * @return  List - List con los exportadores
   */
  public List<Muelle> getMuelles() {
    return muelles;
  }

  /**
   * Método que asigna la lista de exportadores que entra como parametro
   * @param  exportadores - List con los exportadores ha agregar
   */
  public void setMuelle(List<Muelle> muelles) {
    this.muelles = muelles;
  }
  
}
