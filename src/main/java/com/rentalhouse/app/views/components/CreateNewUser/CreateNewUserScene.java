package com.rentalhouse.app.views.components.CreateNewUser;

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
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CreateNewUserScene {
   private final UserController userController = new UserController();
   private final UserRepository checkExists = new UserRepository();
   private static UsersManagementScene usersManagementScene;
   private static Stage stage;
   private static Parent root;
   private static Scene scene;

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
   private Label uploadQR;
   @FXML
   private Label bankNameError;
   @FXML
   private Label bankNumberError;
   @FXML
   private Button uploadBtn;
   @FXML
   private Button cancelUploadBtn;
   @FXML
   private Button cancelBtn;
   @FXML
   private Button closeBtn;
   @FXML
   private Button submitBtn;
   @FXML
   private ImageView bankQRImage;
   @FXML
    private VBox bankQRContainer;

   private static String bankQRBase64 = "";

   private static void init(FXMLLoader loader) throws IOException {
      root = loader.load();
      scene = new Scene(root);
      stage = new CustomStage(scene, root);
   }

   public static void show(UsersManagementScene scene) throws IOException {
      usersManagementScene = scene;
      FXMLLoader loader = new FXMLLoader(CreateNewUserScene.class.getResource("createNewUser.fxml"));
      init(loader);
   }

   @FXML
   private void cancel() {
      stage.close();
   }

   @FXML
   private void close() {
      closeBtn.getScene().getWindow().hide();
   }

   @FXML
   private void submit() {
      String name = nameField.getText();
      Role role = roleComboBox.getValue();
      int roleValue = role.getValue();
      String phone = phoneField.getText();
      String email = emailField.getText();
      String password = passwordField.getText();
      String passwordConfirm = passwordConfirmField.getText();
      String bankName = bankNameField.getText();
      String bankNumber = bankNumberField.getText();
      String bankQR = CreateNewUserScene.bankQRBase64;

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
         errorMessages.put("phone", "Invalid phone number");
      } else if (checkExists.checkPhone(phone)) {
         errorMessages.put("phone", "Phone number already exists ");
      }

      if (ValidateInput.isEmpty(email)) {
         errorMessages.put("email", "Email is required");
      } else if (!ValidateInput.isValidEmail(email)) {
         errorMessages.put("email", "Invalid email");
      } else if (checkExists.checkEmail(email)) {
         errorMessages.put("email", "Email already exists ");
      }

      if (ValidateInput.isEmpty(password)) {
         errorMessages.put("password", "Password is required");

      } else if (password.length() < 8) {
         errorMessages.put("password", "Password must be at least 8 characters");
      } else if (password.length() >= 8 && !ValidateInput.isValidPassword(password)) {
         errorMessages.put("password", "Error: lower + uppercase, numbers, special chars");

      }

      if (ValidateInput.isEmpty(passwordConfirm)) {
         errorMessages.put("passwordConfirm", "Password confirmation is required");
      } else if (!ValidateInput.isValidPasswordConfirmation(password, passwordConfirm)) {
         errorMessages.put("passwordConfirm", "Passwords do not match");
      }

      if (!ValidateInput.isEmpty(bankName)) {
         if (ValidateInput.isEmpty(bankNumber)) {
            errorMessages.put("bankNumer", "Bank number is required");
         } else if (!ValidateInput.isNumber(bankNumber)) {
            errorMessages.put("bankNumer", "Invalid bank number");
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

      User newUser = new User();
      newUser.setName(name);
      newUser.setEmail(email);
      newUser.setPhone(phone);
      newUser.setPassword(password);
      newUser.setBank_name(bankName);
      newUser.setBank_number(bankNumber);
      newUser.setBank_QR(bankQR);
      newUser.setMembership_package(1);
      newUser.setRole(roleValue);
      newUser.setIs_deleted(0);

      if (userController.create(newUser)) {
         try {
            AlertPopup.success("User created successfully", "Created successfully");
            usersManagementScene.refreshTable();
         } catch (Exception e2) {
            System.err.println(e2.getMessage());
         }
         resetAllField();
         return;
      }
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
               img.setFitHeight(180); 
               img.setFitWidth(180); 
               img.setPreserveRatio(true);
               img.setVisible(true);
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
         CreateNewUserScene.bankQRBase64 = imgBase64;
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
   }

   public void initialize() {
      resetAllErrorMessage();
      roleComboBox.getItems().addAll(
            new Role(1, "Admin"),
            new Role(2, "Lessor"),
            new Role(3, "Tenant"));
      roleComboBox.setValue(new Role(0, "Choose role"));
      cancelUploadBtn.setVisible(false);
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

   private void resetAllField() {
      nameField.setText("");
      roleComboBox.setValue(new Role(0, "Choose role"));
      phoneField.setText("");
      emailField.setText("");
      passwordField.setText("");
      passwordConfirmField.setText("");
      bankNameField.setText("");
      bankNumberField.setText("");
      bankQRImage.setImage(null);
   }
}