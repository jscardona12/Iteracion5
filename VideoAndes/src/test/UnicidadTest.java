package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import org.junit.Test;

import dao.DAOTablaGenerica;
import vos.Salida;

public class UnicidadTest extends DAOTablaGenerica {
  
  private String user;
  private String password;
  private String url;
  private String driver;
  private Connection conn;

  
  
  public UnicidadTest() {
    try {
    this.url = "jdbc:oracle:thin:@fn3.oracle.virtual.uniandes.edu.co:1521:prod";
    this.user = "ISIS2304B061610";
    this.password = "pzvWms5XesatHU3U";
    this.driver = "oracle.jdbc.driver.OracleDriver";
    Class.forName(driver);
    
//    System.out.println("Connecting to: " + url + " With user: " + user);
    conn = DriverManager.getConnection(url, user, password);
    
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  @Test
  public void testUnicidad() {
   
    probarConID1000("ALMACEN", "1000");
    probarConID1000("BUQUES", "1000, 'X', 'X', 'X', 'X', 'MULTI_PROPOSITO'");
    probarConID1000("CARGA", "1000, 'X', null, 0, 0, null");
    probarConID1000("MUELLES", "1000, 'X'");
    probarConID1000("PERSONAS", "1000, 1, 'X'");
    probarConID1000("PUERTOEXTERNO", "1000, 'X', 'X', 'X'");
    probarConID1000("ROLUSUARIO", "1000, 'X'");
    probarConID1000("USUARIOS", "1000, 1, 'X'");
    
  }
  
  public void probarConID1000(String tabla, String values) {
    String sql = "INSERT INTO " + tabla + " VALUES (" + values + ")";
    
    try {
      PreparedStatement prepStmt = conn.prepareStatement(sql);
      recursos.add(prepStmt);
      prepStmt.executeQuery();
    } catch (SQLException e) {
      e.printStackTrace();
      fail("No debería lanzar excepción");
    }
    
    try {
      PreparedStatement prepStmt = conn.prepareStatement(sql);
      recursos.add(prepStmt);
      prepStmt.executeQuery();
      fail("Debería lanzar excepción");
    } catch (SQLException e) {}
    
    String sqlBorrar = "DELETE FROM " + tabla + " WHERE ID=1000";
    
    try {
      PreparedStatement prepStmt = conn.prepareStatement(sqlBorrar);
      recursos.add(prepStmt);
      prepStmt.executeQuery();
      assertTrue(true);
    } catch (SQLException e) {
      e.printStackTrace();
      fail("No debería lanzar excepción");
    }
  }

}
