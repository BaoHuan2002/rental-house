package com.rentalhouse.app.views.containers.Auth.Register;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.rentalhouse.app.controllers.AuthController;
import com.rentalhouse.app.middlewares.ValidateInput;
import com.rentalhouse.app.models.User;
import com.rentalhouse.app.views.components.AlertPopup.AlertPopup;
import com.rentalhouse.app.views.containers.Auth.AuthScene;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class RegisterScene {
   private static Parent root;
   private static Scene scene;
   private final AuthController authController = new AuthController();

   public static Scene createScene(Stage stage) {
      try {
         root = FXMLLoader.load(RegisterScene.class.getResource("register.fxml"));
         scene = new Scene(root);
         stage.setResizable(false);
         return scene;
      } catch (IOException e) {
         e.printStackTrace();
         return null;
      }
   }

   @FXML
   public void switchToLogin(ActionEvent event) throws IOException {
      AuthScene.switchToLogin();
   }

   @FXML
   private TextField nameField;

   @FXML
   private TextField emailField;

   @FXML
   private TextField phoneField;

   @FXML
   private PasswordField passwordField;

   @FXML
   private PasswordField passwordConfirmationField;

   @FXML
   private Label nameError;
   @FXML
   private Label emailError;
   @FXML
   private Label phoneError;
   @FXML
   private Label passwordError;
   @FXML
   private Label passwordConfirmationError;

   @FXML
   public void submit(ActionEvent event) throws IOException {
      String name = this.nameField.getText();
      String email = this.emailField.getText();
      String phone = this.phoneField.getText();
      String password = this.passwordField.getText();
      String passwordConfirmation = this.passwordConfirmationField.getText();

      Map<String, String> errorMessages = new HashMap<>();

      if (ValidateInput.isEmpty(name)) {
         errorMessages.put("name", "Name is required");
      }

      if (ValidateInput.isEmpty(email)) {
         errorMessages.put("email", "Email is required");
      } else if (!ValidateInput.isValidEmail(email)) {
         errorMessages.put("email", "Invalid email");
      }

      if (ValidateInput.isEmpty(phone)) {
         errorMessages.put("phone", "Phone number is required");
      } else if (!ValidateInput.isValidPhone(phone)) {
         errorMessages.put("phone", "Invalid phone number");
      }

      if (ValidateInput.isEmpty(password)) {
         errorMessages.put("password", "Password is required");
      } else if (!ValidateInput.isValidPassword(password)) {
         errorMessages.put("password",
               "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one number, and one special character");
      }

      if (ValidateInput.isEmpty(passwordConfirmation)) {
         errorMessages.put("passwordConfirmation", "Password confirmation is required");
      } else if (!ValidateInput.isValidPasswordConfirmation(password, passwordConfirmation)) {
         errorMessages.put("passwordConfirmation", "Passwords do not match");
      }

      if (!errorMessages.isEmpty()) {
         nameError.setText(errorMessages.get("name"));
         emailError.setText(errorMessages.get("email"));
         phoneError.setText(errorMessages.get("phone"));
         passwordError.setText(errorMessages.get("password"));
         passwordConfirmationError.setText(errorMessages.get("passwordConfirmation"));
         return;
      }
      resetAllErrorField();

      User newUser = new User();

      newUser.setName(name);
      newUser.setEmail(email);
      newUser.setPhone(phone);
      newUser.setPassword(password);
      newUser.setRole(2);

      if (authController.register(newUser, passwordConfirmation)) {
         AlertPopup.success("Registration successful", "You have successfully registered");
         AuthScene.switchToLogin();
         return;
      }
      AlertPopup.warning("Registration warning",
            "This email/phone already registered. Please try another one.");
   }

   @FXML
   private void initialize() {
      nameField.addEventHandler(KeyEvent.KEY_RELEASED, event -> resetAllErrorField());
      emailField.addEventHandler(KeyEvent.KEY_RELEASED, event -> resetAllErrorField());
      phoneField.addEventHandler(KeyEvent.KEY_RELEASED, event -> resetAllErrorField());
      passwordField.addEventHandler(KeyEvent.KEY_RELEASED, event -> resetAllErrorField());
      passwordConfirmationField.addEventHandler(KeyEvent.KEY_RELEASED, event -> resetAllErrorField());
   }

   private void resetAllErrorField() {
      nameError.setText("");
      emailError.setText("");
      phoneError.setText("");
      passwordError.setText("");
      passwordConfirmationError.setText("");
   }
}
