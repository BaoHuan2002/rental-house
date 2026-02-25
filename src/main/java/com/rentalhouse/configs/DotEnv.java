package com.rentalhouse.configs;

import io.github.cdimascio.dotenv.Dotenv;

public class DotEnv {
   private static Dotenv dotenv = Dotenv.load();

   public static String get(String key) {
      return dotenv.get(key);
   }
}


