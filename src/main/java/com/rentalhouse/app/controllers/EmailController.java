package com.rentalhouse.app.controllers;

import com.rentalhouse.app.services.EmailService;


public class EmailController {
   private final EmailService _EmailService = new EmailService();
   public boolean sendVerificationCode(String to , String code ){
      return _EmailService.sendVerificationCode(to,code);
   }
}
