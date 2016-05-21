package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.RegistroTerminal;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicación
 * @author Antonio de la Vega
 */
public class DAOTablaRegistroTerminales {


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
	public DAOTablaRegistroTerminales() {
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
	
	public void addRegistroTerminal(RegistroTerminal reg) throws SQLException{
		
		String sql = "INSERT INTO REGISTRO_TERMINALES VALUES (";
		sql += reg.getId() + ",";
		sql += "(TO_DATE('" +reg.getFecha() + "', 'yyyy/mm/dd hh24:mi:ss')),";
		sql += reg.getIdBuque() + ",";
		sql += reg.getIdTerminal() + ",'";
		sql += reg.getEstadoBuque() +  "')";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}
