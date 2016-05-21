/**-------------------------------------------------------------------
 * $ID$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: SiloAndes
 * Autor: Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Silo;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicación
 * @author Juan
 */
public class DAOTablaSilo extends DAOTablaGenerica{

	/**
	 * Método que, usando la conexión a la base de datos, saca todos los silos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM SILOS;
	 * @return Arraylist con los silos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Silo> darSilos() throws SQLException, Exception {
		ArrayList<Silo> silos = new ArrayList<Silo>();

		String sql = "SELECT * FROM SILOS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NAME");
			int id = Integer.parseInt(rs.getString("ID"));
			int duration = Integer.parseInt(rs.getString("DURATION"));
//			silos.add(new Silo(id, name, duration));
		}
		return silos;
	}


	/**
	 * Método que busca el/los silos con el nombre que entra como parámetro.
	 * @param name - Nombre de el/los silos a buscar
	 * @return Arraylist con los silos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Silo> buscarSilosPorName(String name) throws SQLException, Exception {
		ArrayList<Silo> silos = new ArrayList<Silo>();

		String sql = "SELECT * FROM SILOS WHERE NAME ='" + name + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name2 = rs.getString("NAME");
			int id = Integer.parseInt(rs.getString("ID"));
			int duration = Integer.parseInt(rs.getString("DURATION"));
//			silos.add(new Silo(id, name2, duration));
		}

		return silos;
	}

	/**
	 * Método que agrega el silo que entra como parámetro a la base de datos.
	 * @param silo - el silo a agregar. silo !=  null
	 * <b> post: </b> se ha agregado el silo a la base de datos en la transaction actual. pendiente que el silo master
	 * haga commit para que el silo baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el silo a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addSilo(Silo silo) throws SQLException, Exception {

		String sql = "INSERT INTO SILOS VALUES (";
		sql += silo.getID() + ",";
		sql += "'"+silo.getNombre() + "',";
		sql += silo.getCapacidad() + ")";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Método que actualiza el silo que entra como parámetro en la base de datos.
	 * @param silo - el silo a actualizar. silo !=  null
	 * <b> post: </b> se ha actualizado el silo en la base de datos en la transaction actual. pendiente que el silo master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el silo.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateSilo(Silo silo) throws SQLException, Exception {

		String sql = "UPDATE SILOS SET ";
		sql += "nombre='" + silo.getNombre() + "',";
		sql += "capacidad=" + silo.getCapacidad();
		sql += " WHERE id = " + silo.getID();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Método que elimina el silo que entra como parámetro en la base de datos.
	 * @param silo - el silo a borrar. silo !=  null
	 * <b> post: </b> se ha borrado el silo en la base de datos en la transaction actual. pendiente que el silo master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el silo.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteSilo(Silo silo) throws SQLException, Exception {

		String sql = "DELETE FROM SILOS";
		sql += " WHERE id = " + silo.getID();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Método que busca el/los silos mas alquilados.
	 * @return Arraylist con los silos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Silo> darSiloMasAlquilado()  throws SQLException, Exception {
		ArrayList<Silo> silos = new ArrayList<Silo>();

		String sql = "SELECT * " +
					 "FROM SILOS " +
				     "WHERE SILOS.ID IN (SELECT SILO_ID " +
				                         "FROM ALQUILERES " +
				                         "GROUP BY SILO_ID " +
				                         "HAVING COUNT(*) = (SELECT MAX(COUNT(*)) " +
				                                            "FROM ALQUILERES " +
				                                            "GROUP BY SILO_ID)) ";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NAME");
			int id = Integer.parseInt(rs.getString("ID"));
			int duration = Integer.parseInt(rs.getString("DURATION"));
//			silos.add(new Silo(id, name, duration));
		}

		return silos;
	}
}
