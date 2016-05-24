/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: VideoAndes
 * Autor: Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package rest;


import java.sql.ResultSet;
import java.util.ArrayList;

import javax.jms.JMSException;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.PuertoAndesMaster;
import vos.Barco;
import vos.Carga;
import vos.ConsultaAreas;
import vos.ConsultaBarcos;
import vos.ConsultaMovimientos;
import vos.ListaAreaUnificada;
import vos.ListaExportadorUnificado;
import vos.OperadorPortuario;
import vos.ParametroBusqueda;
import vos.RespuestaDescuento;
import vos.Salida;
import vos.respConsultaBarcos;


/**
 * Clase que expone servicios REST con ruta base: http://"ip o nombre de host":8080/VideoAndes/rest/videos/...
 * @author Juan
 */
@Path("operadores")
public class PuertoAndesOperadoresServices {

	// Servicios REST tipo GET:


	/**
	 * Atributo que usa la anotación @Context para tener el ServletContext de la conexión actual.
	 */
	@Context
	private ServletContext context;

	/**
	 * Método que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}


	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}


	/**
	 * Método que expone servicio REST usando GET que da todos los videos de la base de datos.
	 * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos
	 * @return Json con todos los videos de la base de datos O json con 
	 * el error que se produjo
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getOperadores() {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		ArrayList<OperadorPortuario> operadores;
		try {
			operadores = tm.darOperadores();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(operadores).build();
	}


	/**
	 * Método que expone servicio REST usando GET que busca el video con el nombre que entra como parámetro
	 * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/name/"name para la busqueda"
	 * @param name - Nombre del video a buscar que entra en la URL como parámetro 
	 * @return Json con el/los videos encontrados con el nombre que entra como parámetro o json con 
	 * el error que se produjo
	 */
	@GET
	@Path("/name/{name}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getOperadorName(@javax.ws.rs.PathParam("name") String name) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		ArrayList<OperadorPortuario> operador;
		try {
			if (name == null || name.length() == 0)
				throw new Exception("Nombre del barco no valido");
			operador = tm.buscarOperadorPorName(name);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(operador).build();
	}

	/**
	 * Método que expone servicio REST usando PUT que agrega el video que recibe en Json
	 * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/video
	 * @param operador - video a agregar
	 * @return Json con el video que agrego o Json con el error que se produjo
	 */
	@PUT
	@Path("/addOperador")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addOperador(OperadorPortuario operador) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.addOperador(operador);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(operador).build();
	}

	/**
	 * Método que expone servicio REST usando POST que actualiza el video que recibe en Json
	 * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/video
	 * @param operador - video a actualizar. 
	 * @return Json con el video que actualizo o Json con el error que se produjo
	 */
	@POST
	@Path("/updateOperador")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateOperador(OperadorPortuario operador) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.updateOperador(operador);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(operador).build();
	}

	/**
	 * Método que expone servicio REST usando DELETE que actualiza el video que recibe en Json
	 * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/video
	 * @param operador - video a aliminar. 
	 * @return Json con el video que elimino o Json con el error que se produjo
	 */
	@DELETE
	@Path("/deleteOperador")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteOperador(OperadorPortuario operador) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.deleteOperador(operador);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(operador).build();
	}

	@PUT
	@Path("/registrarSalida")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addSalida(Barco operador) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.RegistrarSalida(operador);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(operador).build();
	}

	@PUT
	@Path("/{idBuque}/registrarCargaPuerto")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCargaPuerto(@PathParam("idBuque")int barco, Carga carga) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.registrarCargaPuerto(carga,barco);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(carga).build();
	}

	@PUT
	@Path("/registrarEntregaCarga")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addSalidaCarga(Carga operador) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.registrarEntregaCarga(operador);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(operador).build();
	}

	@PUT
	@Path("/registrarFactura")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addFactura(Barco operador) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.registrarFactura(operador);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(operador).build();
	}
	
	@PUT
	@Path("/{idBuque}/cargarBuque")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cargarBuque(@PathParam("idBuque")int idBuque,Carga carga  ) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.cargarBuque(carga, idBuque);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(carga).build();
	}
	
	@PUT
	@Path("/{idArea}/cargarArea")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cargarArea(@PathParam("idArea")int area,Barco barco  ) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.cargarArea(area, barco);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(barco).build();
	}
	
	@PUT
	@Path("/{estado}/dBarco")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response dBarco(@PathParam("estado")String td,Barco barco  ) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.deshabilitarBarco(td, barco);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(barco).build();
	}

	@PUT
	@Path("/{area}/dArea")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response dArea(@PathParam("area")int area  ) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.deshabilitarArea(area);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(area).build();
	}
	
	@GET
	@Path("/{idAgente}/movimientosCarga")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response movimientosCarga(@PathParam("idAgente")int idAgente  ) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		ArrayList<ConsultaMovimientos> resp;
		try {
			resp =tm.darMovimientosCarga(idAgente);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(resp).build();
	}
	@GET
	@Path("/movimientosCarga/{idAgente}/{tipo}-{costo}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response movCargas(@PathParam("idAgente")int idAgente,
			@PathParam("tipo")String tipo,@PathParam("costo")int costo   ) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		ArrayList<ConsultaMovimientos> resp;
		try {
			resp =tm.darMovCarga(idAgente, tipo, costo);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(resp).build();
	}
	
	@GET
	@Path("/Areas/{area1}.{area2}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response Areas(@PathParam("area1")int area1,@PathParam("area2")int area2 ) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		ArrayList<ConsultaAreas> resp;
		try {
			resp =tm.darAreas(area1, area2);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(resp).build();
	}
	
	@PUT
	@Path("/arribosBarco")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response arribosBarcos(ConsultaBarcos c) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		ArrayList<respConsultaBarcos> resp;
		try {
			resp = tm.darArribossBuques(c);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(resp).build();
	}
	
	@PUT
	@Path("/arribosBarcoEF")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response arribosBarcosPorFecha(ConsultaBarcos c) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		ArrayList<respConsultaBarcos> resp;
		try {
			resp = tm.darArribossBuquesEnFecha(c);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(resp).build();
	}
	
	@PUT
	@Path("/insertar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertar() {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		
		try {
			tm.insertar();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity("se insertaron los datos").build();
	}

	@PUT
	@Path("rf14/{{id}}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response rf14(@PathParam("id")int id)  throws Exception {

		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		tm.iniciarRF14(id);

		return Response.status(200).entity("").build();
	}

	@PUT
	@Path("/bono/{rut}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response consultarBono(@javax.ws.rs.PathParam("rut") String rut) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		int descuento;
		try {
			descuento = tm.inicarRF15(rut);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(descuento).build();
	}
	
	@PUT
	@Path("/consultar_costos")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({MediaType.APPLICATION_JSON})
	public Response consultarCostos(ParametroBusqueda pb) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		ListaExportadorUnificado lista;
		try {
			lista = tm.rfc12(pb);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(lista).build();
	}
	@GET
	@Path("/bono2/{rut}")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({MediaType.APPLICATION_JSON})
	public Response consultarCostos(@javax.ws.rs.PathParam("rut") String rut) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		RespuestaDescuento lista;
		try {
			lista = tm.darDescuentoExportador2PC(rut);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(lista).build();
	}
	
	
	@POST
	@Path("/consultar_distr")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({MediaType.APPLICATION_JSON})
	public Response consultarAreaDistr() {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		ListaAreaUnificada lca;
		try {
			lca = tm.rfc11();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(lca).build();
	}

	
}


