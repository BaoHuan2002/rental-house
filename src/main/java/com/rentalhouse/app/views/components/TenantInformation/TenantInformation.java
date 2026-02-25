package com.rentalhouse.app.views.components.TenantInformation;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

import com.rentalhouse.app.controllers.TenantController;
import com.rentalhouse.app.models.Tenant;
import com.rentalhouse.app.views.components.AlertPopup.AlertPopup;
import com.rentalhouse.app.views.components.EditTenantForm.EditTenantForm;
import com.rentalhouse.app.views.containers.Dashboard.DashboardScene;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class TenantInformation {
   private TenantController tenantController = new TenantController();
   @FXML
   private Label tenantName;

   @FXML
   private Label tenantPhone;

   @FXML
   private ImageView tenantImage;

   @FXML
   private AnchorPane deleteBtn;

   @FXML
   private AnchorPane editBtn;

   public static BorderPane getTenantInformation(Tenant tenant) throws IOException {
      FXMLLoader loader = new FXMLLoader(TenantInformation.class.getResource("tenantInformation.fxml"));
      BorderPane card = loader.load();
      TenantInformation controller = loader.getController();
      init(controller, tenant);
      return card;
   }

   public static void init(TenantInformation controller, Tenant tenant) {
      byte[] bytes = Base64.getDecoder().decode(tenant.getImage_3x4());
      Image image = new Image(new ByteArrayInputStream(bytes));
      controller.tenantImage.setImage(image);
      controller.tenantImage.setPreserveRatio(true);
      controller.tenantImage.setFitHeight(200);
      controller.tenantName.setText(tenant.getName());
      controller.tenantPhone.setText(tenant.getPhone());
      controller.deleteBtn.setOnMouseClicked(e -> {
         try {
            if (AlertPopup.confirm("Confirm", "Are you sure you want to delete this tenant?")) {
               if (controller.tenantController.delete(tenant.getId())) {
                  DashboardScene.switchToInfrastructureDetail(tenant.getInfrastructure_id());
                  AlertPopup.success("Success", "Tenant deleted successfully");
               }
            }
         } catch (IOException ex) {
            ex.printStackTrace();
         }

      });

      controller.editBtn.setOnMouseClicked(e -> {
         try {
            EditTenantForm.show(tenant.getInfrastructure_id(), tenant);
         } catch (IOException ex) {
            ex.printStackTrace();
         }
      });
   }

}
