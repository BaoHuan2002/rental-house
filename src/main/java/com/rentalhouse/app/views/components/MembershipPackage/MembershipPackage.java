package com.rentalhouse.app.views.components.MembershipPackage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.rentalhouse.app.models.Auth;
import com.rentalhouse.app.models.User;
import com.rentalhouse.app.views.components.ExpirationPopup.RentalExpiration;
import com.rentalhouse.app.views.components.PaymentMethod.PaymentMethod;
import com.rentalhouse.app.views.customStages.CustomStage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MembershipPackage {
   private static Parent root;
   private static Stage stage;
   private static Scene scene;

   @FXML
   private ImageView image;

   @FXML
   private VBox oneYear;

   @FXML
   private VBox threeYears;

   @FXML
   private VBox fiveYears;

   @FXML
   private Label one_year;

   @FXML
   private Label three_year;

   @FXML
   private Label five_year;

   @FXML
   private Label price_one_year;

   @FXML
   private Label price_three_year;

   @FXML
   private Label price_five_year;

   @FXML
   private Button memberShip_closeBTN;

   public void initialize() {
      Image icon = new Image(
            MembershipPackage.class.getResourceAsStream("../../assets/icons/arrow-left-long-solid.png"));
      image.setImage(icon);
      switchToRentalExpiration();
      switchToPaymentMethod();
   }

   private static void init(FXMLLoader loader) throws IOException {
      root = loader.load();
      scene = new Scene(root);
      stage = new CustomStage(scene, root);

   }

   public static void show() throws IOException {
      FXMLLoader loader = new FXMLLoader(MembershipPackage.class.getResource("membershipPackage.fxml"));
      init(loader);
   }

   public void close() {
      memberShip_closeBTN.getScene().getWindow().hide();
   }

   public void switchToRentalExpiration() {
      image.setOnMouseClicked(e -> {
         try {
            User user = Auth.getUser();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            if (user.getMembership_expire_at() != null && LocalDateTime.parse(user.getMembership_expire_at(), formatter)
                  .isBefore(LocalDateTime.now())) {
               stage.close();
               RentalExpiration.show();
            } else {
               stage.close();
            }
         } catch (Exception exception) {
            exception.printStackTrace();
         }
      });
   }

   public void switchToPaymentMethod() {
      handleOnClick(oneYear, one_year.getText(), price_one_year.getText());
      handleOnClick(threeYears, three_year.getText(), price_three_year.getText());
      handleOnClick(fiveYears, five_year.getText(), price_five_year.getText());
   }

   public void handleOnClick(VBox vbox, String year, String price) {
      vbox.setOnMouseClicked(e -> {
         try {
            PaymentMethod.show(year, price);
            stage.close();
         } catch (Exception exception) {
            exception.printStackTrace();
         }
      });
   }
}
