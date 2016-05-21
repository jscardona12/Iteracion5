package vos;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class Factura {

  @JsonProperty(value="idBuque")
  private int idBuque;
    
  public int getIdBuque() {
    return idBuque;
  }


  public void setIdBuque(int idBuque) {
    this.idBuque = idBuque;
  }

  @JsonProperty(value="fecha")
  private Date fecha;
  
  public Date getFecha() {
    return fecha;
  }


  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }


  @JsonProperty(value="valorTotal")
  private int valorTotal;
    
     
  public int getValorTotal() {
    return valorTotal;
  }


  public void setValorTotal(int valorTotal) {
    this.valorTotal = valorTotal;
  }


  public Factura(@JsonProperty(value="idBuque") int idBuque,
                 @JsonProperty(value="fecha") Date fecha) throws Exception {
    this.idBuque = idBuque;
    this.fecha = fecha;
  }
  
}
