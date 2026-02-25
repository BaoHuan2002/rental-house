package com.rentalhouse.app.views.components.ConnectDBFailed;

import java.io.IOException;

import com.rentalhouse.app.models.Auth;
import com.rentalhouse.app.views.components.ExpirationPopup.RentalExpiration;
import com.rentalhouse.app.views.containers.Auth.AuthScene;
import com.rentalhouse.app.views.containers.Auth.Login.RememberLogin;
import com.rentalhouse.database.connection.DatabaseConnection;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ConnectDBFailed {
   private Stage stage;
   private static Parent root;
   private static Scene scene;

   @FXML
   private ImageView no_connection;

   public static boolean isConnected() {
      return DatabaseConnection.getConnection() != null ? true : false;
   }

   public void initialize() {
      Image icon = new Image(ConnectDBFailed.class.getResourceAsStream("../../assets/icons/no_connection.jpg"));
      no_connection.setImage(icon);
      no_connection.setBlendMode(BlendMode.LIGHTEN);
   }

   public static Scene createScene(Stage stage) {
      try {
         FXMLLoader loader = new FXMLLoader(ConnectDBFailed.class.getResource("connectDBFailed.fxml"));
         root = loader.load();
         scene = new Scene(root);
         ConnectDBFailed controller = loader.getController();
         stage.setMinWidth(480);
         stage.setWidth(480);
         stage.setMinHeight(880);
         stage.setHeight(880);
         stage.setResizable(false);
         controller.setStage(stage);
         return scene;
      } catch (IOException e) {
         return null;
      }
   }

   public void refresh() throws IOException {
      if (DatabaseConnection.getConnection() != null) {
         stage.getScene().getWindow().hide();
         Scene scene = AuthScene.createScene(stage);
         stage.setTitle("Login");
         if (RememberLogin.createScene(stage) != null) {
            stage.setTitle("Rentify");
            scene = RememberLogin.createScene(stage);
            stage.setMinHeight(800);
            stage.setMinWidth(1200);
         }
         stage.setScene(scene);
         stage.centerOnScreen();
         stage.show();
         if(RememberLogin.checkData() == 2) {
            RentalExpiration.checkExpirationLogin(Auth.getUser());
         }
      }
   }

   public void setStage(Stage stage) {
      this.stage = stage;
   }
}
