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
import vos.ListaMuelles;
import vos.Muelle;

/**
 * Clase que expone servicios REST con ruta base: http://"ip o nombre de host":8080/VideoAndes/rest/videos/...
 * @author Juan
 */
@Path("muelles")
public class MuelleService {

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
  @Path("")
  @Produces({ MediaType.APPLICATION_JSON })
  public Response getMuelles() {
    VideoAndesMaster tm = new VideoAndesMaster(getPath());
    ListaMuelles muelles;
    try {
      muelles = tm.darMuelles();
    } catch (Exception e) {
      System.out.println("ERROR: "+e.getMessage());
      return Response.status(500).entity(doErrorMessage(e)).build();
    }
    return Response.status(200).entity(muelles).build();
  }


    /**
     * Método que expone servicio REST usando PUT que agrega el video que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/personas/importador
     * @param importador - video a agregar
     * @return Json con el video que agrego o Json con el error que se produjo
     */
  @PUT
  @Path("")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addMuelle(@QueryParam("user") int idUser, Muelle muelle) {
    VideoAndesMaster tm = new VideoAndesMaster(getPath());
    try {
      tm.addMuelle(muelle, idUser);
    } catch (Exception e) {
      return Response.status(500).entity(doErrorMessage(e)).build();
    }
    return Response.status(200).entity(muelle).build();
  }


}
