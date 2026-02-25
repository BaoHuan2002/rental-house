package com.rentalhouse.app.models;

public class Auth {
   private static User user;

   public static User getUser() {
      return user;
   }

   public static void setUser(User user) {
      Auth.user = user;
   }

   public static void logout() {
      Auth.setUser(null);
   }

}
