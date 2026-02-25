package com.rentalhouse.app.views.components.ExpirationPopup;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import com.rentalhouse.app.models.Auth;
import com.rentalhouse.app.models.User;
import com.rentalhouse.app.views.components.MembershipPackage.MembershipPackage;
import com.rentalhouse.app.views.containers.Dashboard.DashboardScene;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RentalExpiration {
   private static Parent root;
   private static Scene scene;
   private static Stage stage;

   @FXML
   private Label announcement;

   private static void init(FXMLLoader loader) throws IOException {
      root = loader.load();
      scene = new Scene(root);
      stage = new Stage();
      stage.setScene(scene);
      stage.setResizable(false);
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.setOnCloseRequest(event -> event.consume());
      stage.showAndWait();
   }

   public static void show() throws IOException {
      FXMLLoader loader = new FXMLLoader(RentalExpiration.class.getResource("rentalExpiration.fxml"));
      init(loader);
   }

   private static void initAnnouncement(FXMLLoader loader, String content) throws IOException {
      root = loader.load();
      RentalExpiration controller = loader.getController();
      controller.announcement.setText(content);
      scene = new Scene(root);
      stage = new Stage();
      stage.setScene(scene);
      stage.setResizable(false);
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.showAndWait();

   }

   public static void showAnnouncement(String content) throws IOException {
      FXMLLoader loader = new FXMLLoader(RentalExpiration.class.getResource("expirationAnnouncement.fxml"));
      initAnnouncement(loader, content);
   }

   @FXML
   private void logout() {
      try {
         DashboardScene.switchToLogin();
         Auth.logout();
         stage.close();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   @FXML
   private void exit() {
      stage.close();
   }

   @FXML
   private void openMembershipPackage() {
      try {
         MembershipPackage.show();
         stage.close();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public static void checkExpiration(User user) throws IOException {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      if (LocalDateTime.parse(user.getMembership_expire_at(), formatter).isBefore(LocalDateTime.now())) {
         show();
      }
   }

   public static void checkExpirationLogin(User user) throws IOException {
      try {
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");         
         Period period = Period.between(LocalDateTime.parse(user.getMembership_expire_at(), formatter).toLocalDate(),  LocalDate.now());
         if(user.getMembership_expire_at() != null && LocalDateTime.parse(user.getMembership_expire_at(), formatter).isBefore(LocalDateTime.now())) {
            RentalExpiration.show();
         }

         else if(period.getYears() == 0 && period.getMonths() == 0 && period.getDays() <= 30) {
            period = Period.between(LocalDateTime.parse(user.getMembership_expire_at(), formatter).toLocalDate(),  LocalDate.now());
            String content = period.getDays() != 0 ? "Your membership will expire in " + -1*period.getDays() + " days. Remember to renew " : "Your membership will expire today";
            RentalExpiration.showAnnouncement(content);
         }
      } catch (Exception e) {
         System.err.println(e.getMessage());
      }
   }

}
