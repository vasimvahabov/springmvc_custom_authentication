package com.application.demo.springmvc_custom_authentication.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AutohorizationInterceptor implements HandlerInterceptor{
		
  @Override
  public boolean preHandle(HttpServletRequest request,
                                HttpServletResponse response,Object handler) throws Exception{
    System.out.println("In Pre Handle Interceptor!");
    if(request.getRequestURI().startsWith("/admin") || request.getRequestURI().startsWith("/user")) {
      HttpSession session=request.getSession();    	 
      if(session.getAttribute("SessionToken")==null && session.getAttribute("user")==null){
        response.sendRedirect("/login");
        return false;
      }
   }
   return true;
  }
  
  @Override
  public void postHandle(HttpServletRequest request,HttpServletResponse response,
                         Object handler,ModelAndView modelAndView) throws Exception{
    System.out.println("In Post Handle Interceptor!");
  }
  
  @Override
  public void afterCompletion(HttpServletRequest request,
         HttpServletResponse response,Object handler,Exception ex) throws Exception{
    System.out.println("In After Completion Interceptor!");
  }	
}
