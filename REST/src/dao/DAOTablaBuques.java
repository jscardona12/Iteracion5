package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vos.Buque;
import vos.ConsultaBuque;
import vos.ListaConsultaBuques;
import vos.ListaRegistroBuques;
import vos.ParametroBusqueda;
import vos.RegistroBuque;

public class DAOTablaBuques {

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
	public DAOTablaBuques() {
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

	public void registrarSalidaBuque(Buque buque) throws SQLException, Exception{
		if(buque.getIdTerminal() == -1)	
				throw new Exception("El buque " + buque.getId() + " no se encuentra en una terminal");

		String sql = "UPDATE BUQUES2 SET ID_TERMINAL=NULL WHERE ID=" + buque.getId();
			
		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeUpdate();	
	}
	
	public void updateBuque(Buque buque) throws SQLException, Exception{

		String sql = "UPDATE BUQUES2 SET "
				+ "TIPO = '" + buque.getTipo() + "', "
				+ "CAPACIDAD = " + buque.getCapacidad() + ", "
				+ "NOMBRE = '" + buque.getNombre() + "', "
				+ "NOMBRE_AGENTE  = '" + buque.getNombre_agente() + "', "
				+ "ID_TERMINAL = " + ((buque.getId_terminal() == -1) ? "NULL" : buque.getId_terminal()) +", "
				+ "ESTADO = '" + buque.getEstado() + "' "
				+ "WHERE ID = " + buque.getId();
				
		System.out.println("SQL stmt: " + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeUpdate();
	}

	public Buque buscarBuquePorId(int id) throws NumberFormatException, SQLException {
		String sql = "SELECT * FROM BUQUES2 WHERE ID=" + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id1 = Integer.parseInt(rs.getString("ID"));
			String tipo = rs.getString("TIPO");
			int capacidad = Integer.parseInt(rs.getString("CAPACIDAD"));
			String nombre = rs.getString("NOMBRE");
			String nombre_agente = rs.getString("NOMBRE_AGENTE");
			String idT = rs.getString("ID_TERMINAL");
			int id_terminal = (idT == null) ? -1 : Integer.parseInt(idT);
			String estado = rs.getString("ESTADO");
			return new Buque(-1, id1, tipo, capacidad, nombre, nombre_agente, id_terminal, estado);
		}
		return null;
	}
	
	public List<Buque> getBuquesDisponibles(String tipo, String destino) throws NumberFormatException, SQLException {
		String sql = "SELECT * FROM BUQUES2 WHERE TIPO='" + tipo + "' AND ESTADO = 'ATRACADO'";
		ArrayList<Buque> buques = new ArrayList<>();
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id1 = Integer.parseInt(rs.getString("ID"));
			String tipo1 = rs.getString("TIPO");
			int capacidad = Integer.parseInt(rs.getString("CAPACIDAD"));
			String nombre = rs.getString("NOMBRE");
			String nombre_agente = rs.getString("NOMBRE_AGENTE");
			String idT = rs.getString("ID_TERMINAL");
			int id_terminal = (idT == null) ? -1 : Integer.parseInt(idT);
			String estado = rs.getString("ESTADO");
			buques.add( new Buque(-1,id1, tipo1, capacidad, nombre, nombre_agente, id_terminal, estado));
		}
		return buques;
	}
	
	public ListaConsultaBuques consultarIOBuques(ParametroBusqueda pb) throws SQLException{
		ArrayList<ConsultaBuque> consulta = new ArrayList<>();
		String sql = "SELECT * FROM ((SELECT ID,TIPO,NOMBRE FROM BUQUES) INNER JOIN (SELECT FECHA,ID_BUQUE,ID_TERMINAL, ESTADO_BUQUE FROM REGISTRO_TERMINALES WHERE ESTADO_BUQUE='SALIDA' OR ESTADO_BUQUE='ENTRADA') ON ID_BUQUE = ID)";
		
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
			String tipo = rs.getString("TIPO");
			String nombreBuque = rs.getString("NOMBRE");
			String fecha = rs.getString("FECHA");
			String estado = rs.getString("ESTADO_BUQUE");
			String tipoCarga = rs.getString("TIPO");
			
			consulta.add(new ConsultaBuque(tipo, nombreBuque, fecha, estado, tipoCarga));
		}
		return new ListaConsultaBuques(consulta);
	}
}
