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
package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.activation.DataSource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.UserTransaction;

import dao.DAOTablaAlmacenDeshabilitado;
import dao.DAOTablaAlmacenamiento;
import dao.DAOTablaArribos;
import dao.DAOTablaBodega;
import dao.DAOTablaBuque;
import dao.DAOTablaBuqueDeshabilitado;
import dao.DAOTablaCarga;
import dao.DAOTablaCargaEnAlmacen;
import dao.DAOTablaCargaEnBuque;
import dao.DAOTablaCobertizo;
import dao.DAOTablaEstadoBuque;
import dao.DAOTablaExportador;
import dao.DAOTablaFactura;
import dao.DAOTablaGenerica;
import dao.DAOTablaImportador;
import dao.DAOTablaMovimientoMaritimo;
import dao.DAOTablaMovimientoTerrestre;
import dao.DAOTablaMuelle;
import dao.DAOTablaMuelleOcupado;
import dao.DAOTablaPatio;
import dao.DAOTablaPersona;
import dao.DAOTablaSalidas;
import dao.DAOTablaSilo;
import dao.DAOTablaTipoCarga;
import dao.DAOTablaUsuarios;
import dao.DAOTablaVideos;
import dtm.CargaUnificada;
import dtm.JMSManager;
import exception.BuqueDeshabilitadoException;
import vos.Video;
import vos.Almacenamiento;
import vos.Arribo;
import vos.Bodega;
import vos.Buque;
import vos.Carga;
import vos.CargaEnAlmacen;
import vos.Cobertizo;
import vos.ConsultaUsuario;
import vos.Exportador;
import vos.Factura;
import vos.Importador;
import vos.InfoExportador;
import vos.ListaAlmacenamientos;
import vos.ListaArribos;
import vos.ListaArribosYSalidas;
import vos.ListaBuques;
import vos.ListaExportadores;
import vos.ListaImportadores;
import vos.ListaMovimientoAlmacen;
import vos.ListaMovimientoCarga2;
import vos.ListaMuelles;
import vos.ListaSalidas;
import vos.ListaTipoCarga;
import vos.ListaVideos;
import vos.MovimientoCarga;
import vos.Muelle;
import vos.Patio;
import vos.Salida;
import vos.Silo;
import vos.TipoCarga;
import vos.Usuario;

/**
 * Fachada en patron singleton de la aplicación
 * @author Juan
 */
public class VideoAndesMaster {


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

	/**
	 * 
	 */
	private JMSManager jms;


	/**
	 * Método constructor de la clase VideoAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logia de negocios que estas conllevan.
	 * <b>post: </b> Se crea el objeto VideoAndesMaster, se inicializa el path absoluto de el archivo de conexión y se
	 * inicializa los atributos que se usan par la conexión a la base de datos.
	 * @param contextPathP - path absoluto en el servidor del contexto del deploy actual
	 */
	public VideoAndesMaster(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();		
		jms = new JMSManager(this);
		System.out.println("HAPPENING");
	}




	/**
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
		System.out.println("Connecting to: " +  url + " With user: " + user);
		Connection conn = DriverManager.getConnection(url, user, password);
		return conn;
	}

	////////////////////////////////////////
	///////Transacciones////////////////////
	////////////////////////////////////////

	/**
	 * Metodo que modela la transaccion que agrega un importador a la base de datos.
	 * @param id 
	 * @throws Exception - cualquier error que se genere durante la transaccion.
	 */
	public void addExportador(Exportador exp, int id) throws Exception{
		if(!esRol(id,Usuario.ADMIN))throw new Exception("Solo los administradores pueden agregar exportadores.");
		DAOTablaExportador daoExportador = new DAOTablaExportador();
		DAOTablaPersona daoPersona = new DAOTablaPersona();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoExportador.setConn(conn);
			daoExportador.addExportador(exp);
			conn.commit();
			this.conn = darConexion();
			daoPersona.setConn(conn);
			daoPersona.addPersona(exp);
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
				daoExportador.cerrarRecursos();
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
	 * Metodo que modela la transaccion que agrega un exportador a la base de datos.
	 * @param id 
	 * @throws Exception - cualquier error que se genere durante la transaccion.
	 */
	public void addImportador(Importador imp, int id) throws Exception{
		if(!esRol(id,Usuario.ADMIN))throw new Exception("Solo los administradores pueden agregar importador.");
		DAOTablaImportador daoImportador = new DAOTablaImportador();
		DAOTablaPersona daoPersona = new DAOTablaPersona();

		try 
		{
			if(imp.esNatural() && imp.esTipoHabitual()) throw new Exception("Los importadores habituales no pueden ser personas naturales.");
			//////Transacción

			this.conn = darConexion();
			daoPersona.setConn(conn);
			daoPersona.addPersona(imp);
			conn.commit();

			this.conn = darConexion();
			daoImportador.setConn(conn);
			daoImportador.addImportador(imp);
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
				daoImportador.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public String darTipoPersona(int id) throws Exception {
		DAOTablaPersona daoPersona = new DAOTablaPersona();
		String resp = null;   
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoPersona.setConn(conn);
			resp = daoPersona.darTipoPersona(id);

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
				daoPersona.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return resp;
	}

	public boolean esRol(Integer id,String rol) throws Exception {
		if(id==null)return true;
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		boolean resp=false;		
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			resp=daoUsuarios.esRol(id,rol);

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
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return resp;
	}

	public String darTipoBuque(int idBuque) throws Exception {
		DAOTablaBuque daoBuques = new DAOTablaBuque();
		String resp = null;   
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoBuques.setConn(conn);
			resp = daoBuques.darTipoBuque(idBuque);

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
				daoBuques.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return resp;
	}

	/**
	 * Metodo que modela la transaccion que agrega un exportador a la base de datos.
	 * @param id 
	 * @throws Exception - cualquier error que se genere durante la transaccion.
	 */
	public void addAlmacenamiento(Almacenamiento al, int id) throws Exception{
		if(!esRol(id,Usuario.ADMIN))throw new Exception("Solo el administrador puede registrar zonas de almacenamiento");
		DAOTablaGenerica daoTipo = null;
		DAOTablaAlmacenamiento daoAlm = new DAOTablaAlmacenamiento();
		try 
		{
			if (al instanceof Bodega) {
				daoTipo = new DAOTablaBodega();
				this.conn = darConexion();
				daoTipo.setConn(conn);
				((DAOTablaBodega) daoTipo).addBodega((Bodega) al);
				conn.commit();
			}else if (al instanceof Patio) {
				daoTipo = new DAOTablaPatio();
				this.conn = darConexion();
				daoTipo.setConn(conn);
				((DAOTablaPatio) daoTipo).addPatio((Patio) al);
				conn.commit();
			}else if (al instanceof Silo) {
				daoTipo = new DAOTablaSilo();
				this.conn = darConexion();
				daoTipo.setConn(conn);
				((DAOTablaSilo) daoTipo).addSilo((Silo) al);
				conn.commit();
			}else if (al instanceof Cobertizo) {
				daoTipo = new DAOTablaCobertizo();
				this.conn = darConexion();
				daoTipo.setConn(conn);
				((DAOTablaCobertizo) daoTipo).addCobertizo((Cobertizo) al);
				conn.commit();
			}
			//////Transacción			
			this.conn = darConexion();
			daoAlm.setConn(conn);
			daoAlm.addAlmacenamiento(al);
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
				daoAlm.cerrarRecursos();
				daoTipo.cerrarRecursos();
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
	 * Metodo que modela la transaccion que agrega un exportador a la base de datos.
	 * @param id 
	 * @throws Exception - cualquier error que se genere durante la transaccion.
	 */
	public void addBuque(Buque buque, int id) throws Exception{
		if(!esRol(id,Usuario.ADMIN))throw new Exception("Solo el admin puede agregar buques");
		DAOTablaBuque daoBuque = new DAOTablaBuque();
		try 
		{

			//////Transacción			
			this.conn = darConexion();
			daoBuque.setConn(conn);
			daoBuque.addBuque(buque);
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
				daoBuque.cerrarRecursos();
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
	 * Metodo que modela la transaccion que agrega un arribo a la base de datos.
	 * @throws Exception - cualquier error que se genere durante la transaccion.
	 */
	public void addArribo(Arribo arribo, int id) throws Exception{
		if(!esRol(id,Usuario.AGENTE_PORTUARIO))throw new Exception("El arribo de un buque solo lo puede registrar un agente portuario");
		DAOTablaArribos daoArribo = new DAOTablaArribos();
		try 
		{
			//////Transacción			
			this.conn = darConexion();
			daoArribo.setConn(conn);
			daoArribo.addArribo(arribo);
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
				daoArribo.cerrarRecursos();
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
	 * Metodo que modela la transaccion que agrega una salida a la base de datos.
	 * @throws Exception - cualquier error que se genere durante la transaccion.
	 */
	public void addSalida(Salida salida, int id) throws Exception{
		if(!esRol(id,Usuario.AGENTE_PORTUARIO))throw new Exception("Solo el agente portuario puede registrar salida");
		DAOTablaSalidas daoSalida = new DAOTablaSalidas();
		DAOTablaMuelleOcupado daoMuellesO = new DAOTablaMuelleOcupado();
		try 
		{
			//////Transacción			
			this.conn = darConexion();
			daoSalida.setConn(conn);
			daoSalida.addSalida(salida);
			conn.commit();

			this.conn = darConexion();
			daoMuellesO.setConn(conn);
			daoMuellesO.deleteMuelleOcupado(salida.getIdMuelle());
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
				daoMuellesO.cerrarRecursos();
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
	 * Metodo que modela la transaccion que agrega una salida a la base de datos.
	 * @throws Exception - cualquier error que se genere durante la transaccion.
	 */
	public void addTipoCarga(TipoCarga tipoCarga, int idUser) throws Exception{
		String tipoBuque = darTipoBuque(tipoCarga.getIdBuque());
		boolean cargaCoincide = tipoBuque.equals(Buque.MULTI_PROPOSITO) ||
				tipoBuque.equals(Buque.RORO) && tipoCarga.esCargaRodada() ||
				tipoBuque.equals(Buque.PORTA_CONTENEDOR) && tipoCarga.esContenedor();

		if(!esRol(idUser,Usuario.AGENTE_PORTUARIO))
			throw new Exception("Solo el agente portuario puede registrar salida");
		if(!cargaCoincide)
			throw new Exception("El tipo de la carga y las condiciones (rodada, contenedor) no coincide con el tipo del buque.");

		DAOTablaTipoCarga daoTipoCarga = new DAOTablaTipoCarga();
		try 
		{
			//////Transacción			
			this.conn = darConexion();
			daoTipoCarga.setConn(conn);
			daoTipoCarga.registrarTipoCarga(tipoCarga);
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
				daoTipoCarga.cerrarRecursos();
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
	 * Metodo que modela la transaccion que agrega una salida a la base de datos.
	 * @throws Exception - cualquier error que se genere durante la transaccion.
	 */
	public void addCarga(Carga carga) throws Exception{
		DAOTablaCarga daoCarga = new DAOTablaCarga();
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
	public Carga getCarga(int id) throws Exception{
		Carga resp = null;
		DAOTablaCarga daoCarga = new DAOTablaCarga();
		try 
		{
			//////Transacción			
			this.conn = darConexion();
			daoCarga.setConn(conn);
			resp = daoCarga.darCargaPorId(id);
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
		if(resp==null) throw new Exception("No existe carga con el id "+id);
		return resp;
	}
	public Buque getBuque(int id, Connection conn) throws Exception{
		Buque resp = null;
		DAOTablaBuque daoBuque = new DAOTablaBuque();
		try 
		{
			daoBuque.setConn(conn);
			resp = daoBuque.darBuquePorId(id);
			//			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			daoBuque.cerrarRecursos();
		}
		if(resp==null) throw new Exception("No existe buque con el id "+id);
		return resp;
	}
	public Almacenamiento getAlmacenamiento(int id, Connection conn) throws Exception{
		ListaAlmacenamientos resp = null;
		DAOTablaAlmacenamiento daoAlmacenamiento = new DAOTablaAlmacenamiento();
		try 
		{
			daoAlmacenamiento.setConn(conn);
			resp = daoAlmacenamiento.consultarAlmacenamientos(0, null);
			//			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			daoAlmacenamiento.cerrarRecursos();
		}

		for(Bodega b : resp.getBodegas()) {
			if(b.getID() == id) { return b; }
		}

		for(Silo s : resp.getSilos()) {
			if(s.getID() == id) { return s; }
		}

		for(Cobertizo c : resp.getCobertizos()) {
			if(c.getID() == id) { return c; }
		}

		for(Patio p : resp.getPatios()) {
			if(p.getID() == id) { return p; }
		}

		throw new Exception("No existe almacenamiento con el id "+id);
	}
	public void cargarBuque(List<Integer> idCargas, int idB, int idUser, Connection conn) throws Exception{
		if(!esRol(idUser,Usuario.AGENTE_PORTUARIO))throw new Exception("Solo los agentes portuarios pueden agregar importador.");


		boolean debeCerrar = conn == null;

		if(conn == null) {
			conn = darConexion();
			conn.setAutoCommit(false);
		}

		Savepoint save = conn.setSavepoint();
		try {
			ArrayList<Carga> cargas = new ArrayList<Carga>();
			for(Integer id:idCargas){
				int almacen = cargaEnAlmacen(id, conn);
				if(almacen == -1) throw new Exception("La carga con id "+id+" no se encuentra en puerto andes");
				cargas.add(getCarga(id));
			}
			Buque b = getBuque(idB, conn);
			//RF10.1
			boolean compatibles = true;
			double volumenTotal = 0;
			for(int i=0; i<cargas.size() && compatibles;i++){
				Carga c = cargas.get(i);
				compatibles = verificarCompatibilidadCargaBuque(c,b);
				if(!compatibles) throw new Exception("La carga "+c.getTipoCarga()+" y el buque "+b.getTipo()+"no son compatibles");
				volumenTotal+=c.getVolumen();
			}
			boolean hayCapacidad = verificarCapacidadBuque(volumenTotal, b.getCapacidad(), b.getID(), conn);
			if(!hayCapacidad) throw new Exception("La carga sobrepasa la capacidad del buque");
			boolean buqueHabilitado = buqueHabilitado(b, conn);
			if(!buqueHabilitado) throw new Exception("El buque no esta habilitado para recibir carga " + b.getID());
			//TODO: Cambiar el estado del buque a Proceso de carga.
			cambiarEstadoBuque(Buque.PROCESO_CARGA,b, conn); //Sera que toca hacer un save point aca?
			//RF10.2
			for(int i=0; i<cargas.size() && compatibles;i++){
				Carga c = cargas.get(i);
				int idAlmacen = cargaEnAlmacen(c.getID(), conn);
				registrarCargaBuque(c, b, idAlmacen, conn);
				liberarAreaAlmacenamiento(c, conn);
			}
			//10.4
			cambiarEstadoBuque(Buque.CARGADO,b, conn); //Sera que toca hacer un save point aca?

			conn.commit();
		} catch (Exception e) {
			conn.rollback(save);
			System.out.println("Se hizo rollback al savepoint");
			throw e;
		} finally {
			if(debeCerrar) conn.close();
		}
	}
	public int cargaEnAlmacen(Integer id, Connection conn) throws Exception{
		int cargaEnAlmacen = -1;
		DAOTablaCargaEnAlmacen daoCargaEnAlmacen = new DAOTablaCargaEnAlmacen();
		try 
		{
			//////Transacción			
			//			this.conn = darConexion();
			daoCargaEnAlmacen.setConn(conn);
			cargaEnAlmacen = daoCargaEnAlmacen.cargaEsta(id);
			//			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			daoCargaEnAlmacen.cerrarRecursos();
		}
		return cargaEnAlmacen;
	}

	public boolean buqueHabilitado(Buque b, Connection conn) throws Exception{
		boolean buqueHabilitado = false;
		DAOTablaBuqueDeshabilitado daoBuqueDeshabilitado = new DAOTablaBuqueDeshabilitado();
		try 
		{
			//////Transacción			
			//			this.conn = darConexion();
			daoBuqueDeshabilitado.setConn(conn);
			buqueHabilitado = daoBuqueDeshabilitado.esHabilitado(b.getID());
			//			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			daoBuqueDeshabilitado.cerrarRecursos();
		}
		return buqueHabilitado;
	}

	public boolean almacenamientoHabilitado(Almacenamiento a, Connection conn) throws Exception{
		boolean buqueHabilitado = false;
		DAOTablaAlmacenDeshabilitado daoAlmacenDeshabilitado = new DAOTablaAlmacenDeshabilitado();
		try 
		{
			//////Transacción     
			//      this.conn = darConexion();
			daoAlmacenDeshabilitado.setConn(conn);
			buqueHabilitado = daoAlmacenDeshabilitado.esHabilitado(a.getID());
			//      conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			daoAlmacenDeshabilitado.cerrarRecursos();
		}
		return buqueHabilitado;
	}

	public void registrarCargaBuque(Carga c, Buque b, int idAlmacen, Connection conn) throws SQLException, Exception{
		DAOTablaCargaEnBuque daoCargaEnBuque = new DAOTablaCargaEnBuque();
		try 
		{
			daoCargaEnBuque.setConn(conn);
			daoCargaEnBuque.cargarBuqueConCarga(c, b);
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			daoCargaEnBuque.cerrarRecursos();
		}

		DAOTablaMovimientoMaritimo daoMov = new DAOTablaMovimientoMaritimo();
		try 
		{
			daoMov.setConn(conn);
			daoMov.addMovimientoMaritimo(c.getID(), idAlmacen, b.getID(), "CARGUE");
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			daoMov.cerrarRecursos();
		}
	}

	public void descargarCargaAAlmacen(Carga c, Buque b, int idAlmacen, Connection conn) throws SQLException, Exception{
		DAOTablaCargaEnAlmacen daoCargaEnAlmacen = new DAOTablaCargaEnAlmacen();
		try 
		{
			daoCargaEnAlmacen.setConn(conn);
			daoCargaEnAlmacen.addCargaEnAlmacen(new CargaEnAlmacen(c.getID(), idAlmacen));
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			daoCargaEnAlmacen.cerrarRecursos();
		}

		DAOTablaMovimientoMaritimo daoMov = new DAOTablaMovimientoMaritimo();
		try 
		{
			daoMov.setConn(conn);
			daoMov.addMovimientoMaritimo(c.getID(), idAlmacen, b.getID(), "DESCARGUE");
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			daoMov.cerrarRecursos();
		}
	}


	public boolean verificarCompatibilidadCargaBuque(Carga carga, Buque buque){
		return (buque.getTipo().equals(Buque.MULTI_PROPOSITO)) ||
				(buque.getTipo().equals(Buque.RORO) && carga.getRodada()) ||
				(buque.getTipo().equals(Buque.PORTA_CONTENEDOR) && carga.getContenedor());
	}

	public boolean verificarCapacidadBuque(double volumenNuevaCarga, double capacidadBuque, int idBuque, Connection conn) throws Exception{
		double volumenOcupado = darVolumenOcupadoBuque(idBuque, conn);
		return volumenOcupado+volumenNuevaCarga<=capacidadBuque;
	}

	public double darVolumenOcupadoBuque(int id, Connection conn) throws Exception{
		double resp = 0;
		DAOTablaCargaEnBuque daoCargaEnBuque = new DAOTablaCargaEnBuque();
		try 
		{
			//////Transacción			
			//			this.conn = darConexion();
			daoCargaEnBuque.setConn(conn);
			resp=daoCargaEnBuque.darVolumenCargaEnBuque(id);
			//			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			daoCargaEnBuque.cerrarRecursos();
		}
		return resp;
	}

	public void cambiarEstadoBuque(String estado, Buque b, Connection conn)throws Exception{
		DAOTablaEstadoBuque daoEstado = new DAOTablaEstadoBuque();
		try 
		{
			daoEstado.setConn(conn);
			daoEstado.setEstado(estado,b.getID());
			//			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			daoEstado.cerrarRecursos();

		}
	}

	public void reservarAlmacen(Almacenamiento a, Date fecha, int numElementos, String tipoCarga, double capacidad, Connection conn){
		//11.1 hacer savepoint?

	}
	public void descargarCargaPuertoAndes(int idA, int idB, Date fecha) throws Exception{
		ArrayList<Carga> cargas;

		conn = darConexion();
		conn.setAutoCommit(false);
		Savepoint save = conn.setSavepoint();

		try {
			Almacenamiento a = getAlmacenamiento(idA, conn);
			Buque b = getBuque(idB, conn);

			if(a==null) throw new Exception("No existe el almacenamiento con id "+idA);
			if(b==null) throw new Exception("No existe el buque con id "+idB);

			cargas = darCargasDestinoPuertoAndes(b, conn);

			if(cargas.size()==0) throw new Exception("No hay carga para descargar");

			descargarBuque(a, b, fecha, cargas, conn);

			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback(save);
			System.out.println("Se hizo rollback al savepoint");
			throw e;
		} finally {
			conn.close();
		}

	}
	public void descargarBuque(Almacenamiento a, Buque b, Date fecha, ArrayList<Carga> cargas, Connection conn) throws Exception{

		//RF11.1
		for(Carga c:cargas){
			String tipoCarga = c.getTipoCarga();
			int numCarga = c.getNumero();
			double capacidad=c.getVolumen();
			reservarAlmacen(a, fecha, numCarga, tipoCarga, capacidad, conn);
		}
		//RF11.2
		cambiarEstadoBuque(Buque.PROCESO_DESCARGUE,b, conn);
		for(Carga c:cargas){
			// registrarDisminucionTipoCargaBuque(c,b, conn);//FALTA
			registrarDescargaBuque(c,b, conn);
			descargarCargaAAlmacen(c, b, a.getID(), conn);
			//			registrarMovimientoTerrestreCargaBuque(c.getID(),TipoCarga.DESCARGA, conn);//FALTA

			//RF11.3
			cargarAreaAlmacenamiento(c,a, conn);//FALTA
		}
		//11.4
		cambiarEstadoBuque(Buque.DESCARGADO,b, conn); //Sera que toca hacer un save point aca?
	}

	public void registrarDisminucionTipoCargaBuque(Carga c, Buque b, Connection conn) throws SQLException{

		DAOTablaTipoCarga daoEstado = new DAOTablaTipoCarga();
		try 
		{
			daoEstado.setConn(conn);
			daoEstado.registrarRetiroCarga(c,b);//FALTA HACER ESTE
			//			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			daoEstado.cerrarRecursos();
		}
	}

	public void registrarDescargaBuque(Carga c, Buque b, Connection conn) throws SQLException{

		DAOTablaCargaEnBuque daoEstado = new DAOTablaCargaEnBuque();
		try 
		{
			daoEstado.setConn(conn);
			daoEstado.registrarRetiro(b.getID(), c);
			//			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			daoEstado.cerrarRecursos();
		}
	}
	public void cargarAreaAlmacenamiento(Carga c, Almacenamiento a, Connection conn){
		//TODO: Completar el metodo que cargue la carga en el almacenamiento	
		//DEBE utilizar como subtransacci�n la implementaci�n del requerimiento RF7
		//y un requerimiento de registrar la descarga de una carga de un buque, de la iteraci�n anterior. 
	}
	public ArrayList<Carga> darCargasDestinoPuertoAndes(Buque b, Connection conn)throws Exception{
		ArrayList<Carga> resp = null;
		DAOTablaCargaEnBuque daoCarga = new DAOTablaCargaEnBuque();
		try 
		{
			//////Transacción			
			//			this.conn = darConexion();
			daoCarga.setConn(conn);
			resp = daoCarga.darCargasDestinoPuertoAndes(b.getID());
			//			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			daoCarga.cerrarRecursos();
		}
		return resp;
	}
	public ArrayList<Carga> darTodasCargasBuque(Buque b, Connection conn) throws Exception{
		ArrayList<Carga> resp = null;
		DAOTablaCargaEnBuque daoCarga = new DAOTablaCargaEnBuque();
		try 
		{
			//////Transacción     
			//      this.conn = darConexion();
			daoCarga.setConn(conn);
			resp = daoCarga.darCargasEnBuque(b.getID());
			//      conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			daoCarga.cerrarRecursos();
		}
		return resp;
	}

	public void deshabilitarBuque(int idB, String razon, int idUser) throws Exception{
		this.conn = darConexion();
		conn.setAutoCommit(false);

		try {
			Buque b = getBuque(idB, conn);

			registrarDeshabilidadBuque(b,razon, conn);

			if(!razon.equals(Buque.Deshabilitacion.POLITICA)){			
				descargarBuqueEmergencia( b, new Date(), idUser, conn);
			}

			conn.commit();
		} catch(Exception e) {
			//		  conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
	}
	public void descargarBuqueEmergencia(Buque b, Date fecha, int idUser, Connection conn) throws Exception{
		ArrayList<Carga> cargas = darTodasCargasBuque(b, conn);
		ArrayList<Carga> cargasQueNoPudieronGuardarse = new ArrayList<Carga>();

		Savepoint save = conn.setSavepoint();
		try {
			for(Carga c:cargas){
				Almacenamiento a = buscarAlmacenamientoAdecuado(c, conn);
				Buque buqueReemplazo = buscarBuqueAdecuado(c, conn);

				if(buqueReemplazo!=null){
					System.out.println("Se va a poner en buque");
					ArrayList<Integer> idCargas = new ArrayList<Integer>();
					idCargas.add(c.getID());
					cargarBuque(idCargas, buqueReemplazo.getID(), idUser, conn);
					registrarDescargaBuque(c, b, conn);

				}else if(a!=null){
					System.out.println("Se va a poner en almacen");
					ArrayList<Carga> cs = new ArrayList<Carga>();
					cs.add(c);
					descargarBuque(a, b, fecha, cs, conn);

				}else{
					cargasQueNoPudieronGuardarse.add(c);
				}

				//  			conn.commit();
				save = conn.setSavepoint(); // guarda estado
			}
		} catch(Exception e) {
			e.printStackTrace();
			conn.rollback(save);
			throw e;
		}
		if(cargasQueNoPudieronGuardarse.size()!=0){
			throw new BuqueDeshabilitadoException("No se pudieron descargar todas las cargas. Se dejaron en el buque.", cargasQueNoPudieronGuardarse);
		}
		//		conn.commit();
	}
	public Almacenamiento buscarAlmacenamientoAdecuado(Carga c, Connection conn) throws Exception {
		ListaAlmacenamientos resp = null;
		DAOTablaAlmacenamiento daoAlmacenamiento = new DAOTablaAlmacenamiento();
		try 
		{
			daoAlmacenamiento.setConn(conn);
			resp = daoAlmacenamiento.consultarAlmacenamientos(0, null);
			//      conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			daoAlmacenamiento.cerrarRecursos();
		}

		DAOTablaCargaEnAlmacen daoCargaAlmacen = new DAOTablaCargaEnAlmacen();
		try {
			daoCargaAlmacen.setConn(conn);

			double vol = c.getVolumen();

			for(Bodega b : resp.getBodegas()) {
				double sumaCapacidad = b.getAncho() * b.getLargo();
				List<Carga> cargas = daoCargaAlmacen.darCargasEnAlmacen(b.getID());

				for(Carga carga : cargas) {
					sumaCapacidad -= carga.getVolumen();
				}

				if(sumaCapacidad - vol >= 0 && almacenamientoHabilitado(b, conn) ) {
					return b;
				}
			}

			for(Silo b : resp.getSilos()) {
				double sumaCapacidad = b.getCapacidad();
				List<Carga> cargas = daoCargaAlmacen.darCargasEnAlmacen(b.getID());

				for(Carga carga : cargas) {
					sumaCapacidad -= carga.getVolumen();
				}

				if(sumaCapacidad - vol >= 0 && almacenamientoHabilitado(b, conn)) { return b; }
			}

			for(Cobertizo b : resp.getCobertizos()) {
				double sumaCapacidad = b.getAncho() * b.getLargo();
				List<Carga> cargas = daoCargaAlmacen.darCargasEnAlmacen(b.getID());
				for(Carga carga : cargas) {
					sumaCapacidad -= carga.getVolumen();
				}
				if(sumaCapacidad - vol >= 0 && almacenamientoHabilitado(b, conn)) { return b; }
			}

			for(Patio b : resp.getPatios()) {
				double sumaCapacidad = b.getAncho() * b.getLargo();
				List<Carga> cargas = daoCargaAlmacen.darCargasEnAlmacen(b.getID());
				for(Carga carga : cargas) {
					sumaCapacidad -= carga.getVolumen();
				}
				if(sumaCapacidad - vol >= 0 && almacenamientoHabilitado(b, conn)) { return b; }
			}

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			daoCargaAlmacen.cerrarRecursos();
		}

		return null;
	}
	public Buque buscarBuqueAdecuado(Carga c, Connection conn) throws Exception {
		//TODO: Hacer metodo que segun el tipo de la carga, lo que ocupe y su destino, retorne el 
		//buque adecuado. Null si no encuentra.

		ArrayList<Buque> resp = null;
		DAOTablaBuque daoBuque = new DAOTablaBuque();
		try 
		{
			//////Transacción     
			//      this.conn = darConexion();
			daoBuque.setConn(conn);
			resp = daoBuque.darBuques();
			//      conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			daoBuque.cerrarRecursos();
		}

		for(Buque b : resp) {
			if(verificarCapacidadBuque(c.getVolumen(), b.getCapacidad(), b.getID(), conn) &&
					verificarCompatibilidadCargaBuque(c, b) &&
					buqueHabilitado(b, conn) &&
					b.getDestino().equals(c.getDestino())) {
				return b;
			}

		}
		return null;
	}
	public void registrarDeshabilidadBuque(Buque b, String razon, Connection conn)throws Exception{
		DAOTablaBuqueDeshabilitado daoBuque = new DAOTablaBuqueDeshabilitado();
		try 
		{
			daoBuque.setConn(conn);
			daoBuque.registrarDeshabilidad(b, razon);
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
			daoBuque.cerrarRecursos();
		}

	}

	public void cerrarAreaAlmacenamiento(int idA, String razon, int idUser) throws Exception{
		this.conn = darConexion();
		conn.setAutoCommit(false);

		try {
			Almacenamiento a = getAlmacenamiento(idA, conn);
			registrarDeshabilidadAlmacenamiento(a, razon, conn);
			ArrayList<Carga> cargas = darCargasAlmacenamiento(a);
			ArrayList<Carga> cargasQueNoPudieronGuardarse = new ArrayList<Carga>();

			for(Carga c:cargas) {
				System.out.println(c.getID() + "carga");
				Almacenamiento alReemplazo = buscarAlmacenamientoAdecuado(c, conn);
				Buque buque = buscarBuqueAdecuado(c, conn);
				if(alReemplazo!=null){
					ArrayList<Carga> cs = new ArrayList<Carga>();
					cs.add(c);
					liberarAreaAlmacenamiento(c, conn);
					addAlmacenamientoCarga(new CargaEnAlmacen(c.getID(), alReemplazo.getID()), conn);

				}else if(buque!=null && c.getDestino().equals(buque.getDestino())){
					ArrayList<Integer> idCargas = new ArrayList<Integer>();
					idCargas.add(c.getID());
					cargarBuque(idCargas, buque.getID(), idUser, conn);
				}else{
					cargasQueNoPudieronGuardarse.add(c);
				}
			}

			if(cargasQueNoPudieronGuardarse.size()!=0){
				throw new BuqueDeshabilitadoException("No se pudieron guardar todas las cargas. Se dejaron en el buque.", cargasQueNoPudieronGuardarse);
			}

			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			throw e;
		}
	}
	public ArrayList<Carga> darCargasAlmacenamiento(Almacenamiento a)throws Exception{
		ArrayList<Carga> resp = null;
		DAOTablaCargaEnAlmacen daoAlmacen = new DAOTablaCargaEnAlmacen();
		try 
		{
			//////Transacción			
			this.conn = darConexion();
			daoAlmacen.setConn(conn);
			resp = daoAlmacen.darCargasEnAlmacen(a.getID());
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
			daoAlmacen.cerrarRecursos();
		}
		return resp;
	}
	public void registrarDeshabilidadAlmacenamiento(Almacenamiento a, String razon, Connection conn) throws SQLException{
		//TODO: Hacer que se agrege el almacen a la nueva tabla de ALMACENESDESHABILITADOS
		DAOTablaAlmacenDeshabilitado daoCarga = new DAOTablaAlmacenDeshabilitado();
		try 
		{
			//////Transacción			
			//			this.conn = darConexion();
			daoCarga.setConn(conn);
			daoCarga.registrarDeshabilidad(a, razon);
			//			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			daoCarga.cerrarRecursos();
		}
	}
	/**
	 * Metodo que modela la transaccion que agrega una salida a la base de datos.
	 * @throws Exception - cualquier error que se genere durante la transaccion.
	 */
	public void addAlmacenamientoCarga(CargaEnAlmacen carga, Connection conn) throws Exception{
		DAOTablaCargaEnAlmacen daoCarga = new DAOTablaCargaEnAlmacen();
		try 
		{
			//////Transacción			
			this.conn = darConexion();
			daoCarga.setConn(conn);
			daoCarga.addCargaEnAlmacen(carga);
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
			daoCarga.cerrarRecursos();
		}
	}


	/**
	 * Método que modela la transacción que retorna todos los videos de la base de datos.
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ListaImportadores darImportadores() throws Exception {
		ArrayList<Importador> importadores;
		DAOTablaImportador daoImportador = new DAOTablaImportador();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoImportador.setConn(conn);
			importadores = daoImportador.darImportadores();

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
				daoImportador.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaImportadores(importadores);
	}
	/**
	 * Método que modela la transacción que retorna todos los videos de la base de datos.
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ListaExportadores darExportadores() throws Exception {
		ArrayList<Exportador> exportadores;
		DAOTablaExportador daoExportador = new DAOTablaExportador();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoExportador.setConn(conn);
			exportadores = daoExportador.darExportadores();

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
				daoExportador.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaExportadores(exportadores);
	}

	/**
	 * Método que modela la transacción que retorna todos los videos de la base de datos.
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public InfoExportador darExportador(Integer id_exportador, Boolean natural, String tipoBuque,
			Date inicio, Date fin, String orden) throws Exception {
		InfoExportador exportador;
		DAOTablaExportador daoExportador = new DAOTablaExportador();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoExportador.setConn(conn);
			exportador = daoExportador.darInfoExportador(id_exportador, natural, tipoBuque, inicio, fin, orden);

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
				daoExportador.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return exportador;
	}


	/**
	 * Método que modela la transacción que retorna todos los videos de la base de datos.
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ListaBuques darBuques() throws Exception {
		ArrayList<Buque> buques;
		DAOTablaBuque daoBuque = new DAOTablaBuque();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoBuque.setConn(conn);
			buques = daoBuque.darBuques();

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
				daoBuque.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaBuques(buques);
	}

	/**
	 * Método que modela la transacción que retorna todos los videos de la base de datos.
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ListaArribos darArribos() throws Exception {
		ArrayList<Arribo> arribos;
		DAOTablaArribos daoArribos = new DAOTablaArribos();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoArribos.setConn(conn);
			arribos = daoArribos.darArribos();

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
				daoArribos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaArribos(arribos);
	}

	/**
	 * Método que modela la transacción que retorna todos los videos de la base de datos.
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ListaSalidas darSalidas() throws Exception {
		ArrayList<Salida> salidas;
		DAOTablaSalidas daoSalidas = new DAOTablaSalidas();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoSalidas.setConn(conn);
			salidas = daoSalidas.darSalidas();

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
				daoSalidas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaSalidas(salidas);
	}
	/**
	 * Método que modela la transacción que retorna todos los videos de la base de datos.
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ListaArribosYSalidas darArribosSalidas(String nombreBuque,
			String tipoBuque, Date inicio, Date fin) throws Exception{
		ArrayList<Salida> salidas=null;
		ArrayList<Arribo> arribos = null;
		DAOTablaSalidas daoSalidas = new DAOTablaSalidas();
		DAOTablaArribos daoArribos = new DAOTablaArribos();
		try 
		{
			//////Transacción1
			this.conn = darConexion();
			daoSalidas.setConn(conn);
			salidas = daoSalidas.darSalidas(nombreBuque, tipoBuque, inicio, fin);

			//////Transacción2
			this.conn = darConexion();
			daoArribos.setConn(conn);
			arribos = daoArribos.darArribos(nombreBuque, tipoBuque, inicio, fin);

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
				daoArribos.cerrarRecursos();
				daoSalidas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaArribosYSalidas(arribos,salidas);
	}
	/**
	 * Método que modela la transacción que retorna todos los videos de la base de datos.
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ListaTipoCarga darTiposCarga() throws Exception {
		ArrayList<TipoCarga> tipos;
		DAOTablaTipoCarga daoTipoCarga = new DAOTablaTipoCarga();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoTipoCarga.setConn(conn);
			tipos = daoTipoCarga.darTipoCargas();

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
				daoTipoCarga.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaTipoCarga(tipos);
	}

	public ConsultaUsuario buscarInfoUsiario(Integer id) throws Exception{
		ConsultaUsuario info_usuario=null;
		DAOTablaVideos daoVideos = new DAOTablaVideos();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVideos.setConn(conn);
			//			info_usuario = daoVideos.darVideoMasAlquilado();

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
				daoVideos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return info_usuario;
	}

	/**
	 * Método que modela la transacción que retorna todos los videos de la base de datos.
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ListaMuelles darMuelles() throws Exception {
		ArrayList<Muelle> muelles;
		DAOTablaMuelle daoMuelle = new DAOTablaMuelle();
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
		return new ListaMuelles(muelles);
	}

	public void addMuelle(Muelle muelle, int idUser) throws Exception{
		if(!esRol(idUser,Usuario.ADMIN))throw new Exception("Solo el administrador puede agregar muelle");
		DAOTablaMuelle daoMuelle = new DAOTablaMuelle();
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

	public void liberarAreaAlmacenamiento(Carga carga, Connection conn) throws Exception {

		DAOTablaCargaEnAlmacen daoCargaAlmacen = new DAOTablaCargaEnAlmacen();
		try 
		{
			daoCargaAlmacen.setConn(conn);
			daoCargaAlmacen.deleteCargaEnAlmacen(carga.getID());

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			daoCargaAlmacen.cerrarRecursos();
		}
	}

	public void recogerCarga(Carga carga, int idUser) throws Exception {
		if(!esRol(idUser,Usuario.AGENTE_PORTUARIO))throw new Exception("La recogida de una carga solo la puede registrar un agente portuario");
		DAOTablaCargaEnAlmacen daoCargaAlmacen = new DAOTablaCargaEnAlmacen();
		DAOTablaCarga daoCarga = new DAOTablaCarga();
		DAOTablaMovimientoTerrestre daoMovimiento = new DAOTablaMovimientoTerrestre();
		try 
		{
			//////Transacción     
			this.conn = darConexion();
			daoCarga.setConn(conn);
			int id = daoCarga.darIdDeCarga(carga);

			daoCargaAlmacen.setConn(conn);
			CargaEnAlmacen cargaAlmacen = daoCargaAlmacen.deleteCargaEnAlmacen(id);

			if(cargaAlmacen == null)
				throw new Exception("No existe la carga dada en ningún almacenamiento.");

			this.conn = darConexion();
			daoMovimiento.setConn(conn);
			daoMovimiento.addMovimientoTerrestre(cargaAlmacen, CargaEnAlmacen.DESCARGA);


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
				daoCargaAlmacen.cerrarRecursos();
				daoMovimiento.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void generarFactura(Factura factura, int idUser) throws Exception {
		if(!esRol(idUser,Usuario.AGENTE_PORTUARIO))throw new Exception("La recogida de una carga solo la puede registrar un agente portuario");
		DAOTablaFactura daoFactura = new DAOTablaFactura();
		try 
		{
			//////Transacción     
			this.conn = darConexion();
			daoFactura.setConn(conn);
			int valor=daoFactura.generarFactura(factura.getIdBuque(), factura.getFecha());
			factura.setValorTotal(valor);

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
				daoFactura.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public ListaArribos getInfoArribos(Date inicio, Date fin, String nBuque,
			String tipoBuque, String tipoCarga,String orden)
					throws Exception{
		DAOTablaArribos daoArribo = new DAOTablaArribos();
		ListaArribos resp = null;
		try 
		{
			//////Transacción			
			this.conn = darConexion();
			daoArribo.setConn(conn);
			resp = daoArribo.getArribosWhere(inicio,fin,nBuque,tipoBuque,tipoCarga,orden);
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
				daoArribo.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return resp;
	}
	public List<MovimientoCarga> darMovimientosCarga(int idPersona, Date inicio, Date fin) throws Exception {
		DAOTablaMovimientoMaritimo daoMovCarga = new DAOTablaMovimientoMaritimo();
		List<MovimientoCarga> lista = new ArrayList<>();
		try 
		{
			//////Transacción1
			this.conn = darConexion();
			daoMovCarga.setConn(conn);

			String rol = darTipoPersona(idPersona);

			lista = daoMovCarga.darMovimientos(idPersona, rol, inicio, fin);

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
				daoMovCarga.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return lista;
	}

	public ListaAlmacenamientos consultarAlmacenamientos(int idPersona, Connection conn) throws Exception {
		DAOTablaAlmacenamiento daoAlma = new DAOTablaAlmacenamiento();
		ListaAlmacenamientos lista = new ListaAlmacenamientos(
				new ArrayList<Silo>(),
				new ArrayList<Cobertizo>(),
				new ArrayList<Bodega>(),
				new ArrayList<Patio>());

		boolean debeCerrar = conn == null;

		try 
		{
			if(conn == null) {
				conn = darConexion();
			}

			daoAlma.setConn(conn);

			lista = daoAlma.consultarAlmacenamientos(idPersona, null);

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
				daoAlma.cerrarRecursos();
				if(debeCerrar){
					conn.close();
				}
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return lista;
	}
	public ListaArribos getInfoArribosWhereNot(Date inicio, Date fin, String nBuque,
			String tipoBuque, String tipoCarga,String orden)
					throws Exception{
		DAOTablaArribos daoArribo = new DAOTablaArribos();
		ListaArribos resp = null;
		try 
		{
			//////Transacción			
			this.conn = darConexion();
			daoArribo.setConn(conn);
			resp = daoArribo.getArribosWhereNot(inicio,fin,nBuque,tipoBuque,tipoCarga,orden);
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
				daoArribo.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return resp;
	}

	public ListaMovimientoCarga2 getMovimientosCargaExpConValorMayorA(Integer usuario, int valor, String tipoCarga)
			throws Exception{
		boolean todosLosExportadores = esRol(usuario,Usuario.ADMIN);
		DAOTablaCarga daoCarga = new DAOTablaCarga();
		ListaMovimientoCarga2 resp = null;
		try 
		{
			//////Transacción			
			this.conn = darConexion();
			daoCarga.setConn(conn);
			resp = daoCarga.getMovimientosCargaExpConValorMayorA(todosLosExportadores,usuario,valor,tipoCarga);
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
		return resp;
	}

	public ListaMovimientoAlmacen consultarDosAlmacenamientos(int user, int area1, int area2) throws Exception{
		if(!esRol(user,Usuario.ADMIN)) throw new Exception("Este movimiento solo lo puede hacer un admin.");
		DAOTablaAlmacenamiento daoAlmacen = new DAOTablaAlmacenamiento();
		ListaMovimientoAlmacen resp = null;
		try 
		{
			//////Transacción			
			this.conn = darConexion();
			daoAlmacen.setConn(conn);
			resp = daoAlmacen.getInfoAlmacenamientos(area1,area2);
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
				daoAlmacen.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return resp;
	}




	public void iniciarRF14(int idB) throws Exception{
		System.out.println("inicia bien");
		ArrayList<Carga> cargas;
		conn = darConexion();
		conn.setAutoCommit(false);
		Savepoint save = conn.setSavepoint();
		try {
			Buque b = getBuque(idB, conn);
			if(b==null) throw new Exception("No existe el buque con id "+idB);

			cargas = darCargasDestinoPuertoAndes(b, conn);

			if(cargas.size()==0) throw new Exception("No hay carga para descargar");
			int libreMio = getAlmacenamientoLibre(cargas.get(0).getTipoCarga());
			System.out.println(libreMio);
			ArrayList<CargaUnificada> cargaU = new ArrayList<>();
			ArrayList<Carga> cargaMia = new ArrayList<>();
			for(Carga actual:cargas){
				if(libreMio-actual.getVolumen()>0){
					System.out.println("carga: "+actual.getID()+" vol: "+actual.getVolumen());
					libreMio-=actual.getVolumen();
					cargaMia.add(actual);
				}else{
					cargaU.add(new CargaUnificada(actual.getPeso(), actual.getVolumen(), actual.getTipoCarga(), actual.getValor()));
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
			conn.close();
		}

	}




	public int getAlmacenamientoLibre(String tipo) throws Exception {
		ListaAlmacenamientos resp = null;
		DAOTablaAlmacenamiento daoAlmacenamiento = new DAOTablaAlmacenamiento();
		try 
		{
			daoAlmacenamiento.setConn(conn);
			resp = daoAlmacenamiento.consultarAlmacenamientos(0, null);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			daoAlmacenamiento.cerrarRecursos();
		}
		int libre = 0;
		DAOTablaCargaEnAlmacen daoCargaAlmacen = new DAOTablaCargaEnAlmacen();
		try {
			daoCargaAlmacen.setConn(conn);
			System.out.println(resp.getBodegas().size());
			for(Bodega b : resp.getBodegas()) {
				double sumaCapacidad = b.getAncho() * b.getLargo();
				List<Carga> cargas = daoCargaAlmacen.darCargasEnAlmacen(b.getID());

				for(Carga carga : cargas) {
					sumaCapacidad -= carga.getVolumen();
				}

				libre+=sumaCapacidad;
			}

			for(Silo b : resp.getSilos()) {
				double sumaCapacidad = b.getCapacidad();
				List<Carga> cargas = daoCargaAlmacen.darCargasEnAlmacen(b.getID());

				for(Carga carga : cargas) {
					sumaCapacidad -= carga.getVolumen();
				}
				libre+=sumaCapacidad;
			}

			for(Cobertizo b : resp.getCobertizos()) {
				double sumaCapacidad = b.getAncho() * b.getLargo();
				List<Carga> cargas = daoCargaAlmacen.darCargasEnAlmacen(b.getID());
				for(Carga carga : cargas) {
					sumaCapacidad -= carga.getVolumen();
				}
				libre+=sumaCapacidad;
			}

			for(Patio b : resp.getPatios()) {
				double sumaCapacidad = b.getAncho() * b.getLargo();
				List<Carga> cargas = daoCargaAlmacen.darCargasEnAlmacen(b.getID());
				for(Carga carga : cargas) {
					sumaCapacidad -= carga.getVolumen();
				}
				libre+=sumaCapacidad;
			}

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			daoCargaAlmacen.cerrarRecursos();
		}

		return libre;
	}




	public void insertarCargas(ArrayList<CargaUnificada> cargas) throws Exception {
		ArrayList<Carga> cargas2 = new ArrayList<>();
		for(CargaUnificada c:cargas){
			Random r = new Random();
			int id=1000000+r.nextInt(1)*10000000;
			Carga car = new Carga(id, "", 0, 1, "", c.getTipo(), c.getVolumen(), c.getPeso(), false, true, c.getValor());
			addCarga(car);
			cargas2.add(car);
		}
		DAOTablaCargaEnAlmacen daoAlmacen = new DAOTablaCargaEnAlmacen();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			for(Carga c:cargas2){
				Almacenamiento m = buscarAlmacenamientoAdecuado(c, conn);
				cargarAreaAlmacenamiento(c, m, conn);
			}
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
			daoAlmacen.cerrarRecursos();
		}
	}

	public void descargarCargaPuertoAndes2(int idB, Date date, ArrayList<Carga> cargaMia) throws Exception{
		
		conn = darConexion();
		conn.setAutoCommit(false);
		Savepoint save = conn.setSavepoint();

		try {
			Buque b = getBuque(idB, conn);

			if(b==null) throw new Exception("No existe el buque con id "+idB);


			if(cargaMia.size()==0) throw new Exception("No hay carga para descargar");
			Almacenamiento a = buscarAlmacenamientoAdecuado(cargaMia.get(0), conn);
			descargarBuque(a, b, date, cargaMia, conn);

			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback(save);
			System.out.println("Se hizo rollback al savepoint");
			throw e;
		} finally {
			conn.close();
		}

	}



	public void actualizarExportador(String rut, int descuento) throws Exception{
		DAOTablaExportador daoExportador = new DAOTablaExportador();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoExportador.setConn(conn);
			daoExportador.aplicarDescuento(rut,descuento);
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
				daoExportador.cerrarRecursos();
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
			descargarCargaPuertoAndes2(idB, new Date(), cargaMia);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
