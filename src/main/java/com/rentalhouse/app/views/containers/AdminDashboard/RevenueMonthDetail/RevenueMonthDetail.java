package com.rentalhouse.app.views.containers.AdminDashboard.RevenueMonthDetail;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.rentalhouse.app.controllers.UserController;
import com.rentalhouse.app.controllers.UserMembershipController;
import com.rentalhouse.app.models.User;
import com.rentalhouse.app.models.UserMembership;
import com.rentalhouse.app.views.containers.AdminDashboard.AdminDashboardScene;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class RevenueMonthDetail {

   private static RevenueMonthDetail controller;
   private final UserMembershipController userMembershipController = new UserMembershipController();
   private final UserController userController = new UserController();
   @FXML
   private Label freeTrialQuantity;

   @FXML
   private Label oneYearQuantity;

   @FXML
   private Label threeYearQuantity;

   @FXML
   private Label fiveYearQuantity;

   @FXML
   private Label totalPrice;

   @FXML
   private TableView<UserMembership> packagePurchaseTable;

   @FXML
   private TableColumn<UserMembership, String> ordinalNumber;
   @FXML
   private TableColumn<UserMembership, String> username;
   @FXML
   private TableColumn<UserMembership, Integer> membershipPackage;
   @FXML
   private TableColumn<UserMembership, BigDecimal> price;
   @FXML
   private TableColumn<UserMembership, String> date;

   private ObservableList<UserMembership> packagePurchaseList = FXCollections.observableArrayList();

   public static Parent getRevenueByMonthAndYear(int month, int year) throws IOException {
      FXMLLoader loader = new FXMLLoader(RevenueMonthDetail.class.getResource("revenueMonthDetail.fxml"));
      Parent parent = loader.load();
      controller = loader.getController();
      controller.init(month, year);
      return parent;
   }

   public void init(int month, int year) {
      int freeTrialQuantity = userMembershipController.getQuantityPackageByMonthAndYear(1, month, year);
      int oneYearQuantity = userMembershipController.getQuantityPackageByMonthAndYear(2, month, year);
      int threeYearQuantity = userMembershipController.getQuantityPackageByMonthAndYear(3, month, year);
      int fiveYearQuantity = userMembershipController.getQuantityPackageByMonthAndYear(4, month, year);
      BigDecimal totalPrice = userMembershipController.getTotalPriceByMonthAndYear(month, year);
      setupTable(month, year);
      this.freeTrialQuantity.setText(freeTrialQuantity + "");
      this.oneYearQuantity.setText(oneYearQuantity + "");
      this.threeYearQuantity.setText(threeYearQuantity + "");
      this.fiveYearQuantity.setText(fiveYearQuantity + "");
      this.totalPrice.setText("$ " + totalPrice);
   }

   @FXML
   private void back() throws IOException {
      AdminDashboardScene.switchToRevenueManagement();
   }

   private void setupTable(int month, int year) {
      ordinalNumber.setCellValueFactory(cellData -> new SimpleStringProperty(
            String.valueOf(packagePurchaseTable.getItems().indexOf(cellData.getValue()) + 1)));
      username.setCellValueFactory(cellData -> {
         String userId = cellData.getValue().getUser_id();
         User user = userController.getById(userId);
         return new SimpleStringProperty(user.getName());
      });
      membershipPackage.setCellValueFactory(new PropertyValueFactory<UserMembership, Integer>("membership_package"));
      membershipPackage.setCellFactory(TextFieldTableCell.forTableColumn(new MembershipPackage()));
      price.setCellValueFactory(new PropertyValueFactory<UserMembership, BigDecimal>("price"));
      date.setCellValueFactory(new PropertyValueFactory<UserMembership, String>("created_at"));

      List<UserMembership> membershipPackage = userMembershipController.getAllByMonthAndYear(month, year);
      membershipPackage.forEach(membership -> {
         packagePurchaseList.add(membership);
      });
      packagePurchaseTable.setItems(packagePurchaseList);
   }

}
