package rest;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.PuertoAndesMaster;
import vos.Barco;

@Path("barcos")
public class PuestoAndesBarcosServices
{

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
	public Response getBarcos() {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		ArrayList<Barco> barcos;
		try {
			barcos = tm.darBarcos();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(barcos).build();
	}


    /**
     * Método que expone servicio REST usando GET que busca el video con el nombre que entra como parámetro
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/name/"name para la busqueda"
     * @param name - Nombre del video a buscar que entra en la URL como parámetro 
     * @return Json con el/los videos encontrados con el nombre que entra como parámetro o json con 
     * el error que se produjo
     */
//	@GET
//	@Path("/name/{name}")
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response getBarcoName(@javax.ws.rs.PathParam("name") String name) {
//		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
//		ArrayList<Barco> barco;
//		try {
//			if (name == null || name.length() == 0)
//				throw new Exception("Nombre del barco no valido");
//			barco = tm.buscarBarcoPorName(name);
//		} catch (Exception e) {
//			return Response.status(500).entity(doErrorMessage(e)).build();
//		}
//		return Response.status(200).entity(barco).build();
//	}
	
    /**
     * Método que expone servicio REST usando PUT que agrega el video que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/video
     * @param barco - video a agregar
     * @return Json con el video que agrego o Json con el error que se produjo
     */
	@PUT
	@Path("/barco")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addBarco(Barco barco) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.addBarco(barco);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(barco).build();
	}
	
    /**
     * Método que expone servicio REST usando POST que actualiza el video que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/video
     * @param barco - video a actualizar. 
     * @return Json con el video que actualizo o Json con el error que se produjo
     */
	@POST
	@Path("/barco")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateBarco(Barco barco) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.updateBarco(barco);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(barco).build();
	}
	
    /**
     * Método que expone servicio REST usando DELETE que actualiza el video que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/video
     * @param barco - video a aliminar. 
     * @return Json con el video que elimino o Json con el error que se produjo
     */
	@DELETE
	@Path("/barco")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteBarco(Barco barco) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.deleteBarco(barco);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(barco).build();
	}



}
