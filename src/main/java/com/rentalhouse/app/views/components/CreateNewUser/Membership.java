package com.rentalhouse.app.views.components.CreateNewUser;

public class Membership {
   private final int value;
   private final String displayName;

   public Membership(int value, String displayName) {
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
