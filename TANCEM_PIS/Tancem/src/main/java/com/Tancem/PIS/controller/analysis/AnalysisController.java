
package com.Tancem.PIS.controller.analysis;

//import com.Tancem.PIS.model.AnalysisModel.Analysis;
//import com.Tancem.PIS.service.AnalysisService.AnalysisService;


//import com.Tancem.PIS.Model.AnalysisModel.Analysis;
//import com.Tancem.PIS.Service.AnalysisService.AnalysisService;
//import com.Tancem.PIS.model.analysis.Analysis;
//import com.Tancem.PIS.service.analysis.AnalysisService;
import com.Tancem.PIS.model.analysis.Analysis;
import com.Tancem.PIS.service.analysis.AnalysisService;
import com.Tancem.PIS.service.logService.LogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
//import java.util.List;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analysis")
public class AnalysisController {

    @Autowired
    private LogService logService;

    @Autowired
    private AnalysisService analysisService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createAnalysis(@RequestBody Analysis analysis) {
        Analysis savedAnalysis = analysisService.saveAnalysis(analysis);

        Map<String, Object> response = new LinkedHashMap<>(); // Using LinkedHashMap to maintain order
        response.put("message", "Analysis created successfully");
        response.put("status", HttpStatus.OK.value());
        response.put("Code", HttpStatus.OK.name());
        response.put("Data", savedAnalysis); // Directly place saved analysis under 'Data'

        logService.logApiCall("Analysis created successfully with ID: " + savedAnalysis.getId());
        return ResponseEntity.ok(response);
    }




    @GetMapping
    public ResponseEntity<List<Analysis>> getAllAnalyses() {
        List<Analysis> analyses = analysisService.getAllAnalyses();
        logService.logApiCall("Fetched all analyses");
        return ResponseEntity.ok(analyses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Analysis> getAnalysisById(@PathVariable int id) {
        Analysis analysis = analysisService.getAnalysisById(id);
        logService.logApiCall("Fetched analysis with ID: " + id);
        return ResponseEntity.ok(analysis);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateAnalysis(@PathVariable int id, @RequestBody Analysis analysis) {
        Map<String, Object> response = new LinkedHashMap<>();  // Use LinkedHashMap to maintain order of insertion
        try {
            analysis.setId(id);
            Analysis updatedAnalysis = analysisService.updateAnalysis(analysis);
            response.put("status", HttpStatus.OK.value());
            response.put("message", "Analysis updated successfully");

            response.put("data", updatedAnalysis);  // Directly place the updated analysis under 'data'

            logService.logApiCall("Updated analysis with ID: " + id);
            response.put("update", id);
        } catch (Exception e) {
            logService.logError("Error updating analysis with ID: " + id + ", Error: " + e.getMessage());
            logService.logException(e);

            response.clear();  // Clear any successful data if an exception occurs
            response.put("message", "Error updating analysis");
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return ResponseEntity.ok(response);
    }


    @PutMapping("/deactivate/{id}")
    public ResponseEntity<Map<String, Object>> deactivateAnalysis(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        try {
            logService.logApiCall("Deactivating analysis with ID: " + id);
            analysisService.deactivateAnalysis(id);
            logService.logApiCall("Successfully deactivated analysis with ID: " + id);

            response.put("status", HttpStatus.OK.value());
            response.put("message", "Analysis deactivated successfully");
            response.put("deactivate", id);
        } catch (Exception e) {
            logService.logError("Error deactivating analysis with ID: " + id + ", Error: " + e.getMessage());
            logService.logException(e);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", "Error deactivating analysis: " + e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

//    private void logUserAction(String action, int id) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = (authentication != null) ? authentication.getName() : "Unknown User";
//        String logMessage = String.format("User '%s' performed '%s' on analysis with id %d", username, action, id);
//        logService.logApiCall(logMessage);
//    }
}
