package com.rentalhouse.app.views.containers.AdminDashboard.ContactManagement;

public class StatusFillter {
   private final int value;
   private final String displayName;

   public StatusFillter(int value, String displayName) {
      this.value = value;
      this.displayName = displayName;
   }

   public int getValue() {
      return value;
   }

   public String getDisplayName() {
      return displayName;
   }

   @Override
   public String toString() {
      return displayName;
   }
}
