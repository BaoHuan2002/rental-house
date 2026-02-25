package com.rentalhouse.app.views.containers.Auth.Login;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;

import org.json.JSONObject;

import com.rentalhouse.app.controllers.AuthController;
import com.rentalhouse.app.views.containers.AdminDashboard.AdminDashboardScene;
import com.rentalhouse.app.views.containers.Dashboard.DashboardScene;
import com.rentalhouse.utils.Encryption;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class RememberLogin {
   public static Scene createScene(Stage stage) {
      if (checkData() == 2) {
         Scene dashboard = DashboardScene.createScene(stage);
         stage.setMinHeight(800);
         stage.setMinWidth(1200);
         stage.centerOnScreen();
         return dashboard;
      } else if (checkData() == 1) {
         Scene dashboard = AdminDashboardScene.createScene(stage);
         stage.setMinHeight(800);
         stage.setMinWidth(1200);
         stage.centerOnScreen();
         return dashboard;
      }
      return null;

   }

   public static int checkData() {
      AuthController authController = new AuthController();
      File rememberFile = new File("remember.json");
      if (rememberFile.exists()) {
         String filePath = "remember.json";
         try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            StringBuilder content = new StringBuilder();
            int character;
            while ((character = br.read()) != -1) {
               content.append((char) character);
            }
            br.close();
            JSONObject info = new JSONObject(content.toString());
            String emailOrPhone = Encryption.decrypt(info.getString("emailOrPhone"));
            String password = Encryption.decrypt(info.getString("password"));
            String date = Encryption.decrypt(info.getString("date"));
            int role = Integer.parseInt(Encryption.decrypt(info.getString("role")));
            if (LocalDate.parse(date).isEqual(LocalDate.now())) {
               rememberFile.delete();
               return 0;
            }
            if (authController.login(emailOrPhone, password)) {
               if (role == 1) {
                  return 1;
               } else if (role == 2) {
                  return 2;
               }
            }
         } catch (Exception e) {
            System.err.println(e.getMessage());
            return 0;
         }
      }
      return 0;
   }
}
