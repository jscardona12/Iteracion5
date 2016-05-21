/**-------------------------------------------------------------------
 * $ID$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: BodegaAndes
 * Autor: Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Bodega;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicación
 * @author Juan
 */
public class DAOTablaBodega extends DAOTablaGenerica{

	/**
	 * Método que, usando la conexión a la base de datos, saca todos los bodegas de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM BODEGAS;
	 * @return Arraylist con los bodegas de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Bodega> darBodegas() throws SQLException, Exception {
		ArrayList<Bodega> bodegas = new ArrayList<Bodega>();

		String sql = "SELECT * FROM BODEGAS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NOMBRE");
			int id = Integer.parseInt(rs.getString("ID"));
			int ancho = Integer.parseInt(rs.getString("ANCHO"));
			int largo = Integer.parseInt(rs.getString("LARGO"));
			boolean tienePlataforma = Boolean.parseBoolean(rs.getString("TIENEPLATAFORMA"));
			int separacionColumnas = Integer.parseInt(rs.getString("SEPARACIONCOLUMNAS"));
			bodegas.add(new Bodega(id, largo, ancho, tienePlataforma, separacionColumnas));
		}
		return bodegas;
	}

	/**
	 * Método que agrega el bodega que entra como parámetro a la base de datos.
	 * @param bodega - el bodega a agregar. bodega !=  null
	 * <b> post: </b> se ha agregado el bodega a la base de datos en la transaction actual. pendiente que el bodega master
	 * haga commit para que el bodega baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el bodega a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addBodega(Bodega bodega) throws SQLException, Exception {

		String sql = "INSERT INTO BODEGAS VALUES (";
		sql += bodega.getID() + ",";
		sql += bodega.getAncho() + ",";
		sql += bodega.getLargo() + ",";
		sql += bodega.getTienePlataforma() + ",";
		sql += bodega.getSeparacionColumnas() + ")";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Método que actualiza el bodega que entra como parámetro en la base de datos.
	 * @param bodega - el bodega a actualizar. bodega !=  null
	 * <b> post: </b> se ha actualizado el bodega en la base de datos en la transaction actual. pendiente que el bodega master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el bodega.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateBodega(Bodega bodega) throws SQLException, Exception {

		String sql = "UPDATE BODEGAS SET ";
		sql += "ancho=" + bodega.getAncho() + ",";
		sql += "largo=" + bodega.getLargo() + ",";
		sql += "tienePlataforma=" + bodega.getTienePlataforma() + ",";
		sql += "separacionColumnas=" + bodega.getSeparacionColumnas();
		sql += " WHERE id = " + bodega.getID();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Método que elimina el bodega que entra como parámetro en la base de datos.
	 * @param bodega - el bodega a borrar. bodega !=  null
	 * <b> post: </b> se ha borrado el bodega en la base de datos en la transaction actual. pendiente que el bodega master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el bodega.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteBodega(Bodega bodega) throws SQLException, Exception {

		String sql = "DELETE FROM BODEGAS";
		sql += " WHERE id = " + bodega.getID();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}
