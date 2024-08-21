package com.Tancem.PIS.controller.powerConsumption;

import com.Tancem.PIS.exceptions.CustomException;
import com.Tancem.PIS.model.powerConsumption.PowerConsumption;
import com.Tancem.PIS.service.powerConsumption.PowerConsumptionService;
import com.Tancem.PIS.service.logService.LogService;
import com.Tancem.PIS.util.LogUtil;
import com.Tancem.PIS.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tancem/pis/power_consumption")
public class PowerConsumptionController {


    @Autowired
    private PowerConsumptionService powerConsumptionService;
    @Autowired
    private LogService logService;

    @GetMapping
    public ResponseEntity<Map<String,Object>> getAllPowerConsumptions() {
        try {
            List<PowerConsumption> powerConsumptions = powerConsumptionService.getAllPowerConsumptions();
            logService.logApiCall("Successfully retrieved all power consumptions");
            LogUtil.logRetrieveAll(PowerConsumption.class);
            return ResponseUtil.createResponse(HttpStatus.OK, PowerConsumption.class, powerConsumptions);
        } catch (CustomException e) {
            logService.logError("Error retrieving all power consumptions");
            LogUtil.logErrorRetrieveAll(PowerConsumption.class, e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, PowerConsumption.class, e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> getPowerConsumptionById(@PathVariable Integer id) {
        try {
            PowerConsumption powerConsumption = powerConsumptionService.getPowerConsumptionById(id);
            logService.logApiCall("Successfully retrieved power consumption by id");
            LogUtil.logRetrieveById(PowerConsumption.class, powerConsumption);
            return ResponseUtil.createResponse(HttpStatus.OK, PowerConsumption.class, powerConsumption);
        } catch (CustomException e) {
            logService.logError("Error retrieving power consumption by id");
            LogUtil.logErrorRetrieveById(PowerConsumption.class, id, e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, PowerConsumption.class, e);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String,Object>> createPowerConsumption(@RequestBody PowerConsumption powerConsumption) {
        try {
            PowerConsumption newPowerConsumption = powerConsumptionService.createPowerConsumption(powerConsumption);
            logService.logApiCall("Successfully created power consumption");
            LogUtil.logCreate(PowerConsumption.class, newPowerConsumption);
            return ResponseUtil.createResponse(HttpStatus.CREATED, PowerConsumption.class, newPowerConsumption);
        } catch (CustomException e) {
            logService.logError("Error creating power consumption");
            LogUtil.logErrorCreate(PowerConsumption.class, e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, PowerConsumption.class, e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String,Object>> updatePowerConsumption(@PathVariable Integer id, @RequestBody PowerConsumption powerConsumption) {
        try {
            PowerConsumption existingPowerConsumption = powerConsumptionService.getPowerConsumptionById(id);
            existingPowerConsumption.setAction(true);
            existingPowerConsumption.setUnits(powerConsumption.getUnits());
            existingPowerConsumption.setTransactionDate(powerConsumption.getTransactionDate());
            PowerConsumption updatedPowerConsumption = powerConsumptionService.savePowerConsumption(existingPowerConsumption);
            logService.logApiCall("Successfully updated power consumption");
            LogUtil.logUpdate(PowerConsumption.class, updatedPowerConsumption);
            return ResponseUtil.createResponse(HttpStatus.OK, PowerConsumption.class, updatedPowerConsumption);
        } catch (CustomException e) {
            logService.logError("Error updating power consumption");
            LogUtil.logErrorUpdate(PowerConsumption.class, id, e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, PowerConsumption.class, e);
        }
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<Map<String,Object>> deactivatePowerConsumption(@PathVariable Integer id) {
        try {
            PowerConsumption existingPowerConsumption = powerConsumptionService.getPowerConsumptionById(id);
            if (existingPowerConsumption == null) {
                throw new CustomException("Power consumption already deactivated with id: " + id);
            }
            existingPowerConsumption.setAction(false);
            PowerConsumption deactivatedPowerConsumption = powerConsumptionService.savePowerConsumption(existingPowerConsumption);
            logService.logApiCall("Successfully deactivated power consumption with id: " + id);
            LogUtil.logDelete(PowerConsumption.class, id);
            return ResponseUtil.createResponse(HttpStatus.OK, PowerConsumption.class, deactivatedPowerConsumption);
        } catch (CustomException e) {
             logService.logError("Error deactivating power consumption with id: " + id);
             LogUtil.logErrorDelete(PowerConsumption.class, id, e.getMessage());
             return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, PowerConsumption.class, e);
        }
    }
}
