package vos;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;
//import org.codehaus.jackson.annotate.JsonFormat;

public class MovimientoAlmacen {

    @JsonProperty(value="idCarga")
    private int idCarga;
    
    @JsonProperty(value="destino")
    private String destino;
    
    @JsonProperty(value="origen")
    private String origen;

    @JsonProperty(value="fechaMovimiento")
    private Date fechaMovimiento;

    @JsonProperty(value="tipoCarga")
    private String tipoCarga;
    
    @JsonProperty(value="tipoMovimiento")
    private String tipoMovimiento;
    
    @JsonProperty(value="idAlmacenamiento")
    private int idAlmacenamiento;

   

	public MovimientoAlmacen( int idCarga,
                            String destino,
                            String origen,
                            Date fechaMovimiento,
                            String tipoCarga, String tipoMovimiento, int idAlmacenamiento){
      this.idCarga = idCarga;
      this.destino = destino;
      this.origen = origen;
      this.fechaMovimiento = fechaMovimiento;
      this.tipoCarga = tipoCarga;
      this.tipoMovimiento=tipoMovimiento;
      this.idAlmacenamiento=idAlmacenamiento;
    }

    public int getidCarga() {
      return idCarga;
    }

    public String getdestino() {
      return destino;
    }

    public String getorigen() {
      return origen;
    }

    public Date getfechaMovimiento() {
      return fechaMovimiento;
    }

    public String gettipoCarga() {
      return tipoCarga;
    }

    public void setidCarga(int idCarga) {
      this.idCarga = idCarga;
    }

    public void setdestino(String destino) {
      this.destino = destino;
    }

    public void setorigen(String origen) {
      this.origen = origen;
    }

    public void setfechaMovimiento(Date fechaMovimiento) {
      this. fechaMovimiento = fechaMovimiento;
    }

    public void setTipoCarga(String tipoCarga) {
      this.tipoCarga = tipoCarga;
    }
    
    public String getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public int getIdAlmacenamiento() {
		return idAlmacenamiento;
	}

	public void setIdAlmacenamiento(int idAlmacenamiento) {
		this.idAlmacenamiento = idAlmacenamiento;
	}
}
