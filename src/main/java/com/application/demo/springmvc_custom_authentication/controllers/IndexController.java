package com.application.demo.springmvc_custom_authentication.controllers;

import java.sql.SQLException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.application.demo.springmvc_custom_authentication.dao.ApplicationDAO;
import com.application.demo.springmvc_custom_authentication.functions.HTML;
import com.application.demo.springmvc_custom_authentication.functions.Hash;
import com.application.demo.springmvc_custom_authentication.functions.Token;
import com.application.demo.springmvc_custom_authentication.messenger.MailMessenger;

@Controller
public class IndexController {
	
  @GetMapping("/")
  public ModelAndView getIndex(){
    ModelAndView indexView=new ModelAndView("index");
    indexView.addObject("PageTitle","Home");
    System.out.println("In Index Controller / Page");
    return indexView;
  }
  
//  @GetMapping("/error")
//  public ModelAndView getError() {
//  	ModelAndView errorView=new ModelAndView("error");
//  	errorView.addObject("Page","Error");
//  	System.out.println("In Error Controller / Page");
//  	return errorView;
//  }
  
  @GetMapping("/account_de_activated")
  public ModelAndView getAccountDeActivated(){
    ModelAndView accountDeActivatedView=new ModelAndView("account_de_activated");
    accountDeActivatedView.addObject("PageTitle","Account De-Activated");
    System.out.println("In Account De-Activated Controller / Page");
    return accountDeActivatedView;  	
  }
  
  @PostMapping("/account_de_activated")
  public ModelAndView accountReActivate(@RequestParam("email") String email) throws SQLException{ 
    System.out.println("In Account De-Activated Post Controller / Page");
    // Generate token 	
    String token=Token.generateToken();
    // Generate token
  	
    // Set Verification Token
    if(!ApplicationDAO.setVerificationToken(email,token)){
      ModelAndView errorView=new ModelAndView("error");
      errorView.addObject("baseError","Something went wrong please contact Administrator");	
      return errorView;
    }
    // Set Verification Token
    
    // Send User Notification
    try {
      String emailBody=HTML.emailHtmlBody(email,token);
      MailMessenger.htmlEmailMessenger("",email,"Activate your account", emailBody);
    }catch (Exception error) {
      error.printStackTrace();
      System.out.println("Failed to send email!!!");
    }   
    // Send User Notification
  	
    ModelAndView successView=new ModelAndView("success");
    successView.addObject("PageTitle","Success");
    successView.addObject("success","Form submitted successfully!");
    return successView;
  }
  
  @GetMapping("/verify_account")
  public ModelAndView VerifyAccount(@RequestParam("email") String email,
                                    @RequestParam("verification_token") String verification_token) throws SQLException{
    	
    System.out.println("In Verify Account GET Controller / Page");
  	
    if(!ApplicationDAO.verifyToken(email,verification_token)){
      ModelAndView errorView=new ModelAndView("error");
//      errorView.addObject("PageTitle","Error");
      errorView.addObject("sessionExpired","This session has expired!!!");
      return errorView;
    }

    ModelAndView verifyAccountView=new ModelAndView("account_verification");
    verifyAccountView.addObject("PageTitle","Verify Account");
    System.out.println("In Verify Account Controller / Page");
    return verifyAccountView;
  }
  
  @PostMapping("/verify_account")
  public ModelAndView VerifyAccount(@RequestParam("verification_token") String token,
                                 @RequestParam("email") String email,@RequestParam("password") String password,
                                 @RequestParam("confirm_password") String confirm_password) throws SQLException{
  	
//   System.out.println(email);
//   System.out.println(token);
//   System.out.println(password);
//   System.out.println(confirm_password);
    System.out.println("In Verify Account POST Controller / Page");
  	
    //Check if passwords match
    if(!password.equals(confirm_password)){
      ModelAndView verifyAccountView=new ModelAndView("account_verification");
      verifyAccountView.addObject("PageTitle","Verify Account");
      verifyAccountView.addObject("passwordsDontMatch","Passwords do not match!!!");
      System.out.println("In Verify Account Controller / Page");
      return verifyAccountView;
    }
  	
    // Hash Password
    String hashedPassword=Hash.hashPassword(password);
    // Hash Password
  	
    if(!ApplicationDAO.verifyAccount(email,hashedPassword, token)){
      ModelAndView errorView=new ModelAndView("error");
      errorView.addObject("PageTitle","Error");
      errorView.addObject("verifyError","Error during verifying account,please contact your administrator!");
      return errorView;
    }
  	
    ModelAndView successView=new ModelAndView("success");
    successView.addObject("PageTitle","Success");
    successView.addObject("success","Account successfully verified, please proceed to login!!!");
    System.out.println("In Verify Account Controller / Page");
    return successView;
  }
}
