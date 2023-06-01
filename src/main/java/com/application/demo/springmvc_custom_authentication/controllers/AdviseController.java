package com.application.demo.springmvc_custom_authentication.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.application.demo.springmvc_custom_authentication.models.Login;
import com.application.demo.springmvc_custom_authentication.models.User;

@ControllerAdvice
public class AdviseController {
  
  @ModelAttribute("registerUser")
  public User getUserDefaults(){
    return new User();
  }
  	
  @ModelAttribute("userLogin")
  public Login getLoginDefaults(){
    return new Login();
  }
	
  @ModelAttribute("user_types")
  public List<String> getUserTypes(){
    List<String> userTypes=new ArrayList<>();
    userTypes.add("admin");
    userTypes.add("user");
    return userTypes;
  }
}
