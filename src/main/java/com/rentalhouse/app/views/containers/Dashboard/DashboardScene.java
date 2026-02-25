package com.rentalhouse.app.views.containers.Dashboard;

import java.io.File;
import java.io.IOException;
import com.rentalhouse.app.controllers.InfrastructureController;
import com.rentalhouse.app.models.Auth;
import com.rentalhouse.app.models.Infrastructure;
import com.rentalhouse.app.models.User;
import com.rentalhouse.app.views.components.ExpirationPopup.RentalExpiration;
import com.rentalhouse.app.views.components.UserProfile.UserProfileForm;
import com.rentalhouse.app.views.containers.Auth.AuthScene;
import com.rentalhouse.app.views.containers.Dashboard.Contact.ContactScene;
import com.rentalhouse.app.views.containers.Dashboard.CreateInfrastructure.CreateInfrastructureScene;
import com.rentalhouse.app.views.containers.Dashboard.InfrastructureDetail.InfrastructureDetailScene;
import com.rentalhouse.app.views.containers.Dashboard.RentalManagement.RentalManagementScene;
import com.rentalhouse.app.views.containers.Dashboard.RevenueManagement.RevenueManagementScene;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DashboardScene {
   private static Stage stage;
   private static Parent root;
   private static Scene scene;
   private static DashboardScene controller;
   private static String styleActivePane = "-fx-background-color: white; -fx-background-radius: 10;";
   private static String initStyleBackgroundPane = "-fx-background-color: #8285FF; -fx-background-radius: 10;";
   private static InfrastructureController infrastructureController = new InfrastructureController();
   private User userAuth = Auth.getUser();

   public static String infrastructureID = null;

   @FXML
   private StackPane stackPane;
   @FXML
   private AnchorPane logOutBtn;
   @FXML
   private AnchorPane rentalBtn;
   @FXML
   private Pane rentalIconPane;
   @FXML
   private Label rentalLabel;
   @FXML
   private AnchorPane revenueBtn;
   @FXML
   private Pane revenueIconPane;
   @FXML
   private Label revenueLabel;
   @FXML
   private AnchorPane createInfrastructureBtn;
   @FXML
   private Pane createInfrastructureIconPane;
   @FXML
   private Label createInfrastructureLabel;
   @FXML
   private AnchorPane contactBtn;
   @FXML
   private Pane contactIconPane;
   @FXML
   private Label contactLabel;

   @FXML
   private Label titleSceneLabel;
   @FXML
   private AnchorPane userProfile;
   @FXML
   private FontAwesomeIcon alert_bell;
   @FXML
   private AnchorPane pane_alert;

   public static Scene createScene(Stage stage) {
      try {
         FXMLLoader loader = new FXMLLoader(DashboardScene.class.getResource("dashboardScene.fxml"));
         root = loader.load();
         scene = new Scene(root);
         controller = loader.getController();
         DashboardScene.stage = stage;
         DashboardScene.stage.setResizable(true);
         DashboardScene.stage.setMinWidth(1200);
         DashboardScene.stage.setMinHeight(800);
         return scene;
      } catch (IOException e) {
         e.printStackTrace();
         return null;
      }
   }

   private void init() throws IOException {
      setRentalManagementScene();
      handleLogout();
      onClickRental();
      onClickRevenue();
      onClickInfrastructure();
      onClickUserProfile();
      onClickContract();
   }

   public void initialize() throws IOException {
      init();
      InfrastructureDetailScene.setAlertPane(pane_alert);
   }

   private void onClickRental() {
      rentalBtn.setOnMouseClicked(e -> {
         try {
            userAuth = Auth.getUser();
            RentalExpiration.checkExpiration(userAuth);

         } catch (Exception exception) {
            exception.printStackTrace();
         }
         setActiveRentalBtn();
         this.titleSceneLabel.setText("Rental management");
         try {
            setRentalManagementScene();
         } catch (IOException ex) {
            ex.printStackTrace();
         }
      });
   }

   private void onClickRevenue() {
      revenueBtn.setOnMouseClicked(e -> {
         try {
            userAuth = Auth.getUser();
            RentalExpiration.checkExpiration(userAuth);

         } catch (Exception exception) {
            exception.printStackTrace();
         }
         setActiveRevenueBtn();
         this.titleSceneLabel.setText("Revenue management");
         try {
            setRevenueManagementScene();
         } catch (IOException ex) {
            ex.printStackTrace();
         }
      });
   }

   private void onClickInfrastructure() {
      createInfrastructureBtn.setOnMouseClicked(e -> {
         try {
            userAuth = Auth.getUser();
            RentalExpiration.checkExpiration(userAuth);

         } catch (Exception exception) {
            exception.printStackTrace();
         }
         setActiveInfrastructureBtn();
         this.titleSceneLabel.setText("Create a infrastructure");
         try {
            setCreateInfrastructureScene();
         } catch (IOException ex) {
            ex.printStackTrace();
         }
      });
   }

   private void onClickUserProfile() {
      userProfile.setOnMouseClicked(e -> {
         try {
            userAuth = Auth.getUser();
            RentalExpiration.checkExpiration(userAuth);

         } catch (Exception exception) {
            exception.printStackTrace();
         }
         try {
            UserProfileForm.show(userAuth);
         } catch (Exception exception) {
            System.err.println(exception.getMessage());

         }
      });
   }

   private void onClickContract() {
      contactBtn.setOnMouseClicked(e -> {
         try {
            userAuth = Auth.getUser();
            RentalExpiration.checkExpiration(userAuth);

         } catch (Exception exception) {
            exception.printStackTrace();
         }
         setActiveContactBtn();
         this.titleSceneLabel.setText("Contact support");
         try {
            setContactScene();
         } catch (Exception ex) {
            ex.printStackTrace();
         }
      });
   }

   private void setActiveRentalBtn() {
      rentalIconPane.setStyle(styleActivePane);
      revenueIconPane.setStyle(initStyleBackgroundPane);
      createInfrastructureIconPane.setStyle(initStyleBackgroundPane);
      contactIconPane.setStyle(initStyleBackgroundPane);

      rentalLabel.setTextFill(Color.web("white"));
      revenueLabel.setTextFill(Color.web("#8285FF"));
      createInfrastructureLabel.setTextFill(Color.web("#8285FF"));
      contactLabel.setTextFill(Color.web("#8285FF"));
   }

   private void setActiveRevenueBtn() {
      rentalIconPane.setStyle(initStyleBackgroundPane);
      revenueIconPane.setStyle(styleActivePane);
      createInfrastructureIconPane.setStyle(initStyleBackgroundPane);
      contactIconPane.setStyle(initStyleBackgroundPane);

      rentalLabel.setTextFill(Color.web("#8285FF"));
      revenueLabel.setTextFill(Color.web("white"));
      createInfrastructureLabel.setTextFill(Color.web("#8285FF"));
      contactLabel.setTextFill(Color.web("#8285FF"));
   }

   private void setActiveInfrastructureBtn() {
      rentalIconPane.setStyle(initStyleBackgroundPane);
      revenueIconPane.setStyle(initStyleBackgroundPane);
      createInfrastructureIconPane.setStyle(styleActivePane);
      contactIconPane.setStyle(initStyleBackgroundPane);

      rentalLabel.setTextFill(Color.web("#8285FF"));
      revenueLabel.setTextFill(Color.web("#8285FF"));
      createInfrastructureLabel.setTextFill(Color.web("white"));
      contactLabel.setTextFill(Color.web("#8285FF"));
   }

   private void setActiveContactBtn() {
      contactIconPane.setStyle(styleActivePane);
      rentalIconPane.setStyle(initStyleBackgroundPane);
      revenueIconPane.setStyle(initStyleBackgroundPane);
      createInfrastructureIconPane.setStyle(initStyleBackgroundPane);

      contactLabel.setTextFill(Color.web("white"));
      rentalLabel.setTextFill(Color.web("#8285FF"));
      revenueLabel.setTextFill(Color.web("#8285FF"));
      createInfrastructureLabel.setTextFill(Color.web("#8285FF"));
   }

   private void setRentalManagementScene() throws IOException {
      this.titleSceneLabel.setText("Rental management");
      stackPane.getChildren().clear();
      stackPane.getChildren().setAll(RentalManagementScene.getRentalManagementScene());
   }

   private void setRevenueManagementScene() throws IOException {
      this.titleSceneLabel.setText("Revenue management");
      stackPane.getChildren().clear();
      stackPane.getChildren().setAll(RevenueManagementScene.getRevenueManagement());
   }

   private void setCreateInfrastructureScene() throws IOException {
      this.titleSceneLabel.setText("Create a infrastructure");
      stackPane.getChildren().clear();
      stackPane.getChildren().setAll(CreateInfrastructureScene.getCreateInfrastructure());
   }

   private void setContactScene() throws IOException {
      this.titleSceneLabel.setText("Contact support");
      stackPane.getChildren().clear();
      stackPane.getChildren().setAll(ContactScene.get());
   }

   private void setInfrastructureDetailScene(Infrastructure infrastructure) throws IOException {
      this.titleSceneLabel.setText("Infrastructure detail - " + infrastructure.getName());
      stackPane.getChildren().clear();
      stackPane.getChildren().setAll(InfrastructureDetailScene.getInfrastructureDetail(infrastructure));
   }

   public static void switchToInfrastructureDetail(String id) throws IOException {
      Infrastructure getInfrastructure = infrastructureController.getById(id);
      controller.setInfrastructureDetailScene(getInfrastructure);
   }

   public static void backToRentalManagement() throws IOException {
      controller.setRentalManagementScene();
   }

   public static void switchToLogin() throws IOException {
      stage.close();
      Scene authScene = AuthScene.createScene(stage);
      stage.setTitle("Login");
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
            DashboardScene.switchToLogin();
            Auth.logout();
         } catch (IOException ex) {
            ex.printStackTrace();
         }
      });
   }
}
