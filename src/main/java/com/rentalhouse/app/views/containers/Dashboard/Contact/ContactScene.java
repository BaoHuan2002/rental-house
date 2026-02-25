package com.rentalhouse.app.views.containers.Dashboard.Contact;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.ArrayList;

import com.rentalhouse.app.views.components.ImageComponent.ImageComponent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;

public class ContactScene {
   private static ContactScene controller;

   @FXML
   private ScrollPane scrollPane;

   @FXML
   private VBox sendBtn;

   private VBox container = new VBox(0);

   public static FlowPane imagesContainer = new FlowPane(20, 20);

   public static Parent get() throws IOException {
      FXMLLoader loader = new FXMLLoader(ContactScene.class.getResource("contactScene.fxml"));
      Parent parent = loader.load();
      controller = loader.getController();
      controller.init();
      return parent;
   }

   private void init() throws IOException {
      BorderPane contactForm = (BorderPane) ContactFormScene.getContactScene(sendBtn);
      scrollPane.setContent(container);
      scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
      container.prefWidthProperty().bind(scrollPane.widthProperty());
      container.getChildren().clear();
      imagesContainer.getChildren().clear();
      images.clear();
      imagesContainer.getChildren().add(getAddImgBtn());
      container.getChildren().addAll(contactForm, imagesContainer);
   }

   public static VBox getAddImgBtn() {
      VBox btn = new VBox(10);
      Label img = new Label("Image");
      SVGPath icon = new SVGPath();
      icon.setContent(
            "M34.2857 10.736C34.2857 8.36069 32.3705 6.44165 30 6.44165C27.6295 6.44165 25.7143 8.36069 25.7143 10.736V30.0606H6.42857C4.05803 30.0606 2.14285 31.9796 2.14285 34.355C2.14285 36.7303 4.05803 38.6493 6.42857 38.6493H25.7143V57.9739C25.7143 60.3492 27.6295 62.2683 30 62.2683C32.3705 62.2683 34.2857 60.3492 34.2857 57.9739V38.6493H53.5714C55.942 38.6493 57.8571 36.7303 57.8571 34.355C57.8571 31.9796 55.942 30.0606 53.5714 30.0606H34.2857V10.736Z");
      icon.setFill(Paint.valueOf("#9d9d9d"));
      img.setFont(Font.font(null, FontWeight.BOLD, 18));
      img.setTextFill(Paint.valueOf("#9d9d9d"));
      btn.setAlignment(Pos.CENTER);

      int widthHeigh = 120;
      btn.setPrefWidth(widthHeigh);
      btn.setPrefHeight(widthHeigh);
      btn.setMinWidth(widthHeigh);
      btn.setMinHeight(widthHeigh);
      btn.setMaxWidth(widthHeigh);
      btn.setMaxHeight(widthHeigh);

      btn.setStyle("-fx-border-color: #9d9d9d; -fx-border-radius: 20; -fx-border-width: 2;");
      btn.setCursor(Cursor.HAND);
      btn.getChildren().addAll(icon, img);
      addImage(btn);
      return btn;
   }

   public static List<String> images = new ArrayList<String>();

   private static void addImage(VBox btn) {
      btn.setOnMouseClicked(e -> {
         try {
            String imgBase64 = uploadImage();
            imagesContainer.getChildren().add(ImageComponent.get(imagesContainer, imgBase64));
            images.add(imgBase64);
         } catch (IOException e1) {
            e1.printStackTrace();
         }
      });
   }

   
   private static String uploadImage() {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Choose image");
      fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image", "*.jpg", "*.png", "*.jpeg"));
      File file = fileChooser.showOpenDialog(null);
      if (file != null) {
         try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] imageData = new byte[(int) file.length()];
            fileInputStream.read(imageData);
            fileInputStream.close();

            String base64Image = Base64.getEncoder().encodeToString(imageData);
            return base64Image;

         } catch (IOException e) {
            e.printStackTrace();
         }
      }

      return null;
   }
}
