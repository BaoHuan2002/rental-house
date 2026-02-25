package com.rentalhouse.app.views.components.CreateTenantForm;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import com.rentalhouse.app.controllers.TenantController;
import com.rentalhouse.app.middlewares.ValidateInput;
import com.rentalhouse.app.models.Tenant;

import com.rentalhouse.app.views.components.AlertPopup.AlertPopup;

import com.rentalhouse.app.views.containers.Dashboard.DashboardScene;
import com.rentalhouse.app.views.customStages.CustomStage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

public class CreateTenantForm {
   private final TenantController tenantController = new TenantController();
   private static Scene scene;
   private static Parent root;

   @FXML
   private TextField nameField;
   @FXML
   private ComboBox<Gender> genderComboBox;
   @FXML
   private TextField phoneField;
   @FXML
   private TextField idNumberField;
   @FXML
   private TextField addressField;
   @FXML
   private Label nameError;
   @FXML
   private Label genderError;
   @FXML
   private Label phoneError;
   @FXML
   private Label idNumberError;
   @FXML
   private Label addressError;
   @FXML
   private Label img3x4Error;
   @FXML
   private Label frontIdCardError;
   @FXML
   private Label backIdCardError;
   @FXML
   private ImageView img3x4;
   @FXML
   private ImageView frontIdCard;
   @FXML
   private ImageView backIdCard;
   @FXML
   private AnchorPane addTenant_form;
   @FXML
   private Button tenantForm_closeBTN;
   @FXML
   private Button tenant_SubmitBTN;

   private static String img3x4Base64 = "";
   private static String frontIdCardBase64 = "";
   private static String backIdCardBase64 = "";
   private static String infrastructureId = "";

   private static void init(FXMLLoader loader) throws IOException {
      root = loader.load();
      scene = new Scene(root);
      new CustomStage(scene, root);
   }

   public static void show(String infrastructureId) throws IOException {
      CreateTenantForm.infrastructureId = infrastructureId;
      FXMLLoader loader = new FXMLLoader(CreateTenantForm.class.getResource("createTenantForm.fxml"));
      init(loader);
   }

   public void initialize() {
      resetAllErrorMessage();
      genderComboBox.getItems().addAll(
            new Gender(1, "Female"),
            new Gender(2, "Male"));
      genderComboBox.setValue(new Gender(0, "Choose gender"));
   }

   private String uploadImage(String title, ImageView img) {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Choose image " + title);
      fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image", "*.jpg", "*.png", "*.jpeg"));
      File file = fileChooser.showOpenDialog(null);
      String imgBase64 = null;
      if (file != null) {
         try {
            String extension = com.google.common.io.Files.getFileExtension(file.getName());
            if (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("png")
                  || extension.equalsIgnoreCase("jpeg")) {
               byte[] bytes = Files.readAllBytes(file.toPath());
               imgBase64 = Base64.getEncoder().encodeToString(bytes);
               Image image = new Image(new ByteArrayInputStream(bytes));

               img.setImage(image);
               img.setPreserveRatio(true);
            } else {
               System.out.println("Invalid file extension. Please select a JPG, PNG or JPEG image file.");
            }
         } catch (IOException e) {
            e.printStackTrace();
            return null;
         }
      }
      return imgBase64;
   }

   @FXML
   private void uploadImg3x4() {
      CreateTenantForm.img3x4Base64 = uploadImage("image 3x4", img3x4);
   }

   @FXML
   private void uploadFrontIdCard() {
      CreateTenantForm.frontIdCardBase64 = uploadImage("image front id card", frontIdCard);
   }

   @FXML
   private void uploadBackIdCard() {
      CreateTenantForm.backIdCardBase64 = uploadImage("image backside id card", backIdCard);
   }

   @FXML
   private void submit() throws IOException {
      // get value
      String name = nameField.getText();
      Gender gender = genderComboBox.getValue();
      int genderValue = gender.getValue();
      String phone = phoneField.getText();
      String idNumber = idNumberField.getText();
      String address = addressField.getText();
      String img3x4 = CreateTenantForm.img3x4Base64;
      String frontIdCard = CreateTenantForm.frontIdCardBase64;
      String backIdCard = CreateTenantForm.backIdCardBase64;

      // validate value
      Map<String, String> errorMessages = new HashMap<>();

      if (ValidateInput.isEmpty(name)) {
         errorMessages.put("name", "Name is required");
      }

      if (!ValidateInput.isValidSelector(genderValue)) {
         errorMessages.put("gender", "Gender is required");
      }

      if (ValidateInput.isEmpty(phone)) {
         errorMessages.put("phone", "Phone number is required");
      } else if (!ValidateInput.isValidPhone(phone)) {
         errorMessages.put("phone", "Invalid phone number");
      }

      if (ValidateInput.isEmpty(idNumber)) {
         errorMessages.put("idNumber", "ID number is required");
      } else if (!ValidateInput.isNumber(idNumber)) {
         errorMessages.put("idNumber", "Invalid ID number");
      }

      if (ValidateInput.isEmpty(address)) {
         errorMessages.put("address", "Address is required");
      }
      if (ValidateInput.isEmpty(img3x4)) {
         errorMessages.put("img3x4", "Image 3x4 is required");
      }

      if (ValidateInput.isEmpty(frontIdCard)) {
         errorMessages.put("frontIdCard", "Image front id card is required");
      }

      if (ValidateInput.isEmpty(backIdCard)) {
         errorMessages.put("backIdCard", "Image backside id card is required");
      }

      if (!errorMessages.isEmpty()) {
         nameError.setText(errorMessages.get("name"));
         genderError.setText(errorMessages.get("gender"));
         phoneError.setText(errorMessages.get("phone"));
         idNumberError.setText(errorMessages.get("idNumber"));
         addressError.setText(errorMessages.get("address"));
         img3x4Error.setText(errorMessages.get("img3x4"));
         frontIdCardError.setText(errorMessages.get("frontIdCard"));
         backIdCardError.setText(errorMessages.get("backIdCard"));
         return;
      }
      resetAllErrorMessage();

      // create new tenant
      Tenant newTenant = new Tenant();
      newTenant.setName(name);
      newTenant.setGender(genderValue);
      newTenant.setPhone(phone);
      newTenant.setId_number(idNumber);
      newTenant.setAddress(address);
      newTenant.setImage_3x4(img3x4);
      newTenant.setImage_front_id_card(frontIdCard);
      newTenant.setImage_backside_id_card(backIdCard);
      newTenant.setInfrastructure_id(CreateTenantForm.infrastructureId);
      newTenant.setStatus(1);

      if (tenantController.create(newTenant)) {
         resetAllField();
         AlertPopup.success("Tenant created successfully", "Created successfully");
         DashboardScene.switchToInfrastructureDetail(CreateTenantForm.infrastructureId);
         tenantForm_closeBTN.getScene().getWindow().hide();
      }
   }

   private void resetAllErrorMessage() {
      nameError.setText("");
      genderError.setText("");
      phoneError.setText("");
      idNumberError.setText("");
      addressError.setText("");
      img3x4Error.setText("");
      frontIdCardError.setText("");
      backIdCardError.setText("");
   }

   private void resetAllField() {
      nameField.setText("");
      genderComboBox.setValue(new Gender(0, "Choose gender"));
      phoneField.setText("");
      idNumberField.setText("");
      addressField.setText("");
      img3x4Base64 = "";
      frontIdCardBase64 = "";
      backIdCardBase64 = "";
   }

   public void close() {
      tenantForm_closeBTN.getScene().getWindow().hide();
   }

   public void clear() {
      nameField.setText("");
      genderComboBox.setValue(new Gender(0, "Choose gender"));
      phoneField.setText("");
      idNumberField.setText("");
      addressField.setText("");
      nameError.setText("");
      genderError.setText("");
      phoneError.setText("");
      idNumberError.setText("");
      addressError.setText("");
      img3x4Error.setText("");
      frontIdCardError.setText("");
      backIdCardError.setText("");
      img3x4.setImage(null);
      frontIdCard.setImage(null);
      backIdCard.setImage(null);

   }

   public void clearAvatar(ActionEvent e) {
      img3x4.setImage(null);
      img3x4Base64 = "";
   }

   public void clearFrontSideIDCard(ActionEvent e) {
      frontIdCard.setImage(null);
      frontIdCardBase64 = "";
   }

   public void clearBackSideIDCard(ActionEvent event) {
      backIdCard.setImage(null);
      backIdCardBase64 = "";
   }

}
