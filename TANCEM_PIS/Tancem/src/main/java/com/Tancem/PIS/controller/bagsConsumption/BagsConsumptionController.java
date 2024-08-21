package com.Tancem.PIS.controller.bagsConsumption;

import com.Tancem.PIS.exceptions.ResourceNotFoundException;

import com.Tancem.PIS.model.bagsConsumption.BagsConsumption;
import com.Tancem.PIS.service.bagsConsumption.BagsConsumptionService;
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
@RequestMapping("tancem/pis/BagsConsumption")
public class BagsConsumptionController {



    @Autowired
    private BagsConsumptionService bagsConsumptionService;
    @Autowired
    private LogService logService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllBags() {
        try {
            List<BagsConsumption> bags = bagsConsumptionService.getAllBags();
            logService.logApiCall("Successfully retrieved all BagsConsumption.");
            LogUtil.logRetrieveAll(BagsConsumption.class);
            return ResponseUtil.createResponse(HttpStatus.OK, BagsConsumption.class, bags);
        } catch (Exception e) {
            logService.logError("Failed to retrieve all BagsConsumption.");
            LogUtil.logErrorRetrieveAll(BagsConsumption.class, e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, BagsConsumption.class, e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getBagById(@PathVariable int id) {
        try {
            BagsConsumption bag = bagsConsumptionService.getBagById(id);
            if (bag == null) {
                throw new ResourceNotFoundException("Bag not found with id: " + id);
            }
            logService.logApiCall("Successfully retrieved BagsConsumption with ID: " + id);
            LogUtil.logRetrieveById(BagsConsumption.class, id);
            return ResponseUtil.createResponse(HttpStatus.OK, BagsConsumption.class, bag);
        } catch (ResourceNotFoundException e) {
            logService.logError("Failed to retrieve BagsConsumption with ID: " + id);
            LogUtil.logErrorRetrieveById(BagsConsumption.class, id, e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, BagsConsumption.class, e);
        } catch (Exception e) {
            logService.logError("Unexpected error while retrieving BagsConsumption with ID: " + id);
            return ResponseUtil.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, BagsConsumption.class, e);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createBag(@RequestBody BagsConsumption bag) {
        try {
            BagsConsumption savedBag = bagsConsumptionService.saveBag(bag);
            logService.logApiCall("BagsConsumption created successfully with ID: " + savedBag.getId());
            LogUtil.logCreate(BagsConsumption.class, savedBag.getId());
            return ResponseUtil.createResponse(HttpStatus.CREATED, BagsConsumption.class, savedBag);
        } catch (ResourceNotFoundException e) {
            logService.logError("Failed to create BagsConsumption. " + e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.BAD_REQUEST, BagsConsumption.class, e);
        } catch (Exception e) {
            logService.logError("Unexpected error while creating BagsConsumption.");
            return ResponseUtil.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, BagsConsumption.class, e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateBag(@PathVariable int id, @RequestBody BagsConsumption bag) {
        try {
            BagsConsumption updatedBag = bagsConsumptionService.updateBag(id, bag);
            logService.logApiCall("BagsConsumption updated successfully with ID: " + id);
            LogUtil.logUpdate(BagsConsumption.class, id);
            return ResponseUtil.createResponse(HttpStatus.OK, BagsConsumption.class, updatedBag);
        } catch (ResourceNotFoundException e) {
            logService.logError("Failed to update BagsConsumption with ID: " + id + ". Not found.");
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, BagsConsumption.class, e);
        } catch (Exception e) {
            logService.logError("Unexpected error while updating BagsConsumption with ID: " + id);
            return ResponseUtil.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, BagsConsumption.class, e);
        }
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<String> deactivateBagConsumption(@PathVariable int id) {
        try {
            bagsConsumptionService.deactivateBagConsumption(id);
            logService.logApiCall("BagsConsumption deactivated successfully with ID: " + id);
            LogUtil.logUpdate(BagsConsumption.class, id);
            return new ResponseEntity<>("BagConsumption deactivated successfully", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            logService.logError("Failed to deactivate BagsConsumption with ID: " + id + ". Not found.");
            return new ResponseEntity<>("BagConsumption not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logService.logError("Unexpected error while deactivating BagsConsumption with ID: " + id);
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}