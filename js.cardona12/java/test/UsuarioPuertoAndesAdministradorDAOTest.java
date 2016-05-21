package test;

import java.sql.SQLException;

import org.junit.Test;

import edu.uniandes.main.data.Almacen;
import edu.uniandes.main.data.AlmacenBodega;
import edu.uniandes.main.data.AlmacenBodegaCuartoFrio;
import edu.uniandes.main.data.AlmacenBodegaPlataformaExterna;
import edu.uniandes.main.data.AlmacenCobertizo;
import edu.uniandes.main.data.AlmacenPatio;
import edu.uniandes.main.data.AlmacenSitio;
import edu.uniandes.main.data.Buque;
import edu.uniandes.main.data.BuqueMultiProposito;
import edu.uniandes.main.data.BuquePortaContenedor;
import edu.uniandes.main.data.BuqueRORO;
import edu.uniandes.main.data.Exportador;
import edu.uniandes.main.data.Importador;
import edu.uniandes.main.data.Muelle;

public class UsuarioPuertoAndesAdministradorDAOTest {

	@Test
	public void testEjecutarQueryRegistrarImportador() {
		Importador importador = new Importador(1, "Maria", "NO", "Persona Natural");
		UsuarioPuertoAndesAdministradorDAO connection = new UsuarioPuertoAndesAdministradorDAO();
		try {
			connection.ejecutarQueryRegistrarImportador(importador);
		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
		}
		Importador importadorNoRegistable = new Importador(2, "", "", "");
		try {
			connection.ejecutarQueryRegistrarImportador(importadorNoRegistable);
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	@Test
	public void testEjecutarQueryRegistrarExportador(){
		Exportador exportador = new Exportador(1, "Sofia", "Persona Natural", "OP");
		UsuarioPuertoAndesAdministradorDAO connection = new UsuarioPuertoAndesAdministradorDAO();
		try {
			connection.ejecutarQueryRegistrarExportador(exportador);
		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
		}
		Exportador exportadorNoRegistable = new Exportador(2, "", "", "");
		try {
			connection.ejecutarQueryRegistrarExportador(exportadorNoRegistable);
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	@Test
	public void testEjecutarQueryRegistrarMuelle(){
		Muelle muelle = new Muelle(1);
		UsuarioPuertoAndesAdministradorDAO connection = new UsuarioPuertoAndesAdministradorDAO();
		try {
			connection.ejecutarQueryRegistrarMuelle(muelle);
		} catch (SQLException e) {
			e.getMessage();
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testEjecutarQueryRegistrarBodega(){
		UsuarioPuertoAndesAdministradorDAO connection = new UsuarioPuertoAndesAdministradorDAO();
		AlmacenBodega bodegaSinCFYPL = new AlmacenBodega(1, Almacen.getBodega(), 5, 5, null, null);
		try {
			connection.ejecutarQueryRegistrarBodega(bodegaSinCFYPL);
		} catch (Exception e) {
			e.getMessage();
		}
		AlmacenBodegaCuartoFrio cuartoFrio = new AlmacenBodegaCuartoFrio(2, 8, 2, 2, 2, 10);
		AlmacenBodega bodegaConCFYSinPL = new AlmacenBodega(2, Almacen.getBodega(), 5, 5, cuartoFrio, null);
		try {
			connection.ejecutarQueryRegistrarBodega(bodegaConCFYSinPL);
		} catch (Exception e) {
			e.getMessage();
		}
		AlmacenBodegaCuartoFrio cuartoFrioConCFYPL = new AlmacenBodegaCuartoFrio(3, 5, 1, 5, 1, 6);
		AlmacenBodegaPlataformaExterna plataformaExternacuartoFrioConCFYPL = new AlmacenBodegaPlataformaExterna(3, 5);
		AlmacenBodega bodegaConCFYPL = new AlmacenBodega(3, Almacen.getBodega(), 5, 5, cuartoFrioConCFYPL, plataformaExternacuartoFrioConCFYPL);
		try {
			connection.ejecutarQueryRegistrarBodega(bodegaConCFYPL);
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	@Test
	public void testEjecutarQueryRegistrarCobertizo() {
		UsuarioPuertoAndesAdministradorDAO connection = new UsuarioPuertoAndesAdministradorDAO();
		AlmacenCobertizo cobertizo = new AlmacenCobertizo(4, Almacen.getCobertizo(), 1, "Pelotas");
		try {
			connection.ejecutarQueryRegistrarCobertizo(cobertizo);
		} catch (SQLException e) {
			e.getMessage();
		}
	}
	
	@Test
	public void testEjecutarQueryRegistrarPatio() {
		UsuarioPuertoAndesAdministradorDAO connection = new UsuarioPuertoAndesAdministradorDAO();
		AlmacenPatio patio = new AlmacenPatio(5, Almacen.getPatio(), 2, "Computadores");
		try {
			connection.ejecutarQueryRegistrarPatio(patio);
		} catch (SQLException e) {
			e.getMessage();
		}
	}
	
	@Test
	public void testEjecutarQueryRegistrarSitio() {
		UsuarioPuertoAndesAdministradorDAO connection = new UsuarioPuertoAndesAdministradorDAO();
		AlmacenSitio sitio = new AlmacenSitio(6, Almacen.getSitio(), "Edu", 20);
		try {
			connection.ejecutarQueryRegistrarSitio(sitio);
		} catch (SQLException e) {
			e.getMessage();
		}
	}
	
	
	@Test
	public void testEjecutarQueryRegistrarBuqueMultiProposito(){
		UsuarioPuertoAndesAdministradorDAO connection = new UsuarioPuertoAndesAdministradorDAO();
		BuqueMultiProposito buqueMultiProposito = new BuqueMultiProposito(1, "USS Diego", "Diego", "Diego", Buque.getCargaMultiple(), 100);
		try {
			connection.ejecutarQueryRegistrarBuqueMultiProposito(buqueMultiProposito);
		} catch (SQLException e) {
			e.getMessage();
		}
	}
	
	@Test
	public void testEjecutarQueryRegistrarBuquePortaContenedor(){
		UsuarioPuertoAndesAdministradorDAO connection = new UsuarioPuertoAndesAdministradorDAO();
		BuquePortaContenedor buquePortaContenedor = new BuquePortaContenedor(2, "USS Martin", "Martin", "Martin", Buque.getCargaContenedor(), 40);
		try {
			connection.ejecutarQueryRegistrarBuquePortaContenedor(buquePortaContenedor);
		} catch (SQLException e) {
			e.getMessage();
		}
	}
	
	@Test
	public void testEjecutarQueryRegistrarBuqueRORO(){
		UsuarioPuertoAndesAdministradorDAO connection = new UsuarioPuertoAndesAdministradorDAO();
		BuqueRORO buqueRORO = new BuqueRORO(3, "USS John", "John", "John", Buque.getCargaMovil(), 50);
		try {
			connection.ejecutarQueryRegistrarBuqueRORO(buqueRORO);
		} catch (SQLException e) {
			e.getMessage();
		}
	}
}
