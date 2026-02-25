package com.rentalhouse.app.models;

import java.math.BigDecimal;

public class Infrastructure {
   private String id;
   private String name;
   private BigDecimal price;
   private int category;
   private String user_id;
   private BigDecimal electricity_price;
   private BigDecimal water_price;
   private Long electricity_number;
   private Long new_electricity_number;
   private Long water_number;
   private Long new_water_number;
   private int tenant_quantity;
   private int status;
   private String rental_at;
   public Infrastructure() {
   }

   public Infrastructure(String id, String name, BigDecimal price, int category, String user_id,
         BigDecimal electricity_price, BigDecimal water_price, Long electricity_number, Long new_electricity_number,
         Long water_number, Long new_water_number, int i, String rental_at) {
      this.id = id;
      this.name = name;
      this.price = price;
      this.category = category;
      this.user_id = user_id;
      this.electricity_price = electricity_price;
      this.water_price = water_price;
      this.electricity_number = electricity_number;
      this.new_electricity_number = new_electricity_number;
      this.water_number = water_number;
      this.new_water_number = new_water_number;
      this.rental_at = rental_at;
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

   public BigDecimal getPrice() {
      return price;
   }

   public void setPrice(BigDecimal price) {
      this.price = price;
   }

   public int getCategory() {
      return category;
   }

   public void setCategory(int category) {
      this.category = category;
   }

   public String getUser_id() {
      return user_id;
   }

   public void setUser_id(String user_id) {
      this.user_id = user_id;
   }

   public BigDecimal getElectricity_price() {
      return electricity_price;
   }

   public void setElectricity_price(BigDecimal electricity_price) {
      this.electricity_price = electricity_price;
   }

   public BigDecimal getWater_price() {
      return water_price;
   }

   public void setWater_price(BigDecimal water_price) {
      this.water_price = water_price;
   }

   public Long getElectricity_number() {
      return electricity_number;
   }

   public void setElectricity_number(Long electricity_number) {
      this.electricity_number = electricity_number;
   }

   public Long getNew_electricity_number() {
      return new_electricity_number;
   }

   public void setNew_electricity_number(Long new_electricity_number) {
      this.new_electricity_number = new_electricity_number;
   }

   public Long getWater_number() {
      return water_number;
   }

   public void setWater_number(Long water_number) {
      this.water_number = water_number;
   }

   public Long getNew_water_number() {
      return new_water_number;
   }

   public void setNew_water_number(Long new_water_number) {
      this.new_water_number = new_water_number;
   }

   public int getStatus() {
      return status;
   }

   public void setStatus(int status) {
      this.status = status;
   }


   public int getTenant_quantity() {
      return tenant_quantity;
   }

   public void setTenant_quantity(int tenant_quantity) {
      this.tenant_quantity = tenant_quantity;
   }

   public String getRental_at() {
      return rental_at;
   }

   public void setRental_at(String rental_at) {
      this.rental_at = rental_at;
   }
   @Override
   public String toString() {
      return "Infrastructure [id=" + id + ", name=" + name + ", price=" + price + ", category=" + category
            + ", user_id=" + user_id + ", electricity_price=" + electricity_price + ", water_price=" + water_price
            + ", electricity_number=" + electricity_number + ", new_electricity_number=" + new_electricity_number
            + ", water_number=" + water_number + ", new_water_number=" + new_water_number + ", tenant_quantity="
            + tenant_quantity + ", status=" + status + "]";
   }

}
