/*
 * Copyright 2009 Red Hat, Inc.
 * Red Hat licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package dtm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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

import tm.PuertoAndesMaster;
import vos.ConsultaAreas;
import vos.ListaAreaUnificada;
import vos.ListaConsultaAreas;
import vos.ParametroBusqueda;

/**
 * A simple JMS Queue example that creates a producer and consumer on a queue
 * and sends then receives a message.
 *
 * @author <a href="ataylor@redhat.com">Andy Taylor</a>
 */
public class JMSManager {
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
	private Queue cola1;
	/**
	 * Cola definida para enviar a puerto 2
	 */
	private Queue cola2;
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
	 * Suscribirse a puerto 1
	 */
	private TopicSubscriber topicSubs1;
	/**
	 * Suscribirse a puerto 2
	 */
	private TopicSubscriber topicSubs2;
	/**
	 * yo
	 */
	private TopicPublisher topicPublisher;

	private PuertoAndesMaster master;

	public JMSManager(PuertoAndesMaster master) {
		this.master = master;
		inicializarTopic();
	}

	public void inicializarTopic() {
		inicializarAmbos();
		try {
			TopicConnectionFactory tcf = (TopicConnectionFactory) cf;
			connTopic1 = tcf.createTopicConnection();
			connTopic2 = tcf.createTopicConnection();
			connTopic3 = tcf.createTopicConnection();
			t1 = (Topic) context.lookup("topic/WebApp1");
			t2 = (Topic) context.lookup("topic/WebApp2");
			t3 = (Topic) context.lookup("topic/WebApp3");
			ts1 = connTopic1.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
			ts2 = connTopic2.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
			ts3 = connTopic3.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
			subscribe();
			connTopic1.start();
			connTopic2.start();
			connTopic3.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void inicializarAmbos() {

		try {
			context = new InitialContext();

			// inicializa la fabrica de conexiones jms
			cf = (ConnectionFactory) context.lookup("java:/ConnectionFactory");

		} catch (NamingException e) {
			System.out.println("Error");
		}

	}

	public void subscribe() throws JMSException {
		topicSubs1 = ts3.createSubscriber(t1);
		topicSubs2 = ts3.createSubscriber(t2);
		topicPublisher = ts3.createPublisher(t3);

		MessageListener msglistener1 = new Listener1();
		MessageListener msglistener2 = new Listener2();

		topicSubs1.setMessageListener(msglistener1);
		topicSubs2.setMessageListener(msglistener2);
	}

	public void inicializarContexto() {
		try {

			// inicializa datasource por jndi
			ds1 = (DataSource) context.lookup("java:jboss/datasources/XAChie2");
			// accede a la cola de la web app 2
			miCola = (Queue) context.lookup("queue/WebApp3");
			cola1 = (Queue) context.lookup("queue/WebApp1");
			cola2 = (Queue) context.lookup("queue/WebApp2");
			conm = cf.createConnection();
			System.out.println("Contexto inicializado - AN");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cerrarConexion() {
		try {
			conm.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void transaccionEnviarEjemplo() {
		try {
			UserTransaction utx = (UserTransaction) context.lookup("/UserTransaction");
			inicializarContexto();
			utx.begin();

			// Hacemos cambios sobre bd
			Statement st = conn1.createStatement();
			String sql = "";
			System.out.println(sql);
			int num = st.executeUpdate(sql);
			System.out.println("PuertoAndes 0206 - Se ingresaron " + num + " filas - conexion1");
			st.close();

			// Inicia sesion utilizando la conexion
			Session session = conm.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Crea una sesion para producir mensajes hacia la cola que habiamos
			// creado
			MessageProducer producer = session.createProducer(miCola);

			// Existen otros tipos de mensajes.
			// En este caso utilizamos un mensaje simple de texto para enviar la
			// informacion
			TextMessage msg = session.createTextMessage();
			String peticion = "";
			msg.setText(peticion);
			producer.send(msg);
			System.out.println("PuertoAndes 0206 - Se envi�: " + peticion);

			// Si se envio de forma correcta el mensaje y se realizaron los
			// cambios
			// se hace commit
			utx.commit();
			cerrarConexion();

		} catch (Exception e) {

		}
	}

	public void transaccionRecibirEjemplo() {
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

			// Recibimos un mensaje
			System.out.println("PuertoAndes0206 - Esperando mensaje...");
			Message msn = consumer.receive();
			TextMessage txt = (TextMessage) msn;
			String porInsertar = txt.getText();
			System.out.println("PuertoAndes0206 - Recibido..." + porInsertar);

			// Ejecutamos la transaccion en la base de datos
			Statement st = conn1.createStatement();
			String sql = "INESERT INTO PRUEBA VALUES ('" + porInsertar + "')";
			System.out.println(sql);

			int num = st.executeUpdate(sql);
			System.out.println("Se ingresaron " + num + " filas - PuertoAndes0206");
			// Si se envio de forma correcta el mensaje y se realizaron los
			// cambios
			// se hace commit
			utx.commit();
			cerrarConexion();

		} catch (Exception e) {

		}
	}

	public void empezarRF14(ArrayList<CargaUnificada> cargas) throws JMSException {
		MensajeCargas msjCargas = new MensajeCargas(3, "RF14", cargas);
		ObjectMessage msg = ts3.createObjectMessage(msjCargas);
		System.out.println("va a publicar RF14 - AN");
		topicPublisher.publish(msg);
		System.out.println("publico RF14 - AN");
	}

	public void responderRF14(Queue cola) {
		System.out.println("Va a responer rf14 - AN");
	}

	public class Listener1 implements MessageListener {
		public void onMessage(Message msg) {
			try {
				ObjectMessage obj = (ObjectMessage) msg;
				Mensaje msj = (Mensaje) obj.getObject();
				String texto = msj.getMensaje();
				if (texto.equals("RF14")) {
					responderRF14(cola1);
				} else if (texto.contains("RF15P1")) {
					responderRF15(cola1, texto.substring(7));
				} else if (texto.contains("RF15P2")) {
					terminarRF15(texto.substring(7));
				} else if (texto.contains("RFC11")) {
					responderRFC11(cola1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public class Listener2 implements MessageListener {
		public void onMessage(Message msg) {
			try {
				ObjectMessage obj = (ObjectMessage) msg;
				Mensaje msj = (Mensaje) obj.getObject();
				String texto = msj.getMensaje();
				if (texto.equals("RF14")) {
					responderRF14(cola2);
				} else if (texto.contains("RF15P1")) {
					responderRF15(cola2, texto.substring(7));
				} else if (texto.contains("RF15P2")) {
					terminarRF15(texto.substring(7));
				} else if (texto.contains("RFC11")) {
					responderRFC11(cola2);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public int empezarRF15(String rut) throws Exception {
		int descuento = 0;
		Mensaje msj = new Mensaje(3, "RF15P1 " + rut);
		ObjectMessage msg = ts3.createObjectMessage(msj);
		System.out.println("va a publicar RF15P1 - AN " + rut);
		topicPublisher.publish(msg);
		System.out.println("publico RF15P1 - AN " + rut);
		try {
			inicializarContexto();

			// Inicia sesion utilizando la conexion
			Session session = conm.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Crea una sesion para producir mensajes hacia la cola que habiamos
			// creado
			MessageConsumer consumer = session.createConsumer(miCola);
			conm.start();

			// Recibimos LOS mensaje

			int numClientes = 0;

			System.out.println("Esperando 1 mensaje RF15 - AN...");
			Message msn = consumer.receive();
			TextMessage txt = (TextMessage) msn;
			String respuesta1 = txt.getText();
			if (respuesta1.contains("SI"))
				numClientes++;

			System.out.println("Esperando 2 mensaje RF15 - AN...");
			Message msn2 = consumer.receive();
			TextMessage txt2 = (TextMessage) msn2;
			String respuesta2 = txt2.getText();
			if (respuesta2.contains("SI"))
				numClientes++;

			System.out.println("El exportador existe en " + numClientes + " bd - AN");

			switch (numClientes) {
			case 2:
				descuento = 3;
				break;
			case 3:
				descuento = 5;
				break;
			}

			// TIENES QUE ACTUALIZAR TU BASE DE DATOS.
			
			master.actualizarExportador(rut, descuento);

			cerrarConexion();

		} catch (Exception e) {
			throw e;
		}
		Mensaje msjFinal = new Mensaje(3, "RF15P2 " + rut + " " + descuento);
		ObjectMessage msgFinal = ts3.createObjectMessage(msjFinal);
		System.out.println("va a publicar RF15P2 - AN " + rut + " descuento: " + descuento);
		topicPublisher.publish(msgFinal);
		System.out.println("publico RF15P2 - AN");
		return descuento;
	}

	public void terminarRF15(String rutDescuento) throws JMSException {
		try {
			inicializarContexto();

			String[] data = rutDescuento.split(" ");

			// TIENES QUE ACTUALIZAR TU BASE DE DATOS.
			master.actualizarExportador(data[0], Integer.parseInt(data[1]));
			// *******************************************************

			cerrarConexion();
		} catch (Exception e) {
		}
	}

	public void responderRF15(Queue cola, String rut) {
		System.out.println("Va a responer rf15 - AN");
		try {
			UserTransaction utx = (UserTransaction) context.lookup("/UserTransaction");
			inicializarContexto();
			utx.begin();

			// BUSCAMOS EN LA TABLA SI EXISTE EL EXPORTADOR CON RUT PASADO POR
			// PARAMETRO
			Statement st = conn1.createStatement();
			String sql = "SELECT * FROM EXPORTADORES WHERE RUT = " + rut;
			System.out.println(sql + " - AN");
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
			System.out.println("Se envio " + peticion + " - AN");

			cerrarConexion();

		} catch (Exception e) {

		}
	}

	public ListaAreaUnificada empezarRFC11() throws Exception {
		ArrayList<vos.AreaUnificada> unif = new ArrayList<>();
		Mensaje msj = new Mensaje(3, "RFC11");
		ObjectMessage msg = ts3.createObjectMessage(msj);
		System.out.println("va a publicar RFC11 - AN");
		topicPublisher.publish(msg);
		System.out.println("publico RFC11 - AN ");
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


			System.out.println("Esperando 1 mensaje RFC11 - AN...");
			Message msn = consumer.receive();
			ObjectMessage txt = (ObjectMessage) msn;
			MensajeAreas respuesta1 = (MensajeAreas) txt.getObject();


			System.out.println("Esperando 2 mensaje RFC11 - AN...");
			Message msn2 = consumer.receive();
			ObjectMessage txt2 = (ObjectMessage) msn2;
			MensajeAreas respuesta2 = (MensajeAreas) txt2.getObject();
			cerrarConexion();
			
			for(AreaUnificada au : respuesta1.getAreas()){
				unif.add(new vos.AreaUnificada(au.getEstado(),au.getTipo()));
			}
			
			for(AreaUnificada au : respuesta2.getAreas()){
				unif.add(new vos.AreaUnificada(au.getEstado(),au.getTipo()));
			}

		} catch (Exception e) {
			throw e;
		}
		return new ListaAreaUnificada(unif);
	}

	public void responderRFC11(Queue cola) throws Exception {
		// SIMPLEMENTE HACEN EL LLAMADO AL METODO NORMAL QUE YA TIENEN
		// IMPLEMENTADO DE RFC6 Y CONVIERTEN LAS
		// AREAS A LAS AREAS ESTANDAR.
		ListaConsultaAreas lista = master.consultarAreas(1,
				new ParametroBusqueda(new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>()));
		List<ConsultaAreas> areas = lista.getAreas();
		ArrayList<AreaUnificada> areasUnificadas = new ArrayList<>();
		for (ConsultaAreas a : areas) {
			areasUnificadas.add(new AreaUnificada(a.getEstado_area(), a.getTipo_area()));
		}
		MensajeAreas msj = new MensajeAreas(3, "RFC11", areasUnificadas);

		System.out.println("Va a responer RFC11 - AN");
		try {
			UserTransaction utx = (UserTransaction) context.lookup("/UserTransaction");
			inicializarContexto();
			utx.begin();

			// Inicia sesion utilizando la conexion
			Session session = conm.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Crea una sesion para producir mensajes hacia la cola que habiamos
			// creado
			MessageProducer producer = session.createProducer(cola);

			// Existen otros tipos de mensajes.
			// En este caso utilizamos un mensaje simple de texto para enviar la
			// informacion
			ObjectMessage msg = session.createObjectMessage(msj);
			producer.send(msg);
			System.out.println("Se puso en la cola de RFC11 - AN");

			cerrarConexion();

		} catch (Exception e) {

		}
	}
}
