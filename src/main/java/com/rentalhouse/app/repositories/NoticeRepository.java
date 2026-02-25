package com.rentalhouse.app.repositories;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rentalhouse.app.models.Notice;
import com.rentalhouse.database.connection.DatabaseConnection;

public class NoticeRepository implements IGenericRepository<Notice> {
   @Override
   public List<Notice> getAll() {
      return null;
   };

   @Override
   public Notice getById(String id) {
      return null;
   };

   @Override
   public boolean create(Notice notice) {
      try (Connection conn = DatabaseConnection.getConnection()) {
         CallableStatement pr = conn.prepareCall("{CALL INSERTNOTICE(?,?,?,?,?)}");
         conn.setAutoCommit(false);
         pr.setString(1, notice.getTitle());
         pr.setBigDecimal(2, new BigDecimal(notice.getAmount_received()));
         pr.setBigDecimal(3, new BigDecimal(notice.getRemaining_amount()));
         pr.setString(4, notice.getInvoiceID());
         pr.setString(5, notice.getDescription());
         conn.setAutoCommit(false);
         if (pr.executeUpdate() == 0) {
            conn.rollback();
            return false;
         }
         conn.commit();
         return true;
      } catch (SQLException e) {
         System.err.println("Create Invoice failed: " + e.getMessage());
         return false;
      }
   };

   @Override
   public boolean update(Notice entity) {
      return false;
   };

   @Override
   public boolean delete(String ID) {
      return false;
   };

   public Boolean delete(Notice notice) {
      try (Connection conn = DatabaseConnection.getConnection()) {
         PreparedStatement pr = conn.prepareStatement("DELETE FROM notices WHERE id = ?");
         pr.setInt(1, notice.getNoticeID());
         conn.setAutoCommit(false);
         if (pr.executeUpdate() == 0) {
            conn.rollback();
            return false;
         }
         conn.commit();
         return true;
      } catch (SQLException e) {
         System.err.println("Delete Notice Occurred an Error => " + e.getMessage());
         return false;
      }
   }
   public Boolean updateStatus(Notice notice, int status) {
      try (Connection conn = DatabaseConnection.getConnection()) {
         PreparedStatement pr = conn.prepareStatement("UPDATE notices SET status = ? WHERE id = ?");
         pr.setInt(1, status);
         pr.setInt(2, notice.getNoticeID());
         conn.setAutoCommit(false);
         if (pr.executeUpdate() == 0) {
            conn.rollback();
            return false;
         }
         conn.commit();
         return true;
      } catch (SQLException e) {
         System.err.println("Delete Notice Occurred an Error => " + e.getMessage());
         return false;
      }
   }

   public Notice getNotice(Notice notice) {
      try (Connection conn = DatabaseConnection.getConnection()) {
         PreparedStatement pr = conn.prepareStatement("SELECT * FROM notices WHERE id = ?");
         pr.setInt(1, notice.getNoticeID());
         ResultSet rs = pr.executeQuery();
         if (rs.next()) {
            return new Notice(
                  rs.getInt("id"),
                  String.valueOf(rs.getString("title")),
                  String.valueOf(rs.getBigDecimal("amount_received")),
                  String.valueOf(rs.getBigDecimal("remaining_amount")),
                  rs.getString("invoice_id"),
                  rs.getString("description"),
                  rs.getInt("status"),
                  rs.getDate("created_at"),
                  rs.getDate("updated_at"));
         }
      } catch (SQLException e) {
         System.err.println("Get notice failed: " + e.getMessage());
      }
      return null;
   }
   public Notice getNotice(String invoiceID) {
      try (Connection conn = DatabaseConnection.getConnection()) {
         PreparedStatement pr = conn.prepareStatement("SELECT * FROM notices WHERE invoice_id = ? ORDER BY created_at DESC LIMIT 1");
         pr.setString(1, invoiceID);
         ResultSet rs = pr.executeQuery();
         if (rs.next()) {
            return new Notice(
                  rs.getInt("id"),
                  String.valueOf(rs.getString("title")),
                  String.valueOf(rs.getBigDecimal("amount_received")),
                  String.valueOf(rs.getBigDecimal("remaining_amount")),
                  rs.getString("invoice_id"),
                  rs.getString("description"),
                  rs.getInt("status"),
                  rs.getDate("created_at"),
                  rs.getDate("updated_at"));
         }
      } catch (SQLException e) {
         System.err.println("Get notice failed: " + e.getMessage());
      }
      return null;
   }

   public List<Notice> getNotices(String invoiceID) {
      List<Notice> notices = new ArrayList<Notice>();
      try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pr = conn
                  .prepareStatement("SELECT * FROM notices WHERE invoice_id = ? ORDER BY created_at DESC");) {
         pr.setString(1, invoiceID);
         ResultSet rs = pr.executeQuery();
         while (rs.next()) {
            notices.add(new Notice(
                  rs.getInt("id"),
                  String.valueOf(rs.getString("title")),
                  String.valueOf(rs.getBigDecimal("amount_received")),
                  String.valueOf(rs.getBigDecimal("remaining_amount")),
                  rs.getString("invoice_id"),
                  rs.getString("description"),
                  rs.getInt("status"),
                  rs.getDate("created_at"),
                  rs.getDate("updated_at")));
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return notices;
   }

   private String query(String search, String invoiceID) {
      return (search.trim().isEmpty()) ? 
      "SELECT * FROM notices WHERE invoice_id = ? ORDER BY created_at DESC;" :
      "SELECT * FROM notices WHERE invoice_id = ? AND (title LIKE CONCAT('%', ?, '%') OR created_at LIKE CONCAT('%', ?, '%')) ORDER BY created_at DESC;";
   }

   public List<Notice> getList(String keyword, String invoiceID) {
      List<Notice> notices = new ArrayList<Notice>();
      try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pr = conn.prepareStatement(query(keyword, invoiceID));) {
         pr.setString(1, invoiceID);
         if (!keyword.trim().isEmpty()) {
            pr.setString(2, keyword);
            pr.setString(3, keyword);
         }
         ResultSet rs = pr.executeQuery();
         while (rs.next()) {
            notices.add(new Notice(
                  rs.getInt("id"),
                  String.valueOf(rs.getString("title")),
                  String.valueOf(rs.getBigDecimal("amount_received")),
                  String.valueOf(rs.getBigDecimal("remaining_amount")),
                  rs.getString("invoice_id"),
                  rs.getString("description"),
                  rs.getInt("status"),
                  rs.getDate("created_at"),
                  rs.getDate("updated_at")));
         }
      } catch (SQLException e) {
         System.err.println(e.getMessage());
      }
      return notices;
   }

   public Boolean existNotice(String invoiceID) {
      try (Connection conn = DatabaseConnection.getConnection();) {
         PreparedStatement pr = conn.prepareStatement("SELECT COUNT(*) FROM notices WHERE invoice_id = ?");
         pr.setString(1, invoiceID);
         ResultSet rs = pr.executeQuery();
         while (rs.next()) {
            return rs.getInt(1) > 0;
         }
      } catch (SQLException e) {
         System.err.println(e.getMessage());
      }
      return false;
   }

   public Integer getCountUnProcess(String invoiceID, int status) {
      try (Connection conn = DatabaseConnection.getConnection();) {
         PreparedStatement pr = conn.prepareStatement("SELECT COUNT(*) FROM notices WHERE invoice_id = ? AND status = ?");
         pr.setString(1, invoiceID);
         pr.setInt(2, status);
         ResultSet rs = pr.executeQuery();
         if (rs.next()) {
            return rs.getInt(1);
         }
      } catch (SQLException e) {
         System.err.println(e.getMessage());
      }
      return 0;
   }

   public Boolean deleteNotices(List<Notice> notices) {
      try (Connection conn = DatabaseConnection.getConnection();) {
         PreparedStatement pr = conn.prepareStatement("DELETE FROM notices WHERE invoice_id = ?;");
         conn.setAutoCommit(false);
         for (Notice notice : notices) {
            pr.setString(1, notice.getInvoiceID());
            pr.addBatch();
         }
         int[] results = pr.executeBatch();

         for (int result : results) {
               if (result == PreparedStatement.EXECUTE_FAILED) {
                  conn.rollback();
                  return false;
               }
         }
         conn.commit();
         return true;
      } catch (SQLException e) {
         System.err.println(e.getMessage());
         return false;
      }
   }

   public static void main(String[] args) {
      new NoticeRepository().getNotices("wWaXz-usg3-dyVf-ORjj").forEach(n -> System.out.println(n.toString()));
   }
}
