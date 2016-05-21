package vos;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class Salida {
	//--------------------------
		//ATRIBUTOS
		//--------------------------

    /**
     * Atributo entero que modela la fecha del arribo
     */
    @JsonProperty(value="idMuelle")
    private int idMuelle;
		/**
		 * Atributo entero que modela la fecha del arribo
		 */
		@JsonProperty(value="fecha")
		private Date fecha;
		/**
		 * Atributo entero que modela el buque del arribo
		 */
		@JsonProperty(value="idBuque")
		private int idBuque;

		//--------------------------
		//CONSTRUCTOR
		//--------------------------
		/**
		 * Constructor de la clase abstracta
		 * @param nombre nombre de la persona
		 * @param id id de la persona
		 * @param esNatural boolean diciendo si es persona natural o no
		 */
		public Salida(@JsonProperty(value="idBuque")  int idBuque,
		              @JsonProperty(value="idMuelle") int idMuelle){
			super();
			this.idMuelle = idMuelle;
			this.idBuque=idBuque;
		}
		//--------------------------
		//METODOS
		//--------------------------
		/**
		 * Metodo para obtener el fecha
		 * @return fecha
		 */
		public Date getFecha(){
			return fecha;
		}
		/**
		 * Metodo para modificar el nombre
		 * @param nombre 
		 */
		public void setFecha(Date fecha){
			this.fecha=fecha;
		}
		/**
		 * Metodo para obtener el id del buque
		 * @return id_buque
		 */
		public int getIdBuque(){
			return idBuque;
		}
		/**
		 * Metodo para modificar el nombre
		 * @param nombre 
		 */
		public void setIdBuque(int idBuque){
			this.idBuque=idBuque;
		}
		
		/**
     * Metodo para obtener el id del muelle
     * @return id_buque
     */
    public int getIdMuelle(){
      return idMuelle;
    }
    /**
     * Metodo para modificar el nombre
     * @param nombre 
     */
    public void setIdMuelle(int idMuelle){
      this.idMuelle=idMuelle;
    }
}
