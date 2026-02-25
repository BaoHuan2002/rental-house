package com.rentalhouse.app.controllers;

import com.rentalhouse.app.models.User;
import com.rentalhouse.app.services.AuthService;

public class AuthController {
   private final AuthService _AuthService = new AuthService();

   public boolean register(User user, String confirmPassword) {
      return _AuthService.register(user, confirmPassword);
   }

   public boolean login(String emailOrPhone, String password) {
      return _AuthService.login(emailOrPhone, password);
   }

   public boolean forgotPassword(String inputEmail){
      return _AuthService.forgotPassword(inputEmail);
   }

   public boolean updatePassword(String email, String newPassword){ 
      return _AuthService.updatePassword(email, newPassword);
   }

}
