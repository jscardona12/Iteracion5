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
import java.sql.SQLException;
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

import dao.DAOTablaAlmacenamiento;
import dao.DAOTablaCargaEnAlmacen;
import tm.VideoAndesMaster;
import vos.ListaMovimientoAlmacen;

/**
 * A simple JMS Queue example that creates a producer and consumer on a queue and sends then receives a message.
 *
 * @author <a href="ataylor@redhat.com">Andy Taylor</a>
 */
public class JMSManager
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
	 * Fabrica de conexiones para topics
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
	private Queue cola1;
	/**
	 * Cola definida para enviar a puerto 3
	 */
	private Queue cola3;
	/**
	 * Conexion al topico1
	 */
	private TopicConnection connTopic1;
	/**
	 * Conexion al topico2
	 */
	private TopicConnection connTopic2;
	/**
	 * Conexion al topico3
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
	 * Suscribirse a puerto 3
	 */
	private TopicSubscriber topicSubs3;
	/**
	 * yo
	 */
	private TopicPublisher topicPublisher;
	private VideoAndesMaster videoMaster;
	public JMSManager(VideoAndesMaster videoMaster){
		this.videoMaster=videoMaster;
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
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void inicializarAmbos(){

		try {
			context = new InitialContext();

			//inicializa la fabrica de conexiones jms
			cf=(ConnectionFactory) context.lookup("java:/JmsXA");
		} catch (NamingException e) {
			System.out.println("Error");
		}

	}

	public void subscribe() throws JMSException{

		topicSubs1 = ts1.createSubscriber(t1);
		topicSubs3 = ts3.createSubscriber(t3);
		topicPublisher = ts2.createPublisher(t2);
		topicSubs1.setMessageListener(new Listener1());
		topicSubs3.setMessageListener(new Listener3());
	}

	public void inicializarContexto(){

		try {

			//inicializa datasource por jndi
			ds1=(DataSource) context.lookup("java:jboss/datasources/XAChie2");
			//accede a la cola de la web app 2
			miCola=(Queue) context.lookup("queue/WebApp2");
			cola1 = (Queue) context.lookup("queue/WebApp1");
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
			
			ArrayList<CargaUnificada> cargasPara1=new ArrayList<>();
			ArrayList<CargaUnificada> cargasPara3=new ArrayList<>();
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
				MessageProducer producer = session.createProducer(cola1);
				ObjectMessage message = session.createObjectMessage( new MensajeCargas(2, "CANCEL", null) );
				producer.send(message);
				producer = session.createProducer(cola3);
				message = session.createObjectMessage( new MensajeCargas(2, "CANCEL", null) );
				producer.send(message);
			}else{
				MessageProducer producer = session.createProducer(cola1);
				ObjectMessage message = session.createObjectMessage( new MensajeCargas(2, "CANCEL", cargasPara1) );
				producer.send(message);
				producer = session.createProducer(cola3);
				message = session.createObjectMessage( new MensajeCargas(2, "CANCEL", cargasPara3) );
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
			libre = videoMaster.getAlmacenamientoLibre(tipo);

			//fin transaccion propia
			try{
				UserTransaction utx = (UserTransaction) context.lookup("/UserTransaction");
				inicializarContexto();
				utx.begin();

				//Inicia sesion utilizando la conexion
				Session session = conm.createSession(false, Session.AUTO_ACKNOWLEDGE);

				//Crea una sesion para producir mensajes hacia la cola destino
				MessageProducer producer = session.createProducer(cola);
				ObjectMessage message = session.createObjectMessage( new Mensaje(2, libre+"") );
				producer.send(message);
				System.out.println("PuertoAndes 0206 - Se envi�: "+libre);

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
					videoMaster.insertarCargas(porInsertar.getCargas());
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



	public void responderRF15(Queue cola){
		System.out.println("Va a responer rf15");		
	}

	public class Listener1 implements MessageListener
	{
		public void onMessage(Message msg){
			try{
				ObjectMessage t = (ObjectMessage) msg;
				Mensaje m = (Mensaje) t.getObject();
				String texto = m.getMensaje();
				if(texto.startsWith("RF14")){
					responderRF14(cola1, texto.split("-")[1]);
				}else if(texto.equals("RF15")){
					responderRF15(cola1);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public class Listener3 implements MessageListener
	{
		public void onMessage(Message msg){
			try{
				ObjectMessage t = (ObjectMessage) msg;
				Mensaje m = (Mensaje) t.getObject();
				String texto = m.getMensaje();
				if(texto.startsWith("RF14")){
					responderRF14(cola3,texto.split("-")[1]);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
