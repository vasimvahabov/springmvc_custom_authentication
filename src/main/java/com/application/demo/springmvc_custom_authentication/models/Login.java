package com.application.demo.springmvc_custom_authentication.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class Login {
	
  @Email
  @NotBlank(message="The Email field is required!")
  private String email;
 	
  @NotBlank(message="The Password field is required!")
  private String password;
	
  @NotBlank
  private String token;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getToken() {
    return token;
  }
 
  public void setToken(String token) {
    this.token = token;
  }	
} 
