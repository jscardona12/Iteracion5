
package dao;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Barco;
import vos.Carga;
import vos.Muelle;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicación
 * @author Juan
 */
public class DAOBarco {


	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Método constructor que crea DAOVideo
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOBarco() 
	{
		recursos = new ArrayList<Object>();
	}

	/**
	 * Método que cierra todos los recursos que estan enel arreglo de recursos
	 * <b>post: </b> Todos los recurso del arreglo de recursos han sido cerrados
	 */
	public void cerrarRecursos() {
		for(Object ob : recursos){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}

	/**
	 * Método que inicializa la connection del DAO a la base de datos con la conexión que entra como parámetro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}


	/**
	 * Método que, usando la conexión a la base de datos, saca todos los videos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM VIDEOS;
	 * @return Arraylist con los videos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Barco> darBarcos() throws SQLException, Exception 
	{
		boolean ev = true;
		boolean s = true;
		ArrayList<Barco> barco = new ArrayList<Barco>();

		String sql = "SELECT * FROM BARCOS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) 
		{
			String name = rs.getString("NAME");
			int id = Integer.parseInt(rs.getString("ID"));
			String pto = rs.getString("PUERTO_ORIGEN");
			String ptd = rs.getString("PUERTO_DESTINO");
			int cap = Integer.parseInt(rs.getString("CAPACIDAD"));
			String na = rs.getString("NOMBRE_AGENTE");
			String rc = rs.getString("REGISTRO");
			String t = rs.getString("TIPO");
			int idc = Integer.parseInt(rs.getString("ID_TIPO_CARGA"));
			int idr = Integer.parseInt(rs.getString("ID_RESERVA"));
			int des = Integer.parseInt(rs.getString("DESHABILITADOS"));			
			barco.add(new Barco(name, pto, ptd, id, cap, na, rc, t, idc,idr,des));
		}
		return barco;
	}


	/**
	 * Método que busca el/los videos con el nombre que entra como parámetro.
	 * @param name - Nombre de el/los videos a buscar
	 * @return Arraylist con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public Barco buscarBarcoPorID(int id) throws SQLException, Exception {
		
		boolean ev = true;
		boolean s = true;
		
		Barco barco = null;

		String sql = "SELECT * FROM BARCOS WHERE ID =" + id ;

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) 
		{
			String name2 = rs.getString("NAME");
			int id2 = Integer.parseInt(rs.getString("ID"));
			String pto = rs.getString("PUERTO_ORIGEN");
			String ptd = rs.getString("PUERTO_DESTINO");
			int cap = Integer.parseInt(rs.getString("CAPACIDAD"));
			String na = rs.getString("NOMBRE_AGENTE");
			String rc = rs.getString("REGISTRO");
			String t = rs.getString("TIPO");
			int idc = Integer.parseInt(rs.getString("ID_TIPO_CARGA"));
			int idr = Integer.parseInt(rs.getString("ID_RESERVA"));
			int des = Integer.parseInt(rs.getString("DESHABILITADOS"));			
			barco = new Barco(name2, pto, ptd, id, cap, na, rc, t, idc,idr,des);
		}

		return barco;
	}

	/**
	 * Método que agrega el barco que entra como parámetro a la base de datos.
	 * @param barco - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addBarco(Barco barco) throws SQLException, Exception {

		String sql = "INSERT INTO BARCOS VALUES (";
		sql += barco.getId() + ",'";
		sql += barco.getNombre() + "','";
		sql += barco.getPuertoOrigen() + "','";
		sql += barco.getPuertoDestino() + "',";
		sql += barco.getCapacidad() + ",'";
		sql += barco.getNombreAgente()+ "','";
		sql += barco.getRegistroCapitania() + "','";
		sql += barco.getTipoBarco() + "',";
		sql += 1 + ",";
		sql += 2 + ")";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Método que actualiza el video que entra como parámetro en la base de datos.
	 * @param barco - el barco a actualizar. barco !=  null
	 * <b> post: </b> se ha actualizado el video en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateBarco(Barco barco) throws SQLException, Exception {

		String sql = "UPDATE BARCOS SET ";
		sql += "NAME='" + barco.getNombre() + "',";
		sql += "PUERTO_ORIGEN='" + barco.getPuertoOrigen() + "','";
		sql += "PUERTO_DESTINO='" +barco.getPuertoDestino() + "',";
		sql += "CAPACIDAD='" +barco.getCapacidad() + ",'";
		sql += "NOMBRE_AGENTE='" +barco.getNombreAgente()+ "','";
		sql += "REGISTRO='" +barco.getRegistroCapitania() + "','";
		sql += "TIPO='" +barco.getTipoBarco() + "',";
		sql += " WHERE ID = " + barco.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Método que elimina el video que entra como parámetro en la base de datos.
	 * @param barco - el video a borrar. video !=  null
	 * <b> post: </b> se ha borrado el video en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteBarco(Barco barco) throws SQLException, Exception {

		String sql = "DELETE FROM BARCOS";
		sql += " WHERE id = " + barco.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public int getDiferenciaCarga(int area, Barco barco) throws SQLException
	{
		
		int verificar =0;
		String sql1 = "SELECT AREAS_DE_ALMACENAMIENTO.TIPO,AREAS_DE_ALMACENAMIENTO.ESTADO";
		sql1 += " FROM AREAS_DE_ALMACENAMIENTO"
				+ " WHERE AREAS_DE_ALMACENAMIENTO.ID ="+ area;

		System.out.println("SQL stmt:" + sql1);
		PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
		recursos.add(prepStmt1);
		ResultSet rs1 = prepStmt1.executeQuery();
		if(rs1.next())
		{
			String tipo = rs1.getString("TIPO");
			int estado = Integer.parseInt(rs1.getString("ESTADO"));
			if(estado == 0)
			{
				String sql2 = "SELECT " + tipo + "S.";
				if(tipo.equalsIgnoreCase("SILO"))
				{
					sql2 += "CAPACIDAD FROM SILOS"
							+ " WHERE SILOS.ID = " + area;
				}
				else
				{
					sql2 += "AREA FROM " + tipo
							+ "S WHERE " + tipo +"S.ID = " + area;
				}
				System.out.println("SQL stmt:" + sql2);
				PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
				recursos.add(prepStmt2);
				ResultSet rs2 = prepStmt2.executeQuery();
				if(rs2.next())
				{
					String b = "";
					if(tipo.equalsIgnoreCase("SILO"))
						b = "CAPACIDAD";
					else b = "AREA";

					int capacidad = Integer.parseInt(rs2.getString(b));

					String sql3 = "SELECT CARGAS.VOLUMEN, CARGAS.TIPO"
							+ " FROM CARGAS"
							+ " WHERE CARGAS.ID = " +barco.getCarga();

					System.out.println("SQL stmt:" + sql3);
					PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
					recursos.add(prepStmt3);
					ResultSet rs3 = prepStmt3.executeQuery();
					if(rs3.next())
					{
						System.out.println("A");
						int volCarga = Integer.parseInt(rs3.getString("VOLUMEN"));
						String tipoCarga = rs3.getString("TIPO");
						System.out.println(capacidad+","+ volCarga);
						System.out.println(tipo);

						if((tipo.equalsIgnoreCase("BODEGA")
								&& tipoCarga.equalsIgnoreCase("CONTENEDORES"))||
								(tipo.equalsIgnoreCase("PATIO")
										&& tipoCarga.equalsIgnoreCase("VEHICULOS"))||
								(tipo.equalsIgnoreCase("COBERTIZO")
										&& tipoCarga.equalsIgnoreCase("BIOTIPOS"))||
								(tipo.equalsIgnoreCase("SILO")
										&& tipoCarga.equalsIgnoreCase("GRANEL SOLIDO"))
								&& (capacidad > volCarga)
								)
						{
							System.out.println("B");
							verificar = volCarga - capacidad;
						}

					}
				}
			}
		}


		return verificar;
	}
	
	public int getVolumenCarga(int area, Barco barco) throws SQLException
	{
		
		int verificar =0;
		String sql1 = "SELECT AREAS_DE_ALMACENAMIENTO.TIPO,AREAS_DE_ALMACENAMIENTO.ESTADO";
		sql1 += " FROM AREAS_DE_ALMACENAMIENTO"
				+ " WHERE AREAS_DE_ALMACENAMIENTO.ID ="+ area;

		System.out.println("SQL stmt:" + sql1);
		PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
		recursos.add(prepStmt1);
		ResultSet rs1 = prepStmt1.executeQuery();
		if(rs1.next())
		{
			String tipo = rs1.getString("TIPO");
			int estado = Integer.parseInt(rs1.getString("ESTADO"));
			if(estado == 0)
			{
				String sql2 = "SELECT " + tipo + "S.";
				if(tipo.equalsIgnoreCase("SILO"))
				{
					sql2 += "CAPACIDAD FROM SILOS"
							+ " WHERE SILOS.ID = " + area;
				}
				else
				{
					sql2 += "AREA FROM " + tipo
							+ "S WHERE " + tipo +"S.ID = " + area;
				}
				System.out.println("SQL stmt:" + sql2);
				PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
				recursos.add(prepStmt2);
				ResultSet rs2 = prepStmt2.executeQuery();
				if(rs2.next())
				{
					String b = "";
					if(tipo.equalsIgnoreCase("SILO"))
						b = "CAPACIDAD";
					else b = "AREA";

					int capacidad = Integer.parseInt(rs2.getString(b));

					String sql3 = "SELECT CARGAS.VOLUMEN, CARGAS.TIPO"
							+ " FROM CARGAS"
							+ " WHERE CARGAS.ID = " +barco.getCarga();

					System.out.println("SQL stmt:" + sql3);
					PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
					recursos.add(prepStmt3);
					ResultSet rs3 = prepStmt3.executeQuery();
					if(rs3.next())
					{
						System.out.println("A");
						int volCarga = Integer.parseInt(rs3.getString("VOLUMEN"));
						String tipoCarga = rs3.getString("TIPO");
						System.out.println(capacidad+","+ volCarga);
						System.out.println(tipo);

						if((tipo.equalsIgnoreCase("BODEGA")
								&& tipoCarga.equalsIgnoreCase("CONTENEDORES"))||
								(tipo.equalsIgnoreCase("PATIO")
										&& tipoCarga.equalsIgnoreCase("VEHICULOS"))||
								(tipo.equalsIgnoreCase("COBERTIZO")
										&& tipoCarga.equalsIgnoreCase("BIOTIPOS"))||
								(tipo.equalsIgnoreCase("SILO")
										&& tipoCarga.equalsIgnoreCase("GRANEL SOLIDO"))
								&& (capacidad > volCarga)
								)
						{
							System.out.println("B");
							verificar = volCarga ;
						}

					}
				}
			}
		}


		return verificar;
	}
	
	public String gettipoCarga(Barco b) throws SQLException
	{
		String sql3 = "SELECT CARGAS.TIPO"
				+ " FROM CARGAS"
				+ " WHERE CARGAS.ID = " +b.getCarga();

		System.out.println("SQL stmt:" + sql3);
		PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
		recursos.add(prepStmt3);
		ResultSet rs3 = prepStmt3.executeQuery();
		if(rs3.next())
		{
			return rs3.getString("TIPO");
		}
		return "";
	}
}
	
	



