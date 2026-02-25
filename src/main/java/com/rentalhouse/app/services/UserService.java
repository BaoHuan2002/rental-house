package com.rentalhouse.app.services;

import java.util.Comparator;
import java.util.List;

import com.rentalhouse.app.middlewares.ValidateInput;
import com.rentalhouse.app.models.User;
import com.rentalhouse.app.repositories.UserRepository;
import com.rentalhouse.utils.Uuid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.mindrot.jbcrypt.BCrypt;

public class UserService implements IGenericService<User> {

   private final UserRepository _UserRepository = new UserRepository();

   @Override
   public List<User> getAll() {
      return _UserRepository.getAll();
   }

   @Override
   public User getById(String id) {
      return _UserRepository.getById(id);
   }

   @Override
   public boolean create(User entity) {
      entity.setId(Uuid.get());
      entity.setPassword(BCrypt.hashpw(entity.getPassword(), BCrypt.gensalt()));
      return _UserRepository.create(entity);
   }

   @Override
   public boolean update(User entity) {
      User u = getById(entity.getId());
      if (ValidateInput.isEmpty(entity.getPassword())) {
         entity.setPassword(u.getPassword());
         return _UserRepository.update(entity);
      }
      entity.setPassword(BCrypt.hashpw(entity.getPassword(), BCrypt.gensalt()));
      return _UserRepository.update(entity);
   }

   @Override
   public boolean delete(String id) {
      return _UserRepository.delete(id);
   }

   public static Comparator<User> sortByCreatedAt() {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      return (User u1, User u2) -> {
         LocalDateTime date1 = LocalDateTime.parse(u1.getCreated_at(), formatter);
         LocalDateTime date2 = LocalDateTime.parse(u2.getCreated_at(), formatter);
         return date1.compareTo(date2);
      };
   }

   public int getQuantityLessor() {
      return _UserRepository.getQuantityLessor();
   }

   public int getQuantityLessorFreeTrial() {
      return _UserRepository.getQuantityLessorFreeTrial();
   }

   public int getQuantityLessorVIP() {
      return _UserRepository.getQuantityLessorVIP();
   }

   public int getQuantityLessorExpired() {
      return _UserRepository.getQuantityLessorExpired();
   }
}
