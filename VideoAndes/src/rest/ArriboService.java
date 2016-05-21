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
import vos.Arribo;
import vos.ListaArribos;
import vos.ListaArribosYSalidas;

/**
 * Clase que expone servicios REST con ruta base: http://"ip o nombre de host":8080/VideoAndes/rest/videos/...
 * @author Juan
 */
@Path("arribo")
public class ArriboService {

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
	@Path("getArribos")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getArribos() {
		VideoAndesMaster tm = new VideoAndesMaster(getPath());
		ListaArribos salidas;
		try {
			salidas = tm.darArribos();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(salidas).build();
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
	public Response addArribo(Arribo b,@javax.ws.rs.PathParam("id") int id) {
		VideoAndesMaster tm = new VideoAndesMaster(getPath());
		try {
			tm.addArribo(b,id);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(b).build();
	}

	/**
     * Método que expone servicio REST usando PUT que agrega el video que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/personas/importador
     * @param exportador - video a agregar
     * @return Json con el video que agrego o Json con el error que se produjo
     */
	@GET
	@Path("info")
	@Produces(MediaType.APPLICATION_JSON)
	public Response darInfoArribos(@QueryParam(value = "fInicio") String fechaInicio,
			@QueryParam(value = "fFin") String fechaFin,
			@QueryParam(value = "nBuque") String nBuque,
			@QueryParam(value = "tBuque") String tipoBuque,
			@QueryParam(value = "tCarga") String tipoCarga,
			@QueryParam(value = "orden") String orden
			) {
		VideoAndesMaster tm = new VideoAndesMaster(getPath());
		ListaArribos lista;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date inicio = sdf.parse(fechaInicio);
			Date fin = sdf.parse(fechaFin);
			lista = tm.getInfoArribos(inicio,fin,nBuque,tipoBuque,tipoCarga,orden);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}

		return Response.status(200).entity(lista).build();
	}
	
	/**
     * Método que expone servicio REST usando PUT que agrega el video que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/personas/importador
     * @param exportador - video a agregar
     * @return Json con el video que agrego o Json con el error que se produjo
     */
	@GET
	@Path("info_otros")
	@Produces(MediaType.APPLICATION_JSON)
	public Response darNoInfoArribos(@QueryParam(value = "fInicio") String fechaInicio,
			@QueryParam(value = "fFin") String fechaFin,
			@QueryParam(value = "nBuque") String nBuque,
			@QueryParam(value = "tBuque") String tipoBuque,
			@QueryParam(value = "tCarga") String tipoCarga,
			@QueryParam(value = "orden") String orden
			) {
		VideoAndesMaster tm = new VideoAndesMaster(getPath());
		ListaArribos lista;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date inicio = sdf.parse(fechaInicio);
			Date fin = sdf.parse(fechaFin);
			lista = tm.getInfoArribosWhereNot(inicio,fin,nBuque,tipoBuque,tipoCarga,orden);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}

		return Response.status(200).entity(lista).build();
	}
}
