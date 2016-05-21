package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.PuertoAndesMaster;
import vos.Carga;
import vos.ParametroFactura;

@Path("facturas")
public class PuertoAndesFacturasServices {

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
	
	@PUT
	@Path("/id_usuario:{idUsuario}/id_cliente:{idCliente}/generar_factura")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({MediaType.APPLICATION_JSON})
	public Response generarFactura(@javax.ws.rs.PathParam("idUsuario") int idUsuario,
			@javax.ws.rs.PathParam("idCliente") int idCliente,
			ParametroFactura param) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.generarFactura(idUsuario, idCliente, param.getId_buque(), param.getFecha());
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(param).build();
	}
}
