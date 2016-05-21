package test;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

import edu.uniandes.edu.co.main.queryData.ConsultaExportador;
import edu.uniandes.main.data.Date;
import edu.uniandes.main.data.Exportador;
import edu.uniandes.main.data.Mercancia;

public class UsuarioPuertoAndesGerenteDAOTest {

	@Test
	public void testEjecutarQueryConsularExportador() {
		 UsuarioPuertoAndesGerenteDAO connection = new UsuarioPuertoAndesGerenteDAO();
		 Date fechaLlegada = new Date("2016-03-11", 01, 10, 20);
		 Date fechaSalida = new Date("2017-03-11", 01, 10, 20);
		 try {
			 ArrayList<ConsultaExportador> consultasExportadores = connection.ejecutarQueryConsultarExportador("", "", "", "", "", "", fechaLlegada, fechaSalida, "", "");
			 assertEquals(1, consultasExportadores.size());
			 ConsultaExportador consultaExportador = consultasExportadores.get(0);
			 assertEquals(1, consultaExportador.getExportador().getId());
			 assertEquals("Sofia", consultaExportador.getExportador().getNombre());
			 assertEquals(Exportador.getPersonaNatural(), consultaExportador.getExportador().getTipo());
			 assertEquals("OP", consultaExportador.getExportador().getrUT());
			 ArrayList<Mercancia> mercancias = consultaExportador.getMercancias();
			 Mercancia mercancia = mercancias.get(0);
			 assertEquals(2, mercancia.getId());
			 assertEquals("pelotas", mercancia.getTipo());
			 assertEquals(20, mercancia.getTamano());
			 
		 } catch (SQLException e) {
			e.getMessage();
		}
	}
	
	@Test
	public void testEjecutarQueryConsularExportadorVacio() {
		 UsuarioPuertoAndesGerenteDAO connection = new UsuarioPuertoAndesGerenteDAO();
		 try {
			 ArrayList<ConsultaExportador> consultasExportadores = connection.ejecutarQueryConsultarExportador("", "", "", "", "", "", null, null, "", "");
			 assertEquals(1, consultasExportadores.size());
			 
		 } catch (SQLException e) {
			e.getMessage();
		}
	}

}
