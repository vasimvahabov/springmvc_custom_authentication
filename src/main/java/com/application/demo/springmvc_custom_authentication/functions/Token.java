package com.application.demo.springmvc_custom_authentication.functions;

import java.util.UUID;

public class Token {
  public static String generateToken(){
    String randomTokenString=UUID.randomUUID().toString();
    return randomTokenString;
  }
}
