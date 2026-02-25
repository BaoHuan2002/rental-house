package com.rentalhouse.app.views.components.InfrastructureCard;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import com.rentalhouse.app.controllers.InfrastructureController;
import com.rentalhouse.app.controllers.InvoiceController;
import com.rentalhouse.app.models.Auth;
import com.rentalhouse.app.models.Infrastructure;
import com.rentalhouse.app.models.Invoice;
import com.rentalhouse.app.models.User;
import com.rentalhouse.app.views.components.AlertPopup.AlertPopup;
import com.rentalhouse.app.views.components.ExpirationPopup.RentalExpiration;
import com.rentalhouse.app.views.containers.Dashboard.DashboardScene;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class InfrastructureCard {
   private static final String SVG_PATH_USER = "M8.75 10C10.0761 10 11.3479 9.47322 12.2855 8.53553C13.2232 7.59785 13.75 6.32608 13.75 5C13.75 3.67392 13.2232 2.40215 12.2855 1.46447C11.3479 0.526784 10.0761 0 8.75 0C7.42392 0 6.15215 0.526784 5.21447 1.46447C4.27678 2.40215 3.75 3.67392 3.75 5C3.75 6.32608 4.27678 7.59785 5.21447 8.53553C6.15215 9.47322 7.42392 10 8.75 10ZM6.96484 11.875C3.11719 11.875 0 14.9922 0 18.8398C0 19.4805 0.519531 20 1.16016 20H16.3398C16.9805 20 17.5 19.4805 17.5 18.8398C17.5 14.9922 14.3828 11.875 10.5352 11.875H6.96484Z";
   private static final String SVG_PATH_MOTEL = "M31.2558 13.9727C31.2558 14.957 30.4416 15.7281 29.5188 15.7281H27.7817L27.8197 24.4891C27.8197 24.6367 27.8089 24.7844 27.7926 24.932V25.8125C27.7926 27.0211 26.8209 28 25.6213 28H24.7528C24.6931 28 24.6334 28 24.5736 27.9945C24.4977 28 24.4217 28 24.3457 28H22.5815H21.2787C20.0791 28 19.1074 27.0211 19.1074 25.8125V24.5V21C19.1074 20.032 18.3312 19.25 17.3704 19.25H13.8963C12.9355 19.25 12.1593 20.032 12.1593 21V24.5V25.8125C12.1593 27.0211 11.1876 28 9.98796 28H8.68519H6.95358C6.87215 28 6.79073 27.9945 6.70931 27.9891C6.64417 27.9945 6.57903 28 6.51389 28H5.64537C4.44573 28 3.47407 27.0211 3.47407 25.8125V19.6875C3.47407 19.6383 3.47407 19.5836 3.4795 19.5344V15.7281H1.73704C0.759954 15.7281 0 14.9625 0 13.9727C0 13.4805 0.162847 13.043 0.542824 12.6602L14.4608 0.4375C14.8408 0.0546875 15.2751 0 15.655 0C16.035 0 16.4693 0.109375 16.795 0.382812L30.6587 12.6602C31.093 13.043 31.3101 13.4805 31.2558 13.9727Z";
   private static final String SVG_PATH_WHOLE_HOUSE = "M0 26.6875V9.36796C0 7.93515 0.86529 6.64999 2.18771 6.11953L16.767 0.262494C17.1806 0.0929626 17.6432 0.0929626 18.0622 0.262494L32.6416 6.11953C33.964 6.64999 34.8293 7.94062 34.8293 9.36796V26.6875C34.8293 27.4148 34.247 28 33.5232 28H30.911C30.1872 28 29.6049 27.4148 29.6049 26.6875V12.25C29.6049 11.282 28.8267 10.5 27.8634 10.5H6.96585C6.00261 10.5 5.22439 11.282 5.22439 12.25V26.6875C5.22439 27.4148 4.64209 28 3.91829 28H1.3061C0.582302 28 0 27.4148 0 26.6875ZM26.5573 28H8.27195C7.54816 28 6.96585 27.4148 6.96585 26.6875V23.625H27.8634V26.6875C27.8634 27.4148 27.2811 28 26.5573 28ZM6.96585 21.875V18.375H27.8634V21.875H6.96585ZM6.96585 16.625V12.25H27.8634V16.625H6.96585Z";
   private static final String SVG_PATH_APARTMENT = "M2.625 0C1.17578 0 0 1.17578 0 2.625V25.375C0 26.8242 1.17578 28 2.625 28H7.875V23.625C7.875 22.1758 9.05078 21 10.5 21C11.9492 21 13.125 22.1758 13.125 23.625V28H18.375C19.8242 28 21 26.8242 21 25.375V2.625C21 1.17578 19.8242 0 18.375 0H2.625ZM3.5 13.125C3.5 12.6438 3.89375 12.25 4.375 12.25H6.125C6.60625 12.25 7 12.6438 7 13.125V14.875C7 15.3562 6.60625 15.75 6.125 15.75H4.375C3.89375 15.75 3.5 15.3562 3.5 14.875V13.125ZM9.625 12.25H11.375C11.8562 12.25 12.25 12.6438 12.25 13.125V14.875C12.25 15.3562 11.8562 15.75 11.375 15.75H9.625C9.14375 15.75 8.75 15.3562 8.75 14.875V13.125C8.75 12.6438 9.14375 12.25 9.625 12.25ZM14 13.125C14 12.6438 14.3938 12.25 14.875 12.25H16.625C17.1063 12.25 17.5 12.6438 17.5 13.125V14.875C17.5 15.3562 17.1063 15.75 16.625 15.75H14.875C14.3938 15.75 14 15.3562 14 14.875V13.125ZM4.375 5.25H6.125C6.60625 5.25 7 5.64375 7 6.125V7.875C7 8.35625 6.60625 8.75 6.125 8.75H4.375C3.89375 8.75 3.5 8.35625 3.5 7.875V6.125C3.5 5.64375 3.89375 5.25 4.375 5.25ZM8.75 6.125C8.75 5.64375 9.14375 5.25 9.625 5.25H11.375C11.8562 5.25 12.25 5.64375 12.25 6.125V7.875C12.25 8.35625 11.8562 8.75 11.375 8.75H9.625C9.14375 8.75 8.75 8.35625 8.75 7.875V6.125ZM14.875 5.25H16.625C17.1063 5.25 17.5 5.64375 17.5 6.125V7.875C17.5 8.35625 17.1063 8.75 16.625 8.75H14.875C14.3938 8.75 14 8.35625 14 7.875V6.125C14 5.64375 14.3938 5.25 14.875 5.25Z";

   private static InfrastructureController infrastructureController = new InfrastructureController();
   private static InfrastructureCard controller;
   private static User userAuth = Auth.getUser();
   @FXML
   private BorderPane infrastructureCard;

   @FXML
   private Label infrastructurePrice;

   @FXML
   private Label infrastructureName;

   @FXML
   private FlowPane tenantQuantityPane;

   @FXML
   private VBox categoryIcon;

   private Boolean isCreatedInvoice = false;
   private LocalDate currentDate = LocalDate.now();
   private LocalDate invoicePayment;
   private LocalDate nextPayment;

   private Infrastructure infrastructure;
   private static InvoiceController invoiceController = new InvoiceController();

   public static BorderPane getInfrastructureCard(String id) throws IOException {
      FXMLLoader loader = new FXMLLoader(InfrastructureCard.class.getResource("infrastructureCard.fxml"));
      BorderPane card = loader.load();
      controller = loader.getController();
      Infrastructure infrastructure = infrastructureController.getById(id);
      controller.setInfrastructure(infrastructure);
      init(infrastructure);
      return card;
   }

   public void setInfrastructure(Infrastructure infrastructure) {
      this.infrastructure = infrastructure;
   }

   private static void init(Infrastructure infrastructure) {
      controller.infrastructurePrice.setText("$" + infrastructure.getPrice().toString() + " /Month");
      controller.infrastructureName.setText(infrastructure.getName());
      int tenantQuantity = infrastructure.getTenant_quantity();
      setBackgroundInfrastructure(tenantQuantity);
      setQuantityTenantIcon(tenantQuantity);
      setCategoryIcon(infrastructure);
      setBackgroundStatusPaid(infrastructure, tenantQuantity);
   }

   private static void setBackgroundInfrastructure(int tenantQuantity) {
      if (tenantQuantity == 0) {
         String currentStyle = controller.infrastructureCard.getStyle();
         controller.infrastructureCard.setStyle(
               currentStyle.replace(" linear-gradient(#383698, #0A0E47);", "linear-gradient(#737373, #D9D9D9);"));
      }
   }

   public static void setBackgroundStatusPaid(Infrastructure infras, int tenantQuantity) {
      Invoice invoice = invoiceController.getInvoice(infras.getId());
      if (invoice != null) {
         if (invoice.getStatus() == 0) {
            String currentStyle = controller.infrastructureCard.getStyle();
            controller.infrastructureCard.setStyle(currentStyle.replace(" linear-gradient(#383698, #0A0E47);",
                  "linear-gradient(to bottom right, #ff5100, #ff0000);"));
         }
      } else {
         setBackgroundInfrastructure(tenantQuantity);
      }
   }

   private static void setQuantityTenantIcon(int tenantQuantity) {
      controller.tenantQuantityPane.getChildren().clear();
      if (tenantQuantity <= 4) {
         for (int i = 0; i < tenantQuantity; i++) {
            controller.tenantQuantityPane.getChildren().add(getSVGPath(SVG_PATH_USER));
         }
      } else {
         String quantityText = tenantQuantity < 10 ? "0" + tenantQuantity : String.valueOf(tenantQuantity);
         Label quantity = new Label(quantityText);
         quantity.setTextFill(Color.WHITE);
         quantity.setFont(Font.font(null, FontWeight.BOLD, 24));
         controller.tenantQuantityPane.getChildren().addAll(quantity, getSVGPath(SVG_PATH_USER));
      }
   }

   public static void setCategoryIcon(Infrastructure infrastructure) {
      controller.categoryIcon.getChildren().clear();
      if (infrastructure.getCategory() == 1) {
         controller.categoryIcon.getChildren().addAll(getSVGPath(SVG_PATH_MOTEL));
      } else if (infrastructure.getCategory() == 2) {
         controller.categoryIcon.getChildren().addAll(getSVGPath(SVG_PATH_APARTMENT));
      } else {
         controller.categoryIcon.getChildren().addAll(getSVGPath(SVG_PATH_WHOLE_HOUSE));
      }
   }

   private static SVGPath getSVGPath(String svg) {
      SVGPath svgPath = new SVGPath();
      svgPath.setContent(svg);
      svgPath.setStyle("-fx-fill: white;");
      return svgPath;
   }

   public void initialize() {
      infrastructureCard.setOnMouseClicked(e -> {
         try {
            RentalExpiration.checkExpiration(userAuth);
            DashboardScene.switchToInfrastructureDetail(infrastructure.getId());
            try {
               if (invoiceController.getInvoice(infrastructure.getId()) != null) {
                  invoicePayment = invoiceController.getInvoice(infrastructure.getId()).getCreate_at().toLocalDate();
                  nextPayment = invoicePayment.plusMonths(1);
                  long daysToPayment = ChronoUnit.DAYS.between(currentDate, nextPayment);

                  if (infrastructure.getTenant_quantity() > 0) {
                     if ((daysToPayment >= 0 && daysToPayment <= 5) && !isCreatedInvoice) {
                        AlertPopup.info("PaymentDate Alert", String.format(
                              "%s days left until payment for this infrastructure!\n\tPresent Day:\t\t%s\n\tInvoice of Month: \t%s",
                              daysToPayment, currentDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                              invoicePayment.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
                     } else if ((daysToPayment < 0 && !isCreatedInvoice)) {
                        AlertPopup.info("PaymentDate Alert", String.format(
                              "It's been more than %s days but you haven't\ncreated a new invoice to complete the July\ninvoice %s",
                              -daysToPayment, currentDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                              invoicePayment.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
                     }
                  }
               } else {
                  if (infrastructure.getTenant_quantity() > 0) {
                     String firstTenant = infrastructureController.getById(infrastructure.getId()).getRental_at();
                     DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                     LocalDate rentalDate = LocalDate.parse(firstTenant, df);
                     nextPayment = rentalDate.plusMonths(1);
                     long daysToPayment = ChronoUnit.DAYS.between(currentDate, nextPayment);

                     if ((daysToPayment >= 0 && daysToPayment <= 5) && !isCreatedInvoice) {
                        AlertPopup.info("PaymentDate Alert", String.format(
                              "%s days left until payment for this infrastructure!\n\tPresent Day:\t\t%s\n\tRental Start Date: \t%s",
                              daysToPayment, currentDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                              rentalDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
                     } else if ((daysToPayment < 0 && !isCreatedInvoice)) {
                        AlertPopup.info("PaymentDate Alert", String.format(
                              "It's been more than %s days but you haven't\ncreated a new invoice to complete the July\ninvoice %s",
                              -daysToPayment, currentDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                              rentalDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
                     }
                  }
               }
            } catch (Exception exception) {
               exception.printStackTrace();
            }
         } catch (IOException ex) {
            ex.printStackTrace();
         }
      });
   }

}
