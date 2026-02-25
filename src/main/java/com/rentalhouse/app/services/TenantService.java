package com.rentalhouse.app.services;

import java.time.LocalDateTime;
import java.util.List;

import com.rentalhouse.app.controllers.InfrastructureController;
import com.rentalhouse.app.middlewares.ValidateInput;
import com.rentalhouse.app.models.Infrastructure;
import com.rentalhouse.app.models.Tenant;
import com.rentalhouse.app.repositories.TenantRepository;

public class TenantService implements IGenericService<Tenant> {

   private final TenantRepository _TenantRepository = new TenantRepository();

   public boolean create(Tenant tenant) {
      if (ValidateInput.isEmpty(tenant.getName())
            || ValidateInput.isEmpty(tenant.getPhone())
            || ValidateInput.isEmpty(tenant.getAddress())
            || ValidateInput.isEmpty(tenant.getId_number())
            || ValidateInput.isEmpty(tenant.getImage_3x4())
            || ValidateInput.isEmpty(tenant.getImage_backside_id_card())
            || ValidateInput.isEmpty(tenant.getImage_front_id_card())) {
         return false;
      }
      if (!ValidateInput.isNumber(tenant.getId_number())
            || !ValidateInput.isValidPhone(tenant.getPhone())) {
         return false;
      }

      if (_TenantRepository.create(tenant)) {
         Infrastructure infrastructure = new InfrastructureController().getById(tenant.getInfrastructure_id());
         if(infrastructure.getTenant_quantity() == 0 ) {
            infrastructure.setRental_at(LocalDateTime.now().toString());
         }
         infrastructure.setTenant_quantity(infrastructure.getTenant_quantity() + 1);
         infrastructure.setStatus(1);
         
         return new InfrastructureController().update(infrastructure);
      }
      return false;
   }

   public List<Tenant> getAll() {
      return _TenantRepository.getAll();
   }

   public List<Tenant> getTenantsByInfrastructureId(String infrastructureId) {
      return _TenantRepository.getTenantsByInfrastructureId(infrastructureId);
   }

   public boolean update(Tenant tenant) {
      if (ValidateInput.isEmpty(tenant.getId()) ||
            ValidateInput.isEmpty(tenant.getName()) ||
            ValidateInput.isEmpty(tenant.getPhone()) ||
            ValidateInput.isEmpty(tenant.getAddress()) ||
            ValidateInput.isEmpty(tenant.getId_number()) ||
            ValidateInput.isEmpty(tenant.getImage_3x4()) ||
            ValidateInput.isEmpty(tenant.getImage_backside_id_card()) ||
            ValidateInput.isEmpty(tenant.getImage_front_id_card())) {
         return false;
      }
      if (!ValidateInput.isNumber(tenant.getId_number()) ||
            !ValidateInput.isValidPhone(tenant.getPhone())) {
         return false;
      }
      return _TenantRepository.update(tenant);
   }

   public Tenant getById(String id) {
      return _TenantRepository.getById(id);
   }

   public boolean delete(String id) {
      Tenant tenant = _TenantRepository.getById(id);
      Infrastructure infrastructure = new InfrastructureController().getById(tenant.getInfrastructure_id());
      infrastructure.setTenant_quantity(infrastructure.getTenant_quantity() - 1);
      if (infrastructure.getTenant_quantity() == 0) {
         infrastructure.setStatus(2);
         infrastructure.setRental_at(null);
      }
      if (_TenantRepository.delete(id)) {
         return new InfrastructureController().update(infrastructure);
      }
      return false;
   }

}