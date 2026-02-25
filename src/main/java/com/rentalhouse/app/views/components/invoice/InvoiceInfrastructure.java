package com.rentalhouse.app.views.components.invoice;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

import com.rentalhouse.app.controllers.InvoiceController;
import com.rentalhouse.app.models.Infrastructure;
import com.rentalhouse.app.models.Invoice;
import com.rentalhouse.app.views.containers.Dashboard.InfrastructureDetail.InfrastructureDetailScene;
import com.rentalhouse.utils.Uuid;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class InvoiceInfrastructure implements Initializable {
   @FXML
   private Button invoice_closeBTN;
   @FXML
   private AnchorPane invoice_form;
   @FXML
   private Button infrastructure_invoice_confirmBTN;
   @FXML
   private Label infrastructure_invoice_labelDate;
   @FXML
   private Label infrastructure_invoice_label_ElecNumber;
   @FXML
   private Label infrastructure_invoice_label_InfrasName;
   @FXML
   private Label infrastructure_invoice_label_WaterPrice;
   @FXML
   private Label infrastructure_invoice_label_lElectPrice;
   @FXML
   private Label infrastructure_invoice_label_listedPrice;
   @FXML
   private Label infrastructure_invoice_label_newElectNumber;
   @FXML
   private Label infrastructure_invoice_label_newWaterNumber;
   @FXML
   private Label infrastructure_invoice_label_tenantQuantity;
   @FXML
   private Label infrastructure_invoice_label_totalPrice;
   @FXML
   private Label infrastructure_invoice_label_waterNumber;

   private InvoiceController invoiceController;

   public InvoiceInfrastructure() {
      invoiceController = new InvoiceController();
   }

   // WHEN PROGRAM FIRST START, METHOD TO DISABLE CONFIRM BUTTON IF
   // NEW_ELECTRIC_NUMBER AND NEW_WATER_NUMBER THE SAME WITHIN DATABASE
   // DISABLE TO FALSE FOR CONFIRM BUTTON IF VALUE HAS CHANGED (CLICK BUTTON ADD
   // NEW ELECTRIC OR WATER);
   @Override
   public void initialize(URL location, ResourceBundle resources) {
      showData(InfrastructureDetailScene.getData());
      for (Invoice invoice : invoiceController.getAll()) {
         if (invoiceController.isExisted(invoice)) {
            if (invoice.getInfrastructureID().equals(InfrastructureDetailScene.getData().getId())) {
               if (Long.parseLong(invoice.getNewElectricity_number()) == Long
                     .parseLong(infrastructure_invoice_label_newElectNumber.getText()) &&
                     Long.parseLong(invoice.getNewWater_number()) == Long
                           .parseLong(infrastructure_invoice_label_newWaterNumber.getText())) {
                  infrastructure_invoice_confirmBTN.setDisable(true);
               } else {
                  // infrastructure_invoice_confirmBTN.setDisable(false);
               }
            }
         }
      }
   }

   public void setData(Infrastructure infrastructure) {
      showData(infrastructure);
   }

   public void close() {
      invoice_closeBTN.getScene().getWindow().hide();
   }

   public void showData(Infrastructure infrastructure) {
      if (infrastructure != null) {
         Long electricNumber = infrastructure.getNew_electricity_number() - infrastructure.getElectricity_number();
         Long waterNumber = infrastructure.getNew_water_number() - infrastructure.getWater_number();
         BigDecimal electricPrice = BigDecimal.valueOf(electricNumber).multiply(infrastructure.getElectricity_price());
         BigDecimal waterPrice = BigDecimal.valueOf(waterNumber).multiply(infrastructure.getWater_price());
         BigDecimal totalPrice = electricPrice.add(waterPrice).add(infrastructure.getPrice());

         infrastructure_invoice_label_InfrasName.setText(infrastructure.getName());
         infrastructure_invoice_label_listedPrice.setText(infrastructure.getPrice().toString());
         infrastructure_invoice_label_ElecNumber.setText(infrastructure.getElectricity_number().toString());
         infrastructure_invoice_label_newElectNumber.setText(infrastructure.getNew_electricity_number().toString());
         infrastructure_invoice_label_lElectPrice.setText(infrastructure.getElectricity_price().toString());
         infrastructure_invoice_label_waterNumber.setText(infrastructure.getWater_number().toString());
         infrastructure_invoice_label_newWaterNumber.setText(infrastructure.getNew_water_number().toString());
         infrastructure_invoice_label_WaterPrice.setText(infrastructure.getWater_price().toString());
         infrastructure_invoice_label_tenantQuantity.setText(String.valueOf(infrastructure.getTenant_quantity()));
         infrastructure_invoice_label_totalPrice.setText(String.valueOf(totalPrice));
      } else {
         System.err.println("Show invoice occurred an Error!");
      }
   }

   public Invoice createInvoice() {
      return new Invoice(Uuid.get(),
            InfrastructureDetailScene.getData().getId(),
            infrastructure_invoice_label_listedPrice.getText(),
            infrastructure_invoice_label_WaterPrice.getText(),
            infrastructure_invoice_label_lElectPrice.getText(),
            infrastructure_invoice_label_ElecNumber.getText(),
            infrastructure_invoice_label_newElectNumber.getText(),
            infrastructure_invoice_label_waterNumber.getText(),
            infrastructure_invoice_label_newWaterNumber.getText(),
            infrastructure_invoice_label_totalPrice.getText());
   }

   // HANDLE ADD LISTENING BUTTON CONFIRM SIDE VIEW INFRASTRUCTURE DETAIL TO
   // DISABLE AS TRUE FOR CONFIRM BUTTON WITHIN STAGE SHOW DETAIL INFRASTRUCTURE:
   public void confirmInvoice() {
      // if (invoiceController.getAll() == null) {
      // invoiceController.create(createInvoice());
      // infrastructure_invoice_confirmBTN.setDisable(true);
      // return;
      // }
      // for (Invoice invoice : invoiceController.getAll()) {
      // if (invoiceController.isExisted(invoice)) {
      // if
      // (invoice.getInfrastructureID().equals(InfrastructureDetailScene.getData().getId()))
      // {
      // if (Long.parseLong(invoice.getNewElectricity_number()) ==
      // Long.parseLong(infrastructure_invoice_label_newElectNumber.getText()) &&
      // Long.parseLong(invoice.getNewWater_number()) ==
      // Long.parseLong(infrastructure_invoice_label_newWaterNumber.getText())) {
      // infrastructure_invoice_confirmBTN.setDisable(true);
      // System.out.println("Confirmation Occurred an Error!");
      // throw new IllegalArgumentException("Recheck value New_Water_number OR
      // New_Electric_Number has changed or NOT! ");
      // } else {
      // invoiceController.create(createInvoice());
      // infrastructure_invoice_confirmBTN.setDisable(true);
      // return;
      // }
      // }
      // }
      // }
      // invoiceController.create(createInvoice());
      // infrastructure_invoice_confirmBTN.setDisable(true);

   }
}
