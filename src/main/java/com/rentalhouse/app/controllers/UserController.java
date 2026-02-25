package com.rentalhouse.app.controllers;

import java.util.List;

import com.rentalhouse.app.models.User;
import com.rentalhouse.app.services.UserService;

public class UserController {
   private final UserService _userService = new UserService();

   public List<User> GetUsers() {
      return _userService.getAll();
   }

   public User getById(String id) {
      return _userService.getById(id);
   }

   public boolean create(User entity) {
      return _userService.create(entity);
   }

   public boolean update(User entity) {
      return _userService.update(entity);
   }

   public boolean delete(String id) {
      return _userService.delete(id);
   }

   public int getQuantityLessor() {
      return _userService.getQuantityLessor();
   }

   public int getQuantityLessorFreeTrial() {
      return _userService.getQuantityLessorFreeTrial();
   }

   public int getQuantityLessorVIP() {
      return _userService.getQuantityLessorVIP();
   }

   public int getQuantityLessorExpired() {
      return _userService.getQuantityLessorExpired();
   }
}
