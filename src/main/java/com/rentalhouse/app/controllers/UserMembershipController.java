package com.rentalhouse.app.controllers;

import java.math.BigDecimal;
import java.util.List;

import com.rentalhouse.app.models.UserMembership;
import com.rentalhouse.app.services.UserMembershipService;

public class UserMembershipController {
   private final UserMembershipService _userMembershipService = new UserMembershipService();

   public List<UserMembership> getAll() {
      return _userMembershipService.getAll();
   }

   public List<UserMembership> getAllByUserId(String id) {
      return _userMembershipService.getAllByUserId(id);
   }

   public List<UserMembership> getAllByMonthAndYear(int month, int year) {
      return _userMembershipService.getAllByMonthAndYear(month, year);
   }

   public boolean create(UserMembership entity) {
      return _userMembershipService.create(entity);
   }

   public BigDecimal getTotalPriceByMonthAndYear(int month, int year) {
      return _userMembershipService.getTotalPriceByMonthAndYear(month, year);
   }

   public List<Integer> getAllYears() {
      return _userMembershipService.getAllYears();
   }

   public int getQuantityPackageByMonthAndYear(int packageNumber, int month, int year) {
      return _userMembershipService.getQuantityPackageByMonthAndYear(packageNumber, month, year);
   }

   public BigDecimal getTotalPriceRevenueByMonthAndYear(int month, int year) {
      return _userMembershipService.getTotalPriceRevenueByMonthAndYear(month, year);
   }

   public static void main(String[] args) {
      UserMembershipController userMembershipController = new UserMembershipController();
      System.out.println(userMembershipController.getQuantityPackageByMonthAndYear(4, 7, 2024));
   }

}
