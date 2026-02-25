package com.rentalhouse.app.views.containers.Auth;
import java.io.IOException;

import com.rentalhouse.app.models.User;
import com.rentalhouse.app.views.components.ExpirationPopup.RentalExpiration;
import com.rentalhouse.app.views.containers.AdminDashboard.AdminDashboardScene;
import com.rentalhouse.app.views.containers.Auth.ChangePassword.ChangePasswordScene;
import com.rentalhouse.app.views.containers.Auth.VerificationCode.VerificationCodeScene;
import com.rentalhouse.app.views.containers.Dashboard.DashboardScene;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class AuthScene {
   private static Stage stage;
   private static Parent root;
   private static Scene scene;
   private static AuthScene controller;

   public static Scene createScene(Stage stage) {
      try {
         FXMLLoader loader = new FXMLLoader(AuthScene.class.getResource("auth.fxml"));
         root = loader.load();
         scene = new Scene(root);
         stage.setResizable(false);
         controller = loader.getController();
         controller.setLoginScene();
         return scene;
      } catch (IOException e) {
         return null;
      }
   }

   @FXML
   private StackPane authPane;

   private void setLoginScene() throws IOException {
      Parent login = FXMLLoader.load(getClass().getResource("./Login/login.fxml"));
      authPane.getChildren().clear();
      authPane.getChildren().add(login);
   }

   private void setRegisterScene() throws IOException {
      Parent register = FXMLLoader.load(getClass().getResource("./Register/register.fxml"));
      authPane.getChildren().clear();
      authPane.getChildren().add(register);
   }

   private void setForgotPasswordScene() throws IOException {
      Parent forgotPassword = FXMLLoader.load(getClass().getResource("./ForgotPassword/forgotPassword.fxml"));
      authPane.getChildren().clear();
      authPane.getChildren().add(forgotPassword);
   }

   private void setVerificationCodeScene(String email) throws IOException {
      Parent verificationCode = VerificationCodeScene.getVerificationCodeScene(email);
      authPane.getChildren().clear();
      authPane.getChildren().add(verificationCode);
   }

   private void setChangePasswordScene(String email) throws IOException {
      Parent changePassword = ChangePasswordScene.getChangePasswordScene(email);
      authPane.getChildren().clear();
      authPane.getChildren().add(changePassword);
   }

   public static void switchToRegister() throws IOException {
      controller.setRegisterScene();
   }

   public static void switchToLogin() throws IOException {
      controller.setLoginScene();
   }


   public static void switchToForgotPassword() throws IOException {
      controller.setForgotPasswordScene();
   }

   public static void switchToVerificationCode(String email) throws IOException {
      controller.setVerificationCodeScene(email);
   }

   public static void switchToChangePassword(String email) throws IOException {
      controller.setChangePasswordScene(email);
   }
   public static void switchToAdminScene(ActionEvent event) throws IOException {
      stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      stage.setScene(AdminDashboardScene.createScene(stage));
      stage.setResizable(true);
      stage.setMinWidth(1210);
      stage.setMinHeight(800);
      stage.centerOnScreen();
      stage.show();
   }

   public static void switchToDashboard(ActionEvent event, User user) throws IOException {
      stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      stage.setScene(DashboardScene.createScene(stage));
      stage.setTitle("Rentify");
      stage.setResizable(true);
      stage.setMinWidth(1200);
      stage.setMinHeight(800);
      stage.centerOnScreen();
      stage.show();
      RentalExpiration.checkExpirationLogin(user);
   }
}
