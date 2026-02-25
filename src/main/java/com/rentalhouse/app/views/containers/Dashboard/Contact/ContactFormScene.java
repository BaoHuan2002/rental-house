package com.rentalhouse.app.views.containers.Dashboard.Contact;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.rentalhouse.app.controllers.ContactController;
import com.rentalhouse.app.controllers.ImageContactController;
import com.rentalhouse.app.middlewares.ValidateInput;
import com.rentalhouse.app.models.Auth;
import com.rentalhouse.app.models.Contact;
import com.rentalhouse.app.models.User;
import com.rentalhouse.app.views.components.AlertPopup.AlertPopup;
import com.rentalhouse.utils.Uuid;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class ContactFormScene {
   private final ContactController _contactController = new ContactController();
   private final ImageContactController _imageContactController = new ImageContactController();

   private static ContactFormScene controller;

   @FXML
   private TextField nameField;

   @FXML
   private TextField emailField;

   @FXML
   private TextField phoneField;

   @FXML
   private TextField titleField;

   @FXML
   private TextArea descriptionField;

   @FXML
   private Label nameError;

   @FXML
   private Label emailError;

   @FXML
   private Label phoneError;

   @FXML
   private Label titleError;

   @FXML
   private Label descriptionError;

   @FXML
   private FlowPane imgContainer;

   public static Parent getContactScene(VBox sendBtn) throws IOException {
      FXMLLoader loader = new FXMLLoader(ContactFormScene.class.getResource("contactForm.fxml"));
      Parent parent = loader.load();
      controller = loader.getController();
      controller.sendContact(sendBtn);
      return parent;
   }

   public void initialize() {
      setInitTextUserAuth();
      resetErrorsMessage();
   }

   private void setInitTextUserAuth() {
      User user = Auth.getUser();
      if (user != null) {
         nameField.setText(user.getName());
         emailField.setText(user.getEmail());
         phoneField.setText(user.getPhone());
      }
   }

   private void resetErrorsMessage() {
      nameError.setText("");
      emailError.setText("");
      phoneError.setText("");
      titleError.setText("");
      descriptionError.setText("");
   }

   private void resetAllFields() {
      titleField.setText("");
      descriptionField.setText("");
   }

   public void sendContact(VBox sendBtn) {
      sendBtn.setOnMouseClicked(e -> {
         String name = nameField.getText();
         String email = emailField.getText();
         String phone = phoneField.getText();
         String title = titleField.getText();
         String description = descriptionField.getText();

         Map<String, String> errorMessages = new HashMap<>();

         if (ValidateInput.isEmpty(name)) {
            errorMessages.put("name", "Name is required");
         }

         if (ValidateInput.isEmpty(email)) {
            errorMessages.put("email", "Email is required");
         } else if (!ValidateInput.isValidEmail(email)) {
            errorMessages.put("email", "Invalid email");
         }

         if (ValidateInput.isEmpty(phone)) {
            errorMessages.put("phone", "Phone number is required");
         } else if (!ValidateInput.isValidPhone(phone)) {
            errorMessages.put("phone", "Invalid phone number");
         }

         if (ValidateInput.isEmpty(title)) {
            errorMessages.put("title", "Title is required");
         }

         if (ValidateInput.isEmpty(description)) {
            errorMessages.put("description", "Description is required");
         }

         if (!errorMessages.isEmpty()) {
            nameError.setText(errorMessages.get("name"));
            emailError.setText(errorMessages.get("email"));
            phoneError.setText(errorMessages.get("phone"));
            titleError.setText(errorMessages.get("title"));
            descriptionError.setText(errorMessages.get("description"));
            return;
         }
         resetErrorsMessage();
         Contact newContact = new Contact();
         newContact.setId(Uuid.get());
         newContact.setName(name);
         newContact.setEmail(email);
         newContact.setPhone(phone);
         newContact.setTitle(title);
         newContact.setDescription(description);

         if (_contactController.create(newContact)) {
            try {
               AlertPopup.success("Successfully", "Send contact successfully");
               ContactScene.images.forEach(img -> {
                  _imageContactController.uploadImageContact(newContact.getId(), img);
               });
            } catch (IOException e1) {
               e1.printStackTrace();
            }
            resetAllFields();
            ContactScene.imagesContainer.getChildren().clear();
            ContactScene.imagesContainer.getChildren().add(ContactScene.getAddImgBtn());
            ContactScene.images.clear();
            return;
         }
      });
   }

   public static String truncateString(String input) {
      int maxLength = 30;
      if (input.length() <= maxLength) {
         return input;
      } else {
         return input.substring(0, maxLength) + "...";
      }
   }
}
