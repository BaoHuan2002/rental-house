package com.rentalhouse.app.views.containers.AdminDashboard;

import java.io.File;
import java.io.IOException;

import com.rentalhouse.app.models.Auth;
import com.rentalhouse.app.models.User;
import com.rentalhouse.app.views.components.UserProfile.UserProfileForm;
import com.rentalhouse.app.views.containers.AdminDashboard.ContactManagement.ContactManagementScene;
import com.rentalhouse.app.views.containers.AdminDashboard.DetailContact.DetailContact;
import com.rentalhouse.app.views.containers.AdminDashboard.RevenueManagement.RevenueManagementScene;
import com.rentalhouse.app.views.containers.AdminDashboard.RevenueMonthDetail.RevenueMonthDetail;
import com.rentalhouse.app.views.containers.AdminDashboard.UsersManagement.UsersManagementScene;
import com.rentalhouse.app.views.containers.Auth.AuthScene;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class AdminDashboardScene {
   private static Stage stage;
   private static Parent root;
   private static Scene scene;
   private static String styleActivePane = "-fx-background-color: white; -fx-background-radius: 10;";
   private static String initStyleBackgroundPane = "-fx-background-color: #8285FF; -fx-background-radius: 10;";
   private User userAuth = Auth.getUser();

   @FXML
   private StackPane stackPane;
   @FXML
   private AnchorPane logOutBtn;
   @FXML
   private AnchorPane usersBtn;
   @FXML
   private AnchorPane revenueBtn;
   @FXML
   private AnchorPane contactBtn;
   @FXML
   private Pane usersIconPane;
   @FXML
   private Pane revenueIconPane;
   @FXML
   private Pane contactIconPane;
   @FXML
   private Label usersLabel;
   @FXML
   private Label revenueLabel;
   @FXML
   private Label contactLabel;
   @FXML
   private Label titleSceneLabel;

   @FXML
   private AnchorPane userProfile;

   private static AdminDashboardScene controller;

   public static Scene createScene(Stage stage) {
      try {
         FXMLLoader loader = new FXMLLoader(AdminDashboardScene.class.getResource("adminDashboardScene.fxml"));
         root = loader.load();
         scene = new Scene(root);
         controller = loader.getController();
         AdminDashboardScene.stage = stage;
         AdminDashboardScene.stage.setResizable(true);
         AdminDashboardScene.stage.setMinWidth(1000);
         AdminDashboardScene.stage.setMinHeight(800);
         return scene;
      } catch (IOException e) {
         e.printStackTrace();
         return null;
      }
   }

   private void init() throws IOException {
      setUsersManagementScene();
      onClickUsers();
      handleLogout();
      onClickRevenue();
      onClickContact();
      onClickUserProfile();
   }

   public void initialize() throws IOException {
      init();
   }

   private void onClickUsers() {
      usersBtn.setOnMouseClicked(e -> {
         setActiveUsersBtn();
         this.titleSceneLabel.setText("Users management");
         try {
            setUsersManagementScene();
         } catch (IOException ex) {
            ex.printStackTrace();
         }
      });
   }

   private void onClickRevenue() {
      revenueBtn.setOnMouseClicked(e -> {
         setActiveRevenueBtn();
         this.titleSceneLabel.setText("Revenue management");
         try {
            setRevenueManagementScene();
         } catch (IOException ex) {
            ex.printStackTrace();
         }
      });
   }

   private void onClickContact() {
      contactBtn.setOnMouseClicked(e -> {
         setActiveContactBtn();
         this.titleSceneLabel.setText("Contact management");
         try {
            setContactManagementScene();
         } catch (IOException ex) {
            ex.printStackTrace();
         }
      });
   }

   private void onClickUserProfile() {
      userProfile.setOnMouseClicked(e -> {
         try {
            UserProfileForm.show(userAuth);
         } catch (Exception exception) {
            System.err.println(exception.getMessage());

         }
      });
   }

   private void setActiveUsersBtn() {
      usersIconPane.setStyle(styleActivePane);
      revenueIconPane.setStyle(initStyleBackgroundPane);
      contactIconPane.setStyle(initStyleBackgroundPane);

      usersLabel.setTextFill(Color.web("white"));
      revenueLabel.setTextFill(Color.web("#8285FF"));
      contactLabel.setTextFill(Color.web("#8285FF"));
   }

   private void setActiveRevenueBtn() {
      revenueIconPane.setStyle(styleActivePane);
      usersIconPane.setStyle(initStyleBackgroundPane);
      contactIconPane.setStyle(initStyleBackgroundPane);

      revenueLabel.setTextFill(Color.web("white"));
      usersLabel.setTextFill(Color.web("#8285FF"));
      contactLabel.setTextFill(Color.web("#8285FF"));
   }

   private void setActiveContactBtn() {
      contactIconPane.setStyle(styleActivePane);
      revenueIconPane.setStyle(initStyleBackgroundPane);
      usersIconPane.setStyle(initStyleBackgroundPane);

      contactLabel.setTextFill(Color.web("white"));
      revenueLabel.setTextFill(Color.web("#8285FF"));
      usersLabel.setTextFill(Color.web("#8285FF"));
   }

   private void setUsersManagementScene() throws IOException {
      this.titleSceneLabel.setText("Users management");
      stackPane.getChildren().clear();
      stackPane.getChildren().setAll(UsersManagementScene.getUsersManagement());
   }

   private void setRevenueManagementScene() throws IOException {
      this.titleSceneLabel.setText("Revenue management");
      stackPane.getChildren().clear();
      stackPane.getChildren().setAll(RevenueManagementScene.getRevenueManagement());
   }

   private void setContactManagementScene() throws IOException {
      this.titleSceneLabel.setText("Contact management");
      stackPane.getChildren().clear();
      stackPane.getChildren().setAll(ContactManagementScene.getContactManagement());
   }

   private void setContactDetailScene(String contactID, String name, String phone) throws IOException {
      this.titleSceneLabel.setText("Contact Detail" + " - " + name + " - " + phone);
      stackPane.getChildren().clear();
      stackPane.getChildren().setAll(DetailContact.getDetailContact(contactID));
   }

   private void setRevenueDetailByMonthAndYear(String monthName, int month, int year) throws IOException {
      this.titleSceneLabel.setText("Revenue detail of " + monthName + " - " + year);
      stackPane.getChildren().clear();
      stackPane.getChildren().setAll(RevenueMonthDetail.getRevenueByMonthAndYear(month, year));
   }

   public static void switchToRevenueManagement() throws IOException {
      controller.setRevenueManagementScene();
   }

   public static void switchToContactManagement() throws IOException {
      controller.setContactManagementScene();
   }

   public static void switchToContactDetail(String contactID, String name, String phone) throws IOException {
      controller.setContactDetailScene(contactID, name, phone);
   }

   public static void switchToRevenueDetailByMonthAndYear(String monthName, int month, int year) throws IOException {
      controller.setRevenueDetailByMonthAndYear(monthName, month, year);
   }

   private static void switchToLogin() throws IOException {
      stage.close();
      Scene authScene = AuthScene.createScene(stage);
      stage.setScene(authScene);
      stage.setMinWidth(480);
      stage.setWidth(480);
      stage.setMinHeight(880);
      stage.setHeight(880);
      stage.centerOnScreen();
      stage.setResizable(false);
      stage.setMaximized(false);
      stage.setIconified(false);
      stage.show();
   }

   private void handleLogout() {
      logOutBtn.setOnMouseClicked(e -> {
         try {
            File rememberFile = new File("remember.json");
            if (rememberFile.exists()) {
               rememberFile.delete();
            }
            AdminDashboardScene.switchToLogin();
            Auth.logout();
         } catch (IOException ex) {
            ex.printStackTrace();
         }
      });
   }
}
