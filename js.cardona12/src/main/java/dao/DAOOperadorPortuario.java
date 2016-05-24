
package dao;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dtm.CargaUnificada;
import vos.Barco;
import vos.Carga;
import vos.ConsultaAreas;
import vos.ConsultaBarcos;
import vos.ConsultaMovimientos;
import vos.OperadorPortuario;
import vos.respConsultaBarcos;


/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicación
 * @author Juan
 */
public class DAOOperadorPortuario {


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
	public DAOOperadorPortuario() 
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
	public ArrayList<OperadorPortuario> darOperadores() throws SQLException, Exception 
	{

		ArrayList<OperadorPortuario> operadores = new ArrayList<OperadorPortuario>();

		String sql = "SELECT * FROM OPERADORES_PORTUARIOS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) 
		{
			String name = rs.getString("NOMBRE");
			int id = Integer.parseInt(rs.getString("ID"));
			int id_puerto = Integer.parseInt(rs.getString("ID_PUERTO"));

			operadores.add(new OperadorPortuario(id,id_puerto, name));
		}
		return operadores;
	}

	public void insertarDatos() throws SQLException
	{

		insertarDatosRESERVAS();


	}

	public void insertarDatosBarco() throws SQLException
	{



		for(int i = 818; i<1020; i++){

			int q = (int) (1 + (Math.random() * 4));
			int volumen = i*1000;
			int costo = i*100000;

			String tipo="";
			if(q == 1)
				tipo="'Multiproposito'";
			else if(q==2)
			{
				tipo ="'Portacontenedores'";
			}
			else if (q==3)
			{
				tipo = "'Ro-Ro'";
			}
			else
				tipo = "'Multiproposito'";

			if (i <520){
				String sql3 = "INSERT INTO BARCOS VALUES("
						+ i+","+"'barco"+ i+ "'"+ ","+q + ","+1+ ","+volumen+ ","+"'A"+i+costo+volumen+q+"'"+ ","+tipo+","+1+","+q+","+"NULL"+ ","
						+"NULL"+ ","+"NULL"+ ","+(q*2)+ ","+0+")";

				System.out.println("SQL stmt:" + sql3);

				PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
				recursos.add(prepStmt3);
				ResultSet rs3 = prepStmt3.executeQuery();

			}

			else{

				String sql3 = "INSERT INTO BARCOS VALUES("
						+ i+","+"'barco"+ i+ "'"+","+q + ","+1+ ","+volumen+ ","+"'A"+i+costo+volumen+q+"'"+ ","+tipo+","+1+","+q+","+"NULL"+ ","
						+i+ ","+"NULL"+ ","+(q*2)+ ","+0+")";

				System.out.println("SQL stmt:" + sql3);

				PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
				recursos.add(prepStmt3);
				ResultSet rs3 = prepStmt3.executeQuery();


			}

		}



	}

	public void insertarDatosSalidas() throws SQLException
	{
		for(int i = 160; i<310; i++){
			long d= i*1220227200;
			Date fecha = new Date(d);
			String date = fecha.toString();

			String sql4 = "INSERT INTO SALIDAS VALUES("
					+ i+","+(i+10)+","+"TO_DATE('" + date
					+ "', 'YYYY-MM-DD HH24:MI:SS')"+")";

			System.out.println("SQL stmt:" + sql4);

			PreparedStatement prepStmt4= conn.prepareStatement(sql4);
			recursos.add(prepStmt4);
			ResultSet rs4 = prepStmt4.executeQuery();
			String sql3 = "UPDATE BARCOS "
					+ "SET BARCOS.ID_SALIDA ="+ i
					+ " WHERE BARCOS.ID =" +(i+10);

			System.out.println("SQL stmt:" + sql3);

			PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
			recursos.add(prepStmt3);
			ResultSet rs3 = prepStmt3.executeQuery();
		}

	}

	public void insertarDatosRESERVAS() throws SQLException
	{
		for(int i = 159; i<310; i++){
			long d= i*1220220000;
			Date fecha = new Date(d);
			String date = fecha.toString();
			int q = (int) (1 + (Math.random() * 4));


			String sql4 = "INSERT INTO RESERVAS VALUES("
					+ "TO_DATE('" + date
					+ "', 'YYYY-MM-DD HH24:MI:SS')"+","+(i)+","+(i+10)+","+q+")";

			System.out.println("SQL stmt:" + sql4);

			PreparedStatement prepStmt4= conn.prepareStatement(sql4);
			recursos.add(prepStmt4);
			ResultSet rs4 = prepStmt4.executeQuery();
			String sql3 = "UPDATE BARCOS "
					+ "SET BARCOS.ID_RESERVA ="+ i
					+ " WHERE BARCOS.ID =" +(i+10);

			System.out.println("SQL stmt:" + sql3);

			PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
			recursos.add(prepStmt3);
			ResultSet rs3 = prepStmt3.executeQuery();
		}

	}

	public void insertarDatosCarga() throws SQLException
	{



		for(int i = 914; i<1020; i++){
			long d= i* 1220227200;
			Date fecha = new Date(d);
			String date = fecha.toString();
			int q = (int) (1 + (Math.random() * 4));
			int volumen = i*1000;
			int costo = i*100000;
			String tipo="";
			if(q == 1)
				tipo="CONTENEDORES";
			else if(q==2)
			{
				tipo ="BIOTIPOS";
			}
			else if (q==3)
			{
				tipo = "GRANEL SOLIDO";
			}
			else
				tipo = "VEHICULOS";
			if(i < 520)
			{
				String sql3 = "INSERT INTO CARGAS VALUES("
						+ i+","+"'"+tipo+"'"+","+volumen+","+costo+","+	q+","+i+","+i+","+(q*2)+")";

				System.out.println("SQL stmt:" + sql3);

				PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
				recursos.add(prepStmt3);
				ResultSet rs3 = prepStmt3.executeQuery();

				String sql4 = "INSERT INTO CARGASXALMACENAMIENTO VALUES("
						+ i+","+(i+500)+","+"TO_DATE('" + date
						+ "', 'YYYY-MM-DD HH24:MI:SS')"+")";

				System.out.println("SQL stmt:" + sql4);

				PreparedStatement prepStmt4= conn.prepareStatement(sql4);
				recursos.add(prepStmt4);
				ResultSet rs4 = prepStmt4.executeQuery();

			}
			else
			{
				String sql3 = "INSERT INTO CARGAS VALUES("
						+ i+","+"'"+tipo+"'"+","+volumen+","+costo+","+q+","+i+","+"NULL"+","+(q*2)+")";

				System.out.println("SQL stmt:" + sql3);

				PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
				recursos.add(prepStmt3);
				ResultSet rs3 = prepStmt3.executeQuery();

				String sql4 = "INSERT INTO CARGASXBARCO VALUES("
						+ i+","+(i-500)+","+"TO_DATE('" + date
						+ "', 'YYYY-MM-DD HH24:MI:SS')"+")";

				System.out.println("SQL stmt:" + sql4);

				PreparedStatement prepStmt4= conn.prepareStatement(sql4);
				recursos.add(prepStmt4);
				ResultSet rs4 = prepStmt4.executeQuery();
			}




		}

	}



	public void insertarDatosAlmacenamiento() throws SQLException
	{

		for(int i = 914; i<1020; i++){

			int q = (int) (1 + (Math.random() * 4));
			int volumen = i*1000;
			int costo = i*100000;
			String tipo="";
			String carga = "";
			if(q == 1){
				carga = "CONTENEDORES";
				tipo="BODEGA";
			}
			else if(q==2)
			{
				carga = "BIOTIPOS";
				tipo ="COBERTIZO";
			}
			else if (q==3)
			{
				carga ="GRANEL SOLIDO";
				tipo = "SILO";
			}
			else{
				tipo = "PATIO";
				carga = "VEHICULOS";
			}

			String t = tipo + "S";
			if(i < 510)
			{
				String sql3 = "INSERT INTO AREAS_DE_ALMACENAMIENTO VALUES("
						+ i+","+ 1+","+costo+","+1+","+"'"+tipo+"'"+","+0+")";

				System.out.println("SQL stmt:" + sql3);

				PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
				recursos.add(prepStmt3);
				ResultSet rs3 = prepStmt3.executeQuery();
				if(q==1)
				{
					String sql4 = "INSERT INTO "+t+ " VALUES("
							+ i+","+ 1+","+q+","+0+","+"NULL"+","+ volumen +")";

					System.out.println("SQL stmt:" + sql4);

					PreparedStatement prepStmt4= conn.prepareStatement(sql4);
					recursos.add(prepStmt4);
					ResultSet rs4 = prepStmt4.executeQuery();
				}
				else if(q==2 || q==4)
				{

					String sql4 = "INSERT INTO "+t+ " VALUES("
							+ i+","+ volumen +","+"'"+carga+"'"+")";

					System.out.println("SQL stmt:" + sql4);

					PreparedStatement prepStmt4= conn.prepareStatement(sql4);
					recursos.add(prepStmt4);
					ResultSet rs4 = prepStmt4.executeQuery();
				}
				else 
				{

					String sql4 = "INSERT INTO "+t+ " VALUES("
							+ i+","+ "'SILO"+i+"'" +","+i+")";

					System.out.println("SQL stmt:" + sql4);

					PreparedStatement prepStmt4= conn.prepareStatement(sql4);
					recursos.add(prepStmt4);
					ResultSet rs4 = prepStmt4.executeQuery();
				}


			}
			else
			{
				String sql3 = "INSERT INTO AREAS_DE_ALMACENAMIENTO VALUES("
						+ i+","+ 1+","+costo+","+0+","+"'"+tipo+"'"+","+0+")";

				System.out.println("SQL stmt:" + sql3);

				PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
				recursos.add(prepStmt3);
				ResultSet rs3 = prepStmt3.executeQuery();

				if(q==1)
				{
					String sql4 = "INSERT INTO "+t+ " VALUES("
							+ i+","+ 1+","+q+","+0+","+"NULL"+","+ volumen +")";

					System.out.println("SQL stmt:" + sql4);

					PreparedStatement prepStmt4= conn.prepareStatement(sql4);
					recursos.add(prepStmt4);
					ResultSet rs4 = prepStmt4.executeQuery();
				}
				else if(q==2 || q==4)
				{

					String sql4 = "INSERT INTO "+t+ " VALUES("
							+ i+","+ volumen +","+"'"+carga+"'"+")";

					System.out.println("SQL stmt:" + sql4);

					PreparedStatement prepStmt4= conn.prepareStatement(sql4);
					recursos.add(prepStmt4);
					ResultSet rs4 = prepStmt4.executeQuery();
				}
				else 
				{

					String sql4 = "INSERT INTO "+t+ " VALUES("
							+ i+","+ "'SILO"+i+"'" +","+i+")";

					System.out.println("SQL stmt:" + sql4);

					PreparedStatement prepStmt4= conn.prepareStatement(sql4);
					recursos.add(prepStmt4);
					ResultSet rs4 = prepStmt4.executeQuery();
				}

			}




		}

	}

	/**
	 * Método que busca el/los videos con el nombre que entra como parámetro.
	 * @param name - Nombre de el/los videos a buscar
	 * @return Arraylist con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<OperadorPortuario> buscarOperadorPorName(String name) throws SQLException, Exception {


		ArrayList<OperadorPortuario> operadores = new ArrayList<OperadorPortuario>();

		String sql = "SELECT * FROM OPERADORES_PORTUARIOS WHERE NAME ='" + name + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) 
		{
			String name2 = rs.getString("NOMBRE");
			int id = Integer.parseInt(rs.getString("ID"));
			int id_puerto = Integer.parseInt(rs.getString("ID_PUERTO"));

			operadores.add(new OperadorPortuario(id,id_puerto, name2));
		}

		return operadores;
	}

	/**
	 * Método que agrega el video que entra como parámetro a la base de datos.
	 * @param operador - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addOperador(OperadorPortuario operador) throws SQLException, Exception {

		String sql = "INSERT INTO OPERADORES_PORTUARIOS VALUES ("
				+ operador.getId() +","
				+ "'" + operador.getNombre() +"',"+ operador.getId_puerto()+")";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		System.out.println("termino");

	}

	/**
	 * Método que actualiza el video que entra como parámetro en la base de datos.
	 * @param operador - el barco a actualizar. barco !=  null
	 * <b> post: </b> se ha actualizado el video en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateOperador(OperadorPortuario operador) throws SQLException, Exception {

		String sql = "UPDATE OPERADORES_PORTUARIOS SET ";
		sql += "NAME='" + operador.getNombre() + "')";
		sql += " WHERE ID = " + operador.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Método que elimina el video que entra como parámetro en la base de datos.
	 * @param operador - el video a borrar. video !=  null
	 * <b> post: </b> se ha borrado el video en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteOperador(OperadorPortuario operador) throws SQLException, Exception {

		String sql = "DELETE FROM OPERADORES?PORTUARIOS";
		sql += " WHERE id = " + operador.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public void registrarSalidaBarco(Barco barco) throws SQLException
	{
		String sql = "SELECT RESERVAS.ID_BARCO,BARCOS.NOMBRE,BARCOS.HORA_SALIDA "
				+ "FROM RESERVAS"
				+ " INNER JOIN BARCOS"
				+ " ON BARCOS.ID =" + barco.getId() 
				+ " AND RESERVAS.ID_BARCO=" + barco.getId();
		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		System.out.println("hola");
		if(!rs.next())
		{
			System.out.println("hola2");
			System.out.println("no se agrego");

		}
		else{
			System.out.println("hola3");
			int id = Integer.parseInt(rs.getString("ID_BARCO"));
			String name = rs.getString("NOMBRE");
			String date = rs.getDate("HORA_SALIDA").toString();
			System.out.println(date);
			String sql1 = "INSERT INTO SALIDAS VALUES (";
			sql1 += id + ",'";
			sql1 += name + "',";
			sql1 += barco.getId() + ",";
			sql1 += "TO_DATE('" + date
					+ "', 'YYYY-MM-DD HH24:MI:SS'))";

			System.out.println("SQL stmt:" + sql1);

			PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
			ResultSet rs1 = prepStmt1.executeQuery();
		}
	}

	public void registrarTipoCargaBarco(Carga carga,int barco) throws SQLException
	{	
		int idBarco = barco;
		int idCarga = carga.getId();
		String sql = "BEGIN"
				+ " INSERT INTO CARGAS VALUES("
				+ carga.getId() + ",'"
				+ carga.tipo+ "',"
				+ carga.getVolumen() + ","
				+ carga.getCosto() + ","
				+ carga.getEquipo() + ","
				+ carga.getPeso() + ","
				+ carga.getArea()+","
				+ carga.getAgente()+");"
				+ "EXCEPTION WHEN DUP_VAL_ON_INDEX THEN "
				+ "UPDATE CARGAS "
				+ "SET PESO=PESO +"+carga.getPeso()+",VOLUMEN=VOLUMEN +"+carga.getVolumen()
				+ " WHERE CARGAS.ID="+idCarga
				+ "; END;"

				;
		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();


		String sql1 = "UPDATE BARCOS "
				+ "SET BARCOS.ID_CARGA="+idCarga
				+ " WHERE BARCOS.ID="+barco;

		System.out.println("SQL stmt:" + sql1);

		PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
		recursos.add(prepStmt1);
		ResultSet rs1 = prepStmt1.executeQuery();

		if(rs1.next())
		{
			String sql2 = "INSERT INTO CARGASXBARCO VALUES (";
			sql2 +=  idCarga +  ",";
			sql2 += barco + ",";
			sql2 += "TRUNC(SYSDATE)) ";

			System.out.println("SQL stmt:" + sql2);

			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();


		}





	}

	public void registrarSalidaCarga(Carga carga) throws SQLException
	{
		String sql = "SELECT CARGAS_PUERTO.ID_CARGA"
				+ " FROM CARGAS_PUERTO"
				+ " WHERE CARGAS_PUERTO.ID_CARGA =" + carga.getId();
		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next())
		{
			String sql2 = "SELECT IMPORTADORESXCARGA.ID_IMPORTADOR"
					+ " FROM IMPORTADORESXCARGA"
					+ " WHERE IMPORTADORESXCARGA.ID_CARGA =" + carga.getId();
			System.out.println("SQL stmt:" + sql2);

			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();
			if (rs2.next())
			{
				int idImp = Integer.parseInt(rs2.getString("ID_IMPORTADOR"));

				String sql1 = "INSERT INTO ENTREGAS_IMPORTADOR VALUES (";
				sql1 +=  idImp +  ",'";
				sql1 += carga.getId() + "',";
				sql1 += "TRUNC(SYSDATE)) ";

				System.out.println("SQL stmt:" + sql1);

				PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
				recursos.add(prepStmt1);
				ResultSet rs1 = prepStmt1.executeQuery();
			}

		}

	}

	public void registrarFactura(Barco barco) throws SQLException
	{
		String sql = "SELECT CARGAS_PUERTO.ID_CARGA, CARGAS_PUERTO.ID_AREA"
				+ " FROM CARGAS_PUERTO"
				+ " WHERE CARGAS_PUERTO.ID_BARCO = " + barco.getId();
		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next())
		{

			int idc = Integer.parseInt(rs.getString("ID_CARGA"));
			int ida = Integer.parseInt(rs.getString("ID_AREA"));

			String sql1 = "SELECT CARGAS.COSTO, CARGAS.ID_EQUIPO"
					+ " FROM CARGAS"
					+ " WHERE CARGAS.ID = " + idc;
			System.out.println("SQL stmt:" + sql1);

			PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
			recursos.add(prepStmt1);
			System.out.println("a");
			ResultSet rs1 = prepStmt1.executeQuery();
			System.out.println("b");

			if(rs1.next())
			{
				int cosCarga = Integer.parseInt(rs1.getString("COSTO"));
				int ide = Integer.parseInt(rs1.getString("ID_EQUIPO"));

				String sql2 = "SELECT EQUIPOS.COSTO"
						+ " FROM EQUIPOS"
						+ " WHERE EQUIPOS.ID = " + ide;
				System.out.println("SQL stmt:" + sql2);

				PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
				recursos.add(prepStmt2);
				ResultSet rs2 = prepStmt2.executeQuery();

				if(rs2.next())
				{
					int cosEquipo = Integer.parseInt(rs2.getString("COSTO"));
					String sql3 = "SELECT AREAS_DE_ALMACENAMIENTO.COSTO"
							+ " FROM AREAS_DE_ALMACENAMIENTO"
							+ " WHERE AREAS_DE_ALMACENAMIENTO.ID = " + ida;
					System.out.println("SQL stmt:" + sql3);

					PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
					recursos.add(prepStmt3);

					ResultSet rs3 = prepStmt3.executeQuery();
					if(rs3.next())
					{
						int cosArea = Integer.parseInt(rs3.getString("COSTO"));

						int sumaCostos = cosArea + cosEquipo+ cosCarga + 500000;
						int ID = 5 + (int)(Math.random() * 500); 
						String sql4 = "INSERT INTO COSTOS_FACTURADOS VALUES (";
						sql4 +=  ID  +  ",";
						sql4 +=   sumaCostos +  ",'";
						sql4 += "MARITIMO" + "',";
						sql4 += idc +",";
						sql4 += ide +",";
						sql4 += ida +")";


						System.out.println("SQL stmt:" + sql4);

						PreparedStatement prepStmt4 = conn.prepareStatement(sql4);
						recursos.add(prepStmt4);
						ResultSet rs4 = prepStmt4.executeQuery();

					}
				}

			}

		}

	}

	public boolean verificarTipoBuque(Carga carga, int barco) throws SQLException
	{

		boolean verificar = false;
		String sql1 = "SELECT BARCOS.ID, BARCOS.TIPO, BARCOS.ID_CARGA,BARCOS.CAPACIDAD";
		sql1 += " FROM BARCOS"
				+ " WHERE BARCOS.ID ="+ barco;

		System.out.println("SQL stmt:" + sql1);
		PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
		recursos.add(prepStmt1);
		ResultSet rs1 = prepStmt1.executeQuery();
		if(rs1.next())
		{
			int id = Integer.parseInt(rs1.getString("ID"));
			String tipo = rs1.getString("TIPO");
			String idCarga = rs1.getString("ID_CARGA");
			int capacidad = Integer.parseInt(rs1.getString("CAPACIDAD"));

			if((tipo.equalsIgnoreCase("Portacontenedores")
					&& carga.tipo.equalsIgnoreCase("CONTENEDORES"))||
					(tipo.equalsIgnoreCase("Ro-Ro")
							&& carga.tipo.equalsIgnoreCase("VEHICULOS"))&& (capacidad > carga.getVolumen())||
					(tipo.equalsIgnoreCase("Multiproposito")
							))
			{
				capacidad -= carga.getVolumen();
				verificar = true;
				String sql2 = "UPDATE BARCOS "
						+ "SET BARCOS.CAPACIDAD= "+capacidad
						+ " WHERE BARCOS.ID="+barco;

				System.out.println("SQL stmt:" + sql2);

				PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
				recursos.add(prepStmt2);
				ResultSet rs2 = prepStmt2.executeQuery();


			}

		}

		return verificar;
	}

	public void cargarBuque(Carga carga, int barco) throws SQLException
	{
		if(verificarTipoBuque(carga, barco))
		{
			registrarTipoCargaBarco(carga, barco);
			String sql2 = "UPDATE AREAS_DE_ALMACENAMIENTO "
					+ "SET AREAS_DE_ALMACENAMIENTO.ESTADO= 0"
					+ " WHERE AREAS_DE_ALMACENAMIENTO.ID="+carga.getArea();

			System.out.println("SQL stmt:" + sql2);

			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();



		}
	}
	public void cargarBuque1(Carga carga, int barco) throws SQLException
	{
		registrarTipoCargaBarco(carga, barco);
		String sql2 = "UPDATE AREAS_DE_ALMACENAMIENTO "
				+ "SET AREAS_DE_ALMACENAMIENTO.ESTADO= 0"
				+ " WHERE AREAS_DE_ALMACENAMIENTO.ID="+carga.getArea();

		System.out.println("SQL stmt:" + sql2);

		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		ResultSet rs2 = prepStmt2.executeQuery();


	}
	public boolean verificarAlmacenamiento(int area, Barco barco) throws SQLException
	{

		boolean verificar = false;
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
							verificar = true;
						}

					}
				}
			}
		}


		return verificar;
	}



	public void registrarTipoCargaArea(int area ,Barco barco) throws SQLException
	{	
		int idBarco = barco.getId();
		int idCarga = barco.getCarga();

		String sql1 = "UPDATE BARCOS "
				+ "SET BARCOS.ID_CARGA="+"(null)"
				+ " WHERE BARCOS.ID="+idBarco;

		System.out.println("SQL stmt:" + sql1);

		PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
		recursos.add(prepStmt1);
		ResultSet rs1 = prepStmt1.executeQuery();

		if(rs1.next())
		{
			String sql2 = "INSERT INTO CARGASXALMACENAMIENTO VALUES (";
			sql2 +=  idCarga +  ",";
			sql2 += area + ",";
			sql2 += "TRUNC(SYSDATE)) ";

			System.out.println("SQL stmt:" + sql2);

			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();
		}
	}

	public void cargarArea(int area, Barco barco)throws SQLException
	{
		if(verificarAlmacenamiento(area, barco))
		{
			registrarTipoCargaArea(area, barco);
			String sql2 = "UPDATE AREAS_DE_ALMACENAMIENTO "
					+ "SET AREAS_DE_ALMACENAMIENTO.ESTADO= 1"
					+ " WHERE AREAS_DE_ALMACENAMIENTO.ID="+area;

			System.out.println("SQL stmt:" + sql2);

			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();

		}
	}
	public void cargarArea1(int area, Barco barco)throws SQLException
	{

		registrarTipoCargaArea(area, barco);
		String sql2 = "UPDATE AREAS_DE_ALMACENAMIENTO "
				+ "SET AREAS_DE_ALMACENAMIENTO.ESTADO= 1"
				+ " WHERE AREAS_DE_ALMACENAMIENTO.ID="+area;

		System.out.println("SQL stmt:" + sql2);

		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		ResultSet rs2 = prepStmt2.executeQuery();

	}

	public void deshabilitarBarco(String td,Barco barco) throws SQLException
	{
		Carga carga = null;
		boolean cargo = false;

		String sql = "SELECT * FROM CARGAS"
				+ " WHERE CARGAS.ID = "+ barco.getCarga();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while(rs.next())
		{
			int id = Integer.parseInt(rs.getString("ID"));
			String tipo = rs.getString("TIPO");
			int volumen = Integer.parseInt(rs.getString("VOLUMEN"));
			int costo = Integer.parseInt(rs.getString("COSTO"));
			int equipo = Integer.parseInt(rs.getString("ID_EQUIPO"));
			int peso = Integer.parseInt(rs.getString("PESO"));
			int area = Integer.parseInt(rs.getString("ID_AREA"));
			int agente = Integer.parseInt(rs.getString("ID_AGENTE"));

			carga = new Carga(tipo, volumen, id, costo, equipo, peso, area, agente);


			String sql2 = "UPDATE BARCOS "
					+ "SET BARCOS.DESHABILITADO= 1"
					+ " WHERE BARCOS.ID="+barco.getId();

			System.out.println("SQL stmt:" + sql2);

			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();



			int q = (int) (1 + (Math.random() * 2));
			if(td.equalsIgnoreCase("A") || td.equalsIgnoreCase("S1") )
			{
				if(q == 1)
				{
					int i = 1;
					while(i < 9)
					{
						if(verificarAlmacenamiento(i, barco))
						{
							cargarArea1(i, barco);
							cargo = true;
							i = 9;
						}
						else i++;
					}
				}
				else if(q == 2)
				{

					String sql1 = "SELECT * FROM BARCOS"
							+ " WHERE NOT BARCOS.ID = "+ barco.getId();

					System.out.println("SQL stmt:" + sql1);

					PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
					recursos.add(prepStmt1);
					ResultSet rs1 = prepStmt1.executeQuery();

					while(rs1.next()&& !cargo)
					{
						int idBarcoAux = Integer.parseInt(rs1.getString("ID"));

						if(verificarTipoBuque(carga, idBarcoAux))
						{
							cargarBuque1(carga, idBarcoAux);
							cargo = true;
						}
					}
					if(!rs1.next())
					{
						int i = 1;
						while(i < 9)
						{
							if(verificarAlmacenamiento(i, barco))
							{
								cargarArea1(i, barco);
								cargo = true;
								i = 9;
							}
							else i++;
						}
					}
				}

			}

			if(!cargo || td.equalsIgnoreCase("S2")){
				String sql3 = "INSERT INTO DESHABILITADOS "
						+ "VALUES( " + carga.getId() + "," + carga.getAgente()
						+ ")";
				System.out.println("SQL stmt:" + sql3);

				PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
				recursos.add(prepStmt3);
				ResultSet rs3 = prepStmt3.executeQuery();
			}
		}
	}

	public void deshabilitarArea(int area) throws SQLException
	{
		conn.setAutoCommit(false);
		conn.setTransactionIsolation(conn.TRANSACTION_SERIALIZABLE);
		Carga carga = null;
		boolean cargo = false;

		String sql = "SELECT CARGAS.ID, CARGAS.TIPO, CARGAS.VOLUMEN, CARGAS.PESO,"
				+ " CARGAS.COSTO, CARGAS.ID_EQUIPO, CARGAS.ID_AREA, CARGAS.ID_AGENTE"
				+ " FROM CARGAS JOIN AREAS_DE_ALMACENAMIENTO "
				+ "ON CARGAS.ID_AREA = " + area;

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while(rs.next()&& !cargo)
		{
			int id = Integer.parseInt(rs.getString("ID"));
			String tipo = rs.getString("TIPO");
			int volumen = Integer.parseInt(rs.getString("VOLUMEN"));
			int costo = Integer.parseInt(rs.getString("COSTO"));
			int equipo = Integer.parseInt(rs.getString("ID_EQUIPO"));
			int peso = Integer.parseInt(rs.getString("PESO"));
			int idArea = Integer.parseInt(rs.getString("ID_AREA"));
			int agente = Integer.parseInt(rs.getString("ID_AGENTE"));

			carga = new Carga(tipo, volumen, id, costo, equipo, peso, area, agente);


			String sql2 = "UPDATE AREAS_DE_ALMACENAMIENTO "
					+ "SET AREAS_DE_ALMACENAMIENTO.DESHABILITADO= 1"
					+ " WHERE AREAS_DE_ALMACENAMIENTO.ID="+area;

			System.out.println("SQL stmt:" + sql2);

			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();

			String sqlBarcos = "SELECT * FROM BARCOS";
			System.out.println("SQL stmt:" + sqlBarcos);

			PreparedStatement prepStmtBarcos = conn.prepareStatement(sqlBarcos);
			recursos.add(prepStmtBarcos);
			ResultSet rsBarcos = prepStmtBarcos.executeQuery();

			while(rsBarcos.next() && !cargo)
			{
				System.out.println("entro");
				int idBarco = Integer.parseInt(rsBarcos.getString("ID"));
				System.out.println("entro1");

				String tipoBarco = rsBarcos.getString("TIPO");
				System.out.println("entro2");
				int capacidad = Integer.parseInt(rsBarcos.getString("CAPACIDAD"));
				System.out.println("entro3");
				int cargaBarco = Integer.parseInt(rsBarcos.getString("ID_CARGA"));
				System.out.println("entro4");

				Barco barco = new Barco("", "","" , idBarco, capacidad, "", "", tipoBarco, cargaBarco, 0, 0);


				int q = (int) (1 + (Math.random() * 2));
				if(q == 1)
				{
					int i = 1;
					while(i < 9)
					{	
						if(i != area)
						{
							if(verificarAlmacenamiento(i, barco))
							{
								cargarArea1(i, barco);
								cargo = true;
								i = 9;
							}
							else i++;
						}
						else 
							i++;
					}
				}
				else if(q == 2)
				{

					int idBarcoAux = barco.getId();

					if(verificarTipoBuque(carga, idBarcoAux))
					{
						cargarBuque1(carga, idBarcoAux);
						cargo = true;
					}

					else
					{
						int i = 1;
						while(i < 9)
						{	
							if(i != area)
							{
								if(verificarAlmacenamiento(i, barco))
								{
									cargarArea1(i, barco);
									cargo = true;
									i = 9;
								}
								else i++;
							}
							else 
								i++;
						}
					}
				}

			}
			if(!cargo )
			{
				String sql3 = "INSERT INTO AREAS_DESHABILITADAS "
						+ "VALUES( " + carga.getId() + "," + area
						+ ")";
				System.out.println("SQL stmt:" + sql3);

				PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
				recursos.add(prepStmt3);
				ResultSet rs3 = prepStmt3.executeQuery();
			}
			conn.setSavepoint();

		}
		conn.commit();
	}

	public ArrayList<ConsultaMovimientos> getMoviemientosCargas(int idAgente) throws SQLException
	{
		conn.setAutoCommit(false);
		conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
		ArrayList<ConsultaMovimientos> resp = new ArrayList<ConsultaMovimientos>();
		if(idAgente != -1)
		{
			String sql3 = "SELECT AGENTE.ID AS ID_AGENTE, CARGAS.ID AS ID_CARGA,BARCOS.NOMBRE AS ORIGEN_BARCO,"
					+ "CARGASXALMACENAMIENTO.FECHA AS FECHA_MOVIMIENTO_AL,CARGASXALMACENAMIENTO.ID_AREA AS DESTINO_AREA,"
					+ "CARGASXBARCO.ID_BARCO AS DESTINO_BARCO, CARGASXBARCO.FECHA_ENTREGA AS FECHA_MOVIMIENTO_BAR "
					+ "FROM AGENTE "
					+ "JOIN CARGAS "
					+ "ON AGENTE.ID = " + idAgente
					+ " AND CARGAS.ID_AGENTE = " +idAgente
					+ " LEFT JOIN CARGASXALMACENAMIENTO " 
					+ "ON CARGAS.ID = CARGASXALMACENAMIENTO.ID_CARGA "
					+ "LEFT JOIN CARGASXBARCO "
					+ "ON CARGAS.ID = CARGASXBARCO.ID_CARGA "
					+ "LEFT JOIN BARCOS "
					+ "ON CARGAS.ID = BARCOS.ID_CARGA";

			System.out.println("SQL stmt:" + sql3);

			PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
			recursos.add(prepStmt3);
			ResultSet rs3 = prepStmt3.executeQuery();
			while(rs3.next())
			{

				int idCarga = Integer.parseInt(rs3.getString("ID_CARGA"));
				String origenBarco="";
				if(rs3.getString("ORIGEN_BARCO")!= null)
				{
					origenBarco = rs3.getString("ORIGEN_BARCO");
				}
				String FMB="";
				if(rs3.getString("FECHA_MOVIMIENTO_BAR")!=null)
					FMB = rs3.getString("FECHA_MOVIMIENTO_BAR");
				String FMA="";
				if(rs3.getString("FECHA_MOVIMIENTO_AL")!=null)
					FMA = rs3.getString("FECHA_MOVIMIENTO_AL");
				int DA=-1;
				if(rs3.getString("DESTINO_AREA")!= null)
					DA = Integer.parseInt(rs3.getString("DESTINO_AREA"));
				int DB=-1;
				if(rs3.getString("DESTINO_BARCO")!= null)
					DB = Integer.parseInt(rs3.getString("DESTINO_BARCO"));

				ConsultaMovimientos con = new ConsultaMovimientos(idAgente, idCarga, origenBarco, FMB, FMA, DA, DB);
				resp.add(con);
			}

		}


		else{
			String sql3 = "SELECT CARGAS.ID AS ID_CARGA,BARCOS.NOMBRE AS ORIGEN_BARCO,"
					+ "CARGASXALMACENAMIENTO.FECHA AS FECHA_MOVIMIENTO_AL,CARGASXALMACENAMIENTO.ID_AREA AS DESTINO_AREA,"
					+ "CARGASXBARCO.ID_BARCO AS DESTINO_BARCO, CARGASXBARCO.FECHA_ENTREGA AS FECHA_MOVIMIENTO_BAR "
					+ "FROM CARGAS "
					+ "LEFT JOIN CARGASXALMACENAMIENTO " 
					+ "ON CARGAS.ID = CARGASXALMACENAMIENTO.ID_CARGA "
					+ "LEFT JOIN CARGASXBARCO "
					+ "ON CARGAS.ID = CARGASXBARCO.ID_CARGA "
					+ "LEFT JOIN BARCOS "
					+ "ON CARGAS.ID = BARCOS.ID_CARGA";

			System.out.println("SQL stmt:" + sql3);

			PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
			recursos.add(prepStmt3);
			ResultSet rs3 = prepStmt3.executeQuery();
			while(rs3.next())
			{

				int idCarga = Integer.parseInt(rs3.getString("ID_CARGA"));
				String origenBarco="";
				if(rs3.getString("ORIGEN_BARCO")!= null)
				{
					origenBarco = rs3.getString("ORIGEN_BARCO");
				}
				String FMB="";
				if(rs3.getString("FECHA_MOVIMIENTO_BAR")!=null)
					FMB = rs3.getString("FECHA_MOVIMIENTO_BAR");
				String FMA="";
				if(rs3.getString("FECHA_MOVIMIENTO_AL")!=null)
					FMA = rs3.getString("FECHA_MOVIMIENTO_AL");
				int DA=-1;
				if(rs3.getString("DESTINO_AREA")!= null)
					DA = Integer.parseInt(rs3.getString("DESTINO_AREA"));
				int DB=-1;
				if(rs3.getString("DESTINO_BARCO")!= null)
					DB = Integer.parseInt(rs3.getString("DESTINO_BARCO"));

				ConsultaMovimientos con = new ConsultaMovimientos(idAgente, idCarga, origenBarco, FMB, FMA, DA, DB);
				resp.add(con);


			}

		}
		return resp;

	}

	public ArrayList<ConsultaAreas> getAreas(int idAgente) throws SQLException
	{
		conn.setAutoCommit(false);
		conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
		ArrayList<ConsultaAreas> resp = new ArrayList<ConsultaAreas>();
		if(idAgente != -1)
		{
			String sql3 = "SELECT AGENTE.ID AS ID_AGENTE, CARGAS.ID AS ID_CARGA, AREAS_DE_ALMACENAMIENTO.ID AS ID_AREA, "
					+"AREAS_DE_ALMACENAMIENTO.ESTADO,AREAS_DE_ALMACENAMIENTO.TIPO,AREAS_DE_ALMACENAMIENTO.COSTO, "
					+"AREAS_DE_ALMACENAMIENTO.DESHABILITADO, CARGASXALMACENAMIENTO.FECHA "
					+"FROM AGENTE "
					+"JOIN CARGAS "
					+"ON AGENTE.ID = "+ idAgente
					+" AND CARGAS.ID_AGENTE = " + idAgente
					+" LEFT JOIN AREAS_DE_ALMACENAMIENTO "
					+"ON AREAS_DE_ALMACENAMIENTO.ID = CARGAS.ID_AREA "
					+ "LEFT JOIN CARGASXALMACENAMIENTO " 
					+ "ON CARGASXALMACENAMIENTO.ID_AREA = AREAS_DE_ALMACENAMIENTO.ID";

			System.out.println("SQL stmt:" + sql3);

			PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
			recursos.add(prepStmt3);
			ResultSet rs3 = prepStmt3.executeQuery();
			while(rs3.next())
			{

				int idCarga = Integer.parseInt(rs3.getString("ID_CARGA"));
				String tipoArea="";
				if(rs3.getString("TIPO")!= null)
				{
					tipoArea = rs3.getString("TIPO");
				}
				String FEA="";
				if(rs3.getString("FECHA")!= null)
				{
					FEA = rs3.getString("FECHA");
				}
				int estado=-1;
				if(rs3.getString("ESTADO")!=null)
					estado = Integer.parseInt( rs3.getString("ESTADO"));
				int deshabilitado=-1;
				if(rs3.getString("DESHABILITADO")!=null)
					deshabilitado = Integer.parseInt(rs3.getString("DESHABILITADO"));
				int idArea=-1;
				if(rs3.getString("ID_AREA")!= null)
					idArea = Integer.parseInt(rs3.getString("ID_AREA"));
				int costo=-1;
				if(rs3.getString("COSTO")!= null)
					costo = Integer.parseInt(rs3.getString("COSTO"));

				String sql2 = "SELECT " + tipoArea + "S.";
				if(tipoArea.equalsIgnoreCase("SILO"))
				{
					sql2 += "CAPACIDAD FROM SILOS"
							+ " WHERE SILOS.ID = " + idArea;
				}
				else
				{
					sql2 += "AREA FROM " + tipoArea
							+ "S WHERE " + tipoArea +"S.ID = " + idArea;
				}
				System.out.println("SQL stmt:" + sql2);
				PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
				recursos.add(prepStmt2);
				ResultSet rs2 = prepStmt2.executeQuery();
				if(rs2.next())
				{
					String b = "";
					if(tipoArea.equalsIgnoreCase("SILO"))
						b = "CAPACIDAD";
					else b = "AREA";

					int capacidad = Integer.parseInt(rs2.getString(b));
					ConsultaAreas a = new ConsultaAreas( idArea, idCarga, FEA,FEA, tipoArea, estado, costo, deshabilitado, capacidad);
					resp.add(a);
				}





			}

		}
		else{
			String sql3 = "SELECT CARGAS.ID AS ID_CARGA, AREAS_DE_ALMACENAMIENTO.ID AS ID_AREA "
					+"AREAS_DE_ALMACENAMIENTO.ESTADO,AREAS_DE_ALMACENAMIENTO.TIPO,AREAS_DE_ALMACENAMIENTO.COSTO, "
					+"AREAS_DE_ALMACENAMIENTO.DESHABILITADO, CARGASXALMACENAMIENTO.FECHA "
					+"FROM CARGAS "
					+" LEFT JOIN AREAS_DE_ALMACENAMIENTO "
					+"ON AREAS_DE_ALMACENAMIENTO.ID = CARGAS.ID_AREA "
					+ "LEFT JOIN CARGASXALMACENAMIENTO " 
					+ "ON CARGASXALMACENAMIENTO.ID_AREA = AREAS_DE_ALMACENAMIENTO.ID";

			System.out.println("SQL stmt:" + sql3);

			PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
			recursos.add(prepStmt3);
			ResultSet rs3 = prepStmt3.executeQuery();
			while(rs3.next())
			{

				int idCarga = Integer.parseInt(rs3.getString("ID_CARGA"));
				String tipoArea="";
				if(rs3.getString("TIPO")!= null)
				{
					tipoArea = rs3.getString("TIPO");
				}
				String FEA="";
				if(rs3.getString("FECHA")!= null)
				{
					FEA = rs3.getString("FECHA");
				}
				int estado=-1;
				if(rs3.getString("ESTADO")!=null)
					estado = Integer.parseInt( rs3.getString("ESTADO"));
				int deshabilitado=-1;
				if(rs3.getString("DESHABILITADO")!=null)
					deshabilitado = Integer.parseInt(rs3.getString("DESHABILITADO"));
				int idArea=-1;
				if(rs3.getString("ID_AREA")!= null)
					idArea = Integer.parseInt(rs3.getString("ID_AREA"));
				int costo=-1;
				if(rs3.getString("COSTO")!= null)
					costo = Integer.parseInt(rs3.getString("COSTO"));

				String sql2 = "SELECT " + tipoArea + "S.";
				if(tipoArea.equalsIgnoreCase("SILO"))
				{
					sql2 += "CAPACIDAD FROM SILOS"
							+ " WHERE SILOS.ID = " + idArea;
				}
				else
				{
					sql2 += "AREA FROM " + tipoArea
							+ "S WHERE " + tipoArea +"S.ID = " + tipoArea;
				}
				System.out.println("SQL stmt:" + sql2);
				PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
				recursos.add(prepStmt2);
				ResultSet rs2 = prepStmt2.executeQuery();
				if(rs2.next())
				{
					String b = "";
					if(tipoArea.equalsIgnoreCase("SILO"))
						b = "CAPACIDAD";
					else b = "AREA";

					int capacidad = Integer.parseInt(rs2.getString(b));
					ConsultaAreas a = new ConsultaAreas( idArea, idCarga, FEA,FEA, tipoArea, estado, costo, deshabilitado, capacidad);
					resp.add(a);
				}
			}

		}

		return resp;

	}

	public ArrayList<ConsultaAreas> getMovAreas(int area1, int area2) throws SQLException
	{
		System.out.println(area1 + ","+area2);
		conn.setAutoCommit(false);
		conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
		ArrayList<ConsultaAreas> resp = new ArrayList<ConsultaAreas>();
		int q = (int) (1 + (Math.random() * 2));
		int area = 0;
		if(q==1)
			area=area1;
		else if(q==2)
			area=area2;


		String sql3 = "SELECT AREAS_DE_ALMACENAMIENTO.ID AS ID_AREA, AREAS_DE_ALMACENAMIENTO.ID_PUERTO,"
				+  " AREAS_DE_ALMACENAMIENTO.COSTO, AREAS_DE_ALMACENAMIENTO.ESTADO,AREAS_DE_ALMACENAMIENTO.TIPO,"
				+ " AREAS_DE_ALMACENAMIENTO.DESHABILITADO, CARGAS.ID AS ID_CARGA, CARGASXALMACENAMIENTO.FECHA AS FECHA_ENTRADA,"
				+ " CARGASXBARCO.FECHA_ENTREGA AS FECHA_SALIDA"
				+  " FROM AREAS_DE_ALMACENAMIENTO"
				+  " LEFT JOIN CARGAS"
				+ " ON CARGAS.ID_AREA =" + area
				+  " LEFT JOIN CARGASXALMACENAMIENTO"
				+  " ON CARGASXALMACENAMIENTO.ID_CARGA = CARGAS.ID"
				+  " LEFT JOIN CARGASXBARCO"
				+  " ON CARGASXBARCO.ID_CARGA = CARGAS.ID"
				+" WHERE AREAS_DE_ALMACENAMIENTO.ID ="+ area;

		System.out.println("SQL stmt:" + sql3);

		PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
		recursos.add(prepStmt3);
		ResultSet rs3 = prepStmt3.executeQuery();
		while(rs3.next())
		{

			int idCarga=-1;
			if(rs3.getString("ID_CARGA")!=null)
				idCarga= Integer.parseInt(rs3.getString("ID_CARGA"));

			String tipoArea="";
			if(rs3.getString("TIPO")!= null)
			{
				tipoArea = rs3.getString("TIPO");
			}
			String FEA="";
			if(rs3.getString("FECHA_ENTRADA")!= null)
			{
				FEA = rs3.getString("FECHA_ENTRADA");
			}
			String FES="";
			if(rs3.getString("FECHA_SALIDA")!= null)
			{
				FES = rs3.getString("FECHA_SALIDA");
			}
			int estado=-1;
			if(rs3.getString("ESTADO")!=null)
				estado = Integer.parseInt( rs3.getString("ESTADO"));
			int deshabilitado=-1;
			if(rs3.getString("DESHABILITADO")!=null)
				deshabilitado = Integer.parseInt(rs3.getString("DESHABILITADO"));
			int idArea=-1;
			if(rs3.getString("ID_AREA")!= null)
				idArea = Integer.parseInt(rs3.getString("ID_AREA"));
			int costo=-1;
			if(rs3.getString("COSTO")!= null)
				costo = Integer.parseInt(rs3.getString("COSTO"));

			String tipo = tipoArea + "S";

			String sql2 = "SELECT "+ tipo;
			if(tipoArea.equalsIgnoreCase("SILO"))
			{
				sql2 += ".CAPACIDAD FROM SILOS"
						+ " WHERE SILOS.ID = " + area;
			}
			else
			{
				sql2 += ".AREA FROM " + tipo
						+ " WHERE " + tipo+".ID = " + area;
			}
			System.out.println("SQL stmt:" + sql2);
			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();
			if(rs2.next())
			{
				String b = "";
				if(tipoArea.equalsIgnoreCase("SILO"))
					b = "CAPACIDAD";
				else
					b = "AREA";

				int capacidad = Integer.parseInt(rs2.getString(b));
				ConsultaAreas a = new ConsultaAreas(idArea, idCarga, FEA,FES, tipoArea, estado, costo, deshabilitado, capacidad);
				resp.add(a);
			}
		}



		return resp;

	}

	public ArrayList<respConsultaBarcos> getArribosBuques(ConsultaBarcos cons) throws SQLException
	{
		conn.setAutoCommit(false);
		conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
		ArrayList<respConsultaBarcos> resp = new ArrayList<respConsultaBarcos>();


		String sql3 = "SELECT BARCOS.ID AS ID_BARCO, BARCOS.NOMBRE AS NOMBRE_BARCO,BARCOS.TIPO AS TIPO_BARCO, "
				+"RESERVAS.FECHA_INICIAL, SALIDAS.HORA_SALIDA AS FECHA_SALIDA "
				+"FROM BARCOS "
				+"LEFT JOIN RESERVAS "
				+"ON RESERVAS.ID_BARCO = BARCOS.ID "
				+"LEFT JOIN SALIDAS "
				+"ON SALIDAS.ID_BARCO = BARCOS.ID "
				+"JOIN CARGAS "
				+"ON BARCOS.ID_CARGA = CARGAS.ID "
				+"WHERE BARCOS.NOMBRE =" + "'"+cons.nombreBarco+"' " 
				+"AND CARGAS.TIPO =" + "'"+cons.tipoCarga+"' " 
				+"AND BARCOS.TIPO = " + "'"+cons.tipoBarco+"' " 
				+"AND ((RESERVAS.FECHA_INICIAL >= TO_DATE(" + "'"+cons.fechaInicial+"' "+",'DD-MM-YYYY') "
				+"AND RESERVAS.FECHA_INICIAL <= TO_DATE(" + "'"+cons.fechaFinal+"' "+",'DD-MM-YYYY')) "
				+"OR (SALIDAS.HORA_SALIDA >= TO_DATE(" + "'"+cons.fechaInicial+"' "+",'DD-MM-YYYY') "
				+"AND SALIDAS.HORA_SALIDA <= TO_DATE(" + "'"+cons.fechaInicial+"' "+",'DD-MM-YYYY'))) "
				+ "ORDER BY " + cons.orden ;

		System.out.println("SQL stmt:" + sql3);

		PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
		recursos.add(prepStmt3);
		ResultSet rs3 = prepStmt3.executeQuery();
		while(rs3.next())
		{

			int id = -1;
			if(rs3.getString("ID_BARCO")!= null)
			{
				id= Integer.parseInt(rs3.getString("ID_BARCO"));
			}
			String horaSalida="";
			if(rs3.getString("FECHA_SALIDA")!= null)
			{
				horaSalida = rs3.getString("FECHA_SALIDA");
			}

			String FE="";
			if(rs3.getString("FECHA_INICIAL")!= null)
			{
				FE = rs3.getString("FECHA_INICIAL");
			}
			String nombre ="";
			if(rs3.getString("NOMBRE_BARCO")!=null)
				nombre = rs3.getString("NOMBRE_BARCO");
			String tipoBarco="";
			if(rs3.getString("TIPO_BARCO")!=null)
				tipoBarco = rs3.getString("TIPO_BARCO");



			respConsultaBarcos a = new respConsultaBarcos(horaSalida, FE, nombre, tipoBarco,id);
			resp.add(a);
		}



		return resp;

	}

	public ArrayList<respConsultaBarcos> getArribosBuquesEnFecha(ConsultaBarcos cons) throws SQLException
	{
		conn.setAutoCommit(false);
		conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
		ArrayList<respConsultaBarcos> resp = new ArrayList<respConsultaBarcos>();


		String sql3 = "SELECT BARCOS.ID AS ID_BARCO, BARCOS.NOMBRE AS NOMBRE_BARCO,BARCOS.TIPO AS TIPO_BARCO, "
				+"RESERVAS.FECHA_INICIAL, SALIDAS.HORA_SALIDA AS FECHA_SALIDA "
				+"FROM BARCOS "
				+"LEFT JOIN RESERVAS "
				+"ON RESERVAS.ID_BARCO = BARCOS.ID "
				+"LEFT JOIN SALIDAS "
				+"ON SALIDAS.ID_BARCO = BARCOS.ID "
				+"JOIN CARGAS "
				+"ON BARCOS.ID_CARGA = CARGAS.ID "
				+"WHERE BARCOS.NOMBRE !=" + "'"+cons.nombreBarco+"' " 
				+"AND CARGAS.TIPO !=" + "'"+cons.tipoCarga+"' " 
				+"AND BARCOS.TIPO != " + "'"+cons.tipoBarco+"' " 
				+"AND ((RESERVAS.FECHA_INICIAL >= TO_DATE(" + "'"+cons.fechaInicial+"' "+",'DD-MM-YYYY') "
				+"AND RESERVAS.FECHA_INICIAL <= TO_DATE(" + "'"+cons.fechaFinal+"' "+",'DD-MM-YYYY')) "
				+"OR (SALIDAS.HORA_SALIDA >= TO_DATE(" + "'"+cons.fechaInicial+"' "+",'DD-MM-YYYY') "
				+"AND SALIDAS.HORA_SALIDA <= TO_DATE(" + "'"+cons.fechaInicial+"' "+",'DD-MM-YYYY'))) "
				+ "ORDER BY " + cons.orden ;

		System.out.println("SQL stmt:" + sql3);

		PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
		recursos.add(prepStmt3);
		ResultSet rs3 = prepStmt3.executeQuery();
		while(rs3.next())
		{

			int id = -1;
			if(rs3.getString("ID_BARCO")!= null)
			{
				id= Integer.parseInt(rs3.getString("ID_BARCO"));
			}
			String horaSalida="";
			if(rs3.getString("FECHA_SALIDA")!= null)
			{
				horaSalida = rs3.getString("FECHA_SALIDA");
			}

			String FE="";
			if(rs3.getString("FECHA_INICIAL")!= null)
			{
				FE = rs3.getString("FECHA_INICIAL");
			}
			String nombre ="";
			if(rs3.getString("NOMBRE_BARCO")!=null)
				nombre = rs3.getString("NOMBRE_BARCO");
			String tipoBarco="";
			if(rs3.getString("TIPO_BARCO")!=null)
				tipoBarco = rs3.getString("TIPO_BARCO");



			respConsultaBarcos a = new respConsultaBarcos(horaSalida, FE, nombre, tipoBarco,id);
			resp.add(a);
		}



		return resp;

	}


	public ArrayList<ConsultaMovimientos> getMovCargas(int idAgente,String tipo,int costo) throws SQLException
	{
		conn.setAutoCommit(false);
		conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
		ArrayList<ConsultaMovimientos> resp = new ArrayList<ConsultaMovimientos>();
		if(idAgente != -1)
		{
			String sql3 = "SELECT AGENTE.ID AS ID_AGENTE, CARGAS.ID AS ID_CARGA,BARCOS.NOMBRE AS ORIGEN_BARCO,"
					+ "CARGASXALMACENAMIENTO.FECHA AS FECHA_MOVIMIENTO_AL,CARGASXALMACENAMIENTO.ID_AREA AS DESTINO_AREA,"
					+ "CARGASXBARCO.ID_BARCO AS DESTINO_BARCO, CARGASXBARCO.FECHA_ENTREGA AS FECHA_MOVIMIENTO_BAR "
					+ "FROM AGENTE "
					+ "JOIN CARGAS "
					+ "ON AGENTE.ID = " + idAgente
					+ " AND CARGAS.ID_AGENTE = " +idAgente
					+ " LEFT JOIN CARGASXALMACENAMIENTO " 
					+ "ON CARGAS.ID = CARGASXALMACENAMIENTO.ID_CARGA "
					+ "LEFT JOIN CARGASXBARCO "
					+ "ON CARGAS.ID = CARGASXBARCO.ID_CARGA "
					+ "LEFT JOIN BARCOS "
					+ "ON CARGAS.ID = BARCOS.ID_CARGA"
					+ " WHERE CARGAS.COSTO > "+  costo
					+ " AND CARGAS.TIPO= " + "'"+ tipo + "'";

			System.out.println("SQL stmt:" + sql3);

			PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
			recursos.add(prepStmt3);
			ResultSet rs3 = prepStmt3.executeQuery();
			while(rs3.next())
			{

				int idCarga = Integer.parseInt(rs3.getString("ID_CARGA"));
				String origenBarco="";
				if(rs3.getString("ORIGEN_BARCO")!= null)
				{
					origenBarco = rs3.getString("ORIGEN_BARCO");
				}
				String FMB="";
				if(rs3.getString("FECHA_MOVIMIENTO_BAR")!=null)
					FMB = rs3.getString("FECHA_MOVIMIENTO_BAR");
				String FMA="";
				if(rs3.getString("FECHA_MOVIMIENTO_AL")!=null)
					FMA = rs3.getString("FECHA_MOVIMIENTO_AL");
				int DA=-1;
				if(rs3.getString("DESTINO_AREA")!= null)
					DA = Integer.parseInt(rs3.getString("DESTINO_AREA"));
				int DB=-1;
				if(rs3.getString("DESTINO_BARCO")!= null)
					DB = Integer.parseInt(rs3.getString("DESTINO_BARCO"));

				ConsultaMovimientos con = new ConsultaMovimientos(idAgente, idCarga, origenBarco, FMB, FMA, DA, DB);
				resp.add(con);
			}

		}


		else{
			String sql3 = "SELECT CARGAS.ID AS ID_CARGA,BARCOS.NOMBRE AS ORIGEN_BARCO,"
					+ "CARGASXALMACENAMIENTO.FECHA AS FECHA_MOVIMIENTO_AL,CARGASXALMACENAMIENTO.ID_AREA AS DESTINO_AREA,"
					+ "CARGASXBARCO.ID_BARCO AS DESTINO_BARCO, CARGASXBARCO.FECHA_ENTREGA AS FECHA_MOVIMIENTO_BAR "
					+ "FROM CARGAS "
					+ "LEFT JOIN CARGASXALMACENAMIENTO " 
					+ "ON CARGAS.ID = CARGASXALMACENAMIENTO.ID_CARGA "
					+ "LEFT JOIN CARGASXBARCO "
					+ "ON CARGAS.ID = CARGASXBARCO.ID_CARGA "
					+ "LEFT JOIN BARCOS "
					+ "ON CARGAS.ID = BARCOS.ID_CARGA"
					+ " WHERE CARGAS.COSTO > "+  costo
					+ " AND CARGAS.TIPO= " + "'"+ tipo + "'";

			System.out.println("SQL stmt:" + sql3);

			PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
			recursos.add(prepStmt3);
			ResultSet rs3 = prepStmt3.executeQuery();
			while(rs3.next())
			{

				int idCarga = Integer.parseInt(rs3.getString("ID_CARGA"));
				String origenBarco="";
				if(rs3.getString("ORIGEN_BARCO")!= null)
				{
					origenBarco = rs3.getString("ORIGEN_BARCO");
				}
				String FMB="";
				if(rs3.getString("FECHA_MOVIMIENTO_BAR")!=null)
					FMB = rs3.getString("FECHA_MOVIMIENTO_BAR");
				String FMA="";
				if(rs3.getString("FECHA_MOVIMIENTO_AL")!=null)
					FMA = rs3.getString("FECHA_MOVIMIENTO_AL");
				int DA=-1;
				if(rs3.getString("DESTINO_AREA")!= null)
					DA = Integer.parseInt(rs3.getString("DESTINO_AREA"));
				int DB=-1;
				if(rs3.getString("DESTINO_BARCO")!= null)
					DB = Integer.parseInt(rs3.getString("DESTINO_BARCO"));

				ConsultaMovimientos con = new ConsultaMovimientos(idAgente, idCarga, origenBarco, FMB, FMA, DA, DB);
				resp.add(con);


			}

		}
		return resp;

	}

	public void almacenarCargas(ArrayList<CargaUnificada> cargas) throws SQLException
	{
		conn.setAutoCommit(false);
		conn.setTransactionIsolation(conn.TRANSACTION_SERIALIZABLE);
		CargaUnificada carga = null;
		boolean cargo = false;


		int i =0;

		while(i <cargas.size()&& !cargo)
		{
			carga = cargas.get(i);


			while(carga!=null && !cargo)
			{


				int a = 1;
				while(a < 9)
				{	

					if(verificarAlmacenamientoRF14(a, carga))
					{
						cargarArea1(a, carga);
						cargo = true;
						a = 9;
					}
					else a++;
				}

			}

			i++;
		}
	}

	public boolean verificarAlmacenamientoRF14(int area, CargaUnificada barco) throws SQLException
	{

		boolean verificar = false;
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


					if(barco != null)
					{
						System.out.println("A");
						int volCarga = (int) barco.getVolumen();
						String tipoCarga = barco.getTipo();
						System.out.println(capacidad+","+ volCarga);
						System.out.println(tipo);

						if((tipo.equalsIgnoreCase("BODEGA")
								&& (tipoCarga.equalsIgnoreCase("CONTENEDORES") || tipoCarga.equalsIgnoreCase("CONTENEDOR"))||
								(tipo.equalsIgnoreCase("PATIO")
										&& tipoCarga.equalsIgnoreCase("VEHICULOS"))||
								(tipo.equalsIgnoreCase("COBERTIZO")
										&& tipoCarga.equalsIgnoreCase("BIOTIPOS"))||
								(tipo.equalsIgnoreCase("SILO")
										&& tipoCarga.equalsIgnoreCase("GRANEL SOLIDO"))
								&& (capacidad > volCarga)))

						{
							System.out.println("B");
							verificar = true;
						}

					}
				}
			}
		}


		return verificar;
	}

	public void registrarTipoCargaArea(int area ,CargaUnificada carga) throws SQLException
	{	

		int idCarga = (int) carga.getValor();

		String sql2 = "INSERT INTO CARGASXALMACENAMIENTO VALUES (";
		sql2 +=  idCarga +  ",";
		sql2 += area + ",";
		sql2 += "TRUNC(SYSDATE)) ";

		System.out.println("SQL stmt:" + sql2);

		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		ResultSet rs2 = prepStmt2.executeQuery();
	}

	public void cargarArea1(int area, CargaUnificada barco)throws SQLException
	{

		registrarTipoCargaArea(area, barco);
		String sql2 = "UPDATE AREAS_DE_ALMACENAMIENTO "
				+ "SET AREAS_DE_ALMACENAMIENTO.ESTADO= 1"
				+ " WHERE AREAS_DE_ALMACENAMIENTO.ID="+area;

		System.out.println("SQL stmt:" + sql2);

		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		ResultSet rs2 = prepStmt2.executeQuery();

	}

	public double getCapacidad(String tipo) throws SQLException
	{
		double count = 0;
		if(tipo.equalsIgnoreCase("CONTENEDORES") || tipo.equalsIgnoreCase("CONTENEDOR"))
		{
			String sql2 = "SELECT COUNT(BODEGAS.AREAS) AS CUENTA "
					+ "FROM BODEGAS ";
			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();
			if(rs2.next())
			{
				count = Double.parseDouble(rs2.getString("CUENTA"));
			}
			
		}
		
		else if(tipo.equalsIgnoreCase("VEHICULOS"))
		{
			String sql2 = "SELECT COUNT(PATIOS.AREAS) AS CUENTA "
					+ "FROM PATIOS";
			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();
			if(rs2.next())
			{
				count = Double.parseDouble(rs2.getString("CUENTA"));
			}
			
		}
		
		else if(tipo.equalsIgnoreCase("BIOTIPOS"))
		{
			String sql2 = "SELECT COUNT(COBERTIZOS.AREAS) AS CUENTA "
					+ "FROM COBERTIZOS";
			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();
			if(rs2.next())
			{
				count = Double.parseDouble(rs2.getString("CUENTA"));
			}
		}
		
		else if(tipo.equalsIgnoreCase("GRANEL SOLIDO"))
		{
			String sql2 = "SELECT COUNT(SILOS.CAPACIDAD) AS CUENTA "
					+ "FROM SILOS";
			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();
			if(rs2.next())
			{
				count = Double.parseDouble(rs2.getString("CUENTA"));
			}
		}
		return count;
	}
	
}