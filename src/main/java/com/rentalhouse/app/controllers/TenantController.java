package com.rentalhouse.app.controllers;

import java.util.List;

import com.rentalhouse.app.models.Tenant;
import com.rentalhouse.app.services.TenantService;

public class TenantController {
   private final TenantService _TenantService = new TenantService();

   public boolean create(Tenant tenant) {
      return _TenantService.create(tenant);
   }

   public List<Tenant> getTenants() {
      return _TenantService.getAll();
   }

   public List<Tenant> getTenantsByInfrastructureId(String infrastructureId) {
      return _TenantService.getTenantsByInfrastructureId(infrastructureId);
   }

   public Tenant getTenantById(String id) {
      return _TenantService.getById(id);
   }

   public boolean update(Tenant tenant) {
      return _TenantService.update(tenant);
   }

   public boolean delete(String id) {
      return _TenantService.delete(id);
   }


}
