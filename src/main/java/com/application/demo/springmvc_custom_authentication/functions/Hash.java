package com.application.demo.springmvc_custom_authentication.functions;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class Hash {

  // Hash Password Method
  public static String hashPassword(String password){
    return BCrypt.hashpw(password,BCrypt.gensalt());
  }
  // Hash Password Method
	
  // Check / Compare Passwords
  public static boolean verifyPassword(String inputPassword,String hashedPassword){
    if(BCrypt.checkpw(inputPassword,hashedPassword)) 
      return true;
    return false;
  }
  // Check / Compare Passwords
	
}
