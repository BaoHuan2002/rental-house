package com.rentalhouse.app.views.containers.AdminDashboard.DetailContact;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import com.rentalhouse.app.controllers.ContactController;
import com.rentalhouse.app.controllers.ImageContactController;
import com.rentalhouse.app.models.Contact;
import com.rentalhouse.app.models.ImageContact;
import com.rentalhouse.app.views.components.AlertPopup.AlertPopup;
import com.rentalhouse.app.views.containers.AdminDashboard.AdminDashboardScene;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class DetailContact {
   private final ContactController contactController = new ContactController();
   private static Contact contact;
   private static DetailContact controller;

   @FXML
   private BorderPane borderPane;
   @FXML
   private Label nameField;
   @FXML
   private Label emailField;
   @FXML
   private Label phoneField;
   @FXML
   private Label dateField;
   @FXML
   private Label titleField;
   @FXML
   private Label statusField;
   @FXML
   private Label descriptionField;
   @FXML
   private Label noImage;
   @FXML
   private ScrollPane scrollPane;
   @FXML
   private HBox imageContainer;
   @FXML
   private Button processingBtn;
   @FXML
   private Button completeBtn;
   @FXML
   private Button returnBtn;

   public static Parent getDetailContact(String contactID) throws IOException {
      contact = new ContactController().getById(contactID);
      FXMLLoader loader = new FXMLLoader(DetailContact.class.getResource("detailContact.fxml"));
      Parent parent = loader.load();
      controller = loader.getController();
      controller.init();
      return parent;
   }

   public void init() {
   }

   public void initialize() {
      nameField.setText(contact.getName());
      emailField.setText(contact.getEmail());
      phoneField.setText(contact.getPhone());
      dateField.setText(contact.getCreated_at());
      titleField.setText(contact.getTitle());
      descriptionField.setText(contact.getDescription());
      setStatusField(contact.getStatus());
      loadContactImages();
      init();

      if (imageContainer.getChildren().isEmpty()) {
         noImage.setText("No photos selected");
         noImage.setVisible(true);
      } else {
         noImage.setVisible(false);
      }
   }

   private void setStatusField(int status) {
      String statusText = convertStatusToText(status);
      statusField.setText(statusText);
   }

   private String convertStatusToText(int status) {
      switch (status) {
         case 1:
            return "Completed";
         case 2:
            return "No process";
         case 3:
            return "Processing";
         default:
            return "Unknown";
      }
   }

   @FXML
   private void processing() {
      if (contact.getStatus() == 1) {
         processingBtn.setOnMouseClicked(e -> {
            try {
               AlertPopup.error("Request is complete", "This request has been completed!");
            } catch (Exception e2) {
               System.err.println(e2.getMessage());
            }
         });
      } else {
         try {
            boolean confirm = AlertPopup.confirm("Request is processing",
                  "Are you want to put this request in processing status?");
            if (confirm) {
               AlertPopup.success("Put into processing successfully", "The request has been successfully processed");
               contactController.ChangeStatusContact(contact.getId(), 3);

            }
         } catch (Exception e2) {
            System.err.println(e2.getMessage());
         }
      }
   }

   @FXML
   private void complete() {
      if (contact.getStatus() == 1) {
         completeBtn.setOnMouseClicked(e -> {
            try {
               AlertPopup.error("Request is complete", "This request has been completed!");
            } catch (Exception e2) {
               System.err.println(e2.getMessage());
            }
         });
      } else {
         try {
            boolean confirm1 = AlertPopup.confirm("Request is complete",
                  "Are you want to put this request in complete status?");
            if (confirm1) {
               boolean confirm2 = AlertPopup.confirm("This action cannot be undone",
                     "Confirm completion of this request?");
               if (confirm2) {
                  AlertPopup.success("Put into complete successfully", "The request has been successfully completed");
                  contactController.ChangeStatusContact(contact.getId(), 1);
               }
            }
         } catch (Exception e2) {
            System.err.println(e2.getMessage());
         }
      }
   }

   @FXML
   private void returnBtn() throws IOException {
      AdminDashboardScene.switchToContactManagement();
   }

   private void loadContactImages() {
      List<ImageContact> imageContacts = new ImageContactController().getImageContactByID(contact.getId());
      for (ImageContact imageContact : imageContacts) {
         showImageElement(imageContact.getImages());
      }
   }

   private void showImageElement(String imageBase64) {
      try {
         byte[] imageBytes = Base64.getDecoder().decode(imageBase64);
         Image image = new Image(new ByteArrayInputStream(imageBytes));
         ImageView imageView = new ImageView(image);
         imageView.setFitWidth(250);
         imageView.setFitHeight(250);
         imageView.setPreserveRatio(true);

         HBox imageBox = new HBox(imageView);
         imageBox.setPrefSize(280, 240);
         imageBox.setStyle("-fx-border-color: #D9D9D9; " +
               "-fx-border-width: 1px; " +
               "-fx-alignment: center;");
         imageBox.setAlignment(Pos.CENTER);
         imageBox.setMinSize(280, 240);
         imageBox.setMaxSize(280, 240);

         HBox.setMargin(imageBox, new Insets(0, 10, 0, 0));

         imageContainer.getChildren().add(imageBox);
         double totalWidth = imageContainer.getChildren().size() * (270 + 10); // 270 (imageBox width) + 10 (margin)
         imageContainer.setMinWidth(totalWidth);
      } catch (IllegalArgumentException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}
