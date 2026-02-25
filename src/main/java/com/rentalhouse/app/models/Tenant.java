package com.rentalhouse.app.models;

public class Tenant {
   private String id;
   private String infrastructure_id;
   private String name;
   private String phone;
   private String address;
   private String id_number;
   private Integer gender;
   private String image_3x4;
   private String image_backside_id_card;
   private String image_front_id_card;
   private Integer status;
   private String created_at;
   private String updated_at;

   public Tenant() {

   }

   public Tenant(String id, String infrastructure_id, String name, String phone, String address, String id_number,
         Integer gender, String image_3x4, String image_backside_id_card, String image_front_id_card, Integer status,
         String created_at, String updated_at) {
      this.id = id;
      this.infrastructure_id = infrastructure_id;
      this.name = name;
      this.phone = phone;
      this.address = address;
      this.id_number = id_number;
      this.gender = gender;
      this.image_3x4 = image_3x4;
      this.image_backside_id_card = image_backside_id_card;
      this.image_front_id_card = image_front_id_card;
      this.status = status;
      this.created_at = created_at;
      this.updated_at = updated_at;
   }

   public String getId() {
      return this.id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getInfrastructure_id() {
      return this.infrastructure_id;
   }

   public void setInfrastructure_id(String infrastructure_id) {
      this.infrastructure_id = infrastructure_id;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getPhone() {
      return this.phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public String getAddress() {
      return this.address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public String getId_number() {
      return this.id_number;
   }

   public void setId_number(String id_number) {
      this.id_number = id_number;
   }

   public Integer getGender() {
      return this.gender;
   }

   public void setGender(Integer gender) {
      this.gender = gender;
   }

   public String getImage_3x4() {
      return this.image_3x4;
   }

   public void setImage_3x4(String image_3x4) {
      this.image_3x4 = image_3x4;
   }

   public String getImage_backside_id_card() {
      return this.image_backside_id_card;
   }

   public void setImage_backside_id_card(String image_backside_id_card) {
      this.image_backside_id_card = image_backside_id_card;
   }

   public String getImage_front_id_card() {
      return this.image_front_id_card;
   }

   public void setImage_front_id_card(String image_front_id_card) {
      this.image_front_id_card = image_front_id_card;
   }

   public Integer getStatus() {
      return this.status;
   }

   public void setStatus(Integer status) {
      this.status = status;
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

   @Override
   public String toString() {
      return "Tenant [id=" + id + ", infrastructure_id=" + infrastructure_id + ", name=" + name + ", phone=" + phone
            + ", address=" + address + ", id_number=" + id_number + ", gender=" + gender + ", image_3x4=" + image_3x4
            + ", image_backside_id_card=" + image_backside_id_card + ", image_front_id_card=" + image_front_id_card
            + ", status=" + status + ", created_at=" + created_at + ", updated_at=" + updated_at + "]";
   }

}