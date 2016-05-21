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
import vos.Carga;
import vos.ListaArribos;
import vos.ListaArribosYSalidas;
import vos.ListaBuques;
import vos.ListaExportadores;
import vos.ListaMovimientoCarga2;
import vos.MovimientoCarga;

/**
 * Clase que expone servicios REST con ruta base: http://"ip o nombre de host":8080/VideoAndes/rest/videos/...
 * @author Juan
 */
@Path("cargas")
public class CargaService {

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
	 * Método que expone servicio REST usando PUT para recoger una carga de un área de almacenamiento
	 * @return
	 */
	@PUT
	@Path("recogerCarga/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response recogerCarga(@QueryParam("user") int idUser, Carga carga) {
		VideoAndesMaster tm = new VideoAndesMaster(getPath());
		ListaBuques videos;
		try {
			tm.recogerCarga(carga, idUser);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(carga).build();
	}


	// RFC5
	@GET
	@Path("movimientosCarga")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response darMovimientosCarga(@QueryParam("persona") int idPersona,
			@QueryParam("fechaInicio") String inicio,
			@QueryParam("fechaFin") String fin)
	{
		VideoAndesMaster tm = new VideoAndesMaster(getPath());
		List<MovimientoCarga> lista;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date inicioD = (inicio == null) ? null : sdf.parse(inicio);
			Date finD = (fin == null) ? null : sdf.parse(fin);

			lista = tm.darMovimientosCarga(idPersona, inicioD, finD);
		} catch (Exception e) {
			e.printStackTrace();
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
	@Path("movimientosCarga2")
	@Produces(MediaType.APPLICATION_JSON)
	public Response darMovimientosCargaExportadorConValorMayorA(@QueryParam(value = "valor") int valor,
			@QueryParam(value = "user") Integer usuario,
			@QueryParam(value = "tipo_carga") String tipoCarga
			) {

		VideoAndesMaster tm = new VideoAndesMaster(getPath());
		ListaMovimientoCarga2 lista;
		try {
			lista = tm.getMovimientosCargaExpConValorMayorA(usuario,valor,tipoCarga);
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
	@Path("rf14")
	@Produces(MediaType.APPLICATION_JSON)
	public Response darMovimientosCargaExportadorConValorMayorA() {

		VideoAndesMaster tm = new VideoAndesMaster(getPath());
		tm.iniciarRF14();

		return Response.status(200).entity("").build();
	}

}
