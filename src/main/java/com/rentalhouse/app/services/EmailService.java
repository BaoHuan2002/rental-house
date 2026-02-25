package com.rentalhouse.app.services;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.rentalhouse.configs.DotEnv;
import com.rentalhouse.configs.EmailConfig;

public class EmailService {
   private static final String EMAIL = DotEnv.get("EMAIL");

   public boolean sendVerificationCode(String to, String code) {
      Session session = EmailConfig.getSession();
      MimeMessage msg = new MimeMessage(session);
      try {
         msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
         msg.setFrom(new InternetAddress(EMAIL));
         msg.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
         msg.setSubject("Password Reset Verification Code");
         msg.setText("Your verification code is: " + code);
         Transport.send(msg);
         System.out.println("Sent message successfully....");
      } catch (MessagingException mex) {
         mex.printStackTrace();
         System.out.println("Failed to send message....");
      }
      return false;
   }

}
