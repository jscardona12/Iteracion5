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

public class IntegridadTest extends DAOTablaGenerica {
  
  private String user;
  private String password;
  private String url;
  private String driver;
  private Connection conn;
  
  public IntegridadTest() {
    try {
    this.url = "jdbc:oracle:thin:@fn3.oracle.virtual.uniandes.edu.co:1521:prod";
    this.user = "ISIS2304B061610";
    this.password = "pzvWms5XesatHU3U";
    this.driver = "oracle.jdbc.driver.OracleDriver";
    Class.forName(driver);
    
    conn = DriverManager.getConnection(url, user, password);
    
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  @Test
  public void testIntegridad() {
   
    probarTablaFKFalla("BODEGAS", "2000, 0, 0, 0, 0");
    probarTablaFKFalla("ARRIBOS", "2000, null, 2000, 2000");
    probarTablaFKFalla("CARGAENALMACEN", "2000, 2000");
    probarTablaFKFalla("COBERTIZOS", "2000, 0,0");
    probarTablaFKFalla("CARGAENBUQUE", "2000, 2000");
    probarTablaFKFalla("EXPORTADORES", "2000, 'X'");
    probarTablaFKFalla("IMPORTADORES", "2000, 'X', 0");
    
    probarTablaFKExito("ALMACEN", "2000", "BODEGAS", "2000, 0, 0, 0, 0");
    probarTablaFKExito("ALMACEN", "2000", "COBERTIZOS", "2000, 0,0");
    probarTablaFKExito("PERSONAS", "2000, 1, 'X'", "EXPORTADORES", "2000, 'X'");
    probarTablaFKExito("PERSONAS", "2000, 1, 'X'", "IMPORTADORES", "2000, 'X', 0");
    
  }
  
  public void probarTablaFKFalla(String tabla1, String values1) {
    String sql = "INSERT INTO " + tabla1 + " VALUES (" + values1 + ")";
   
    try {
      PreparedStatement prepStmt = conn.prepareStatement(sql);
      recursos.add(prepStmt);
      prepStmt.executeQuery();
      fail("Debería lanzar excepción");
    } catch (SQLException e) {}
    
  }
  
  public void probarTablaFKExito(String tabla1, String values1, String tabla2, String values2) {
    String sql = "INSERT INTO " + tabla1 + " VALUES (" + values1 + ")";
   
    try {
      PreparedStatement prepStmt = conn.prepareStatement(sql);
      recursos.add(prepStmt);
      prepStmt.executeQuery();
    } catch (SQLException e) {
      fail("No debería lanzar excepción");
    }
    
    sql = "INSERT INTO " + tabla2 + " VALUES (" + values2 + ")";
    
    try {
      PreparedStatement prepStmt = conn.prepareStatement(sql);
      recursos.add(prepStmt);
      prepStmt.executeQuery();
    } catch (SQLException e) {
      fail("No debería lanzar excepción");
    }
    
    String sqlBorrar = "DELETE FROM " + tabla2 + " WHERE ID=2000";
    
    try {
      PreparedStatement prepStmt = conn.prepareStatement(sqlBorrar);
      recursos.add(prepStmt);
      prepStmt.executeQuery();
      assertTrue(true);
    } catch (SQLException e) {
      e.printStackTrace();
      fail("No debería lanzar excepción");
    }
    
    sqlBorrar = "DELETE FROM " + tabla1 + " WHERE ID=2000";
    
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
