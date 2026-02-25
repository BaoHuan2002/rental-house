package com.rentalhouse.database.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.cj.jdbc.Driver;
import com.rentalhouse.configs.DotEnv;

public class DatabaseConnection {
   private static final String URL = DotEnv.get("DATABASE_URL");
   private static final String USERNAME = DotEnv.get("DATABASE_USERNAME");
   private static final String PASSWORD = DotEnv.get("DATABASE_PASSWORD");

   public static Connection getConnection() {
      try {
         Connection c = null;
         DriverManager.registerDriver(new Driver());
         c = DriverManager.getConnection(URL, USERNAME, PASSWORD);
         return c;
      } catch (SQLException e) {
         e.printStackTrace();
         return null;
      }
   }

   public static void closeConnection(Connection c) {
      try {
         c.close();
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   public static void main(String[] args) {
      System.out.println(DatabaseConnection.getConnection());
   }

}
