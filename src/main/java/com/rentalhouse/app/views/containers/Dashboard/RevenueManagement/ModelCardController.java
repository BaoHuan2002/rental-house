package com.rentalhouse.app.views.containers.Dashboard.RevenueManagement;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ModelCardController implements Initializable {
   @FXML
   private AnchorPane cardModel_form;
   
   @FXML
   private Label cardModel_month;

   @FXML
   private Label cardModel_revenue;

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      // todo;
      RevenueManagementScene.cardModel = cardModel_form;
      RevenueManagementScene.revenueLabel = cardModel_revenue;
   }

   public void setData(String month, BigDecimal revenue) {
      cardModel_month.setText(month);
      cardModel_revenue.setText(String.valueOf(revenue));
   }
}
