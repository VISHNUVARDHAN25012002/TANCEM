package com.Tancem.PIS.controller.production;


import com.Tancem.PIS.exceptions.CustomException;
import com.Tancem.PIS.model.production.Production;
import com.Tancem.PIS.service.production.ProductionService;
import com.Tancem.PIS.service.logService.LogService;
import com.Tancem.PIS.util.LogUtil;
import com.Tancem.PIS.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tancem/pis/productions")
public class ProductionController {

    private static final Logger log = LoggerFactory.getLogger(ProductionController.class);
    @Autowired
    private ProductionService productionService;
    @Autowired
    private LogService logService;

    @GetMapping
    public ResponseEntity<Map<String,Object>> getAllProductions() {
        try {
            List<Production> productions = productionService.getAllProductions();
            logService.logApiCall("Successfully retrieved all productions");
            LogUtil.logRetrieveAll(ProductionController.class);
            return ResponseUtil.createResponse(HttpStatus.OK, ProductionController.class, productions);
        }catch (CustomException e) {
            logService.logError("Error retrieving all productions");
            LogUtil.logErrorRetrieveAll(Production.class, e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, ProductionController.class, e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> getProductionById(@PathVariable Integer id) {
        try {
            Production production = productionService.getProductionById(id);
            logService.logApiCall("Successfully retrieved production with id " + id);
            LogUtil.logRetrieveById(ProductionController.class, id);
            return ResponseUtil.createResponse(HttpStatus.OK, ProductionController.class, production);
        }catch (CustomException e) {
            logService.logError("Error retrieving production with id " + id);
            LogUtil.logErrorRetrieveById(Production.class, id, e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, ProductionController.class, e);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String,Object>> createProduction(@RequestBody Production production) {
        try {
            Production newProduction = productionService.saveProduction(production);
            logService.logApiCall("Successfully created production");
            LogUtil.logCreate(ProductionController.class, newProduction);
            return ResponseUtil.createResponse(HttpStatus.CREATED, ProductionController.class, newProduction);
        }catch (CustomException e) {
            logService.logError("Error creating production");
            LogUtil.logErrorCreate(Production.class, e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, ProductionController.class, e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateProduction(@PathVariable Integer id, @RequestBody Production production) {
        try {
            Production existingProduction = productionService.getProductionById(id);
            existingProduction.setAction(true);
            existingProduction.setOutput(production.getOutput());
            existingProduction.setRunningHours(production.getRunningHours());
            existingProduction.setFuelConsumption(production.getFuelConsumption());
            existingProduction.setRemarks(production.getRemarks());
            Production updatedProduction = productionService.updateProduction(id, existingProduction);
            logService.logApiCall("Successfully updated production with id " + id);
            LogUtil.logUpdate(Production.class, id);
            return ResponseUtil.createResponse(HttpStatus.OK, ProductionController.class, updatedProduction);
        }catch (CustomException e) {
            logService.logError("Error updating production with id " + id);
            LogUtil.logErrorUpdate(Production.class, id, e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, ProductionController.class, e);
        }
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<Map<String, Object>> deactivateProduction(@PathVariable Integer id) {
        try {
            Production existingProduction = productionService.getProductionById(id);
            if (existingProduction == null) {
                throw new CustomException("Production with id " + id + " does not exist");
            }
            existingProduction.setAction(false);
            Production deactivatedProduction = productionService.deactivateProduction(id);
            logService.logApiCall("Successfully deactivated production with id " + id);
            LogUtil.logDelete(ProductionController.class, id);
            return ResponseUtil.createResponse(HttpStatus.OK, ProductionController.class, deactivatedProduction);
        }catch (CustomException e) {
            logService.logError("Error deactivating production with id " + id);
            LogUtil.logDelete(Production.class, id);
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, ProductionController.class, e);
        }
    }
}