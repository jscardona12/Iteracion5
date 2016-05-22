package rest;

import java.util.List;

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
import vos.ListaConsultaBuques;
import vos.ListaRegistroBuques;
import vos.ParametroBusqueda;
import vos.RegistroBuque;
import vos.RegistroTerminal;

@Path("buques")
public class PuertoAndesBuquesServices {

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
	@Path("/consultar_io_buques")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({MediaType.APPLICATION_JSON})
	public Response consultarIOBuques(ParametroBusqueda pb) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		ListaConsultaBuques lrb;
		try {
			lrb = tm.consultarIOBuques(pb);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(lrb).build();
	}
	
	@GET
	@Path("/id/{id}/")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getBuque(@javax.ws.rs.PathParam("id") int idBuque) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		Buque b;
		try {
			b = tm.getBuque(idBuque);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(b).build();
	}
	
	@POST
	@Path("cargar_buque")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public Response cargarBuque(RegistroBuque rb){
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.cargarBuque(rb);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(rb).build();
	}
	
	@POST
	@Path("descargar_buque")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public Response descargarBuque(RegistroBuque rb){
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.descargarBuque(rb);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(rb).build();
	}
	
	@PUT
	@Path("rf14")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public Response rf14(RegistroBuque rb){
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.iniciarRF14(rb);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity("Done").build();
	}
	
	@POST
	@Path("deshabilitar_buque/{tipo}")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public Response descargarBuque(@javax.ws.rs.PathParam("tipo") String tipo,Buque b){
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		List<Carga> huerfanas;
		try {
			huerfanas = tm.deshabilitarBuque(tipo, b);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(huerfanas).build();
	}
	
	@POST
	@Path("/id_buque:{idBuque}/id_usuario:{idUsuario}/registrar_salida")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response registrarSalidaBuque(@javax.ws.rs.PathParam("idBuque") int idBuque,
			@javax.ws.rs.PathParam("idUsuario") int idUsuario) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.registrarSalidaBuque(idBuque, idUsuario);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(idBuque).build();
	}
}
