/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: BuqueAndes
 * Autor: Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import vos.Almacenamiento;
import vos.Buque;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicación
 * @author Juan
 */
public class DAOTablaBuqueDeshabilitado extends DAOTablaGenerica{

	public boolean esHabilitado(int id) throws Exception{
		boolean resp = true;
		  String sql = "SELECT * "
	               + "FROM BUQUESDESHABILITADOS "
	               + "WHERE ID_BUQUE=" + id+" AND FECHA_FIN IS NULL";

	    PreparedStatement prepStmt = conn.prepareStatement(sql);
	    recursos.add(prepStmt);
	    ResultSet rs = prepStmt.executeQuery();
	    while (rs.next()) {
	      resp = false;
	    }
	    return resp;
	}
	public void registrarDeshabilidad(Buque b, String razon) throws SQLException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String fecha = "TO_DATE('"+ sdf.format(new Date()) +"', 'yyyy/mm/dd hh24:mi:ss')";
		String sql = "INSERT INTO BUQUESDESHABILITADOS VALUES (";
		sql += b.getID() + ", '"+razon+"',"+fecha+", null )";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}
