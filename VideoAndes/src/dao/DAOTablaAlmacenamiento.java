/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: AlmacenamientoAndes
 * Autor: Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vos.Almacenamiento;
import vos.Bodega;
import vos.Buque;
import vos.Cobertizo;
import vos.ListaAlmacenamientos;
import vos.ListaMovimientoAlmacen;
import vos.ListaMovimientoAlmacen;
import vos.MovimientoAlmacen;
import vos.ParametroBusqueda;
import vos.Patio;
import vos.Silo;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicación
 * @author Juan
 */
public class DAOTablaAlmacenamiento extends DAOTablaGenerica{




	/**
	 * Método que agrega el almacenamiento que entra como parámetro a la base de datos.
	 * @param almacenamiento - el almacenamiento a agregar. almacenamiento !=  null
	 * <b> post: </b> se ha agregado el almacenamiento a la base de datos en la transaction actual. pendiente que el almacenamiento master
	 * haga commit para que el almacenamiento baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el almacenamiento a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addAlmacenamiento(Almacenamiento almacenamiento) throws SQLException, Exception {

		String sql = "INSERT INTO ALMACENAMIENTOS VALUES (";
		sql += almacenamiento.getID() + ")";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}



	/**
	 * Método que elimina el almacenamiento que entra como parámetro en la base de datos.
	 * @param almacenamiento - el almacenamiento a borrar. almacenamiento !=  null
	 * <b> post: </b> se ha borrado el almacenamiento en la base de datos en la transaction actual. pendiente que el almacenamiento master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el almacenamiento.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteAlmacenamiento(Almacenamiento almacenamiento) throws SQLException, Exception {

		String sql = "DELETE FROM ALMACENAMIENTOS";
		sql += " WHERE id = " + almacenamiento.getID();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}



	public Almacenamiento darAlmacenPorId(int idAlmacen) throws SQLException {
		Almacenamiento resp = null;
		String sql = "SELECT * "
				+ "FROM ALMACEN a, BODEGAS b, COBERTIZOS c, SILOS s, PATIOS p "
				+ "WHERE a.ID=" + idAlmacen
				+ " AND (b.ID = a.ID OR c.ID = a.ID OR s.ID = a.ID OR p.ID = a.ID)";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		while (rs.next()) {
			int id = Integer.parseInt(rs.getString("ID"));
			String ancho = rs.getString("ANCHO");
			Integer anchoBodega = ancho==null?null:Integer.parseInt(ancho);
			String largo = rs.getString("LARGO");
			Integer largoBodega = largo==null?null:Integer.parseInt(largo);
			String separacion = rs.getString("SEPARACION");
			Integer separacionBodega = separacion==null?null:Integer.parseInt(separacion);
			Boolean tienePlataformaBodega = rs.getBoolean("TIENEPLATAFORMA");

			ancho = rs.getString("ANCHO_1");
			Integer anchoCobertizo = ancho==null?null:Integer.parseInt(ancho);
			largo = rs.getString("LARGO_1");
			Integer largoCobertizo = largo==null?null:Integer.parseInt(largo);

			String nombreSilo= rs.getString("NOMBRE");
			String capacidad = rs.getString("CAPACIDAD");
			Integer capacidadSilo = capacidad==null?null:Integer.parseInt(capacidad);

			ancho = rs.getString("ANCHO_2");
			Integer anchoPatio = ancho==null?null:Integer.parseInt(ancho);
			largo = rs.getString("LARGO_2");
			Integer largoPatio = largo==null?null:Integer.parseInt(largo);
			if(anchoBodega!=null){
				resp = new Bodega(id, largoBodega, anchoBodega, tienePlataformaBodega, separacionBodega);
			}else if(anchoCobertizo!=null){
				resp = new Cobertizo(id, largoCobertizo, anchoCobertizo);
			}else if(nombreSilo!=null){
				resp = new Silo(id, capacidadSilo, nombreSilo);
			}else if(anchoPatio!=null){
				resp = new Patio(id, largoPatio, anchoPatio);
			}


		}
		return resp;
	}

	public ListaAlmacenamientos consultarAlmacenamientos(int idPersona, String rolPersona) throws SQLException, Exception {
		// si rol es null no mirar eso

		List<Patio> listaP = new ArrayList<>();
		List<Silo> listaS = new ArrayList<>();
		List<Bodega> listaB = new ArrayList<>();
		List<Cobertizo> listaC = new ArrayList<>();

		String sql = "SELECT b.* "
				+ "FROM ALMACEN a, BODEGAS b "
				+ "WHERE a.ID = b.ID";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while(rs.next()) {
			int id = Integer.parseInt(rs.getString("ID")); 
			int ancho = Integer.parseInt(rs.getString("ANCHO")); 
			int largo = Integer.parseInt(rs.getString("LARGO")); 
			int separacionColumnas = Integer.parseInt(rs.getString("SEPARACIONCOLUMNAS")); 
			boolean tienePlataforma = Integer.parseInt(rs.getString("TIENEPLATAFORMA")) == 1;
			Bodega b = new Bodega(id, largo, ancho, tienePlataforma, separacionColumnas);
			listaB.add(b);
		}

		sql = "SELECT p.* "
				+ "FROM ALMACEN a, PATIOS p "
				+ "WHERE a.ID = p.ID";

		prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		rs = prepStmt.executeQuery();

		while(rs.next()) {
			int id = Integer.parseInt(rs.getString("ID")); 
			int ancho = Integer.parseInt(rs.getString("ANCHO")); 
			int largo = Integer.parseInt(rs.getString("LARGO")); 

			Patio p = new Patio(id, largo, ancho);
			listaP.add(p);
		}

		sql = "SELECT c.* "
				+ "FROM ALMACEN a, COBERTIZOS c "
				+ "WHERE a.ID = c.ID";

		prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		rs = prepStmt.executeQuery();

		while(rs.next()) {
			int id = Integer.parseInt(rs.getString("ID")); 
			int ancho = Integer.parseInt(rs.getString("ANCHO")); 
			int largo = Integer.parseInt(rs.getString("LARGO")); 

			Cobertizo c = new Cobertizo(id, largo, ancho);
			listaC.add(c);
		}

		sql = "SELECT s.* "
				+ "FROM ALMACEN a, SILOS s "
				+ "WHERE a.ID = s.ID";

		prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		rs = prepStmt.executeQuery();

		while(rs.next()) {
			int id = Integer.parseInt(rs.getString("ID")); 
			String nombre = rs.getString("NOMBRE"); 
			int capacidad = Integer.parseInt(rs.getString("CAPACIDAD")); 

			// Cambiar atributos
			Silo s = new Silo(id, capacidad, nombre);
			listaS.add(s);
		}

		return new ListaAlmacenamientos(listaS, listaC, listaB, listaP);
	}



	public ListaMovimientoAlmacen getInfoAlmacenamientos(int area1, int area2) throws Exception{
		ArrayList<MovimientoAlmacen> movimientos = new ArrayList<MovimientoAlmacen>();
		String sql="SELECT ID, TIPO as TIPO_CARGA,ORIGEN as ORIGEN_CARGA, DESTINO as DESTINO_CARGA,FECHA, TIPO_MOV, ID_ALMACENAMIENTO FROM"+ 
				" CARGA INNER JOIN("+
				" SELECT ID_CARGA, FECHA, TIPO||' respecto al buque' as TIPO_MOV, ID_ALMACENAMIENTO"+
				" FROM MOVIMIENTOMARITIMO"+
				" UNION"+
				" SELECT ID_CARGA, FECHA, TIPO||' respecto al almacen' as TIPO_MOV, ID_ALMACEN"+
				" FROM MOVIMIENTOTERRESTRE"+
				") ON (ID=ID_CARGA)"+
				" WHERE ID_ALMACENAMIENTO="+area1+" OR ID_ALMACENAMIENTO="+area2;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Date fechaMovimiento = rs.getDate("FECHA");
			int idCarga = Integer.parseInt(rs.getString("ID"));
			int idAlmacenamiento = Integer.parseInt(rs.getString("ID_ALMACENAMIENTO"));
			String origen = rs.getString("ORIGEN_CARGA");
			String destino = rs.getString("DESTINO_CARGA");
			String tC = rs.getString("TIPO_CARGA");
			String tipoMovimiento = rs.getString("TIPO_MOV");
			movimientos.add(new MovimientoAlmacen(idCarga, destino, origen, fechaMovimiento, tC, tipoMovimiento, idAlmacenamiento));
		}

		return new ListaMovimientoAlmacen(movimientos);
	}



	public ListaAlmacenamientos consultarAreas(ParametroBusqueda pb) throws SQLException {
		List<Patio> listaP = new ArrayList<>();
		List<Silo> listaS = new ArrayList<>();
		List<Bodega> listaB = new ArrayList<>();
		List<Cobertizo> listaC = new ArrayList<>();

		String sql = "SELECT b.* "
				+ "FROM ALMACEN a, BODEGAS b "
				+ "WHERE a.ID = b.ID";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while(rs.next()) {
			int id = Integer.parseInt(rs.getString("ID")); 
			int ancho = Integer.parseInt(rs.getString("ANCHO")); 
			int largo = Integer.parseInt(rs.getString("LARGO")); 
			int separacionColumnas = Integer.parseInt(rs.getString("SEPARACIONCOLUMNAS")); 
			boolean tienePlataforma = Integer.parseInt(rs.getString("TIENEPLATAFORMA")) == 1;
			Bodega b = new Bodega(id, largo, ancho, tienePlataforma, separacionColumnas);
			listaB.add(b);
		}

		sql = "SELECT p.* "
				+ "FROM ALMACEN a, PATIOS p "
				+ "WHERE a.ID = p.ID";

		prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		rs = prepStmt.executeQuery();

		while(rs.next()) {
			int id = Integer.parseInt(rs.getString("ID")); 
			int ancho = Integer.parseInt(rs.getString("ANCHO")); 
			int largo = Integer.parseInt(rs.getString("LARGO")); 

			Patio p = new Patio(id, largo, ancho);
			listaP.add(p);
		}

		sql = "SELECT c.* "
				+ "FROM ALMACEN a, COBERTIZOS c "
				+ "WHERE a.ID = c.ID";

		prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		rs = prepStmt.executeQuery();

		while(rs.next()) {
			int id = Integer.parseInt(rs.getString("ID")); 
			int ancho = Integer.parseInt(rs.getString("ANCHO")); 
			int largo = Integer.parseInt(rs.getString("LARGO")); 

			Cobertizo c = new Cobertizo(id, largo, ancho);
			listaC.add(c);
		}

		sql = "SELECT s.* "
				+ "FROM ALMACEN a, SILOS s "
				+ "WHERE a.ID = s.ID";

		prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		rs = prepStmt.executeQuery();

		while(rs.next()) {
			int id = Integer.parseInt(rs.getString("ID")); 
			String nombre = rs.getString("NOMBRE"); 
			int capacidad = Integer.parseInt(rs.getString("CAPACIDAD")); 

			// Cambiar atributos
			Silo s = new Silo(id, capacidad, nombre);
			listaS.add(s);
		}

		return new ListaAlmacenamientos(listaS, listaC, listaB, listaP);
	}
}
