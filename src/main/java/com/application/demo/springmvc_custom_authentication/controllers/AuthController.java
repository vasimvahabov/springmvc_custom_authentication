package com.application.demo.springmvc_custom_authentication.controllers;

import java.sql.SQLException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.application.demo.springmvc_custom_authentication.dao.ApplicationDAO;
import com.application.demo.springmvc_custom_authentication.functions.Hash;
import com.application.demo.springmvc_custom_authentication.functions.Token;
import com.application.demo.springmvc_custom_authentication.models.Login;
import com.application.demo.springmvc_custom_authentication.models.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class AuthController {
	
  @GetMapping("/login")
  public ModelAndView getLogin(){
    String tokenFromController=Token.generateToken();
    ModelAndView loginView=new ModelAndView("login");
    loginView.addObject("PageTitle","Login");
    loginView.addObject("tokenFromController",tokenFromController);
//    System.out.println(tokenFromController);
//    System.out.println("In Login Controller / Page");
		
    return loginView;
  }
	
  @PostMapping("/login")
  public ModelAndView login(@Valid @ModelAttribute("userLogin") Login login,BindingResult result,
                        @RequestParam("email") String email,@RequestParam("password") String password, 
                        @RequestParam("token") String token,HttpSession session) throws SQLException{
    // Check Form Errors
    if(result.hasErrors()){
      ModelAndView loginView=new ModelAndView("login");
      loginView.addObject("PageTitle","Login");
      return loginView;
    }
    // Check Form Errors
    
    // Is Registered Email
    if(!ApplicationDAO.isRegisteredEmail(email)) {
      ModelAndView errorView=new ModelAndView("error");
      errorView.addObject("PageTitle","Error");
      errorView.addObject("notRegistered","There are not registered accounts with "
                                                + "this email address, please contact your administrator!");
      return errorView;
    }
    // Is Registered Email
    
    // CHECK IF THE USER ACCOUNT IS VERIFIED
    if(!ApplicationDAO.isAccountVerified(email)) { 
      ModelAndView errorView=new ModelAndView("error");
      errorView.addObject("PageTitle","Error");
      errorView.addObject("accountNotVerified","This Account is not yet verified, "
                                                + "please check your email and proceed with instruction");
      return errorView;
    }
    // CHECK IF THE USER ACCOUNT IS VERIFIED
    
    // CHECK IF THE ACCOUNT IS ACTIVE
    if(!ApplicationDAO.isAccountActive(email)){
      ModelAndView accountDeActivatedView=new ModelAndView("account_de_activated");
      accountDeActivatedView.addObject("PageTitle","Account De-Activated");
      return accountDeActivatedView;
    }
    // CHECK IF THE ACCOUNT IS ACTIVE
    
    //  Get the hashed password
    String hashedPassword=ApplicationDAO.getStoredHashedUserPassword(email);
    // System.out.println(hashedPassword);
    // System.out.println(password);
    // System.out.println(BCrypt.hashpw(password,BCrypt.gensalt()));
    // System.out.println(BCrypt.hashpw(password,BCrypt.gensalt()).toString().length());
    // Get the hashed password
    
    // Validate User Password
    if(!Hash.verifyPassword(password,hashedPassword)) {
      ModelAndView loginView=new ModelAndView("login");
      loginView.addObject("incorrectDetails","Incorrect Username or Password");
      loginView.addObject("PageTitle","Login");
      return loginView;
    }
    // Validate User Password
    
    // Get The User Type
    String userType=ApplicationDAO.getUserType(email);
    // Get The User Type
   
    // Get User
    User user=ApplicationDAO.getUser(email);
    // Get User
   
    // Set Session Attributes If No Errors
//    System.out.println(user.getFirst_name()+" "+user.getLast_name());
//    System.out.println(token);
    session.setAttribute("SessionToken",token);
    session.setAttribute("user", user);
    // Set Session Attributes If No Errors
   
    // Check User Type and Redirect Users
    switch(userType){
      case "admin":{
        ModelAndView adminView=new ModelAndView("admin/dashboard");
        adminView.addObject("PageTitle","Admin Dashboard");
        return adminView;
      }
      case "user":{
        ModelAndView userView=new ModelAndView("user/dashboard");
        userView.addObject("PageTitle","User Dashboard"); 
        return userView;
      }
      default:{
        ModelAndView errorView=new ModelAndView("error");
        errorView.addObject("loginError","Something went wrong! Contact Administrator!");
        errorView.addObject("PageTitle","Error");
        return errorView;
      }
    }
  }

  @GetMapping("/logout")
  public ModelAndView getLogOut(HttpSession session){
    session.invalidate();
    System.out.println("Session has ended!");
    ModelAndView loginView=new ModelAndView("login");
    loginView.addObject("logoutSuccess","You have Successfully Logged Out");
    return loginView;
  }	
}
