package com.rentalhouse.app.views.components.SpecRevenueCard;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SpecRevenueCard {

   private static SpecRevenueCard controller;

   @FXML
   private Label title;

   @FXML
   private Label content;

   public static Parent get(String title, String content) throws IOException {
      FXMLLoader loader = new FXMLLoader(SpecRevenueCard.class.getResource("specRevenueCard.fxml"));
      VBox card = loader.load();
      controller = loader.getController();
      controller.init(title, content);
      return card;
   }

   private void init(String title, String content) {
      controller.title.setText(title);
      controller.content.setText(content);
   }
}
