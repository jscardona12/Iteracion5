package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import vos.CargaEnAlmacen;
import vos.MovimientoCarga;
import vos.Persona;
import vos.Salida;

public class DAOTablaMovimientoMaritimo extends DAOTablaGenerica {

  /**
   * Método que, usando la conexión a la base de datos, saca todos los cargasAlmacenadas de la base de datos
   * <b>SQL Statement:</b> SELECT * FROM CARGA_ALMACENADAS;
   * @return Arraylist con los cargasAlmacenadas de la base de datos.
   * @throws SQLException - Cualquier error que la base de datos arroje.
   * @throws Exception - Cualquier error que no corresponda a la base de datos
   */
  public ArrayList<MovimientoCarga> darMovimientos(int idPersona,
                                                  String rolPersona, 
                                                  Date fechaInicio,
                                                  Date fechaFin) throws SQLException, Exception
  {
    ArrayList<MovimientoCarga> movimientos = new ArrayList<MovimientoCarga>();
    
    String restriccionUsuario = "";
    
    if (rolPersona == Persona.EXPORTADOR) {
      restriccionUsuario = " AND c.ID_EXPORTADOR=" + idPersona;
    } else if (rolPersona == Persona.IMPORTADOR) {
      restriccionUsuario = " AND c.ID_IMPORTADOR=" + idPersona;
    }
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    String restriccionFechaI = fechaInicio == null ? "" : " AND salida.FECHA>='"+sdf.format(fechaInicio)+ "'";
    String restriccionFechaF = fechaFin == null ? "" : " AND salida.FECHA<='"+sdf.format(fechaFin)+ "'";
    
    String sql= "SELECT mm.ID_CARGA, c.ORIGEN, c.DESTINO, mm.FECHA, "
              + "c.ID_EXPORTADOR, c.ID_IMPORTADOR, mm.TIPO AS TIPO_M, "
              + "c.TIPO TIPO_C, mm.ID_BUQUE, mm.ID_ALMACENAMIENTO "
              + "FROM MOVIMIENTOMARITIMO mm, CARGA c "
              + "WHERE mm.ID_CARGA=c.ID "
              + restriccionFechaI + restriccionFechaF + restriccionUsuario;
    
    PreparedStatement prepStmt = conn.prepareStatement(sql);
    recursos.add(prepStmt);
    
    System.out.println(sql);
    
    ResultSet rs = prepStmt.executeQuery();

    while (rs.next()) {
      int idCarga = Integer.parseInt(rs.getString("ID_CARGA"));
      String origen = rs.getString("ORIGEN");
      String destino = rs.getString("DESTINO");
      String fechaMovimiento = rs.getString("FECHA");
      int idExportador = Integer.parseInt(rs.getString("ID_EXPORTADOR"));
      int idImportador = Integer.parseInt(rs.getString("ID_IMPORTADOR"));
      String tipoCarga = rs.getString("TIPO_C");
      String tipoMovimiento = rs.getString("TIPO_M");
      int idBuque = Integer.parseInt(rs.getString("ID_BUQUE"));
      int idAlmacen = Integer.parseInt(rs.getString("ID_ALMACENAMIENTO"));
      MovimientoCarga mov = new MovimientoCarga(idCarga,
          destino,
          origen,
          sdf.parse(fechaMovimiento),
          idExportador,
          idImportador,
          tipoCarga,
          tipoMovimiento,
          idBuque,
          idAlmacen);
      movimientos.add(mov);
    }

    return movimientos;
  }


  /**
   * Método que agrega el cargaAlmacenada que entra como parámetro a la base de datos.
   * @param cargaAlmacenada - el cargaAlmacenada a agregar. cargaAlmacenada !=  null
   * <b> post: </b> se ha agregado el cargaAlmacenada a la base de datos en la transaction actual. pendiente que el cargaAlmacenada master
   * haga commit para que el cargaAlmacenada baje  a la base de datos.
   * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el cargaAlmacenada a la base de datos
   * @throws Exception - Cualquier error que no corresponda a la base de datos
   */
  public void addMovimientoMaritimo(int cargaID, int almacenID, int buqueID, String tipo) throws SQLException, Exception {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    String fecha = "TO_DATE('" + sdf.format(new Date()) + "', 'yyyy/mm/dd hh24:mi:ss')";
    
    String sql = "INSERT INTO MOVIMIENTOMARITIMO VALUES (";
    sql += cargaID + ", ";
    sql += fecha + ", ";
    sql += buqueID + ", '";
    sql += tipo + "',";
    sql += almacenID + ")";

    System.out.println("SQL stmt:" + sql);

    PreparedStatement prepStmt = conn.prepareStatement(sql);
    recursos.add(prepStmt);
    prepStmt.executeQuery();

  }
  
}

