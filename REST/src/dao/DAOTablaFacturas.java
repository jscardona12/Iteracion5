package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import vos.Buque;
import vos.Factura;
import vos.RegistroCarga;

public class DAOTablaFacturas {

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;
	
	private static int id;

	/**
	 * Método constructor que crea DAOVideo
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaFacturas() {
		recursos = new ArrayList<Object>();
		id = 1000;
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

	public void addFactura(Factura fac) throws SQLException {

		String sql = "INSERT INTO FACTURAS VALUES (";
		sql += fac.getId() + ",";
		sql += fac.getCosto() + ",";
		sql += fac.getId_cliente() + ",";
		sql += "(TO_DATE('" +fac.getFecha() + "', 'yyyy/mm/dd hh24:mi:ss')))";
		

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public Factura generarFactura(int idCliente, int idBuque, String fecha) throws SQLException{
		String sql= "SELECT COUNT(*) AS NUMERO FROM (SELECT ID_CARGA AS ID1 FROM REGISTRO_CARGAS "
				+ "WHERE FECHA = " + "(TO_DATE('" + fecha + "', 'yyyy/mm/dd hh24:mi:ss'))"
				+ " AND ESTADO = 'CARGADO') INNER JOIN (SELECT ID AS ID2 FROM CARGAS WHERE ID_CLIENTE = "+ idCliente +" "
						+ "AND ID_BUQUE=" + idBuque + ")"
						+ " ON ID2 = ID1";
		
		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		int numEntradas = 0;
		while (rs.next()) {
			numEntradas = Integer.parseInt(rs.getString("NUMERO"));
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String date  = sdf.format(new Date());
		return new Factura(id++,numEntradas*100,idCliente, date);
	}
}
