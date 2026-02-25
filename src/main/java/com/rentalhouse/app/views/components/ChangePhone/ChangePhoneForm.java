package com.rentalhouse.app.views.components.ChangePhone;

import java.io.IOException;
import java.util.ArrayList;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChangePhoneForm {
   private static UserController userController = new UserController();
   private static User userAuth;
   private static Scene scene;
   private static Parent root;
   private static Stage stage;

   @FXML
   private PasswordField currentPassword;

   @FXML
   private TextField newPhone;

   @FXML
   private Label currentError;

   @FXML
   private Label phoneError;

   @FXML private Button changePhone_close;

   private static void init(FXMLLoader loader) throws IOException {
      root = loader.load();
      scene = new Scene(root);
      // stage = new Stage();
      // stage.setScene(scene);
      // stage.setResizable(false);
      // stage.initModality(Modality.APPLICATION_MODAL);
      // stage.showAndWait();
      stage = new CustomStage(scene, root);
   }

   public static void show(User userAuth) throws IOException {
      ChangePhoneForm.userAuth = userAuth;
      FXMLLoader loader = new FXMLLoader(ChangePhoneForm.class.getResource("changePhone.fxml"));
      init(loader);
   }

   public void cancel() {
      stage.close();
   }

   public void close() {
      changePhone_close.getScene().getWindow().hide();
   }

   public void submit() {
      ArrayList<String> numberPhone = new ArrayList<>();
      userController.GetUsers().forEach(user -> {
         numberPhone.add(user.getPhone());
      });

      String currentPassword = this.currentPassword.getText();
      String newPhone = this.newPhone.getText();

      Map<String, String> errorMessages = new HashMap<>();
      if (ValidateInput.isEmpty(currentPassword)) {
         errorMessages.put("currentPassword", "Current password is required");
      } else if (!BCrypt.checkpw(currentPassword, userAuth.getPassword())) {
         errorMessages.put("currentPassword", "Passwords do not match");
      }

      if (ValidateInput.isEmpty(newPhone)) {
         errorMessages.put("phone", "Phone is required");
      } else if (!ValidateInput.isValidPhone(newPhone)) {
         errorMessages.put("phone", "This isn\'t a number phone");
      } else if (numberPhone.contains(newPhone)) {
         errorMessages.put("phone", "This phone is existed");
      }

      if (!errorMessages.isEmpty()) {
         currentError.setText(errorMessages.get("currentPassword"));
         phoneError.setText(errorMessages.get("phone"));
         return;
      }
      resetAllErrorField();

      if (BCrypt.checkpw(currentPassword, userAuth.getPassword())) {
         userAuth.setPhone(newPhone);
         userAuth.setPassword(currentPassword);
         if (userController.update(userAuth)) {
            try {
               AlertPopup.success("Change phone ", "Phone is changed successfully");
               stage.close();

            } catch (Exception e) {
               System.err.println(e.getMessage());
            }
         }
      }
   }

   public void resetAllErrorField() {
      phoneError.setText("");
      currentError.setText("");
   }
}
