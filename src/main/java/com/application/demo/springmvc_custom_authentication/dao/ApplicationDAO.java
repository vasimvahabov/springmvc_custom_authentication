package com.application.demo.springmvc_custom_authentication.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.application.demo.springmvc_custom_authentication.functions.DBConfig;
import com.application.demo.springmvc_custom_authentication.models.User;

public class ApplicationDAO {
	
  // Register User Method	
  public static boolean registerUser(String firstName,String lastName,
    String password,String email,String userType,String verificationToken) throws SQLException{
    Connection connection=null;
    String registerUserQuery="insert into users(first_name,last_name,email,password,user_type,"
                                        +"verification_token) values(?,?,?,?,?,?);";
  		
    try{			
      connection=DBConfig.getConnection();
      PreparedStatement statement=connection.prepareStatement(registerUserQuery);
	 		
      statement.setString(1,firstName);
      statement.setString(2,lastName); 
      statement.setString(3,email);
      statement.setString(4,password);
      statement.setString(5,userType);
      statement.setString(6,verificationToken);
			
      int affectedRows=statement.executeUpdate();
      if(affectedRows==1) 
        return true;
    }catch(Exception error){
      error.printStackTrace();
      System.out.println("Failed to register User!");
    }finally{
      if(connection!=null){
        connection.close();
        System.out.println("Closing DB Connection!");
      }
    }
    
    return false;
  }
  // Register User Method	
	
  public static boolean isRegisteredEmail(String email) throws SQLException{
    Connection connection=null;
    ResultSet resultSet=null;
    String isRegisteredEmailQuery="select email from users where email=?;";
    
    try {
      connection=DBConfig.getConnection();
      PreparedStatement statement=connection.prepareStatement(isRegisteredEmailQuery);
      statement.setString(1, email);
      resultSet=statement.executeQuery();
      while(resultSet.next()) {
        return true;
      }
    }catch(Exception error){
      error.printStackTrace();
    }finally {
      if(resultSet!=null) {
        resultSet.close();
      }
      if(connection!=null) {
        connection.close();
        System.out.println("Closing DB Connection!");
      }
    }
    
    return false;		
  }
	
  public static String getStoredHashedUserPassword(String email) throws SQLException{
    Connection connection=null;
    ResultSet resultSet=null;
    String hashedPassword=null;
    String getHashedPasswordQuery="select password from users where email=?;";
    
    try{
      connection=DBConfig.getConnection();
      PreparedStatement statement=connection.prepareStatement(getHashedPasswordQuery);
      statement.setString(1,email);
      resultSet=statement.executeQuery();
      while(resultSet.next()) {
        hashedPassword=resultSet.getString("password");
      }
    }catch(Exception error){
      error.printStackTrace();
    }finally{
      if(resultSet!=null) {
        resultSet.close();
      }
      if(connection!=null) {
        connection.close();
        System.out.println("Closing DB Connection!");
      }
    }
		
    return hashedPassword; 
  }
	
  public static String getUserType(String email) throws SQLException{
    Connection connection=null;
    ResultSet resultSet=null;
    String userType=null;
    String getUseTypeQuery="select user_type from users where email=?;";
    		
    try {
      connection=DBConfig.getConnection();
      PreparedStatement statement=connection.prepareStatement(getUseTypeQuery);
      statement.setString(1,email);
      resultSet=statement.executeQuery();
      while(resultSet.next()) { 
        userType=resultSet.getString("user_type");
      }
    }catch(Exception error) {
      error.printStackTrace();
    }finally {
      if(resultSet!=null) {
        resultSet.close();  
      }
      if(connection!=null) {
        connection.close();
        System.out.println("Closing DB Connection!");
      }
    }
			
    return userType;
  }
	
  public static User getUser(String email) throws SQLException{
    Connection connection=null;
    User user=null;
    ResultSet resultSet=null;
    String getUserQuery="select * from users where email=?;";
    
    try {
      connection=DBConfig.getConnection();
      PreparedStatement statement=connection.prepareStatement(getUserQuery);
      statement.setString(1,email);
      resultSet=statement.executeQuery();
      while(resultSet.next()){
        user=new User();
        user.setFirst_name(resultSet.getString("first_name"));
        user.setLast_name(resultSet.getString("last_name"));
        user.setPassword(resultSet.getString("email"));
        user.setEmail(resultSet.getString("email"));     
        user.setUser_type(resultSet.getString("user_type"));
        user.setCreated_at(resultSet.getDate("created_at"));
      }
    }catch(Exception error){
      error.printStackTrace();
    }finally{
      if(resultSet!=null) {
        resultSet.close();
      }
      if(connection!=null) {
        connection.close();
        System.out.println("Closing DB Connection!");
      }
    }
		
    return user;
  }
	
  public static boolean isAccountVerified(String email) throws SQLException{
    Connection connection=null; 
    ResultSet resultSet=null; 
    String isAccountVerifiedQuery="select verified from users where email=?;";
    
    try {
      connection=DBConfig.getConnection();
      PreparedStatement statement=connection.prepareStatement(isAccountVerifiedQuery);
      statement.setString(1,email);
      resultSet=statement.executeQuery();
      while(resultSet.next()){
       if(resultSet.getInt("verified")==1)
         return true;
      }
    }catch(Exception error){
      error.printStackTrace();
    }finally{
      if(resultSet!=null) {
        resultSet.close();
      }
      if(connection!=null) {
        connection.close();
        System.out.println("Closing DB Connection!");
      }
    }
    
    return false;
  }
	
  public static boolean isAccountActive(String email) throws SQLException{
    Connection connection=null; 
    ResultSet resultSet=null;
    String isAccountActiveQuery="select active from users where email=?;";
    try {
      connection=DBConfig.getConnection();
      PreparedStatement statement=connection.prepareStatement(isAccountActiveQuery);
      statement.setString(1,email);
      resultSet=statement.executeQuery();
      while(resultSet.next()){
        if(resultSet.getInt("active")==1)
          return true;
      }
    }catch(Exception error){
      error.printStackTrace();
    }finally{
      if(resultSet!=null) {
        resultSet.close();
      }
      if(connection!=null) {
        connection.close();
        System.out.println("Closing DB Connection!");
      }
    }
    
    return false;
  }
	
  public static boolean verifyAccount(String email,String password,String verification_token) throws SQLException{
    Connection connection=null;  
    String verifyAccountQuery="update users set password=?,verification_token=null,active=1,verified=1,verified_on=now() "
				+ "where verification_token=? and email=?;";

    try {
      connection=DBConfig.getConnection();
      PreparedStatement statement=connection.prepareStatement(verifyAccountQuery);
      statement.setString(1,password);
      statement.setString(2,verification_token);
      statement.setString(3,email);
      int affectedRows=statement.executeUpdate();
      if(affectedRows==1) 
        return true;
    }catch(Exception error){
      error.printStackTrace();			
      System.out.println("Failed to Verify / Update Account");
    }finally{
      if(connection!=null) {
        connection.close();
        System.out.println("Closing DB Connection!");
      }
    }
    
    return false;
  }

  public static boolean getVerificationToken(String email,String verification_token) throws SQLException{
    Connection connection=null;  
    ResultSet resultSet=null;
    String getVerificationTokenQuery="select verification_token from users where email=? and verification_token=?;";
    
    try {
      connection=DBConfig.getConnection();
      PreparedStatement statement=connection.prepareStatement(getVerificationTokenQuery);
      
      statement.setString(1,email);
      statement.setString(2,verification_token);
      resultSet=statement.executeQuery();
      while(resultSet.next()){
        return true;
      }
    }catch(Exception error){
      error.printStackTrace();
      System.out.println("Failed to Verify / Update Account");
    }finally{
      if(resultSet!=null) {
        resultSet.close();
      }
      if(connection!=null) {
        connection.close();
        System.out.println("Closing DB Connection!");
      }
    }
    return false;
  }

  public static boolean setVerificationToken(String email,String verification_token) throws SQLException{
    Connection connection=null;   
    String setVerificationTokenQuery="update users set verification_token=? where email=?;";

    try {
      connection=DBConfig.getConnection();
      PreparedStatement statement=connection.prepareStatement(setVerificationTokenQuery);
      
      statement.setString(1,verification_token);		
      statement.setString(2,email); 			
      int affectedRows=statement.executeUpdate();
      if(affectedRows==1) 
        return true;
    }catch(Exception error){
      error.printStackTrace(); 
    }finally{
      if(connection!=null) {
        connection.close();
        System.out.println("Closing DB Connection!");
      }
    }
    return false;
  }

  public static boolean verifyToken(String email,String verification_token) throws SQLException{
    Connection connection=null; 
    ResultSet resultSet=null;
    String tokenVerifyQuery="select verification_token from users where email=? and verification_token=?;";
    
    try {
      connection=DBConfig.getConnection();
      PreparedStatement statement=connection.prepareStatement(tokenVerifyQuery);
        
      statement.setString(1,email);
      statement.setString(2,verification_token);
      resultSet=statement.executeQuery();
      while(resultSet.next()){ 
        return true;
      }
    }catch(Exception error){
      error.printStackTrace();
    }finally{
      if(resultSet!=null) {
        resultSet.close();
      }
      if(connection!=null) {
        connection.close();
        System.out.println("Closing DB Connection!");
      }
    }
    return false;
  }	
}
