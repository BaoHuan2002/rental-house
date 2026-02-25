package com.rentalhouse.app.views.components.EditUserForm;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import com.rentalhouse.app.controllers.UserController;
import com.rentalhouse.app.middlewares.ValidateInput;
import com.rentalhouse.app.models.User;
import com.rentalhouse.app.repositories.UserRepository;
import com.rentalhouse.app.views.components.AlertPopup.AlertPopup;
import com.rentalhouse.app.views.containers.AdminDashboard.UsersManagement.UsersManagementScene;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class EditUserForm {
   private final UserController userController = new UserController();
   private final UserRepository checkExists = new UserRepository();
   private static UsersManagementScene usersManagementScene;
   private static Stage stage;
   private static Parent root;
   private static Scene scene;
   private static User user;

   @FXML
   private TextField nameField;
   @FXML
   private TextField phoneField;
   @FXML
   private TextField emailField;
   @FXML
   private TextField passwordField;
   @FXML
   private TextField passwordConfirmField;
   @FXML
   private TextField bankNameField;
   @FXML
   private TextField bankNumberField;
   @FXML
   private ComboBox<Role> roleComboBox;
   @FXML
   private Label nameError;
   @FXML
   private Label emailError;
   @FXML
   private Label phoneError;
   @FXML
   private Label roleError;
   @FXML
   private Label passwordError;
   @FXML
   private Label passwordConfirmError;
   @FXML
   private Label bankNameError;
   @FXML
   private Label bankNumberError;
   @FXML
   private Label uploadQR;
   @FXML
   private Button uploadBtn;
   @FXML
   private Button cancelUploadBtn;
   @FXML
   private Button cancelBtn;
   @FXML
   private Button submitBtn;
   @FXML
   private Button closeBtn;
   @FXML
   private ImageView bankQRImage;

   private static String bankQRBase64 = "";

   private static void init(FXMLLoader loader) throws IOException {
      root = loader.load();
      scene = new Scene(root);
      stage = new CustomStage(scene, root);
   }

   public static void show(String userId, UsersManagementScene scene) throws IOException {
      user = new UserController().getById(userId);
      usersManagementScene = scene;
      FXMLLoader loader = new FXMLLoader(EditUserForm.class.getResource("editUserForm.fxml"));
      init(loader);
   }

   @FXML
   private void cancel() {
      stage.close();
   }

   public void close() {
      closeBtn.getScene().getWindow().hide();
   }

   @FXML
   private void submit() {
      String name = nameField.getText();
      Role role = roleComboBox.getValue();
      int roleValue = role != null ? role.getValue() : user.getRole();
      String phone = phoneField.getText();
      String email = emailField.getText();
      String password = passwordField.getText();
      String passwordConfirm = passwordConfirmField.getText();
      String bankName = bankNameField.getText();
      String bankNumber = bankNumberField.getText();
      String bankQR = bankQRBase64;

      if (bankName == null) {
         bankName = "";
      }
      if (bankNumber == null) {
         bankNumber = "";
      }
      if (bankQR == null) {
         bankQR = "";
      }

      Map<String, String> errorMessages = new HashMap<>();

      if (ValidateInput.isEmpty(name)) {
         errorMessages.put("name", "Name is required");
      }

      if (!ValidateInput.isValidSelector(roleValue)) {
         errorMessages.put("role", "Role is required");
      }

      if (ValidateInput.isEmpty(phone)) {
         errorMessages.put("phone", "Phone number is required");
      } else if (!ValidateInput.isValidPhone(phone)) {
         errorMessages.put("phone", "Please enter the number");
      } else if (checkExists.checkPhoneByID(phone, user.getId())) {
         errorMessages.put("phone", "Phone number already exists ");
      }

      if (ValidateInput.isEmpty(email)) {
         errorMessages.put("email", "Email is required");
      } else if (!ValidateInput.isValidEmail(email)) {
         errorMessages.put("email", "Invalid email");
      } else if (checkExists.checkEmailByID(email, user.getId())) {
         errorMessages.put("email", "Email already exists");
      }

      if (!ValidateInput.isEmpty(password)) {
         if (password.length() < 8) {
            errorMessages.put("password", "Password must be at least 8 characters");
         } else if (password.length() >= 8 && !ValidateInput.isValidPassword(password)) {
            errorMessages.put("password", "Error: lower + uppercase, numbers, special chars");
         } else {
            if (ValidateInput.isEmpty(passwordConfirm)) {
               errorMessages.put("passwordConfirm", "Password confirmation is required");
            } else if (!ValidateInput.isValidPasswordConfirmation(password, passwordConfirm)) {
               errorMessages.put("passwordConfirm", "Passwords do not match");
            }
         }
      }

      if (!ValidateInput.isEmpty(bankName)) {
         if (ValidateInput.isEmpty(bankNumber)) {
            errorMessages.put("bankNumer", "Bank number is required");
         } else if (!ValidateInput.isNumber(bankNumber)) {
            errorMessages.put("bankNumer", "Please enter the number");
         }
      }

      if (!ValidateInput.isEmpty(bankNumber)) {
         if (ValidateInput.isEmpty(bankName)) {
            errorMessages.put("bankName", "Bank name is required");
         }
      }

      if (!errorMessages.isEmpty()) {
         nameError.setText(errorMessages.get("name"));
         roleError.setText(errorMessages.get("role"));
         phoneError.setText(errorMessages.get("phone"));
         emailError.setText(errorMessages.get("email"));
         passwordError.setText(errorMessages.get("password"));
         passwordConfirmError.setText(errorMessages.get("passwordConfirm"));
         bankNameError.setText(errorMessages.get("bankName"));
         bankNumberError.setText(errorMessages.get("bankNumer"));
         return;
      }
      resetAllErrorMessage();

      user.setName(name);
      user.setEmail(email);
      user.setPhone(phone);
      user.setPassword(password);
      user.setBank_name(bankName);
      user.setBank_number(bankNumber);
      user.setBank_QR(bankQR);
      user.setRole(roleValue);
      user.setIs_deleted(0);

      try {
         boolean confirm = AlertPopup.confirm("Update User", "Are you sure you want to update this user?");
         if (confirm) {
            userController.update(user);
            AlertPopup.success(
                  "User updated successfully", "Updated successfully");
            usersManagementScene.refreshTable();
         } else {
            stage.close();
         }
      } catch (Exception e2) {
         System.err.println(e2.getMessage());
      }
      return;
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
      String imgBase64 = uploadImage("QR code", bankQRImage);

      if (imgBase64 != null) {
         EditUserForm.bankQRBase64 = imgBase64;
         cancelUploadBtn.setVisible(true);
         uploadBtn.setVisible(false);
         uploadQR.setVisible(false);
      } else {
         cancelUploadBtn.setVisible(false);
         uploadBtn.setVisible(true);
         uploadQR.setVisible(true);
      }
   }

   public void clearBankQR(ActionEvent e) {
      bankQRImage.setImage(null);
      uploadBtn.setVisible(true);
      uploadQR.setVisible(true);
      cancelUploadBtn.setVisible(false);
      bankQRBase64 = "";
   }

   public void initialize() {
      cancelUploadBtn.setVisible(false);
      resetAllErrorMessage();
      nameField.setText(user.getName());
      emailField.setText(user.getEmail());
      phoneField.setText(user.getPhone());
      emailField.setDisable(true);
      roleComboBox.getItems().addAll(
            new Role(1, "Admin"),
            new Role(2, "Lessor"),
            new Role(3, "Tenant"));
      String roleName = Role.getRoleByValue(user.getRole());
      roleComboBox.setValue(new Role(user.getRole(), roleName));
      bankNameField.setText(user.getBank_name());
      bankNumberField.setText(user.getBank_number());

      if (user.getBank_QR() != null && !user.getBank_QR().isEmpty()) {
         byte[] imageBytes = Base64.getDecoder().decode(user.getBank_QR());
         Image image = new Image(new ByteArrayInputStream(imageBytes));
         bankQRImage.setImage(image);
         bankQRImage.setPreserveRatio(true);
         bankQRBase64 = user.getBank_QR();
         cancelUploadBtn.setVisible(true);
         uploadBtn.setVisible(false);
         uploadQR.setVisible(false);
      } else {
         bankQRImage.setImage(null);
         cancelUploadBtn.setVisible(false);
      }

      if (user.getRole() == 1) {
         roleComboBox.setDisable(true);
      }
   }

   private void resetAllErrorMessage() {
      nameError.setText("");
      emailError.setText("");
      phoneError.setText("");
      roleError.setText("");
      passwordError.setText("");
      passwordConfirmError.setText("");
      bankNameError.setText("");
      bankNumberError.setText("");
   }
}
