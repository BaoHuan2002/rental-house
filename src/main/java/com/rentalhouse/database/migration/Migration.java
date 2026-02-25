package com.rentalhouse.database.migration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rentalhouse.configs.DotEnv;
import com.rentalhouse.database.connection.DatabaseConnection;

public class Migration {

   private static final Connection connection = DatabaseConnection.getConnection();

   private static String[] readAllFilesInFolder(String folderPath) throws IOException, URISyntaxException {
      URL url = Migration.class.getResource(folderPath);
      File folder = new File(url.toURI());
      File[] files = folder.listFiles((dir, name) -> name.endsWith(".sql"));

      if (files == null) {
         return new String[0];
      }

      List<String> fileContents = new ArrayList<>();
      System.out.println("================================");
      for (File file : files) {
         if (file.isFile()) {
            System.out.println(file.getName());
            String content = readSqlFile(file);
            fileContents.add(content);
         }
      }
      System.out.println("================================");
      return fileContents.toArray(new String[0]);
   }

   private static String readSqlFile(File file) throws IOException {
      StringBuilder str = new StringBuilder();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
         String line;
         while ((line = br.readLine()) != null) {
            str.append(line);
            str.append(System.lineSeparator());
         }
      }
      return str.toString();
   }

   private static void dropTableSQL() {
      try (PreparedStatement preparedStatement = connection.prepareStatement("SHOW TABLES");
            ResultSet resultSet = preparedStatement.executeQuery()) {
         try (PreparedStatement disableFKChecks = connection.prepareStatement("SET FOREIGN_KEY_CHECKS = 0")) {
            disableFKChecks.executeUpdate();
         }
         while (resultSet.next()) {
            String dropTableSQL = "DROP TABLE IF EXISTS " + resultSet.getString(1);
            try (PreparedStatement dropStatement = connection.prepareStatement(dropTableSQL)) {
               dropStatement.executeUpdate();
            }
         }
         try (PreparedStatement enableFKChecks = connection.prepareStatement("SET FOREIGN_KEY_CHECKS = 1")) {
            enableFKChecks.executeUpdate();
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   private static void dropProcedureSql() {
      try {
         final String databaseName = DotEnv.get("DATABASE_NAME");
         String showProcedure = "SELECT ROUTINE_NAME " +
               "FROM INFORMATION_SCHEMA.ROUTINES " +
               "WHERE ROUTINE_TYPE = 'PROCEDURE' AND ROUTINE_SCHEMA = '" + databaseName + "';";

         try (PreparedStatement preparedStatement = connection.prepareStatement(showProcedure);
               ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
               String dropProcedureSQL = "DROP PROCEDURE IF EXISTS " + resultSet.getString(1);
               try (PreparedStatement dropStatement = connection.prepareStatement(dropProcedureSQL)) {
                  dropStatement.executeUpdate();
               }
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }

   }

   private static void executeScripts() {
      try {
         String scriptsPath = "../scripts";
         String[] scripts = readAllFilesInFolder(scriptsPath);
         for (String script : scripts) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(script)) {
               preparedStatement.executeUpdate();
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   private static void migration() throws SQLException, IOException, URISyntaxException {
      dropTableSQL();
      dropProcedureSql();
      executeScripts();
   }

   public static void main(String[] args) {
      try {
         migration();
         System.out.println("Done");
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}
