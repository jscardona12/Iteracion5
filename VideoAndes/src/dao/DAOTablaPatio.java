/**-------------------------------------------------------------------
 * $ID$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: PatioAndes
 * Autor: Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Patio;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicación
 * @author Juan
 */
public class DAOTablaPatio extends DAOTablaGenerica{


	/**
	 * Método que, usando la conexión a la base de datos, saca todos los patios de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM PATIOS;
	 * @return Arraylist con los patios de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Patio> darPatios() throws SQLException, Exception {
		ArrayList<Patio> patios = new ArrayList<Patio>();

		String sql = "SELECT * FROM PATIOS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NAME");
			int id = Integer.parseInt(rs.getString("ID"));
			int duration = Integer.parseInt(rs.getString("DURATION"));
//			patios.add(new Patio(id, name, duration));
		}
		return patios;
	}


	/**
	 * Método que busca el/los patios con el nombre que entra como parámetro.
	 * @param name - Nombre de el/los patios a buscar
	 * @return Arraylist con los patios encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Patio> buscarPatiosPorName(String name) throws SQLException, Exception {
		ArrayList<Patio> patios = new ArrayList<Patio>();

		String sql = "SELECT * FROM PATIOS WHERE NAME ='" + name + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name2 = rs.getString("NAME");
			int id = Integer.parseInt(rs.getString("ID"));
			int duration = Integer.parseInt(rs.getString("DURATION"));
//			patios.add(new Patio(id, name2, duration));
		}

		return patios;
	}

	/**
	 * Método que agrega el patio que entra como parámetro a la base de datos.
	 * @param patio - el patio a agregar. patio !=  null
	 * <b> post: </b> se ha agregado el patio a la base de datos en la transaction actual. pendiente que el patio master
	 * haga commit para que el patio baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el patio a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addPatio(Patio patio) throws SQLException, Exception {

//		String sql = "INSERT INTO PATIOS VALUES (";
//		sql += patio.getID() + ",";
//		sql += patio.getDimension() + ",";
//
//		System.out.println("SQL stmt:" + sql);
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		prepStmt.executeQuery();

	}
	
	/**
	 * Método que actualiza el patio que entra como parámetro en la base de datos.
	 * @param patio - el patio a actualizar. patio !=  null
	 * <b> post: </b> se ha actualizado el patio en la base de datos en la transaction actual. pendiente que el patio master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el patio.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updatePatio(Patio patio) throws SQLException, Exception {

//		String sql = "UPDATE PATIOS SET ";
//		sql += "dimension=" + patio.getDimension() + ",";
//		sql += " WHERE id = " + patio.getID();
//
//		System.out.println("SQL stmt:" + sql);
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		prepStmt.executeQuery();
	}

	/**
	 * Método que elimina el patio que entra como parámetro en la base de datos.
	 * @param patio - el patio a borrar. patio !=  null
	 * <b> post: </b> se ha borrado el patio en la base de datos en la transaction actual. pendiente que el patio master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el patio.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deletePatio(Patio patio) throws SQLException, Exception {

		String sql = "DELETE FROM PATIOS";
		sql += " WHERE id = " + patio.getID();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Método que busca el/los patios mas alquilados.
	 * @return Arraylist con los patios encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Patio> darPatioMasAlquilado()  throws SQLException, Exception {
		ArrayList<Patio> patios = new ArrayList<Patio>();

		String sql = "SELECT * " +
					 "FROM PATIOS " +
				     "WHERE PATIOS.ID IN (SELECT PATIO_ID " +
				                         "FROM ALQUILERES " +
				                         "GROUP BY PATIO_ID " +
				                         "HAVING COUNT(*) = (SELECT MAX(COUNT(*)) " +
				                                            "FROM ALQUILERES " +
				                                            "GROUP BY PATIO_ID)) ";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NAME");
			int id = Integer.parseInt(rs.getString("ID"));
			int duration = Integer.parseInt(rs.getString("DURATION"));
//			patios.add(new Patio(id, name, duration));
		}

		return patios;
	}

}
