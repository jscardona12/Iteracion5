/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: importadorAndes
 * Autor: Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Importador;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicación
 * @author Juan
 */
public class DAOTablaImportador extends DAOTablaGenerica{



	/**
	 * Método que busca el/los importadors con el nombre que entra como parámetro.
	 * @param name - Nombre de el/los importadors a buscar
	 * @return Arraylist con los importadors encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
//	public ArrayList<importador> buscarimportadorsPorName(String name) throws SQLException, Exception {
//		ArrayList<importador> importadors = new ArrayList<importador>();
//
//		String sql = "SELECT * FROM IMPORTADORES WHERE NAME ='" + name + "'";
//
//		System.out.println("SQL stmt:" + sql);
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		ResultSet rs = prepStmt.executeQuery();
//
//		while (rs.next()) {
//			String name2 = rs.getString("NAME");
//			int id = Integer.parseInt(rs.getString("ID"));
//			int duration = Integer.parseInt(rs.getString("DURATION"));
//			importadors.add(new Importador(id, name2, duration));
//		}
//
//		return importadors;
//	}
	
	/**
	 * Método que actualiza el importador que entra como parámetro en la base de datos.
	 * @param importador - el importador a actualizar. importador !=  null
	 * <b> post: </b> se ha actualizado el importador en la base de datos en la transaction actual. pendiente que el importador master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el importador.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateImportador(Importador importador) throws SQLException, Exception {

		String sql = "UPDATE IMPORTADORES SET ";
		sql += "registroAduana='" + importador.getRegistroAduana() + "',";
		sql += "tipoHabitual=" + importador.esTipoHabitual();
		sql += " WHERE id = " + importador.getID();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Método que elimina el importador que entra como parámetro en la base de datos.
	 * @param importador - el importador a borrar. importador !=  null
	 * <b> post: </b> se ha borrado el importador en la base de datos en la transaction actual. pendiente que el importador master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el importador.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteImportador(Importador importador) throws SQLException, Exception {

		String sql = "DELETE FROM IMPORTADORES, PERSONAS";
		sql += " WHERE id = " + importador.getID();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

//	/**
//	 * Método que busca el/los importadors mas alquilados.
//	 * @return Arraylist con los importadors encontrados
//	 * @throws SQLException - Cualquier error que la base de datos arroje.
//	 * @throws Exception - Cualquier error que no corresponda a la base de datos
//	 */
//	public ArrayList<importador> darimportadorMasAlquilado()  throws SQLException, Exception {
//		ArrayList<importador> importadors = new ArrayList<importador>();
//
//		String sql = "SELECT * " +
//					 "FROM IMPORTADORES " +
//				     "WHERE IMPORTADORES.ID IN (SELECT importador_ID " +
//				                         "FROM ALQUILERES " +
//				                         "GROUP BY importador_ID " +
//				                         "HAVING COUNT(*) = (SELECT MAX(COUNT(*)) " +
//				                                            "FROM ALQUILERES " +
//				                                            "GROUP BY importador_ID)) ";
//
//		System.out.println("SQL stmt:" + sql);
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		ResultSet rs = prepStmt.executeQuery();
//
//		while (rs.next()) {
//			String name = rs.getString("NAME");
//			int id = Integer.parseInt(rs.getString("ID"));
//			int duration = Integer.parseInt(rs.getString("DURATION"));
//			importadors.add(new Importador(id, name, duration));
//		}
//
//		return importadors;
//	}

	/*
	 * SENTENCIAS EN SQL
	 * R1: REGISTRAR IMPORTADOR O EXPORTADOR 
	 * INSERT INTO PERSONA (DEPARTMENT_ID,DEPARTMENT_NAME) 
 	   Values (300, 'Research'); 
	 */
	/**
	 * Método que agrega el importaodr que entra como parámetro a la base de datos.
	 * @param imp - el importador a agregar. imp !=  null
	 * <b> post: </b> se ha agregado el importador a la base de datos en la transaction actual. pendiente que el importador master
	 * haga commit para que el importador baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el importador a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addImportador(Importador imp) throws SQLException, Exception {

		String sql = "INSERT INTO IMPORTADORES"
		    + " (ID, REGISTROADUANA, TIPOHABITUAL)"
		    + " VALUES (";
		sql += imp.getID() + ", '";
		sql += imp.getRegistroAduana() + "', ";
		sql += (imp.esTipoHabitual() ? 1 : 0) + ")";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public ArrayList<Importador> darImportadores() throws Exception {
		ArrayList<Importador> importadores = new ArrayList<Importador>();

		String sql = "SELECT * FROM IMPORTADORES imp, PERSONAS pe WHERE imp.ID=pe.ID";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NOMBRE");
			int id = Integer.parseInt(rs.getString("ID"));
			String rAduana = rs.getString("REGISTROADUANA");
			boolean tipoH = Integer.parseInt(rs.getString("TIPOHABITUAL"))==1;
			boolean esNatural = Integer.parseInt(rs.getString("ESNATURAL"))==1;
			importadores.add(new Importador(name, id, esNatural, rAduana, tipoH));
		}
		return importadores;
	}
}
