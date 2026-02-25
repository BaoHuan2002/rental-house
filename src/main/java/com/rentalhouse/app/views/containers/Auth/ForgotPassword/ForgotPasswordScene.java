package com.rentalhouse.app.views.containers.Auth.ForgotPassword;

import java.io.IOException;

import com.rentalhouse.app.controllers.AuthController;
import com.rentalhouse.app.controllers.EmailController;
import com.rentalhouse.app.views.components.AlertPopup.AlertPopup;
import com.rentalhouse.app.views.containers.Auth.AuthScene;
import com.rentalhouse.utils.VerificationCode;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ForgotPasswordScene {
   private static Parent root;
   private static Scene scene;
   private final AuthController authController = new AuthController();
   private final EmailController emailController = new EmailController();
   private String verificationCode;
   private String userEmail;

   public static Scene createScene(Stage stage) {
      try {
         root = FXMLLoader.load(ForgotPasswordScene.class.getResource("forgotPassword.fxml"));
         scene = new Scene(root);
         stage.setResizable(false);
         return scene;
      } catch (IOException e) {
         e.printStackTrace();
         return null;
      }
   }

   @FXML
   private TextField emailField;

   @FXML
   private Button cancel;

   @FXML
   private Button send;

   @FXML
   private Label emailError;

   @FXML
   private void cancel(ActionEvent event) {
      try {
         AuthScene.switchToLogin();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   @FXML
   private void send(ActionEvent event) throws IOException {
      String email = this.emailField.getText();
      if (authController.forgotPassword(email)) {
         userEmail = email;
         verificationCode = VerificationCode.get();
         emailController.sendVerificationCode(userEmail, verificationCode);
         try {
            AlertPopup.success("Success", "Verification code to your email address");
            AuthScene.switchToVerificationCode(userEmail);
         } catch (Exception e) {
            e.printStackTrace();
         }
      } else {
         emailError.setText("Email does not exist");
      }
   }

   @FXML
   private void initialize() {
      emailField.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> resetError());
   }

   public void resetError() {
      this.emailField.setText("");
      this.emailError.setText("");
   }

}
