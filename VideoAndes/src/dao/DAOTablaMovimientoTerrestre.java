package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import vos.CargaEnAlmacen;

public class DAOTablaMovimientoTerrestre extends DAOTablaGenerica {

  /**
   * Método que, usando la conexión a la base de datos, saca todos los cargasAlmacenadas de la base de datos
   * <b>SQL Statement:</b> SELECT * FROM CARGA_ALMACENADAS;
   * @return Arraylist con los cargasAlmacenadas de la base de datos.
   * @throws SQLException - Cualquier error que la base de datos arroje.
   * @throws Exception - Cualquier error que no corresponda a la base de datos
   */
  public ArrayList<CargaEnAlmacen> darCargasEnAlmacen() throws SQLException, Exception {
    ArrayList<CargaEnAlmacen> cargas = new ArrayList<CargaEnAlmacen>();

    String sql = "SELECT * FROM CARGAENALMACEN";

    PreparedStatement prepStmt = conn.prepareStatement(sql);
    recursos.add(prepStmt);
    ResultSet rs = prepStmt.executeQuery();

    while (rs.next()) {
      int idCarga = Integer.parseInt(rs.getString("ID_CARGA"));
      int idAlmacen = Integer.parseInt(rs.getString("ID_ALMACEN"));
      cargas.add(new CargaEnAlmacen(idCarga, idAlmacen));
    }
    return cargas;
  }


  /**
   * Método que agrega el cargaAlmacenada que entra como parámetro a la base de datos.
   * @param cargaAlmacenada - el cargaAlmacenada a agregar. cargaAlmacenada !=  null
   * <b> post: </b> se ha agregado el cargaAlmacenada a la base de datos en la transaction actual. pendiente que el cargaAlmacenada master
   * haga commit para que el cargaAlmacenada baje  a la base de datos.
   * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el cargaAlmacenada a la base de datos
   * @throws Exception - Cualquier error que no corresponda a la base de datos
   */
  public void addMovimientoTerrestre(CargaEnAlmacen carga, String tipo) throws SQLException, Exception {

    String sql = "INSERT INTO MOVIMIENTOTERRESTRE VALUES (";
    sql += carga.getIdCarga() + ", '";
    sql += tipo + "', ";
    sql += " ? ,";
    sql += carga.getIdAlmacen() + ")";

    System.out.println("SQL stmt:" + sql);

    PreparedStatement prepStmt = conn.prepareStatement(sql);
    prepStmt.setTimestamp(1, new Timestamp((new Date()).getTime()));
    recursos.add(prepStmt);
    prepStmt.executeQuery();

  }
  
  /**
   * Método que elimina el video que entra como parámetro en la base de datos.
   * @param video - el video a borrar. video !=  null
   * <b> post: </b> se ha borrado el video en la base de datos en la transaction actual. pendiente que el video master
   * haga commit para que los cambios bajen a la base de datos.
   * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
   * @throws Exception - Cualquier error que no corresponda a la base de datos
   */
  public void deleteCargaEnAlmacen(int idCarga) throws SQLException, Exception {

    String sql = "DELETE FROM CARGAENALMACEN ";
    sql += "WHERE ID_CARGA=" + idCarga;

    System.out.println("SQL stmt:" + sql);

    PreparedStatement prepStmt = conn.prepareStatement(sql);
    recursos.add(prepStmt);
    prepStmt.executeQuery();

  }
  
}

