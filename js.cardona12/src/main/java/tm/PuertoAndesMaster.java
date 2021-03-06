package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.jms.JMSException;

import vos.*;
import dao.*;
import dtm.CargaUnificada;
import dtm.PuertoAndesQueue;

/**
 * Fachada en patron singleton de la aplicación
 * @author Juan
 */
public class PuertoAndesMaster {


	/**
	 * Atributo estático que contiene el path relativo del archivo que tiene los datos de la conexión
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	/**
	 * Atributo estático que contiene el path absoluto del archivo que tiene los datos de la conexión
	 */
	private  String connectionDataPath;

	/**
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base de datos.
	 */
	private String user;

	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base de datos.
	 */
	private String password;

	/**
	 * Atributo que guarda el URL que se va a usar para conectarse a la base de datos.
	 */
	private String url;

	/**
	 * Atributo que guarda el driver que se va a usar para conectarse a la base de datos.
	 */
	private String driver;
	
	/**
	 * Conexión a la base de datos
	 */
	private Connection conn;
	
	private PuertoAndesQueue jms;


	/**
	 * Método constructor de la clase VideoAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logia de negocios que estas conllevan.
	 * <b>post: </b> Se crea el objeto VideoAndesMaster, se inicializa el path absoluto de el archivo de conexión y se
	 * inicializa los atributos que se usan par la conexión a la base de datos.
	 * @param contextPathP - path absoluto en el servidor del contexto del deploy actual
	 */
	public PuertoAndesMaster(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		jms = new PuertoAndesQueue(this);
		jms.inicializarContexto();
		initConnectionData();
	}

	/*
	 * Método que  inicializa los atributos que se usan para la conexion a la base de datos.
	 * <b>post: </b> Se han inicializado los atributos que se usan par la conexión a la base de datos.
	 */
	private void initConnectionData() {
		try {
			File arch = new File(this.connectionDataPath);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
			prop.load(in);
			in.close();
			this.url = prop.getProperty("url");
			this.user = prop.getProperty("usuario");
			this.password = prop.getProperty("clave");
			this.driver = prop.getProperty("driver");
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que  retorna la conexión a la base de datos
	 * @return Connection - la conexión a la base de datos
	 * @throws SQLException - Cualquier error que se genere durante la conexión a la base de datos
	 */
	private Connection darConexion() throws SQLException {
		System.out.println("Connecting to: " + url + " With user: " + user);
		return DriverManager.getConnection(url, user, password);
		
	}

	////////////////////////////////////////
	///////Transacciones////////////////////
	////////////////////////////////////////

	/**
	 * Método que modela la transacción que retorna todos los videos de la base de datos.
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ArrayList<Barco> darBarcos() throws Exception {
		ArrayList<Barco> barcos;
		DAOBarco daoBarco = new DAOBarco();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoBarco.setConn(conn);
			barcos = daoBarco.darBarcos();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoBarco.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return  barcos;
	}

	/**
	 * Método que modela la transacción que busca el/los videos en la base de datos con el nombre entra como parámetro.
	 * @param name - Nombre del video a buscar. name != null
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public Barco buscarBarcoPorName(int name) throws Exception {
		Barco barcos;
		DAOBarco daoBarcos = new DAOBarco();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoBarcos.setConn(conn);
			barcos = daoBarcos.buscarBarcoPorID(name);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoBarcos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return barcos;
	}
	
	/**
	 * Método que modela la transacción que agrega un solo video a la base de datos.
	 * <b> post: </b> se ha agregado el video que entra como parámetro
	 * @param barco - el video a agregar. video != null
	 * @throws Exception - cualquier error que se genera agregando el video
	 */
	public void addBarco(Barco barco) throws Exception {
		DAOBarco daoBarcos = new DAOBarco();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoBarcos.setConn(conn);
			daoBarcos.addBarco(barco);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoBarcos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	
	/**
	 * Método que modela la transacción que actualiza el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha actualizado el video que entra como parámetro
	 * @param barco - Video a actualizar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateBarco(Barco barco) throws Exception {
		DAOBarco daoBarcos = new DAOBarco();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoBarcos.setConn(conn);
			daoBarcos.updateBarco(barco);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoBarcos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que elimina el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha eliminado el video que entra como parámetro
	 * @param barcos - Video a eliminar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteBarco(Barco barcos) throws Exception {
		DAOBarco daoBarcos = new DAOBarco();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoBarcos.setConn(conn);
			daoBarcos.deleteBarco(barcos);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoBarcos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	
	/**
	 * Método que modela la transacción que retorna todos los videos de la base de datos.
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ArrayList<Puerto> darPuertos() throws Exception {
		ArrayList<Puerto> puertos;
		DAOPuerto daoPuerto = new DAOPuerto();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoPuerto.setConn(conn);
			puertos = daoPuerto.darPuertos();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPuerto.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return  puertos;
	}

	/**
	 * Método que modela la transacción que busca el/los videos en la base de datos con el nombre entra como parámetro.
	 * @param name - Nombre del video a buscar. name != null
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ArrayList<Puerto> buscarPuertoPorName(String name) throws Exception {
		ArrayList<Puerto> puertos;
		DAOPuerto daoPuertos = new DAOPuerto();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoPuertos.setConn(conn);
			puertos = daoPuertos.buscarPuertoPorName(name);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPuertos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return puertos;
	}
	
	/**
	 * Método que modela la transacción que agrega un solo video a la base de datos.
	 * <b> post: </b> se ha agregado el video que entra como parámetro
	 * @param puerto - el video a agregar. video != null
	 * @throws Exception - cualquier error que se genera agregando el video
	 */
	public void addPuerto(Puerto puerto) throws Exception {
		DAOPuerto daoPuertos = new DAOPuerto();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoPuertos.setConn(conn);
			daoPuertos.addPuerto(puerto);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPuertos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	
	/**
	 * Método que modela la transacción que actualiza el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha actualizado el video que entra como parámetro
	 * @param puerto - Video a actualizar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updatePuerto(Puerto puerto) throws Exception {
		DAOPuerto daoPuertos = new DAOPuerto();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoPuertos.setConn(conn);
			daoPuertos.updatePuerto(puerto);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPuertos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que elimina el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha eliminado el video que entra como parámetro
	 * @param puertos - Video a eliminar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deletePuerto(Puerto puertos) throws Exception {
		DAOPuerto daoPuertos = new DAOPuerto();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoPuertos.setConn(conn);
			daoPuertos.deletePuerto(puertos);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPuertos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	
	/**
	 * Método que modela la transacción que retorna todos los videos de la base de datos.
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ArrayList<Muelle> darMuelles() throws Exception {
		ArrayList<Muelle> muelles;
		DAOMuelle daoMuelle = new DAOMuelle();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoMuelle.setConn(conn);
			muelles = daoMuelle.darMuelles();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMuelle.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return  muelles;
	}

	/**
	 * Método que modela la transacción que busca el/los videos en la base de datos con el nombre entra como parámetro.
	 * @param name - Nombre del video a buscar. name != null
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ArrayList<Muelle> buscarMuellePorName(String name) throws Exception {
		ArrayList<Muelle> muelles;
		DAOMuelle daoMuelles = new DAOMuelle();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoMuelles.setConn(conn);
			muelles = daoMuelles.buscarMuellePorName(name);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMuelles.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return muelles;
	}
	
	/**
	 * Método que modela la transacción que agrega un solo video a la base de datos.
	 * <b> post: </b> se ha agregado el video que entra como parámetro
	 * @param muelle - el video a agregar. video != null
	 * @throws Exception - cualquier error que se genera agregando el video
	 */
	public void addMuelle(Muelle muelle) throws Exception {
		DAOMuelle daoMuelle = new DAOMuelle();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoMuelle.setConn(conn);
			daoMuelle.addMuelle(muelle);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMuelle.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public void addSalida(Salida salida) throws Exception {
		DAOSalida daoSalida = new DAOSalida();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoSalida.setConn(conn);
			daoSalida.addSalida(salida);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoSalida.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public void RegistrarSalida(Barco barco) throws Exception {
		DAOOperadorPortuario daoOperador = new DAOOperadorPortuario();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoOperador.setConn(conn);
			daoOperador.registrarSalidaBarco(barco);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoOperador.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public void registrarCargaPuerto(Carga carga, int barco) throws Exception {
		DAOOperadorPortuario daoOperador = new DAOOperadorPortuario();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoOperador.setConn(conn);
			daoOperador.registrarTipoCargaBarco(carga,barco);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoOperador.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public void registrarFactura(Barco barco) throws Exception {
		DAOOperadorPortuario daoOperador = new DAOOperadorPortuario();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoOperador.setConn(conn);
			daoOperador.registrarFactura(barco);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoOperador.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public void deshabilitarBarco(String td,Barco barco) throws Exception {
		DAOOperadorPortuario daoOperador = new DAOOperadorPortuario();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoOperador.setConn(conn);
			daoOperador.deshabilitarBarco(td, barco);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoOperador.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public void deshabilitarArea(int area) throws Exception {
		DAOOperadorPortuario daoOperador = new DAOOperadorPortuario();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoOperador.setConn(conn);
			daoOperador.deshabilitarArea(area);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoOperador.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public ArrayList<ConsultaMovimientos> darMovimientosCarga(int idAgente) throws Exception {
		DAOOperadorPortuario daoOperador = new DAOOperadorPortuario();
		ArrayList<ConsultaMovimientos> resp = new ArrayList<ConsultaMovimientos>();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoOperador.setConn(conn);
			resp =daoOperador.getMoviemientosCargas(idAgente);
			conn.commit();
			return resp;

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoOperador.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public ArrayList<ConsultaAreas> darMovimientosArea() throws Exception {
		DAOOperadorPortuario daoOperador = new DAOOperadorPortuario();
		ArrayList<ConsultaAreas> resp = new ArrayList<ConsultaAreas>();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoOperador.setConn(conn);
			resp =daoOperador.getArea1();
			conn.commit();
			return resp;

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoOperador.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public ArrayList<ConsultaAreas> darAreas(int area1,int area2) throws Exception {
		DAOOperadorPortuario daoOperador = new DAOOperadorPortuario();
		ArrayList<ConsultaAreas> resp = new ArrayList<ConsultaAreas>();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoOperador.setConn(conn);
			resp =daoOperador.getMovAreas(area1, area2);
			conn.commit();
			return resp;

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoOperador.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public void insertar() throws Exception {
		DAOOperadorPortuario daoOperador = new DAOOperadorPortuario();
		ArrayList<ConsultaAreas> resp = new ArrayList<ConsultaAreas>();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoOperador.setConn(conn);
			daoOperador.insertarDatos();
			conn.commit();
			

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoOperador.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public void registrarEntregaCarga(Carga carga) throws Exception {
		DAOOperadorPortuario daoOperador = new DAOOperadorPortuario();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoOperador.setConn(conn);
			daoOperador.registrarSalidaCarga(carga);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoOperador.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	public void cargarBuque(Carga carga,int barco) throws Exception {
		DAOOperadorPortuario daoOperador = new DAOOperadorPortuario();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoOperador.setConn(conn);
			daoOperador.cargarBuque(carga, barco);;
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoOperador.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public void cargarArea(int area,Barco barco) throws Exception {
		DAOOperadorPortuario daoOperador = new DAOOperadorPortuario();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoOperador.setConn(conn);
			daoOperador.cargarArea(area, barco);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoOperador.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	/**
	 * Método que modela la transacción que actualiza el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha actualizado el video que entra como parámetro
	 * @param muelle - Video a actualizar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateMuelle(Muelle muelle) throws Exception {
		DAOMuelle daoMuelle = new DAOMuelle();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoMuelle.setConn(conn);
			daoMuelle.updateMuelle(muelle);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMuelle.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que elimina el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha eliminado el video que entra como parámetro
	 * @param muelle - Video a eliminar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteMuelle(Muelle muelle) throws Exception {
		DAOMuelle daoMuelle = new DAOMuelle();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoMuelle.setConn(conn);
			daoMuelle.deleteMuelle(muelle);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMuelle.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	
	/**
	 * Método que modela la transacción que retorna todos los videos de la base de datos.
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ArrayList<OperadorPortuario> darOperadores() throws Exception {
		ArrayList<OperadorPortuario> operadores;
		DAOOperadorPortuario daoOperadorPortuario = new DAOOperadorPortuario();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoOperadorPortuario.setConn(conn);
			operadores = daoOperadorPortuario.darOperadores();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoOperadorPortuario.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return  operadores;
	}
	
	/**
	 * Método que modela la transacción que retorna todos los videos de la base de datos.
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ArrayList<respConsultaBarcos> darArribossBuques(ConsultaBarcos cons) throws Exception {
		ArrayList<respConsultaBarcos> barcos;
		DAOOperadorPortuario daoOperadorPortuario = new DAOOperadorPortuario();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoOperadorPortuario.setConn(conn);
			barcos = daoOperadorPortuario.getArribosBuques(cons);
			

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoOperadorPortuario.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return  barcos;
	}
	
	public ArrayList<respConsultaBarcos> darArribossBuquesEnFecha(ConsultaBarcos cons) throws Exception {
		ArrayList<respConsultaBarcos> barcos;
		DAOOperadorPortuario daoOperadorPortuario = new DAOOperadorPortuario();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoOperadorPortuario.setConn(conn);
			barcos = daoOperadorPortuario.getArribosBuquesEnFecha(cons);
			

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoOperadorPortuario.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return  barcos;
	}

	public ArrayList<ConsultaMovimientos> darMovCarga(int idAgente, String tipo, int costo) throws Exception {
		ArrayList<ConsultaMovimientos> barcos;
		DAOOperadorPortuario daoOperadorPortuario = new DAOOperadorPortuario();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoOperadorPortuario.setConn(conn);
			barcos = daoOperadorPortuario.getMovCargas(idAgente, tipo, costo);
			

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoOperadorPortuario.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return  barcos;
	}

	
	public double darCapacidad(String tipo) throws Exception {
		double barcos;
		DAOOperadorPortuario daoOperadorPortuario = new DAOOperadorPortuario();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoOperadorPortuario.setConn(conn);
			barcos = daoOperadorPortuario.getCapacidad(tipo);
			

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoOperadorPortuario.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return  barcos;
	}
	public void almacenarCargas(ArrayList<CargaUnificada> cargas) throws Exception {
		double barcos;
		DAOOperadorPortuario daoOperadorPortuario = new DAOOperadorPortuario();
		try 
		{
			
			//////Transacción
			this.conn = darConexion();
			daoOperadorPortuario.setConn(conn);
			daoOperadorPortuario.almacenarCargas(cargas);
			

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoOperadorPortuario.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		
	}
	/**
	 * Método que modela la transacción que busca el/los videos en la base de datos con el nombre entra como parámetro.
	 * @param name - Nombre del video a buscar. name != null
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ArrayList<OperadorPortuario> buscarOperadorPorName(String name) throws Exception {
		ArrayList<OperadorPortuario> operadores;
		DAOOperadorPortuario daoOperadorPortuario = new DAOOperadorPortuario();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoOperadorPortuario.setConn(conn);
			operadores = daoOperadorPortuario.buscarOperadorPorName(name);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoOperadorPortuario.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return operadores;
	}
	
	/**
	 * Método que modela la transacción que agrega un solo video a la base de datos.
	 * <b> post: </b> se ha agregado el video que entra como parámetro
	 * @param operador - el video a agregar. video != null
	 * @throws Exception - cualquier error que se genera agregando el video
	 */
	public void addOperador(OperadorPortuario operador) throws Exception {
		DAOOperadorPortuario daoOperadorPortuario = new DAOOperadorPortuario();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoOperadorPortuario.setConn(conn);
			daoOperadorPortuario.addOperador(operador);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoOperadorPortuario.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	
	/**
	 * Método que modela la transacción que actualiza el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha actualizado el video que entra como parámetro
	 * @param operador - Video a actualizar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateOperador(OperadorPortuario operador) throws Exception {
		DAOOperadorPortuario daoOperadorPortuario = new DAOOperadorPortuario();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoOperadorPortuario.setConn(conn);
			daoOperadorPortuario.updateOperador(operador);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoOperadorPortuario.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que elimina el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha eliminado el video que entra como parámetro
	 * @param operador - Video a eliminar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteOperador(OperadorPortuario operador) throws Exception {
		DAOOperadorPortuario daoOperadorPortuario = new DAOOperadorPortuario();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoOperadorPortuario.setConn(conn);
			daoOperadorPortuario.deleteOperador(operador);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoOperadorPortuario.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	/**
	 * Método que modela la transacción que retorna todos los videos de la base de datos.
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ArrayList<Carga> darCargas() throws Exception {
		ArrayList<Carga> cargas;
		DAOCarga daoCarga = new DAOCarga();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoCarga.setConn(conn);
			cargas = daoCarga.darCargas();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCarga.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return  cargas;
	}

	/**
	 * Método que modela la transacción que busca el/los videos en la base de datos con el nombre entra como parámetro.
	 * @param tipo - Nombre del video a buscar. name != null
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ArrayList<Carga> buscarCargaPorTipo(String tipo) throws Exception {
		ArrayList<Carga> cargas;
		DAOCarga daoCarga = new DAOCarga();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoCarga.setConn(conn);
			cargas = daoCarga.buscarCargaPorTipo(tipo);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCarga.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return cargas;
	}
	
	/**
	 * Método que modela la transacción que agrega un solo video a la base de datos.
	 * <b> post: </b> se ha agregado el video que entra como parámetro
	 * @param carga - el video a agregar. video != null
	 * @throws Exception - cualquier error que se genera agregando el video
	 */
	public void addCarga(Carga carga) throws Exception {
		DAOCarga daoCarga = new DAOCarga();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoCarga.setConn(conn);
			daoCarga.addCarga(carga);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCarga.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	
	/**
	 * Método que modela la transacción que actualiza el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha actualizado el video que entra como parámetro
	 * @param cargas - Video a actualizar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateCarga(Carga cargas) throws Exception {
		DAOCarga daoCarga = new DAOCarga();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoCarga.setConn(conn);
			daoCarga.updateCarga(cargas);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCarga.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que elimina el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha eliminado el video que entra como parámetro
	 * @param carga - Video a eliminar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteCarga(Carga carga) throws Exception {
		DAOCarga daoCarga = new DAOCarga();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoCarga.setConn(conn);
			daoCarga.deleteCarga(carga);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCarga.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public void terminarRF14(ArrayList<Carga> cargaMia, int idB) {
		try {
			ArrayList cargas = new ArrayList<CargaUnificada>();
			for(int i =0; i<cargaMia.size();i++)
			{
				Carga c = cargaMia.get(i);
				CargaUnificada ca = new CargaUnificada(c.getPeso(),c.getVolumen(),c.getTipo(),c.getCosto());
				
				cargas.add(ca);
			}
			almacenarCargas(cargas);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void iniciarRF14(int idB) throws Exception {
		
		System.out.println("inicia bien");
		ArrayList<Carga> cargas = new ArrayList<Carga>();
		conn = darConexion();
		conn.setAutoCommit(false);
		java.sql.Savepoint save = conn.setSavepoint();
		DAOBarco ba = new DAOBarco();
		ba.setConn(conn);
		try {
			Barco b = ba.buscarBarcoPorID(idB);
			if(b==null) throw new Exception("No existe el buque con id "+idB);
			String tipo = ba.gettipoCarga(b);
			int libreMio = (int) darCapacidad(tipo);
			double volumen = ba.getVolumenCarga(1, b);
			double diferencia = volumen - libreMio;
			if(diferencia <0) throw new Exception("toda la carga se puede almacenar ");
			else
			{
				double vol = volumen/6;
				for(int i =0; i<6;i++)
				{
					Carga c = new Carga(tipo, volumen, (100000 + i), 10000, 1, 2000, -1, i);
					cargas.add(c);
				}
			}
				
			

			if(cargas.size()==0) throw new Exception("No hay carga para descargar");
			
			System.out.println(libreMio);
			ArrayList<CargaUnificada> cargaU = new ArrayList<>();
			ArrayList<Carga> cargaMia = new ArrayList<>();
			for(Carga actual:cargas){
				if(libreMio-actual.getVolumen()>0){
					System.out.println("carga: "+actual.getId()+" vol: "+actual.getVolumen());
					libreMio-=actual.getVolumen();
					cargaMia.add(actual);
				}else{
					cargaU.add(new CargaUnificada(actual.getPeso(), actual.getVolumen(), tipo, actual.getCosto()));
				}
			}
			System.out.println(cargaU.size()+" - "+cargas.size()+" - "+libreMio);
			jms.empezarRF14(cargaU,cargaMia, idB);

			conn = darConexion();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback(save);
			System.out.println("Se hizo rollback al savepoint");
			throw e;
		} finally {
			ba.cerrarRecursos();
			conn.close();
		}

	
	}
	
	public boolean buscarExportador(String rut) throws SQLException{
		DAOTablaExportadores daoExportadores = new DAOTablaExportadores();
		try {
			this.conn = darConexion();
			conn.setAutoCommit(false);

			daoExportadores.setConn(conn);
			
			boolean resp =daoExportadores.buscarExportador(rut);
			
			daoExportadores.cerrarRecursos();
			return resp;
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoExportadores.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public void actualizarExportador(String rut, int descuento) throws SQLException{
		DAOTablaExportadores daoExportadores = new DAOTablaExportadores();
		try {
			this.conn = darConexion();
			conn.setAutoCommit(false);

			daoExportadores.setConn(conn);
			
			daoExportadores.actualizarExportador(rut, descuento);
			
			daoExportadores.cerrarRecursos();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoExportadores.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public ArrayList<Exportador> getExportadores() throws SQLException{
		DAOTablaExportadores daoExportadores = new DAOTablaExportadores();
		try {
			this.conn = darConexion();
			conn.setAutoCommit(false);
			ArrayList<Exportador> expos = new ArrayList<Exportador>();
			daoExportadores.setConn(conn);
			
			expos = daoExportadores.getExportadores();
			
			daoExportadores.cerrarRecursos();
			return expos;

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoExportadores.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		
	}
	
	public int inicarRF15(String rut) throws Exception {
		return jms.empezarRF15(rut);
	}
	
	public ListaAreaUnificada rfc11() throws Exception {
		ArrayList<AreaUnificada> cu = new ArrayList<>();

		cu.addAll(jms.empezarRFC11().getAreas());

		ArrayList<ConsultaAreas> lsa = darMovimientosArea();

		for (ConsultaAreas ca : lsa) {
			cu.add(new AreaUnificada(ca.getSEstado(), ca.getTipo()));
		}

		return new ListaAreaUnificada(cu);
	}
	
	public ListaExportadorUnificado rfc12(ParametroBusqueda pb) throws Exception {
		DAOTablaExportadores daoExportadores = new DAOTablaExportadores();
		ArrayList<ExportadorUnificado> ex = new ArrayList<>();
		try {
			this.conn = darConexion();

			daoExportadores.setConn(conn);

			String rango = "";
			for (String s : pb.getWhere()) {
				rango += s + " ";
			}

			ex.addAll(jms.empezarRFC12(rango));
			for(Exportador a: daoExportadores.getExportadores())
			{
				ExportadorUnificado d = new ExportadorUnificado(a.toString(), a.getCosto());
				ex.add(d);
			}
			

			daoExportadores.cerrarRecursos();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoExportadores.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaExportadorUnificado(ex);
	}
	
	  public RespuestaDescuento darDescuentoExportador2PC(String rut) {
		    int desc = jms.twoPhaseCommitRF15(rut);
		    RespuestaDescuento res = new RespuestaDescuento();
		    if(desc != 0) {
		      res.setSuccess(true);
		      res.setMessage("Se realizó el descuento correctamente");
		      res.setDescuento(desc);
		    } else {
		      res.setSuccess(false);
		      res.setMessage("No se encuentra el exportador.");
		      res.setDescuento(desc);
		    }
		    return res;
		  }

}

