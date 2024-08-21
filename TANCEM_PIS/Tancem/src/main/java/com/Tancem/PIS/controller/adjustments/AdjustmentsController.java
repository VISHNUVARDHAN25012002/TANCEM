package com.Tancem.PIS.controller.adjustments;

import com.Tancem.PIS.exceptions.ResourceNotFoundException;
import com.Tancem.PIS.model.adjustments.Adjustments;
import com.Tancem.PIS.repository.material.MaterialRepository;
import com.Tancem.PIS.service.adjustments.AdjustmentsService;
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
@RequestMapping("tancem/pis/Adjustments")
public class AdjustmentsController {

    @Autowired
    private AdjustmentsService adjustmentsService;

    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private LogService logService;


    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllAdjustments() {
        try {
            List<Adjustments> adjustments = adjustmentsService.getAllAdjustments();
            logService.logApiCall("Successfully retrieved all Adjustments.");
            LogUtil.logRetrieveAll(Adjustments.class);
            return ResponseUtil.createResponse(HttpStatus.OK, Adjustments.class, adjustments);
        } catch (Exception e) {
            logService.logError("Failed to retrieve all Adjustments.");
            LogUtil.logErrorRetrieveAll(Adjustments.class, e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, Adjustments.class, e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getAdjustmentById(@PathVariable int id) {
        try {
            Adjustments adjustment = adjustmentsService.getAdjustmentById(id);
            if (adjustment == null) {
                throw new ResourceNotFoundException("Adjustment not found with id: " + id);
            }
            logService.logApiCall("Successfully retrieved Adjustment with ID: " + id);
            LogUtil.logRetrieveById(Adjustments.class, id);
            return ResponseUtil.createResponse(HttpStatus.OK, Adjustments.class, adjustment);
        } catch (ResourceNotFoundException e) {
            logService.logError("Failed to retrieve Adjustment with ID: " + id);
            LogUtil.logErrorRetrieveById(Adjustments.class, id, e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, Adjustments.class, e);
        } catch (Exception e) {
            logService.logError("Unexpected error while retrieving Adjustment with ID: " + id);
            return ResponseUtil.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, Adjustments.class, e);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createAdjustment(@RequestBody Adjustments adjustment) {
        try {
            Adjustments savedAdjustment = adjustmentsService.createAdjustment(adjustment);
            logService.logApiCall("Adjustment created successfully with ID: " + savedAdjustment.getId());
            LogUtil.logCreate(Adjustments.class, savedAdjustment.getId());
            return ResponseUtil.createResponse(HttpStatus.CREATED, Adjustments.class, savedAdjustment);
        } catch (ResourceNotFoundException e) {
            logService.logError("Failed to create Adjustment. " + e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.BAD_REQUEST, Adjustments.class, e);
        } catch (Exception e) {
            logService.logError("Unexpected error while creating Adjustment.");
            return ResponseUtil.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, Adjustments.class, e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateAdjustment(@PathVariable int id, @RequestBody Adjustments adjustment) {
        try {
            Adjustments updatedAdjustment = adjustmentsService.updateAdjustment(id, adjustment);
            logService.logApiCall("Adjustment updated successfully with ID: " + id);
            LogUtil.logUpdate(Adjustments.class, id);
            return ResponseUtil.createResponse(HttpStatus.OK, Adjustments.class, updatedAdjustment);
        } catch (ResourceNotFoundException e) {
            logService.logError("Failed to update Adjustment with ID: " + id + ". Not found.");
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, Adjustments.class, e);
        } catch (Exception e) {
            logService.logError("Unexpected error while updating Adjustment with ID: " + id);
            return ResponseUtil.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, Adjustments.class, e);
        }
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<Map<String, Object>> deactivateAdjustment(@PathVariable int id) {
        try {
            Adjustments deactivatedAdjustment = adjustmentsService.deactivateAdjustments(id);
            logService.logApiCall("Adjustment deactivated successfully with ID: " + id);
            LogUtil.logUpdate(Adjustments.class, id);
            return ResponseUtil.createResponse(HttpStatus.OK, Adjustments.class, deactivatedAdjustment);
        } catch (ResourceNotFoundException e) {
            logService.logError("Failed to deactivate Adjustment with ID: " + id + ". Not found.");
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, Adjustments.class, e);
        } catch (Exception e) {
            logService.logError("Unexpected error while deactivating Adjustment with ID: " + id);
            return ResponseUtil.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, Adjustments.class, e);
        }
    }


}
