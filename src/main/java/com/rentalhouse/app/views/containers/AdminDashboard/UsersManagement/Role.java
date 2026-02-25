package com.rentalhouse.app.views.containers.AdminDashboard.UsersManagement;

import javafx.util.StringConverter;

public class Role extends StringConverter<Integer> {
   @Override
   public String toString(Integer roleValue) {
      switch (roleValue) {
         case 1:
            return "Admin";
         case 2:
            return "Lessor";
         case 3:
            return "Tenant";
         default:
            return "Unknown";
      }
   }

   @Override
   public Integer fromString(String string) {
      switch (string) {
         case "Admin":
            return 1;
         case "Lessor":
            return 2;
         case "Tenant":
            return 3;
         default:
            return 0;
      }
   }
}
