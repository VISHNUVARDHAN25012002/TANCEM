package com.Tancem.PIS.controller.strength;

import com.Tancem.PIS.exceptions.CustomException;
import com.Tancem.PIS.exceptions.ResourceNotFoundException;
import com.Tancem.PIS.model.strength.Strength;
import com.Tancem.PIS.service.strength.StrengthService;
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
@RequestMapping("tancem/pis/Strength")
public class StrengthController {
    @Autowired
    private StrengthService strengthService;

    @Autowired
    private LogService logService;


    @PostMapping
    public ResponseEntity<Map<String, Object>> createStrength(@RequestBody Strength strength) {
        try {
            // Save the Strength
            Strength createdStrength = strengthService.saveStrength(strength);

            // Log the operation
            logService.logApiCall("Strength created successfully with ID: " + createdStrength.getId());
            LogUtil.logCreate(Strength.class, createdStrength.getId());

            // Create a response
            return ResponseUtil.createResponse(HttpStatus.CREATED, Strength.class, createdStrength);
        } catch (CustomException e) {
            logService.logError("Failed to create Strength: " + e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.BAD_REQUEST, Strength.class, e);
        }
    }


    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllStrengths() {
        try {
            // Fetch all Strengths
            List<Strength> strengths = strengthService.getAllStrengths();

            // Log the operation
            LogUtil.logRetrieveAll(Strength.class);

            // Create a response
            return ResponseUtil.createResponse(HttpStatus.OK, Strength.class, strengths);
        } catch (Exception e) {
            logService.logError("Failed to retrieve Strengths: " + e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, Strength.class, e);
        }
    }

    // Get Strength by ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getStrengthById(@PathVariable int id) {
        try {
            // Retrieve the Strength by ID
            Strength strength = strengthService.getStrengthById(id);

            // Log the operation
            logService.logApiCall("Successfully retrieved Strength with ID: " + id);
            LogUtil.logRetrieveById(Strength.class, id);

            // Create a response
            return ResponseUtil.createResponse(HttpStatus.OK, Strength.class, strength);
        } catch (CustomException e) {
            logService.logError("Failed to retrieve Strength with ID: " + id);
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, Strength.class, e);
        }
    }

    // Update Strength
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateStrength(@PathVariable int id, @RequestBody Strength strength) {
        try {
            // Update the Strength
            Strength updatedStrength = strengthService.updateStrength(id, strength);

            // Log the operation
            logService.logApiCall("Strength updated successfully with ID: " + id);
            LogUtil.logUpdate(Strength.class, id);

            // Create a response
            return ResponseUtil.createResponse(HttpStatus.OK, Strength.class, updatedStrength);
        } catch (CustomException e) {
            logService.logError("Failed to update Strength with ID: " + id + ". Not found.");
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, Strength.class, e);
        }
    }



    @PutMapping("/deactivate/{id}")
    public ResponseEntity<String> deactivateStrength(@PathVariable int id) {
        try {
            // Deactivate the Strength
            strengthService.deactivateStrength(id);

            // Log the operation
            logService.logApiCall("Strength deactivated successfully with ID: " + id);
            LogUtil.logUpdate(Strength.class, id);

            // Create a response
            return new ResponseEntity<>("Strength deactivated successfully", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            // Log error if the Strength was not found
            logService.logError("Failed to deactivate Strength with ID: " + id + ". Not found.");
            return new ResponseEntity<>("Strength not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Log unexpected errors
            logService.logError("Unexpected error while deactivating Strength with ID: " + id);
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
