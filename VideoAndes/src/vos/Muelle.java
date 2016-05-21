package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Muelle {

  //--------------------------
  //ATRIBUTOS
  //--------------------------

  /**
   * Atributo entero que modela el buque del arribo
   */
  @JsonProperty(value="id")
  private int id;
  
  /**
   * Atributo entero que modela el buque del arribo
   */
  @JsonProperty(value="nombre")
  private String nombre;

  //--------------------------
  //CONSTRUCTOR
  //--------------------------
  
  /**
   * Constructor de la clase abstracta
   * @param nombre nombre de la persona
   * @param id id de la persona
   * @param esNatural boolean diciendo si es persona natural o no
   */
  public Muelle(@JsonProperty(value="id")     int id,
                @JsonProperty(value="nombre") String nombre) {
    super();
    this.id=id;
    this.nombre=nombre;
  }
  //--------------------------
  //METODOS
  //--------------------------
  /**
   * Metodo para obtener el ID
   * @return fecha
   */
  public int getId(){
    return id;
  }
  /**
   * Metodo para modificar el nombre
   * @param nombre 
   */
  public void setId(int id){
    this.id=id;
  }
  /**
   * Metodo para obtener el nombre 
   * @return nombre
   */
  public String getNombre(){
    return nombre;
  }
  /**
   * Metodo para modificar el nombre
   * @param nombre 
   */
  public void setNombre(String nombre){
    this.nombre=nombre;
  }
}
