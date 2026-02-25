package com.rentalhouse.app.views.containers.Dashboard.CreateInfrastructure;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.rentalhouse.app.controllers.InfrastructureController;
import com.rentalhouse.app.middlewares.ValidateInput;
import com.rentalhouse.app.models.Infrastructure;
import com.rentalhouse.app.views.components.AlertPopup.AlertPopup;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class CreateInfrastructureScene {

   private final String activeStyle = "-fx-background-color: linear-gradient(#3A389B, #0F176F); -fx-background-radius: 20;";
   private final String inActiveStyle = "-fx-background-color: #D9D9D9; -fx-background-radius: 20;";

   private final InfrastructureController infrastructureController = new InfrastructureController();

   public static Parent getCreateInfrastructure() throws IOException {
      return FXMLLoader
            .load(CreateInfrastructureScene.class
                  .getResource("createInfrastructure.fxml"));
   }

   @FXML
   private BorderPane motelCategoryBtn;
   @FXML
   private BorderPane apartmentCategoryBtn;
   @FXML
   private BorderPane wholeHouseCategoryBtn;

   @FXML
   private Label motelLabel;
   @FXML
   private Label apartmentLabel;
   @FXML
   private Label wholeHouseLabel;

   @FXML
   private TextField nameField;
   @FXML
   private TextField priceField;
   @FXML
   private TextField electricityNumberField;
   @FXML
   private TextField electricityPriceField;
   @FXML
   private TextField waterNumberField;
   @FXML
   private TextField waterPriceField;

   @FXML
   private Label nameError;
   @FXML
   private Label priceError;
   @FXML
   private Label electricityNumberError;
   @FXML
   private Label electricityPriceError;
   @FXML
   private Label waterNumberError;
   @FXML
   private Label waterPriceError;

   @FXML
   private VBox submitBtn;

   private int categoryInfrastructureId = 1;

   public void initialize() {
      init();
   }

   private void init() {
      onClickCategory(motelCategoryBtn,
            apartmentCategoryBtn,
            wholeHouseCategoryBtn,
            motelLabel,
            apartmentLabel,
            wholeHouseLabel,
            1);

      onClickCategory(apartmentCategoryBtn,
            motelCategoryBtn,
            wholeHouseCategoryBtn,
            apartmentLabel,
            motelLabel,
            wholeHouseLabel,
            2);

      onClickCategory(wholeHouseCategoryBtn,
            motelCategoryBtn,
            apartmentCategoryBtn,
            wholeHouseLabel,
            motelLabel,
            apartmentLabel,
            3);

      onClickSubmit();
      resetAllErrorMessage();
      resetAllField();

   }

   private void onClickCategory(BorderPane activeButton, BorderPane inactiveButton1, BorderPane inactiveButton2,
         Label activeLabel,
         Label inactiveLabel1, Label inactiveLabel2, int categoryInfrastructureId) {
      activeButton.setOnMouseClicked(e -> {
         activeButton.setStyle(activeStyle);
         inactiveButton1.setStyle(inActiveStyle);
         inactiveButton2.setStyle(inActiveStyle);

         activeLabel.setTextFill(Color.web("white"));
         inactiveLabel1.setTextFill(Color.web("black"));
         inactiveLabel2.setTextFill(Color.web("black"));

         this.categoryInfrastructureId = categoryInfrastructureId;
      });
   }

   private void onClickSubmit() {
      submitBtn.setOnMouseClicked(e -> {
         String name = nameField.getText();
         String price = priceField.getText();
         String electricityNumber = electricityNumberField.getText();
         String electricityPrice = electricityPriceField.getText();
         String waterNumber = waterNumberField.getText();
         String waterPrice = waterPriceField.getText();

         Map<String, String> errorMessages = new HashMap<>();

         if (ValidateInput.isEmpty(name)) {
            errorMessages.put("name", "Name is required");
         }

         if (ValidateInput.isEmpty(price)) {
            errorMessages.put("price", "Price is required");
         } else if (!ValidateInput.isNumber(price)) {
            errorMessages.put("price", "Price must be a number");
         } else if (!ValidateInput.isPositiveNumber(price)) {
            errorMessages.put("price", "Price must be greater than 0");
         }

         if (ValidateInput.isEmpty(electricityNumber)) {
            errorMessages.put("electricityNumber", "Electricity number is required");
         } else if (!ValidateInput.isNumber(electricityNumber)) {
            errorMessages.put("electricityNumber", "Electricity number must be a number");
         } else if (!ValidateInput.isPositiveNumber(electricityNumber)) {
            errorMessages.put("electricityNumber", "Electricity number must be greater than 0");
         }

         if (ValidateInput.isEmpty(electricityPrice)) {
            errorMessages.put("electricityPrice", "Electricity price is required");
         } else if (!ValidateInput.isNumber(electricityPrice)) {
            errorMessages.put("electricityPrice", "Electricity price must be a number");
         } else if (!ValidateInput.isPositiveNumber(electricityPrice)) {
            errorMessages.put("electricityPrice", "Electricity price must be greater than 0");
         }

         if (ValidateInput.isEmpty(waterNumber)) {
            errorMessages.put("waterNumber", "Water number is required");
         } else if (!ValidateInput.isNumber(waterNumber)) {
            errorMessages.put("waterNumber", "Water number must be a number");
         } else if (!ValidateInput.isPositiveNumber(waterNumber)) {
            errorMessages.put("waterNumber", "Water number must be greater than 0");
         }

         if (ValidateInput.isEmpty(waterPrice)) {
            errorMessages.put("waterPrice", "Water price is required");
         } else if (!ValidateInput.isNumber(waterPrice)) {
            errorMessages.put("waterPrice", "Water price must be a number");
         } else if (!ValidateInput.isPositiveNumber(waterPrice)) {
            errorMessages.put("waterPrice", "Water price must be greater than 0");
         }

         if (!errorMessages.isEmpty()) {
            nameError.setText(errorMessages.get("name"));
            priceError.setText(errorMessages.get("price"));
            electricityNumberError.setText(errorMessages.get("electricityNumber"));
            electricityPriceError.setText(errorMessages.get("electricityPrice"));
            waterNumberError.setText(errorMessages.get("waterNumber"));
            waterPriceError.setText(errorMessages.get("waterPrice"));
            return;
         } else {
            resetAllErrorMessage();
         }

         Infrastructure newInfrastructure = new Infrastructure();
         newInfrastructure.setName(name);
         newInfrastructure.setPrice(new BigDecimal(price));
         newInfrastructure.setCategory(categoryInfrastructureId);
         newInfrastructure.setStatus(2);
         newInfrastructure.setElectricity_number(Long.parseLong(electricityNumber));
         newInfrastructure.setNew_electricity_number(Long.parseLong(electricityNumber));
         newInfrastructure.setElectricity_price(new BigDecimal(electricityPrice));
         newInfrastructure.setWater_number(Long.parseLong(waterNumber));
         newInfrastructure.setNew_water_number(Long.parseLong(waterNumber));
         newInfrastructure.setWater_price(new BigDecimal(waterPrice));

         if (infrastructureController.create(newInfrastructure)) {
            try {
               AlertPopup.success("Infrastructure created successfully", "Created successfully");
            } catch (Exception e2) {
               System.err.println(e2.getMessage());
            }
            resetAllField();
            return;
         }
      });
   }

   private void resetAllField() {
      nameField.setText("");
      priceField.setText("");
      electricityNumberField.setText("");
      electricityPriceField.setText("");
      waterNumberField.setText("");
      waterPriceField.setText("");
   }

   private void resetAllErrorMessage() {
      nameError.setText("");
      priceError.setText("");
      electricityNumberError.setText("");
      electricityPriceError.setText("");
      waterNumberError.setText("");
      waterPriceError.setText("");
   }

}
