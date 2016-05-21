package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import vos.Salida;

public class DAOTablaSalidas extends DAOTablaGenerica{


	/**
	 * Método que, usando la conexión a la base de datos, saca todos los salidas de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM SALIDAS;
	 * @return Arraylist con los salidas de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Salida> darSalidas() throws SQLException, Exception {
		ArrayList<Salida> salidas = new ArrayList<Salida>();

		String sql = "SELECT * FROM SALIDAS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
		  int id_muelle = Integer.parseInt(rs.getString("ID_MUELLE"));
			Date fecha = rs.getDate("FECHA");
			int id_buque = Integer.parseInt(rs.getString("ID_BUQUE"));
			Salida s = new Salida(id_buque, id_muelle);
      s.setFecha(fecha);
      salidas.add(s);
		}
		return salidas;
	}


	/**
	 * Método que busca el/los salidas con el nombre que entra como parámetro.
	 * @param name - Nombre de el/los salidas a buscar
	 * @return Arraylist con los salidas encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Salida> buscarSalidasPorName(String name) throws SQLException, Exception {
		ArrayList<Salida> salidas = new ArrayList<Salida>();

		String sql = "SELECT * FROM SALIDAS WHERE NAME ='" + name + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name2 = rs.getString("NAME");
			int id = Integer.parseInt(rs.getString("ID"));
			int duration = Integer.parseInt(rs.getString("DURATION"));
//			salidas.add(new Salida(id, name2, duration));
		}

		return salidas;
	}

	/**
	 * Método que agrega el arribo que entra como parámetro a la base de datos.
	 * @param arribo - el arribo a agregar. arribo !=  null
	 * <b> post: </b> se ha agregado el arribo a la base de datos en la transaction actual. pendiente que el arribo master
	 * haga commit para que el arribo baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el arribo a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addSalida(Salida arribo) throws SQLException, Exception {

	  Date fecha = new Date();
	  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	  
		String sql = "INSERT INTO SALIDAS VALUES (";
		sql += arribo.getIdBuque() + ", '";
		sql += sdf.format(fecha) + "',";
		sql += arribo.getIdMuelle() + ")";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	

	/**
	 * Método que elimina el arribo que entra como parámetro en la base de datos.
	 * @param arribo - el arribo a borrar. arribo !=  null
	 * <b> post: </b> se ha borrado el arribo en la base de datos en la transaction actual. pendiente que el arribo master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el arribo.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteSalida(Salida arribo) throws SQLException, Exception {

		String sql = "DELETE FROM SALIDAS";
		sql += " WHERE id = " + arribo.getIdBuque()+" AND fecha="+arribo.getFecha();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public ArrayList<Salida> darSalidas(String nombreBuque, String tipoBuque, Date inicio, Date fin) throws Exception{
		ArrayList<Salida> salidas = new ArrayList<Salida>();
		String restriccionNombre=nombreBuque==null?"":" AND buque.NOMBRE="+nombreBuque;
//		String restriccionTipoCarga=tipoCarga==null?"":" AND tc.TIPO="+tipoCarga;
		String restriccionTipoBuque=tipoBuque==null?"":" AND buque.TIPO="+tipoBuque;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String restriccionFechaI = inicio==null? "":" AND salida.FECHA>='"+sdf.format(inicio)+ "'";
		String restriccionFechaF = fin==null? "":" AND salida.FECHA<='"+sdf.format(fin)+ "'";
		String sql=
				"SELECT salida.* "+
				"FROM BUQUES buque, SALIDAS salida "+
				"WHERE salida.ID_BUQUE=buque.ID "+restriccionNombre+
				restriccionTipoBuque+restriccionFechaI+restriccionFechaF;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		
		System.out.println(sql);
		
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Date fecha = rs.getDate("FECHA");
			int idBuque = Integer.parseInt(rs.getString("ID_BUQUE"));
			int idMuelle = Integer.parseInt(rs.getString("ID_MUELLE"));
			Salida s = new Salida(idBuque, idMuelle);
			s.setFecha(fecha);
			salidas.add(s);
		}

		return salidas;
	}
}

