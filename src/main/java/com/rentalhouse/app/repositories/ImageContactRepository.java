package com.rentalhouse.app.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rentalhouse.app.models.ImageContact;
import com.rentalhouse.database.connection.DatabaseConnection;
import com.rentalhouse.utils.Uuid;

public class ImageContactRepository {
   private final Connection connection = DatabaseConnection.getConnection();

   public List<ImageContact> getImageContactByID(String contactID){
      List<ImageContact> imageContacts = new ArrayList<>();
      String querySQL = "Call GetImageContactByID(?)";
      try {
         try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
            preparedStatement.setString(1, contactID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
               ImageContact imageContact = new ImageContact();
               imageContact.setId(resultSet.getString("id"));
               imageContact.setImages(resultSet.getString("images"));
               imageContact.setContact_id(resultSet.getString("contact_id"));
               imageContacts.add(imageContact);
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
      return imageContacts;
   }

   public boolean uploadImageContact(String contactID, String imageBase64) {
      String querySQL = "CALL UploadImageContact(?, ?, ?)";
      String id = Uuid.get();
      try {
         try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, imageBase64);
            preparedStatement.setString(3, contactID);
            preparedStatement.executeUpdate();
            return true;
         }
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }
   }

   public boolean deleteImageContact(String contactID) {
      String querySQL = "CALL DeleteImageContact(?)";
      try {
         try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
            preparedStatement.setString(1, contactID);
            preparedStatement.executeUpdate();
            return true;
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return false;
   }
}
