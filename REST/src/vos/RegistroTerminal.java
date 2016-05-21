package vos;

import java.sql.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class RegistroTerminal {
	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="fecha")
	private String fecha;
	
	@JsonProperty(value="id_buque")
	private int idBuque;
	
	@JsonProperty(value="id_terminal")
	private int idTerminal;
	
	@JsonProperty(value="estado_buque")
	private String estadoBuque;
	
	public RegistroTerminal(@JsonProperty(value="id") int id, @JsonProperty(value="fecha") String fecha,
			@JsonProperty(value="id_buque")int idBuque, @JsonProperty(value="id_terminal")int idTerminal,
				@JsonProperty(value="estado_buque")String estadoBuque){
		super();
		this.id= id;
		this.fecha = fecha;
		this.idBuque = idBuque;
		this.idTerminal = idTerminal;
		this.estadoBuque = estadoBuque;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public int getIdBuque() {
		return idBuque;
	}

	public void setIdBuque(int idBuque) {
		this.idBuque = idBuque;
	}

	public int getIdTerminal() {
		return idTerminal;
	}

	public void setIdTerminal(int idTerminal) {
		this.idTerminal = idTerminal;
	}

	public String getEstadoBuque() {
		return estadoBuque;
	}

	public void setEstadoBuque(String estadoBuque) {
		this.estadoBuque = estadoBuque;
	}
	
	
}
