package com.rentalhouse.app.views.containers.Auth.Login;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.rentalhouse.app.controllers.AuthController;
import com.rentalhouse.app.middlewares.ValidateInput;
import com.rentalhouse.app.models.Auth;
import com.rentalhouse.app.views.components.AlertPopup.AlertPopup;
import com.rentalhouse.app.views.containers.Auth.AuthScene;
import com.rentalhouse.utils.Encryption;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginScene {
   private final AuthController authController = new AuthController();
   private static Parent root;
   private static Scene scene;

   public static Scene createScene(Stage stage) {
      try {
         FXMLLoader loader = new FXMLLoader(LoginScene.class.getResource("login.fxml"));
         root = loader.load();
         scene = new Scene(root);
         stage.setResizable(false);
         return scene;
      } catch (IOException e) {
         e.printStackTrace();
         return null;
      }
   }

   @FXML
   public void switchToForgotPassword(MouseEvent event) throws IOException {
      AuthScene.switchToForgotPassword();
   }

   @FXML
   private Button submit;

   @FXML
   private TextField emailOrPhoneField;

   @FXML
   private Label emailOrPhoneError;

   @FXML
   private Label passwordError;

   @FXML
   private PasswordField passwordField;

   @FXML
   private Label forgotPassword;

   @FXML
   private CheckBox remember;

   @FXML
   public void switchToRegister(ActionEvent event) throws IOException {
      AuthScene.switchToRegister();
   }

   @FXML
   public void submit(ActionEvent event) throws IOException {
      String emailOrPhone = this.emailOrPhoneField.getText();
      String password = this.passwordField.getText();
      Map<String, String> errorMessages = new HashMap<>();

      if (ValidateInput.isEmpty(emailOrPhone)) {
         errorMessages.put("emailOrPhone", "Email/Phone number is required");
      } else {
         if (emailOrPhone.startsWith("0")) {
            if (!ValidateInput.isValidPhone(emailOrPhone)) {
               errorMessages.put("emailOrPhone", "Invalid phone number");
            }
         } else {
            if (!ValidateInput.isValidEmail(emailOrPhone)) {
               errorMessages.put("emailOrPhone", "Invalid email");
            }
         }
      }

      if (ValidateInput.isEmpty(password)) {
         errorMessages.put("password", "Password is required");
      }

      if (!errorMessages.isEmpty()) {
         emailOrPhoneError.setText(errorMessages.get("emailOrPhone"));
         passwordError.setText(errorMessages.get("password"));
         return;
      }
      resetAllErrorField();

      if (authController.login(emailOrPhone, password)) {
         if (Auth.getUser().getRole() == 1) {
            AuthScene.switchToAdminScene(event);
         } else if (Auth.getUser().getRole() == 2) {
            AuthScene.switchToDashboard(event, Auth.getUser());
         } else {
            AlertPopup.warning("You do not have access", "You do not have access");
         }
         if (remember.isSelected()) {
            createRememberFile();
         }
         return;
      }
      AlertPopup.error("Login failed", "Your Email/Phone or password incorrect");
   }

   @FXML
   private void initialize() {
      emailOrPhoneField.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> resetAllErrorField());
      passwordField.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> resetAllErrorField());
   }

   private void resetAllErrorField() {
      emailOrPhoneError.setText("");
      passwordError.setText("");
   }

   private void createRememberFile() {
      try {
         File newFile = new File("remember.json");
         JSONObject info = new JSONObject();
         info.put("emailOrPhone", Encryption.encrypt(emailOrPhoneField.getText()));
         info.put("password", Encryption.encrypt(passwordField.getText()));
         info.put("date", Encryption.encrypt(LocalDate.now().plusDays(10).toString()));
         info.put("role", Encryption.encrypt(Auth.getUser().getRole().toString()));
         BufferedWriter bw = new BufferedWriter(new FileWriter(newFile));
         bw.write(info.toString());
         bw.close();
      } catch (Exception e) {
         System.err.println(e.getMessage());
      }
   }
}
