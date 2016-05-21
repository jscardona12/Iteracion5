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
import java.sql.SQLException;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicación
 * @author Juan
 */
public class DAOTablaMuelleOcupado extends DAOTablaGenerica{

  /**
   * Método que agrega el exportador que entra como parámetro a la base de datos.
   * @param exportador - el exportador a agregar. video !=  null
   * <b> post: </b> se ha agregado el exportador a la base de datos en la transaction actual.
   * pendiente que el video master
   * haga commit para que el exportador baje  a la base de datos.
   * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
   * @throws Exception - Cualquier error que no corresponda a la base de datos
   */
  public void addMuelleOcupado(int idMuelle, int idBuque) throws SQLException, Exception {

    String sql = "INSERT INTO MUELLEOCUPADO VALUES (";
    sql += idMuelle + ", ";
    sql += idBuque + ")";

    System.out.println("SQL stmt:" + sql);

    PreparedStatement prepStmt = conn.prepareStatement(sql);
    recursos.add(prepStmt);
    prepStmt.executeQuery();

  }

  /**
   * Método que elimina el arribo que entra como parámetro en la base de datos.
   * @param arribo - el arribo a borrar. arribo !=  null
   * <b> post: </b> se ha borrado el arribo en la base de datos en la transaction actual. pendiente que el arribo master
   * haga commit para que los cambios bajen a la base de datos.
   * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el arribo.
   * @throws Exception - Cualquier error que no corresponda a la base de datos
   */
  public void deleteMuelleOcupado(int idMuelle) throws SQLException, Exception {

    String sql = "DELETE FROM MUELLEOCUPADO";
    sql += " WHERE id_muelle = " + idMuelle;

    System.out.println("SQL stmt:" + sql);

    PreparedStatement prepStmt = conn.prepareStatement(sql);
    recursos.add(prepStmt);
    prepStmt.executeQuery();
  }
}
