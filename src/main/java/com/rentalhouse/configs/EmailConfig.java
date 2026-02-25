package com.rentalhouse.configs;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class EmailConfig {
   private static final String EMAIL = DotEnv.get("EMAIL");
   private static final String PASSWORD = DotEnv.get("PASSWORD");

   public static Session getSession() {
      Properties props = new Properties();
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", "smtp.gmail.com");
      props.put("mail.smtp.port", "587");

      Authenticator auth = new Authenticator() {
         @Override
         protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(EMAIL, PASSWORD);
         }
      };

      return Session.getInstance(props, auth);
   }
}
