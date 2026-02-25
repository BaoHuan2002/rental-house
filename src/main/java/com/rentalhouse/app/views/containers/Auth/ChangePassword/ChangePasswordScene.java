package com.rentalhouse.app.views.containers.Auth.ChangePassword;

import com.rentalhouse.app.controllers.AuthController;
import com.rentalhouse.app.middlewares.ValidateInput;
import com.rentalhouse.app.views.components.AlertPopup.AlertPopup;
import com.rentalhouse.app.views.containers.Auth.AuthScene;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class ChangePasswordScene {
   private final AuthController authController = new AuthController();
   private static String email;

   public static Parent getChangePasswordScene(String email) {
      ChangePasswordScene.email = email;
      try {
         return FXMLLoader.load(ChangePasswordScene.class.getResource("changePassword.fxml"));
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }
   }

   @FXML
   private PasswordField newPasswordField;

   @FXML
   private PasswordField confirmPasswordField;

   @FXML
   private Label newPasswordError;

   @FXML
   private Label confirmPasswordError;

   @FXML
   private Button submit;

   @FXML
   private void submit(ActionEvent event) {
      String newPassword = this.newPasswordField.getText();
      String confirmPassword = this.confirmPasswordField.getText();
      boolean valid = true;

      if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
         newPasswordError.setText("Password is required");
         valid = false;
      } else if (!ValidateInput.isValidPassword(newPassword)) {
         newPasswordError.setText("");
         confirmPasswordError.setText("Password is invalid");
         valid = false;
      } else if (!ValidateInput.isValidPasswordConfirmation(newPassword, confirmPassword)) {
         newPasswordError.setText("");
         confirmPasswordError.setText("Passwords do not match");
         valid = false;
      }
      if (valid) {
         try {
            if (authController.updatePassword(email, newPassword)) {
               AlertPopup.success("Success", "Update Password successfully.");
               AuthScene.switchToLogin();
            } else {
               newPasswordError.setText("Failed to update password");
            }
         } catch (Exception e) {
            e.printStackTrace();
            newPasswordError.setText("An error occurred");
         }
      }
   }

   @FXML
   private void initialize() {
      newPasswordField.textProperty().addListener((observable, oldValue, newValue) -> resetAllErrorField());
      confirmPasswordField.textProperty().addListener((observable, oldValue, newValue) -> resetAllErrorField());
   }

   private void resetAllErrorField() {
      newPasswordError.setText("");
      confirmPasswordError.setText("");
   }
}
