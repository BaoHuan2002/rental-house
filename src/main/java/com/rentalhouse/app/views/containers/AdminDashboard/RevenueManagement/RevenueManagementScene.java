package com.rentalhouse.app.views.containers.AdminDashboard.RevenueManagement;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

import com.rentalhouse.app.controllers.UserController;
import com.rentalhouse.app.controllers.UserMembershipController;
import com.rentalhouse.app.views.components.RevenueCard.RevenueCard;
import com.rentalhouse.app.views.components.SpecRevenueCard.SpecRevenueCard;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class RevenueManagementScene {

   public static Parent getRevenueManagement() throws IOException {
      return FXMLLoader
            .load(RevenueManagementScene.class
                  .getResource("adminRevenueManagement.fxml"));
   }

   private final UserMembershipController userMembershipController = new UserMembershipController();
   private final List<Integer> listYear = userMembershipController.getAllYears();

   @FXML
   private ScrollPane scrollPane;

   private VBox revenueContent = new VBox(30);
   private VBox revenueItem;
   private Label title;
   private FlowPane revenueCardContainer;

   public void initialize() throws IOException {
      scrollPane.setContent(revenueContent);
      revenueContent.prefWidthProperty().bind(scrollPane.widthProperty());
      scrollPane.setFitToWidth(true);
      scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

      revenueContent.getChildren().add(createCurrentStatistics());

      listYear.forEach(e -> {
         revenueContent.getChildren().add(createRevenueByYear(e));
      });

   }

   private VBox createCurrentStatistics() throws IOException {
      UserController userController = new UserController();
      revenueItem = new VBox(20);
      revenueCardContainer = new FlowPane(20, 20);
      title = new Label("Current statistics - Lessor user");
      title.setFont(Font.font(null, FontWeight.BOLD, 28));
      Parent totalUser = SpecRevenueCard.get("Total", userController.getQuantityLessor() + "");
      Parent totalUser1 = SpecRevenueCard.get("Free Trial", userController.getQuantityLessorFreeTrial() + "");
      Parent totalUser2 = SpecRevenueCard.get("VIP", userController.getQuantityLessorVIP() + "");
      Parent totalUser3 = SpecRevenueCard.get("Expired", userController.getQuantityLessorExpired() + "");
      revenueCardContainer.getChildren().addAll(totalUser, totalUser1, totalUser2, totalUser3);
      revenueItem.getChildren().addAll(title, revenueCardContainer);
      return revenueItem;
   }

   private VBox createRevenueByYear(int yearValue) {
      revenueItem = new VBox(20);
      revenueCardContainer = new FlowPane(20, 20);

      title = new Label("#" + yearValue);
      title.setFont(Font.font(null, FontWeight.BOLD, 28));
      IntStream.rangeClosed(1, 12)
            .forEach(month -> {
               BigDecimal totalPrice = userMembershipController.getTotalPriceByMonthAndYear(month, yearValue);
               try {
                  Parent revenueCard = RevenueCard.get(month, yearValue, totalPrice);
                  revenueCardContainer.getChildren().add(revenueCard);
               } catch (IOException ex) {
                  ex.printStackTrace();
               }
            });
      revenueItem.getChildren().addAll(title, revenueCardContainer);
      return revenueItem;
   }

}
