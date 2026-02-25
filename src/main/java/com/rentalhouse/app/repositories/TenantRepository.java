package com.rentalhouse.app.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rentalhouse.app.models.Tenant;
import com.rentalhouse.database.connection.DatabaseConnection;
import com.rentalhouse.utils.Uuid;

public class TenantRepository implements IGenericRepository<Tenant> {
   private final Connection connection = DatabaseConnection.getConnection();

   @Override
   public boolean create(Tenant tenant) {
      String querySQL = "Call CreateTenant(?,?,?,?,?,?,?,?,?,?,?)";
      try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
         preparedStatement.setString(1, Uuid.get());
         preparedStatement.setString(2, tenant.getInfrastructure_id());
         preparedStatement.setString(3, tenant.getName());
         preparedStatement.setString(4, tenant.getPhone());
         preparedStatement.setString(5, tenant.getAddress());
         preparedStatement.setString(6, tenant.getId_number());
         preparedStatement.setInt(7, tenant.getGender());
         preparedStatement.setString(8, tenant.getImage_3x4());
         preparedStatement.setString(9, tenant.getImage_backside_id_card());
         preparedStatement.setString(10, tenant.getImage_front_id_card());
         preparedStatement.setInt(11, tenant.getStatus());
         return preparedStatement.executeUpdate() == 1;
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }
   }

   @Override
   public List<Tenant> getAll() {
      List<Tenant> tenants = new ArrayList<>();
      String querySQL = "Call GetTenants(?)";
      try {
         try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
            preparedStatement.setInt(1, 3);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
               Tenant tenant = new Tenant();
               tenant.setId(resultSet.getString("id"));
               tenant.setInfrastructure_id(resultSet.getString("infrastructure_id"));
               tenant.setName(resultSet.getString("name"));
               tenant.setPhone(resultSet.getString("phone"));
               tenant.setAddress(resultSet.getString("address"));
               tenant.setId_number(resultSet.getString("id_number"));
               tenant.setGender(resultSet.getInt("gender"));
               tenant.setImage_3x4(resultSet.getString("image_3x4"));
               tenant.setImage_backside_id_card(resultSet.getString("image_backside_id_card"));
               tenant.setImage_front_id_card(resultSet.getString("image_front_id_card"));
               tenant.setStatus(resultSet.getInt("status"));
               tenants.add(tenant);
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
      return tenants;
   }

   public List<Tenant> getTenantsByInfrastructureId(String infrastructureId) {
      List<Tenant> tenants = new ArrayList<>();
      String querySQL = "Call GetTenantsByInfrastructureId(?, ?)";
      try {
         try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
            preparedStatement.setInt(1, 3);
            preparedStatement.setString(2, infrastructureId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
               Tenant tenant = new Tenant();
               tenant.setId(resultSet.getString("id"));
               tenant.setInfrastructure_id(resultSet.getString("infrastructure_id"));
               tenant.setName(resultSet.getString("name"));
               tenant.setPhone(resultSet.getString("phone"));
               tenant.setAddress(resultSet.getString("address"));
               tenant.setId_number(resultSet.getString("id_number"));
               tenant.setGender(resultSet.getInt("gender"));
               tenant.setImage_3x4(resultSet.getString("image_3x4"));
               tenant.setImage_backside_id_card(resultSet.getString("image_backside_id_card"));
               tenant.setImage_front_id_card(resultSet.getString("image_front_id_card"));
               tenant.setStatus(resultSet.getInt("status"));
               tenants.add(tenant);
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
      return tenants;
   }

   @Override
   public Tenant getById(String id) {
      String querySQL = "Call GetTenant(?)";
      try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
         preparedStatement.setString(1, id);
         ResultSet resultSet = preparedStatement.executeQuery();
         if (resultSet.next()) {
            Tenant tenant = new Tenant();
            tenant.setId(resultSet.getString("id"));
            tenant.setInfrastructure_id(resultSet.getString("infrastructure_id"));
            tenant.setName(resultSet.getString("name"));
            tenant.setPhone(resultSet.getString("phone"));
            tenant.setAddress(resultSet.getString("address"));
            tenant.setId_number(resultSet.getString("id_number"));
            tenant.setGender(resultSet.getInt("gender"));
            tenant.setImage_3x4(resultSet.getString("image_3x4"));
            tenant.setImage_backside_id_card(resultSet.getString("image_backside_id_card"));
            tenant.setImage_front_id_card(resultSet.getString("image_front_id_card"));
            tenant.setStatus(resultSet.getInt("status"));
            return tenant;
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return null;
   }

   @Override
   public boolean update(Tenant tenant) {
      String querySQL = "Call UpdateTenant(?,?,?,?,?,?,?,?,?,?,?)";
      try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
         preparedStatement.setString(1, tenant.getId());
         preparedStatement.setString(2, tenant.getInfrastructure_id());
         preparedStatement.setString(3, tenant.getName());
         preparedStatement.setString(4, tenant.getPhone());
         preparedStatement.setString(5, tenant.getAddress());
         preparedStatement.setString(6, tenant.getId_number());
         preparedStatement.setInt(7, tenant.getGender());
         preparedStatement.setString(8, tenant.getImage_3x4());
         preparedStatement.setString(9, tenant.getImage_backside_id_card());
         preparedStatement.setString(10, tenant.getImage_front_id_card());
         preparedStatement.setInt(11, tenant.getStatus());
         return preparedStatement.executeUpdate() == 1;
      } catch (Exception e) {
         e.printStackTrace();
         return false;
      }
   }

   @Override
   public boolean delete(String id) {
      String querySQL = "Call DeleteTenant(?)";
      try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
         preparedStatement.setString(1, id);
         return preparedStatement.executeUpdate() == 1;
      } catch (Exception e) {
         e.printStackTrace();
         return false;
      }
   }

   
}
