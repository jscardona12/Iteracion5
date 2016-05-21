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
import vos.Buque;
import vos.ListaArribosYSalidas;
import vos.ListaBuques;

/**
 * Clase que expone servicios REST con ruta base: http://"ip o nombre de host":8080/VideoAndes/rest/videos/...
 * @author Juan
 */
@Path("buque")
public class BuqueService {

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
	@GET
	@Path("getBuques")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getBuques() {
		VideoAndesMaster tm = new VideoAndesMaster(getPath());
		ListaBuques videos;
		try {
			videos = tm.darBuques();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(videos).build();
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
	public Response addBuque(Buque b,@javax.ws.rs.PathParam("id") int id) {
		VideoAndesMaster tm = new VideoAndesMaster(getPath());
		try {
			tm.addBuque(b,id);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(b).build();
	}
	/**
	 * Método que expone servicio REST usando GET que da todos los videos de la base de datos.
	 * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos
	 * @return Json con todos los videos de la base de datos O json con 
	 * el error que se produjo
	 */
	@GET
	@Path("arribos_salidas")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getArribosSalidas(@QueryParam("nombre") String nombreBuque, @QueryParam("tipoCarga") String tipoCarga,
			@QueryParam("tipoBuque") String tipoBuque, @QueryParam("inicio") String inicio,@QueryParam("fin") String fin) {
		VideoAndesMaster tm = new VideoAndesMaster(getPath());
		ListaArribosYSalidas lista;
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date inicioD = (inicio == null) ? null : sdf.parse(inicio);
			Date finD = (fin == null) ? null : sdf.parse(fin);

			lista = tm.darArribosSalidas(nombreBuque, tipoBuque, inicioD, finD);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(lista).build();
	}
	/**
	 * RF12
	 * Método que expone servicio REST usando PUT para deshabilitar un buque una carga de un área de almacenamiento
	 * @return
	 */
	@GET
	@Path("deshabilitar/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deshabilitarBuque( @QueryParam("razon") String razon, @QueryParam("buque") int idB, @QueryParam("user") int idUser) {
		VideoAndesMaster tm = new VideoAndesMaster(getPath());
		try {
			tm.deshabilitarBuque(idB, razon, idUser);
		} catch (Exception e) {
		  e.printStackTrace();
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(idB).build();
	}
	/**
	 * RF10
	 * Método que expone servicio REST usando PUT para cargar en un buque una carga de un área de almacenamiento
	 * @return
	 */
	@PUT
	@Path("cargarBuque/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cargarBuque(@QueryParam("user") int idUser, List<Integer> idCargas, @QueryParam("buque") int idB) {
		VideoAndesMaster tm = new VideoAndesMaster(getPath());
		try {
			tm.cargarBuque(idCargas, idB,idUser, null);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(idCargas).build();
	}
	/**
	 * Método que expone servicio REST usando GET para descargar en un buque una carga de un área de almacenamiento
	 * @return
	 */
	@GET
	@Path("descargarBuque/")
//	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response descargarBuque(@QueryParam("user") int idUser, @QueryParam("buque") int idB, @QueryParam("almacen") int idA) {
		VideoAndesMaster tm = new VideoAndesMaster(getPath());
		try {
			tm.descargarCargaPuertoAndes(idA, idB, new Date());
		} catch (Exception e) {
		  e.printStackTrace();
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(idB).build();
	}
	

}
