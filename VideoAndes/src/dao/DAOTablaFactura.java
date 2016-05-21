/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: FacturaAndes
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
import java.util.HashSet;

import vos.Factura;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicación
 * @author Juan
 */
public class DAOTablaFactura extends DAOTablaGenerica{

  public int generarFactura(int idBuque, Date fecha) throws SQLException, Exception {
    
    HashSet<Integer> idsAlmacen = new HashSet<>();
    HashSet<Integer> idsCarga = new HashSet<>();
    
    int suma = 0;
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String fechaFormato = sdf.format(fecha);
    
    String sql = "SELECT * FROM MOVIMIENTOMARITIMO ";
    sql += "WHERE ID_BUQUE=" + idBuque + " AND FECHA='" + fechaFormato+ "'";
    
    int mmCount = 0;
    int precioMM = 1;
    
    PreparedStatement prepStmt = conn.prepareStatement(sql);
    recursos.add(prepStmt);
    ResultSet rs = prepStmt.executeQuery();
    
    while (rs.next()) {
      idsCarga.add(Integer.parseInt(rs.getString("ID_CARGA")));
      mmCount++;
    }
    
    sql = "SELECT PRECIO FROM PRECIOS WHERE ELEMENTO='TRANSPORTE MARITIMO'";
    prepStmt = conn.prepareStatement(sql);
    recursos.add(prepStmt);
    rs = prepStmt.executeQuery();
    
    while (rs.next()) {
      precioMM = Integer.parseInt(rs.getString("PRECIO"));
    }
    
    suma += mmCount * precioMM;
    
    // --------
    
    sql = "SELECT T.ID_ALMACEN FROM MOVIMIENTOMARITIMO M, MOVIMIENTOTERRESTRE T ";
    sql += "WHERE M.ID_BUQUE=" + idBuque + " AND ";
    sql += "T.FECHA='" + fechaFormato + "' AND ";
    sql += "M.ID_CARGA=T.ID_CARGA";
    
    int mtCount = 0;
    int precioMT = 1;
    
    prepStmt = conn.prepareStatement(sql);
    recursos.add(prepStmt);
    rs = prepStmt.executeQuery();
    
    while (rs.next()) {
      mtCount++;
      idsAlmacen.add(Integer.parseInt(rs.getString("ID_ALMACEN")));
    }
    
    // --------
    
    sql = "SELECT PRECIO FROM PRECIOS WHERE ELEMENTO='TRANSPORTE TERRESTRE'";
    prepStmt = conn.prepareStatement(sql);
    recursos.add(prepStmt);
    rs = prepStmt.executeQuery();
    
    while (rs.next()) {
      precioMT = Integer.parseInt(rs.getString("PRECIO"));
    }
    
    suma += mtCount * precioMT;
    
    int sumaEquipoYVolumen = 0;
    
    for (Integer idCarga : idsCarga) {
      sql = "SELECT PRECIO FROM EQUIPODEAPOYO " 
          + "WHERE ID_CARGA=" + idCarga + " AND "
          + "FECHA='" + fechaFormato+"'";
      prepStmt = conn.prepareStatement(sql);
      recursos.add(prepStmt);
      rs = prepStmt.executeQuery();
      while (rs.next()) {
        sumaEquipoYVolumen += Integer.parseInt(rs.getString("PRECIO"));
      }
      
      sql = "SELECT PRECIO FROM PRECIOS "
          + "WHERE ELEMENTO='POR_VOLUMEN'";
      
      prepStmt = conn.prepareStatement(sql);
      recursos.add(prepStmt);
      rs = prepStmt.executeQuery();
      
      int precioPorVolumen = 0;
      
      while (rs.next()) {
        precioPorVolumen = Integer.parseInt(rs.getString("PRECIO"));
      }
      
      sql = "SELECT VOLUMEN FROM CARGA "
          + "WHERE ID=" + idCarga;
      
      System.out.println(sql);
      
      prepStmt = conn.prepareStatement(sql);
      recursos.add(prepStmt);
      rs = prepStmt.executeQuery();
      
      while (rs.next()) {
        int volumen = Integer.parseInt(rs.getString("VOLUMEN"));
        sumaEquipoYVolumen += volumen * precioPorVolumen;
      }
    }
    
    suma += sumaEquipoYVolumen;
    
    // --------
    
    sql = "INSERT INTO FACTURAS VALUES("
        + idBuque + ", " + suma + ", '" + fechaFormato + "')";
    prepStmt = conn.prepareStatement(sql);
    recursos.add(prepStmt);
    prepStmt.executeQuery();
    
    return suma;
  }
  
  
  
	/**
	 * Método que, usando la conexión a la base de datos, saca todos los facturas de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM FACTURAS;
	 * @return Arraylist con los facturas de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Factura> darFacturas() throws SQLException, Exception {
		ArrayList<Factura> facturas = new ArrayList<Factura>();

		String sql = "SELECT * FROM FACTURAS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NAME");
			int id = Integer.parseInt(rs.getString("ID"));
			int duration = Integer.parseInt(rs.getString("DURATION"));
//			facturas.add(new Factura(id, name, duration));
		}
		return facturas;
	}
	
	/**
	 * Método que busca el/los facturas con el nombre que entra como parámetro.
	 * @param name - Nombre de el/los facturas a buscar
	 * @return Arraylist con los facturas encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Factura> buscarFacturasPorName(String name) throws SQLException, Exception {
		ArrayList<Factura> facturas = new ArrayList<Factura>();

		String sql = "SELECT * FROM FACTURAS WHERE NAME ='" + name + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name2 = rs.getString("NAME");
			int id = Integer.parseInt(rs.getString("ID"));
			int duration = Integer.parseInt(rs.getString("DURATION"));
//			facturas.add(new Factura(id, name2, duration));
		}

		return facturas;
	}

	/**
	 * Método que agrega el factura que entra como parámetro a la base de datos.
	 * @param factura - el factura a agregar. factura !=  null
	 * <b> post: </b> se ha agregado el factura a la base de datos en la transaction actual. pendiente que el factura master
	 * haga commit para que el factura baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el factura a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addFactura(Factura factura) throws SQLException, Exception {

		String sql = "INSERT INTO FACTURAS VALUES (";
//		sql += factura.getId() + ",'";
//		sql += factura.getName() + "',";
//		sql += factura.getDuration() + ")";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Método que actualiza el factura que entra como parámetro en la base de datos.
	 * @param factura - el factura a actualizar. factura !=  null
	 * <b> post: </b> se ha actualizado el factura en la base de datos en la transaction actual. pendiente que el factura master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el factura.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateFactura(Factura factura) throws SQLException, Exception {

		String sql = "UPDATE FACTURAS SET ";
//		sql += "name='" + factura.getName() + "',";
//		sql += "duration=" + factura.getDuration();
//		sql += " WHERE id = " + factura.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Método que elimina el factura que entra como parámetro en la base de datos.
	 * @param factura - el factura a borrar. factura !=  null
	 * <b> post: </b> se ha borrado el factura en la base de datos en la transaction actual. pendiente que el factura master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el factura.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteFactura(Factura factura) throws SQLException, Exception {

		String sql = "DELETE FROM FACTURAS";
//		sql += " WHERE id = " + factura.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
