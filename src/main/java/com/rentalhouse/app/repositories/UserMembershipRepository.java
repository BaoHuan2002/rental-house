package com.rentalhouse.app.repositories;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rentalhouse.app.models.UserMembership;
import com.rentalhouse.database.connection.DatabaseConnection;

public class UserMembershipRepository implements IGenericRepository<UserMembership> {
   private final Connection connection = DatabaseConnection.getConnection();

   @Override
   public List<UserMembership> getAll() {
      List<UserMembership> memberships = new ArrayList<>();
      String querySQL = "SELECT * FROM user_membership ORDER BY created_at DESC";
      try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
         ResultSet resultSet = preparedStatement.executeQuery();
         while (resultSet.next()) {
            UserMembership userMembership = new UserMembership();
            userMembership.setId(resultSet.getInt("id"));
            userMembership.setUser_id(resultSet.getString("user_id"));
            userMembership.setMembership_package(resultSet.getInt("membership_package"));
            userMembership.setPrice(resultSet.getBigDecimal("price"));
            userMembership.setCreated_at(resultSet.getString("created_at"));
            memberships.add(userMembership);
         }
         return memberships;
      } catch (SQLException e) {
         e.printStackTrace();
         return null;
      }
   }

   @Override
   public UserMembership getById(String id) {
      return null;
   }

   public List<UserMembership> getAllByUserId(String id) {
      List<UserMembership> memberships = new ArrayList<>();
      String querySQL = "SELECT * FROM user_membership WHERE user_id = ? ORDER BY created_at DESC";
      try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
         preparedStatement.setString(1, id);
         ResultSet resultSet = preparedStatement.executeQuery();
         while (resultSet.next()) {
            UserMembership userMembership = new UserMembership();
            userMembership.setId(resultSet.getInt("id"));
            userMembership.setUser_id(resultSet.getString("user_id"));
            userMembership.setMembership_package(resultSet.getInt("membership_package"));
            userMembership.setCreated_at(resultSet.getString("created_at"));
            memberships.add(userMembership);
         }
         return memberships;
      } catch (SQLException e) {
         e.printStackTrace();
         return null;
      }
   }

   @Override
   public boolean create(UserMembership entity) {
      String querySql = "Call InsertUserMembership(?,?,?)";
      try (PreparedStatement preparedStatement = connection.prepareStatement(querySql)) {
         preparedStatement.setString(1, entity.getUser_id());
         preparedStatement.setInt(2, entity.getMembership_package());
         preparedStatement.setBigDecimal(3, entity.getPrice());
         preparedStatement.executeUpdate();
         return true;
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }
   }

   @Override
   public boolean update(UserMembership entity) {
      return false;
   }

   @Override
   public boolean delete(String id) {
      return false;
   }

   public BigDecimal getTotalPriceByMonthAndYear(int month, int year) {
      String querySQL = "SELECT SUM(price) AS total_price FROM `user_membership` WHERE MONTH(created_at) = ? AND YEAR(created_at) = ?;";
      try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
         preparedStatement.setInt(1, month);
         preparedStatement.setInt(2, year);
         ResultSet resultSet = preparedStatement.executeQuery();
         if (resultSet.next()) {
            return resultSet.getBigDecimal("total_price");
         }
         return BigDecimal.ZERO; // Return 0 if no result
      } catch (SQLException e) {
         e.printStackTrace();
         return null;
      }
   }

   public List<Integer> getAllYears() {
      String querySQL = "SELECT DISTINCT YEAR(created_at) AS year FROM `user_membership` ORDER BY year DESC;";
      try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
         ResultSet resultSet = preparedStatement.executeQuery();
         List<Integer> years = new ArrayList<>();
         while (resultSet.next()) {
            years.add(resultSet.getInt("year"));
         }
         return years;
      } catch (SQLException e) {
         e.printStackTrace();
         return null;
      }
   }

   public int getQuantityPackageByMonthAndYear(int packageNumber, int month, int year) {
      String querySQL = "SELECT COUNT(id) AS quantity FROM `user_membership` WHERE membership_package = ? AND MONTH(created_at) = ? AND YEAR(created_at) = ?;";
      try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
         preparedStatement.setInt(1, packageNumber);
         preparedStatement.setInt(2, month);
         preparedStatement.setInt(3, year);
         ResultSet resultSet = preparedStatement.executeQuery();
         if (resultSet.next()) {
            return resultSet.getInt("quantity");
         }
         return 0;
      } catch (SQLException e) {
         e.printStackTrace();
         return 0;
      }
   }

   public BigDecimal getTotalPriceRevenueByMonthAndYear(int month, int year) {
      String querySQL = "SELECT sum(price) as total_price FROM `user_membership` WHERE MONTH(created_at) = ? AND YEAR(created_at) = ?;";
      try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
         preparedStatement.setInt(1, month);
         preparedStatement.setInt(2, year);
         ResultSet resultSet = preparedStatement.executeQuery();
         if (resultSet.next()) {
            return resultSet.getBigDecimal("total_price");
         }
         return BigDecimal.ZERO;
      } catch (SQLException e) {
         e.printStackTrace();
         return null;
      }
   }

   public List<UserMembership> getAllByMonthAndYear(int month, int year) {
      String querySQL = "Call GetUserMembershipByMonthAndYear(?,?)";
      try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
         preparedStatement.setInt(1, month);
         preparedStatement.setInt(2, year);
         ResultSet resultSet = preparedStatement.executeQuery();
         List<UserMembership> memberships = new ArrayList<>();
         while (resultSet.next()) {
            UserMembership userMembership = new UserMembership();
            userMembership.setId(resultSet.getInt("id"));
            userMembership.setUser_id(resultSet.getString("user_id"));
            userMembership.setMembership_package(resultSet.getInt("membership_package"));
            userMembership.setPrice(resultSet.getBigDecimal("price"));
            userMembership.setCreated_at(resultSet.getString("created_at"));
            memberships.add(userMembership);
         }
         return memberships;
      } catch (SQLException e) {
         e.printStackTrace();
         return null;
      }

   }

}
