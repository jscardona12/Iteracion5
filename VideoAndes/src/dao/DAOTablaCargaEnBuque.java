package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import vos.Buque;
import vos.Carga;

public class DAOTablaCargaEnBuque extends DAOTablaGenerica{

	/**
	 * Método que, usando la conexión a la base de datos, saca todos ids de todas las cargas que se
	 * encuentren en un buque
	 * <b>SQL Statement:</b> SELECT ID_CARGA FROM CARGAENBUQUE WHERE FECHA_RETIRO IS NULL AND ID_BUQUE="+idBuque;
	 * @return Arraylist con los ids de las cargas de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Integer> darIdCargasAunEnBuque(int idBuque) throws SQLException, Exception {
		ArrayList<Integer> ids = new ArrayList<Integer>();

		String sql = "SELECT ID_CARGA FROM CARGAENBUQUE WHERE FECHA_RETIRO IS NULL AND ID_BUQUE="+idBuque;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id = Integer.parseInt(rs.getString("ID_CARGA"));
			ids.add(id);
		}
		return ids;
	}


	/**
	 * Método que busca el/los cargas con el nombre que entra como parámetro.
	 * @param name - Nombre de el/los cargas a buscar
	 * @return Arraylist con los cargas encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Carga> buscarCargasPorName(String name) throws SQLException, Exception {
		ArrayList<Carga> cargas = new ArrayList<Carga>();

		String sql = "SELECT * FROM CARGAS WHERE NAME ='" + name + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String destino = rs.getString("DESTINO");
			int id = Integer.parseInt(rs.getString("ID"));
			String origen = (rs.getString("ORIGEN"));
			int id_exportador = Integer.parseInt(rs.getString("ID_EXPORTADOR"));
			int numero = Integer.parseInt(rs.getString("NUMERO"));
			String tipo = rs.getString("TIPO");
			int volumen = Integer.parseInt(rs.getString("VOLUMEN"));
			double peso = Double.parseDouble(rs.getString("PESO"));
			double valor = Double.parseDouble(rs.getString("VALOR"));
			boolean rodada = rs.getBoolean("RODADA");
			boolean contenedor = rs.getBoolean("CONTENEDOR");
			cargas.add(new Carga(id, origen, id_exportador, numero, destino, tipo, volumen, peso, rodada, contenedor, valor));
		}

		return cargas;
	}

	/**
	 * Método que agrega el carga que entra como parámetro a la base de datos.
	 * @param carga - el carga a agregar. carga !=  null
	 * <b> post: </b> se ha agregado el carga a la base de datos en la transaction actual. pendiente que el carga master
	 * haga commit para que el carga baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el carga a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addCarga(Carga carga) throws SQLException, Exception {
		//
		//		String sql = "INSERT INTO CARGAS VALUES (";
		//		sql += carga.getID() + ",";
		//		sql += carga.getBuque() + ",";
		//		sql += carga.getExportador() + ",";
		//		sql += carga.getNumero() + ",";
		//		sql += carga.getTipoCarga() + ",";
		//		sql += carga.getVolumen() + ")";
		//		System.out.println("SQL stmt:" + sql);
		//
		//		PreparedStatement prepStmt = conn.prepareStatement(sql);
		//		recursos.add(prepStmt);
		//		prepStmt.executeQuery();

	}

	/**
	 * Método que actualiza el carga que entra como parámetro en la base de datos.
	 * @param carga - el carga a actualizar. carga !=  null
	 * <b> post: </b> se ha actualizado el carga en la base de datos en la transaction actual. pendiente que el carga master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el carga.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateCarga(Carga carga) throws SQLException, Exception {

		String sql = "UPDATE CARGAS SET ";
		//		sql += "nombre='" + carga.getNombre() + "',";
		//		sql += "agenteMaritimo='" + carga.getAgenteMaritimo()+"',";
		//		sql += "registroCapitania='" + carga.getRegistroCapitania()+"',";
		//		sql += "unidad='" + carga.getUnidad()+"',";
		//		sql += " WHERE id = " + carga.getID();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Método que elimina el carga que entra como parámetro en la base de datos.
	 * @param carga - el carga a borrar. carga !=  null
	 * <b> post: </b> se ha borrado el carga en la base de datos en la transaction actual. pendiente que el carga master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el carga.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteCarga(Carga carga) throws SQLException, Exception {

		String sql = "DELETE FROM CARGAS";
		sql += " WHERE id = " + carga.getID();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	public int darIdDeCarga(Carga carga) throws SQLException, Exception {
		int id = -1;

		String sql = "SELECT ID FROM CARGA ";
		sql += "WHERE NUMERO=" + carga.getNumero() + " AND ";
		sql += "VOLUMEN=" + carga.getVolumen() + " AND ";
		sql += "TIPO='" + carga.getTipoCarga() + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			id = Integer.parseInt(rs.getString("ID"));
		}

		return id;
	}

	public ArrayList<Carga> darCargasDestinoPuertoAndes(int id_b) throws SQLException, Exception {
		ArrayList<Carga> cargas = new ArrayList<Carga>();

		String sql = "SELECT * FROM CARGA c, CARGAENBUQUE cb "
				+"WHERE c.ID=cb.ID_CARGA AND cb.ID_BUQUE="+id_b+ 
				"AND c.DESTINO='PUERTO_ANDES'"+
				"AND cb.FECHA_RETIRADO IS NULL";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String destino = rs.getString("DESTINO");
			int id = Integer.parseInt(rs.getString("ID"));
			String origen = (rs.getString("ORIGEN"));
			int id_exportador = Integer.parseInt(rs.getString("ID_EXPORTADOR"));
			int numero = Integer.parseInt(rs.getString("NUMERO"));
			String tipo = rs.getString("TIPO");
			int volumen = Integer.parseInt(rs.getString("VOLUMEN"));
			double peso = Double.parseDouble(rs.getString("PESO"));
			String v="";
			double valor = Double.parseDouble((v=rs.getString("VALOR"))!=null?v:"0.0");
			boolean rodada = rs.getBoolean("RODADA");
			boolean contenedor = rs.getBoolean("CONTENEDOR");
			cargas.add(new Carga(id, origen, id_exportador, numero, destino, tipo, volumen, peso, rodada, contenedor, valor));
		}
		return cargas;
	}

	public ArrayList<Carga> darCargasEnBuque(int id_b) throws SQLException, Exception {
		ArrayList<Carga> cargas = new ArrayList<Carga>();

		String sql = "SELECT c.* FROM CARGA c, CARGAENBUQUE cb "
				+"WHERE c.ID=cb.ID_CARGA AND cb.ID_BUQUE="+id_b+ 
				" AND cb.FECHA_RETIRADO IS NULL";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String destino = rs.getString("DESTINO");
			int id = Integer.parseInt(rs.getString("ID"));
			String origen = (rs.getString("ORIGEN"));
			int id_exportador = Integer.parseInt(rs.getString("ID_EXPORTADOR"));
			int numero = Integer.parseInt(rs.getString("NUMERO"));
			String tipo = rs.getString("TIPO");
			int volumen = Integer.parseInt(rs.getString("VOLUMEN"));
			double peso = Double.parseDouble(rs.getString("PESO"));
			double valor = Double.parseDouble(rs.getString("VALOR"));
			boolean rodada = rs.getBoolean("RODADA");
			boolean contenedor = rs.getBoolean("CONTENEDOR");
			cargas.add(new Carga(id, origen, id_exportador, numero, destino, tipo, volumen, peso, rodada, contenedor, valor));
		}
		return cargas;
	}

	public void registrarRetiro(int idB, Carga c) throws SQLException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String fecha = "TO_DATE('" + sdf.format(new Date()) + "', 'yyyy/mm/dd hh24:mi:ss')";
		String sql = "UPDATE CARGAENBUQUE "+
				"SET FECHA_RETIRADO = "+fecha+" "+
				"WHERE ID_CARGA = "+c.getID()+" AND ID_BUQUE="+idB;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	public double darVolumenCargaEnBuque(int id_b) throws NumberFormatException, SQLException {
		double resp = 0;
		String sql = "SELECT * FROM CARGA c, CARGAENBUQUE cb "
				+"WHERE c.ID=cb.ID_BUQUE AND c.ID="+id_b+
				"AND cb.FECHA_RETIRADO IS NULL";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int peso = Integer.parseInt(rs.getString("VOLUMEN"));
			resp+=peso;
		}
		return resp;
	}


	public void cargarBuqueConCarga(Carga c, Buque b) throws SQLException  {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String fecha = "TO_DATE('" + sdf.format(new Date()) + "', 'yyyy/mm/dd hh24:mi:ss')";
		String sql = "INSERT INTO CARGAENBUQUE "+
				"VALUES(" + c.getID() + ", " + b.getID() + ", "+fecha+", null) ";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}
