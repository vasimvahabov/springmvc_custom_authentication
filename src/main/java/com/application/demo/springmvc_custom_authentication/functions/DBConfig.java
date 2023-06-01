package com.application.demo.springmvc_custom_authentication.functions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfig {
  private static final String DB_USERNAME="";
  private static final String DB_PASSWORD="";
  private static final String CONNECTION_STRING="";
 	
  public static Connection getConnection(){
    Connection connection=null;
    try {
      return connection=DriverManager.getConnection(CONNECTION_STRING ,DB_USERNAME,DB_PASSWORD);
    }catch(SQLException error) {
      error.getStackTrace();
    }
    return connection;
  }
}
