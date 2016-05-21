package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Carga;
import vos.RegistroBuque;
import vos.RegistroCarga;

public class DAOTablaRegistroBuques {

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Método constructor que crea DAOVideo <b>post: </b> Crea la instancia del
	 * DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaRegistroBuques() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Método que cierra todos los recursos que estan enel arreglo de recursos
	 * <b>post: </b> Todos los recurso del arreglo de recursos han sido cerrados
	 */
	public void cerrarRecursos() {
		for (Object ob : recursos) {
			if (ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}

	/**
	 * Método que inicializa la connection del DAO a la base de datos con la
	 * conexión que entra como parámetro.
	 * 
	 * @param con
	 *            - connection a la base de datos
	 */
	public void setConn(Connection con) {
		this.conn = con;
	}
	
	public void addRegistroBuque(RegistroBuque reg) throws SQLException {

		String sql = "INSERT INTO REGISTRO_BUQUES VALUES (";
		sql += reg.getId() + ",'";
		sql += reg.getNombre_capitan() + "','";
		sql += reg.getPais_origen() + "','";
		sql += reg.getPais_destino() + "',";
		sql += "(TO_DATE('" +reg.getFecha_arribo() + "', 'yyyy/mm/dd hh24:mi:ss')),";
		sql += "(TO_DATE('" +reg.getFecha_salida() + "', 'yyyy/mm/dd hh24:mi:ss')),'";
		sql += reg.getPuerto_origen() + "','";
		sql += reg.getPuerto_arribo() + "','";
		sql += reg.getCiudad_origen() + "','";
		sql += reg.getCiudad_arribo() + "',";
		sql += reg.getId_buque() + ",'";
		sql += reg.getTipo_carga() + "')";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
