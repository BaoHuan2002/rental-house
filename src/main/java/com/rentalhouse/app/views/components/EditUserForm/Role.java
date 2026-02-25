package com.rentalhouse.app.views.components.EditUserForm;

public class Role {
   private final int value;
   private final String displayName;

   public Role(int value, String displayName) {
      this.value = value;
      this.displayName = displayName;
   }

   public int getValue() {
      return value;
   }

   public String getDisplayName() {
      return displayName;
   }

   public static String getRoleByValue(int roleValue) {
      switch (roleValue) {
          case 1:
              return "Admin";
          case 2:
              return "Lessor";
          case 3:
              return "Tenant";
          default:
              return "Choose role";
      }
  }

   @Override
   public String toString() {
      return displayName;
   }
}
