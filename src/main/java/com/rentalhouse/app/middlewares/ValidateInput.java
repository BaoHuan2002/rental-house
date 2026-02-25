package com.rentalhouse.app.middlewares;

public class ValidateInput {
   public static boolean isEmpty(String input) {
      return input.length() == 0;
   }

   public static boolean isValidSelector(int selector) {
      return selector != 0;
   }

   public static boolean isValidEmail(String email) {
      return email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
   }

   public static boolean isValidPhone(String phone) {
      return phone.matches("^\\+?[0-9]\\d{1,14}$");
   }

   public static boolean isValidPassword(String password) {
      return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
   }

   public static boolean isValidPasswordConfirmation(String password, String passwordConfirmation) {
      return password.equals(passwordConfirmation);
   }

   public static boolean isNumber(String input) {
      return input.matches("-?\\d+");
   }

   public static boolean isPositiveNumber(String input) {
      return input.matches("\\d+");
   }

   public static void main(String[] args) {
   }

}