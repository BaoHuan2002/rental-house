package com.rentalhouse.app.models;

import java.math.BigDecimal;

public class UserMembership {

   private int id;

   private String user_id;

   private int membership_package;

   private BigDecimal price;

   private String created_at;

   public UserMembership() {
   }

   public UserMembership(int id, String user_id, int membership_package, BigDecimal price, String created_at) {
      this.id = id;
      this.user_id = user_id;
      this.membership_package = membership_package;
      this.price = price;
      this.created_at = created_at;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getUser_id() {
      return user_id;
   }

   public void setUser_id(String user_id) {
      this.user_id = user_id;
   }

   public int getMembership_package() {
      return membership_package;
   }

   public void setMembership_package(int membership_package) {
      this.membership_package = membership_package;
   }

   public BigDecimal getPrice() {
      return price;
   }

   public void setPrice(BigDecimal price) {
      this.price = price;
   }

   public String getCreated_at() {
      return created_at;
   }

   public void setCreated_at(String created_at) {
      this.created_at = created_at;
   }

   @Override
   public String toString() {
      return "UserMembership [id=" + id + ", user_id=" + user_id + ", membership_package=" + membership_package
            + ", price=" + price + ", created_at=" + created_at + "]";
   }

}
