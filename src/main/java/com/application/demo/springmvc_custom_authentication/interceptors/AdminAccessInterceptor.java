package com.application.demo.springmvc_custom_authentication.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.application.demo.springmvc_custom_authentication.models.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AdminAccessInterceptor implements HandlerInterceptor{
	
  @Override
  public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws Exception{
    System.out.println("In Pre Handle Admin Access Interceptor!");
    
    if(request.getRequestURI().startsWith("/admin")){
      HttpSession session=request.getSession();
      User user=(User)session.getAttribute("user");
      
      if(!user.getUser_type().equals("admin")){
        response.sendRedirect("/user/dashboard");
        return false;
      }
    }
    return true;
  }
	
  @Override
  public void postHandle(HttpServletRequest request,HttpServletResponse response,
                             Object handler,ModelAndView modelAndView) throws Exception{
    System.out.println("In Post Handle Admin Access Interceptor!");
  }
	
  @Override
  public void afterCompletion(HttpServletRequest request,
             HttpServletResponse response,Object handler,Exception ex) throws Exception{
    System.out.println("In After Completion Admin Access Interceptor!");
  }
}
