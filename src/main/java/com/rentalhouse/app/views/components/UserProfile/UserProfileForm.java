package com.rentalhouse.app.views.components.UserProfile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.rentalhouse.app.models.Auth;
import com.rentalhouse.app.models.User;
import com.rentalhouse.app.views.components.ChangeBankInfo.ChangeBankInfo;
import com.rentalhouse.app.views.components.ChangePassword.ChangePasswordForm;
import com.rentalhouse.app.views.components.ChangePhone.ChangePhoneForm;
import com.rentalhouse.app.views.components.MembershipPackage.MembershipPackage;
import com.rentalhouse.app.views.customStages.CustomStage;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserProfileForm {
   private static User userAuth = Auth.getUser();
   private static Scene scene;
   private static Parent root;
   private static Stage stage;

   @FXML
   private Label name;

   @FXML
   private Label membership;

   @FXML
   private Label role;

   @FXML
   private Label email;

   @FXML
   private Label phone;

   @FXML
   private Label bankAccountNumber;

   @FXML
   private Label bankName;
   @FXML
   private HBox hBoxRenew;

   @FXML
   private Button renewBtn;
   @FXML
   private HBox hBoxBankInfo;

   @FXML
   private HBox hboxBankAccountNumber;

   @FXML
   private HBox hBoxBankName;

   @FXML
   private VBox vBox;

   @FXML
   private Label profileLabel;

   @FXML
   private HBox hBoxButton;

   @FXML Button profileUser_closeBTN;
   @FXML
   private HBox hBoxMembership;

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
      UserProfileForm.userAuth = userAuth;
      FXMLLoader loader = new FXMLLoader(UserProfileForm.class.getResource("userProfile.fxml"));
      init(loader);
   }

   public void initialize() {
      try {

         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
         LocalDate membership_expire_at = LocalDateTime.parse(userAuth.getMembership_expire_at(), formatter)
               .toLocalDate();
         name.setText(userAuth.getName());
         phone.setText(userAuth.getPhone());
         membership.setText(userAuth.getMembership_package() == 1
               ? "Free trial (" + membership_expire_at + ")"
               : (userAuth.getMembership_package() == 2
                     ? "VIP (" + membership_expire_at + ")"
                     : "Unlimited"));
         email.setText(userAuth.getEmail());
         role.setText(userAuth.getRole() == 1 ? "Admin" : (userAuth.getRole() == 2 ? "Lessor" : "Tenant"));
         bankAccountNumber.setText(userAuth.getBank_number());
         bankName.setText(userAuth.getBank_name());
         try {
            if (userAuth.getRole() == 1) {
               Platform.runLater(() -> {
                  vBox.getChildren().remove(hBoxRenew);
                  vBox.getChildren().remove(hBoxBankInfo);
                  vBox.getChildren().remove(hboxBankAccountNumber);
                  vBox.getChildren().remove(hBoxBankName);
                  hBoxButton.setPadding(new Insets(10, 0, 20, 5));

                  vBox.getChildren().add(0, profileLabel);
                  vBox.getChildren().add(7, hBoxButton);
                  stage.setHeight(360);
                  vBox.getChildren().remove(hBoxMembership);
               });
            }

         } catch (Exception e) {
            System.err.println(e.getMessage());
         }
      } catch (Exception e) {
         System.err.println(e.getMessage());
      }
   }

   public void close() {
      profileUser_closeBTN.getScene().getWindow().hide();
   }

   public void openChangePasswordForm() {
      try {
         ChangePasswordForm.show(userAuth);
      } catch (Exception e) {
         System.err.println(e.getMessage());
      }
   }

   public void openChangePhoneForm() {
      try {
         ChangePhoneForm.show(userAuth);
      } catch (Exception e) {
         System.err.println(e.getMessage());
      }
   }

   public void openChangeBankInfo() {
      try {
         ChangeBankInfo.show(userAuth);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public void openMembershipPackage() {
      try {
         MembershipPackage.show();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public static void refresh() {
      try {
         FXMLLoader loader = new FXMLLoader(UserProfileForm.class.getResource("userProfile.fxml"));
         Parent refreshedRoot = loader.load();
         Scene refreshedScene = new Scene(refreshedRoot);
         stage.setScene(refreshedScene);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
