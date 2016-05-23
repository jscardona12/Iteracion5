
package dao;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Carga;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicación
 * @author Juan
 */
public class DAOCarga {


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
	public DAOCarga() 
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
	public ArrayList<Carga> darCargas() throws SQLException, Exception 
	{
		
		ArrayList<Carga> carga = new ArrayList<Carga>();

		String sql = "SELECT * FROM CARGAS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) 
		{
			String tipo = rs.getString("TIPO");
			int id = Integer.parseInt(rs.getString("ID"));
			double v = Double.parseDouble(rs.getString("VOLUMEN"));
			int c = Integer.parseInt(rs.getString("COSTO"));
			int e = Integer.parseInt(rs.getString("ID_EQUIPO"));
			int p = Integer.parseInt(rs.getString("PESO"));
			String ar;
			int a = Integer.parseInt((ar=rs.getString("ID_AREA"))!=null?ar:"0");
			int g = Integer.parseInt(rs.getString("ID_AGENTE"));
			
	
						
			carga.add(new Carga(tipo, v, id,c,e,p,a,g));
		}
		return carga;
	}


	/**
	 * Método que busca el/los videos con el nombre que entra como parámetro.
	 * @param name - Nombre de el/los videos a buscar
	 * @return Arraylist con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Carga> buscarCargaPorTipo(String t) throws SQLException, Exception {
		
		
		ArrayList<Carga> carga = new ArrayList<Carga>();

		String sql = "SELECT * FROM CARGAS WHERE TIPO ='" + t + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) 
		{
			String tipo = rs.getString("TIPO");
			int id = Integer.parseInt(rs.getString("ID"));
			double v = Double.parseDouble(rs.getString("VOLUMEN"));
			int c = Integer.parseInt(rs.getString("COSTO"));
			int e = Integer.parseInt(rs.getString("ID_EQUIPO"));
			int p = Integer.parseInt(rs.getString("PESO"));
			int a = Integer.parseInt(rs.getString("ID_AREA"));
			int g = Integer.parseInt(rs.getString("ID_AGENTE"));
			
			
			
			carga.add(new Carga(tipo, v, id,c,e,p,a,g));
		}

		return carga;
	}

	/**
	 * Método que agrega el video que entra como parámetro a la base de datos.
	 * @param carga - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addCarga(Carga carga) throws SQLException, Exception {

		String sql = "INSERT INTO CARGAS VALUES (";
		sql += carga.getId() + ",'";
		sql += carga.getTipo() + "',";
		sql += carga.getVolumen() ;

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Método que actualiza el video que entra como parámetro en la base de datos.
	 * @param carga - el barco a actualizar. barco !=  null
	 * <b> post: </b> se ha actualizado el video en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateCarga(Carga carga) throws SQLException, Exception {

		String sql = "UPDATE CARGAS SET ";
		sql += "TIPO='" + carga.getTipo() + "',";
		sql += "VOLUMEN=" +carga.getVolumen() + ",";
		sql += " WHERE ID = " + carga.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Método que elimina el video que entra como parámetro en la base de datos.
	 * @param carga - el video a borrar. video !=  null
	 * <b> post: </b> se ha borrado el video en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteCarga(Carga carga) throws SQLException, Exception {

		String sql = "DELETE FROM CARGAS";
		sql += " WHERE id = " + carga.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
}


