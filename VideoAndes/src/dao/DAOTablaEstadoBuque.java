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
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicación
 * @author Juan
 */
public class DAOTablaEstadoBuque extends DAOTablaGenerica{


	public void setEstado(String estado, int id) throws Exception{
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String fechaFormato = "TO_DATE('"+ sdf.format(new Date()) +"', 'yyyy/mm/dd hh24:mi:ss')";
		String sql = "INSERT INTO ESTADOBUQUE "
				+ "VALUES ("+id+", '"+estado+"', "+fechaFormato+")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}
