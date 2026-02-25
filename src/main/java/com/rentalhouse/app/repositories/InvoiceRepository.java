package com.rentalhouse.app.repositories;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rentalhouse.app.models.Invoice;
import com.rentalhouse.database.connection.DatabaseConnection;

public class InvoiceRepository implements IGenericRepository<Invoice> {

   // GET ALL INVOICES:
   @Override
   public List<Invoice> getAll() {
      List<Invoice> invoices = new ArrayList<>();
      try (Connection conn = DatabaseConnection.getConnection()) {
         PreparedStatement pr = conn.prepareStatement("SELECT * FROM invoices ORDER BY created_at DESC");
         ResultSet rs = pr.executeQuery();

         while (rs.next()) {
            invoices.add(new Invoice(
                  String.valueOf(rs.getString("id")),
                  String.valueOf(rs.getString("infrastructure_id")),
                  String.valueOf(rs.getBigDecimal("price")),
                  String.valueOf(rs.getBigDecimal("water_price")),
                  String.valueOf(rs.getBigDecimal("electricity_price")),
                  String.valueOf(rs.getLong("old_electricity_number")),
                  String.valueOf(rs.getLong("new_electricity_number")),
                  String.valueOf(rs.getLong("old_water_number")),
                  String.valueOf(rs.getLong("new_water_number")),
                  String.valueOf(rs.getBigDecimal("total_price")),
                  rs.getDate("created_at"),
                  rs.getInt("status")));
         }

      } catch (SQLException e) {
         System.err.println("MySql getAll() from InvoiceRepository failed! " + e.getMessage());
      }
      return invoices;
   }

   // GET ID INVOICES:
   @Override
   public Invoice getById(String id) {
      try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pr = conn.prepareStatement("SELECT * FROM invoices WHERE id = ?")) {
         pr.setString(1, id);
         ResultSet rs = pr.executeQuery();
         if (rs.next()) {
            return new Invoice(
                  String.valueOf(rs.getString("id")),
                  String.valueOf(rs.getString("infrastructure_id")),
                  String.valueOf(rs.getBigDecimal("price")),
                  String.valueOf(rs.getBigDecimal("water_price")),
                  String.valueOf(rs.getBigDecimal("electricity_price")),
                  String.valueOf(rs.getLong("old_electricity_number")),
                  String.valueOf(rs.getLong("new_electricity_number")),
                  String.valueOf(rs.getLong("old_water_number")),
                  String.valueOf(rs.getLong("new_water_number")),
                  String.valueOf(rs.getBigDecimal("total_price")),
                  rs.getDate("created_at"),
                  rs.getInt("status"));
         }
         System.out.println("DONE!");
      } catch (SQLException e) {
         System.err.println("GetIDInvoice() method Occurred an Error => " + e.getMessage());
      }
      System.out.println(id + " Not Found!");
      return null;
   }

   // ADD INVOICES:
   @Override
   public boolean create(Invoice invoice) {
      try (Connection conn = DatabaseConnection.getConnection()) {
         CallableStatement pr = conn.prepareCall("{CALL CREATEINVOICE(?,?,?,?,?,?,?,?,?,?)}");
         pr.setString(1, invoice.getInvoiveID());
         pr.setString(2, invoice.getInfrastructureID());
         pr.setBigDecimal(3, new BigDecimal(invoice.getPrice()));
         pr.setBigDecimal(4, new BigDecimal(invoice.getWater_price()));
         pr.setBigDecimal(5, new BigDecimal(invoice.getElectricity_price()));
         pr.setLong(6, Long.parseLong(invoice.getOldElectricity_number()));
         pr.setLong(7, Long.parseLong(invoice.getNewElectricity_number()));
         pr.setLong(8, Long.parseLong(invoice.getOldWater_number()));
         pr.setLong(9, Long.parseLong(invoice.getNewWater_number()));
         pr.setBigDecimal(10, new BigDecimal(invoice.getTotal_price()));

         // SET AUTOCOMMIT = FALSE TO SYNCHRONIZATION:
         conn.setAutoCommit(false);
         if (pr.executeUpdate() == 0) {
            conn.rollback();
            return false;
         }
         conn.commit();
         return true;
      } catch (SQLException e) {
         System.err.println("Create Invoice failed: " + e.getMessage());
      }
      return false;
   }

   // UPDATE INVOICE:
   @Override
   public boolean update(Invoice invoice) {
      try (Connection conn = DatabaseConnection.getConnection()) {
         CallableStatement stm = conn.prepareCall("{CALL UPDATEINVOICE(?,?,?,?,?,?,?,?,?,?)}");
         stm.setString(1, invoice.getInvoiveID());
         stm.setString(2, invoice.getInfrastructureID());
         stm.setBigDecimal(3, new BigDecimal(invoice.getPrice()));
         stm.setBigDecimal(4, new BigDecimal(invoice.getWater_price()));
         stm.setBigDecimal(5, new BigDecimal(invoice.getElectricity_price()));
         stm.setLong(6, Long.parseLong(invoice.getOldElectricity_number()));
         stm.setLong(7, Long.parseLong(invoice.getNewElectricity_number()));
         stm.setLong(8, Long.parseLong(invoice.getOldWater_number()));
         stm.setLong(9, Long.parseLong(invoice.getNewWater_number()));
         stm.setBigDecimal(10, new BigDecimal(invoice.getTotal_price()));

         // SET AUTOCOMMIT = FALSE TO SYNCHRONIZATION:
         conn.setAutoCommit(false);
         if (stm.executeUpdate() == 0) {
            conn.rollback();
            return false;
         }
         conn.commit();
         return true;
      } catch (SQLException e) {
         System.err.println("Update invoice Occurred an Error => " + e.getMessage());
         return false;
      }
   }

   // DELETE INVOICE:
   @Override
   public boolean delete(String id) {
      try (Connection conn = DatabaseConnection.getConnection()) {
         PreparedStatement pr = conn.prepareStatement("DELETE FROM invoices WHERE id = ?");
         pr.setString(1, id);
         conn.setAutoCommit(false);
         if (pr.executeUpdate() == 0) {
            conn.rollback();
            return false;
         }
         conn.commit();
         return true;

      } catch (SQLException e) {
         System.err.println("Delete invoice Occurred an Error => " + e.getMessage());
      }
      return false;
   }

   public Boolean isExisted(Invoice invoice) {
      try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pr = conn.prepareStatement("SELECT * FROM invoices WHERE ID = ?;")) {
         pr.setString(1, invoice.getInvoiveID());
         ResultSet rs = pr.executeQuery();

         if (rs.next()) {
            return true;
         }
      } catch (SQLException e) {
         System.err.println("Check isExisted failed => " + e.getMessage());
         throw new IllegalStateException("Check isExisted failed");
      }
      return false;
   }

   public Invoice getInvoice(String infrastructureID) {
      try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pr = conn.prepareStatement(
                  "SELECT * FROM invoices WHERE infrastructure_id = ? ORDER BY created_at DESC LIMIT 1;")) {
         pr.setString(1, infrastructureID);
         ResultSet rs = pr.executeQuery();
         if (rs.next()) {
            return new Invoice(
                  String.valueOf(rs.getString("id")),
                  String.valueOf(rs.getString("infrastructure_id")),
                  String.valueOf(rs.getBigDecimal("price")),
                  String.valueOf(rs.getBigDecimal("water_price")),
                  String.valueOf(rs.getBigDecimal("electricity_price")),
                  String.valueOf(rs.getLong("old_electricity_number")),
                  String.valueOf(rs.getLong("new_electricity_number")),
                  String.valueOf(rs.getLong("old_water_number")),
                  String.valueOf(rs.getLong("new_water_number")),
                  String.valueOf(rs.getBigDecimal("total_price")),
                  rs.getDate("created_at"),
                  rs.getInt("status"));
         }
      } catch (SQLException e) {
         System.err.println("getInvoice() where infrastructureID failed => " + e.getMessage());
      }
      return null;
   }

   private String query(String kw, String inFrasID) {
      return (kw.trim().isEmpty()) ? "SELECT * FROM invoices WHERE infrastructure_id = ?;" : """
            SELECT * FROM invoices WHERE infrastructure_id = ? AND (total_price LIKE CONCAT('%', ?, '%') OR created_at LIKE CONCAT('%', ?, '%')
            OR id LIKE CONCAT('%', ?, '%'));
            """;
   }

   public List<Invoice> getInvoices(String keyword, String inFrasID) {
      List<Invoice> invoices = new ArrayList<Invoice>();
      try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pr = conn.prepareStatement(query(keyword, inFrasID));) {
            pr.setString(1, inFrasID);
         if (!keyword.trim().isEmpty()) {
            pr.setString(2, keyword);
            pr.setString(3, keyword);
            pr.setString(4, keyword);
         }
         ResultSet rs = pr.executeQuery();
         while (rs.next()) {
            invoices.add(new Invoice(
                  String.valueOf(rs.getString("id")),
                  String.valueOf(rs.getString("infrastructure_id")),
                  String.valueOf(rs.getBigDecimal("price")),
                  String.valueOf(rs.getBigDecimal("water_price")),
                  String.valueOf(rs.getBigDecimal("electricity_price")),
                  String.valueOf(rs.getLong("old_electricity_number")),
                  String.valueOf(rs.getLong("new_electricity_number")),
                  String.valueOf(rs.getLong("old_water_number")),
                  String.valueOf(rs.getLong("new_water_number")),
                  String.valueOf(rs.getBigDecimal("total_price")),
                  rs.getDate("created_at"),
                  rs.getInt("status")));
         }
      } catch (SQLException e) {
         System.err.println(e.getMessage());
      }
      return invoices;
   }

   public void updateStatus(String invoiceID) {
      try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pr = conn.prepareStatement("UPDATE invoices SET status = 1 WHERE id = ?");) {
         pr.setString(1, invoiceID);
         conn.setAutoCommit(false);
         pr.executeUpdate();
         conn.commit();
      } catch (SQLException e) {
         System.err.println(e.getMessage());
      }
   }

   public String getQueryPriceTotal() {
      return """
            SELECT
               SUM(i.total_price) AS total,
               EXTRACT(MONTH FROM i.created_at) AS month,
               EXTRACT(YEAR FROM i.created_at) AS year
            FROM invoices i
            JOIN infrastructures inf ON i.infrastructure_id = inf.id
            JOIN users u ON inf.user_id = u.id
            WHERE u.id = ?
            GROUP BY year, month;
            """;
   }
   public Map<String, BigDecimal> getRevenueByMonths(String userID) {
      Map<String, BigDecimal> mapTotal = new HashMap<>();
      try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pr = conn.prepareStatement(getQueryPriceTotal());) {
               pr.setString(1, userID);
            ResultSet rs = pr.executeQuery();
               while (rs.next()) {
                  Integer month = rs.getInt("month");
                  Integer year = rs.getInt("year");
                  BigDecimal revenue = rs.getBigDecimal("total");
                  mapTotal.put(month + "-" + year, revenue);
               }
            } catch (SQLException e) {
               System.err.println(e.getMessage());
            }
         return mapTotal;
   }

   public String queryGetYear() {
      return """
            SELECT
               SUM(i.total_price) AS total
            FROM invoices i
            JOIN infrastructures inf ON i.infrastructure_id = inf.id
            JOIN users u ON inf.user_id = u.id
            WHERE EXTRACT(YEAR FROM i.created_at) = ? AND u.id = ?
            GROUP BY EXTRACT(YEAR FROM i.created_at);
            """;
   }
   public BigDecimal getTotalYear(Integer year, String userID) {
      try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pr = conn.prepareStatement(queryGetYear());) {
               pr.setInt(1, year);
               pr.setString(2, userID);
            ResultSet rs = pr.executeQuery();
               while (rs.next()) {
                  return rs.getBigDecimal("total");
               }
            } catch (SQLException e) {
               System.err.println(e.getMessage());
            }
         return BigDecimal.ZERO;
   }

   public String getQueryBySearchYear() {
      return """
               SELECT
                  SUM(i.total_price) AS total,
                  EXTRACT(MONTH FROM i.created_at) AS month,
                  EXTRACT(YEAR FROM i.created_at) AS year
               FROM invoices i
               JOIN infrastructures inf ON i.infrastructure_id = inf.id
               JOIN users u ON inf.user_id = u.id
               WHERE EXTRACT(YEAR FROM i.created_at) = ? AND u.id = ?
               GROUP BY year, month;
               """;
   }
   public Map<String, BigDecimal> getMonthBySearch(Integer year, String userID) {
      Map<String, BigDecimal> mapTotal = new HashMap<>();
      try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pr = conn.prepareStatement(getQueryBySearchYear());) {
            pr.setInt(1, year);
            pr.setString(2, userID);
            ResultSet rs = pr.executeQuery();
               while (rs.next()) {
                  Integer getMonth = rs.getInt("month");
                  Integer getYear = rs.getInt("year");
                  BigDecimal revenue = rs.getBigDecimal("total");
                  mapTotal.put(getMonth + "-" + getYear, revenue);
               }
            } catch (SQLException e) {
               System.err.println(e.getMessage());
            }
      return mapTotal;
   }

   public String getQueryInvoiceByMonth() {
      return """
            SELECT i.*
            FROM invoices i
            JOIN infrastructures inf ON i.infrastructure_id = inf.id
            JOIN users u ON inf.user_id = u.id
            WHERE EXTRACT(MONTH FROM i.created_at) = ? 
               AND EXTRACT(YEAR FROM i.created_at) = ? 
               AND u.id = ?
            ORDER BY i.created_at DESC;
            """;
   }
   public List<Invoice> getInvoicesByMonth(String date, String userID) {
      List<Invoice> invoices = new ArrayList<>();
      try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pr = conn.prepareStatement(getQueryInvoiceByMonth())) {
         String[] splitDate = date.split("-");
         Integer month = Integer.parseInt(splitDate[0]);
         Integer year = Integer.parseInt(splitDate[1]);
         pr.setInt(1, month);
         pr.setInt(2, year);
         pr.setString(3, userID);
         ResultSet rs = pr.executeQuery();
         while (rs.next()) {
            invoices.add(new Invoice(
               String.valueOf(rs.getString("id")),
               String.valueOf(rs.getString("infrastructure_id")),
               String.valueOf(rs.getBigDecimal("price")),
               String.valueOf(rs.getBigDecimal("water_price")),
               String.valueOf(rs.getBigDecimal("electricity_price")),
               String.valueOf(rs.getLong("old_electricity_number")),
               String.valueOf(rs.getLong("new_electricity_number")),
               String.valueOf(rs.getLong("old_water_number")),
               String.valueOf(rs.getLong("new_water_number")),
               String.valueOf(rs.getBigDecimal("total_price")),
               rs.getDate("created_at"),
               rs.getInt("status")));
         }
      } catch (SQLException e) {
         System.err.println("getInvoice() where infrastructureID failed => " + e.getMessage());
      }
      return invoices;
   }

   public static void main(String[] args) {
      List<Invoice> invoices = new InvoiceRepository().getInvoicesByMonth("7-2024", "krma-6T1d-1BLD-7ifM");
      invoices.stream().forEach(invoice -> {
         System.out.println(invoice.toString());
      });
   }
}
