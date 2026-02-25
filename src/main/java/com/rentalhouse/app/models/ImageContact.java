package com.rentalhouse.app.models;

public class ImageContact {
   private String id;
   private String images;
   private String contact_id;

   public ImageContact() {
   }

   public ImageContact(String id, String images, String contact_id) {
      this.id = id;
      this.images = images;
      this.contact_id = contact_id;
   }

   public String getId() {
      return id;
   }

   public String getImages() {
      return images;
   }

   public String getContact_id() {
      return contact_id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public void setImages(String images) {
      this.images = images;
   }

   public void setContact_id(String contact_id) {
      this.contact_id = contact_id;
   }
}
