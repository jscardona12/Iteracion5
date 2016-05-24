package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vos.Buque;
import vos.Carga;
import vos.ConsultaAreas;
import vos.ListaConsultaAreas;
import vos.ListaMovimientoCargas;
import vos.ListaRegistroBuques;
import vos.MovimientoCarga;
import vos.ParametroBusqueda;
import vos.RegistroBuque;
import vos.RegistroTerminal;

public class DAOTablaCargas {

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
	public DAOTablaCargas() {
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
	
	//public void existeCargaParaEnviar()
	public void updateCarga(Carga carga) throws SQLException{
		System.out.println("LA ID DE LCAMOIN ES : "  + carga.getId_camion());
		String sql = "UPDATE CARGAS SET ";
		sql += "TIPO = '"+carga.getTipo() + "',";
		sql += "PESO = "+carga.getPeso() + ",";
		sql += "VOLUMEN = " + carga.getVolumen() + ",";
		sql += "ID_BUQUE = " + (carga.getId_buque() == -1 ? "NULL" : carga.getId_buque()) + ",";
		sql += "ID_CAMION = " + (carga.getId_camion() == -1 ? "NULL" : carga.getId_camion()) + ",";
		sql += "ID_ALMACENAMIENTO = " + (carga.getId_almacenamiento() == -1 ? "NULL" : carga.getId_almacenamiento()) + ",";
		sql += "ID_CLIENTE = " + carga.getId_cliente() + ",";
		sql += "PUERTO_ORIGEN = '" + carga.getPuerto_origen() + "',";
		sql += "PUERTO_DESTINO = '" + carga.getPuerto_destino() +"' ";
		sql += " WHERE ID = " + carga.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeUpdate();
	}
	
	public List<Carga>cargasSinCargar(String tipoBuque, String destino) throws SQLException{
		String sql = "SELECT * FROM CARGAS WHERE ID_BUQUE IS NULL AND TIPO = '" + tipoBuque
				+ "' AND PUERTO_DESTINO = '" + destino + "'";
		ArrayList<Carga> cargas = new ArrayList<>();
		System.out.println("sql stmt: " + sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id1 = Integer.parseInt(rs.getString("ID"));
			String tipo = rs.getString("TIPO");
			double peso = Integer.parseInt(rs.getString("PESO"));
			double volumen = Double.parseDouble(rs.getString("VOLUMEN"));
			String tempId = rs.getString("ID_BUQUE");
			int idBuque = (tempId == null) ? -1 : Integer.parseInt(tempId);
			tempId = rs.getString("ID_ALMACENAMIENTO");
			int idAlmacenamiento = (tempId == null) ? -1 : Integer.parseInt(tempId);
			tempId = rs.getString("ID_CLIENTE");
			int idCliente = (tempId == null) ? -1 : Integer.parseInt(tempId);
			tempId = rs.getString("ID_CAMION");
			int idCamion = (tempId == null) ? -1 : Integer.parseInt(tempId);
			String puertoOrigen = rs.getString("PUERTO_ORIGEN");
			String puertoDestino = rs.getString("PUERTO_DESTINO");
			String estado = rs.getString("ESTADO");
			cargas.add(new Carga(id1, tipo, peso, volumen, idBuque, idCamion, idAlmacenamiento, idCliente, puertoOrigen, puertoDestino,estado));
		}
		return cargas;
	}
	
	public List<Carga>cargasSinDescargar(int idBuque, String destino) throws SQLException{
		String sql = "SELECT * FROM CARGAS WHERE ID_ALMACENAMIENTO IS NULL AND ID_BUQUE = " + idBuque
				+ " AND PUERTO_DESTINO = '" + destino + "'";
		ArrayList<Carga> cargas = new ArrayList<>();
		System.out.println("sql stmt: " + sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id1 = Integer.parseInt(rs.getString("ID"));
			String tipo = rs.getString("TIPO");
			double peso = Double.parseDouble(rs.getString("PESO"));
			double volumen = Double.parseDouble(rs.getString("VOLUMEN"));
			String tempId = rs.getString("ID_BUQUE");
			int idBuque1 = (tempId == null) ? -1 : Integer.parseInt(tempId);
			tempId = rs.getString("ID_ALMACENAMIENTO");
			int idAlmacenamiento = (tempId == null) ? -1 : Integer.parseInt(tempId);
			tempId = rs.getString("ID_CLIENTE");
			int idCliente = (tempId == null) ? -1 : Integer.parseInt(tempId);
			tempId = rs.getString("ID_CAMION");
			int idCamion = (tempId == null) ? -1 : Integer.parseInt(tempId);
			String puertoOrigen = rs.getString("PUERTO_ORIGEN");
			String puertoDestino = rs.getString("PUERTO_DESTINO");
			String estado = rs.getString("ESTADO");
			cargas.add(new Carga(id1, tipo, peso, volumen, idBuque1, idCamion, idAlmacenamiento, idCliente, puertoOrigen, puertoDestino, estado));
		}
		return cargas;
	}
	
	public List<Carga>cargasDelBuque(int idBuque) throws SQLException{
		String sql = "SELECT * FROM CARGAS WHERE ID_BUQUE = " + idBuque
				+ " AND ESTADO = 'CARGADO'";
		ArrayList<Carga> cargas = new ArrayList<>();
		System.out.println("sql stmt: " + sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id1 = Integer.parseInt(rs.getString("ID"));
			String tipo = rs.getString("TIPO");
			double peso = Double.parseDouble(rs.getString("PESO"));
			double volumen = Double.parseDouble(rs.getString("VOLUMEN"));
			String tempId = rs.getString("ID_BUQUE");
			int idBuque1 = (tempId == null) ? -1 : Integer.parseInt(tempId);
			tempId = rs.getString("ID_ALMACENAMIENTO");
			int idAlmacenamiento = (tempId == null) ? -1 : Integer.parseInt(tempId);
			tempId = rs.getString("ID_CLIENTE");
			int idCliente = (tempId == null) ? -1 : Integer.parseInt(tempId);
			tempId = rs.getString("ID_CAMION");
			int idCamion = (tempId == null) ? -1 : Integer.parseInt(tempId);
			String puertoOrigen = rs.getString("PUERTO_ORIGEN");
			String puertoDestino = rs.getString("PUERTO_DESTINO");
			String estado = rs.getString("ESTADO");
			cargas.add(new Carga(id1, tipo, peso, volumen, idBuque1, idCamion, idAlmacenamiento, idCliente, puertoOrigen, puertoDestino, estado));
		}
		return cargas;
	}
	
	public List<Carga>cargasDelArea(int idArea) throws SQLException{
		String sql = "SELECT * FROM CARGAS WHERE ID_ALMACENAMIENTO = " + idArea
				+ " AND ESTADO = 'ALMACENADO'";
		ArrayList<Carga> cargas = new ArrayList<>();
		System.out.println("sql stmt: " + sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id1 = Integer.parseInt(rs.getString("ID"));
			String tipo = rs.getString("TIPO");
			double peso = Double.parseDouble(rs.getString("PESO"));
			double volumen = Double.parseDouble(rs.getString("VOLUMEN"));
			String tempId = rs.getString("ID_BUQUE");
			int idBuque1 = (tempId == null) ? -1 : Integer.parseInt(tempId);
			tempId = rs.getString("ID_ALMACENAMIENTO");
			int idAlmacenamiento = (tempId == null) ? -1 : Integer.parseInt(tempId);
			tempId = rs.getString("ID_CLIENTE");
			int idCliente = (tempId == null) ? -1 : Integer.parseInt(tempId);
			tempId = rs.getString("ID_CAMION");
			int idCamion = (tempId == null) ? -1 : Integer.parseInt(tempId);
			String puertoOrigen = rs.getString("PUERTO_ORIGEN");
			String puertoDestino = rs.getString("PUERTO_DESTINO");
			String estado = rs.getString("ESTADO");
			cargas.add(new Carga(id1, tipo, peso, volumen, idBuque1, idCamion, idAlmacenamiento, idCliente, puertoOrigen, puertoDestino, estado));
		}
		return cargas;
	}
	
	public void addCarga(Carga carga) throws SQLException {

		String sql = "INSERT INTO CARGAS VALUES (";
		sql += carga.getId() + ",'";
		sql += carga.getTipo() + "',";
		sql += carga.getPeso() + ",";
		sql += carga.getVolumen() + ",";
		sql += (carga.getId_buque() == 0 ? "NULL" : carga.getId_buque()) + ",";
		sql += (carga.getId_camion() == 0 ? "NULL" : carga.getId_camion()) + ",";
		sql += (carga.getId_almacenamiento() == 0 ? "NULL" : carga.getId_almacenamiento()) + ",";
		sql += carga.getId_cliente() + ")";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public Carga buscarCargaPorId(int id) throws NumberFormatException, SQLException {
		String sql = "SELECT * FROM CARGAS WHERE ID=" + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id1 = Integer.parseInt(rs.getString("ID"));
			String tipo = rs.getString("TIPO");
			double peso = Double.parseDouble(rs.getString("PESO"));
			double volumen = Double.parseDouble(rs.getString("VOLUMEN"));
			String tempId = rs.getString("ID_BUQUE");
			int idBuque = (tempId == null) ? -1 : Integer.parseInt(tempId);
			tempId = rs.getString("ID_ALMACENAMIENTO");
			int idAlmacenamiento = (tempId == null) ? -1 : Integer.parseInt(tempId);
			tempId = rs.getString("ID_CLIENTE");
			int idCliente = (tempId == null) ? -1 : Integer.parseInt(tempId);
			tempId = rs.getString("ID_CAMION");
			int idCamion = (tempId == null) ? -1 : Integer.parseInt(tempId);
			String puertoOrigen = rs.getString("PUERTO_ORIGEN");
			String puertoDestino = rs.getString("PUERTO_DESTINO");
			String estado = rs.getString("ESTADO");
			return new Carga(id1, tipo, peso, volumen, idBuque, idCamion, idAlmacenamiento, idCliente, puertoOrigen, puertoDestino, estado);
		}
		return null;
	}
	public ListaMovimientoCargas consultarMovimientoCargas(ParametroBusqueda pb, int cliente) throws SQLException{
		ArrayList<MovimientoCarga> registros = new ArrayList<>();
		String sql = "SELECT * FROM ("
				+ "REGISTRO_CARGAS INNER JOIN CARGAS ON "
				+ "REGISTRO_CARGAS.ID_CARGA = CARGAS.ID) WHERE CARGAS.ID_CLIENTE = " + cliente;
		
		if(!pb.getWhere().isEmpty()) sql += " WHERE ";
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
			int id1 = Integer.parseInt(rs.getString("ID"));
			String tipo = rs.getString("TIPO");
			double peso = Double.parseDouble(rs.getString("PESO"));
			double volumen = Double.parseDouble(rs.getString("VOLUMEN"));
			String tempId = rs.getString("ID_BUQUE");
			int idBuque = (tempId == null) ? -1 : Integer.parseInt(tempId);
			tempId = rs.getString("ID_ALMACENAMIENTO");
			int idAlmacenamiento = (tempId == null) ? -1 : Integer.parseInt(tempId);
			tempId = rs.getString("ID_CLIENTE");
			int idCliente = (tempId == null) ? -1 : Integer.parseInt(tempId);
			tempId = rs.getString("ID_CAMION");
			int idCamion = (tempId == null) ? -1 : Integer.parseInt(tempId);
			String puertoOrigen = rs.getString("PUERTO_ORIGEN");
			String puertoDestino = rs.getString("PUERTO_DESTINO");
			String estado = rs.getString("ESTADO");
			String fecha = rs.getString("FECHA");
			double valor = Double.parseDouble(rs.getString("VALOR"));
			registros.add(new MovimientoCarga(id1,tipo,peso,volumen,idBuque,
					idCamion,idAlmacenamiento, idCliente,puertoOrigen,puertoDestino, id1, fecha, estado, valor));
		}
		return new ListaMovimientoCargas(registros);
	}
	
	public ListaMovimientoCargas consultarMovimientoCargas(ParametroBusqueda pb) throws SQLException{
		ArrayList<MovimientoCarga> registros = new ArrayList<>();
		String sql = "SELECT * FROM ("
				+ "REGISTRO_CARGAS INNER JOIN CARGAS ON "
				+ "REGISTRO_CARGAS.ID_CARGA = CARGAS.ID)";
		
		if(!pb.getWhere().isEmpty()) sql += " WHERE ";
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
			int id1 = Integer.parseInt(rs.getString("ID"));
			String tipo = rs.getString("TIPO");
			double peso = Double.parseDouble(rs.getString("PESO"));
			double volumen = Double.parseDouble(rs.getString("VOLUMEN"));
			String tempId = rs.getString("ID_BUQUE");
			int idBuque = (tempId == null) ? -1 : Integer.parseInt(tempId);
			tempId = rs.getString("ID_ALMACENAMIENTO");
			int idAlmacenamiento = (tempId == null) ? -1 : Integer.parseInt(tempId);
			tempId = rs.getString("ID_CLIENTE");
			int idCliente = (tempId == null) ? -1 : Integer.parseInt(tempId);
			tempId = rs.getString("ID_CAMION");
			int idCamion = (tempId == null) ? -1 : Integer.parseInt(tempId);
			String puertoOrigen = rs.getString("PUERTO_ORIGEN");
			String puertoDestino = rs.getString("PUERTO_DESTINO");
			String estado = rs.getString("ESTADO");
			String fecha = rs.getString("FECHA");
			tempId = rs.getString("VALOR");
			double valor = (tempId == null) ? Math.random()*1000 : Double.parseDouble(tempId);
			registros.add(new MovimientoCarga(id1,tipo,peso,volumen,idBuque,
					idCamion,idAlmacenamiento, idCliente,puertoOrigen,puertoDestino, id1, fecha, estado, valor));
		}
		return new ListaMovimientoCargas(registros);
	}
	
	public ListaConsultaAreas consultarAreas(ParametroBusqueda pb) throws SQLException{
		ArrayList<ConsultaAreas> registros = new ArrayList<>();
		String sql = "SELECT * FROM ("
				+ " (SELECT ID AS ID_REGISTRO, FECHA, ESTADO AS EST, ID_CARGA FROM REGISTRO_CARGAS WHERE ESTADO = 'ALMACENADO' OR ESTADO = 'CARGADO') INNER JOIN (SELECT ID AS ID_CARGA1, ID_ALMACENAMIENTO AS ID_ALM FROM CARGAS) ON "
				+ "ID_CARGA = ID_CARGA1 "
				+ "INNER JOIN (SELECT ID AS IDA, DIMENSION, ESTADO, TIPO FROM AREAS_ALMACENAMIENTO)"
				+ "ON ID_ALM = IDA) ";
		
		if(!pb.getWhere().isEmpty()) sql += " WHERE ";
		for(int i = 0; i<pb.getWhere().size(); i++){
			if(i < pb.getWhere().size() - 1) sql += pb.getWhere().get(i) + " OR ";
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
			int idArea = Integer.parseInt(rs.getString("IDA"));
			double dimension = Double.parseDouble(rs.getString("DIMENSION"));
			String estadoArea = rs.getString("ESTADO");
			String tipo = rs.getString("TIPO");
			String fecha = rs.getString("FECHA");
			String estadoCarga = rs.getString("EST");
			registros.add(new ConsultaAreas(idArea,dimension,estadoArea,tipo,idArea,fecha,estadoCarga));
		}
		return new ListaConsultaAreas(registros);
	}
}
