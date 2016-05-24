/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: VideoAndes
 * Autor: Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import vos.Carga;
import vos.Exportador;
import vos.ExportadorUnificado;
import vos.InfoExportador;
import vos.ListaCargas;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicación
 * @author Juan
 */
public class DAOTablaExportador extends DAOTablaGenerica{



	/**
	 * Método que busca el/los videos mas alquilados.
	 * @return Arraylist con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	//	tipo de exportador, caracter�sticas del buque o de la carga
	//	involucrada en un rango de fechas. Debe ofrecerse la posibilidad de agrupamiento y ordenamiento de las respuestas
	//	seg�n los intereses del usuario que consulta.
	public InfoExportador darInfoExportador(int id_exportador, Boolean natural,
			String tipoBuque, Date inicio, Date fin, String orden)  throws SQLException, Exception {
		InfoExportador ie=null;
		String restriccionNatural=natural==null?"":(natural?" AND pe.ESNATURAL=1":" AND pe.ESNATURAL=0");
		String restriccionBuque=tipoBuque==null?"":" AND b.TIPO='"+tipoBuque+"'";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String restriccionFechaI = inicio==null? "":" AND mm.FECHA>='"+sdf.format(inicio)+"'";
		String restriccionFechaF = fin==null? "":" AND mm.FECHA<='"+sdf.format(fin)+"'";
		String restOrden= orden==null?"":" ORDER BY "+orden;
		String sql=
				"SELECT exp.*,pe.*, CANTUSO, b.ID id_buque, c.*, c.id id_carga "+
						"FROM (SELECT count(*) CANTUSO "+
						"FROM MOVIMIENTOMARITIMO mm, CARGA c, BUQUES b "+
						"WHERE mm.ID_CARGA=c.ID AND mm.ID_BUQUE=b.ID AND c.ID_exportador= "+id_exportador
						+restriccionBuque+restriccionFechaI+restriccionFechaF+
						" group by c.ID_EXPORTADOR)car, EXPORTADORES exp, PERSONAS pe, MOVIMIENTOMARITIMO mm, BUQUES b, CARGA c "+
						"WHERE exp.ID=pe.ID AND c.id=mm.ID_CARGA AND mm.ID_BUQUE=b.ID AND c.ID_EXPORTADOR=exp.ID AND exp.ID="+id_exportador+restriccionBuque+restriccionNatural+restriccionFechaI+restriccionFechaF+restOrden;

		System.out.println(sql);
		//		if(true) throw new Exception(sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		ArrayList<Carga> cargas = new ArrayList<Carga>();

		int i=0;
		while (rs.next()) {
			if(i==0){
				String nombre = rs.getString("NOMBRE");
				String rut = rs.getString("RUT");
				int id = Integer.parseInt(rs.getString("ID"));
				boolean esNatural = Integer.parseInt(rs.getString("ESNATURAL"))==1;
				int cantidadUso = Integer.parseInt(rs.getString("CANTUSO"));
				ie=new InfoExportador(nombre, id, esNatural, rut, cantidadUso);
			}

			String destino = rs.getString("DESTINO");
			String tipo = rs.getString("TIPO");
			int id = Integer.parseInt(rs.getString("ID"));
			String origen = (rs.getString("ORIGEN"));
			int id_exp = Integer.parseInt(rs.getString("ID_EXPORTAODR"));
			int numero = Integer.parseInt(rs.getString("NUMERO"));
			double volumen = Double.parseDouble(rs.getString("VOLUMEN"));
			double peso = Double.parseDouble(rs.getString("PESO"));
			double valor = Double.parseDouble(rs.getString("VALOR"));
			boolean rodada = rs.getBoolean("RODADA");
			boolean contenedor = rs.getBoolean("CONTENEDOR");
			cargas.add(new Carga(id, origen, id_exportador, numero, destino, tipo, volumen, peso, rodada, contenedor, valor));

			i++;
		}
		if(ie!=null)ie.setCargas(new ListaCargas(cargas));

		return ie;
	}
	/**
	 * Método que agrega el exportador que entra como parámetro a la base de datos.
	 * @param exportador - el exportador a agregar. video !=  null
	 * <b> post: </b> se ha agregado el exportador a la base de datos en la transaction actual.
	 * pendiente que el video master
	 * haga commit para que el exportador baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addExportador(Exportador exportador) throws SQLException, Exception {

		String sql = "INSERT INTO EXPORTADORES VALUES (";
		sql += exportador.getID() + ", '";
		sql += exportador.getRUT() + "')";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public ArrayList<Exportador> darExportadores() throws Exception {
		ArrayList<Exportador> exportadores = new ArrayList<Exportador>();

		String sql = "SELECT * FROM EXPORTADORES imp, PERSONAS pe WHERE imp.ID = pe.ID";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NOMBRE");
			int id = Integer.parseInt(rs.getString("ID"));
			String rut = rs.getString("RUT");
			boolean esNatural = Integer.parseInt(rs.getString("ESNATURAL"))==1;
			exportadores.add(new Exportador(name, id, esNatural, rut));
		}
		return exportadores;
	}
	public void aplicarDescuento(String rut, int descuento) throws SQLException {
		String sql = "UPDATE EXPORTADORES SET ";
		sql += "DESCUENTO=" + descuento;
		sql += " WHERE RUT = '" + rut +"'";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	public boolean existeExportador(String rut) throws SQLException {
		String sql = "SELECT * FROM EXPORTADORES WHERE RUT = '" + rut+"'";
		System.out.println(sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		return prepStmt.executeQuery().next();
	}
	public ArrayList<ExportadorUnificado> costoExportadores(String fechas) throws SQLException{
		ArrayList<ExportadorUnificado> eu = new ArrayList<>();
		String sql = "select NOMBRE, COSTO FROM"
				+" (SELECT NOMBRE, ID FROM PERSONAS, FACTURAS"
				+" WHERE ID_EXPORTADOR=ID),"
				+"(SELECT ID_EXPORTADOR, SUM(PRECIO_TOTAL) AS COSTO FROM FACTURAS"
				+"where FECHA_FACTURA >= '" + fechas.split(" ")[0] + "' "
				+ "AND FECHA_FACTURA <= '" + fechas.split(" ")[1]
				+" GROUP BY PRECIO_TOTAL, ID_EXPORTADOR)"
				+" WHERE ID_EXPORTADOR=ID";
				System.out.println(sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		while(rs.next()){
			String nombre = rs.getString("NOMBRE");
			double costo = Double.parseDouble(rs.getString("COSTO"));
			eu.add(new ExportadorUnificado(nombre,costo));
		}

		return eu;
	} 
}
