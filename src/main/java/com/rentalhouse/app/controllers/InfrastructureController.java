package com.rentalhouse.app.controllers;

import java.util.List;
import com.rentalhouse.app.models.Infrastructure;
import com.rentalhouse.app.services.InfrastructuresService;

public class InfrastructureController {
   private final InfrastructuresService _InfrastructuresService = new InfrastructuresService();

   public List<Infrastructure> GetInfrastructures() {
      return _InfrastructuresService.getAll();
   }

   public boolean create(Infrastructure infrastructure) {
      return _InfrastructuresService.create(infrastructure);
   }

   public boolean update(Infrastructure updateInfrastructure) {
      return _InfrastructuresService.update(updateInfrastructure);
   }

   public boolean delete(Infrastructure infrastructure) {
      return _InfrastructuresService.delete(infrastructure.getId());
   }

   public Infrastructure getById(String id) {
      return _InfrastructuresService.getById(id);
   }

   public boolean updateElectricityAndWaterNumbers(String id, Long new_electricity_number, Long new_water_number) {
      return _InfrastructuresService.updateElectricityAndWaterNumbers(id, new_electricity_number, new_water_number);
   }

   public boolean updateWaterAndElectricity(String electricNumber, String waterNumber, String infrastructureID) {
      return _InfrastructuresService.updateWaterAndElectricity(electricNumber, waterNumber, infrastructureID);
   }
}
