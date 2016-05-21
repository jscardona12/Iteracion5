/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: TipoCargaAndes
 * Autor: Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import vos.Buque;
import vos.Carga;
import vos.TipoCarga;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicación
 * @author Juan
 */
public class DAOTablaTipoCarga extends DAOTablaGenerica{

	/**
	 * Método que, usando la conexión a la base de datos, saca todos los tipoCargas de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM TIPOCARGA;
	 * @return Arraylist con los tipoCargas de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<TipoCarga> darTipoCargas() throws SQLException, Exception {
		ArrayList<TipoCarga> tipoCargas = new ArrayList<TipoCarga>();

		String sql = "SELECT * FROM TIPOCARGA";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String tipoCarga = rs.getString("TIPOCARGA");
			int idBuque = Integer.parseInt(rs.getString("ID_BUQUE"));
			boolean esRodada = Integer.parseInt(rs.getString("ES_RODADA")) == 1;
			boolean esContenedor = Integer.parseInt(rs.getString("ES_CONTENEDOR")) == 1;
			tipoCargas.add(new TipoCarga(idBuque, tipoCarga, esRodada, esContenedor));
		}
		return tipoCargas;
	}

	/**
	 * Método que agrega el tipoCarga que entra como parámetro a la base de datos.
	 * @param tipoCarga - el tipoCarga a agregar. tipoCarga !=  null
	 * <b> post: </b> se ha agregado el tipoCarga a la base de datos en la transaction actual. pendiente que el tipoCarga master
	 * haga commit para que el tipoCarga baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el tipoCarga a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void registrarTipoCarga(TipoCarga tipoCarga) throws SQLException, Exception {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	  
		String sql = "INSERT INTO TIPOCARGA VALUES (";
		sql += tipoCarga.getIdBuque() + ",'";
		sql += tipoCarga.getTipoCarga() + "',";
		sql += "?,";
		sql += (tipoCarga.esCargaRodada() ? 1 : 0) + ",";
		sql += (tipoCarga.esContenedor() ? 1 : 0) + ")";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setTimestamp(1, new Timestamp(new Date().getTime()));
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void registrarRetiroCarga(Carga c, Buque b) throws SQLException {
//		// TODO Auto-generated method stub
//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//		String fecha = sdf.format(new Date());
//		String sql = "UPDATE CARGAENBUQUE"+
//				"SET FECHA_RETIRADO = '"+fecha+"'"+
//				"WHERE ID_CARGA = "+c.getID()+" AND ID_BUQUE="+b.getID();
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		prepStmt.executeQuery();
	}

}
