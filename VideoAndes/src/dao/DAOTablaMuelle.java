/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: VideoAndes
 * Autor: Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Muelle;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicación
 * @author Juan
 */
public class DAOTablaMuelle extends DAOTablaGenerica{

  /**
   * Método que agrega el exportador que entra como parámetro a la base de datos.
   * @param exportador - el exportador a agregar. video !=  null
   * <b> post: </b> se ha agregado el exportador a la base de datos en la transaction actual.
   * pendiente que el video master
   * haga commit para que el exportador baje  a la base de datos.
   * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
   * @throws Exception - Cualquier error que no corresponda a la base de datos
   */
  public void addMuelle(Muelle muelle) throws SQLException, Exception {

    String sql = "INSERT INTO MUELLES VALUES (";
    sql += muelle.getId() + ", '";
    sql += muelle.getNombre() + "')";

    System.out.println("SQL stmt:" + sql);

    PreparedStatement prepStmt = conn.prepareStatement(sql);
    recursos.add(prepStmt);
    prepStmt.executeQuery();

  }

  public ArrayList<Muelle> darMuelles() throws Exception {
    ArrayList<Muelle> muelles = new ArrayList<Muelle>();

    String sql = "SELECT * FROM MUELLES";

    PreparedStatement prepStmt = conn.prepareStatement(sql);
    recursos.add(prepStmt);
    ResultSet rs = prepStmt.executeQuery();

    while (rs.next()) {
      String name = rs.getString("NOMBRE");
      int id = Integer.parseInt(rs.getString("ID"));
      muelles.add(new Muelle(id, name));
    }
    return muelles;
  }
}
