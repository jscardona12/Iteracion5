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


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.VideoAndesMaster;
import vos.Almacenamiento;
import vos.ListaAlmacenamientos;
import vos.ListaMovimientoAlmacen;
import vos.MovimientoCarga;

/**
 * Clase que expone servicios REST con ruta base: http://"ip o nombre de host":8080/VideoAndes/rest/videos/...
 * @author Juan
 */
@Path("almacenamiento")
public class AlmacenamientoService {

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
     * Método que expone servicio REST usando PUT que agrega el video que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/personas/importador
     * @param exportador - video a agregar
     * @return Json con el video que agrego o Json con el error que se produjo
     */
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addAlmacenamiento(Almacenamiento al,@javax.ws.rs.PathParam("id") int id) {
		VideoAndesMaster tm = new VideoAndesMaster(getPath());
		try {
			tm.addAlmacenamiento(al,id);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(al).build();
	}
	/**
	 * Método que expone servicio REST usando PUT para deshabilitar un buque una carga de un área de almacenamiento
	 * @return
	 */
	@GET
	@Path("cerrar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response cerrarAlmacen( @QueryParam("razon") String razon, @QueryParam("almacen") int idA, @QueryParam("user") int idUser) {
		VideoAndesMaster tm = new VideoAndesMaster(getPath());
		try {
			tm.cerrarAreaAlmacenamiento(idA, razon, idUser);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(idA).build();
	}
	
 @GET
 @Path("consultarAlmacenamientos")
 @Produces({ MediaType.APPLICATION_JSON })
 public Response darMovimientosCarga(@QueryParam("persona") int idPersona)
 {
   VideoAndesMaster tm = new VideoAndesMaster(getPath());
   ListaAlmacenamientos lista;
   try {
     
     lista = tm.consultarAlmacenamientos(idPersona, null);
   } catch (Exception e) {
     e.printStackTrace();
     return Response.status(500).entity(doErrorMessage(e)).build();
   }
   return Response.status(200).entity(lista).build();
 }
 
 @GET
 @Path("movimientosAreas")
 @Produces({ MediaType.APPLICATION_JSON })
 public Response darMovimientosAreas(@QueryParam("user") int user, @QueryParam("area1") int area1, @QueryParam("area2") int area2)
 {
   VideoAndesMaster tm = new VideoAndesMaster(getPath());
   ListaMovimientoAlmacen lista;
   try {
     
     lista = tm.consultarDosAlmacenamientos(user,area1,area2);
   } catch (Exception e) {
     e.printStackTrace();
     return Response.status(500).entity(doErrorMessage(e)).build();
   }
   return Response.status(200).entity(lista).build();
 }

}
