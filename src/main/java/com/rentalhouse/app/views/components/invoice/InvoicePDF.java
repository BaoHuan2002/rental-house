package com.rentalhouse.app.views.components.invoice;
import com.rentalhouse.app.controllers.UserController;
import com.rentalhouse.app.models.Infrastructure;
import com.rentalhouse.app.models.Invoice;
import com.rentalhouse.app.models.User;
import com.rentalhouse.app.views.containers.Dashboard.InfrastructureDetail.InfrastructureDetailScene;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class InvoicePDF implements Initializable {
   @FXML
   private Label invoicePDF_electricNumber;
   @FXML
   private Label invoicePDF_electric_price;
   @FXML
   private Label invoicePDF_hoster_name;
   @FXML
   private Label invoicePDF_hoster_email;
   @FXML
   private Label invoicePDF_hoster_tel;
   @FXML
   private Label invoicePDF_new_electricNumber;
   @FXML
   private Label invoicePDF_new_waterNumber;
   @FXML
   private Label invoicePDF_priceForRent;
   @FXML
   private Label invoicePDF_total_price;
   @FXML
   private Label invoicePDF_waterNumber;
   @FXML
   private Label invoicePDF_water_price;
   @FXML
   private AnchorPane invoice_formPDF;
   @FXML
   private Button invoicePDF_buttonSAVEPDF;
   @FXML
   private Label invoicePDF_electricNumber_totalUsed;
   @FXML
   private Label invoicePDF_waterNumber_totalUsed;
   @FXML
   private Label invoicePDF_date;
   @FXML
   private Label invoicePDF_ID;
   @FXML
   private Label invoicePDF_electPrice;
   @FXML
   private Label invoicePDF_waterPrice;

   public static Infrastructure infrastructure;

   @Override
   public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
      // todo;
   }

   public User getUser () {
      return new UserController().getById(InfrastructureDetailScene.getData().getUser_id());
   }

   public void setData(Invoice invoice) {
      if (invoice != null) {
         try {
            Long electricNumber = Long.parseLong(invoice.getNewElectricity_number()) - Long.parseLong(invoice.getOldElectricity_number());
            Long waterNumber = Long.parseLong(invoice.getNewWater_number()) - Long.parseLong(invoice.getOldWater_number());

            DecimalFormatSymbols symbols = new DecimalFormatSymbols(java.util.Locale.getDefault());
            symbols.setGroupingSeparator('.');
            symbols.setDecimalSeparator(',');
            DecimalFormat df = new DecimalFormat("#,##0.##", symbols);

            invoicePDF_ID.setText(invoice.getInvoiveID());
            invoicePDF_date.setText(String.valueOf(invoice.getCreate_at()));
            invoicePDF_electricNumber.setText(df.format(Long.parseLong(invoice.getOldElectricity_number())));
            invoicePDF_new_electricNumber.setText(df.format(Long.parseLong(invoice.getNewElectricity_number())));
            invoicePDF_waterNumber.setText(invoice.getOldWater_number());
            invoicePDF_new_waterNumber.setText(df.format(Long.parseLong(invoice.getNewWater_number())));
            invoicePDF_electric_price.setText(df.format(new BigDecimal(invoice.getElectricity_price())));
            invoicePDF_water_price.setText(df.format(new BigDecimal(invoice.getWater_price())));
            invoicePDF_electricNumber_totalUsed.setText(df.format(electricNumber) + " Kw/ month");
            invoicePDF_waterNumber_totalUsed.setText(df.format(waterNumber) + " m3/ month");

            invoicePDF_electric_price.setText("$" + df.format(new BigDecimal(invoice.getElectricity_price())));
            invoicePDF_water_price.setText("$" + df.format(new BigDecimal(invoice.getWater_price())));
            invoicePDF_priceForRent.setText("$" + df.format(new BigDecimal(invoice.getPrice())));
            invoicePDF_total_price.setText("$" + df.format(new BigDecimal(invoice.getTotal_price())));
            invoicePDF_hoster_name.setText(getUser().getName());
            invoicePDF_hoster_email.setText(getUser().getEmail());
            invoicePDF_hoster_tel.setText(getUser().getPhone());
            invoicePDF_electPrice.setText(infrastructure.getElectricity_price().toString() + " * Electricity number to used total");
            invoicePDF_waterPrice.setText(infrastructure.getElectricity_price().toString() + " * Water number to used total");;
         } catch (NumberFormatException e) {
            System.err.println("Show detail invoice failed => " + e.getMessage());
         }
      } else {
         return;
      }
   }

   public void savePDF() {
      PDFgenerator.showWindowToSave(invoice_formPDF);
   }
}
