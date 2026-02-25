package com.rentalhouse.app.views.components.PaymentMethod;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.rentalhouse.app.controllers.UserController;
import com.rentalhouse.app.controllers.UserMembershipController;
import com.rentalhouse.app.models.Auth;
import com.rentalhouse.app.models.User;
import com.rentalhouse.app.models.UserMembership;
import com.rentalhouse.app.views.components.AlertPopup.AlertPopup;
import com.rentalhouse.app.views.components.MembershipPackage.MembershipPackage;
import com.rentalhouse.app.views.components.UserProfile.UserProfileForm;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PaymentMethod {
   private static final UserController userController = new UserController();
   private static final UserMembershipController userMembershipController = new UserMembershipController();
   private static UserMembership newUserMembership;
   private static Parent root;
   private static Stage stage;
   private static Scene scene;
   private static String styleActivePane = "-fx-background-radius: 20; " +
   "-fx-border-radius: 20; " +
   "-fx-border-color: linear-gradient(#5D5BDC, #1C2CD4); " +
   "-fx-border-width: 2;";

   private static String initStyleBackgroundPane = "-fx-background-radius: 20; " +
   "-fx-background-color: #D9D9D9; ";

   private boolean method = false;
   @FXML
   private ImageView image;

   @FXML
   private ImageView paypalImage;

   @FXML
   private ImageView visaImage;

   @FXML
   private ImageView mastercardImage;

   @FXML
   private Label packageName;

   @FXML
   private Label price;

   @FXML
   private Label paymentError;

   @FXML
   private AnchorPane paypalPane;

   @FXML
   private AnchorPane visaPane;

   @FXML
   private AnchorPane mastercardPane;

   @FXML private Button paymentMethod_close;

   public void initialize() {
      setImageView(image, "../../assets/icons/arrow-left-long-solid.png");
      setImageView(paypalImage, "../../assets/images/paypal_payment.png");
      setImageView(visaImage, "../../assets/images/visa_payment.png");
      setImageView(mastercardImage, "../../assets/images/mastercard_payment.png");

      switchToMembershipPackage();
      setActivePaypalPane();
      setActiveVisaPane();
      setActiveMastercardPane();
   }

   private void setImageView(ImageView imageView, String url) {
      Image image = new Image(PaymentMethod.class.getResourceAsStream(url));
      imageView.setImage(image);
      imageView.setBlendMode(BlendMode.MULTIPLY);
   }

   private static void init(FXMLLoader loader, String year, String cost) throws IOException {
      root = loader.load();
      User userAuth = Auth.getUser();
      PaymentMethod.newUserMembership = new UserMembership();
      PaymentMethod controller = loader.getController();
      controller.price.setText("Price: " + cost);
      if (year.equalsIgnoreCase("1 YEAR")) {
         controller.packageName.setText("VIP - 1 YEAR");
         newUserMembership.setMembership_package(2);
      } else if (year.equalsIgnoreCase("3 YEARS")) {
         controller.packageName.setText("VIP - 3 YEARS");
         newUserMembership.setMembership_package(3);
      } else {
         controller.packageName.setText("VIP - 5 YEARS");
         newUserMembership.setMembership_package(4);
      }
      newUserMembership.setUser_id(userAuth.getId());
      newUserMembership.setPrice(BigDecimal.valueOf(Integer.parseInt(cost.replaceAll("[^\\d]", ""))));
      scene = new Scene(root);
      stage = new Stage();
      stage.setScene(scene);
      stage.setResizable(false);
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.setOnCloseRequest(event -> event.consume());
      stage.show();
   }

   public static void show(String year, String cost) throws IOException {
      FXMLLoader loader = new FXMLLoader(PaymentMethod.class.getResource("paymentMethod.fxml"));
      init(loader, year, cost);
   }

   public void switchToMembershipPackage() {
      image.setOnMouseClicked(e -> {
         try {
            stage.close();
            MembershipPackage.show();
         } catch (Exception exception) {
            exception.printStackTrace();
         }
      });
   }

   public void onClickProgressToPayment() {
      if (method) {
         User userAuth = Auth.getUser();
         try {
            String membership_expire_at = userAuth.getMembership_expire_at();
            String[] parts = membership_expire_at.split("-");
            int newYear = Integer.parseInt(parts[0]);
            int year = 0;
            if (packageName.getText().equalsIgnoreCase("VIP - 1 YEAR")) {
               year = 1;
               newYear += year;
            } else if (packageName.getText().equalsIgnoreCase("VIP - 3 YEARS")) {
               year = 3;
               newYear += year;
            } else if (packageName.getText().equalsIgnoreCase("VIP - 5 YEARS")) {
               year = 5;
               newYear += year;
            }
            String new_expire = "";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");         
            if(LocalDateTime.parse(userAuth.getMembership_expire_at(), formatter).isAfter(LocalDateTime.now())) {
               new_expire = newYear + "-" + parts[1] + "-" + parts[2];
               userAuth.setMembership_expire_at(new_expire);

            }
            else {
               LocalDateTime now = LocalDateTime.now();
               newYear = now.getYear() + year;
               String newMonth = now.getMonthValue() < 10 ? "0" + now.getMonthValue() : String.valueOf(now.getMonthValue());
               String newDay = now.getDayOfMonth() < 10 ? "0" + now.getDayOfMonth() : String.valueOf(now.getDayOfMonth());
               String newHour = now.getHour() < 10 ? "0" + now.getHour() : String.valueOf(now.getHour());
               String newMinutes = now.getMinute() < 10 ? "0" + now.getMinute() : String.valueOf(now.getMinute());
               String newSecond = now.getSecond() < 10 ? "0" + now.getSecond() : String.valueOf(now.getSecond());
               String newTime = newHour + ":" + newMinutes + ":" + newSecond;
               new_expire = newYear + "-" + newMonth + "-" + newDay + " " + newTime;
               userAuth.setMembership_expire_at(new_expire);
            }
            userAuth.setMembership_package(2);
            userAuth.setPassword("");
            if (userController.update(userAuth)) {
               try {
                  userMembershipController.create(newUserMembership);
               
                  AlertPopup.success("Success", "Successfully renewed");
                  stage.close();
                  UserProfileForm.refresh();

               } catch (Exception e) {
               }
            }
         } catch (Exception e) {
            e.printStackTrace();
         }
      } else {
         paymentError.setText("Please choose a method to pay");
      }
   }

   private void setActivePaypalPane() {
      paypalPane.setOnMouseClicked(e -> {
         method = true;
         paymentError.setText("");
         paypalPane.setStyle(styleActivePane);
         visaPane.setStyle(initStyleBackgroundPane);
         mastercardPane.setStyle(initStyleBackgroundPane);
      });
   }

   private void setActiveVisaPane() {
      visaPane.setOnMouseClicked(e -> {
         method = true;
         paymentError.setText("");
         visaPane.setStyle(styleActivePane);
         paypalPane.setStyle(initStyleBackgroundPane);
         mastercardPane.setStyle(initStyleBackgroundPane);
      });
   }

   private void setActiveMastercardPane() {
      mastercardPane.setOnMouseClicked(e -> {
         method = true;
         paymentError.setText("");
         mastercardPane.setStyle(styleActivePane);
         paypalPane.setStyle(initStyleBackgroundPane);
         visaPane.setStyle(initStyleBackgroundPane);
      });
   }

}
