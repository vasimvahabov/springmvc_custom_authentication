package com.application.demo.springmvc_custom_authentication.controllers;

import java.sql.SQLException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.application.demo.springmvc_custom_authentication.dao.ApplicationDAO;
import com.application.demo.springmvc_custom_authentication.functions.HTML;
import com.application.demo.springmvc_custom_authentication.functions.Hash;
import com.application.demo.springmvc_custom_authentication.functions.Token;
import com.application.demo.springmvc_custom_authentication.messenger.MailMessenger;
import com.application.demo.springmvc_custom_authentication.models.User;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
  
  @GetMapping("/dashboard")
  public ModelAndView getAdminDashBoard(){
    ModelAndView adminDashBoardView=new ModelAndView("admin/dashboard");
    adminDashBoardView.addObject("PageTitle","Admin Dashboard");
    System.out.println("In Admin Dashboard Controller / Page");
    return adminDashBoardView;
  }
  
  @GetMapping("/add_user")
  public ModelAndView getAddUser(){
    ModelAndView addUserView=new ModelAndView("admin/add_user");
    addUserView.addObject("PageTitle","Add User");
    System.out.println("In Admin Add User Controller / Page");
    return addUserView;
  }
	
  @PostMapping("/add_user")
  public ModelAndView addUser(@Valid @ModelAttribute("registerUser") User user,BindingResult result,
                              @RequestParam("first_name") String first_name,@RequestParam("last_name") String last_name,
                              @RequestParam("email") String email,@RequestParam("password") String password,
			@RequestParam("confirm_password") String confirm_password,
			@RequestParam("user_type") String user_type) throws SQLException {
  
    ModelAndView addUserView=new ModelAndView("admin/add_user");
    addUserView.addObject("PageTitle","Add User");
    // Check For Errors
    if(result.hasErrors()){ 
      return addUserView;
    }		
    // Check For Errors

    // Check If Password Match
     if(!password.equals(confirm_password)){
       addUserView.addObject("passwordsDontMatch","Passwords do not match!!!");
       return addUserView;
     }
     // Check If Password Match

    // Generate Verification Token
    String verification_token=Token.generateToken(); 
    // Generate Verification Token

    // Hash Password
    String hashedPassword=Hash.hashPassword(password);
    // Hash Password

    // Register User
    if(ApplicationDAO.registerUser(first_name, last_name,
                  hashedPassword, email, user_type,verification_token)){
      addUserView.addObject("RegistrationSuccess","User Added Successfully!");

      try {
        String htmlEmailBody=HTML.emailHtmlBody(email, verification_token);
        MailMessenger.htmlEmailMessenger("",email,"",htmlEmailBody);
      }catch(Exception error){
        error.printStackTrace();
        System.out.println("Failed to send email!!!");
      }
      return addUserView;
    }
    // Register User

    addUserView.addObject("RegistrationError","Something went wrong! Contact Administrator!");
    return addUserView;
  }	
}
