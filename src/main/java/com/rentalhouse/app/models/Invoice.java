package com.rentalhouse.app.models;

import java.sql.Date;

public class Invoice {
   private String invoiveID;
   private String infrastructureID;
   private String price;
   private String water_price;
   private String electricity_price;
   private String OldElectricity_number;
   private String NewElectricity_number;
   private String OldWater_number;
   private String NewWater_number;
   private String total_price;
   private Date create_at;
   private int status;

   public Invoice() {
   }

   // CONSTRUCTOR TO GET DATA WITHIN DATABASE:
   public Invoice(String invoiveID, String infrastructureID, String price, String water_price, 
                  String electricity_price, String oldElectricity_number, String newElectricity_number, 
                  String oldWater_number, String newWater_number, String total_price, Date create_at, int status) {
      this.invoiveID = invoiveID;
      this.infrastructureID = infrastructureID;
      this.price = price;
      this.water_price = water_price;
      this.electricity_price = electricity_price;
      this.OldElectricity_number = oldElectricity_number;
      this.NewElectricity_number = newElectricity_number;
      this.OldWater_number = oldWater_number;
      this.NewWater_number = newWater_number;
      this.total_price = total_price;
      this.create_at = create_at;
      this.status = status;
   }

   // CONSTRUCTOR TO CREATE NEW INVOICE:
   public Invoice(String invoiveID, String infrastructureID, String price, String water_price, 
                  String electricity_price, String oldElectricity_number, String newElectricity_number, 
                  String oldWater_number, String newWater_number, String total_price) {
      this.invoiveID = invoiveID;
      this.infrastructureID = infrastructureID;
      this.price = price;
      this.water_price = water_price;
      this.electricity_price = electricity_price;
      this.OldElectricity_number = oldElectricity_number;
      this.NewElectricity_number = newElectricity_number;
      this.OldWater_number = oldWater_number;
      this.NewWater_number = newWater_number;
      this.total_price = total_price;
   }

   public String getInvoiveID() {return invoiveID;}

   public void setInvoiveID(String invoiveID) {this.invoiveID = invoiveID;}

   public String getInfrastructureID() {return infrastructureID;}

   public void setInfrastructureID(String infrastructureID) {this.infrastructureID = infrastructureID;}

   public String getPrice() {return price;}

   public void setPrice(String price) {this.price = price;}

   public String getWater_price() {return water_price;}

   public void setWater_price(String water_price) {this.water_price = water_price;}

   public String getElectricity_price() {return electricity_price;}

   public void setElectricity_price(String electricity_price) {this.electricity_price = electricity_price;}

   public String getOldElectricity_number() {return OldElectricity_number;}

   public void setOldElectricity_number(String oldElectricity_number) {OldElectricity_number = oldElectricity_number;}

   public String getNewElectricity_number() {return NewElectricity_number;}

   public void setNewElectricity_number(String newElectricity_number) {NewElectricity_number = newElectricity_number;}

   public String getOldWater_number() {return OldWater_number;}

   public void setOldWater_number(String oldWater_number) {OldWater_number = oldWater_number;}

   public String getNewWater_number() {return NewWater_number;}

   public void setNewWater_number(String newWater_number) {NewWater_number = newWater_number;}

   public String getTotal_price() {return total_price;}

   public void setTotal_price(String total_price) {this.total_price = total_price;}

   public Date getCreate_at() {return create_at;}

   public void setCreate_at(Date create_at) {this.create_at = create_at;}

   
   public int getStatus() {
      return status;
   }

   public void setStatus(int status) {
      this.status = status;
   }

   @Override
   public String toString() {
      return "Invoice [invoiveID=" + invoiveID + ", infrastructureID=" + infrastructureID + ", price=" + price
            + ", water_price=" + water_price + ", electricity_price=" + electricity_price + ", OldElectricity_number="
            + OldElectricity_number + ", NewElectricity_number=" + NewElectricity_number + ", OldWater_number="
            + OldWater_number + ", NewWater_number=" + NewWater_number + ", total_price=" + total_price + ", create_at="
            + create_at + ", status=" + status + "]";
   }  
}
