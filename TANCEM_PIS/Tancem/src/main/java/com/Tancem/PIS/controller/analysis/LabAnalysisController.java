//package com.Tancem.PIS.controller.AnalysisController;
package com.Tancem.PIS.controller.analysis;

import com.Tancem.PIS.model.analysis.LabAnalysis;
//import com.Tancem.PIS.service.analysis.LabAnalysisService;
import com.Tancem.PIS.service.analysis.LabAnalysisService;
import com.Tancem.PIS.service.logService.LogService;
import com.Tancem.PIS.serviceImpl.analysis.LabAnalysisServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lab-analysis")
public class LabAnalysisController {
    @Autowired
    private LogService logService;

    @Autowired
    private LabAnalysisService labAnalysisService;

    private static final Logger logger = LoggerFactory.getLogger(LabAnalysisController.class);

    @PostMapping
    public ResponseEntity<Map<String, Object>> createLabAnalysis(@RequestBody LabAnalysis labAnalysis) {
        Map<String, Object> response = new HashMap<>();
        try {
            LabAnalysis savedLabAnalysis = labAnalysisService.saveLabAnalysis(labAnalysis);
            response.put("status", HttpStatus.OK.value());
            response.put("message", "Lab Analysis created successfully");
            response.put("data", savedLabAnalysis);
            logService.logDbOperation("Created Lab Analysis with ID: " + savedLabAnalysis.getId());
            response.put("create", savedLabAnalysis.getId());
        } catch (Exception e) {
            logger.error("Error creating Lab Analysis: {}", e.getMessage());
            logService.logError("Error creating Lab Analysis: " + e.getMessage());
            logService.logException(e);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", "Error creating Lab Analysis");
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<LabAnalysis>> getAllLabAnalyses() {
        List<LabAnalysis> labAnalyses = labAnalysisService.getAllLabAnalyses();
        logService.logDbOperation("Fetched all Lab Analyses");
        return ResponseEntity.ok(labAnalyses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getLabAnalysisById(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        try {
            LabAnalysis labAnalysis = labAnalysisService.getLabAnalysisById(id);
            response.put("status", HttpStatus.OK.value());
            response.put("message", "Lab Analysis retrieved successfully");
            response.put("data", labAnalysis);
            logService.logDbOperation("Fetched Lab Analysis with ID: " + id);
        } catch (Exception e) {
            logger.error("Error retrieving Lab Analysis with id {}: {}", id, e.getMessage());
            logService.logError("Error retrieving Lab Analysis with ID: " + id + ", Error: " + e.getMessage());
            logService.logException(e);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", "Error retrieving Lab Analysis");
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<Map<String, Object>> toggleActive(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        try {
            LabAnalysis labAnalysis = labAnalysisService.getLabAnalysisById(id);
            if (labAnalysis != null) {
                labAnalysis.setActive(!labAnalysis.isActive());
                labAnalysisService.updateLabAnalysis(labAnalysis);
                response.put("status", HttpStatus.OK.value());
                response.put("message", "Lab Analysis active state toggled successfully");
                response.put("data", labAnalysis);
                logService.logDbOperation("Toggled active state for Lab Analysis with ID: " + id);
                response.put("toggle active", id);
            } else {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", "Lab Analysis not found");
            }
        } catch (Exception e) {
            logger.error("Error toggling active state for Lab Analysis with id {}: {}", id, e.getMessage());
            logService.logError("Error toggling active state for Lab Analysis with ID: " + id + ", Error: " + e.getMessage());
            logService.logException(e);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", "Error toggling active state for Lab Analysis: " + e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

//    private void logUserAction(String action, int id) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = (authentication != null) ? authentication.getName() : "Unknown User";
//        String logMessage = String.format("User '%s' performed '%s' on Lab Analysis with id %d", username, action, id);
//        logService.logApiCall(logMessage);
//    }
}
