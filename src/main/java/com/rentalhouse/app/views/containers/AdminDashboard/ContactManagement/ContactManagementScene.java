package com.rentalhouse.app.views.containers.AdminDashboard.ContactManagement;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.rentalhouse.app.controllers.ContactController;
import com.rentalhouse.app.models.Contact;
import com.rentalhouse.app.services.ContactService;
import com.rentalhouse.app.views.components.AlertPopup.AlertPopup;
import com.rentalhouse.app.views.containers.AdminDashboard.AdminDashboardScene;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class ContactManagementScene {
   @FXML
   private BorderPane borderPane;
   @FXML
   private Button detailContactBtn;
   @FXML
   private Button deleteContactBtn;
   @FXML
   private ComboBox<StatusFillter> sortByStatusComboBox;
   @FXML
   private TextField searchField;
   @FXML
   private TableView<Contact> ContactTable;
   @FXML
   private TableColumn<Contact, String> ID;
   @FXML
   private TableColumn<Contact, String> Name;
   @FXML
   private TableColumn<Contact, String> Title;
   @FXML
   private TableColumn<Contact, String> Email;
   @FXML
   private TableColumn<Contact, String> Phone;
   @FXML
   private TableColumn<Contact, Integer> Status;
   @FXML
   private TableColumn<Contact, String> Create_At;

   private final ContactController contactController = new ContactController();
   private ObservableList<Contact> contactList = FXCollections.observableArrayList();
   private List<Contact> originalContactList;

   public static Parent getContactManagement() throws IOException {
      return FXMLLoader
            .load(ContactManagementScene.class
                  .getResource("contactManagement.fxml"));
   }

   public void initialize() {
      init();
      setupTable();
      loadContact();

      sortByStatusComboBox.getItems().addAll(
            new StatusFillter(0, "All Status"),
            new StatusFillter(1, "Completed"),
            new StatusFillter(2, "No process"),
            new StatusFillter(3, "Processing"));
      sortByStatusComboBox.setValue(new StatusFillter(0, "Status"));

      sortByStatusComboBox.setOnAction(e -> searchContact());
      searchField.textProperty().addListener((observable, oldValue, newValue) -> searchContact());
   }

   private void init() {
      onClickDeleteContact();
      onClickDetailContact();
   }

   private void onClickDeleteContact() {
      deleteContactBtn.setOnMouseClicked(e -> {
         Contact selectedContact = ContactTable.getSelectionModel().getSelectedItem();
         if (selectedContact != null) {
            try {
               boolean confirm = AlertPopup.confirm("Delete Contact",
                     "Are you sure you want to delete this contact?");
               if (confirm) {
                  if (contactController.delete(selectedContact.getId())) {
                     try {
                        AlertPopup.success("Contact deleted successfully", "Deleted successfully");
                        contactList.remove(selectedContact);
                        originalContactList.remove(selectedContact);
                     } catch (Exception e2) {
                        System.err.println(e2.getMessage());
                     }
                  } else {
                     try {
                        AlertPopup.error("Failed to delete contact", "Delete failed");
                     } catch (Exception e2) {
                        System.err.println(e2.getMessage());
                     }
                  }
               }
            } catch (Exception e2) {
               System.err.println(e2.getMessage());
            }
         } else {
            System.out.println("Please select a contact to delete.");
         }
      });
   }

   private void onClickDetailContact() {
      detailContactBtn.setOnMouseClicked(e -> {
         Contact selectedContact = ContactTable.getSelectionModel().getSelectedItem();
         if (selectedContact != null) {
            try {
               AdminDashboardScene.switchToContactDetail(selectedContact.getId(), selectedContact.getName(),
                     selectedContact.getPhone());
               refreshTable();
            } catch (Exception e2) {
               System.err.println(e2.getMessage());
            }
         } else {
            System.out.println("Please select a contact to see detail");
         }
      });
   }

   private void setupTable() {
      ID.setCellValueFactory(cellData -> new SimpleStringProperty(
            String.valueOf(ContactTable.getItems().indexOf(cellData.getValue()) + 1)));
      Name.setCellValueFactory(new PropertyValueFactory<Contact, String>("name"));
      Title.setCellValueFactory(new PropertyValueFactory<Contact, String>("title"));
      Email.setCellValueFactory(new PropertyValueFactory<Contact, String>("email"));
      Phone.setCellValueFactory(new PropertyValueFactory<Contact, String>("phone"));
      Status.setCellValueFactory(new PropertyValueFactory<Contact, Integer>("status"));
      Status.setCellFactory(column -> new StatusTableCell());
      Create_At.setCellValueFactory(cellData -> {
         String formattedDate = formatDateTime(cellData.getValue().getCreated_at());
         return new SimpleStringProperty(formattedDate);
      });
      ContactTable.setItems(contactList);
   }

   public void loadContact() {
      contactList.clear();
      originalContactList = contactController.GetAll();
      Collections.sort(originalContactList, ContactService.sortByCreatedAt());
      contactList.addAll(originalContactList);
   }

   private void searchContact() {
      StatusFillter selectedContact = sortByStatusComboBox.getValue();
      String search = searchField.getText().toLowerCase();

      List<Contact> filteredContact = originalContactList.stream()
            .filter(contact -> (selectedContact.getValue() == 0 || contact.getStatus() == selectedContact.getValue()))
            .filter(contact -> {
               int contactIndex = originalContactList.indexOf(contact) + 1;
               return String.valueOf(contactIndex).contains(search) ||
                     contact.getName().toLowerCase().contains(search) ||
                     contact.getEmail().toLowerCase().contains(search) ||
                     contact.getPhone().toLowerCase().contains(search);
            })
            .collect(Collectors.toList());

      contactList.setAll(filteredContact);
   }

   private void refreshTable() {
      loadContact();
   }

   private class StatusTableCell extends TableCell<Contact, Integer> {
      private final Image completeIcon;
      private final Image noProcessingIcon;
      private final Image processingIcon;
      private final ImageView imageView = new ImageView();

      public StatusTableCell() {
         completeIcon = loadImage("/com/rentalhouse/app/views/assets/icons/check-solid.png");
         noProcessingIcon = loadImage("/com/rentalhouse/app/views/assets/icons/error-icon.png");
         processingIcon = loadImage("/com/rentalhouse/app/views/assets/icons/info-solid.png");
         imageView.setFitWidth(15);
         imageView.setFitHeight(15);
      }

      private Image loadImage(String path) {
         try {
            return new Image(getClass().getResourceAsStream(path));
         } catch (Exception e) {
            System.err.println("Could not load image: " + path);
            return null;
         }
      }

      @Override
      protected void updateItem(Integer status, boolean empty) {
         super.updateItem(status, empty);

         if (empty || status == null) {
            setGraphic(null);
         } else {
            if (status == 1) {
               imageView.setImage(completeIcon);
            } else if (status == 2) {
               imageView.setImage(noProcessingIcon);
            } else if (status == 3) {
               imageView.setImage(processingIcon);
            }
            setGraphic(imageView);
         }
      }
   }

   private String formatDateTime(String dateTimeStr) {
      DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
      try {
         LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, inputFormatter);
         return outputFormatter.format(dateTime);
      } catch (Exception e) {
         e.printStackTrace();
         return dateTimeStr;
      }
   }
}
