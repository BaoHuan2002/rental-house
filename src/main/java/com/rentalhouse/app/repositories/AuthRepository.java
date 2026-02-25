package com.rentalhouse.app.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.rentalhouse.app.models.User;
import com.rentalhouse.database.connection.DatabaseConnection;

public class AuthRepository {
   private final Connection connection = DatabaseConnection.getConnection();

   public boolean doesUserExist(String email_or_phone_param) throws SQLException {
      String checkSQL = "Call CheckUsers(?)";
      try (PreparedStatement preparedStatement = connection.prepareStatement(checkSQL)) {
         preparedStatement.setString(1, email_or_phone_param);
         ResultSet resultSet = preparedStatement.executeQuery();
         return resultSet.next();
      }
   }

   public boolean register(User user) {
      String querySQL = "Call Register(?,?,?,?,?,?)";
      try {
         try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
            preparedStatement.setString(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPhone());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setInt(6, user.getRole());
            return preparedStatement.executeUpdate() == 1;
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
      return false;
   }

   public User login(String emailOrPhone) {
      String querySQL = "Call Login(?)";
      try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
         preparedStatement.setString(1, emailOrPhone);
         ResultSet resultSet = preparedStatement.executeQuery();
         if (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getString("id"));
            user.setName(resultSet.getString("name"));
            user.setEmail(resultSet.getString("email"));
            user.setPhone(resultSet.getString("phone"));
            user.setPassword(resultSet.getString("password"));
            user.setRole(resultSet.getInt("role"));
            user.setMembership_package(resultSet.getInt("membership_package"));
            user.setMembership_expire_at(resultSet.getString("membership_expire_at"));
            user.setBank_QR(resultSet.getString("bank_QR"));
            user.setBank_name(resultSet.getString("bank_name"));
            user.setBank_number(resultSet.getString("bank_number"));
            user.setIs_deleted(resultSet.getInt("is_deleted"));
            user.setCreated_at(resultSet.getDate("created_at").toString());
            user.setUpdated_at(resultSet.getDate("updated_at").toString());
            user.setMembership_expire_at(resultSet.getString("membership_expire_at"));
            return user;
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
      return null;
   }

   public boolean updatePassword(String email, String newPassword ){
      String querySQL = "Call UpdatePassword(?,?)";
      try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
         preparedStatement.setString(1,email );
         preparedStatement.setString(2,newPassword);
         return preparedStatement.executeUpdate() == 1;
      } catch (Exception e) {
         e.printStackTrace();
      }
      return false;
   }

   public boolean checkEmail(String inputEmail) throws SQLException {
      String checkSQL = "Call EmailExist(?)";
      try (PreparedStatement preparedStatement = connection.prepareStatement(checkSQL)) {
         preparedStatement.setString(1, inputEmail);
         ResultSet resultSet = preparedStatement.executeQuery();
         return resultSet.next();
      }
   }
   
}