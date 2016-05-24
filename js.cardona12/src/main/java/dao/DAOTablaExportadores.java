package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import vos.Barco;
import vos.Exportador;


public class DAOTablaExportadores {

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
	public DAOTablaExportadores() {
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
	public void actualizarExportador(String rut, int descuento) throws SQLException{
		String sql = "UPDATE EXPORTADORES SET DESCUENTO = " + descuento + " WHERE RUT = " + rut;
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeUpdate();
	}

	public boolean buscarExportador(String rut) throws SQLException {
		String sql = "SELECT * FROM EXPORTADORES WHERE RUT = " + rut;
		System.out.println(sql + " - JS");
		PreparedStatement prepStmt3 = conn.prepareStatement(sql);
		recursos.add(prepStmt3);
		ResultSet rs3 = prepStmt3.executeQuery();
		
		return rs3.next();
		
	}
	
	public ArrayList<Exportador> getExportadores() throws SQLException
	{
		ArrayList barco = new ArrayList<Exportador>();
		String sql = "SELECT * FROM EXPORTADORES";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) 
		{
			String name = rs.getString("NAME");
			int id = Integer.parseInt(rs.getString("ID"));
			int rut = Integer.parseInt(rs.getString("RUT"));
			int costoR = Integer.parseInt(rs.getString("ID_COSTO_FACTURADO"));
			
			String sql1 = "SELECT COSTO FROM COSTOS_FACTURADOS "
					+ "WHERE COSTOS_FACTURADOS.ID = " + costoR;

			PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
			recursos.add(prepStmt1);
			ResultSet rs1 = prepStmt1.executeQuery();
			
			if(rs1.next())
			{
				int costo = Integer.parseInt(rs.getString("COSTO"));
				barco.add(new Exportador(id, rut, costo));
			}
			
		}
		return barco;
	}
	
}
