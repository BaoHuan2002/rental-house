package com.rentalhouse.app.views.containers.AdminDashboard.UsersManagement;

public class MembershipFilter {
   private final int value;
   private final String displayName;

   public MembershipFilter(int value, String displayName) {
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
