package com.Tancem.PIS.service.powerConsumption;


import com.Tancem.PIS.model.powerConsumption.PowerConsumption;

import java.util.List;

public interface PowerConsumptionService {
    List<PowerConsumption> getAllPowerConsumptions();
    PowerConsumption getPowerConsumptionById(Integer id);
    PowerConsumption createPowerConsumption(PowerConsumption powerConsumption);
    PowerConsumption savePowerConsumption(PowerConsumption powerConsumption); // Ensure this is present
    PowerConsumption deactivatePowerConsumption(Integer id);
}