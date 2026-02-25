package com.rentalhouse.app.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

import com.rentalhouse.app.models.Contact;
import com.rentalhouse.app.repositories.ContactRepository;

public class ContactService implements IGenericService<Contact> {
   private final ContactRepository _ContactRepository = new ContactRepository();

   @Override
   public List<Contact> getAll() {
      return _ContactRepository.getAll();
   }

   @Override
   public Contact getById(String id) {
      return _ContactRepository.getById(id);
   }

   @Override
   public boolean create(Contact entity) {
      return _ContactRepository.create(entity);
   }

   @Override
   public boolean update(Contact entity) {
      return false;
   }

   @Override
   public boolean delete(String id) {
      return _ContactRepository.delete(id);
   }

   public static Comparator<Contact> sortByCreatedAt() {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      return (Contact u1, Contact u2) -> {
         LocalDateTime date1 = LocalDateTime.parse(u1.getCreated_at(), formatter);
         LocalDateTime date2 = LocalDateTime.parse(u2.getCreated_at(), formatter);
         return date1.compareTo(date2);
      };
   }

   public boolean ChangeStatusContact(String id, int newStatus){
      return _ContactRepository.ChangeStatusContact(id, newStatus);
   }
}
