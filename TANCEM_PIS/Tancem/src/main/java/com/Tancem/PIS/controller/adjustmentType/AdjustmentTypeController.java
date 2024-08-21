package com.Tancem.PIS.controller.adjustmentType;

import com.Tancem.PIS.exceptions.CustomException;
import com.Tancem.PIS.exceptions.ResourceNotFoundException;
import com.Tancem.PIS.model.adjustmentType.AdjustmentType;
import com.Tancem.PIS.service.adjustmentType.AdjustmentTypeService;
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
@RequestMapping("tancem/pis/AdjustmentType")
public class AdjustmentTypeController {

    @Autowired
    private AdjustmentTypeService adjustmentTypeService;
    @Autowired
    private LogService logService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllAdjustmentTypes() {
        try {
            List<AdjustmentType> adjustmentTypes = adjustmentTypeService.getAllAdjustmentTypes();
            LogUtil.logRetrieveAll(AdjustmentType.class);
            return ResponseUtil.createResponse(HttpStatus.OK, AdjustmentType.class, adjustmentTypes);
        } catch (ResourceNotFoundException e) {
            LogUtil.logErrorRetrieveAll(AdjustmentType.class, e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, AdjustmentType.class, e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getAdjustmentTypeById(@PathVariable int id) {
        try {
            AdjustmentType adjustmentType = adjustmentTypeService.getAdjustmentTypeById(id);
            logService.logApiCall("Successfully retrieved AdjustmentType with ID: " + id);
            LogUtil.logRetrieveById(AdjustmentType.class, id);
            return ResponseUtil.createResponse(HttpStatus.OK, AdjustmentType.class, adjustmentType);
        } catch (CustomException e) {
            logService.logError("Failed to retrieve AdjustmentType with ID: " + id);
            LogUtil.logErrorRetrieveById(AdjustmentType.class, id, e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, AdjustmentType.class, e);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createAdjustmentType(@RequestBody AdjustmentType adjustmentType) {
        try {
            AdjustmentType savedAdjustmentType = adjustmentTypeService.createAdjustmentType(adjustmentType);
            logService.logApiCall("AdjustmentType created successfully with ID: " + savedAdjustmentType.getId());
            LogUtil.logCreate(AdjustmentType.class, savedAdjustmentType.getId());
            return ResponseUtil.createResponse(HttpStatus.CREATED, AdjustmentType.class, savedAdjustmentType);
        } catch (CustomException e) {
            logService.logError("Failed to create AdjustmentType. " + e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.BAD_REQUEST, AdjustmentType.class, e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateAdjustmentType(@PathVariable int id, @RequestBody AdjustmentType adjustmentType) {
        try {
            AdjustmentType updatedAdjustmentType = adjustmentTypeService.updateAdjustmentType(id, adjustmentType);
            logService.logApiCall("AdjustmentType updated successfully with ID: " + id);
            LogUtil.logUpdate(AdjustmentType.class, id);
            return ResponseUtil.createResponse(HttpStatus.OK, AdjustmentType.class, updatedAdjustmentType);
        } catch (CustomException e) {
            logService.logError("Failed to update AdjustmentType with ID: " + id + ". Not found.");
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, AdjustmentType.class, e);
        }
    }



    @PutMapping("/deactivate/{id}")
    public ResponseEntity<String> deactivateAdjustmentType(@PathVariable int id) {
        try {
            adjustmentTypeService.deactivateAdjustmentType(id);
            logService.logApiCall("AdjustmentType deactivated successfully with ID: " + id);
            LogUtil.logUpdate(AdjustmentType.class, id);
            return new ResponseEntity<>("AdjustmentType deactivated successfully", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            logService.logError("Failed to deactivate AdjustmentType with ID: " + id + ". Not found.");
            return new ResponseEntity<>("AdjustmentType not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logService.logError("Unexpected error while deactivating AdjustmentType with ID: " + id);
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
