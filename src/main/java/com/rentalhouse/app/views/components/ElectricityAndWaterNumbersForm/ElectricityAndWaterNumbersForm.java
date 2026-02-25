package com.rentalhouse.app.views.components.ElectricityAndWaterNumbersForm;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.rentalhouse.app.controllers.InfrastructureController;
import com.rentalhouse.app.middlewares.ValidateInput;
import com.rentalhouse.app.models.Infrastructure;
import com.rentalhouse.app.views.containers.Dashboard.DashboardScene;
import com.rentalhouse.app.views.customStages.CustomStage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class ElectricityAndWaterNumbersForm {
   private static Scene scene;
   private static Parent root;
   private static InfrastructureController infrastructureController = new InfrastructureController();
   private static Infrastructure infrastructure;
   @FXML
   private BorderPane electricWater_numbers_form;
   @FXML
   private TextField electricityField;
   @FXML
   private TextField waterField;
   @FXML
   private Label electricityError;
   @FXML
   private Label waterError;
   @FXML
   private Label oldElectricityNumber;
   @FXML
   private Label oldWaterNumber;

   private static String infrastructureId = "";

   private static void init(FXMLLoader loader) throws IOException {
      root = loader.load();
      scene = new Scene(root);
      new CustomStage(scene, root);
   }

   public static void show(String infrastructureId) throws IOException {
      ElectricityAndWaterNumbersForm.infrastructureId = infrastructureId;
      infrastructure = infrastructureController.getById(infrastructureId);
      FXMLLoader loader = new FXMLLoader(
            ElectricityAndWaterNumbersForm.class.getResource("electricityAndWaterNumbersForm.fxml"));
      init(loader);
   }

   public void initialize() {
      resetAllErrorMessage();
      oldElectricityNumber.setText("Old Electricity number: " + infrastructure.getElectricity_number());
      oldWaterNumber.setText("Old Water number: " + infrastructure.getWater_number());
   }

   @FXML
   private void submit() throws IOException {
      String electricity = electricityField.getText();
      String water = waterField.getText();

      Map<String, String> errorMessages = new HashMap<>();
      if (ValidateInput.isEmpty(electricity)) {
         errorMessages.put("electricity", "Electricity number is required");
      } else if (!ValidateInput.isNumber(electricity)) {
         errorMessages.put("electricity", "Electricity number must be a number");
      } else if (Long.parseLong(electricity) < infrastructure.getElectricity_number()) {
         errorMessages.put("electricity", "New electricity number must be bigger than the old one");
      }

      if (ValidateInput.isEmpty(water)) {
         errorMessages.put("water", "Water number is required");
      } else if (!ValidateInput.isNumber(water)) {
         errorMessages.put("water", "Water number must be a number");
      } else if (Long.parseLong(water) < infrastructure.getWater_number()) {
         errorMessages.put("water", "New water number must be bigger than the old one");
      }

      if (!errorMessages.isEmpty()) {
         electricityError.setText(errorMessages.get("electricity"));
         waterError.setText(errorMessages.get("water"));
         return;
      }

      resetAllErrorMessage();
      if (infrastructureController.updateElectricityAndWaterNumbers(infrastructureId, Long.parseLong(electricity),
            Long.parseLong(water))) {
         resetAllField();
         DashboardScene.switchToInfrastructureDetail(infrastructureId);
      }

   }

   private void resetAllErrorMessage() {
      electricityError.setText("");
      waterError.setText("");
   }

   private void resetAllField() {
      electricityField.setText("");
      waterField.setText("");
   }

   public void clear() {
      electricityField.setText("");
      waterField.setText("");
      electricityError.setText("");
      waterError.setText("");
   }

   public void close() {
      electricWater_numbers_form.getScene().getWindow().hide();
   }
}
