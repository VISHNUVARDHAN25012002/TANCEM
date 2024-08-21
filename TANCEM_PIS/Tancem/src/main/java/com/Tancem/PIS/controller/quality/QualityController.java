package com.Tancem.PIS.controller.quality;

import com.Tancem.PIS.exceptions.CustomException;
import com.Tancem.PIS.exceptions.ResourceNotFoundException;
import com.Tancem.PIS.model.quality.Quality;
import com.Tancem.PIS.service.quality.QualityService;
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
@RequestMapping("tancem/pis/Quality")
public class QualityController {
    @Autowired
    private QualityService qualityService;

    @Autowired
    private LogService logService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllQualities() {
        try {
            List<Quality> qualities = qualityService.getAllQualities();
            logService.logApiCall("Retrieved all Quality entities, count: " + qualities.size());
            LogUtil.logRetrieveAll(Quality.class);
            return ResponseUtil.createResponse(HttpStatus.OK, Quality.class, qualities);
        } catch (Exception e) {
            logService.logError("Failed to retrieve all Qualities: " + e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, Quality.class, e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getQualityById(@PathVariable Integer id) {
        try {
            Quality quality = qualityService.getQualityById(id);
            logService.logApiCall("Successfully retrieved Quality with ID: " + id);
            LogUtil.logRetrieveById(Quality.class, id);
            return ResponseUtil.createResponse(HttpStatus.OK, Quality.class, quality);
        } catch (CustomException e) {
            logService.logError("Failed to retrieve Quality with ID: " + id + ". " + e.getMessage());
            LogUtil.logErrorRetrieveById(Quality.class, id, e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, Quality.class, e);
        } catch (Exception e) {
            logService.logError("Unexpected error while retrieving Quality with ID: " + id);
            return ResponseUtil.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, Quality.class, e);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createQuality(@RequestBody Quality quality) {
        try {
            Quality createdQuality = qualityService.createQuality(quality);
            logService.logApiCall("Quality created successfully with ID: " + createdQuality.getId());
            LogUtil.logCreate(Quality.class, createdQuality.getId());
            return ResponseUtil.createResponse(HttpStatus.CREATED, Quality.class, createdQuality);
        } catch (CustomException e) {
            logService.logError("Failed to create Quality: " + e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.BAD_REQUEST, Quality.class, e);
        } catch (Exception e) {
            logService.logError("Unexpected error while creating Quality");
            return ResponseUtil.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, Quality.class, e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateQuality(@PathVariable Integer id, @RequestBody Quality quality) {
        try {
            Quality existingQuality = qualityService.getQualityById(id);
            if (existingQuality == null) {
                throw new ResourceNotFoundException("Quality not found with id: " + id);
            }

            quality.setId(id);
            Quality updatedQuality = qualityService.updateQuality(quality);

            logService.logApiCall("Quality updated successfully with ID: " + id);
            LogUtil.logUpdate(Quality.class, id);
            return ResponseUtil.createResponse(HttpStatus.OK, Quality.class, updatedQuality);
        } catch (ResourceNotFoundException e) {
            logService.logError("Failed to update Quality with ID: " + id + ". Not found.");
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, Quality.class, e);
        } catch (Exception e) {
            logService.logError("Unexpected error while updating Quality with ID: " + id);
            return ResponseUtil.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, Quality.class, e);
        }
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<String> deactivateQuality(@PathVariable Integer id) {
        try {
            qualityService.deactivateQuality(id);
            logService.logApiCall("Quality deactivated successfully with ID: " + id);
            LogUtil.logUpdate(Quality.class, id);
            return new ResponseEntity<>("Quality deactivated successfully", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            logService.logError("Failed to deactivate Quality with ID: " + id + ". Not found.");
            return new ResponseEntity<>("Quality not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logService.logError("Unexpected error while deactivating Quality with ID: " + id);
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
