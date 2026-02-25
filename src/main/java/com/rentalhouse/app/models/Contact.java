package com.rentalhouse.app.models;

public class Contact {
   private String id;
   private String name;
   private String title;
   private String email;
   private String phone;
   private String description;
   private Integer status;
   private Integer is_deleted;
   private String created_at;
   private String updated_at;

   public Contact() {
   }

   public Contact(String id, String name, String title, String email, String phone, String description,
         Integer status, Integer is_deleted, String created_at, String updated_at) {
      this.id = id;
      this.name = name;
      this.title = title;
      this.email = email;
      this.phone = phone;
      this.description = description;
      this.status = status;
      this.is_deleted = is_deleted;
      this.created_at = created_at;
      this.updated_at = updated_at;
   }

   public void setId(String id) {
      this.id = id;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public void setIs_deleted(Integer is_deleted) {
      this.is_deleted = is_deleted;
   }

   public void setCreated_at(String created_at) {
      this.created_at = created_at;
   }

   public void setUpdated_at(String updated_at) {
      this.updated_at = updated_at;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public void setStatus(Integer status) {
      this.status = status;
   }

   public String getId() {
      return id;
   }

   public String getName() {
      return name;
   }

   public String getTitle() {
      return title;
   }

   public String getEmail() {
      return email;
   }

   public String getPhone() {
      return phone;
   }

   public String getDescription() {
      return description;
   }

   public Integer getStatus() {
      return status;
   }

   public Integer getIs_deleted() {
      return is_deleted;
   }

   public String getCreated_at() {
      return created_at;
   }

   public String getUpdated_at() {
      return updated_at;
   }

   @Override
   public String toString() {
      return "Contact [id=" + id + ", name=" + name + ", title=" + title + ", email=" + email + ", phone=" + phone
            + ", description=" + description + ", status=" + status + ", is_deleted=" + is_deleted + ", created_at="
            + created_at + ", updated_at=" + updated_at + "]";
   }
}
