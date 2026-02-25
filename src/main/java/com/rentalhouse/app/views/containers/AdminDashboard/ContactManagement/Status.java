package com.rentalhouse.app.views.containers.AdminDashboard.ContactManagement;

import javafx.util.StringConverter;

public class Status extends StringConverter<Integer> {
   @Override
   public String toString(Integer roleValue) {
      switch (roleValue) {
         case 1:
            return "Completed";
         case 2:
            return "No process";
         case 3:
            return "Processing";
         default:
            return "Unknown";
      }
   }

   @Override
   public Integer fromString(String string) {
      switch (string) {
         case "Completed":
            return 1;
         case "No process":
            return 2;
         case "Processing":
            return 3;
         default:
            return 0;
      }
   }
}
