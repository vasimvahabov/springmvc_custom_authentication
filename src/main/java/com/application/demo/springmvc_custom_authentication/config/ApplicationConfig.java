package com.application.demo.springmvc_custom_authentication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport; 
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import com.application.demo.springmvc_custom_authentication.interceptors.AdminAccessInterceptor;
import com.application.demo.springmvc_custom_authentication.interceptors.AutohorizationInterceptor; 

@Configuration
@ComponentScan(basePackages = {"com.application.demo.springmvc_custom_authentication"})
public class ApplicationConfig extends WebMvcConfigurationSupport{

//  @Override
//  protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//    registry.addResourceHandler("css/**","js/**","images/**")
//    .addResourceLocations("classpath:/static/js/","classpath:/static/images/","classpath:/static/css/");
//  }
	
  @Bean
  public SpringResourceTemplateResolver viewResolver(){
		SpringResourceTemplateResolver htmlViewResolver=new SpringResourceTemplateResolver();
    htmlViewResolver.setPrefix("/WEB-INF/html/");
    htmlViewResolver.setSuffix(".html");
    
    return htmlViewResolver;
  }
  
  @Override 
  public void addInterceptors(InterceptorRegistry registry){
		registry.addInterceptor(new AutohorizationInterceptor()).addPathPatterns("/admin/*","/user/*");
		registry.addInterceptor(new AdminAccessInterceptor()).addPathPatterns("/admin/*");
  }		
}
