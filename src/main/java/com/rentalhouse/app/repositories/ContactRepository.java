package com.rentalhouse.app.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rentalhouse.app.models.Contact;
import com.rentalhouse.database.connection.DatabaseConnection;

public class ContactRepository implements IGenericRepository<Contact> {
   private final Connection connection = DatabaseConnection.getConnection();

   @Override
   public List<Contact> getAll() {
      List<Contact> contacts = new ArrayList<Contact>();
      String querySQL = "Call GetAllContact(?)";
      try {
         try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
            preparedStatement.setInt(1, 0);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
               Contact contact = new Contact();
               contact.setId(resultSet.getString("id"));
               contact.setName(resultSet.getString("name"));
               contact.setTitle(resultSet.getString("title"));
               contact.setEmail(resultSet.getString("email"));
               contact.setPhone(resultSet.getString("phone"));
               contact.setDescription(resultSet.getString("description"));
               contact.setStatus(resultSet.getInt("status"));
               contact.setIs_deleted(resultSet.getInt("is_deleted"));
               contact.setCreated_at(resultSet.getString("created_at"));
               contact.setUpdated_at(resultSet.getString("updated_at"));
               contacts.add(contact);
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
      return contacts;
   };

   @Override
   public Contact getById(String id) {
      Contact contact = null;
      String querySQL = "CALL GetContactByID(?)";
      try {
         try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
               contact = new Contact();
               contact.setId(resultSet.getString("id"));
               contact.setName(resultSet.getString("name"));
               contact.setTitle(resultSet.getString("title"));
               contact.setEmail(resultSet.getString("email"));
               contact.setPhone(resultSet.getString("phone"));
               contact.setDescription(resultSet.getString("description"));
               contact.setStatus(resultSet.getInt("status"));
               contact.setIs_deleted(resultSet.getInt("is_deleted"));
               contact.setCreated_at(resultSet.getString("created_at"));
               contact.setUpdated_at(resultSet.getString("updated_at"));
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
      return contact;
   };

   @Override
   public boolean create(Contact entity) {
      String querySQL = "CALL CreateContact(?, ?, ?, ?, ?, ?, ?, ?)";
      try {
         try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
            preparedStatement.setString(1, entity.getId());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setString(3, entity.getTitle());
            preparedStatement.setString(4, entity.getEmail());
            preparedStatement.setString(5, entity.getPhone());
            preparedStatement.setString(6, entity.getDescription());
            preparedStatement.setInt(7, 2);
            preparedStatement.setInt(8, 0);
            preparedStatement.executeUpdate();
            return true;
         }
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }
   }

   public boolean update(Contact entity) {
      return false;
   }

   @Override
   public boolean delete(String id) {
      String querySQL = "CALL DeleteContact(?)";
      try {
         try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
            return true;
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return false;
   }

   public boolean checkEmail(String email) {
      String querySQL = "SELECT COUNT(*) FROM contacts WHERE email = ?";
      try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
         preparedStatement.setString(1, email);
         ResultSet resultSet = preparedStatement.executeQuery();
         if (resultSet.next()) {
            return resultSet.getInt(1) > 0;
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return false;
   }

   public boolean checkPhone(String phone) {
      String querySQL = "SELECT COUNT(*) FROM contacts WHERE phone = ?";
      try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
         preparedStatement.setString(1, phone);
         ResultSet resultSet = preparedStatement.executeQuery();
         if (resultSet.next()) {
            return resultSet.getInt(1) > 0;
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return false;
   }

   public boolean ChangeStatusContact(String id, int newStatus) {
      String query = "UPDATE contacts SET status = ? WHERE id = ?";
      try (PreparedStatement stmt = connection.prepareStatement(query)) {
         stmt.setInt(1, newStatus);
         stmt.setString(2, id);
         int rowsUpdated = stmt.executeUpdate();
         return rowsUpdated > 0;
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }
   }
}
