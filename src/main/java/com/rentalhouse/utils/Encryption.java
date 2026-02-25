package com.rentalhouse.utils;

import java.util.Base64;

import com.rentalhouse.configs.DotEnv;

public class Encryption {
   private static final String SECRET_KEY = DotEnv.get("SECRET_KEY");

   public static String encrypt(String input) {
      byte[] inputBytes = input.getBytes();
      byte[] keyBytes = SECRET_KEY.getBytes();
      byte[] encryptedBytes = new byte[inputBytes.length];
      for (int i = 0; i < inputBytes.length; i++) {
         encryptedBytes[i] = (byte) (inputBytes[i] ^ keyBytes[i % keyBytes.length]);
      }
      return Base64.getEncoder().encodeToString(encryptedBytes);
   }

   public static String decrypt(String encryptedInput) {
      byte[] encryptedBytes = Base64.getDecoder().decode(encryptedInput);
      byte[] keyBytes = SECRET_KEY.getBytes();
      byte[] decryptedBytes = new byte[encryptedBytes.length];
      for (int i = 0; i < encryptedBytes.length; i++) {
         decryptedBytes[i] = (byte) (encryptedBytes[i] ^ keyBytes[i % keyBytes.length]);
      }
      return new String(decryptedBytes);
   }

   public static boolean checkEncryption(String input, String encrypt) {
      return encrypt(input).equals(encrypt);
   }

   public static void main(String[] args) {
      String input = "something string data";
      String encrypted = encrypt(input);
      String decrypted = decrypt(encrypted);
      System.out.println("Input: " + input);
      System.out.println("Encrypted: " + encrypted);
      System.out.println("Decrypted: " + decrypted);
      System.out.println("Check input vs encrypted: " + checkEncryption(input, encrypted));
   }
}
