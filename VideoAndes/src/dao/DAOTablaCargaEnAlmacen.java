package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import vos.Carga;
import vos.CargaEnAlmacen;

public class DAOTablaCargaEnAlmacen extends DAOTablaGenerica{

	/**
	 * Método que, usando la conexión a la base de datos, saca todos los cargasAlmacenadas de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM CARGA_ALMACENADAS;
	 * @return Arraylist con los cargasAlmacenadas de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Carga> darCargasEnAlmacen(int idAl) throws SQLException, Exception {
		ArrayList<Carga> cargas = new ArrayList<Carga>();

		String sql = "SELECT * FROM CARGAENALMACEN ca, CARGA c WHERE ca.ID_ALMACEN="+idAl
				+" AND c.ID=ca.ID_CARGA AND ca.FECHA_RETIRADO IS NULL";

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
			String v;
			double valor = Double.parseDouble((v=rs.getString("VALOR"))!=null?v:"0.0");
			boolean rodada = rs.getBoolean("RODADA");
			boolean contenedor = rs.getBoolean("CONTENEDOR");
			cargas.add(new Carga(id, origen, id_exportador, numero, destino, tipo, volumen, peso, rodada, contenedor, valor));
		}
		return cargas;
	}


	/**
	 * Método que agrega el cargaAlmacenada que entra como parámetro a la base de datos.
	 * @param cargaAlmacenada - el cargaAlmacenada a agregar. cargaAlmacenada !=  null
	 * <b> post: </b> se ha agregado el cargaAlmacenada a la base de datos en la transaction actual. pendiente que el cargaAlmacenada master
	 * haga commit para que el cargaAlmacenada baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el cargaAlmacenada a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addCargaEnAlmacen(CargaEnAlmacen carga) throws SQLException, Exception {

	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    String fecha = "TO_DATE('" + sdf.format(new Date()) + "', 'yyyy/mm/dd hh24:mi:ss')";
	  
		String sql = "INSERT INTO CARGAENALMACEN VALUES (";
		sql += carga.getIdCarga() + ",";
		sql += carga.getIdAlmacen() + ",";
		sql += fecha + ", NULL)";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
   * Método que elimina el video que entra como parámetro en la base de datos.
   * @param video - el video a borrar. video !=  null
   * <b> post: </b> se ha borrado el video en la base de datos en la transaction actual. pendiente que el video master
   * haga commit para que los cambios bajen a la base de datos.
   * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
   * @throws Exception - Cualquier error que no corresponda a la base de datos
   */
  public CargaEnAlmacen deleteCargaEnAlmacen(int idCarga) throws SQLException, Exception {
    
    CargaEnAlmacen c = null;
    
    String sql1 = "SELECT * FROM CARGAENALMACEN WHERE ID_CARGA=" + idCarga;
    
    PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
    ResultSet rs = prepStmt1.executeQuery();
    
    while (rs.next()) {
      int idAlmacen = Integer.parseInt(rs.getString("ID_ALMACEN"));
      c = new CargaEnAlmacen(idCarga, idAlmacen);
    }
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    String fecha = "TO_DATE('" + sdf.format(new Date()) + "', 'yyyy/mm/dd hh24:mi:ss')";
    String sql = "UPDATE CARGAENALMACEN "+
        "SET FECHA_RETIRADO = "+fecha+" "+
        "WHERE ID_CARGA = "+ idCarga + 
        " AND FECHA_RETIRADO IS NULL";

    PreparedStatement prepStmt = conn.prepareStatement(sql);
    recursos.add(prepStmt);
    prepStmt.executeQuery();
    
    return c;

  }


	public int cargaEsta(Integer id) throws SQLException {
		
	  int resp = -1;
		  String sql = "SELECT * "
	               + "FROM CARGAENALMACEN "
	               + "WHERE ID_CARGA=" + id+" AND FECHA_RETIRADO IS NULL";

	    PreparedStatement prepStmt = conn.prepareStatement(sql);
	    recursos.add(prepStmt);
	    ResultSet rs = prepStmt.executeQuery();
	    while (rs.next()) {
	      resp = Integer.parseInt(rs.getString("ID_ALMACEN"));
	    }
	    return resp;
	}	
}

