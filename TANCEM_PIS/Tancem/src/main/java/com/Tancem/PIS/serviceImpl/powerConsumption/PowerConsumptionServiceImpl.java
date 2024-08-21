package com.Tancem.PIS.serviceImpl.powerConsumption;


import com.Tancem.PIS.exceptions.CustomException;
import com.Tancem.PIS.model.powerConsumption.PowerConsumption;
import com.Tancem.PIS.repository.powerConsumption.PowerConsumptionRepository;
import com.Tancem.PIS.service.powerConsumption.PowerConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PowerConsumptionServiceImpl implements PowerConsumptionService {

    @Autowired
    private PowerConsumptionRepository powerConsumptionRepository;

    @Override
    public List<PowerConsumption> getAllPowerConsumptions() {
        try {
            return powerConsumptionRepository.findAll();
        }catch (Exception e){
            throw new CustomException("Something went wrong");
        }
    }

    @Override
    public PowerConsumption getPowerConsumptionById(Integer id) {
        try {
            Optional<PowerConsumption> powerConsumption = powerConsumptionRepository.findById(id);
            return powerConsumption.orElse(null);
        }catch (Exception e){
            throw new CustomException("Something went wrong");
        }
    }

    @Override
    public PowerConsumption createPowerConsumption(PowerConsumption powerConsumption) {
        try {
            return powerConsumptionRepository.save(powerConsumption);
        }catch (Exception e){
            throw new CustomException("Something went wrong");
        }
    }

    @Override
    public PowerConsumption savePowerConsumption(PowerConsumption powerConsumption) {
        try {
            return powerConsumptionRepository.save(powerConsumption);
        }catch (Exception e){
            throw new CustomException("Something went wrong");
        }
    }

    @Override
    public PowerConsumption deactivatePowerConsumption(Integer id) {
        try {
            Optional<PowerConsumption> existingPowerConsumption = powerConsumptionRepository.findById(id);
                PowerConsumption powerConsumption = existingPowerConsumption.get();
                return powerConsumptionRepository.save(powerConsumption);
            } catch(Exception e){
                throw new CustomException("Error deactivating equipment", e);
            }
    }
}