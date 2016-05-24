package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.jms.JMSException;

import dao.DAOTablaAreasAlmacenamiento;
import dao.DAOTablaBuques;
import dao.DAOTablaCargas;
import dao.DAOTablaExportadores;
import dao.DAOTablaFacturas;
import dao.DAOTablaRegistroAlmacenamiento;
import dao.DAOTablaRegistroBuques;
import dao.DAOTablaRegistroCargas;
import dao.DAOTablaRegistroTerminales;
import dao.DAOTablaUsuarios;
import dtm.CargaUnificada;
import dtm.JMSManager;
import vos.AreaAlmacenamiento;
import vos.AreaUnificada;
import vos.Buque;
import vos.Carga;
import vos.ConsultaAreas;
import vos.ExportadorUnificado;
import vos.Factura;
import vos.ListaAreaUnificada;
import vos.ListaConsultaAreas;
import vos.ListaConsultaBuques;
import vos.ListaExportadorCompleto;
import vos.ListaExportadorUnificado;
import vos.ListaMovimientoCargas;
import vos.ListaRegistroBuques;
import vos.MovimientoCarga;
import vos.ParametroBusqueda;
import vos.RegistroAlmacenamiento;
import vos.RegistroBuque;
import vos.RegistroCarga;
import vos.RegistroTerminal;
import vos.Usuario;

public class PuertoAndesMaster {

	/**
	 * Atributo estático que contiene el path relativo del archivo que tiene
	 * los datos de la conexión
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	/**
	 * Atributo estático que contiene el path absoluto del archivo que tiene
	 * los datos de la conexión
	 */
	private String connectionDataPath;

	/**
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base
	 * de datos.
	 */
	private String user;

	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base
	 * de datos.
	 */
	private String password;

	/**
	 * Atributo que guarda el URL que se va a usar para conectarse a la base de
	 * datos.
	 */
	private String url;

	/**
	 * Atributo que guarda el driver que se va a usar para conectarse a la base
	 * de datos.
	 */
	private String driver;

	/**
	 * Conexión a la base de datos
	 */
	private Connection conn;

	private static int auxId;
	
	private JMSManager jms;

	/**
	 * Método constructor de la clase VideoAndesMaster, esta clase modela y
	 * contiene cada una de las transacciones y la logia de negocios que estas
	 * conllevan. <b>post: </b> Se crea el objeto VideoAndesMaster, se
	 * inicializa el path absoluto de el archivo de conexión y se inicializa
	 * los atributos que se usan par la conexión a la base de datos.
	 * 
	 * @param contextPathP
	 *            - path absoluto en el servidor del contexto del deploy actual
	 */
	public PuertoAndesMaster(String contextPathP) {
		auxId = 5000;
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
		
		jms = new JMSManager(this);
		jms.inicializarContexto();
		System.out.println("Funciona");
	}

	/*
	 * Método que inicializa los atributos que se usan para la conexion a la
	 * base de datos. <b>post: </b> Se han inicializado los atributos que se
	 * usan par la conexión a la base de datos.
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
	 * Método que retorna la conexión a la base de datos
	 * 
	 * @return Connection - la conexión a la base de datos
	 * @throws SQLException
	 *             - Cualquier error que se genere durante la conexión a la
	 *             base de datos
	 */
	private Connection darConexion() throws SQLException {
		System.out.println("Connecting to: " + url + " With user: " + user);
		return DriverManager.getConnection(url, user, password);
	}

	// TRANSACCIONES
	public ListaExportadorCompleto consultarExportador(int idExportador, ParametroBusqueda pb) throws SQLException {
		DAOTablaBuques daoExportadores = new DAOTablaBuques();
		try {
			this.conn = darConexion();
			conn.setAutoCommit(true);

			daoExportadores.setConn(conn);
			return null;
			// return daoExportadores.consultarExportador( pb,idExportador);

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

	public ListaConsultaBuques consultarIOBuques(ParametroBusqueda pb) throws SQLException {
		DAOTablaBuques daoBuques = new DAOTablaBuques();
		try {
			this.conn = darConexion();
			conn.setAutoCommit(true);

			daoBuques.setConn(conn);
			return daoBuques.consultarIOBuques(pb);

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
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void generarFactura(int idUsuario, int idCliente, int idBuque, String fecha) throws Exception {
		DAOTablaFacturas daoFacturas = new DAOTablaFacturas();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try {
			this.conn = darConexion();
			conn.setAutoCommit(false);

			daoUsuarios.setConn(conn);
			Usuario usuario = daoUsuarios.buscarUsuarioPorId(idUsuario);
			if (!usuario.getTipo().equals("OPERADOR") && !usuario.getTipo().equals("ADMINISTRADOR"))
				throw new Exception("No se tienen los privilegios para realizar esta acci�n.");

			daoFacturas.setConn(conn);
			Factura f = daoFacturas.generarFactura(idCliente, idBuque, fecha);
			daoFacturas.addFactura(f);

			conn.commit();
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
				daoUsuarios.cerrarRecursos();
				daoFacturas.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void registrarEntregaCarga(Carga carga, int idUsuario) throws Exception {
		DAOTablaCargas daoCargas = new DAOTablaCargas();
		DAOTablaRegistroCargas daoRegistroCargas = new DAOTablaRegistroCargas();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try {
			this.conn = darConexion();
			conn.setAutoCommit(false);

			daoUsuarios.setConn(conn);
			Usuario usuario = daoUsuarios.buscarUsuarioPorId(idUsuario);
			if (!usuario.getTipo().equals("OPERADOR") && !usuario.getTipo().equals("ADMINISTRADOR"))
				throw new Exception("No se tienen los privilegios para realizar esta acci�n.");

			daoCargas.setConn(conn);
			Carga cargaDB = daoCargas.buscarCargaPorId(carga.getId());
			if (!cargaDB.getTipo().equals(carga.getTipo())
					|| cargaDB.getId_almacenamiento() != carga.getId_almacenamiento()
					|| cargaDB.getVolumen() != carga.getVolumen())
				throw new Exception("Los datos provistos de la carga no corresponden a ninguna carga del sistema");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String date = sdf.format(new Date());
			RegistroCarga nuevoReg = new RegistroCarga(auxId++, carga.getId(), date, "ENTREGADO");
			daoRegistroCargas.setConn(conn);
			daoRegistroCargas.addRegistroCarga(nuevoReg);
			conn.commit();
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
				daoUsuarios.cerrarRecursos();
				daoCargas.cerrarRecursos();
				daoRegistroCargas.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public ArrayList<Carga> deshabilitarAreaMant(String tipo, AreaAlmacenamiento a) throws Exception {
		DAOTablaBuques daoBuques = new DAOTablaBuques();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		DAOTablaCargas daoCargas = new DAOTablaCargas();
		DAOTablaAreasAlmacenamiento daoAA = new DAOTablaAreasAlmacenamiento();
		ArrayList<Carga> cargasHuerfanas = new ArrayList<Carga>();
		try {
			this.conn = darConexion();
			conn.setAutoCommit(false);

			daoUsuarios.setConn(conn);
			daoBuques.setConn(conn);
			daoAA.setConn(conn);
			daoCargas.setConn(conn);

			Usuario usuario = daoUsuarios.buscarUsuarioPorId(a.getUser_id());
			if (!usuario.getTipo().equals("OPERADOR") && !usuario.getTipo().equals("ADMINISTRADOR"))
				throw new Exception("No se tienen los privilegios para realizar esta acci�n.");

			AreaAlmacenamiento aa = daoAA.buscarAreaPorId(a.getId());

			aa.setEstado(tipo);
			daoAA.updateArea(aa);

			if (tipo.equals("INCAUTADO")) {
				conn.commit();
				return cargasHuerfanas;
			}

			Buque buque = daoBuques.buscarBuquePorId(0);

			ArrayList<Carga> listaCarga = (ArrayList<Carga>) daoCargas.cargasDelArea(a.getId());

			for (Carga c : listaCarga) {
				Carga temp = new Carga(c.getId(), c.getTipo(), c.getPeso(), c.getVolumen(), 0, c.getId_camion(), -1,
						c.getId_cliente(), c.getPuerto_origen(), "Puerto Andes", "CARGADO");
				daoCargas.updateCarga(temp);
			}

			cargasHuerfanas = deshabilitarBuque2(tipo, buque);

			for (Carga c : listaCarga) {
				Carga temp = daoCargas.buscarCargaPorId(c.getId());
				temp.setPuerto_destino(c.getPuerto_destino());
				temp.setPuerto_origen(c.getPuerto_origen());
				temp.setEstado("ALMACENADO");
				temp.setId_buque((temp.getId_buque() == 0 ? c.getId_buque() : temp.getId_buque()));
				daoCargas.updateCarga(temp);
			}

			buque.setEstado("ATRACADO");
			daoBuques.updateBuque(buque);
			conn.commit();
			return cargasHuerfanas;
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
				daoUsuarios.cerrarRecursos();
				daoBuques.cerrarRecursos();
				daoCargas.cerrarRecursos();
				daoAA.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
			return cargasHuerfanas;
		}
	}

	@SuppressWarnings("finally")
	public ArrayList<Carga> deshabilitarBuque2(String tipo, Buque b) throws Exception {
		DAOTablaBuques daoBuques = new DAOTablaBuques();
		DAOTablaCargas daoCargas = new DAOTablaCargas();
		ArrayList<Carga> cargasHuerfanas = new ArrayList<Carga>();
		try {

			// SE ACTUALIZA EL ESTADO DEL BUQUE.
			daoBuques.setConn(conn);
			Buque buque = daoBuques.buscarBuquePorId(b.getId());

			buque.setEstado(tipo);

			if (tipo.equals("INCAUTADO")) {
				daoBuques.updateBuque(buque);
				return cargasHuerfanas;
			}

			daoCargas.setConn(conn);
			ArrayList<Carga> listaCarga = (ArrayList<Carga>) daoCargas.cargasDelBuque(b.getId());

			RegistroBuque rb = new RegistroBuque(2, -1, "", "", "", "", "", "", "Puerto Andes", "", "", 0,
					buque.getTipo());

			descargarBuque2(rb);

			// Las cargas cuyo ID DE ALMAC buque sigan estando en null fueron
			// las que no se pudieron reubicar en areas de almacenamiento.
			ArrayList<Buque> bDisponibles = new ArrayList<>();
			if (!listaCarga.isEmpty()) {
				bDisponibles = (ArrayList<Buque>) daoBuques.getBuquesDisponibles(buque.getTipo(),
						listaCarga.get(0).getPuerto_destino());
			}
			for (Carga c : listaCarga) {
				Carga temp = daoCargas.buscarCargaPorId(c.getId());
				temp.setId_buque(-1);
				daoCargas.updateCarga(temp);
				if (temp.getId_almacenamiento() == -1) {
					for (Buque bb : bDisponibles) {
						if (bb.getId() == b.getId() || bb.getId() == 0)
							continue;
						if (bb.getCapacidad() > temp.getPeso()) {
							temp.setId_buque(bb.getId());
							daoCargas.updateCarga(temp);
							conn.setSavepoint();
						}
					}
					if (temp.getId_buque() == -1)
						cargasHuerfanas.add(temp);
				}
			}

			daoBuques.updateBuque(buque);
			return cargasHuerfanas;
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			daoBuques.cerrarRecursos();
			daoCargas.cerrarRecursos();
			return cargasHuerfanas;
		}
	}
	
	public ListaConsultaAreas consultarAreas(int idUsuario, ParametroBusqueda pb) throws Exception{
		DAOTablaCargas daoCargas = new DAOTablaCargas();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try {
			this.conn = darConexion();
			conn.setAutoCommit(true);
			
			daoUsuarios.setConn(conn);
			Usuario usuario = daoUsuarios.buscarUsuarioPorId(idUsuario);
			if (!usuario.getTipo().equals("ADMINISTRADOR"))
				throw new Exception("No se tienen los privilegios para realizar esta acci�n.");
			
			daoCargas.setConn(conn);
			return daoCargas.consultarAreas(pb);
			
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
				daoCargas.cerrarRecursos();
				daoUsuarios.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public ListaMovimientoCargas consultarMovimientoCargas(int idUsuario, ParametroBusqueda pb) throws Exception{
		DAOTablaCargas daoCargas = new DAOTablaCargas();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try {
			this.conn = darConexion();
			conn.setAutoCommit(true);
			
			daoUsuarios.setConn(conn);
			Usuario usuario = daoUsuarios.buscarUsuarioPorId(idUsuario);
			if (!usuario.getTipo().equals("CLIENTE") && !usuario.getTipo().equals("ADMINISTRADOR"))
				throw new Exception("No se tienen los privilegios para realizar esta acci�n.");
			
			daoCargas.setConn(conn);
			if(usuario.getTipo().equals("CLIENTE"))
				return daoCargas.consultarMovimientoCargas(pb, idUsuario);
			else if(usuario.getTipo().equals("ADMINISTRADOR"))
				return daoCargas.consultarMovimientoCargas(pb);
			else
				return null;
			
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
				daoCargas.cerrarRecursos();
				daoUsuarios.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public ArrayList<Carga> deshabilitarBuque(String tipo, Buque b) throws Exception {
		DAOTablaBuques daoBuques = new DAOTablaBuques();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		DAOTablaCargas daoCargas = new DAOTablaCargas();
		ArrayList<Carga> cargasHuerfanas = new ArrayList<Carga>();
		try {
			this.conn = darConexion();
			conn.setAutoCommit(false);

			daoUsuarios.setConn(conn);
			Usuario usuario = daoUsuarios.buscarUsuarioPorId(b.getUser_id());
			if (!usuario.getTipo().equals("OPERADOR") && !usuario.getTipo().equals("ADMINISTRADOR"))
				throw new Exception("No se tienen los privilegios para realizar esta acci�n.");

			// SE ACTUALIZA EL ESTADO DEL BUQUE.
			daoBuques.setConn(conn);
			Buque buque = daoBuques.buscarBuquePorId(b.getId());

			buque.setEstado(tipo);

			if (tipo.equals("INCAUTADO")) {
				daoBuques.updateBuque(buque);
				conn.commit();
				return cargasHuerfanas;
			}

			daoCargas.setConn(conn);
			ArrayList<Carga> listaCarga = (ArrayList<Carga>) daoCargas.cargasDelBuque(b.getId());

			// Forza el destino para puerto andes para poder descargar el buque.
			for (Carga c : listaCarga) {
				Carga temp = new Carga(c.getId(), c.getTipo(), c.getPeso(), c.getVolumen(), 0, c.getId_camion(), -1,
						c.getId_cliente(), c.getPuerto_origen(), "Puerto Andes", "CARGADO");
				daoCargas.updateCarga(temp);
			}

			RegistroBuque rb = new RegistroBuque(2, -1, "", "", "", "", "", "", "Puerto Andes", "", "", 0,
					buque.getTipo());

			descargarBuque2(rb);

			// Las cargas cuyo ID DE ALMAC buque sigan estando en null fueron
			// las que no se pudieron reubicar en areas de almacenamiento.
			ArrayList<Buque> bDisponibles = new ArrayList<>();
			if (!listaCarga.isEmpty()) {
				bDisponibles = (ArrayList<Buque>) daoBuques.getBuquesDisponibles(buque.getTipo(),
						listaCarga.get(0).getPuerto_destino());
			}
			for (Carga c : listaCarga) {
				Carga temp = daoCargas.buscarCargaPorId(c.getId());
				temp.setId_buque(-1);
				daoCargas.updateCarga(temp);
				if (temp.getId_almacenamiento() == -1) {
					for (Buque bb : bDisponibles) {
						if (bb.getId() == b.getId())
							continue;
						if (bb.getCapacidad() > temp.getPeso()) {
							temp.setId_buque(bb.getId());
							daoCargas.updateCarga(temp);
						}
					}
					if (temp.getId_buque() == -1)
						cargasHuerfanas.add(temp);
				}
			}

			daoBuques.updateBuque(buque);
			conn.commit();
			return cargasHuerfanas;
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
				daoUsuarios.cerrarRecursos();
				daoBuques.cerrarRecursos();
				daoCargas.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
			return cargasHuerfanas;
		}
	}

	public void descargarBuque2(RegistroBuque rb) throws Exception {
		DAOTablaCargas daoCargas = new DAOTablaCargas();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		DAOTablaBuques daoBuques = new DAOTablaBuques();
		DAOTablaAreasAlmacenamiento daoAA = new DAOTablaAreasAlmacenamiento();
		DAOTablaRegistroCargas daoRegistroCargas = new DAOTablaRegistroCargas();
		DAOTablaRegistroAlmacenamiento daoRA = new DAOTablaRegistroAlmacenamiento();
		try {

			// busco todas las cargas que van en ese buque con destino puerto
			// andes: id_buque y id_almacenamiento es null
			// y destino es puerto andes.
			daoCargas.setConn(conn);
			ArrayList<Carga> listaCargas = (ArrayList<Carga>) daoCargas.cargasSinDescargar(rb.getId_buque(),
					"Puerto Andes");
			System.out.println("HAY " + listaCargas.size() + " CARGAS PARA DESCARGAR.");
			double capacidadNecesitada = 0;
			for (Carga c : listaCargas)
				capacidadNecesitada += c.getVolumen();

			// busco areas de almacenamiento que tengan el tipo igual al tipo de
			// la carga y la capacidad.
			daoAA.setConn(conn);
			ArrayList<AreaAlmacenamiento> list = (ArrayList<AreaAlmacenamiento>) daoAA
					.buscarAreaParaCargas(rb.getTipo_carga(), capacidadNecesitada);

			// Busco un area de mantenimiento que
			// no est� reservada en la fecha que se va a descargar.
			daoRA.setConn(conn);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String date = sdf.format(new Date());
			AreaAlmacenamiento aaEscogida = null;
			System.out.println("Hay " + list.size() + " Area de almacenamiento para verificar");
			for (AreaAlmacenamiento aa : list) {
				if (!daoRA.existeRegistroReserva(aa.getId(), date)
						&& !(aa.getEstado() != null && aa.getEstado().equals("RESERVADA"))) {
					aaEscogida = aa;
					break;
				}
			}

			if (aaEscogida == null)
				return;

			// Se reserva el area de almacenamiento. Se cambio el estado del
			// area a reservada y se crea un registro.
			aaEscogida.setEstado("RESERVADA");
			daoAA.updateArea(aaEscogida);
			daoRA.addRegistro(new RegistroAlmacenamiento(auxId++ + 100, date, "'RESERVA'", aaEscogida.getId()));

			// busca el buque que va a ser cargado, lo pone en proceso de
			// descarga si es posible.
			daoBuques.setConn(conn);
			Buque buque = daoBuques.buscarBuquePorId(rb.getId_buque());
			if (!buque.getEstado().equals("ATRACADO"))
				throw new Exception("El buque no puede ser descargado. No est� en el puerto");

			buque.setEstado("PROCESO DE DESCARGUE");
			daoBuques.updateBuque(buque);

			daoRegistroCargas.setConn(conn);
			Iterator<Carga> i = listaCargas.iterator();

			double capacidadBuque = buque.getCapacidad();
			aaEscogida.setDimension(aaEscogida.getDimension() - capacidadNecesitada);

			while (i.hasNext()) {
				System.out.println("entro al loop");
				Carga c = i.next();

				capacidadBuque += c.getPeso();
				c.setId_almacenamiento(aaEscogida.getId());
				descargarCarga(c, daoCargas, daoRegistroCargas);
				conn.setSavepoint();
			}
			daoAA.updateArea(aaEscogida);
			// actualizo el buque.
			buque.setCapacidad(capacidadBuque);
			buque.setEstado("ATRACADO");
			daoBuques.updateBuque(buque);

			aaEscogida.setEstado("DISPONIBLE");
			daoAA.updateArea(aaEscogida);

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
			daoCargas.cerrarRecursos();
			daoUsuarios.cerrarRecursos();
			daoBuques.cerrarRecursos();
			daoAA.cerrarRecursos();
			daoRA.cerrarRecursos();
			daoRegistroCargas.cerrarRecursos();
		}
	}

	public void descargarBuque(RegistroBuque rb) throws Exception {
		DAOTablaCargas daoCargas = new DAOTablaCargas();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		DAOTablaBuques daoBuques = new DAOTablaBuques();
		DAOTablaAreasAlmacenamiento daoAA = new DAOTablaAreasAlmacenamiento();
		DAOTablaRegistroCargas daoRegistroCargas = new DAOTablaRegistroCargas();
		DAOTablaRegistroAlmacenamiento daoRA = new DAOTablaRegistroAlmacenamiento();
		try {
			this.conn = darConexion();
			conn.setAutoCommit(false);

			daoUsuarios.setConn(conn);
			Usuario usuario = daoUsuarios.buscarUsuarioPorId(rb.getId_usuario());
			if (!usuario.getTipo().equals("OPERADOR") && !usuario.getTipo().equals("ADMINISTRADOR"))
				throw new Exception("No se tienen los privilegios para realizar esta acci�n.");

			// busco todas las cargas que van en ese buque con destino puerto
			// andes: id_buque y id_almacenamiento es null
			// y destino es puerto andes.
			daoCargas.setConn(conn);
			ArrayList<Carga> listaCargas = (ArrayList<Carga>) daoCargas.cargasSinDescargar(rb.getId_buque(),
					"Puerto Andes");

			double capacidadNecesitada = 0;
			for (Carga c : listaCargas)
				capacidadNecesitada += c.getVolumen();

			// busco areas de almacenamiento que tengan el tipo igual al tipo de
			// la carga y la capacidad.
			daoAA.setConn(conn);
			ArrayList<AreaAlmacenamiento> list = (ArrayList<AreaAlmacenamiento>) daoAA
					.buscarAreaParaCargas(rb.getTipo_carga(), capacidadNecesitada);

			// Busco un area de mantenimiento que
			// no est� reservada en la fecha que se va a descargar.
			daoRA.setConn(conn);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String date = sdf.format(new Date());
			AreaAlmacenamiento aaEscogida = null;
			System.out.println("Hay " + list.size() + " Area de almacenamiento para verificar");
			for (AreaAlmacenamiento aa : list) {
				if (!daoRA.existeRegistroReserva(aa.getId(), date)
						&& !(aa.getEstado() != null && aa.getEstado().equals("RESERVADA"))) {
					aaEscogida = aa;
					break;
				}
			}

			if (aaEscogida == null)
				throw new Exception("No existe un area de almacenamiento capaz de guardar las cargas.");

			// Se reserva el area de almacenamiento. Se cambio el estado del
			// area a reservada y se crea un registro.
			aaEscogida.setEstado("RESERVADA");
			daoAA.updateArea(aaEscogida);
			daoRA.addRegistro(new RegistroAlmacenamiento(auxId++ + 100, date, "'RESERVA'", aaEscogida.getId()));

			// busca el buque que va a ser cargado, lo pone en proceso de
			// descarga si es posible.
			daoBuques.setConn(conn);
			Buque buque = daoBuques.buscarBuquePorId(rb.getId_buque());
			if (!buque.getEstado().equals("ATRACADO"))
				throw new Exception("El buque no puede ser descargado. No est� en el puerto");

			buque.setEstado("PROCESO DE DESCARGUE");
			daoBuques.updateBuque(buque);

			daoRegistroCargas.setConn(conn);
			Iterator<Carga> i = listaCargas.iterator();

			double capacidadBuque = buque.getCapacidad();
			aaEscogida.setDimension(aaEscogida.getDimension() - capacidadNecesitada);

			while (i.hasNext()) {
				System.out.println("entro al loop");
				Carga c = i.next();

				capacidadBuque += c.getPeso();
				c.setId_almacenamiento(aaEscogida.getId());
				descargarCarga(c, daoCargas, daoRegistroCargas);
			}
			daoAA.updateArea(aaEscogida);
			// actualizo el buque.
			buque.setCapacidad(capacidadBuque);
			buque.setEstado("ATRACADO");
			daoBuques.updateBuque(buque);

			aaEscogida.setEstado("DISPONIBLE");
			daoAA.updateArea(aaEscogida);

			conn.commit();
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
				daoCargas.cerrarRecursos();
				daoUsuarios.cerrarRecursos();
				daoBuques.cerrarRecursos();
				daoAA.cerrarRecursos();
				daoRA.cerrarRecursos();
				daoRegistroCargas.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public void iniciarRF14(RegistroBuque rb) throws Exception {
		DAOTablaBuques daoBuques = new DAOTablaBuques();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		DAOTablaCargas daoCargas = new DAOTablaCargas();
		ArrayList<Carga> cargasHuerfanas = new ArrayList<Carga>();
		try {
			this.conn = darConexion();
			conn.setAutoCommit(false);

			daoUsuarios.setConn(conn);
			Usuario usuario = daoUsuarios.buscarUsuarioPorId(rb.getId_usuario());
			if (!usuario.getTipo().equals("OPERADOR") && !usuario.getTipo().equals("ADMINISTRADOR"))
				throw new Exception("No se tienen los privilegios para realizar esta acci�n.");

			// SE ACTUALIZA EL ESTADO DEL BUQUE.
			daoBuques.setConn(conn);
			Buque buque = daoBuques.buscarBuquePorId(rb.getId_buque());

			daoCargas.setConn(conn);
			ArrayList<Carga> listaCarga = (ArrayList<Carga>) daoCargas.cargasDelBuque(buque.getId());

			// Forza el destino para puerto andes para poder descargar el buque.
			for (Carga c : listaCarga) {
				Carga temp = new Carga(c.getId(), c.getTipo(), c.getPeso(), c.getVolumen(), 0, c.getId_camion(), -1,
						c.getId_cliente(), c.getPuerto_origen(), "Puerto Andes", "CARGADO");
				daoCargas.updateCarga(temp);
			}

			RegistroBuque rb1 = new RegistroBuque(2, -1, "", "", "", "", "", "", "Puerto Andes", "", "", 0,
					buque.getTipo());

			descargarBuque2(rb1);

			// Las cargas cuyo ID DE ALMAC buque sigan estando en null fueron
			// las que no se pudieron reubicar en areas de almacenamiento.
			ArrayList<Buque> bDisponibles = new ArrayList<>();
			if (!listaCarga.isEmpty()) {
				bDisponibles = (ArrayList<Buque>) daoBuques.getBuquesDisponibles(buque.getTipo(),
						listaCarga.get(0).getPuerto_destino());
			}
			for (Carga c : listaCarga) {
				Carga temp = daoCargas.buscarCargaPorId(c.getId());
				temp.setId_buque(-1);
				daoCargas.updateCarga(temp);
				if (temp.getId_almacenamiento() == -1) {
					for (Buque bb : bDisponibles) {
						if (bb.getId() == buque.getId())
							continue;
						if (bb.getCapacidad() > temp.getPeso()) {
							temp.setId_buque(bb.getId());
							daoCargas.updateCarga(temp);
						}
					}
					if (temp.getId_buque() == -1)
						cargasHuerfanas.add(temp);
				}
			}
			if(!cargasHuerfanas.isEmpty()){
				jms.empezarRF14(estandarizarCargas(cargasHuerfanas),null,rb.getId_buque());
			}
			
			conn.commit();
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
				daoUsuarios.cerrarRecursos();
				daoBuques.cerrarRecursos();
				daoCargas.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public ListaAreaUnificada rfc11(int idUsuario, ParametroBusqueda pb) throws Exception{
		ArrayList<AreaUnificada> cu = new ArrayList<>();

		cu.addAll(jms.empezarRFC11().getAreas());
		
		ListaConsultaAreas lsa = consultarAreas(idUsuario, pb);

		for(ConsultaAreas ca : lsa.getAreas()){
			cu.add(new AreaUnificada(ca.getEstado_area(), ca.getTipo_area()));
		}
		
		return new ListaAreaUnificada(cu);
	}
	
	public ListaExportadorUnificado consultarCostos(ParametroBusqueda pb) throws Exception{
		DAOTablaExportadores daoExportadores = new DAOTablaExportadores();
		ArrayList <ExportadorUnificado> ex = new ArrayList<>();
		try {
			this.conn = darConexion();

			daoExportadores.setConn(conn);
			
			String rango = "";
			for(String s : pb.getWhere()){
				rango += s;
			}
						
			ex.addAll(daoExportadores.costoExportadores(rango));
						
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
	
	public ListaExportadorUnificado rfc12(ParametroBusqueda pb) throws Exception{
		DAOTablaExportadores daoExportadores = new DAOTablaExportadores();
		ArrayList <ExportadorUnificado> ex = new ArrayList<>();
		try {
			this.conn = darConexion();

			daoExportadores.setConn(conn);
			
			String rango = "";
			for(String s : pb.getWhere()){
				rango += s + " ";
			}
			
			ex.addAll(jms.empezarRFC12(rango));
			
			ex.addAll(daoExportadores.costoExportadores(rango));
						
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
	
	public void actualizarExportador(String rut, int descuento) throws SQLException{
		DAOTablaExportadores daoExportadores = new DAOTablaExportadores();
		try {
			this.conn = darConexion();

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
	
	public boolean encontrarExportador(String rut) throws SQLException{
		DAOTablaExportadores daoExportadores = new DAOTablaExportadores();
		boolean existe = false;
		try {
			this.conn = darConexion();
			conn.setAutoCommit(false);

			daoExportadores.setConn(conn);
			
			existe = daoExportadores.existeExportador(rut);
			
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
		return existe;
	}
	
	public int consultarBono(String rut) throws Exception{
		return jms.empezarRF15(rut);
	}
	
	private ArrayList<CargaUnificada> estandarizarCargas(ArrayList<Carga> cargas){
		ArrayList<CargaUnificada> cargasUnificadas = new ArrayList<>();
		for(Carga c : cargas){
			cargasUnificadas.add(new CargaUnificada(c.getPeso(),c.getVolumen(), c.getTipo(), 1000));
		}
		return cargasUnificadas;
	}

	public void cargarBuque(RegistroBuque rb) throws Exception {
		DAOTablaCargas daoCargas = new DAOTablaCargas();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		DAOTablaBuques daoBuques = new DAOTablaBuques();
		DAOTablaRegistroBuques daoRegistroBuques = new DAOTablaRegistroBuques();
		DAOTablaAreasAlmacenamiento daoAA = new DAOTablaAreasAlmacenamiento();
		DAOTablaRegistroCargas daoRegistroCargas = new DAOTablaRegistroCargas();
		try {
			this.conn = darConexion();
			conn.setAutoCommit(false);

			daoUsuarios.setConn(conn);
			Usuario usuario = daoUsuarios.buscarUsuarioPorId(rb.getId_usuario());
			if (!usuario.getTipo().equals("OPERADOR") && !usuario.getTipo().equals("ADMINISTRADOR"))
				throw new Exception("No se tienen los privilegios para realizar esta acci�n.");

			// busca el buque que va a ser cargado, lo pone en proceso de carga
			// si es posible.
			daoBuques.setConn(conn);
			Buque buque = daoBuques.buscarBuquePorId(rb.getId_buque());
			if (!buque.getEstado().equals("ATRACADO") || buque.getCapacidad() == 0)
				throw new Exception("El buque no puede ser cargado.");

			buque.setEstado("PROCESO DE CARGA");
			daoBuques.updateBuque(buque);

			// busca todas las cargas que compartan destino con el buque y tipo
			// y no hayan sido cargadas.
			daoCargas.setConn(conn);
			ArrayList<Carga> cargas = (ArrayList<Carga>) daoCargas.cargasSinCargar(rb.getTipo_carga(),
					rb.getPuerto_arribo());

			daoAA.setConn(conn);
			daoRegistroCargas.setConn(conn);
			Iterator<Carga> i = cargas.iterator();
			double capacidad = buque.getCapacidad();
			while (capacidad > 0 && i.hasNext()) {
				System.out.println("entro al loop");
				Carga c = i.next();
				if (capacidad > c.getPeso()) {
					System.out.println("se agreg�");
					c.setId_buque(rb.getId_buque());
					addTipoCarga(c, daoCargas, daoRegistroCargas);
					capacidad -= c.getPeso();

					// cambios en el area de almacenamiento donde estaba la
					// carga
					AreaAlmacenamiento aa = daoAA.buscarAreaPorId(c.getId_almacenamiento());
					aa.setDimension(aa.getDimension() - c.getVolumen());
					daoAA.updateArea(aa);
				}
			}
			System.out.println("LA CAPACIDAD DEL BUQUE NUEVA ES: " + capacidad);
			// actualizo el buque.
			buque.setCapacidad(capacidad);
			buque.setEstado("CARGADO");
			daoBuques.updateBuque(buque);

			// agrego registro de buque.
			daoRegistroBuques.setConn(conn);
			daoRegistroBuques.addRegistroBuque(rb);

			conn.commit();
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
				daoCargas.cerrarRecursos();
				daoUsuarios.cerrarRecursos();
				daoBuques.cerrarRecursos();
				daoRegistroBuques.cerrarRecursos();
				daoAA.cerrarRecursos();
				daoRegistroCargas.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addTipoCarga(Carga carga, DAOTablaCargas daoCargas, DAOTablaRegistroCargas daoRegistroCargas)
			throws Exception {
		daoCargas.updateCarga(carga);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String date = sdf.format(new Date());
		RegistroCarga nuevoReg = new RegistroCarga(auxId++ + 1, carga.getId(), date, "CARGADO");

		daoRegistroCargas.addRegistroCarga(nuevoReg);
	}

	public void descargarCarga(Carga carga, DAOTablaCargas daoCargas, DAOTablaRegistroCargas daoRegistroCargas)
			throws Exception {
		daoCargas.updateCarga(carga);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String date = sdf.format(new Date());
		RegistroCarga nuevoReg = new RegistroCarga(auxId++ + 100, carga.getId(), date, "ALMACENADO");

		daoRegistroCargas.addRegistroCarga(nuevoReg);
	}

	public void addTipoCarga(Carga carga, int idUsuario) throws Exception {
		DAOTablaCargas daoCargas = new DAOTablaCargas();
		DAOTablaRegistroCargas daoRegistroCargas = new DAOTablaRegistroCargas();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try {
			this.conn = darConexion();
			conn.setAutoCommit(false);

			daoUsuarios.setConn(conn);
			Usuario usuario = daoUsuarios.buscarUsuarioPorId(idUsuario);
			if (!usuario.getTipo().equals("OPERADOR") && !usuario.getTipo().equals("ADMINISTRADOR"))
				throw new Exception("No se tienen los privilegios para realizar esta acci�n.");

			daoCargas.setConn(conn);
			daoCargas.updateCarga(carga);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String date = sdf.format(new Date());
			RegistroCarga nuevoReg = new RegistroCarga(auxId++ + 1, carga.getId(), date, "CARGADO");

			daoRegistroCargas.setConn(conn);
			daoRegistroCargas.addRegistroCarga(nuevoReg);
			conn.commit();
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
				daoCargas.cerrarRecursos();
				daoRegistroCargas.cerrarRecursos();
				daoUsuarios.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public Buque getBuque(int idBuque) throws SQLException {
		DAOTablaBuques daoBuques = new DAOTablaBuques();
		try {
			this.conn = darConexion();
			conn.setAutoCommit(true);
			daoBuques.setConn(conn);
			Buque b = daoBuques.buscarBuquePorId(idBuque);
			return b;
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
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void registrarSalidaBuque(int idBuque, int idUsuario) throws Exception {
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		DAOTablaBuques daoBuques = new DAOTablaBuques();
		DAOTablaRegistroTerminales daoRegistroTerminales = new DAOTablaRegistroTerminales();
		try {
			////// Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);

			daoUsuarios.setConn(conn);
			Usuario usuario = daoUsuarios.buscarUsuarioPorId(idUsuario);
			if (!usuario.getTipo().equals("AGENTE") && !usuario.getTipo().equals("ADMINISTRADOR"))
				throw new Exception("El usuario no tiene los privilegios para realizar esta operacion.");

			daoBuques.setConn(conn);
			Buque b = daoBuques.buscarBuquePorId(idBuque);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String date = sdf.format(new Date());
			RegistroTerminal rt = new RegistroTerminal(auxId++, date, b.getId(), b.getIdTerminal(), "SALIDA");
			daoBuques.registrarSalidaBuque(b); // Cambia el id del terminal del
												// buque a null si ten�a una
												// terminal.

			daoRegistroTerminales.setConn(conn);
			daoRegistroTerminales.addRegistroTerminal(rt);
			conn.commit();
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
				daoBuques.cerrarRecursos();
				daoRegistroTerminales.cerrarRecursos();
				daoUsuarios.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

}
