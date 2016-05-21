package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Importador {
	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="registro_aduana")
	private String registro_aduana;
	
	@JsonProperty(value="habitual")
	private int habitual;
	
	@JsonProperty(value="juridico")
	private int juridico;

	public Importador(@JsonProperty(value="id") int id,@JsonProperty(value="registro_aduana") String registro_aduana,@JsonProperty(value="habitual") int habitual,@JsonProperty(value="juridico") int juridico) {
		super();
		this.id = id;
		this.registro_aduana = registro_aduana;
		this.habitual = habitual;
		this.juridico = juridico;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRegistro_aduana() {
		return registro_aduana;
	}

	public void setRegistro_aduana(String registro_aduana) {
		this.registro_aduana = registro_aduana;
	}

	public int getHabitual() {
		return habitual;
	}

	public void setHabitual(int habitual) {
		this.habitual = habitual;
	}

	public int getJuridico() {
		return juridico;
	}

	public void setJuridico(int juridico) {
		this.juridico = juridico;
	}
	
	
}
