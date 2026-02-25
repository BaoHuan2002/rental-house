package com.rentalhouse.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateTimeLocal {
   private static DateTimeFormatter formatter;
   public static LocalDateTime now = LocalDateTime.now();

   public static String getDateNow() {
      formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
      return formatter.print(now);
   }

   public static String getTimeNow() {
      formatter = DateTimeFormat.forPattern("HH:mm");
      return formatter.print(now);
   }

   public static String getDateTimeNow() {
      formatter = DateTimeFormat.forPattern("HH:mm dd/MM/yyyy");
      return formatter.print(now);
   }

   public static boolean isDateGreaterThanNow(String date) {
      LocalDateTime givenDate = DateTimeFormat.forPattern("dd/MM/yyyy").parseLocalDateTime(date);
      System.out.println(givenDate);
      return givenDate.isAfter(now);
   }

   public static boolean isDateLessThanNow(String date) {
      LocalDateTime givenDate = DateTimeFormat.forPattern("dd/MM/yyyy").parseLocalDateTime(date);
      return givenDate.isBefore(now);
   }

   public static boolean isDateEqualToNow(String date) {
      formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
      String timeNow = formatter.print(now);
      return timeNow.equals(date);
   }

   public static boolean isDateGreaterOrEqualToNow(String date) {
      LocalDateTime givenDate = DateTimeFormat.forPattern("dd/MM/yyyy").parseLocalDateTime(date);
      return givenDate.isAfter(now) || isDateEqualToNow(date);
   }

   public static boolean isDateLessOrEqualToNow(String date) {
      LocalDateTime givenDate = DateTimeFormat.forPattern("dd/MM/yyyy").parseLocalDateTime(date);
      return givenDate.isBefore(now) || isDateEqualToNow(date);
   }
   public static String getRandomDate() {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      long startMillis = 1672531200000L; 
      long endMillis = 2235660800000L; 
      long randomMillis = ThreadLocalRandom.current().nextLong(startMillis, endMillis);
      Date randomDate = new Date(randomMillis);
      return sdf.format(randomDate);
   }
   
   public static void main(String[] args) {
      System.out.println(now);
   }
}
