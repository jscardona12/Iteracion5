package test;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

import edu.uniandes.edu.co.main.queryData.ConsultaAlmacenMasUtilizado;
import edu.uniandes.edu.co.main.queryData.ConsultaValorFacturadoExportador;
import edu.uniandes.edu.co.main.queryData.ElementosConsultaValorFacturaExportador;
import edu.uniandes.main.data.Almacen;
import edu.uniandes.main.data.AlmacenBodega;
import edu.uniandes.main.data.AlmacenBodegaCuartoFrio;
import edu.uniandes.main.data.AlmacenBodegaPlataformaExterna;
import edu.uniandes.main.data.AlmacenCobertizo;
import edu.uniandes.main.data.AlmacenPatio;
import edu.uniandes.main.data.AlmacenSitio;
import edu.uniandes.main.data.Buque;
import edu.uniandes.main.data.Date;
import static org.junit.Assert.*;

public class UsuarioPuertoAndesDAOTest {

	@Test
	public void testEjecutarQueryConsultarArribosYSalidasBuque() {
		UsuarioPuertoAndesDAO connection = new UsuarioPuertoAndesDAO();
		String nombreBuque = "";
		String tipoBuque = "";
		String tipoCargaBuque = "";
		Date fechaInicial = new Date("2015-02-01", 02, 20, 50);
		Date fechaFinal = new Date("2017-10-03", 10, 10, 50);
		String agrupamiento = "";
		String ordenamiento = "";
		try {
			ArrayList<Buque> buques = connection.ejecutarQueryConsultarArribosYSalidasBuques(nombreBuque, tipoBuque,
					tipoCargaBuque, fechaInicial, fechaFinal, agrupamiento, ordenamiento);
			Buque buque = buques.get(0);
			assertEquals(1, buque.getID());
			assertEquals("USS Diego", buque.getNombre());
			assertEquals("Diego", buque.getNombreCompania());
			assertEquals("Diego", buque.getRegistroDeCapitania());
			assertEquals(3, buque.getTipoBuque());
		} catch (SQLException e) {
			e.getMessage();
		}
	}

	@Test
	public void testEjecutarQueryConsultarValorTotalFacturadoAExportadores() {
		UsuarioPuertoAndesDAO connection = new UsuarioPuertoAndesDAO();
		Date fechaInicial = new Date("2015-02-01", 02, 20, 50);
		Date fechaFinal = new Date("2017-10-03", 10, 10, 50);
		try {
			ArrayList<ConsultaValorFacturadoExportador> consultasValoresFacturados =connection.ejecutarQueryConsultarValorTotalFacturadoAExportadores(fechaInicial, fechaFinal);
			ConsultaValorFacturadoExportador consultaValorFacturado = consultasValoresFacturados.get(0);
			int idExportador = consultaValorFacturado.getExportadorID();
			assertEquals(1, idExportador);
			ArrayList<ElementosConsultaValorFacturaExportador> elementos = consultaValorFacturado.getElementos();
			ElementosConsultaValorFacturaExportador elemento = elementos.get(0);
			assertEquals("pelotas", elemento.getTipoCarga());
			assertEquals(1, elemento.getNumeroViajes());
			assertEquals(1, elemento.getNumeroElementosTransportados());
			assertEquals(new Double(10), elemento.getPesoPromedioCarga());
			assertEquals(20, elemento.getTotalValorFacturado());
		} catch (SQLException e) {
			e.getMessage();
		}
	}

	@SuppressWarnings("unused")
	@Test
	public void testEjecutarQueryObtenerAlmacenMasUtilizado() {
		UsuarioPuertoAndesDAO connection = new UsuarioPuertoAndesDAO();
		Date fechaInicial = new Date("2015-02-01", 02, 20, 50);
		Date fechaFinal = new Date("2016-10-03", 10, 10, 50);
		try {
			ArrayList<ConsultaAlmacenMasUtilizado> almacenes = connection.ejecutarQueryObtenerAlmacenMasUtilizado(fechaInicial, fechaFinal);
			assertEquals(1, almacenes.size());
			ConsultaAlmacenMasUtilizado almacen = almacenes.get(0);
			int almacenID = almacen.getAlmacenID();
			int tipoAlmacen = almacen.getAlmacenTipo();
			if (Almacen.getBodega() == tipoAlmacen) {
				int ancho = almacen.getBodegaAncho();
				int largo = almacen.getBodegaLargo();
				AlmacenBodegaCuartoFrio cuartoFrio = null;
				if (UsuarioPuertoAndesDAO.getEsnulo() == almacen.getCuartoFrioArea()) {
					assertEquals(UsuarioPuertoAndesDAO.getEsnulo(), almacen.getCuartoFrioArea());
					assertEquals(UsuarioPuertoAndesDAO.getEsnulo(), almacen.getCuartoFrioAncho());
					assertEquals(UsuarioPuertoAndesDAO.getEsnulo(), almacen.getCuartoFrioLargo());
					assertEquals(UsuarioPuertoAndesDAO.getEsnulo(), almacen.getCuartoFrioAlto());
					assertEquals(UsuarioPuertoAndesDAO.getEsnulo(), almacen.getCuartoFrioAreaFuncionBodega());
				}else {
					int areaCuartoFrio = almacen.getCuartoFrioArea();
					int anchoCuartoFrio = almacen.getCuartoFrioAncho();
					int largoCuartoFrio = almacen.getCuartoFrioLargo();
					int altoCuartoFrio = almacen.getCuartoFrioAlto();
					int areaEnFuncionDeLaBodegaCuartoFrio = almacen.getCuartoFrioAreaFuncionBodega();
					cuartoFrio = new AlmacenBodegaCuartoFrio(almacenID, areaCuartoFrio, anchoCuartoFrio,
							largoCuartoFrio, altoCuartoFrio, areaEnFuncionDeLaBodegaCuartoFrio);
				}
				AlmacenBodegaPlataformaExterna plataformaExterna = null;
				if (UsuarioPuertoAndesDAO.getEsnulo() == almacen.getPlataformaExternaSeparacion()) {
					assertEquals(UsuarioPuertoAndesDAO.getEsnulo(), almacen.getPlataformaExternaSeparacion());
				}else {
					int separacionEntreColumnas = almacen.getPlataformaExternaSeparacion();
					plataformaExterna = new AlmacenBodegaPlataformaExterna(almacenID,
							separacionEntreColumnas);
				}
				AlmacenBodega bodega = new AlmacenBodega(almacenID, tipoAlmacen, ancho, largo, cuartoFrio,
						plataformaExterna);
			} else if (Almacen.getCobertizo() == tipoAlmacen) {
				if (UsuarioPuertoAndesDAO.getEsnulo() == almacen.getCobertizoArea()) {
					assertEquals(UsuarioPuertoAndesDAO.getEsnulo(), almacen.getCobertizoArea());
					assertEquals(null, almacen.getCobertizoTipo());
				}else {
					int area = almacen.getCobertizoArea();
					String tipoCarga = almacen.getCobertizoTipo();
					AlmacenCobertizo cobertizo = new AlmacenCobertizo(almacenID, tipoAlmacen, area, tipoCarga);
				}
			} else if (Almacen.getPatio() == tipoAlmacen) {
				if (UsuarioPuertoAndesDAO.getEsnulo() == almacen.getPatioArea()) {
					assertEquals(UsuarioPuertoAndesDAO.getEsnulo(), almacen.getCobertizoArea());
					assertEquals(null, almacen.getPatioTipo());
				}else {
					int area = almacen.getPatioArea();
					String tipoCarga = almacen.getPatioTipo();
					AlmacenPatio patio = new AlmacenPatio(almacenID, tipoAlmacen, area, tipoCarga);
				}
			} else if (Almacen.getSitio() == tipoAlmacen) {
				if (null == almacen.getSitioNombre()) {
					assertEquals(null, almacen.getSitioNombre());
					assertEquals(UsuarioPuertoAndesDAO.getEsnulo(), almacen.getSitioCapacidad());
				}else {
					String nombre = almacen.getSitioNombre();
					int capacidadGranelesSolidos = almacen.getSitioCapacidad();
					AlmacenSitio sitio = new AlmacenSitio(almacenID, tipoAlmacen, nombre,
							capacidadGranelesSolidos);
				}
			} 
		}catch (SQLException e) {
			e.getMessage();
		}
	}
	
}
