package com.rentalhouse.app.views.containers.Auth.VerificationCode;

import java.io.IOException;

import com.rentalhouse.app.views.components.AlertPopup.AlertPopup;
import com.rentalhouse.app.views.containers.Auth.AuthScene;
import com.rentalhouse.utils.VerificationCode;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class VerificationCodeScene {
   private static String email;

   public static Parent getVerificationCodeScene(String email) {
      try {
         VerificationCodeScene.email = email;
         return FXMLLoader.load(VerificationCodeScene.class.getResource("verificationCode.fxml"));
      } catch (IOException e) {
         e.printStackTrace();
      }
      return null;
   }

   @FXML
   private TextField verificationCodeField;

   @FXML
   private Label verificationCodeError;

   @FXML
   private void cancel(ActionEvent event) {
      try {
         AuthScene.switchToLogin();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   @FXML
   private void confirm(ActionEvent event) {
      String verificationCode = this.verificationCodeField.getText();
      if (VerificationCode.isValidCode(verificationCode)) {
         try {
            AlertPopup.success("Success", "Successful Authentication");
            AuthScene.switchToChangePassword(VerificationCodeScene.email);
         } catch (IOException e) {
            e.printStackTrace();
         }
      } else {
         verificationCodeError.setText("Invalid verification code");
      }
   }

   @FXML
   private void initialize() {
      verificationCodeField.addEventHandler(KeyEvent.KEY_RELEASED, event -> resetError());
   }

   private void resetError() {
      this.verificationCodeError.setText("");
   }
}