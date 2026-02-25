package com.rentalhouse.app.views.components.ImageComponent;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

import com.rentalhouse.app.views.containers.Dashboard.Contact.ContactScene;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

public class ImageComponent {
   private static ImageComponent controller;

   @FXML
   private Button delBtn;

   @FXML
   private ImageView imageView;

   public static Parent get(FlowPane imagesContainer, String imgBase64) throws IOException {
      FXMLLoader loader = new FXMLLoader(ImageComponent.class.getResource("imageComponent.fxml"));
      Parent parent = loader.load();
      controller = loader.getController();
      controller.setDeleteAction(parent, imagesContainer, imgBase64);
      controller.setImage(imgBase64);
      return parent;
   }

   private void setImage(String imgBase64) {
      imageView.setImage(new Image(new ByteArrayInputStream(Base64.getDecoder().decode(imgBase64))));

   }

   private void setDeleteAction(Parent parent, FlowPane imagesContainer, String imgBase64) {
      delBtn.setOnMouseClicked(e -> {
         imagesContainer.getChildren().remove(parent);
         ContactScene.images.removeIf(img -> img.equals(imgBase64));
      });
   }
}
