package com.rentalhouse.app.services;

import java.util.List;

import com.rentalhouse.app.models.ImageContact;
import com.rentalhouse.app.repositories.ImageContactRepository;

public class ImageContactService {
   private final ImageContactRepository _ImageContactRepository = new ImageContactRepository();

   public List<ImageContact> getImageContactByID(String contactID) {
      return _ImageContactRepository.getImageContactByID(contactID);
   }

   public boolean uploadImageContact(String contactID, String imageBase64) {
      return _ImageContactRepository.uploadImageContact(contactID, imageBase64);
   }

   public boolean deleteImageContact(String contactID) {
      return _ImageContactRepository.deleteImageContact(contactID);
   }
}
