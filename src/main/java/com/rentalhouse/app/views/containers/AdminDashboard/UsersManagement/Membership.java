package com.rentalhouse.app.views.containers.AdminDashboard.UsersManagement;

import javafx.util.StringConverter;

public class Membership extends StringConverter<Integer> {
   @Override
   public String toString(Integer membershipValue) {
      switch (membershipValue) {
         case 1:
            return "Free trial";
         case 2:
            return "VIP";
         case 3:
            return "Unlimited";
         default:
            return "Unknown";
      }
   }

   @Override
   public Integer fromString(String string) {
      switch (string) {
         case "Free trial":
            return 1;
         case "Vip":
            return 2;
         case "Unlimited":
            return 3;
         default:
            return 0;
      }
   }
}
