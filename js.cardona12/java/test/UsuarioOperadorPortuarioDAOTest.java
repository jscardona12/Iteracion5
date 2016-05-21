package test;


import java.sql.SQLException;

import org.junit.Test;

import edu.uniandes.main.data.Date;
import edu.uniandes.main.data.Estancia;
import edu.uniandes.main.data.Mercancia;

public class UsuarioOperadorPortuarioDAOTest {

	@Test
	public void testEjecutarQueryRegistrarArriboBuque() {
		UsuarioOperadorPortuarioDAO connection = new UsuarioOperadorPortuarioDAO();
		Date fechaLlegada = new Date("2016-03-11", 02, 20, 50);
		Estancia estancia = new Estancia(1, fechaLlegada, fechaLlegada, 1, 1);
		try {
			connection.ejecutarQueryRegistarArriboBuque(estancia);
		} catch (SQLException e) {
			e.getMessage();
		}
	}
	
	@Test
	public void testEjecutarQueryRegistrarSalidaBuque() {
		UsuarioOperadorPortuarioDAO connection = new UsuarioOperadorPortuarioDAO();
		Date fechaSalida = new Date("2016-10-11", 02, 20, 50);
		try {
			connection.ejecutarQueryRegistrarSalidaBuque("1", fechaSalida);
		} catch (SQLException e) {
			e.getMessage();
		}
	}
	
	@Test
	public void testEjecutarQueryRegistrarEntregaCargaImportador() {
		UsuarioOperadorPortuarioDAO connection = new UsuarioOperadorPortuarioDAO();
		Date fechaEjecucion = new Date("2016-03-11", 02, 20, 50);
		try {
			connection.ejecutarQueryRegistrarEntregaCargaImportador(1, 1, 1, 1, fechaEjecucion);
		} catch (SQLException e) {
			e.getMessage();
		}
	}
	
	@Test
	public void testEjecutarQueryRegistrarLlegadaCargaAlAlmacen() {
		UsuarioOperadorPortuarioDAO connection = new UsuarioOperadorPortuarioDAO();
		Date fechaEjecucion = new Date("2016-03-11", 02, 20, 50);
		Mercancia mercancia = new Mercancia(2, "Audifonos", 20, 1);
		try {
			connection.ejecutarQueryRegistrarLlegadaCargaAlAlmacen(2, 1, mercancia, fechaEjecucion);
		} catch (SQLException e) {
			e.getMessage();
		}
	}
	
	@Test
	public void testEjecutarQueryRegistrarTipoCargaBuque() {
		UsuarioOperadorPortuarioDAO connection = new UsuarioOperadorPortuarioDAO();
		Date fechaEjecucion = new Date("2016-03-11", 02, 20, 50);
		Mercancia mercancia = new Mercancia(3, "Contenedor", 10, 2);
		try {
			connection.ejecutarQueryRegistrarTipoCargaBuque(3, 1, mercancia, fechaEjecucion);
		} catch (SQLException e) {
			e.getMessage();
		}
	}
	@Test
	public void ejecutarQueryGenerarFacturaExportador(){
		UsuarioOperadorPortuarioDAO connection = new UsuarioOperadorPortuarioDAO();
		Date fecha = new Date("2016-12-03", 14, 50, 30);
		try {
			connection.ejecutarQueryGenerarFacturaExportador(1, 1, 2, 1, fecha, 20);
		} catch (SQLException e) {
			e.getMessage();
		}
	}

}
