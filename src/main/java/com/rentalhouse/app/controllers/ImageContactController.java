package com.rentalhouse.app.controllers;

import java.util.List;

import com.rentalhouse.app.models.ImageContact;
import com.rentalhouse.app.services.ImageContactService;

public class ImageContactController {
   private final ImageContactService _ImageContactService = new ImageContactService();

   public List<ImageContact> getImageContactByID(String contactID) {
      return _ImageContactService.getImageContactByID(contactID);
   }

   public boolean uploadImageContact(String contactID, String imageBase64) {
      return _ImageContactService.uploadImageContact(contactID, imageBase64);
   }

   public boolean deleteImageContact(String contactID) {
      return _ImageContactService.deleteImageContact(contactID);
   }
}
