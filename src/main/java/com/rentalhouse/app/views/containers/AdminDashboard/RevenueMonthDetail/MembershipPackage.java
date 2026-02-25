package com.rentalhouse.app.views.containers.AdminDashboard.RevenueMonthDetail;

import javafx.util.StringConverter;

public class MembershipPackage extends StringConverter<Integer> {
   @Override
   public String toString(Integer membershipPackage) {
      switch (membershipPackage) {
         case 1:
            return "Free";
         case 2:
            return "VIP - 1 Year";
         case 3:
            return "VIP - 3 Years";
         case 4:
            return "VIP - 5 Years";
         default:
            return "Unknown";
      }
   }

   @Override
   public Integer fromString(String string) {
      switch (string) {
         case "Free trial":
            return 1;
         case "One year":
            return 2;
         case "Three years":
            return 3;
         case "Five years":
            return 4;
         default:
            return 0;
      }
   }
}
