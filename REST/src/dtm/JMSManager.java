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
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
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
	 * Conexion al topico
	 */
	private TopicConnection connTopic;
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

	public JMSManager(){
		inicializarTopic();
	}
	
	public void inicializarTopic(){
		inicializarAmbos();
		try{
			TopicConnectionFactory tcf = (TopicConnectionFactory) cf;
			connTopic=tcf.createTopicConnection();
			t1=(Topic) context.lookup("topic/WebApp1");
			t2=(Topic) context.lookup("topic/WebApp2");
			t3=(Topic) context.lookup("topic/WebApp3");
			ts1 = connTopic.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
			ts2 = connTopic.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
			ts3 = connTopic.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
			connTopic.start();
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
		inicializarTopic();
		topicSubs1 = ts1.createSubscriber(t1);
		topicSubs3 = ts3.createSubscriber(t3);
		topicPublisher = ts2.createPublisher(t2);
		topicSubs1.setMessageListener(new Listener1());
		topicSubs3.setMessageListener(new Listener3());
	}

	public void inicializarContexto(){
		inicializarAmbos();
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

	public void empezarRF14() throws JMSException{
		String mensaje = "RF14";
		TextMessage tm = ts2.createTextMessage(mensaje);
		topicPublisher.publish(tm);
		
	}
	
	public void responderRF14(Queue cola){
		System.out.println("Va a responer rf14");
	}

	public void responderRF15(Queue cola){
		System.out.println("Va a responer rf15");		
	}
	
	public class Listener1 implements MessageListener
	{
		public void onMessage(Message msg){
			try{
				TextMessage t = (TextMessage) msg;
				String texto = t.getText();
				if(texto.equals("RF14")){
					responderRF14(cola1);
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
				TextMessage t = (TextMessage) msg;
				String texto = t.getText();
				if(texto.equals("RF14")){
					responderRF14(cola3);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
