package com.application.demo.springmvc_custom_authentication.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {
	
  @GetMapping("/dashboard")
  public ModelAndView getUserDashBoard(){
    ModelAndView userDashboardView=new ModelAndView("user/dashboard");
    userDashboardView.addObject("PageTitle","User Dashboard");
    System.out.println("In User Dashboard Controller / Page");
    return userDashboardView;
  }
}
