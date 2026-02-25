package com.rentalhouse.app.services;
import java.util.List;

import com.rentalhouse.app.middlewares.ValidateInput;
import com.rentalhouse.app.models.Auth;
import com.rentalhouse.app.models.Infrastructure;
import com.rentalhouse.app.repositories.InfrastructureRepository;
import com.rentalhouse.utils.Uuid;

public class InfrastructuresService implements IGenericService<Infrastructure> {
   private final InfrastructureRepository _InfrastructuresRepository = new InfrastructureRepository();

   @Override
   public List<Infrastructure> getAll() {
      return _InfrastructuresRepository.getAll();
   }

   @Override
   public Infrastructure getById(String id) {

      return _InfrastructuresRepository.getById(id);

   }

   @Override
   public boolean create(Infrastructure infrastructures) {
      if (ValidateInput.isEmpty(infrastructures.getName()) ||
            ValidateInput.isEmpty(String.valueOf(infrastructures.getPrice())) ||
            ValidateInput.isEmpty(String.valueOf(infrastructures.getElectricity_price())) ||
            ValidateInput.isEmpty(String.valueOf(infrastructures.getWater_price())) ||
            ValidateInput.isEmpty(String.valueOf(infrastructures.getElectricity_number())) ||
            ValidateInput.isEmpty(String.valueOf(infrastructures.getWater_number()))) {
         return false;
      }

      if (!ValidateInput.isNumber(String.valueOf(infrastructures.getPrice().intValue())) ||
            !ValidateInput.isNumber(String.valueOf(infrastructures.getElectricity_price().intValue()))
            || !ValidateInput.isNumber(String.valueOf(infrastructures.getWater_price().intValue()))
            || !ValidateInput.isNumber(String.valueOf(infrastructures.getElectricity_number()))
            || !ValidateInput.isNumber(String.valueOf(infrastructures.getWater_number()))) {
         return false;
      }
      infrastructures.setId(Uuid.get());
      infrastructures.setUser_id(Auth.getUser().getId());

      return _InfrastructuresRepository.create(infrastructures);
   }

   @Override
   public boolean update(Infrastructure infrastructures) {
      if (ValidateInput.isEmpty(infrastructures.getName()) ||
            ValidateInput.isEmpty(String.valueOf(infrastructures.getPrice())) ||
            ValidateInput.isEmpty(String.valueOf(infrastructures.getElectricity_price())) ||
            ValidateInput.isEmpty(String.valueOf(infrastructures.getWater_price())) ||
            ValidateInput.isEmpty(String.valueOf(infrastructures.getElectricity_number())) ||
            ValidateInput.isEmpty(String.valueOf(infrastructures.getWater_number()))) {
         return false;
      }

      if (!ValidateInput.isNumber(String.valueOf(infrastructures.getPrice().intValue())) ||
            !ValidateInput.isNumber(String.valueOf(infrastructures.getElectricity_price().intValue()))
            || !ValidateInput.isNumber(String.valueOf(infrastructures.getWater_price().intValue()))
            || !ValidateInput.isNumber(String.valueOf(infrastructures.getElectricity_number()))
            || !ValidateInput.isNumber(String.valueOf(infrastructures.getWater_number()))) {
         return false;
      }
      return _InfrastructuresRepository.update(infrastructures);

   }

   @Override
   public boolean delete(String id) {
      return _InfrastructuresRepository.delete(id);
   }

   public boolean updateElectricityAndWaterNumbers(String id, Long new_electricity_number, Long new_water_number) {
      return _InfrastructuresRepository.updateElectricityAndWaterNumbers(id, new_electricity_number, new_water_number);
   }

   public boolean updateWaterAndElectricity(String electricNumber, String waterNumber, String infrastructureID) {
      return _InfrastructuresRepository.updateWaterAndElectricity(electricNumber, waterNumber, infrastructureID);
   }

}
