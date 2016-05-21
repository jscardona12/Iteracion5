/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: personaAndes
 * Autor: Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import vos.Persona;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicación
 * @author Juan
 */
public class DAOTablaPersona extends DAOTablaGenerica{

//	/**
//	 * Método que, usando la conexión a la base de datos, saca todos los personas de la base de datos
//	 * <b>SQL Statement:</b> SELECT * FROM personaS;
//	 * @return Arraylist con los personas de la base de datos.
//	 * @throws SQLException - Cualquier error que la base de datos arroje.
//	 * @throws Exception - Cualquier error que no corresponda a la base de datos
//	 */
//	public ArrayList<persona> darpersonas() throws SQLException, Exception {
//		ArrayList<persona> personas = new ArrayList<persona>();
//
//		String sql = "SELECT * FROM PERSONAS";
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		ResultSet rs = prepStmt.executeQuery();
//
//		while (rs.next()) {
//			String name = rs.getString("NAME");
//			int id = Integer.parseInt(rs.getString("ID"));
//			int duration = Integer.parseInt(rs.getString("DURATION"));
//			personas.add(new persona(id, name, duration));
//		}
//		return personas;
//	}

//
//	/**
//	 * Método que busca el/los personas con el nombre que entra como parámetro.
//	 * @param name - Nombre de el/los personas a buscar
//	 * @return Arraylist con los personas encontrados
//	 * @throws SQLException - Cualquier error que la base de datos arroje.
//	 * @throws Exception - Cualquier error que no corresponda a la base de datos
//	 */
//	public ArrayList<Persona> buscarpersonasPorName(String name) throws SQLException, Exception {
//		ArrayList<Persona> personas = new ArrayList<Persona>();
//
//		String sql = "SELECT * FROM PERSONAS, EXPORTADORES, IMPORTADORES WHERE NAME ='" + name + "'"
//				;
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
//			int esNatural = Integer.parseInt(rs.getString("ESNATURAL"));
//			personas.add(new Persona(id, name2, esNatural));
//		}
//
//		return personas;
//	}
	
	/**
	 * Método que actualiza la persona que entra como parámetro en la base de datos.
	 * @param persona - la persona a actualizar. persona !=  null
	 * <b> post: </b> se ha actualizado la persona en la base de datos en la transaction actual. pendiente que la persona master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar la persona.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updatePersona(Persona persona) throws SQLException, Exception {

		String sql = "UPDATE PERSONAS SET ";
		sql += "id='" + persona.getID() + "',";
		sql += "name='" + persona.getNombre() + "',";
		sql += "esNatural=" + persona.esNatural();
		sql += " WHERE id = " + persona.getID();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Método que elimina la persona que entra como parámetro en la base de datos.
	 * @param persona - la persona a borrar. persona !=  null
	 * <b> post: </b> se ha borrado la persona en la base de datos en la transaction actual. pendiente que la persona master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar la persona.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deletePersona(Persona persona) throws SQLException, Exception {

		String sql = "DELETE FROM personaS";
		sql += " WHERE id = " + persona.getID();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Método que agrega la persona que entra como parámetro a la base de datos.
	 * @param imp - la persona a agregar. imp !=  null
	 * <b> post: </b> se ha agregado la persona a la base de datos en la transaction actual. pendiente que la persona master
	 * haga commit para que la persona baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar la persona a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addPersona(Persona imp) throws SQLException, Exception {

		String sql = "INSERT INTO PERSONAS VALUES (";
		sql += imp.getID() + ",";
		sql += (imp.esNatural() ? 1 : 0) + ",'";
		sql += imp.getNombre() + "')";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public String darTipoPersona(int id) throws SQLException, Exception {
	  String sql = "SELECT * FROM EXPORTADORES WHERE ID = " + id;

    System.out.println("SQL stmt:" + sql);
  
    PreparedStatement prepStmt = conn.prepareStatement(sql);
    recursos.add(prepStmt);
    ResultSet rs = prepStmt.executeQuery();
  
    if (rs.next()) { return Persona.EXPORTADOR; }
    
    sql = "SELECT * FROM IMPORTADORES WHERE ID = " + id;

    System.out.println("SQL stmt:" + sql);
  
    PreparedStatement prepStmt2 = conn.prepareStatement(sql);
    recursos.add(prepStmt2);
    rs = prepStmt2.executeQuery();
  
    if (rs.next()) { return Persona.IMPORTADOR; }
    
    return null;

	}
}
