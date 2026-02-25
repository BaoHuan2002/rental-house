package com.rentalhouse.utils;

import java.util.Random;

public class Uuid {
   private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

   public static String get() {
      return get(16);
   }

   public static String get(int length) {
      StringBuilder uuid = new StringBuilder();
      Random random = new Random();
      for (int i = 0; i < length; i++) {
         uuid.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
         if ((i + 1) % 4 == 0 && i < length - 1) {
            uuid.append('-');
         }
      }
      return uuid.toString().toLowerCase();
   }

   public static void main(String[] args) {
      System.out.println(get());
   }
}
