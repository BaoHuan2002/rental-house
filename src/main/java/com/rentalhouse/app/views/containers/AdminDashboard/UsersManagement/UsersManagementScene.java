package com.rentalhouse.app.views.containers.AdminDashboard.UsersManagement;

import com.rentalhouse.app.controllers.UserController;
import com.rentalhouse.app.models.User;
import com.rentalhouse.app.services.UserService;
import com.rentalhouse.app.views.components.AlertPopup.AlertPopup;
import com.rentalhouse.app.views.components.CreateNewUser.CreateNewUserScene;
import com.rentalhouse.app.views.components.EditUserForm.EditUserForm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UsersManagementScene {
   @FXML
   private Button createNewUserBtn;
   @FXML
   private Button editUserBtn;
   @FXML
   private Button deleteUserBtn;
   @FXML
   private ComboBox<RoleFilter> sortByRoleComboBox;
   @FXML
   private ComboBox<MembershipFilter> sortByMembershipComboBox;
   @FXML
   private TextField searchField;
   @FXML
   private TableView<User> userTable;
   @FXML
   private TableColumn<User, String> ID;
   @FXML
   private TableColumn<User, String> Name;
   @FXML
   private TableColumn<User, String> Email;
   @FXML
   private TableColumn<User, String> Phone;
   @FXML
   private TableColumn<User, Integer> Member_ship;
   @FXML
   private TableColumn<User, Integer> Role;
   @FXML
   private TableColumn<User, String> Create_At;
   @FXML
   private TableColumn<User, String> Expiration_Date;

   private final UserController userController = new UserController();
   private ObservableList<User> userList = FXCollections.observableArrayList();
   private List<User> originalUserList;

   public static Parent getUsersManagement() throws IOException {
      return FXMLLoader.load(
            UsersManagementScene.class.getResource("usersManagement.fxml"));
   }

   public void initialize() {
      init();
      setupTable();
      loadUsers();

      sortByRoleComboBox.getItems().addAll(
            new RoleFilter(0, "All Role"),
            new RoleFilter(1, "Admin"),
            new RoleFilter(2, "Lessor"),
            new RoleFilter(3, "Tenant"));
      sortByRoleComboBox.setValue(new RoleFilter(0, "Role"));

      sortByMembershipComboBox.getItems().addAll(
            new MembershipFilter(0, "All Membership"),
            new MembershipFilter(1, "Free trial"),
            new MembershipFilter(2, "Vip"),
            new MembershipFilter(3, "Unlimited"));
      sortByMembershipComboBox.setValue(new MembershipFilter(0, "Membership"));

      sortByRoleComboBox.setOnAction(e -> searchUsers());
      sortByMembershipComboBox.setOnAction(e -> searchUsers());
      searchField.textProperty().addListener((observable, oldValue, newValue) -> searchUsers());
   }

   private void init() {
      onClickCreateNewUser();
      onClickEditUser();
      onClickDeleteUser();
   }

   private void onClickCreateNewUser() {
      createNewUserBtn.setOnMouseClicked(e -> {
         try {
            CreateNewUserScene.show(this);
            refreshTable();
         } catch (IOException ex) {
            ex.printStackTrace();
         }
      });
   }

   private void onClickEditUser() {
      editUserBtn.setOnMouseClicked(e -> {
         User selectedUser = userTable.getSelectionModel().getSelectedItem();
         if (selectedUser != null) {
            try {
               EditUserForm.show(selectedUser.getId(), this);
               refreshTable();
            } catch (IOException ex) {
               ex.printStackTrace();
            }
         } else {
            System.out.println("Please select a user to edit.");
         }
      });
   }

   private void onClickDeleteUser() {
      deleteUserBtn.setOnMouseClicked(e -> {
         User selectedUser = userTable.getSelectionModel().getSelectedItem();
         if (selectedUser != null) {
            if (selectedUser.getRole() == 1) {
               try {
                  AlertPopup.error("Delete Admin", "Cannot delete Admin");
               } catch (Exception e2) {
                  System.err.println(e2.getMessage());
               }
               return;
            }

            try {
               boolean confirm = AlertPopup.confirm("Delete User",
                     "Are you sure you want to delete this user?");
               if (confirm) {
                  if (userController.delete(selectedUser.getId())) {
                     try {
                        AlertPopup.success("User deleted successfully", "Deleted successfully");
                        userList.remove(selectedUser);
                        originalUserList.remove(selectedUser);
                     } catch (Exception e2) {
                        System.err.println(e2.getMessage());
                     }
                  } else {
                     try {
                        AlertPopup.error("Failed to delete user", "Deletion failed");
                     } catch (Exception e2) {
                        System.err.println(e2.getMessage());
                     }
                  }
               }
            } catch (Exception e2) {
               System.err.println(e2.getMessage());
            }
         } else {
            System.out.println("Please select a user to delete.");
         }
      });
   }

   private void setupTable() {
      ID.setCellValueFactory(cellData -> new SimpleStringProperty(
            String.valueOf(userTable.getItems().indexOf(cellData.getValue()) + 1)));
      Name.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
      Email.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
      Phone.setCellValueFactory(new PropertyValueFactory<User, String>("phone"));
      Member_ship.setCellValueFactory(new PropertyValueFactory<User, Integer>("membership_package"));
      Member_ship.setCellFactory(TextFieldTableCell.forTableColumn(new Membership()));
      Role.setCellValueFactory(new PropertyValueFactory<User, Integer>("role"));
      Role.setCellFactory(TextFieldTableCell.forTableColumn(new Role()));
      Create_At.setCellValueFactory(cellData -> {
         String formattedDate = formatDateTime(cellData.getValue().getCreated_at());
         return new SimpleStringProperty(formattedDate);
      });
      Expiration_Date.setCellValueFactory(cellData -> {
         return new SimpleStringProperty(cellData.getValue().getExpirationDays());
      });
      userTable.setItems(userList);
   }

   public void loadUsers() {
      userList.clear();
      originalUserList = userController.GetUsers();
      Collections.sort(originalUserList, UserService.sortByCreatedAt());
      userList.addAll(originalUserList);
   }

   public void refreshTable() {
      loadUsers();
   }

   private void searchUsers() {
      MembershipFilter selectedMembership = sortByMembershipComboBox.getValue();
      RoleFilter selectedRole = sortByRoleComboBox.getValue();
      String search = searchField.getText().toLowerCase();

      List<User> filteredUsers = originalUserList.stream()
            .filter(user -> (selectedRole.getValue() == 0 || user.getRole() == selectedRole.getValue()))
            .filter(user -> (selectedMembership.getValue() == 0
                  || user.getMembership_package() == selectedMembership.getValue()))
            .filter(user -> {
               int userIndex = originalUserList.indexOf(user) + 1;
               return String.valueOf(userIndex).contains(search) ||
                     user.getName().toLowerCase().contains(search) ||
                     user.getEmail().toLowerCase().contains(search) ||
                     user.getPhone().toLowerCase().contains(search);
            })
            .collect(Collectors.toList());

      userList.setAll(filteredUsers);
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
