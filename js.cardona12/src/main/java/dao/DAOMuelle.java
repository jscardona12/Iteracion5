
package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Muelle;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicación
 * @author Juan
 */
public class DAOMuelle {


	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Método constructor que crea DAOVideo
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOMuelle() 
	{
		recursos = new ArrayList<Object>();
	}

	/**
	 * Método que cierra todos los recursos que estan enel arreglo de recursos
	 * <b>post: </b> Todos los recurso del arreglo de recursos han sido cerrados
	 */
	public void cerrarRecursos() {
		for(Object ob : recursos){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}

	/**
	 * Método que inicializa la connection del DAO a la base de datos con la conexión que entra como parámetro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}


	/**
	 * Método que, usando la conexión a la base de datos, saca todos los videos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM VIDEOS;
	 * @return Arraylist con los videos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Muelle> darMuelles() throws SQLException, Exception 
	{
		boolean er = true;
		boolean eo = true;
		ArrayList<Muelle> muelle = new ArrayList<Muelle>();

		String sql = "SELECT * FROM MUELLES";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) 
		{
			String name = rs.getString("NAME");
			int id = Integer.parseInt(rs.getString("ID"));
			int erint = Integer.parseInt(rs.getString("ESTA_RESERVADO"));
			int eoint = Integer.parseInt(rs.getString("ESTA_OCUPADO"));
			if(erint == 0)
			{
				er = false;
			}
			if(eoint == 0)
			{
				eo = false;
			}
			
			muelle.add(new Muelle(id, name, er, eo));
		}
		return muelle;
	}


	/**
	 * Método que busca el/los videos con el nombre que entra como parámetro.
	 * @param name - Nombre de el/los videos a buscar
	 * @return Arraylist con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Muelle> buscarMuellePorName(String name) throws SQLException, Exception {
		
		boolean er = true;
		boolean eo = true;
		
		ArrayList<Muelle> muelle = new ArrayList<Muelle>();

		String sql = "SELECT * FROM MUELLES WHERE NAME ='" + name + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) 
		{
			String name2 = rs.getString("NAME");
			int id = Integer.parseInt(rs.getString("ID"));
			int erint = Integer.parseInt(rs.getString("ESTA_RESERVADO"));
			int eoint = Integer.parseInt(rs.getString("ESTA_OCUPADO"));
			if(erint == 0)
			{
				er = false;
			}
			if(eoint == 0)
			{
				eo = false;
			}
			muelle.add(new Muelle(id, name2, er, eo));
		}

		return muelle;
	}

	/**
	 * Método que agrega el video que entra como parámetro a la base de datos.
	 * @param muelle - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addMuelle(Muelle muelle) throws SQLException, Exception {

		String sql = "INSERT INTO MUELLES VALUES (";
		sql += muelle.getId() + ",'";
		sql += muelle.getNombre() + "',";
		sql += muelle.getEstaReservado() + ",";
		sql += muelle.getEstaOcupado() + ")";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Método que actualiza el video que entra como parámetro en la base de datos.
	 * @param muelle - el video a actualizar. video !=  null
	 * <b> post: </b> se ha actualizado el video en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateMuelle(Muelle muelle) throws SQLException, Exception {

		String sql = "UPDATE MUELLES SET ";
		sql += "NAME='" + muelle.getNombre() + "',";
		sql += "ESTA_RESERVADO=" + muelle.getEstaReservado() + ",";
		sql += "ESTA_OCUPADO=" + muelle.getEstaOcupado();
		sql += " WHERE ID = " + muelle.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Método que elimina el video que entra como parámetro en la base de datos.
	 * @param muelle - el video a borrar. video !=  null
	 * <b> post: </b> se ha borrado el video en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteMuelle(Muelle muelle) throws SQLException, Exception {

		String sql = "DELETE FROM MUELLES";
		sql += " WHERE id = " + muelle.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}
