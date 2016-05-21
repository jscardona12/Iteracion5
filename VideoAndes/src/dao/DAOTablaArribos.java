/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: ArriboAndes
 * Autor: Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import vos.Arribo;
import vos.ListaArribos;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicación
 * @author Juan
 */
public class DAOTablaArribos extends DAOTablaGenerica{


	/**
	 * Método que, usando la conexión a la base de datos, saca todos los arribos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM ARRIBOS;
	 * @return Arraylist con los arribos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Arribo> darArribos() throws SQLException, Exception {
		ArrayList<Arribo> arribos = new ArrayList<Arribo>();

		String sql = "SELECT * FROM ARRIBOS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Date fecha = rs.getDate("FECHA");
			int id_buque = Integer.parseInt(rs.getString("ID_BUQUE"));
			int id_muelle = Integer.parseInt(rs.getString("ID_MUELLE"));
			int id_origen = Integer.parseInt(rs.getString("ID_ORIGEN"));
			arribos.add(new Arribo(fecha, id_buque, id_muelle, id_origen));
		}
		return arribos;
	}


	/**
	 * Método que busca el/los arribos con el nombre que entra como parámetro.
	 * @param name - Nombre de el/los arribos a buscar
	 * @return Arraylist con los arribos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Arribo> buscarArribosEntreFechas(Date inicio, Date fin) throws SQLException, Exception {
		ArrayList<Arribo> arribos = new ArrayList<Arribo>();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String fechaInicio=sdf.format(inicio);
		String fechaFin=sdf.format(fin);
		String sql = "SELECT * FROM ARRIBOS WHERE DATE >='" + fechaInicio + "' AND DATE <='"+fechaFin+"'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Date fecha = rs.getDate("FECHA");
			int id_buque = Integer.parseInt(rs.getString("ID_BUQUE"));
			int id_muelle = Integer.parseInt(rs.getString("ID_MUELLE"));
			int id_origen = Integer.parseInt(rs.getString("ID_ORIGEN"));
			arribos.add(new Arribo(fecha, id_buque, id_muelle, id_origen));
		}

		return arribos;
	}

	/**
	 * Método que agrega el arribo que entra como parámetro a la base de datos.
	 * @param arribo - el arribo a agregar. arribo !=  null
	 * <b> post: </b> se ha agregado el arribo a la base de datos en la transaction actual. pendiente que el arribo master
	 * haga commit para que el arribo baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el arribo a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addArribo(Arribo arribo) throws SQLException, Exception {

		String sql = "INSERT INTO ARRIBOS VALUES (";
		sql += arribo.getIdBuque() + ",";
		sql += arribo.getFecha() + ")";

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
	public void deleteArribo(Arribo arribo) throws SQLException, Exception {

		String sql = "DELETE FROM ARRIBOS";
		sql += " WHERE id = " + arribo.getIdBuque()+" AND fecha="+arribo.getFecha();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public ArrayList<Arribo> darArribos(String nombreBuque, String tipoBuque, Date inicio, Date fin) throws Exception{
		ArrayList<Arribo> arribos = new ArrayList<Arribo>();
		String restriccionNombre=nombreBuque==null?"":" AND buque.NOMBRE="+nombreBuque;
		//		String restriccionTipoCarga=tipoCarga==null?"":" AND tc.TIPO="+tipoCarga;
		String restriccionTipoBuque=tipoBuque==null?"":" AND buque.TIPO="+tipoBuque;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String restriccionFechaI = inicio==null? "":" AND arribo.FECHA>='"+sdf.format(inicio) + "'";
		String restriccionFechaF = fin==null? "":" AND arribo.FECHA<='"+sdf.format(fin)+ "'";
		String sql=
				"SELECT arribo.* "+
						"FROM BUQUES buque, ARRIBOS arribo "+
						"WHERE arribo.ID_BUQUE=buque.ID"+restriccionNombre+
						restriccionTipoBuque+restriccionFechaI+restriccionFechaF;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String temp=rs.getString("ID_ORIGEN");
			Date fecha = rs.getDate("FECHA");
			int id_buque = Integer.parseInt(rs.getString("ID_BUQUE"));
			int id_muelle = Integer.parseInt(rs.getString("ID_MUELLE"));
			Integer id_origen = Integer.parseInt(temp==null?"0":temp);
			arribos.add(new Arribo(fecha, id_buque, id_muelle, id_origen));
		}

		return arribos;
	}


	public ListaArribos getArribosWhere(Date inicio, Date fin, String nBuque, String tipoBuque, String tipoCarga,
			String orden) throws SQLException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ArrayList<Arribo> arribos = new ArrayList<Arribo>();
		String sql="SELECT arr.*"+
				" FROM ARRIBOS arr INNER JOIN (SELECT b.ID, b.NOMBRE, b.TIPO, t.TIPO_CARGA"+
				" FROM   BUQUES b INNER JOIN TIPOCARGA t ON(b.ID=t.ID_BUQUE)"+
				" WHERE  b.NOMBRE='"+nBuque+
				"' AND b.TIPO ='"+tipoBuque+
				"' AND t.TIPO_CARGA ='" +tipoCarga+
				"') ta ON (arr.ID_BUQUE=ta.ID)"+
				"WHERE FECHA BETWEEN to_date('"+sdf.format(inicio)+
				"','YYYY-MM-DD HH24:MI:SS') AND to_date('"+sdf.format(fin)+"','YYYY-MM-DD HH24:MI:SS')ORDER BY("+orden+")";
		System.out.println(sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String temp=rs.getString("ID_ORIGEN");
			Date fecha = rs.getDate("FECHA");
			int id_buque = Integer.parseInt(rs.getString("ID_BUQUE"));
			int id_muelle = Integer.parseInt(rs.getString("ID_MUELLE"));
			Integer id_origen = Integer.parseInt(temp==null?"0":temp);
			arribos.add(new Arribo(fecha, id_buque, id_muelle, id_origen));
		}

		return new ListaArribos(arribos);
	}


	public ListaArribos getArribosWhereNot(Date inicio, Date fin, String nBuque, String tipoBuque, String tipoCarga,
			String orden) throws SQLException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ArrayList<Arribo> arribos = new ArrayList<Arribo>();
		String sql="SELECT arr.* FROM ARRIBOS arr INNER JOIN"+
				" (SELECT b.ID, b.NOMBRE, b.TIPO, t.TIPO_CARGA"+
				" FROM BUQUES b INNER JOIN TIPOCARGA t ON(b.ID=t.ID_BUQUE)"+
				" WHERE b.ID NOT IN"+
				"(SELECT b.ID FROM BUQUES b"+
				" INNER JOIN TIPOCARGA t ON(b.ID=t.ID_BUQUE)"+
				" WHERE b.NOMBRE   = '"+nBuque+
				"' AND b.TIPO      = '"+tipoBuque+
				"' AND t.TIPO_CARGA = '"+tipoCarga+
				"'))ta ON(arr.ID_BUQUE=ta.ID)"+
				"WHERE FECHA          < to_date('"+sdf.format(inicio)+"','YYYY-MM-DD HH24:MI:SS')"+
				"OR FECHA             > to_date('"+sdf.format(fin)+"','YYYY-MM-DD HH24:MI:SS')"+
				"ORDER BY("+orden+")";
		System.out.println(sql+"; --SI ES ESTE");
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String temp=rs.getString("ID_ORIGEN");
			Date fecha = rs.getDate("FECHA");
			int id_buque = Integer.parseInt(rs.getString("ID_BUQUE"));
			int id_muelle = Integer.parseInt(rs.getString("ID_MUELLE"));
			Integer id_origen = Integer.parseInt(temp==null?"0":temp);
			arribos.add(new Arribo(fecha, id_buque, id_muelle, id_origen));
		}

		return new ListaArribos(arribos);
	}
}
