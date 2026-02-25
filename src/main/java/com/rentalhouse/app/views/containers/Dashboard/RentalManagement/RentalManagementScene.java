package com.rentalhouse.app.views.containers.Dashboard.RentalManagement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.rentalhouse.app.controllers.InfrastructureController;
import com.rentalhouse.app.models.Infrastructure;
import com.rentalhouse.app.views.components.InfrastructureCard.InfrastructureCard;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.util.Duration;

public class RentalManagementScene {
   private FlowPane contentInfrastructure = new FlowPane(20, 20);

   private List<Infrastructure> infrastructuresInfo;
   private InfrastructureController infrastructureController = new InfrastructureController();

   @FXML
   private ScrollPane scrollPane;

   @FXML
   private TextField searchField;

   @FXML
   private ComboBox<Category> categoryComboBox;

   @FXML
   private ComboBox<StatusInfrastructure> statusInfrastructureComboBox;

   public static Parent getRentalManagementScene() throws IOException {
      return FXMLLoader.load(RentalManagementScene.class.getResource("rentalManagement.fxml"));
   }

   public void initialize() {
      scrollPane.setContent(contentInfrastructure);
      contentInfrastructure.prefWidthProperty().bind(scrollPane.widthProperty());
      scrollPane.setFitToWidth(true);
      scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

      categoryComboBox.getItems().addAll(
            new Category(0, "Category"),
            new Category(1, "Motel"),
            new Category(2, "Apartment"),
            new Category(3, "Whole house"));
      categoryComboBox.setValue(new Category(0, "Category"));

      statusInfrastructureComboBox.getItems().addAll(
            new StatusInfrastructure(0, "All"),
            new StatusInfrastructure(1, "Active"),
            new StatusInfrastructure(2, "Inactive"));
      statusInfrastructureComboBox.setValue(new StatusInfrastructure(0, "All"));
      CompletableFuture.runAsync(() -> {
         infrastructuresInfo = infrastructureController.GetInfrastructures();
         infrastructuresInfo.forEach(infrastructure -> {
            try {
               final BorderPane infrastructureCard = InfrastructureCard.getInfrastructureCard(infrastructure.getId());
               Platform.runLater(() -> contentInfrastructure.getChildren().add(infrastructureCard));
            } catch (IOException e) {
               e.printStackTrace();
            }
         });
      });
      PauseTransition pause = new PauseTransition(Duration.seconds(0.2));

      searchField.textProperty().addListener((observable, oldValue, newValue) -> {
         // Reset the pause every time the text changes
         pause.setOnFinished(event -> {
            showInfrastructureBySearchAndFilter();
         });
         pause.playFromStart();
      });
   }

   private List<Infrastructure> searchByName() {
      List<Infrastructure> infrastructuresBySearch = new ArrayList<Infrastructure>();
      List<Infrastructure> infrastructures = infrastructureController.GetInfrastructures();
      if (searchField.getText().trim().isEmpty()) {
         infrastructuresBySearch = infrastructures;
      } else {
         for (Infrastructure infrastructure : infrastructures) {
            if (infrastructure.getName().toLowerCase().contains(searchField.getText().toLowerCase())) {
               infrastructuresBySearch.add(infrastructure);
            }
         }
      }
      return infrastructuresBySearch;
   }

   private List<Infrastructure> filterByCategory() {
      List<Infrastructure> infrastructuresByFilterCategory = new ArrayList<Infrastructure>();
      List<Infrastructure> infrastructures = infrastructureController.GetInfrastructures();

      Category category = categoryComboBox.getValue();
      int categoryValue = category.getValue();
      if (categoryValue == 0) {
         infrastructuresByFilterCategory = infrastructures;
      } else {
         for (Infrastructure infrastructure : infrastructures) {
            if (infrastructure.getCategory() == categoryValue) {
               infrastructuresByFilterCategory.add(infrastructure);
            }
         }
      }
      return infrastructuresByFilterCategory;
   }

   private List<Infrastructure> filterByStatus() {
      List<Infrastructure> infrastructuresByFilterStatus = new ArrayList<Infrastructure>();
      List<Infrastructure> infrastructures = infrastructureController.GetInfrastructures();

      StatusInfrastructure status = statusInfrastructureComboBox.getValue();
      int statusValue = status.getValue();
      if (statusValue == 0) {
         infrastructuresByFilterStatus = infrastructures;
      } else {
         for (Infrastructure infrastructure : infrastructures) {
            if (infrastructure.getStatus() == statusValue) {
               infrastructuresByFilterStatus.add(infrastructure);
            }
         }
      }
      return infrastructuresByFilterStatus;
   }

   @FXML
   private void showInfrastructureBySearchAndFilter() {
      List<Infrastructure> infrastructuresBySearch = searchByName();
      List<Infrastructure> infrastructuresByFilterCategory = filterByCategory();
      List<Infrastructure> infrastructuresByFilterStatus = filterByStatus();
      List<Infrastructure> infrastructuresBySearchAndFilter = new ArrayList<>();
      infrastructuresBySearch.forEach(search -> {
         infrastructuresByFilterCategory.forEach(category -> {
            if (search.getId().equals(category.getId())) {
               infrastructuresByFilterStatus.forEach(status -> {
                  if (search.getId().equals(status.getId())) {
                     infrastructuresBySearchAndFilter.add(search);
                  }
               });
            }
         });
      });
      contentInfrastructure.getChildren().clear();
      infrastructuresBySearchAndFilter.forEach(infrastructure -> {
         try {
            final BorderPane infrastructureCard = InfrastructureCard.getInfrastructureCard(infrastructure.getId());
            Platform.runLater(() -> contentInfrastructure.getChildren().add(infrastructureCard));
         } catch (IOException e) {
            e.printStackTrace();
         }
      });
   }
}
