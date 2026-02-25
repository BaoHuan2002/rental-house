package com.rentalhouse.app.controllers;

import java.util.List;

import com.rentalhouse.app.models.Contact;
import com.rentalhouse.app.services.ContactService;

public class ContactController {
   private final ContactService _ContactService = new ContactService();

   public List<Contact> GetAll() {
      return _ContactService.getAll();
   }

   public Contact getById(String id) {
      return _ContactService.getById(id);
   }

   public boolean create(Contact entity) {
      return _ContactService.create(entity);
   }

   public boolean update(Contact entity) {
      return false;
   }

   public boolean delete(String id) {
      return _ContactService.delete(id);
   }

   public boolean ChangeStatusContact(String id, int newStatus){
      return _ContactService.ChangeStatusContact(id, newStatus);
   }
}
