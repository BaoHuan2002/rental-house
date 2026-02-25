package com.rentalhouse.app.views.containers.Dashboard.RentalManagement;

public class StatusInfrastructure {
   private int value;
   private String displayName;

   public StatusInfrastructure(int value, String displayName) {
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
