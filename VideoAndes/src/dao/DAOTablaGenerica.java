package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public abstract class DAOTablaGenerica {

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	protected ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	protected Connection conn;

	/**
	 * Método constructor que crea DAOVideo
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaGenerica() {
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

}
