package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import oracle.net.aso.a;
import vos.AreaAlmacenamiento;
import vos.Buque;

public class DAOTablaAreasAlmacenamiento {

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
	public DAOTablaAreasAlmacenamiento() {
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
	
	public AreaAlmacenamiento buscarAreaPorId(int id) throws SQLException{
		String sql = "SELECT * FROM AREAS_ALMACENAMIENTO WHERE ID = " + id;
		
		System.out.println("SQL stmt:" + sql);
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = 	prepStmt.executeQuery();
		
		while (rs.next()) {
			int id1 = Integer.parseInt(rs.getString("ID"));
			double dimension = Double.parseDouble(rs.getString("DIMENSION"));
			String estado = rs.getString("ESTADO");
			String tipo = rs.getString("TIPO");
			return new AreaAlmacenamiento(-1,id1, dimension, estado, tipo);
		}
		return null;
	}
	
	public List<AreaAlmacenamiento> buscarAreaPorTipo(String tipo) throws SQLException{
		String sql = "SELECT * FROM AREAS_ALMACENAMIENTO WHERE TIPO = " + tipo;
		ArrayList<AreaAlmacenamiento> list = new ArrayList<>();
		System.out.println("SQL stmt:" + sql);
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = 	prepStmt.executeQuery();
		
		while (rs.next()) {
			int id1 = Integer.parseInt(rs.getString("ID"));
			double dimension = Double.parseDouble(rs.getString("DIMENSION"));
			String estado = rs.getString("ESTADO");
			String tipo1 = rs.getString("TIPO");
			list.add(new AreaAlmacenamiento(-1,id1, dimension, estado,tipo1));
		}
		return list;
	}
	
	public List<AreaAlmacenamiento> buscarAreaParaCargas(String tipo, double capacidad) throws SQLException{
		String sql = "SELECT * FROM AREAS_ALMACENAMIENTO WHERE TIPO = '" + tipo
				+ "' AND DIMENSION >= " + capacidad + " AND ESTADO = 'DISPONIBLE'";
		ArrayList<AreaAlmacenamiento> list = new ArrayList<>();
		System.out.println("SQL stmt:" + sql);
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = 	prepStmt.executeQuery();
		
		while (rs.next()) {
			int id1 = Integer.parseInt(rs.getString("ID"));
			double dimension = Double.parseDouble(rs.getString("DIMENSION"));
			String estado = rs.getString("ESTADO");
			String tipo1 = rs.getString("TIPO");
			list.add(new AreaAlmacenamiento(-1,id1, dimension, estado, tipo1));
		}
		return list;
	}
	
	public List<AreaAlmacenamiento> buscarAreaTotal(String tipo) throws SQLException{
		String sql = "SELECT * FROM AREAS_ALMACENAMIENTO WHERE TIPO = '" + tipo
				+ "' AND ESTADO = 'DISPONIBLE'";
		ArrayList<AreaAlmacenamiento> list = new ArrayList<>();
		System.out.println("SQL stmt:" + sql);
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = 	prepStmt.executeQuery();
		
		while (rs.next()) {
			int id1 = Integer.parseInt(rs.getString("ID"));
			double dimension = Double.parseDouble(rs.getString("DIMENSION"));
			String estado = rs.getString("ESTADO");
			String tipo1 = rs.getString("TIPO");
			list.add(new AreaAlmacenamiento(-1,id1, dimension, estado, tipo1));
		}
		return list;
	}
	
	public void updateArea(AreaAlmacenamiento aa) throws SQLException{
		String sql = "UPDATE AREAS_ALMACENAMIENTO SET "
				+ "DIMENSION = " + aa.getDimension() + ","
				+ "ESTADO = '" + aa.getEstado() + "' "
				+ "WHERE ID = " + aa.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeUpdate();	
	}
}
