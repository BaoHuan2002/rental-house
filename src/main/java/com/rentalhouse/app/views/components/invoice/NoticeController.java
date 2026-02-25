package com.rentalhouse.app.views.components.invoice;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.ResourceBundle;
import com.rentalhouse.app.models.Invoice;
import com.rentalhouse.app.models.Notice;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class NoticeController implements Initializable {
   @FXML private Label panelNotice_case;
   @FXML private Text panelNotice_content;
   @FXML private Label panelNotice_date;
   @FXML private Label panelNotice_receiveAmount;
   @FXML private Label panelNotice_remainingAmount;
   @FXML private Button lineNotice_deleteBTN;
   @FXML private RadioButton notice_checkDone;
   @FXML private AnchorPane anchorPane_notice;

   public static GridPane gridPaneNotice;

   private Notice noticeID;
   private static Invoice invoice;

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      slideInFromLeft(anchorPane_notice);
      // TableInvoice.deleteNotice = lineNotice_deleteBTN;
      // TableInvoice.checkDone = notice_checkDone;
      // TableInvoice.noticePane = anchorPane_notice;

      InvoicesTable.deleteNotice = lineNotice_deleteBTN;
      InvoicesTable.checkDone = notice_checkDone;
   }

   public void setNotice(Notice data) {
      this.noticeID = data;
   }

   public Notice getNoticeID() {
      return noticeID;
   }

   public static void setInvoice(Invoice data) {
      invoice = data;
   }

   public Invoice getInvoice() {
      return invoice;
   }

   public void setData(Notice notice) {
      DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
      symbols.setGroupingSeparator('.');
      symbols.setDecimalSeparator(',');
      DecimalFormat df = new DecimalFormat("#,##0.##", symbols);

      panelNotice_case.setText(notice.getTitle());
      panelNotice_date.setText(notice.getCreate_date().toString());

      try {
         BigDecimal amountReceived = new BigDecimal(notice.getAmount_received().toString());
         BigDecimal remainingAmount = new BigDecimal(notice.getRemaining_amount().toString());

         panelNotice_receiveAmount.setText("$" + df.format(amountReceived));
         panelNotice_remainingAmount.setText("$" + df.format(remainingAmount));
      } catch (NumberFormatException e) {
         e.getMessage();
      }
      panelNotice_content.setText(notice.getDescription());
   }

   public void slideInFromLeft(AnchorPane pane) {
      pane.setTranslateX(-pane.getHeight());
      TranslateTransition tt = new TranslateTransition(Duration.millis(3000), pane);
      tt.setFromX(-pane.getWidth());
      tt.setToX(0);
      tt.play();
   }
}
