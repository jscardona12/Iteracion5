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
import java.sql.SQLException;
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

import dao.DAOTablaAlmacenamiento;
import dao.DAOTablaCargaEnAlmacen;
import tm.VideoAndesMaster;
import vos.Almacenamiento;
import vos.Bodega;
import vos.Cobertizo;
import vos.ListaAlmacenamientos;
import vos.ListaMovimientoAlmacen;
import vos.Patio;
import vos.Silo;

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
   * Datasource fuente de datos 2
   */
  private DataSource ds2;
  
  /**
   * Datasource fuente de datos 3
   */
  private DataSource ds3;
	/**
	 * Conexion para la base de datos 
	 */
	private Connection conn1;
	/**
   * Conexion para la base de datos 
   */
  private Connection conn2;
  /**
   * Conexion para la base de datos 
   */
  private Connection conn3;
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
			ds1=(DataSource) context.lookup("java:jboss/datasources/XAChie1");
			ds2=(DataSource) context.lookup("java:jboss/datasources/XAChie2");
			ds3=(DataSource) context.lookup("java:jboss/datasources/XAChie3");
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

//			System.out.println("Esperando 2 mensaje RF15 - AN...");
//			Message msn2 = consumer.receive();
//			TextMessage txt2 = (TextMessage) msn2;
//			String respuesta2 = txt2.getText();
//			if (respuesta2.contains("SI"))
//				numClientes++;

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
			
			videoMaster.actualizarExportador(rut, descuento);

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



	public void terminarRF15(String rutDescuento) throws JMSException {
		try {
			inicializarContexto();

			String[] data = rutDescuento.split(" ");

			// TIENES QUE ACTUALIZAR TU BASE DE DATOS.
			videoMaster.actualizarExportador(data[0], Integer.parseInt(data[1]));
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

	public ListaAlmacenamientos empezarRFC11() throws Exception {
		ArrayList<Bodega> bodegas = new ArrayList<>();
		ArrayList<Silo> silos = new ArrayList<>();
		ArrayList<Cobertizo> cobertizos = new ArrayList<>();
		ArrayList<Patio> patios = new ArrayList<>();
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
				String tipo = au.getTipo();
				if(tipo.equals("Bodega")){
					bodegas.add(new Bodega(au.getEstado(),au.getTipo()));
				}else if(tipo.equals("Silo")){
					silos.add(new Silo(au.getEstado(),au.getTipo()));					
				}else if(tipo.equals("Patio")){
					patios.add(new Patio(au.getEstado(),au.getTipo()));					
				}else if(tipo.equals("Cobertizo")){
					cobertizos.add(new Cobertizo(au.getEstado(),au.getTipo()));					
				}
			}
			
			for(AreaUnificada au : respuesta2.getAreas()){
				String tipo = au.getTipo();
				if(tipo.equals("Bodega")){
					bodegas.add(new Bodega(au.getEstado(),au.getTipo()));
				}else if(tipo.equals("Silo")){
					silos.add(new Silo(au.getEstado(),au.getTipo()));					
				}else if(tipo.equals("Patio")){
					patios.add(new Patio(au.getEstado(),au.getTipo()));					
				}else if(tipo.equals("Cobertizo")){
					cobertizos.add(new Cobertizo(au.getEstado(),au.getTipo()));					
				}
			}

		} catch (Exception e) {
			throw e;
		}
		return new ListaAlmacenamientos(silos, cobertizos, bodegas, patios);
	}

	public void responderRFC11(Queue cola) throws Exception {
		// SIMPLEMENTE HACEN EL LLAMADO AL METODO NORMAL QUE YA TIENEN
		// IMPLEMENTADO DE RFC6 Y CONVIERTEN LAS
		// AREAS A LAS AREAS ESTANDAR.
		ListaAlmacenamientos lista = videoMaster.consultarAlmacenamientos(0, null);
		List<Bodega> bodegas = lista.getBodegas();
		List<Silo> silos = lista.getSilos();
		List<Cobertizo> cobertizos = lista.getCobertizos();
		List<Patio> patios = lista.getPatios();
		
		ArrayList<AreaUnificada> areasUnificadas = new ArrayList<>();
		for (Bodega a : bodegas) {
			areasUnificadas.add(new AreaUnificada(a.getEstado(), "Bodega"));
		}
		for (Silo a : silos) {
			areasUnificadas.add(new AreaUnificada(a.getEstado(), "Silo"));
		}
		for (Cobertizo a : cobertizos) {
			areasUnificadas.add(new AreaUnificada(a.getEstado(), "Cobertizo"));
		}
		for (Patio a : patios) {
			areasUnificadas.add(new AreaUnificada(a.getEstado(), "Patio"));
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

	public class Listener1 implements MessageListener
	{
		public void onMessage(Message msg){
			try{
				ObjectMessage t = (ObjectMessage) msg;
				Mensaje m = (Mensaje) t.getObject();
				String texto = m.getMensaje();
				if(texto.startsWith("RF14")){
					responderRF14(cola1, texto.split("-")[1]);
				}else if (texto.contains("RF15P1")) {
					responderRF15(cola1, texto.substring(7));
				} else if (texto.contains("RF15P2")) {
					terminarRF15(texto.substring(7));
				} else if (texto.contains("RFC11")) {
					responderRFC11(cola1);
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
				}else if (texto.contains("RF15P1")) {
					responderRF15(cola1, texto.substring(7));
				} else if (texto.contains("RF15P2")) {
					terminarRF15(texto.substring(7));
				} else if (texto.contains("RFC11")) {
					responderRFC11(cola1);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
  public void twoPhaseCommitRF15(String rut) {
    try {
      UserTransaction utx = (UserTransaction) context.lookup("/UserTransaction");
      try {
        inicializarContexto();
        connectXA();
        utx.begin();
        
        System.out.println("Comienza 2PC-RF15");
        try {
          int numClientes = 0;
          
          for(Connection conn : new Connection[]{conn1, conn2, conn3}) {
            Statement st = conn.createStatement();
            String sql = "SELECT * FROM EXPORTADORES WHERE RUT = " + rut;
            System.out.println(sql + " - JS");
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) { numClientes++; };
            st.close();
          }
          
          int descuento = 0;
          
          switch (numClientes) {
          case 2:
            descuento = 3;
            break;
          case 3:
            descuento = 5;
            break;
          }
          
          for(Connection conn : new Connection[]{conn1, conn2, conn3}) {
            Statement st = conn.createStatement();
            String sql = "UPDATE EXPORTADORES SET DESCUENTO = " + descuento + " WHERE RUT = " + rut;
            System.out.println(sql);
            int num = st.executeUpdate(sql);
          }
          
          utx.commit();
        } catch (Exception e) {
          utx.setRollbackOnly();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void connectXA() throws SQLException {
    conn1 = ds1.getConnection();
    conn2 = ds2.getConnection();
    conn2 = ds3.getConnection();
  }
  
  private void closeXA() throws SQLException {
    conn1.close();
    conn2.close();
    conn3.close();
  }
}
