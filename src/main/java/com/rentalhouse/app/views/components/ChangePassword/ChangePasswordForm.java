package com.rentalhouse.app.views.components.ChangePassword;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;

import com.rentalhouse.app.controllers.UserController;
import com.rentalhouse.app.middlewares.ValidateInput;
import com.rentalhouse.app.models.User;

import com.rentalhouse.app.views.components.AlertPopup.AlertPopup;
import com.rentalhouse.app.views.customStages.CustomStage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class ChangePasswordForm {
   private static UserController userController = new UserController();
   private static User userAuth;
   private static Scene scene;
   private static Parent root;
   private static Stage stage;

   @FXML
   private PasswordField currentPassword;
   @FXML
   private PasswordField newPassword;
   @FXML
   private PasswordField confirmPassword;

   @FXML
   private Label currentError;

   @FXML
   private Label newError;

   @FXML
   private Label confirmError;

   @FXML private Button changePass_closeBTN;

   private static void init(FXMLLoader loader) throws IOException {
      root = loader.load();
      scene = new Scene(root);
      stage = new CustomStage(scene, root);
      // stage = new Stage();
      // stage.setScene(scene);
      // stage.setResizable(false);
      // stage.initModality(Modality.APPLICATION_MODAL);
      // stage.showAndWait();
   }

   public static void show(User userAuth) throws IOException {
      ChangePasswordForm.userAuth = userAuth;
      FXMLLoader loader = new FXMLLoader(ChangePasswordForm.class.getResource("changePassword.fxml"));
      init(loader);
   }

   public void close() {
      changePass_closeBTN.getScene().getWindow().hide();
   }

   public void cancel() {
      stage.close();
   }

   public void submit() {
      String currentPassword = this.currentPassword.getText();
      String newPassword = this.newPassword.getText();
      String confirmPassword = this.confirmPassword.getText();

      Map<String, String> errorMessages = new HashMap<>();
      if (ValidateInput.isEmpty(currentPassword)) {
         errorMessages.put("currentPassword", "Current password is required");
      } else if (!BCrypt.checkpw(currentPassword, userAuth.getPassword())) {
         errorMessages.put("currentPassword", "Passwords do not match");
      }

      if (ValidateInput.isEmpty(newPassword)) {
         errorMessages.put("newPassword", "New password is required");
      } else if (!ValidateInput.isValidPassword(newPassword)) {
         errorMessages.put("newPassword",
               "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one number, and one special character");
      }

      if (ValidateInput.isEmpty(confirmPassword)) {
         errorMessages.put("confirmPassword", "Password confirmation is required");
      } else if (!ValidateInput.isValidPasswordConfirmation(newPassword, confirmPassword)) {
         errorMessages.put("confirmPassword", "Passwords do not match");
      }

      if (!errorMessages.isEmpty()) {
         currentError.setText(errorMessages.get("currentPassword"));
         newError.setText(errorMessages.get("newPassword"));
         confirmError.setText(errorMessages.get("confirmPassword"));
         return;
      }
      resetAllErrorField();

      if (BCrypt.checkpw(currentPassword, userAuth.getPassword())) {
         if (newPassword.equals(confirmPassword)) {
            userAuth.setPassword(newPassword);
            if (userController.update(userAuth)) {
               try {
                  AlertPopup.success("Change password ", "Password is changed successfully");
                  stage.close();

               } catch (Exception e) {
                  System.err.println(e.getMessage());
               }
            }
         }
      }
   }

   public void resetAllErrorField() {
      newError.setText("");
      currentError.setText("");
      confirmError.setText("");
   }
}
