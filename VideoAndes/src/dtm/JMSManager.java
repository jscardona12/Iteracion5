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
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
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
	private Queue colaDefinida;
	
	
	/**
   * Cola definida para recepcion de mensajes
   */
	private Queue queuePuerto1, queuePuerto3;
  private Topic topicPuerto1, topicPuerto3;
	
	public void inicializarContexto(){
		try {
			context = new InitialContext();
			
			UserTransaction utx = (UserTransaction) context.lookup("java:comp/UserTransaction");
			
			//inicializa datasource por jndi
			ds1=(DataSource) context.lookup("java:jboss/datasources/XAChie2");
			//inicializa la fabrica de conexiones jms
			cf=(ConnectionFactory) context.lookup("java:/JmsXA");
			//accede a la cola de la web app 2
			colaDefinida=(Queue) context.lookup("queue/WebApp2");
			conm = cf.createConnection();
			
			queuePuerto1= (Queue)context.lookup("queue/WebApp1");
			queuePuerto3= (Queue)context.lookup("queue/WebApp3");
			topicPuerto1= (Topic)context.lookup("topic/WebApp1");
			topicPuerto3= (Topic)context.lookup("topic/WebApp3");
			
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
			MessageProducer producer = session.createProducer(colaDefinida);

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
			MessageConsumer consumer = session.createConsumer(colaDefinida);
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
	private String[] args;

	protected boolean failure = false;

	protected void run(final String[] args1)
	{
		this.args = args1;
		//if we have a cluster of servers wait a while for the cluster to form properly
		if(args != null && args.length > 1)
		{
			System.out.println("****pausing to allow cluster to form****");
			Thread.yield();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				//ignore
			}
		}

		try {
			if (!runExample()) {
				failure = true;
			}
			System.out.println("example complete");
		} catch (Throwable e) {
			failure = true;
			e.printStackTrace();
		}


		if (failure) {
			System.err.println("FAILURE!");
		} else {
			System.out.println("OK.");
		}
	}

	public static void main(final String[] args)
	{
		new JMSManager().run(args);
	}

	protected InitialContext getContext(final int serverId) throws Exception
	{
		//      HornetQExample.log.info("using " + args[serverId] + " for jndi");
		Properties props = new Properties();
		props.put("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
		props.put("java.naming.provider.url", args[serverId]);
		props.put("java.naming.factory.url.pkgs","org.jboss.naming:org.jnp.interfaces");
		return new InitialContext(props);
	}


	public boolean runExample() throws Exception
	{
		javax.jms.Connection connection = null;
		InitialContext initialContext = null;
		try
		{
			// Step 1. Create an initial context to perform the JNDI lookup.
			initialContext = getContext(0);

			// Step 2. Perfom a lookup on the queue
			Queue queue = (Queue)initialContext.lookup("/queue/exampleQueue");

			// Step 3. Perform a lookup on the Connection Factory
			ConnectionFactory conf = (ConnectionFactory)initialContext.lookup("/ConnectionFactory");

			// Step 4.Create a JMS Connection
			connection = conf.createConnection();

			// Step 5. Create a JMS Session
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Step 6. Create a JMS Message Producer
			MessageProducer producer = session.createProducer(queue);

			// Step 7. Create a Text Message
			TextMessage message = session.createTextMessage("This is a text message");

			System.out.println("Sent message: " + message.getText());

			// Step 8. Send the Message
			producer.send(message);

			// Step 9. Create a JMS Message Consumer
			MessageConsumer messageConsumer = session.createConsumer(queue);

			// Step 10. Start the Connection
			connection.start();

			// Step 11. Receive the message
			TextMessage messageReceived = (TextMessage)messageConsumer.receive(5000);

			System.out.println("Received message: " + messageReceived.getText());

			return true;
		}
		finally
		{
			// Step 12. Be sure to close our JMS resources!
			if (initialContext != null)
			{
				initialContext.close();
			}
			if (connection != null)
			{
				connection.close();
			}
		}
	}
}
