package com.Tancem.PIS.controller.stoppageDetails;

import com.Tancem.PIS.exceptions.CustomException;
import com.Tancem.PIS.model.stoppageDetails.StoppageDetails;
import com.Tancem.PIS.service.stoppageDetails.StoppageDetailsService;
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
@RequestMapping("/tancem/pis/stoppage_details")
public class StoppageDetailsController {

    @Autowired
    private StoppageDetailsService stoppageDetailsService;
    @Autowired
    private LogService logService;

    @GetMapping
    public ResponseEntity<Map<String,Object>> getAllStoppageDetails() {
        try {
            List<StoppageDetails> stoppageDetailsList = stoppageDetailsService.getAllStoppageDetails();
            logService.logApiCall("Successfully retrieved stoppage details");
            LogUtil.logRetrieveAll(StoppageDetails.class);
            return ResponseUtil.createResponse(HttpStatus.OK, StoppageDetails.class, stoppageDetailsList);
        }catch (CustomException e) {
            logService.logError("Error retrieving stoppage details");
            LogUtil.logErrorRetrieveAll(StoppageDetails.class, e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, StoppageDetails.class, e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> getStoppageDetailsById(@PathVariable Integer id) {
        try {
            StoppageDetails stoppageDetails = stoppageDetailsService.getStoppageDetailsById(id);
            logService.logApiCall("Successfully retrieved stoppage details with id: " + id);
            LogUtil.logRetrieveById(StoppageDetails.class, id);
            return ResponseUtil.createResponse(HttpStatus.OK, StoppageDetails.class, stoppageDetails);
        }catch (CustomException e) {
            logService.logError("Error retrieving stoppage details with id: " + id);
            LogUtil.logErrorRetrieveById(StoppageDetails.class, id, e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, StoppageDetails.class, e);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String,Object>> createStoppageDetails(@RequestBody StoppageDetails stoppageDetails) {
       try {
           StoppageDetails newStoppageDetails = stoppageDetailsService.createStoppageDetails(stoppageDetails);
           logService.logApiCall("Successfully created stoppage details");
           LogUtil.logCreate(StoppageDetails.class, newStoppageDetails);
           return ResponseUtil.createResponse(HttpStatus.CREATED, StoppageDetails.class, newStoppageDetails);
       }catch (CustomException e) {
           logService.logError("Error creating stoppage details");
           LogUtil.logErrorCreate(StoppageDetails.class, e.getMessage());
           return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, StoppageDetails.class, e);
       }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String,Object>> updateStoppageDetails(@PathVariable Integer id, @RequestBody StoppageDetails stoppageDetails) {
        try {
            StoppageDetails existingStoppageDetails = stoppageDetailsService.getStoppageDetailsById(id);
            stoppageDetails.setActive(true);
            stoppageDetails.setId(id); // Ensure the ID is set for update
            StoppageDetails updatedStoppageDetails = stoppageDetailsService.updateStoppageDetails(id, stoppageDetails);
            logService.logApiCall("Successfully updated stoppage details with id: " + id);
            LogUtil.logUpdate(StoppageDetails.class, id);
            return ResponseUtil.createResponse(HttpStatus.OK, StoppageDetails.class, updatedStoppageDetails);
        }catch (CustomException e) {
            logService.logError("Error updating stoppage details with id: " + id);
            LogUtil.logErrorUpdate(StoppageDetails.class, id, e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, StoppageDetails.class, e);
        }
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<Map<String,Object>> deactivateStoppageDetails(@PathVariable Integer id) {
       try {
           StoppageDetails existingStoppageDetails = stoppageDetailsService.getStoppageDetailsById(id);
           if (existingStoppageDetails == null) {
               throw new CustomException("Stoppage details with id: " + id + " does not exist");
           }
           existingStoppageDetails.setActive(false);
           StoppageDetails deactivatedStoppageDetails = stoppageDetailsService.deactivateStoppageDetails(id);
           logService.logApiCall("Successfully deactivated stoppage details with id: " + id);
           LogUtil.logDelete(StoppageDetails.class, id);
           return ResponseUtil.createResponse(HttpStatus.OK, StoppageDetails.class, deactivatedStoppageDetails);
       }catch (CustomException e) {
           logService.logError("Error deactivating stoppage details with id: " + id);
           LogUtil.logErrorDelete(StoppageDetails.class, id, e.getMessage());
           return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, StoppageDetails.class, e);
       }
    }
}