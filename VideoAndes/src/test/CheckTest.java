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

public class CheckTest extends DAOTablaGenerica {
  
  private String user;
  private String password;
  private String url;
  private String driver;
  private Connection conn;
  
  public CheckTest() {
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
  public void testCheck() {
    String sql = "INSERT INTO BUQUES VALUES ( 3000, 'X', 'OMG' )";
    
    try {
      PreparedStatement prepStmt = conn.prepareStatement(sql);
      recursos.add(prepStmt);
      prepStmt.executeQuery();
      fail("Debería lanzar excepción");
    } catch (SQLException e) {}
    
    sql = "INSERT INTO CARGA VALUES ( 1000, null, null, null, null, null )";
    
    try {
      PreparedStatement prepStmt = conn.prepareStatement(sql);
      recursos.add(prepStmt);
      prepStmt.executeQuery();
      fail("Debería lanzar excepción");
    } catch (SQLException e) {}
    
    sql = "INSERT INTO MUELLES VALUES ( 1000, null )";
    
    try {
      PreparedStatement prepStmt = conn.prepareStatement(sql);
      recursos.add(prepStmt);
      prepStmt.executeQuery();
      fail("Debería lanzar excepción");
    } catch (SQLException e) {}
    
    sql = "INSERT INTO PRECIOS VALUES ( null, null )";
    
    try {
      PreparedStatement prepStmt = conn.prepareStatement(sql);
      recursos.add(prepStmt);
      prepStmt.executeQuery();
      fail("Debería lanzar excepción");
    } catch (SQLException e) {}
    
    sql = "INSERT INTO TIPOCARGA VALUES ( 1, 'X', null, 2, 2 )";
    
    try {
      PreparedStatement prepStmt = conn.prepareStatement(sql);
      recursos.add(prepStmt);
      prepStmt.executeQuery();
      fail("Debería lanzar excepción");
    } catch (SQLException e) {}
  }
}
