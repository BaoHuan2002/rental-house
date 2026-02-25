package com.rentalhouse.app.views.components.RevenueCard;

import java.io.IOException;
import java.math.BigDecimal;

import com.rentalhouse.app.views.containers.AdminDashboard.AdminDashboardScene;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class RevenueCard {

   private static RevenueCard controller;

   private static final String inActiveStyle = "-fx-background-color: linear-gradient(#737373, #D9D9D9); -fx-background-radius: 20;";

   @FXML
   BorderPane container;

   @FXML
   private Label monthNameLabel;

   @FXML
   private Label priceLabel;

   private int month;
   private int year;
   private BigDecimal price;

   private void serMonthYearPrice(int month, int year, BigDecimal price) {
      this.month = month;
      this.year = year;
      this.price = price;
   }

   public static Parent get(int month, int year, BigDecimal price) throws IOException {
      FXMLLoader loader = new FXMLLoader(RevenueCard.class.getResource("revenueCard.fxml"));
      BorderPane card = loader.load();
      controller = loader.getController();
      controller.serMonthYearPrice(month, year, price);
      controller.init();
      return card;
   }

   private void init() {
      controller.monthNameLabel.setText(getMonthName(month));
      if (price == null || price.toString().equals("0.00")) {
         controller.container.setStyle(inActiveStyle);
         controller.priceLabel.setText("$ --");
      } else {
         controller.priceLabel.setText("$ " + price.toString());
         switchToRevenueDetail(month, year);
      }
   }

   public void switchToRevenueDetail(int month, int year) {
      controller.container.setCursor(Cursor.HAND);
      controller.container.setOnMouseClicked(e -> {
         try {
            AdminDashboardScene.switchToRevenueDetailByMonthAndYear(getMonthName(month), month, year);
         } catch (Exception e1) {
            e1.printStackTrace();
         }
      });
   }

   private String getMonthName(int monthNumber) {
      String[] monthNames = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
      };
      if (monthNumber < 1 || monthNumber > 12) {
         throw new IllegalArgumentException("Month number must be between 1 and 12");
      }
      return monthNames[monthNumber - 1];
   }
}
