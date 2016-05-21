package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import vos.Arribo;
import vos.Carga;
import vos.ListaArribos;
import vos.ListaMovimientoCarga2;
import vos.MovimientoCarga2;

public class DAOTablaCarga extends DAOTablaGenerica{

	/**
	 * Método que, usando la conexión a la base de datos, saca todos los cargas de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM CARGAS;
	 * @return Arraylist con los cargas de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Carga> darCargas() throws SQLException, Exception {
		ArrayList<Carga> cargas = new ArrayList<Carga>();

		String sql = "SELECT * FROM CARGAS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String destino = rs.getString("DESTINO");
			String tipo = rs.getString("TIPO");
			int id = Integer.parseInt(rs.getString("ID"));
			String origen = (rs.getString("ORIGEN"));
			int id_exportador = Integer.parseInt(rs.getString("ID_EXPORTADOR"));
			int numero = Integer.parseInt(rs.getString("NUMERO"));
			double volumen = Double.parseDouble(rs.getString("VOLUMEN"));
			double peso = Double.parseDouble(rs.getString("PESO"));
			boolean rodada = rs.getBoolean("RODADA");
			boolean contenedor = rs.getBoolean("CONTENEDOR");
			cargas.add(new Carga(id, origen, id_exportador, numero, destino, tipo, volumen, peso, rodada, contenedor));
		}
		return cargas;
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
			String tipo = rs.getString("TIPO");
			int id = Integer.parseInt(rs.getString("ID"));
			String origen = (rs.getString("ORIGEN"));
			int id_exportador = Integer.parseInt(rs.getString("ID_EXPORTAODR"));
			int numero = Integer.parseInt(rs.getString("NUMERO"));
			double volumen = Double.parseDouble(rs.getString("VOLUMEN"));
			double peso = Double.parseDouble(rs.getString("PESO"));
			boolean rodada = rs.getBoolean("RODADA");
			boolean contenedor = rs.getBoolean("CONTENEDOR");
			cargas.add(new Carga(id, origen, id_exportador, numero, destino, tipo, volumen, peso, rodada, contenedor));
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


	public Carga darCargaPorId(int id_carga) throws Exception{
		String sql = "SELECT * FROM CARGA WHERE ID="+id_carga;
		Carga resp = null;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String destino = rs.getString("DESTINO");
			String origen = rs.getString("ORIGEN");
			String tipo = rs.getString("TIPO");
			int id = Integer.parseInt(rs.getString("ID"));
			//			int id_buque = Integer.parseInt(rs.getString("ID_BUQUE"));
			int id_exportador = Integer.parseInt(rs.getString("ID_EXPORTADOR"));
			int numero = Integer.parseInt(rs.getString("NUMERO"));
			double volumen = Double.parseDouble(rs.getString("VOLUMEN"));
			double peso = Double.parseDouble(rs.getString("PESO"));
			boolean rodada = rs.getBoolean("RODADA");
			boolean contenedor = rs.getBoolean("CONTENEDOR");
			resp =(new Carga(id, origen, id_exportador, numero, destino, tipo, volumen, peso, rodada, contenedor));
		}
		return resp;
	}


	public ListaMovimientoCarga2 getMovimientosCargaExpConValorMayorA(boolean todosLosExportadores, Integer usuario,
			int valor, String tipoCarga) throws Exception{
		ArrayList<MovimientoCarga2> movimientos = new ArrayList<MovimientoCarga2>();
		String adicional= todosLosExportadores?"":" AND c.ID_EXPORTADOR="+usuario;
		String sql="SELECT c.ORIGEN, c.DESTINO, c.ID as ID_CARGA, c.TIPO, m.FECHA as FECHA_MOVIMIENTO FROM"+
				" CARGA c INNER JOIN("+
				" SELECT ID_CARGA, FECHA FROM MOVIMIENTOMARITIMO UNION"+
				" SELECT ID_CARGA, FECHA FROM MOVIMIENTOTERRESTRE) m on(c.ID=m.ID_CARGA)"+
				" WHERE c.ID_EXPORTADOR IN("+
				" SELECT ID_EXPORTADOR FROM"+
				" CARGA WHERE VALOR>"+valor+")AND c.TIPO = 'Ropa'"+adicional;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Date fechaMovimiento = rs.getDate("FECHA_MOVIMIENTO");
			int idCarga = Integer.parseInt(rs.getString("ID_CARGA"));
			String origen = rs.getString("ORIGEN");
			String destino = rs.getString("DESTINO");
			String tC = rs.getString("TIPO");
			movimientos.add(new MovimientoCarga2(idCarga, destino, origen, fechaMovimiento, tC));
		}

		return new ListaMovimientoCarga2(movimientos);
	}

}
