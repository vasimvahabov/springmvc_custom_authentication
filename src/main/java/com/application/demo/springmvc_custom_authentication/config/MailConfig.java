package com.application.demo.springmvc_custom_authentication.config;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class MailConfig {
  @Bean
  public static JavaMailSenderImpl getMailConfig(){
    JavaMailSenderImpl mailSender=new JavaMailSenderImpl();
    Properties properties=mailSender.getJavaMailProperties();
    
    properties.put("mail.transport.protocol","smtp");
    properties.put("mail.smtp.auth","true");
    properties.put("mail.smtp.starttls.enable","true");
    properties.put("mail.smtp.ssl.enable","true"); 
    properties.put("mail.debug","true");

    // If only TLS is enabled      =>  port=587
    // If only SSL or both is enabled  =>  port=465
    // mailSender.setPort(587);		
    mailSender.setPort(465);  
    mailSender.setHost("smtp.gmail.com");
    mailSender.setUsername("");
    mailSender.setPassword("");
		
    return mailSender;
  }
}
