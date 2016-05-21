package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.PuertoAndesMaster;
import vos.ListaExportadorCompleto;
import vos.ListaRegistroBuques;
import vos.ParametroBusqueda;
@Path("exportadores")
public class PuertoAndesExportadoresService {

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
	
	@POST
	@Path("/id_exportador:{idExportador}/consultar_exportador")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({MediaType.APPLICATION_JSON})
	public Response consultarExportador(@javax.ws.rs.PathParam("idUsuario") int idUsuario,
			@javax.ws.rs.PathParam("idExportador") int idExportador,
			ParametroBusqueda pb) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		ListaExportadorCompleto lista;
		try {
			lista = tm.consultarExportador(idExportador, pb);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(lista).build();
	}
}
