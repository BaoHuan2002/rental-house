package com.rentalhouse.app.models;

import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User {
   private String id;
   private String name;
   private String email;
   private String phone;
   private String password;
   private String bank_name;
   private String bank_number;
   private String bank_QR;
   private Integer membership_package;
   private String membership_expire_at;
   private Integer role;
   private Integer is_deleted;
   private String created_at;
   private String updated_at;

   public User() {
   }

   public User(String id, String name, String email, String phone, String password,
         String bank_name, String bank_number, String bank_QR, Integer membership_package,
         String membership_expire_at, Integer role, Integer is_deleted, String created_at, String updated_at) {
      this.id = id;
      this.name = name;
      this.email = email;
      this.phone = phone;
      this.password = password;
      this.password = bank_name;
      this.password = bank_number;
      this.password = bank_QR;
      this.membership_package = membership_package;
      this.membership_expire_at = membership_expire_at;
      this.role = role;
      this.is_deleted = is_deleted;
      this.created_at = created_at;
      this.updated_at = updated_at;
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getPhone() {
      return phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public void setBank_name(String bank_name) {
      this.bank_name = bank_name;
   }

   public String getBank_name() {
      return bank_name;
   }

   public void setBank_number(String bank_number) {
      this.bank_number = bank_number;
   }

   public String getBank_number() {
      return bank_number;
   }

   public void setBank_QR(String bank_QR) {
      this.bank_QR = bank_QR;
   }

   public String getBank_QR() {
      return bank_QR;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public Integer getMembership_package() {
      return membership_package;
   }

   public void setMembership_package(Integer membership_package) {
      this.membership_package = membership_package;
   }

   public Integer getRole() {
      return role;
   }

   public void setRole(Integer role) {
      this.role = role;
   }

   public Integer getIs_deleted() {
      return is_deleted;
   }

   public void setIs_deleted(Integer is_deleted) {
      this.is_deleted = is_deleted;
   }

   public String getCreated_at() {
      return created_at;
   }

   public void setCreated_at(String created_at) {
      this.created_at = created_at;
   }

   public String getUpdated_at() {
      return updated_at;
   }

   public void setUpdated_at(String updated_at) {
      this.updated_at = updated_at;
   }

   public String getMembership_expire_at() {
      return membership_expire_at;
   }

   public void setMembership_expire_at(String membership_expire_at) {
      this.membership_expire_at = membership_expire_at;
   }

   public String getExpirationDays() {
      if (created_at == null || membership_expire_at == null) {
          return "";
      }
  
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      LocalDate expireDate = LocalDateTime.parse(membership_expire_at, formatter).toLocalDate();
      LocalDate today = LocalDate.now();
  
      long daysBetween = ChronoUnit.DAYS.between(today, expireDate);
      if(this.getEmail().equals("admin@rentify.com")){
         return "--";
      } else {
         if (daysBetween > 1){
            return daysBetween + " days";
         } else {
            return daysBetween + " day";
         }
      }
  }
  

   @Override
   public String toString() {
      return "User [id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone + ", password=" + password
            + ", bank_name=" + bank_name + ", bank_number=" + bank_number + ", bank_QR=" + bank_QR
            + ", membership_package=" + membership_package + ", membership_expire_at=" + membership_expire_at
            + ", role=" + role + ", is_deleted=" + is_deleted + ", created_at=" + created_at + ", updated_at="
            + updated_at + "]";
   }

}
