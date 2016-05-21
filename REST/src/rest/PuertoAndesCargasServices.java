package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.PuertoAndesMaster;
import vos.Buque;
import vos.Carga;
import vos.ListaMovimientoCargas;
import vos.ListaRegistroBuques;
import vos.ParametroBusqueda;
import vos.RegistroTerminal;

@Path("cargas")
public class PuertoAndesCargasServices {

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
	@Path("/consultar_movimientos/{idUsuario}")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({MediaType.APPLICATION_JSON})
	public Response consultarIOBuques(@javax.ws.rs.PathParam("idUsuario") int idUsuario,ParametroBusqueda pb) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		ListaMovimientoCargas lmc;
		try {
			lmc = tm.consultarMovimientoCargas(idUsuario,pb);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(lmc).build();
	}
	
	@PUT
	@Path("/id_usuario:{idUsuario}/registrar_carga_de_buque")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({MediaType.APPLICATION_JSON})
	public Response registrarCargaBuque(Carga c,
			@javax.ws.rs.PathParam("idUsuario") int idUsuario) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.addTipoCarga(c, idUsuario);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(c).build();
	}
	
	@POST
	@Path("/id_usuario:{idUsuario}/registrar_entrega_carga")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({MediaType.APPLICATION_JSON})
	public Response registrarEntregaCarga(Carga c,
			@javax.ws.rs.PathParam("idUsuario") int idUsuario){
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.registrarEntregaCarga(c, idUsuario);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(c).build();
	}

}
