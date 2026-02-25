package com.rentalhouse.app.views.components.AlertPopup;

import java.io.IOException;

import com.rentalhouse.app.views.customStages.CustomStage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertPopup {
   @FXML
   private Label title;
   @FXML
   private Label content;
   @FXML
   private static Button okButton;
   @FXML
   private static Button cancelButton;
   @FXML
   private static Button closeButton;
   @FXML
   private VBox alertPopup_info;
   @FXML
   private Button error_close;
   @FXML
   private Button success_close;
   @FXML
   private Button info_close;

   private static boolean confirmationResult;
   private static Scene scene;
   private static Parent root;
   private static Stage stage;

   private static void init(FXMLLoader loader, String title, String content, Image icon) throws IOException {
      if (!loader.getLocation().getPath().endsWith("alertConfirm.fxml")) {
         root = loader.load();
         AlertPopup controller = loader.getController();
         controller.title.setText(title);
         controller.content.setText(content);
         stage = new CustomStage(new Scene(root), root);
         stage.getIcons().add(icon);
      } else {
         root = loader.load();
         AlertPopup controller = loader.getController();
         controller.title.setText(title);
         controller.content.setText(content);
         scene = new Scene(root);
         stage = new Stage();
         stage.setScene(scene);
         stage.setTitle(title);
         stage.getIcons().add(icon);
         stage.setResizable(false);
         stage.initModality(Modality.APPLICATION_MODAL);
         stage.showAndWait();
      }
   }

   @FXML
   private void close() {
      stage.close();
   }

   @FXML
   private void ok() {
      stage.close();
      confirmationResult = true;
   }

   @FXML
   private void cancel() {
      stage.close();
      confirmationResult = false;
   }

   public static void error(String title, String content) throws IOException {
      FXMLLoader loader = new FXMLLoader(AlertPopup.class.getResource("alertError.fxml"));
      Image icon = new Image(AlertPopup.class.getResourceAsStream("../../assets/icons/error-icon.png"));
      init(loader, title, content, icon);
   }

   public static void warning(String title, String content) throws IOException {
      FXMLLoader loader = new FXMLLoader(AlertPopup.class.getResource("alertWarning.fxml"));
      Image icon = new Image(AlertPopup.class
            .getResourceAsStream("../../assets/icons/warning-solid.jpeg"));
      init(loader, title, content, icon);
   }

   public static void info(String title, String content) throws IOException {
      FXMLLoader loader = new FXMLLoader(AlertPopup.class.getResource("alertInfo.fxml"));
      Image icon = new Image(AlertPopup.class.getResourceAsStream("../../assets/icons/info-solid.png"));
      init(loader, title, content, icon);
   }

   public static void success(String title, String content) throws IOException {
      FXMLLoader loader = new FXMLLoader(AlertPopup.class.getResource("alertSuccess.fxml"));
      Image icon = new Image(AlertPopup.class
            .getResourceAsStream("../../assets/icons/check-solid.png"));
      init(loader, title, content, icon);
   }

   public static boolean confirm(String title, String content) throws IOException {
      FXMLLoader loader = new FXMLLoader(AlertPopup.class.getResource("alertConfirm.fxml"));
      Image icon = new Image(AlertPopup.class.getResourceAsStream("../../assets/icons/info-solid.png"));
      init(loader, title, content, icon);
      return getConfirmationResult();
   }

   public static boolean getConfirmationResult() {
      return confirmationResult;
   }
}
