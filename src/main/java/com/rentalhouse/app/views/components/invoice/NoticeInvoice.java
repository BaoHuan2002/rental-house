package com.rentalhouse.app.views.components.invoice;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import com.rentalhouse.app.controllers.NoticesController;
import com.rentalhouse.app.models.Invoice;
import com.rentalhouse.app.models.Notice;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class NoticeInvoice implements Initializable {
   @FXML private TextArea notice_description;
   @FXML private RadioButton notice_extend_radio;
   @FXML private GridPane notice_gridPaneNotices;
   @FXML private RadioButton notice_others_radio;
   @FXML private RadioButton notice_prePayment_radio;
   @FXML private TextField notice_receivedAmount;
   @FXML private TextField notice_remainingAmount;
   @FXML private Label notice_totalPrice;
   @FXML private Label notice_invoiceID;
   private NoticesController noticesController;
   private int col = 0;
   private int row = 1;
   public static Invoice invoice = null;

   public NoticeInvoice() {
      this.noticesController = new NoticesController();
   }

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      showInfoInvoice();
      showNotice();
   }

   public void showNotice() {
      List<Notice> notices = noticesController.getNotices(null);
      if (notices != null) {
         notices.stream().forEach(notice -> {
            try {
               FXMLLoader fxmlLoader = new FXMLLoader();
               NoticeController noticeController = fxmlLoader.getController();
               fxmlLoader.setLocation(getClass().getResource("notice.fxml"));
               AnchorPane lineNotice = fxmlLoader.load();
               noticeController.setData(notice);
               if (col == 1) {
                  col = 0;
                  row++;
               }
               notice_gridPaneNotices.add(lineNotice, col++, row);
               GridPane.setMargin(lineNotice, new Insets(0, 5, 5, 2));
            } catch (IOException e) {
               System.out.println(e.getMessage());
            }
         });
      }
   }

   public void showInfoInvoice() {
      if (invoice != null) {
      // notice_totalPrice.setText("TOTAL PRICE: " + invoice.getTotal_price());
      // notice_invoiceID.setText(invoice.getInvoiveID());
      System.out.println(invoice.toString());
      }
   }
}
