package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import vos.Exportador;
import vos.ExportadorCompleto;
import vos.ExportadorUnificado;
import vos.ListaExportadorCompleto;
import vos.ListaRegistroBuques;
import vos.ParametroBusqueda;
import vos.RegistroBuque;
import vos.Usuario;

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
	
	public boolean existeExportador(String rut) throws SQLException{
		String sql = "SELECT * FROM EXPORTADORES WHERE RUT = " + rut;
		System.out.println(sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		return prepStmt.executeQuery().next();
	}
	
	public ArrayList<ExportadorUnificado> costoExportadores(String fechas) throws SQLException{
		ArrayList<ExportadorUnificado> eu = new ArrayList<>();
		String sql = "SELECT * FROM (SELECT ID AS ID1, NOMBRE FROM CLIENTES) INNER JOIN (SELECT ID AS ID2 FROM EXPORTADORES) ON ID2 = ID1 INNER JOIN"
					+ " (SELECT ID_CLIENTE, SUM(COSTO) as COSTO FROM FACTURAS WHERE "
					+ "FECHA >= '" + fechas.split(" ")[0] + "' "
					+ "AND FECHA <= '" + fechas.split(" ")[1] + "' GROUP BY ID_CLIENTE)"
					+ " ON ID1 = ID_CLIENTE";
		System.out.println(sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		while(rs.next()){
			String nombre = rs.getString("nombre");
			double costo = Double.parseDouble(rs.getString("costo"));
			eu.add(new ExportadorUnificado(nombre,costo));
		}
		
		return eu;
	}
	
	public ListaExportadorCompleto consultarExportador(int idExportador, ParametroBusqueda pb) throws SQLException{
		ArrayList<ExportadorCompleto> registros = new ArrayList<>();
		String sql = "SELECT * FROM "
				+ "CLIENTES "
				+ "INNER JOIN EXPORTADORES ON "
				+ "CLIENTES.ID = EXPORTADORES.ID AND CLIENTES.ID = " + idExportador + " "
						+ "AND EXPORTADORES.ID = " + idExportador + " "
				+ "INNER JOIN CARGAS"
						+ " ON CARGAS.ID_CLIENTE = " + idExportador +" "
						+ "AND CLIENTES.ID = CARGAS.ID_CLIENTE "
				+ "OUTER JOIN"
				+ "SELECT COUNT(*) AS CONTEO FROM (SELECT CARGAS.ID as cargaId, REGISTRO_CARGAS.fecha as fecha1 FROM CARGAS "
				+ "INNERJOIN REGISTRO_CARGAS "
				+ "ON CARGAS.ID = REGISTRO_CARGAS.ID_CARGA "
				+ "WHERE CARGAS.ID_CLIENTE = "+ idExportador + ")"
				+ " INNER JOIN REGISTRO_TERMINALES ON fecha1 = REGISTRO_TERMINALES.fecha "
				+ " INNER JOIN BUQUES ON REGISTRO_TERMINALES.id_buque = BUQUES.id ";
		if(!pb.getWhere().isEmpty())sql += " WHERE ";
		for(int i = 0; i<pb.getWhere().size(); i++){
			if(i < pb.getWhere().size() - 1) sql += pb.getWhere().get(i) + " AND ";
			else sql += pb.getWhere().get(i);
		}
		
		if(!pb.getGroup().isEmpty())sql += " GROUP BY ";
		
		for(int i = 0; i<pb.getGroup().size(); i++){
			if(i < pb.getGroup().size() - 1) sql += pb.getGroup().get(i) + " , ";
			else sql += pb.getGroup().get(i);
		}
		
		if(!pb.getOrder().isEmpty())sql += " ORDER BY ";
		
		for(int i = 0; i<pb.getOrder().size(); i++){
			if(i < pb.getOrder().size() - 1) sql += pb.getOrder().get(i) + " , ";
			else sql += pb.getOrder().get(i);
		}
		
		System.out.println("sql stmt: " + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id1 = Integer.parseInt(rs.getString("ID_EXPORTADOR"));
			int rut = Integer.parseInt(rs.getString("RUT"));
			String num_usos = rs.getString("NUM_USOS");
			int idCarga = Integer.parseInt(rs.getString("ID_CARGA"));
			String tipo = rs.getString("TIPO");
			String peso = rs.getString("PESO");
			String volumen = rs.getString("VOLUMEN");
			String idBuque = rs.getString("ID_BUQUE");
			String idCamion = rs.getString("ID_CAMION");
			String idAlmacenamiento = rs.getString("ID_ALMACENAMIENTO");
			String idCliente = rs.getString("ID_CLIENTE");
			String tipoCarga = rs.getString("TIPO_CARGA");
			//registros.add(new ExportadorCompleto(id1, rut,num_usos, idCarga, tipo, peso,
				//	volumen, idBuque, idCamion, idAlmacenamiento, idCliente, tipoCarga));
		}
		return new ListaExportadorCompleto(registros);
	}
}
