package com.rentalhouse.app.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rentalhouse.app.models.User;
import com.rentalhouse.database.connection.DatabaseConnection;
import com.rentalhouse.utils.Uuid;

public class UserRepository implements IGenericRepository<User> {

   private final Connection connection = DatabaseConnection.getConnection();

   @Override
   public List<User> getAll() {
      List<User> users = new ArrayList<User>();
      String querySQL = "Call GetUsers(?)";
      try {
         try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
            preparedStatement.setInt(1, 0);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
               User user = new User();
               user.setId(resultSet.getString("id"));
               user.setName(resultSet.getString("name"));
               user.setEmail(resultSet.getString("email"));
               user.setPhone(resultSet.getString("phone"));
               user.setPassword(resultSet.getString("password"));
               user.setBank_name(resultSet.getString("bank_name"));
               user.setBank_number(resultSet.getString("bank_number"));
               user.setBank_QR(resultSet.getString("bank_QR"));
               user.setMembership_package(resultSet.getInt("membership_package"));
               user.setMembership_expire_at(resultSet.getString("membership_expire_at"));
               user.setRole(resultSet.getInt("role"));
               user.setIs_deleted(resultSet.getInt("is_deleted"));
               user.setCreated_at(resultSet.getString("created_at"));
               user.setUpdated_at(resultSet.getString("updated_at"));
               users.add(user);
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
      return users;
   }

   @Override
   public User getById(String id) {
      User user = null;
      String querySQL = "CALL GetByID(?)";
      try {
         try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
               user = new User();
               user.setId(resultSet.getString("id"));
               user.setName(resultSet.getString("name"));
               user.setEmail(resultSet.getString("email"));
               user.setPhone(resultSet.getString("phone"));
               user.setPassword(resultSet.getString("password"));
               user.setBank_name(resultSet.getString("bank_name"));
               user.setBank_number(resultSet.getString("bank_number"));
               user.setBank_QR(resultSet.getString("bank_QR"));
               user.setMembership_package(resultSet.getInt("membership_package"));
               user.setMembership_expire_at(resultSet.getString("membership_expire_at"));
               user.setRole(resultSet.getInt("role"));
               user.setIs_deleted(resultSet.getInt("is_deleted"));
               user.setCreated_at(resultSet.getString("created_at"));
               user.setUpdated_at(resultSet.getString("updated_at"));
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
      return user;
   }

   @Override
   public boolean create(User entity) {
      String querySQL = "CALL CreateUser(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
      String id = Uuid.get();
      try {
         try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setString(3, entity.getEmail());
            preparedStatement.setString(4, entity.getPhone());
            preparedStatement.setString(5, entity.getPassword());
            preparedStatement.setString(6, entity.getBank_name());
            preparedStatement.setString(7, entity.getBank_number());
            preparedStatement.setString(8, entity.getBank_QR());
            preparedStatement.setInt(9, entity.getMembership_package());
            preparedStatement.setInt(10, entity.getRole());
            preparedStatement.setInt(11, entity.getIs_deleted());
            preparedStatement.executeUpdate();
            return true;
         }
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }
   }

   @Override
   public boolean update(User entity) {
      String querySQL = "CALL UpdateUser(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
      try {
         try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
            preparedStatement.setString(1, entity.getId());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setString(3, entity.getEmail());
            preparedStatement.setString(4, entity.getPhone());
            preparedStatement.setString(5, entity.getPassword());
            preparedStatement.setString(6, entity.getBank_name());
            preparedStatement.setString(7, entity.getBank_number());
            preparedStatement.setString(8, entity.getBank_QR());
            preparedStatement.setInt(9, entity.getMembership_package());
            preparedStatement.setString(10, entity.getMembership_expire_at());
            preparedStatement.setInt(11, entity.getRole());
            preparedStatement.setInt(12, entity.getIs_deleted());
            preparedStatement.executeUpdate();
         }
         return true;
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }

   }

   @Override
   public boolean delete(String id) {
      String querySQL = "CALL DeleteUser(?)";
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

   public boolean checkEmailByID(String email, String userId) {
      String querySQL = "SELECT COUNT(*) FROM users WHERE email = ? AND id <> ? AND is_deleted = 0";
      try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
         preparedStatement.setString(1, email);
         preparedStatement.setString(2, userId);
         try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
               return resultSet.getInt(1) > 0;
            }
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return false;
   }

   public boolean checkEmail(String email) {
      String querySQL = "SELECT COUNT(*) FROM users WHERE email = ? AND is_deleted = 0";
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
      String querySQL = "SELECT COUNT(*) FROM users WHERE phone = ? AND is_deleted = 0";
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

   public boolean checkPhoneByID(String phone, String userId) {
      String querySQL = "SELECT COUNT(*) FROM users WHERE phone = ? AND id <> ? AND is_deleted = 0";
      try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
         preparedStatement.setString(1, phone);
         preparedStatement.setString(2, userId);
         try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
               return resultSet.getInt(1) > 0;
            }
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return false;
   }

   public int getQuantityLessor() {
      String querySQL = "SELECT COUNT(id) FROM users WHERE role = 2";
      try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
         ResultSet resultSet = preparedStatement.executeQuery();
         if (resultSet.next()) {
            return resultSet.getInt(1);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return 0;
   }

   public int getQuantityLessorFreeTrial() {
      String querySQL = "SELECT COUNT(id) FROM users WHERE role = 2 AND membership_package = 1 AND membership_expire_at >= now()";
      try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
         ResultSet resultSet = preparedStatement.executeQuery();
         if (resultSet.next()) {
            return resultSet.getInt(1);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return 0;
   }

   public int getQuantityLessorVIP() {
      String querySQL = "SELECT COUNT(id) FROM users WHERE role = 2 AND membership_package = 2 AND membership_expire_at >= now()";
      try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
         ResultSet resultSet = preparedStatement.executeQuery();
         if (resultSet.next()) {
            return resultSet.getInt(1);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return 0;
   }

   public int getQuantityLessorExpired() {
      String querySQL = "SELECT COUNT(id) FROM users WHERE role = 2 AND membership_expire_at < now()";
      try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
         ResultSet resultSet = preparedStatement.executeQuery();
         if (resultSet.next()) {
            return resultSet.getInt(1);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return 0;
   }

}
