package com.rentalhouse.utils;

import java.util.Random;

public class VerificationCode {
   private static String Code;
   private static long timestamp;

   public static String get() {
      Random random = new Random();
      int code = random.nextInt(900000) + 100000;
      Code = String.valueOf(code);
      timestamp = System.currentTimeMillis();
      return Code;
   }

   public static boolean isValidCode(String inputCode) {
      long currentTime = System.currentTimeMillis();
      return Code != null &&
            inputCode.equals(Code) &&
            currentTime - timestamp < 300000;
   }
}
