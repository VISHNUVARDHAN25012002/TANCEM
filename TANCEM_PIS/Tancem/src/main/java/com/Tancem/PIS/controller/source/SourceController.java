package com.Tancem.PIS.controller.source;

import com.Tancem.PIS.exceptions.CustomException;
import com.Tancem.PIS.model.source.Source;
import com.Tancem.PIS.service.logService.LogService;
import com.Tancem.PIS.service.source.SourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tancem/pis/sources")
public class SourceController {
    private static final Logger logger = LoggerFactory.getLogger(SourceController.class);

    @Autowired
    private LogService logService;

    @Autowired
    private SourceService sourceService;

    @GetMapping
    public ResponseEntity<?> getAllSources() {
        try {
            List<Source> sources = sourceService.getAllSources();
            logger.info("Retrieved all sources");
            return new ResponseEntity<>(sources, HttpStatus.OK);
        } catch (CustomException e) {
            logger.error("Error retrieving sources: {}", e.getMessage());
            Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorBody.put("message", e.getMessage());
            return new ResponseEntity<>(errorBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSourceById(@PathVariable Integer id) {
        try {
            Source source = sourceService.getSourceById(id);
            logger.info("Retrieved source with id: {}", id);
            return new ResponseEntity<>(source, HttpStatus.OK);
        } catch (CustomException e) {
            logger.error("Error retrieving source: {}", e.getMessage());
            Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("statusCode", HttpStatus.NOT_FOUND.value());
            errorBody.put("message", e.getMessage());
            return new ResponseEntity<>(errorBody, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createSource(@RequestBody Source source) {
        try {
            Source savedSource = sourceService.saveSource(source);
            logger.info("Created new source");
            return new ResponseEntity<>(savedSource, HttpStatus.CREATED);
        } catch (CustomException e) {
            logger.error("Error creating source: {}", e.getMessage());
            Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorBody.put("message", e.getMessage());
            return new ResponseEntity<>(errorBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSource(@PathVariable Integer id, @RequestBody Source source) {
        try {
            Source existingSource = sourceService.getSourceById(id);
            source.setId(id);
            Source updatedSource = sourceService.saveSource(source);
            logger.info("Updated source with id: {}", id);
            return new ResponseEntity<>(updatedSource, HttpStatus.OK);
        } catch (CustomException e) {
            logger.error("Error updating source: {}", e.getMessage());
            Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("statusCode", HttpStatus.NOT_FOUND.value());
            errorBody.put("message", e.getMessage());
            return new ResponseEntity<>(errorBody, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSource(@PathVariable Integer id) {
        try {
            sourceService.deleteSource(id);
            logger.info("Deleted source with id: " + id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (CustomException e) {
            logger.error("Error deleting source: " + e.getMessage());
            Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorBody.put("message", e.getMessage());
            return new ResponseEntity<>(errorBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
