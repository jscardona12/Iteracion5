package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.PuertoAndesMaster;
import vos.AreaAlmacenamiento;
import vos.Buque;
import vos.Carga;
import vos.ListaConsultaAreas;
import vos.ListaMovimientoCargas;
import vos.ListaRegistroBuques;
import vos.ParametroBusqueda;
import vos.RegistroBuque;

@Path("almacenamiento")
public class PuertoAndesAlmacenamientoServices {

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

	@POST
	@Path("deshabilitar_area/{tipo}")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public Response descargarBuque(@javax.ws.rs.PathParam("tipo") String tipo,AreaAlmacenamiento a){
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		List<Carga> huerfanas;
		System.out.println("EL ID DEL USUARIO ES : "+a.getUser_id() + " " + tipo);
		try {
			huerfanas = tm.deshabilitarAreaMant(tipo, a);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(huerfanas).build();
	}
	
	@POST
	@Path("/consultar_areas/{idUsuario}")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({MediaType.APPLICATION_JSON})
	public Response consultarIOBuques(@javax.ws.rs.PathParam("idUsuario") int idUsuario,ParametroBusqueda pb) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		ListaConsultaAreas lca;
		try {
			lca = tm.consultarAreas(idUsuario,pb);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(lca).build();
	}
}
