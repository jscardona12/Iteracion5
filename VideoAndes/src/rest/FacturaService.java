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
import vos.Factura;
import vos.ListaTipoCarga;
import vos.TipoCarga;

/**
 * Clase que expone servicios REST con ruta base: http://"ip o nombre de host":8080/VideoAndes/rest/videos/...
 * @author Juan
 */
@Path("factura")
public class FacturaService {

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
	@Path("getFacturas")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getTipoCargas() {
		VideoAndesMaster tm = new VideoAndesMaster(getPath());
		ListaTipoCarga tipoCargas;
		try {
			tipoCargas = tm.darTiposCarga();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(tipoCargas).build();
	}
	
	@PUT
  @Path("generarFactura")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response generarFactura(Factura factura, @QueryParam("user") int idUser) {
    VideoAndesMaster tm = new VideoAndesMaster(getPath());
    try {
      tm.generarFactura(factura, idUser);
    } catch (Exception e) {
      return Response.status(500).entity(doErrorMessage(e)).build();
    }
    return Response.status(200).entity(factura).build();
  }

}
