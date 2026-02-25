package com.rentalhouse.app.repositories;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rentalhouse.app.models.Auth;
import com.rentalhouse.app.models.Infrastructure;
import com.rentalhouse.database.connection.DatabaseConnection;

public class InfrastructureRepository implements IGenericRepository<Infrastructure> {
   private final Connection connection = DatabaseConnection.getConnection();

   public List<Infrastructure> getAll() {
      List<Infrastructure> infrastructures = new ArrayList<Infrastructure>();

      String querySQL = "{CALL GetInfrastructures(?)}";
      try {
         try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
            preparedStatement.setString(1, Auth.getUser().getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
               Infrastructure infrastructure = new Infrastructure();
               infrastructure.setId(resultSet.getString(1));
               infrastructure.setName(resultSet.getString(2));
               infrastructure.setPrice(resultSet.getBigDecimal(3));
               infrastructure.setCategory(resultSet.getInt(4));
               infrastructure.setUser_id(resultSet.getString(6));
               infrastructure.setElectricity_price(resultSet.getBigDecimal(7));
               infrastructure.setWater_price(resultSet.getBigDecimal(8));
               infrastructure.setElectricity_number(resultSet.getLong(9));
               infrastructure.setNew_electricity_number(resultSet.getLong(10));
               infrastructure.setWater_number(resultSet.getLong(11));
               infrastructure.setNew_water_number(resultSet.getLong(12));
               infrastructure.setStatus(resultSet.getInt(13));
               infrastructure.setRental_at(resultSet.getString(14));
               infrastructures.add(infrastructure);
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
      return infrastructures;
   }

   public boolean create(Infrastructure infrastructures) {
      try {
         String sqlCreate = "{CALL CreateInfrastructures(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
         CallableStatement preparedStatement = connection.prepareCall(sqlCreate);
         preparedStatement.setString(1, infrastructures.getId());
         preparedStatement.setString(2, infrastructures.getName());
         preparedStatement.setBigDecimal(3, infrastructures.getPrice());
         preparedStatement.setInt(4, infrastructures.getCategory());
         preparedStatement.setString(5, infrastructures.getUser_id());
         preparedStatement.setBigDecimal(6, infrastructures.getElectricity_price());
         preparedStatement.setBigDecimal(7, infrastructures.getWater_price());
         preparedStatement.setLong(8, infrastructures.getElectricity_number());
         preparedStatement.setLong(9, infrastructures.getElectricity_number());
         preparedStatement.setLong(10, infrastructures.getWater_number());
         preparedStatement.setLong(11, infrastructures.getWater_number());
         preparedStatement.setInt(12, infrastructures.getStatus());
         preparedStatement.setDate(13, null);
         return preparedStatement.executeUpdate() == 1;
      } catch (Exception e) {
         e.printStackTrace();
      }
      return false;

   }

   @Override
   public Infrastructure getById(String id) {
      String sqlGetById = "{CALL GetInfrastructuresById(?)}";
      Infrastructure infrastructure = new Infrastructure();
      try {
         PreparedStatement preparedStatement = connection.prepareStatement(sqlGetById);
         preparedStatement.setString(1, id);
         ResultSet resultSet = preparedStatement.executeQuery();

         while (resultSet.next()) {
            infrastructure.setId(resultSet.getString(1));
            infrastructure.setName(resultSet.getString(2));
            infrastructure.setPrice(resultSet.getBigDecimal(3));
            infrastructure.setCategory(resultSet.getInt(4));
            infrastructure.setTenant_quantity(resultSet.getInt(5));
            infrastructure.setUser_id(resultSet.getString(6));
            infrastructure.setElectricity_price(resultSet.getBigDecimal(7));
            infrastructure.setWater_price(resultSet.getBigDecimal(8));
            infrastructure.setElectricity_number(resultSet.getLong(9));
            infrastructure.setNew_electricity_number(resultSet.getLong(10));
            infrastructure.setWater_number(resultSet.getLong(11));
            infrastructure.setNew_water_number(resultSet.getLong(12));
            infrastructure.setStatus(resultSet.getInt(13));
            infrastructure.setRental_at(resultSet.getString(14));
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
      return infrastructure;
   }

   @Override
   public boolean update(Infrastructure infrastructures) {
      String sqlUpdate = "{CALL EditInfrastructures(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
      try {
         PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);
         preparedStatement.setString(1, infrastructures.getId());
         preparedStatement.setString(2, infrastructures.getName());
         preparedStatement.setBigDecimal(3, infrastructures.getPrice());
         preparedStatement.setInt(4, infrastructures.getCategory());
         preparedStatement.setString(5, infrastructures.getUser_id());
         preparedStatement.setBigDecimal(6, infrastructures.getElectricity_price());
         preparedStatement.setBigDecimal(7, infrastructures.getWater_price());
         preparedStatement.setLong(8, infrastructures.getElectricity_number());
         preparedStatement.setLong(9, infrastructures.getElectricity_number());
         preparedStatement.setLong(10, infrastructures.getWater_number());
         preparedStatement.setLong(11, infrastructures.getWater_number());
         preparedStatement.setInt(12, infrastructures.getStatus());
         preparedStatement.setInt(13, infrastructures.getTenant_quantity());
         preparedStatement.setString(14, infrastructures.getRental_at());
         return preparedStatement.executeUpdate() == 1;
      } catch (Exception e) {
         e.printStackTrace();
      }
      return false;

   }

   @Override
   public boolean delete(String id) {
      try {
         String sqlDelete = "{CALL DeleteInfrastructures(?)}";
         PreparedStatement preparedStatement = connection.prepareStatement(sqlDelete);
         preparedStatement.setString(1, id);
         return preparedStatement.executeUpdate() == 1;
      } catch (Exception e) {
         e.printStackTrace();
      }
      return false;
   }


   public boolean updateElectricityAndWaterNumbers(String id, Long new_electricity_number, Long new_water_number) {
      try {
         String sqlDelete = "{CALL UpdateElectricityAndWaterNumbers(?,?,?)}";
         PreparedStatement preparedStatement = connection.prepareStatement(sqlDelete);
         preparedStatement.setString(1, id);
         preparedStatement.setLong(2, new_electricity_number);
         preparedStatement.setLong(3, new_water_number);
         return preparedStatement.executeUpdate() == 1;
      } catch (Exception e) {
         e.printStackTrace();
      }
      return false;
   }

   public boolean updateWaterAndElectricity(String electricNumber, String waterNumber, String infrastructureID) {
      try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pr = conn.prepareStatement("{CALL UpdateElectricityWaterNumber(?, ?, ?)}")) {
            conn.setAutoCommit(false);
            pr.setLong(1, Long.parseLong(electricNumber));
            pr.setLong(2, Long.parseLong(waterNumber));
            pr.setString(3, infrastructureID);
            pr.executeUpdate();
            conn.commit();
            return pr.executeUpdate() == 1;
      } catch (Exception e) {
         System.err.println(e.getMessage());
      }
      return false;
   }

   public static void main(String[] args) {

   }
}
