package com.rentalhouse.app.views.containers.Dashboard.InfrastructureDetail;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import com.rentalhouse.app.controllers.InfrastructureController;
import com.rentalhouse.app.controllers.InvoiceController;
import com.rentalhouse.app.controllers.TelegramSender;
import com.rentalhouse.app.controllers.TenantController;
import com.rentalhouse.app.controllers.UserController;
import com.rentalhouse.app.middlewares.ValidateInput;
import com.rentalhouse.app.models.Infrastructure;
import com.rentalhouse.app.models.Invoice;
import com.rentalhouse.app.models.Tenant;
import com.rentalhouse.app.views.components.AlertPopup.AlertPopup;
import com.rentalhouse.app.views.components.CreateTenantForm.CreateTenantForm;
import com.rentalhouse.app.views.components.EditInfrastructure.EditInfrastructureScene;
import com.rentalhouse.app.views.components.ElectricityAndWaterNumbersForm.ElectricityAndWaterNumbersForm;
import com.rentalhouse.app.views.components.TenantInformation.TenantInformation;
import com.rentalhouse.app.views.components.invoice.CustomStageInvoice;
import com.rentalhouse.app.views.components.invoice.InvoiceInfrastructure;
import com.rentalhouse.app.views.components.invoice.InvoicePDF;
// import com.rentalhouse.app.views.components.invoice.TableInvoice;
import com.rentalhouse.app.views.components.invoice.InvoicesTable;
import com.rentalhouse.app.views.containers.Dashboard.DashboardScene;
import com.rentalhouse.app.views.customStages.CustomStage;
import com.rentalhouse.utils.Uuid;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

public class InfrastructureDetailScene {
   private static Infrastructure infrastructure;
   private static final InfrastructureController infrastructureController = new InfrastructureController();
   private static TenantController tenantController = new TenantController();
   public static List<Tenant> tenants;
   public static Boolean flag = false;

   @FXML
   private AnchorPane backBtn;
   @FXML
   private AnchorPane editInfrastructureBtn;
   @FXML
   private ScrollPane scrollPane;
   @FXML
   private Label listedPrice;
   @FXML
   private Label electricityTotalPrice;
   @FXML
   private Label waterTotalPrice;
   @FXML
   private Label total;
   @FXML
   private Button infrastructure_createInvoice;
   @FXML
   private Button update_electricity_water_numberBtn;
   @FXML
   private Button infrastructure_alertBTN;
   @FXML
   private TextField electricityField;
   @FXML
   private TextField waterField;
   @FXML
   private Label electricityError;
   @FXML
   private Label waterError;
   @FXML
   private Label oldElectricityNumber;
   @FXML
   private Label oldWaterNumber;
   @FXML
   private Label newElectricityNumber;
   @FXML
   private Label newWaterNumber;
   @FXML
   private Label totalElectricity;
   @FXML
   private Label totalWater;
   @FXML
   private Label electricityPrice;
   @FXML
   private Label waterPrice;
   @FXML
   private Label infrastructuredetail_currentElectric;
   @FXML
   private Label infrastructuredetail_currentWater;
   @FXML
   private Label infrastructuredetail_newElectric;
   @FXML
   private Label infrastructuredetail_newWater;
   @FXML
   private Label infrastructuredetail_totalElectricUsed;
   @FXML
   private Label infrastructuredetail_totalWaterUsed;
   private FlowPane contentTenantInformation = new FlowPane(20, 20);
   private InvoiceController invoiceController = new InvoiceController();
   public static FontAwesomeIcon iconBell;
   public static AnchorPane pane_alert;

   public static Parent getInfrastructureDetail(Infrastructure infrastructure) throws IOException {
      InfrastructureDetailScene.infrastructure = infrastructure;
      FXMLLoader loader = new FXMLLoader(InfrastructureDetailScene.class.getResource("infrastructureDetail.fxml"));
      Parent root = loader.load();
      return root;
   }

   public void initialize() {
      InvoicePDF.infrastructure = infrastructureController.getById(infrastructure.getId());
      setPrice(infrastructure.getId());
      electricityField.textProperty().addListener((obs, oldValue, newValue) -> {
         if (!newValue.trim().isEmpty() && newValue.length() > 6) {
            try {
               AlertPopup.error("Alert Input Field", newValue + " this value too long, only Under or Equal\n5 digits!");
            } catch (Exception e) {
               System.err.println("Error showing alert: " + e.getMessage());
            }
            Platform.runLater(() -> electricityField.setText(""));
         }
      });
      waterField.textProperty().addListener((obs, oldValue, newValue) -> {
         if (!newValue.trim().isEmpty() && newValue.length() > 6) {
            try {
               AlertPopup.error("Alert Input Field",
                     newValue + " this value too long, only Under or Equal\n5 digits!");
            } catch (Exception e) {
               System.err.println("Error showing alert: " + e.getMessage());
            }
            Platform.runLater(() -> waterField.setText(""));
         }
      });
      this.backBtn.setOnMouseClicked(e -> {
         try {
            DashboardScene.backToRentalManagement();
         } catch (IOException ex) {
            ex.printStackTrace();
         }
      });

      this.editInfrastructureBtn.setOnMouseClicked(e -> {
         try {
            openFormEditInfrastructure();
         } catch (Exception exception) {
            exception.printStackTrace();
         }
      });
      if (infrastructure.getTenant_quantity() < 1) {
         infrastructure_createInvoice.setDisable(true);
         update_electricity_water_numberBtn.setDisable(true);
         electricityField.setDisable(true);
         waterField.setDisable(true);
      }
      scrollPane.setContent(contentTenantInformation);
      contentTenantInformation.prefWidthProperty().bind(scrollPane.widthProperty());
      contentTenantInformation.setStyle("-fx-padding: 10px");
      scrollPane.setFitToWidth(true);
      scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

      tenants = tenantController.getTenantsByInfrastructureId(infrastructure.getId());

      CompletableFuture.runAsync(() -> {
         try {
            setPrice(infrastructure.getId());
            tenants = tenantController.getTenantsByInfrastructureId(infrastructure.getId());
            for (Tenant tenant : tenants) {
               try {
                  final Parent tenantInformation = TenantInformation.getTenantInformation(tenant);
                  Platform.runLater(() -> contentTenantInformation.getChildren().add(tenantInformation));
               } catch (Exception e) {
                  System.out.println(e.getMessage());
               }
            }
         } catch (Exception e) {
            System.out.println(e.getMessage());
         }
      });
      infrastructuredetail_currentElectric.setText(infrastructure.getElectricity_number().toString());
      infrastructuredetail_currentWater.setText(infrastructure.getWater_number().toString());
      infrastructuredetail_newElectric.setText(infrastructure.getNew_electricity_number().toString());
      infrastructuredetail_newWater.setText(infrastructure.getNew_water_number().toString());
      infrastructuredetail_totalElectricUsed
            .setText(String.valueOf(infrastructure.getNew_electricity_number() - infrastructure.getElectricity_number())
                  + " kwh/month");
      infrastructuredetail_totalWaterUsed.setText(
            String.valueOf(infrastructure.getNew_water_number() - infrastructure.getWater_number()) + " m³/month");
      infrastructuredetail_totalElectricUsed.setStyle("-fx-text-fill: Red;");
      infrastructuredetail_totalWaterUsed.setStyle("-fx-text-fill: Red;");
      electricityPrice.setText("($" + infrastructure.getElectricity_price() + "/kwh)");
      waterPrice.setText("($" + infrastructure.getWater_price() + "/m³)");
      electricityField.setText(infrastructure.getNew_electricity_number().toString());
      waterField.setText(infrastructure.getNew_water_number().toString());

   }

   public static void setAlertPane(AnchorPane pane) {
      pane_alert = pane;
   }

   @FXML
   private void openFormEnterElectricityAndWaterNumbers() throws IOException {
      ElectricityAndWaterNumbersForm.show(InfrastructureDetailScene.infrastructure.getId());
   }

   @FXML
   private void AddANewTenant() throws IOException {
      CreateTenantForm.show(InfrastructureDetailScene.infrastructure.getId());
   }

   private void openFormEditInfrastructure() throws IOException {
      EditInfrastructureScene.show(InfrastructureDetailScene.infrastructure.getId());
   }

   public void setPrice(String id) {
      try {
         BigDecimal listedBill = infrastructure.getPrice();
         BigDecimal electricityBill = BigDecimal
               .valueOf(infrastructure.getNew_electricity_number() - infrastructure.getElectricity_number())
               .multiply(infrastructure.getElectricity_price());
         BigDecimal waterBill = BigDecimal
               .valueOf(infrastructure.getNew_water_number() - infrastructure.getWater_number())
               .multiply(infrastructure.getWater_price());

         listedPrice.setText("$" + listedBill.toString());
         electricityTotalPrice.setText("$" + electricityBill.toString());
         waterTotalPrice.setText("$" + waterBill.toString());
         total.setText("$" + electricityBill.add(waterBill).add(listedBill).toString());
      } catch (Exception e) {
         System.err.println(e.getMessage());
      }
   }

   public static Infrastructure getData() {
      return infrastructure;
   }

   public void confirmClicked() throws Exception {
      FXMLLoader fxmlLoader = new FXMLLoader(
            Objects.requireNonNull(InvoiceInfrastructure.class.getResource("invoice.fxml")));
      Parent root = fxmlLoader.load();
      Scene scene = new Scene(root);
      scene.setFill(Color.TRANSPARENT);
      new CustomStageInvoice(scene, root);
      InvoiceInfrastructure invoiceInfrastructure = fxmlLoader.getController();
      invoiceInfrastructure.setData(infrastructureController.getById(InfrastructureDetailScene.infrastructure.getId()));
   }

   public void showInvoice() throws Exception {
      // TableInvoice.infrastructureID = infrastructure.getId();
      // TableInvoice.electricPrice = infrastructure.getElectricity_price();
      // TableInvoice.waterPrice = infrastructure.getWater_price();
      // TableInvoice.infrastructureName = infrastructure.getName();
      InvoicesTable.infrastructureID = infrastructure.getId();
      InvoicesTable.electricPrice = infrastructure.getElectricity_price();
      InvoicesTable.waterPrice = infrastructure.getWater_price();
      InvoicesTable.infrastructureName = infrastructure.getName();
      FXMLLoader fxmlLoader = new FXMLLoader(
            Objects.requireNonNull(InvoiceInfrastructure.class.getResource("invoiceTable.fxml")));
      Parent root = fxmlLoader.load();
      Scene scene = new Scene(root);
      new CustomStage(scene, root);
   }

   @FXML
   private void updateElectricityAndWaterNumbers() throws IOException {
      String electricity = electricityField.getText();
      String water = waterField.getText();

      Map<String, String> errorMessages = new HashMap<>();
      if (ValidateInput.isEmpty(electricity)) {
         errorMessages.put("electricity", "Electricity number is required");
      } else if (!ValidateInput.isNumber(electricity)) {
         errorMessages.put("electricity", "Electricity number must be a number");
      } else if (Long.parseLong(electricity) < infrastructure.getElectricity_number()) {
         errorMessages.put("electricity", "New electricity number must be bigger than the old one");
      }

      if (ValidateInput.isEmpty(water)) {
         errorMessages.put("water", "Water number is required");
      } else if (!ValidateInput.isNumber(water)) {
         errorMessages.put("water", "Water number must be a number");
      } else if (Long.parseLong(water) < infrastructure.getWater_number()) {
         errorMessages.put("water", "New water number must be bigger than the old one");
      }

      if (!errorMessages.isEmpty()) {
         electricityError.setText(errorMessages.get("electricity"));
         waterError.setText(errorMessages.get("water"));
         return;
      }

      resetAllErrorMessage();
      if (infrastructureController.updateElectricityAndWaterNumbers(infrastructure.getId(), Long.parseLong(electricity),
            Long.parseLong(water))) {
         flag = true;
         resetAllField();
         DashboardScene.switchToInfrastructureDetail(infrastructure.getId());
      }
   }

   private void resetAllErrorMessage() {
      electricityError.setText("");
      waterError.setText("");
   }

   private void resetAllField() {
      electricityField.setText("");
      waterField.setText("");
   }

   public void clear() {
      electricityField.setText("");
      waterField.setText("");
      electricityError.setText("");
      waterError.setText("");
   }

   public void createInvoiceForInfras() {
      if (invoiceController.getInvoice(infrastructure.getId()) == null) {
         updateElectricWaterNew();
      } else {
         updateElectricWaterNew();
      }
   }

   public void alertCreateInvoice() {
      try {
         AlertPopup.confirm("Confirm Payment",
               "Have you wanna update Electricity and Water Number\nbefore Confirm Payment?");
         if (AlertPopup.getConfirmationResult()) {
            electricityField.requestFocus();
            electricityField.setText(String.valueOf(infrastructure.getNew_electricity_number()));
            waterField.setText(String.valueOf(infrastructure.getNew_water_number()));
            return;
         } else {
            invoiceController.create(createInvoice());
            if (infrastructureController.updateWaterAndElectricity(infrastructuredetail_newElectric.getText(),
                  infrastructuredetail_newWater.getText(), infrastructure.getId())) {
               try {
                  DashboardScene.switchToInfrastructureDetail(infrastructure.getId());
                  AlertPopup.confirm("Alert For Tenant", "Have you wanna notify for tenant\nin this "
                        + infrastructure.getName() + " ?\n\n\t\tYES to OK\n\t\tNo to Cancel.");
                  if (AlertPopup.getConfirmationResult()) {
                     TelegramSender.sendingTelegram(invoiceController.getInvoice(infrastructure.getId()),
                           new UserController().getById(infrastructure.getUser_id()), infrastructure.getName());
                     return;
                  }
               } catch (Exception e) {
                  return;
               }
            }
            return;
         }
      } catch (Exception e) {
         System.err.println(e.getMessage());
      }
   }

   // todo UPDATE ELECT - WATER NUMBER AFTER CLICKER 'UPDATE ELECT - WATER NUMBER':
   private void updateElectricWaterNew() {
      if ((ValidateInput.isEmpty(electricityField.getText().trim())
            && ValidateInput.isEmpty(waterField.getText().trim())) &&
            (infrastructuredetail_currentElectric.getText().equals(infrastructuredetail_newElectric.getText()) &&
                  infrastructuredetail_currentWater.getText().equals(infrastructuredetail_newWater.getText()))) {
         alertCreateInvoice();
         return;
      }

      if ((!ValidateInput.isEmpty(electricityField.getText().trim())
            && !ValidateInput.isEmpty(waterField.getText().trim())) &&
            (electricityField.getText().equals(infrastructuredetail_currentElectric.getText()) &&
                  waterField.getText().equals(infrastructuredetail_currentWater.getText()))) {
         alertCreateInvoice();
         return;
      }
      if (!infrastructuredetail_currentElectric.getText().equals(infrastructuredetail_newElectric.getText()) ||
            !infrastructuredetail_currentWater.getText().equals(infrastructuredetail_newWater.getText())) {
         alertCreateInvoice();
         return;
      }

      if ((!ValidateInput.isEmpty(electricityField.getText().trim())
            && !ValidateInput.isEmpty(waterField.getText().trim())) &&
            (!electricityField.getText().equals(infrastructuredetail_currentElectric.getText()) ||
                  !waterField.getText().equals(infrastructuredetail_currentWater.getText()))) {
         try {
            AlertPopup.warning("Confirm Payment", "You missed update new Electric OR Water Number, recheck please!");
            System.out.println("this Scope 1");
            update_electricity_water_numberBtn.requestFocus();
            return;
         } catch (Exception e) {
            System.err.println(e.getMessage());
         }
      }

      if ((!ValidateInput.isEmpty(electricityField.getText().trim())
            || !ValidateInput.isEmpty(waterField.getText().trim())) &&
            (!electricityField.getText().equals(infrastructuredetail_currentElectric.getText()) ||
                  !waterField.getText().equals(infrastructuredetail_currentWater.getText()))) {
         try {
            AlertPopup.warning("Confirm Payment", "You missed update new Electric OR Water Number, recheck please!");
            update_electricity_water_numberBtn.requestFocus();
            return;
         } catch (Exception e) {
            System.err.println(e.getMessage());
         }
      }
   }

   public static void reload() throws Exception {
      DashboardScene.switchToInfrastructureDetail(infrastructure.getId());
   }

   private Invoice createInvoice() {
      BigDecimal electricPrice = new BigDecimal(
            (infrastructure.getNew_electricity_number() - infrastructure.getElectricity_number()))
            .multiply(infrastructure.getElectricity_price());
      BigDecimal waterPrice = new BigDecimal((infrastructure.getNew_water_number() - infrastructure.getWater_number()))
            .multiply(infrastructure.getWater_price());
      return new Invoice(Uuid.get(),
            infrastructure.getId(),
            String.valueOf(infrastructure.getPrice()),
            String.valueOf(waterPrice),
            String.valueOf(electricPrice),
            String.valueOf(infrastructure.getElectricity_number()),
            String.valueOf(infrastructure.getNew_electricity_number()),
            String.valueOf(infrastructure.getWater_number()),
            String.valueOf(infrastructure.getNew_water_number()),
            String.valueOf(infrastructure.getPrice().add(waterPrice).add(electricPrice)));
   }
}
