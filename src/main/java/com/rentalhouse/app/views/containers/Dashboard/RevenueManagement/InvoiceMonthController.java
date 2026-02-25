package com.rentalhouse.app.views.containers.Dashboard.RevenueManagement;

import com.rentalhouse.app.controllers.InfrastructureController;
import com.rentalhouse.app.models.Invoice;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class InvoiceMonthController implements Initializable {

   @FXML
   private AnchorPane card_invoice;
   @FXML
   private Label invoiceMonth_date;
   @FXML
   private Label invoiceMonth_infrasName;
   @FXML
   private Label invoiceMonth_listedPrice;
   @FXML
   private Label invoiceMonth_totalPrice;

   private InfrastructureController infrastructureController;

   public InvoiceMonthController() {
      this.infrastructureController = new InfrastructureController();
   }

   @Override
   public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
      styleHover();
   }

   public void setData(Invoice invoice) {
      invoiceMonth_date.setText(invoice.getCreate_at().toString());
      invoiceMonth_infrasName.setText(infrastructureController.getById(invoice.getInfrastructureID()).getName());
      invoiceMonth_listedPrice.setText("$" + String.valueOf(invoice.getPrice()));
      invoiceMonth_totalPrice.setText("$" + String.valueOf(invoice.getTotal_price()));
   }

   public void styleHover() {
      invoiceMonth_infrasName.setOnMouseEntered(event -> {
         invoiceMonth_infrasName.setStyle("""
                  -fx-pref-height: 90;
                  -fx-pref-width: 90;
                  -fx-background-color: linear-gradient(from 27.4882% 20.3791% to 100.0% 100.0%, #001140d4 0.0%, #111d86cf 100.0%);
                  -fx-background-radius: 500px;
                  -fx-border-radius: 500px;
                  -fx-border-width: 0.8px;
                  -fx-border-color: linear-gradient(#020f63, #000033);
                  -fx-font-size: 24px;
                  -fx-font-weight: bold;
                  -fx-alignment: CENTER;
                  -fx-text-fill: #fff;
                  -fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.3), 6, 0, 0, 0);
               """);
         AnchorPane.setTopAnchor(invoiceMonth_infrasName, 10.0);
         AnchorPane.setLeftAnchor(invoiceMonth_infrasName, 200.0);
      });

      invoiceMonth_infrasName.setOnMouseExited(event -> {
         invoiceMonth_infrasName.setStyle("");
         AnchorPane.setTopAnchor(invoiceMonth_infrasName, 45.0);
         AnchorPane.setLeftAnchor(invoiceMonth_infrasName, 220.0);
      });
   }
}
