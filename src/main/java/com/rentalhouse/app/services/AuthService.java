package com.rentalhouse.app.services;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import com.rentalhouse.app.controllers.UserMembershipController;
import com.rentalhouse.app.middlewares.ValidateInput;
import com.rentalhouse.app.models.Auth;
import com.rentalhouse.app.models.User;
import com.rentalhouse.app.models.UserMembership;
import com.rentalhouse.app.repositories.AuthRepository;
import com.rentalhouse.utils.Uuid;

public class AuthService {
   private final AuthRepository _AuthRepository = new AuthRepository();
   private final UserMembershipController _userMembershipController = new UserMembershipController();

   public boolean register(User user, String confirmPassword) {
      if (ValidateInput.isEmpty(user.getEmail())
            || ValidateInput.isEmpty(user.getPhone())
            || ValidateInput.isEmpty(user.getName())
            || ValidateInput.isEmpty(user.getPassword())
            || ValidateInput.isEmpty(confirmPassword)) {
         return false;
      }
      if (!ValidateInput.isValidEmail(user.getEmail())
            || !ValidateInput.isValidPhone(user.getPhone())
            || !ValidateInput.isValidPassword(user.getPassword())
            || !ValidateInput.isValidPasswordConfirmation(user.getPassword(), confirmPassword)) {
         return false;
      }
      try {
         if (_AuthRepository.doesUserExist(user.getEmail()) || _AuthRepository.doesUserExist(user.getPhone())) {
            return false;
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
      user.setId(Uuid.get());

      if (_AuthRepository.register(user)) {
         UserMembership userMembership = new UserMembership();
         userMembership.setUser_id(user.getId());
         userMembership.setMembership_package(1);
         userMembership.setPrice(new BigDecimal(0));
         return _userMembershipController.create(userMembership);
      }

      return false;
   }

   public boolean login(String emailOrPhone, String password) {
      if (ValidateInput.isEmpty(emailOrPhone) || ValidateInput.isEmpty(password)) {
         return false;
      }

      User userAuth = _AuthRepository.login(emailOrPhone);
      if (userAuth == null) {
         return false;
      }

      if (BCrypt.checkpw(password, userAuth.getPassword())) {
         Auth.setUser(userAuth);
         return true;
      }
      return false;
   }

   public boolean forgotPassword(String inputEmail) {

      if (ValidateInput.isEmpty(inputEmail)) {
         return false;
      }
      if (!ValidateInput.isValidEmail(inputEmail)) {
         return false;
      }
      try {
         if (!_AuthRepository.checkEmail(inputEmail)) {
            return false;
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return true;
   }

   public boolean updatePassword(String email, String newPassword) {
      if (ValidateInput.isEmpty(email)
            || ValidateInput.isEmpty(newPassword)) {
         return false;
      }
      String newPasswordBcrypt = BCrypt.hashpw(newPassword, BCrypt.gensalt());
      return _AuthRepository.updatePassword(email, newPasswordBcrypt);
   }

}
