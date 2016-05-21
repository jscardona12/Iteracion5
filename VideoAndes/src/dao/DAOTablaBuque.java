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
import java.util.ArrayList;

import vos.Buque;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicación
 * @author Juan
 */
public class DAOTablaBuque extends DAOTablaGenerica{

	/**
	 * Método que, usando la conexión a la base de datos, saca todos los buques de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM BUQUES;
	 * @return Arraylist con los buques de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Buque> darBuques() throws SQLException, Exception {
		ArrayList<Buque> buques = new ArrayList<Buque>();

		String sql = "SELECT * FROM BUQUES";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NOMBRE");
			String registroCapitania = rs.getString("REGISTROCAPITANIA");
			String agenteMaritimo = rs.getString("AGENTEMARITIMO");
			String unidad = rs.getString("UNIDAD");
			String tipo = rs.getString("TIPO");
			int id = Integer.parseInt(rs.getString("ID"));
			int capacidad = Integer.parseInt(rs.getString("CAPACIDAD"));
			String destino = rs.getString("DESTINO");
			buques.add(new Buque(id, registroCapitania, agenteMaritimo, name, unidad, tipo, capacidad,destino));
		}
		return buques;
	}


	/**
	 * Método que busca el/los buques con el nombre que entra como parámetro.
	 * @param name - Nombre de el/los buques a buscar
	 * @return Arraylist con los buques encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Buque> buscarBuquesPorNombre(String name) throws SQLException, Exception {
		ArrayList<Buque> buques = new ArrayList<Buque>();

		String sql = "SELECT * FROM BUQUES WHERE NAME ='" + name + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NAME");
			String unidad = rs.getString("UNIDAD");
			String tipo = rs.getString("TIPO");
			String agenteMaritimo = rs.getString("AGENTEMARITIMO");
			String registroCapitania = rs.getString("REGISTROCAPITANIA");
			int id = Integer.parseInt(rs.getString("ID"));
			int capacidad = Integer.parseInt(rs.getString("CAPACIDAD"));

			String destino = rs.getString("DESTINO");
			buques.add(new Buque(id, registroCapitania, agenteMaritimo, name, unidad, tipo, capacidad,destino));
		}

		return buques;
	}

	/**
	 * Método que agrega el buque que entra como parámetro a la base de datos.
	 * @param buque - el buque a agregar. buque !=  null
	 * <b> post: </b> se ha agregado el buque a la base de datos en la transaction actual. pendiente que el buque master
	 * haga commit para que el buque baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el buque a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addBuque(Buque buque) throws SQLException, Exception {

		String sql = "INSERT INTO BUQUES VALUES (";
		sql += buque.getID() + ",'";
		sql += buque.getAgenteMaritimo() + "','";
		sql += buque.getRegistroCapitania() + "','";
		sql += buque.getNombre() + "','";
		sql += buque.getUnidad() + "','";
		sql += buque.getTipo() + "')";
		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Método que actualiza el buque que entra como parámetro en la base de datos.
	 * @param buque - el buque a actualizar. buque !=  null
	 * <b> post: </b> se ha actualizado el buque en la base de datos en la transaction actual. pendiente que el buque master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el buque.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateBuque(Buque buque) throws SQLException, Exception {

		String sql = "UPDATE BUQUES SET ";
		sql += "nombre='" + buque.getNombre() + "',";
		sql += "agenteMaritimo='" + buque.getAgenteMaritimo()+"',";
		sql += "registroCapitania='" + buque.getRegistroCapitania()+"',";
		sql += "unidad='" + buque.getUnidad()+"',";
		sql += " WHERE id = " + buque.getID();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Método que elimina el buque que entra como parámetro en la base de datos.
	 * @param buque - el buque a borrar. buque !=  null
	 * <b> post: </b> se ha borrado el buque en la base de datos en la transaction actual. pendiente que el buque master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el buque.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteBuque(Buque buque) throws SQLException, Exception {

		String sql = "DELETE FROM BUQUES";
		sql += " WHERE id = " + buque.getID();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	
	/**
	 * Método que retorna el tipo de un Buque
	 * @param idBuque id del buque
	 * @return tipo del buque
	 * @throws Exception
	 */
	public String darTipoBuque(int idBuque) throws Exception {
	  String resp = null;
	  String sql = "SELECT TIPO "
               + "FROM BUQUES "
               + "WHERE ID=" + idBuque;

    PreparedStatement prepStmt = conn.prepareStatement(sql);
    recursos.add(prepStmt);
    ResultSet rs = prepStmt.executeQuery();
    while (rs.next()) {
      resp = rs.getString("TIPO");
    }
    return resp;
  }


	public Buque darBuquePorId(int idBuque) throws Exception{
		Buque resp = null;
		  String sql = "SELECT * "
	               + "FROM BUQUES "
	               + "WHERE ID=" + idBuque;

	    PreparedStatement prepStmt = conn.prepareStatement(sql);
	    recursos.add(prepStmt);
	    ResultSet rs = prepStmt.executeQuery();
	    while (rs.next()) {
	    	String name = rs.getString("NOMBRE");
			String registroCapitania = rs.getString("REGISTROCAPITANIA");
			String agenteMaritimo = rs.getString("AGENTEMARITIMO");
			String unidad = rs.getString("UNIDAD");
			String tipo = rs.getString("TIPO");
			int id = Integer.parseInt(rs.getString("ID"));
			int capacidad = Integer.parseInt(rs.getString("CAPACIDAD"));
			String destino = rs.getString("DESTINO");
			resp=(new Buque(id, registroCapitania, agenteMaritimo, name, unidad, tipo, capacidad,destino));
		
	    }
	    return resp;
	}

}
