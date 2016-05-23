package dtm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import javax.sql.DataSource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.UserTransaction;

import dtm.PuertoAndesQueue.Listener2;
import dtm.PuertoAndesQueue.Listener3;

/**
 * A simple JMS Queue example that creates a producer and consumer on a queue and sends then receives a message.
 *
 * @author <a href="ataylor@redhat.com">Andy Taylor</a>
 */
public class PuertoAndesQueue
{

	/**
	 * Datasource fuente de datos
	 */
	private DataSource ds1;
	/**
	 * Conexion para la base de datos 
	 */
	private Connection conn1;
	/**
	 * Contexto inicial
	 */
	private InitialContext context;
	/**
	 * Fabrica de conexiones para el envio de mensajes a la cola
	 */
	private ConnectionFactory cf;
	/**
	 * Fabrica de conexiones para el envio de mensajes al topic
	 */
	private TopicConnectionFactory tcf;

	/**
	 * Conexion a la cola de mensajes
	 */
	private javax.jms.Connection conm;
	/**
	 * Cola definida para recepcion de mensajes
	 */

	private Queue miCola;
	/**
	 * Cola definida para enviar a puerto 1
	 */
	private Queue cola2;
	/**
	 * Cola definida para enviar a puerto 3
	 */
	private Queue cola3;
	/**
	 * Conexion al topico 1
	 */
	private TopicConnection connTopic1;
	/**
	 * Conexion al topico 2
	 */
	private TopicConnection connTopic2;
	/**
	 * Conexion al topico 3
	 */
	private TopicConnection connTopic3;
	/**
	 * Crea una sesion del topic1
	 */
	private TopicSession ts1;
	/**
	 * Crea una sesion del topic2
	 */
	private TopicSession ts2;
	/**
	 * Crea una sesion del topic3
	 */
	private TopicSession ts3;
	/**
	 * Topico
	 */
	private Topic t1;
	/**
	 * Topico
	 */
	private Topic t2;
	/**
	 * Topico
	 */
	private Topic t3;
	/**
	 * Suscribirse a puerto 2
	 */
	private TopicSubscriber topicSubs2;
	/**
	 * Suscribirse a puerto 3
	 */
	private TopicSubscriber topicSubs3;
	/**
	 * yo
	 */
	private TopicPublisher topicPublisher;

	public PuertoAndesQueue(){
		inicializarTopic();
		try {
			subscribe();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void inicializarTopic(){
		inicializarAmbos();
		try{
			tcf = (TopicConnectionFactory) context.lookup("java:/ConnectionFactory");
			connTopic1=tcf.createTopicConnection();
			connTopic2=tcf.createTopicConnection();
			connTopic3=tcf.createTopicConnection();
			t1=(Topic) context.lookup("topic/WebApp1");
			t2=(Topic) context.lookup("topic/WebApp2");
			t3=(Topic) context.lookup("topic/WebApp3");
			ts1 = connTopic1.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
			ts2 = connTopic2.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
			ts3 = connTopic3.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
			connTopic1.start();
			connTopic2.start();
			connTopic3.start();

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void inicializarAmbos(){

		try {
			context = new InitialContext();

			//inicializa la fabrica de conexiones jms
			cf=(ConnectionFactory) context.lookup("java:/ConnectionFactory");

		} catch (NamingException e) {
			System.out.println("Error");
		}

	}

	public void subscribe() throws JMSException{

		topicSubs2 = ts2.createSubscriber(t2);
		topicSubs3 = ts3.createSubscriber(t3);
		topicPublisher = ts1.createPublisher(t1);
		topicSubs2.setMessageListener(new Listener2());
		topicSubs3.setMessageListener(new Listener3());
	}

	public void inicializarContexto(){

		try {

			//inicializa datasource por jndi
			ds1=(DataSource) context.lookup("java:jboss/datasources/XAChie2");
			//accede a la cola de la web app 2
			miCola=(Queue) context.lookup("queue/WebApp1");
			cola2 = (Queue) context.lookup("queue/WebApp2");
			cola3 = (Queue) context.lookup("queue/WebApp3");
			conm = cf.createConnection();
			System.out.println("Contexto inicializado");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void cerrarConexion(){
		try {
			conm.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void transaccionEnviarEjemplo(){
		try{
			UserTransaction utx = (UserTransaction) context.lookup("/UserTransaction");
			inicializarContexto();
			utx.begin();

			//Hacemos cambios sobre bd
			Statement st =  conn1.createStatement();
			String sql = "";
			System.out.println(sql);
			int num = st.executeUpdate(sql);
			System.out.println("PuertoAndes 0206 - Se ingresaron "+num+" filas - conexion1");
			st.close();

			//Inicia sesion utilizando la conexion
			Session session = conm.createSession(false, Session.AUTO_ACKNOWLEDGE);

			//Crea una sesion para producir mensajes hacia la cola que habiamos creado
			MessageProducer producer = session.createProducer(miCola);

			//Existen otros tipos de mensajes.
			//En este caso utilizamos un mensaje simple de texto para enviar la informacion
			TextMessage msg = session.createTextMessage(); String peticion="";
			msg.setText(peticion);
			producer.send(msg);
			System.out.println("PuertoAndes 0206 - Se envi�: "+peticion);

			//Si se envio de forma correcta el mensaje y se realizaron los cambios
			//se hace commit
			utx.commit();
			cerrarConexion();

		}catch(Exception e){

		}
	}
	public void transaccionRecibirEjemplo(){
		try{
			UserTransaction utx = (UserTransaction) context.lookup("/UserTransaction");
			inicializarContexto();
			utx.begin();

			//Inicia sesion utilizando la conexion
			Session session = conm.createSession(false, Session.AUTO_ACKNOWLEDGE);

			//Crea una sesion para producir mensajes hacia la cola que habiamos creado
			MessageConsumer consumer = session.createConsumer(miCola);
			conm.start();

			//Recibimos un mensaje
			System.out.println("PuertoAndes0206 - Esperando mensaje...");
			Message msn = consumer.receive();
			TextMessage txt = (TextMessage)msn;
			String porInsertar = txt.getText();
			System.out.println("PuertoAndes0206 - Recibido..."+porInsertar);

			//Ejecutamos la transaccion en la base de datos
			Statement st = conn1.createStatement();
			String sql = "INESERT INTO PRUEBA VALUES ('"+porInsertar+"')";
			System.out.println(sql);

			int num = st.executeUpdate(sql);
			System.out.println("Se ingresaron "+num+" filas - PuertoAndes0206");
			//Si se envio de forma correcta el mensaje y se realizaron los cambios
			//se hace commit
			utx.commit();
			cerrarConexion();

		}catch(Exception e){

		}
	}

	public void empezarRF14(ArrayList<CargaUnificada> cargas) throws JMSException{
		MensajeCargas mensaje = new MensajeCargas(2, "RF14", cargas);
		ObjectMessage tm = ts2.createObjectMessage(mensaje);
		topicPublisher.publish(tm);
		System.out.println("bien");
		recibirRF14(cargas);

	}

	public void recibirRF14(ArrayList<CargaUnificada> cargas){
		try{
			UserTransaction utx = (UserTransaction) context.lookup("/UserTransaction");
			inicializarContexto();
			utx.begin();

			//Inicia sesion utilizando la conexion
			Session session = conm.createSession(false, Session.AUTO_ACKNOWLEDGE);

			//Crea una sesion para producir mensajes hacia la cola que habiamos creado
			MessageConsumer consumer = session.createConsumer(miCola);
			conm.start();

			//Recibimos un mensaje
			System.out.println("PuertoAndes0206 - Esperando mensaje...");
			Message msn = consumer.receive();
			ObjectMessage txt = (ObjectMessage)msn;
			Mensaje mensaje= (Mensaje)txt.getObject();
			double capacidad1=0,capacidad3=0;
			if(mensaje.getNumPuerto()==1){
				capacidad1=Double.parseDouble(mensaje.getMensaje());
			}else{
				capacidad3=Double.parseDouble(mensaje.getMensaje());
			}
			msn = consumer.receive();
			txt = (ObjectMessage)msn;
			mensaje= (Mensaje)txt.getObject();
			if(mensaje.getNumPuerto()==1){
				capacidad1=Integer.parseInt(mensaje.getMensaje());
			}else{
				capacidad3=Integer.parseInt(mensaje.getMensaje());
			}
			
			ArrayList<CargaUnificada> cargasPara1=new ArrayList<CargaUnificada>();
			ArrayList<CargaUnificada> cargasPara3=new ArrayList<CargaUnificada>();
			boolean sePuede=true;
			for(CargaUnificada c:cargas){
				double espacio = c.getVolumen();
				if(capacidad1-espacio>=0){
					cargasPara1.add(c);
				}else if(capacidad3-espacio>=0){
					cargasPara3.add(c);
				}else{
					sePuede=false;
				}
			}
			if(!sePuede){
				MessageProducer producer = session.createProducer(cola2);
				ObjectMessage message = session.createObjectMessage( new MensajeCargas(1, "CANCEL", null) );
				producer.send(message);
				producer = session.createProducer(cola3);
				message = session.createObjectMessage( new MensajeCargas(1, "CANCEL", null) );
				producer.send(message);
			}else{
				MessageProducer producer = session.createProducer(cola2);
				ObjectMessage message = session.createObjectMessage( new MensajeCargas(1, "CANCEL", cargasPara1) );
				producer.send(message);
				producer = session.createProducer(cola3);
				message = session.createObjectMessage( new MensajeCargas(1, "CANCEL", cargasPara3) );
				producer.send(message);
			}
			
			utx.commit();
			cerrarConexion();

		}catch(Exception e){

		}
	}
	
	public void responderRF14(Queue cola, String tipo){
		System.out.println("Va a responer rf14");
		//TRANSACCION PROPIA: Conseguir espacio libre para segun el tipo
		int libre;
		try {
//			libre = videoMaster.getAlmacenamientoLibre(tipo);

			//fin transaccion propia
			try{
				UserTransaction utx = (UserTransaction) context.lookup("/UserTransaction");
				inicializarContexto();
				utx.begin();

				//Inicia sesion utilizando la conexion
				Session session = conm.createSession(false, Session.AUTO_ACKNOWLEDGE);

				//Crea una sesion para producir mensajes hacia la cola destino
				MessageProducer producer = session.createProducer(cola);
				ObjectMessage message = session.createObjectMessage( new Mensaje(2, "") );
				producer.send(message);
				System.out.println("PuertoAndes 0206 - Se envi�: ");

				//Crea una sesion para recibir mensajes
				MessageConsumer consumer = session.createConsumer(miCola);
				conm.start();

				//Recibimos un mensaje
				System.out.println("PuertoAndes0206 - Esperando mensaje...");
				Message msn = consumer.receive();
				ObjectMessage txt = (ObjectMessage)msn;
				MensajeCargas porInsertar = (MensajeCargas)txt.getObject();
				System.out.println("PuertoAndes0206 - Recibido..."+porInsertar.getMensaje());

				//TRANSACCION PROPIA: insertar cargas en bd
				if(!porInsertar.getMensaje().equals("CANCEL")){
					
				}


				utx.commit();
				cerrarConexion();

			}catch(Exception e){
				e.printStackTrace();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}


	public class Listener2 implements MessageListener {
		public void onMessage(Message msg) {
			try {
				ObjectMessage obj = (ObjectMessage) msg;
				Mensaje msj = (Mensaje) obj.getObject();
				String texto = msj.getMensaje();
				if(texto.startsWith("RF14")){
					responderRF14(cola2, texto.split("-")[1]);
				} else if (texto.contains("RF15P1")) {
					responderRF15(cola2, texto.substring(7));
				} else if (texto.contains("RF15P2")) {
					terminarRF15(texto.substring(7));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public class Listener3 implements MessageListener {
		public void onMessage(Message msg) {
			try {
				ObjectMessage obj = (ObjectMessage) msg;
				Mensaje msj = (Mensaje) obj.getObject();
				String texto = msj.getMensaje();
				if(texto.startsWith("RF14")){
					responderRF14(cola2, texto.split("-")[1]);
				} else if (texto.contains("RF15P1")) {
					responderRF15(cola3, texto.substring(7)); 
				} else if (texto.contains("RF15P2")) {
					terminarRF15(texto.substring(7));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public int empezarRF15(String rut) throws JMSException {
		int descuento = 0;
		Mensaje msj = new Mensaje(3, "RF15P1 " + rut);
		ObjectMessage msg = ts3.createObjectMessage(msj);
		System.out.println("va a publicar RF15P1 - JS");
		topicPublisher.publish(msg);
		System.out.println("publico RF15P1 - JS");
		try {
			UserTransaction utx = (UserTransaction) context.lookup("/UserTransaction");
			inicializarContexto();
			utx.begin();

			// Inicia sesion utilizando la conexion
			Session session = conm.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Crea una sesion para producir mensajes hacia la cola que habiamos
			// creado
			MessageConsumer consumer = session.createConsumer(miCola);
			conm.start();

			// Recibimos LOS mensaje

			int numClientes = 0;

			System.out.println("Esperando 1 mensaje RF15 - JS...");
			Message msn = consumer.receive();
			TextMessage txt = (TextMessage) msn;
			String respuesta1 = txt.getText();
			if (respuesta1.contains("SI"))
				numClientes++;

			System.out.println("Esperando 2 mensaje RF15 - JS...");
			Message msn2 = consumer.receive();
			TextMessage txt2 = (TextMessage) msn2;
			String respuesta2 = txt2.getText();
			if (respuesta2.contains("SI"))
				numClientes++;

			System.out.println("El exportador existe en " + numClientes + " bd - JS");

			switch (numClientes) {
			case 2:
				descuento = 3;
				break;
			case 3:
				descuento = 5;
				break;
			}

			// TIENES QUE ACTUALIZAR TU BASE DE DATOS.
			Statement st = conn1.createStatement();
			String sql = "UPDATE EXPORTADORES SET DESCUENTO = " + descuento + " WHERE RUT = " + rut;
			System.out.println(sql);

			int num = st.executeUpdate(sql);
			System.out.println("Se actualizaron " + num + " filas - PuertoAndes0206");
			// Si se envio de forma correcta el mensaje y se realizaron los
			// cambios
			// se hace commit
			utx.commit();
			cerrarConexion();

		} catch (Exception e) {

		}
		Mensaje msjFinal = new Mensaje(3, "RF15P2 " + rut + " " + descuento);
		ObjectMessage msgFinal = ts3.createObjectMessage(msjFinal);
		System.out.println("va a publicar RF15P2 - JS");
		topicPublisher.publish(msgFinal);
		System.out.println("publico RF15P2 - JS");
		return descuento;
	}

	public void terminarRF15(String rutDescuento) throws JMSException {
		try {
			UserTransaction utx = (UserTransaction) context.lookup("/UserTransaction");
			inicializarContexto();
			utx.begin();

			String[] data = rutDescuento.split(" ");

			// TIENES QUE ACTUALIZAR TU BASE DE DATOS.
			Statement st = conn1.createStatement();
			String sql = "UPDATE EXPORTADORES SET DESCUENTO = " + data[1] + " WHERE RUT = " + data[0];
			System.out.println(sql);
			// *******************************************************

			int num = st.executeUpdate(sql);
			System.out.println("Se actualizaron " + num + " filas - PuertoAndes0206 - JS");

			utx.commit();
			cerrarConexion();
		} catch (Exception e) {
		}
	}

	public void responderRF15(Queue cola, String rut) {
		System.out.println("Va a responer rf15 - JS");
		try {
			UserTransaction utx = (UserTransaction) context.lookup("/UserTransaction");
			inicializarContexto();
			utx.begin();

			// BUSCAMOS EN LA TABLA SI EXISTE EL EXPORTADOR CON RUT PASADO POR PARAMETRO
			Statement st = conn1.createStatement();
			String sql = "SELECT * FROM EXPORTADORES WHERE RUT = " + rut;
			System.out.println(sql + " - JS");
			ResultSet rs = st.executeQuery(sql);
			boolean existe = rs.next();
			st.close();
			// **********************************************************************

			// Inicia sesion utilizando la conexion
			Session session = conm.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Crea una sesion para producir mensajes hacia la cola que habiamos
			// creado
			MessageProducer producer = session.createProducer(cola);

			// Existen otros tipos de mensajes.
			// En este caso utilizamos un mensaje simple de texto para enviar la
			// informacion
			TextMessage msg = session.createTextMessage();
			String peticion = existe ? "SI" : "NO";
			msg.setText(peticion);
			producer.send(msg);
			System.out.println("Se envio " + peticion + " - JS");

			cerrarConexion();

		} catch (Exception e) {

		}
	}
}
