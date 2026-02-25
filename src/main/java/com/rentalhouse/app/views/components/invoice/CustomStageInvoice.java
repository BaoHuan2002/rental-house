package com.rentalhouse.app.views.components.invoice;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CustomStageInvoice extends Stage {
   private Double x = 0.0;
   private Double y = 0.0;

   public CustomStageInvoice(Scene scene, Parent root) {
      this.setScene(scene);
      this.setResizable(false);
      this.setAlwaysOnTop(false);
      this.setFullScreenExitHint("");
      root.setStyle("-fx-background-radius:10px; -fx-border-radius:10px;");
      // REMOVE TITLE-BAR DEFAULT ON STAGE:
      initStyle(StageStyle.TRANSPARENT);

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
