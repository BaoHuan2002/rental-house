package com.rentalhouse;

import com.rentalhouse.app.models.Auth;
import com.rentalhouse.app.views.components.ConnectDBFailed.ConnectDBFailed;
import com.rentalhouse.app.views.components.ExpirationPopup.RentalExpiration;
import com.rentalhouse.app.views.containers.Auth.AuthScene;
import com.rentalhouse.app.views.containers.Auth.Login.RememberLogin;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
   public static void main(String[] args) {
      launch();
   }

   @Override
   public void start(Stage primaryStage) throws Exception {
      Scene scene;
      String title;
      boolean isConnected = ConnectDBFailed.isConnected();
      if (isConnected) {
         scene = RememberLogin.createScene(primaryStage);
         if (scene == null) {
            scene = AuthScene.createScene(primaryStage);
            title = "Login";
         } else {
            title = "Rentify";
         }
      } else {
         scene = ConnectDBFailed.createScene(primaryStage);
         title = "No internet";
      }
      primaryStage.setTitle(title);
      primaryStage.setScene(scene);
      primaryStage.centerOnScreen();
      primaryStage.show();
      if (RememberLogin.checkData() == 2) {
         RentalExpiration.checkExpirationLogin(Auth.getUser());
      }
   }
}
