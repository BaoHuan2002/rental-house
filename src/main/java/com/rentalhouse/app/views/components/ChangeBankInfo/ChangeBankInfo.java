package com.rentalhouse.app.views.components.ChangeBankInfo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;

import com.rentalhouse.app.controllers.UserController;
import com.rentalhouse.app.middlewares.ValidateInput;
import com.rentalhouse.app.models.User;
import com.rentalhouse.app.views.customStages.CustomStage;
import com.rentalhouse.app.views.components.AlertPopup.AlertPopup;
import com.rentalhouse.app.views.components.UserProfile.UserProfileForm;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ChangeBankInfo {
   private static final UserController userController = new UserController();
   private static User userAuth;
   private static Parent root;
   private static Scene scene;
   private static Stage stage;


   @FXML
   private PasswordField currentPassword;
   @FXML
   private TextField bankAccountNumber;
   @FXML
   private TextField bankName;
   @FXML
   private ImageView qrCode;
   @FXML
   private Label currentPasswordError;
   @FXML
   private Label bankAccountNumberError;
   @FXML
   private Label bankNameError;
   @FXML
   private Label qrCodeError;

   private static String imgQRBase64 = "";

   private static void init(FXMLLoader loader) throws IOException {
      root = loader.load();
      scene = new Scene(root);
      stage = new CustomStage(scene, root);
      // stage = new Stage();
      // stage.setScene(scene);
      // stage.setResizable(false);
      // stage.initModality(Modality.APPLICATION_MODAL);
      // stage.showAndWait();
   }

   public static void show(User userAuth) throws IOException {
      ChangeBankInfo.userAuth = userAuth;
      FXMLLoader loader = new FXMLLoader(ChangeBankInfo.class.getResource("changeBankInfo.fxml"));
      init(loader);
   }


   public void initialize() {
      resetAllErrorMessage();
      imgQRBase64 = userAuth.getBank_QR() == null ? "" : userAuth.getBank_QR();
      if (!imgQRBase64.isEmpty()) {
         qrCode.setImage(new Image(new ByteArrayInputStream(Base64.getDecoder().decode(imgQRBase64))));
      }
      bankAccountNumber.setText(userAuth.getBank_number() != null ? userAuth.getBank_number() : "");
      bankName.setText(userAuth.getBank_name() != null ? userAuth.getBank_name() : "");
   }

   @FXML
   private void cancel() {
      stage.close();
   }

   public String uploadImage(String title, ImageView img) {
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
   private void qrCode() {
      ChangeBankInfo.imgQRBase64 = uploadImage("QR Code", qrCode);
   }

   @FXML
   private void submit() throws IOException {
      String currentPassword = this.currentPassword.getText();
      String bankAccountNumber = this.bankAccountNumber.getText();
      String bankName = this.bankName.getText();
      String qrCode = ChangeBankInfo.imgQRBase64;
      Map<String, String> errorMessages = new HashMap<>();
      if (ValidateInput.isEmpty(currentPassword)) {
         errorMessages.put("currentPassword", "Current password is required");
      } else if (!BCrypt.checkpw(currentPassword, userAuth.getPassword())) {
         errorMessages.put("currentPassword", "Passwords do not match");
      }

      if (ValidateInput.isEmpty(bankAccountNumber)) {
         errorMessages.put("bankAccountNumber", "Bank account number is required");
      } else if (!ValidateInput.isNumber(bankAccountNumber)) {
         errorMessages.put("bankAccountNumber", "Bank account number must be a number");
      }

      if (ValidateInput.isEmpty(bankName)) {
         errorMessages.put("bankName", "Bank name is required");
      }

      if (ValidateInput.isEmpty(qrCode)) {
         errorMessages.put("qrCode", "Qr code is required");
      }

      if (!errorMessages.isEmpty()) {
         currentPasswordError.setText(errorMessages.get("currentPassword"));
         bankAccountNumberError.setText(errorMessages.get("bankAccountNumber"));
         bankNameError.setText(errorMessages.get("bankName"));
         qrCodeError.setText(errorMessages.get("qrCode"));
         return;
      }
      resetAllErrorMessage();

      userAuth.setBank_QR(qrCode);
      userAuth.setBank_name(bankName);
      userAuth.setBank_number(bankAccountNumber);
      userAuth.setPassword(currentPassword);
      if (userController.update(userAuth)) {
         resetAllField();
         AlertPopup.success("Bank info changed successfully", "Changed successfully");
         UserProfileForm.refresh();
         stage.close();
      }
   }

   private void resetAllErrorMessage() {
      currentPasswordError.setText("");
      bankAccountNumberError.setText("");
      bankNameError.setText("");
      qrCodeError.setText("");
   }

   private void resetAllField() {
      currentPassword.setText("");
      bankAccountNumber.setText("");
      bankName.setText("");
      qrCode.setImage(null);
   }

   @FXML
   private void clearQR() {
      qrCode.setImage(null);
      imgQRBase64 = "";
   }
}
