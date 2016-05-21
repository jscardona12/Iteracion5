package vos;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;
//import org.codehaus.jackson.annotate.JsonFormat;

public class MovimientoCarga {

    @JsonProperty(value="idCarga")
    private int idCarga;
    
    @JsonProperty(value="destino")
    private String destino;
    
    @JsonProperty(value="origen")
    private String origen;

//    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm a z")
    @JsonProperty(value="fechaMovimiento")
    private Date fechaMovimiento;
    
    @JsonProperty(value="idExportador")
    private int idExportador;
    
    @JsonProperty(value="idImportador")
    private int idImportador;

    @JsonProperty(value="tipoCarga")
    private String tipoCarga;
    
    @JsonProperty(value="tipoMovimiento")
    private String tipoMovimiento;
    
    @JsonProperty(value="idBuque")
    private int idBuque;
    
    @JsonProperty(value="idAlmacen")
    private int idAlmacen;

    public MovimientoCarga( int idCarga,
                            String destino,
                            String origen,
                            Date fechaMovimiento,
                            int idExportador,
                            int idImportador,
                            String tipoCarga,
                            String tipoMovimiento,
                            int idBuque,
                            int idAlmacen){
      
      super();
      this.idCarga = idCarga;
      this.destino = destino;
      this.origen = origen;
      this.fechaMovimiento = fechaMovimiento;
      this.idExportador = idExportador;
      this.idImportador = idImportador;
      this.tipoCarga = tipoCarga;
      this.tipoMovimiento = tipoMovimiento;
      this.idBuque = idBuque;
      this.idAlmacen = idAlmacen;
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

    public int getidExportador() {
      return idExportador;
    }

    public int getidImportador() {
      return idImportador;
    }

    public String gettipoCarga() {
      return tipoCarga;
    }

    public String gettipoMovimiento() {
      return tipoMovimiento;
    }

    public int getidBuque() {
      return idBuque;
    }

    public int getidAlmacen() {
      return idAlmacen;
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

    public void setidExportador(int idExportador) {
      this.idExportador = idExportador;
    }

    public void setidImportador(int idImportador) {
      this.idImportador = idImportador;
    }

    public void settipoCarga(String tipoCarga) {
      this.tipoCarga = tipoCarga;
    }

    public void settipoMovimiento(String tipoMovimiento) {
      this.tipoMovimiento = tipoMovimiento;
    }

    public void setidBuque(int idBuque) {
      this.idBuque = idBuque;
    }

    public void setidAlmacen(int idAlmacen) {
      this.idAlmacen = idAlmacen;
    }

}
