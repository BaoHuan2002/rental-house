package com.rentalhouse.app.services;

import java.math.BigDecimal;

import java.util.List;

import com.rentalhouse.app.models.UserMembership;
import com.rentalhouse.app.repositories.UserMembershipRepository;

public class UserMembershipService implements IGenericService<UserMembership> {
   private final UserMembershipRepository _userMembershipRepository = new UserMembershipRepository();

   @Override
   public List<UserMembership> getAll() {
      return _userMembershipRepository.getAll();
   }

   @Override
   public UserMembership getById(String id) {
      return null;
   }

   public List<UserMembership> getAllByUserId(String id) {
      return _userMembershipRepository.getAllByUserId(id);
   }

   public List<UserMembership> getAllByMonthAndYear(int month, int year) {
      return _userMembershipRepository.getAllByMonthAndYear(month, year);
   }

   @Override
   public boolean create(UserMembership entity) {
      return _userMembershipRepository.create(entity);
   }

   @Override
   public boolean update(UserMembership entity) {
      return false;
   }

   @Override
   public boolean delete(String id) {
      return false;
   }

   public BigDecimal getTotalPriceByMonthAndYear(int month, int year) {
      return _userMembershipRepository.getTotalPriceByMonthAndYear(month, year);
   }

   public List<Integer> getAllYears() {
      return _userMembershipRepository.getAllYears();
   }

   public int getQuantityPackageByMonthAndYear(int packageNumber, int month, int year) {
      return _userMembershipRepository.getQuantityPackageByMonthAndYear(packageNumber, month, year);
   }

   public BigDecimal getTotalPriceRevenueByMonthAndYear(int month, int year) {
      return _userMembershipRepository.getTotalPriceRevenueByMonthAndYear(month, year);
   }

}
