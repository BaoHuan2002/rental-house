package com.rentalhouse.app.views.customStages;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CustomStage extends Stage {
   private double x = 0.0;
   private double y = 0.0;

   public CustomStage(Scene scene, Parent root) {
      this.setScene(scene);
      this.setResizable(false);
      this.setAlwaysOnTop(false);
      this.setFullScreenExitHint("");
      scene.setFill(Color.TRANSPARENT);
      root.setStyle("-fx-background-radius:10px; -fx-border-radius:10px;");

      // REMOVE TITLE-BAR DEFAULT ON STAGE:
      initStyle(StageStyle.TRANSPARENT);

      initModality(Modality.APPLICATION_MODAL);

      // HANDLE WHEN STAGE PRESSED TO MOVE:
      root.setOnMousePressed(event -> {
         x = event.getSceneX();
         y = event.getSceneY();
      });

      // HANDLE STAGE WHEN IT DRAGGED:
      root.setOnMouseDragged(event -> {
         this.setX(event.getScreenX() - x);
         this.setY(event.getScreenY() - y);
      });
      root.setOnMouseReleased(event -> {
         this.setOpacity(1);
      });
      show();
   }
}
