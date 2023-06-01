package com.application.demo.springmvc_custom_authentication.messenger;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import com.application.demo.springmvc_custom_authentication.config.MailConfig;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

public class MailMessenger {
  @Bean
  public static void htmlEmailMessenger(String from,String to,String subject,String body) throws MessagingException{
    JavaMailSender mailSender=MailConfig.getMailConfig();
    MimeMessage message=mailSender.createMimeMessage();
    MimeMessageHelper htmlMessage=new MimeMessageHelper(message,true);
        
//    htmlMessage.setFrom("");
     htmlMessage.setTo("");
     htmlMessage.setSubject(subject);
     htmlMessage.setText(body, true);
		
     mailSender.send(message);
  }
}
