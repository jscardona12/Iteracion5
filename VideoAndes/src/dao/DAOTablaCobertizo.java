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

import vos.Cobertizo;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicación
 * @author Juan
 */
public class DAOTablaCobertizo extends DAOTablaGenerica{

	/**
	 * Método que, usando la conexión a la base de datos, saca todos los cobertizos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM COBERTIZOS;
	 * @return Arraylist con los cobertizos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Cobertizo> darCobertizos() throws SQLException, Exception {
		ArrayList<Cobertizo> cobertizos = new ArrayList<Cobertizo>();

		String sql = "SELECT * FROM COBERTIZOS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NAME");
			int id = Integer.parseInt(rs.getString("ID"));
			int duration = Integer.parseInt(rs.getString("DURATION"));
//			cobertizos.add(new Cobertizo(id, name, duration));
		}
		return cobertizos;
	}

	/**
	 * Método que agrega el cobertizo que entra como parámetro a la base de datos.
	 * @param cobertizo - el cobertizo a agregar. cobertizo !=  null
	 * <b> post: </b> se ha agregado el cobertizo a la base de datos en la transaction actual. pendiente que el cobertizo master
	 * haga commit para que el cobertizo baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el cobertizo a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addCobertizo(Cobertizo cobertizo) throws SQLException, Exception {

//		String sql = "INSERT INTO COBERTIZOS VALUES (";
//		sql += cobertizo.getID() + ",";
//		sql += cobertizo.getDimension() + ",";
//
//		System.out.println("SQL stmt:" + sql);
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		prepStmt.executeQuery();

	}
	
	/**
	 * Método que actualiza el cobertizo que entra como parámetro en la base de datos.
	 * @param cobertizo - el cobertizo a actualizar. cobertizo !=  null
	 * <b> post: </b> se ha actualizado el cobertizo en la base de datos en la transaction actual. pendiente que el cobertizo master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el cobertizo.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateCobertizo(Cobertizo cobertizo) throws SQLException, Exception {

//		String sql = "UPDATE COBERTIZOS SET ";
//		sql += "dimension=" + cobertizo.getDimension() + ",";
//		sql += " WHERE id = " + cobertizo.getID();
//
//		System.out.println("SQL stmt:" + sql);
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		prepStmt.executeQuery();
	}

	/**
	 * Método que elimina el cobertizo que entra como parámetro en la base de datos.
	 * @param cobertizo - el cobertizo a borrar. cobertizo !=  null
	 * <b> post: </b> se ha borrado el cobertizo en la base de datos en la transaction actual. pendiente que el cobertizo master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el cobertizo.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteCobertizo(Cobertizo cobertizo) throws SQLException, Exception {

		String sql = "DELETE FROM COBERTIZOS";
		sql += " WHERE id = " + cobertizo.getID();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
