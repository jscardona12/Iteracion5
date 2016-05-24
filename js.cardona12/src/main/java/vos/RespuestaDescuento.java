package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class RespuestaDescuento {

  @JsonProperty(value="descuento")
  private int descuento;
  @JsonProperty(value="exitoso")
  private boolean success;
  @JsonProperty(value="mensaje")
  private String message;
  
  public void setSuccess(boolean success) {
    this.success = success;
  }

  public void setMessage(String message) {
    this.message = message;
  }
  
  public void setDescuento(int descuento) {
    this.descuento = descuento;
  }
}
