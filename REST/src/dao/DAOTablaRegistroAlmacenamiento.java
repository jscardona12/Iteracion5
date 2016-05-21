package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.RegistroAlmacenamiento;
import vos.RegistroTerminal;

public class DAOTablaRegistroAlmacenamiento {

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
	public DAOTablaRegistroAlmacenamiento() {
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
	
	public void addRegistro(RegistroAlmacenamiento reg) throws SQLException{
		String sql = "INSERT INTO REGISTRO_ALMCNMIENTO VALUES (";
		sql += reg.getId() + ",";
		sql += reg.getTipo() + ",";
		sql += "(TO_DATE('" +reg.getFecha() + "', 'yyyy/mm/dd hh24:mi:ss')),";
		sql += reg.getId_almacenamiento() + ")";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeUpdate();
	}
	
	public boolean existeRegistroReserva(int idArea, String fecha) throws NumberFormatException, SQLException{
		String sql = "SELECT * FROM REGISTRO_ALMCNMIENTO WHERE ID_ALMACENAMIENTO = " + idArea
				+ " AND FECHA = (TO_DATE('" + fecha + "', 'yyyy/mm/dd hh24:mi:ss'))"
				+ " AND TIPO = 'RESERVA'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		return rs.next();
	}
	
	public RegistroAlmacenamiento getRegistro(int id) throws NumberFormatException, SQLException{
		String sql = "SELECT * FROM REGISTRO_ALMCNMIENTO WHERE ID = " + id;

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		while(rs.next()){
			int id1 = Integer.parseInt(rs.getString("ID"));
			String fecha = rs.getString("FECHA");
			String tipo = rs.getString("TIPO");
			int id_almacenamiento = Integer.parseInt("ID_ALMACENAMIENTO");
			return new RegistroAlmacenamiento(id1,fecha, tipo, id_almacenamiento);
		}
		return null;
	}
}
