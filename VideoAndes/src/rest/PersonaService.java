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
import vos.Exportador;
import vos.Importador;
import vos.InfoExportador;
import vos.ListaExportadores;
import vos.ListaImportadores;

/**
 * Clase que expone servicios REST con ruta base: http://"ip o nombre de host":8080/VideoAndes/rest/videos/...
 * @author Juan
 */
@Path("personas")
public class PersonaService {

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
	@Path("getImportadores")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getImportadores() {
		VideoAndesMaster tm = new VideoAndesMaster(getPath());
		ListaImportadores impo;
		try {
			impo = tm.darImportadores();
		} catch (Exception e) {
			System.out.println("ERROR: "+e.getMessage());
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(impo).build();
	}


	/**
	 * Método que expone servicio REST usando PUT que agrega el video que recibe en Json
	 * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/personas/importador
	 * @param importador - video a agregar
	 * @return Json con el video que agrego o Json con el error que se produjo
	 */
	@PUT
	@Path("importador")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addImportador(@QueryParam("user") int idUser, Importador importador) {
		VideoAndesMaster tm = new VideoAndesMaster(getPath());
		try {
			tm.addImportador(importador, idUser);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(importador).build();
	}
	/**
	 * Método que expone servicio REST usando GET que da todos los videos de la base de datos.
	 * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos
	 * @return Json con todos los videos de la base de datos O json con 
	 * el error que se produjo
	 */
	@GET
	@Path("getExportadores")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getExportadores() {
		VideoAndesMaster tm = new VideoAndesMaster(getPath());
		ListaExportadores exp;
		try {
			exp = tm.darExportadores();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(exp).build();
	}

	/**
	 * Método que expone servicio REST usando GET que da el exportador con las restricciones por parametro
	 * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos
	 * @return Json con InfoExportador
	 */
	@GET
	@Path("infoExportador")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getExportador(@QueryParam("export") Integer id_exportador, @QueryParam("natural") Boolean natural,
			@QueryParam("tipoBuque") String tipoBuque, @QueryParam("inicio") String inicio,@QueryParam("fin") String fin,
			@QueryParam("orden") String orden) {
		VideoAndesMaster tm = new VideoAndesMaster(getPath());
		InfoExportador exportador;
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date inicioD = (inicio == null) ? null : sdf.parse(inicio);
			Date finD = (fin == null) ? null : sdf.parse(fin);
			exportador = tm.darExportador(id_exportador, natural, tipoBuque, inicioD, finD, orden);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(exportador).build();
	}


	/**
	 * Método que expone servicio REST usando PUT que agrega el video que recibe en Json
	 * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/personas/importador
	 * @param exportador - video a agregar
	 * @return Json con el video que agrego o Json con el error que se produjo
	 */
	@PUT
	@Path("/exportador")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addExportador(@QueryParam("user") int idUser, Exportador exportador) {
		VideoAndesMaster tm = new VideoAndesMaster(getPath());
		try {
			tm.addExportador(exportador,idUser);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(exportador).build();
	}

	@PUT
	@Path("/bono/{rut}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response consultarBono(@javax.ws.rs.PathParam("rut") String rut) {
		VideoAndesMaster tm = new VideoAndesMaster(getPath());
		int descuento;
		try {
			descuento = tm.consultarBono(rut);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(descuento).build();
	}
	

}
