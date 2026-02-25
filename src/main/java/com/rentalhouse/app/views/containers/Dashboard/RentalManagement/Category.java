package com.rentalhouse.app.views.containers.Dashboard.RentalManagement;

public class Category {
   private int value;
   private String displayName;

   public Category(int value, String displayName) {
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
