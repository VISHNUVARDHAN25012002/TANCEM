package com.Tancem.PIS.controller.subEquipment;

import com.Tancem.PIS.exceptions.CustomException;
import com.Tancem.PIS.model.subEquipment.SubEquipment;
import com.Tancem.PIS.service.subEquipment.SubEquipmentService;
import com.Tancem.PIS.service.logService.LogService;
import com.Tancem.PIS.util.LogUtil;
import com.Tancem.PIS.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tancem/pis/sub_equipment")
public class SubEquipmentController {

    @Autowired
    private SubEquipmentService subEquipmentService;
    @Autowired
    private LogService logService;

    @GetMapping
    public ResponseEntity<Map<String,Object>> getAllSubEquipments() {
        try {
            List<SubEquipment> subEquipments = subEquipmentService.getAllSubEquipments();
            logService.logApiCall("Successfully retrieved all sub equipments");
            LogUtil.logRetrieveAll(SubEquipment.class);
            return ResponseUtil.createResponse(HttpStatus.OK, SubEquipment.class, subEquipments);
        }catch (CustomException e) {
            logService.logError("Error retrieving all sub equipments");
            LogUtil.logErrorRetrieveAll(SubEquipment.class, e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, SubEquipment.class, e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> getSubEquipmentById(@PathVariable Integer id) {
        try {
            SubEquipment subEquipment = subEquipmentService.getSubEquipmentById(id);
            logService.logApiCall("Successfully retrieved sub equipment with id: " + id);
            LogUtil.logRetrieveById(SubEquipment.class, id);
            return ResponseUtil.createResponse(HttpStatus.OK, SubEquipment.class, subEquipment);
        }catch (CustomException e) {
            logService.logError("Error retrieving sub equipment with id: " + id);
            LogUtil.logErrorRetrieveById(SubEquipment.class, id, e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, SubEquipment.class, e);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String,Object>> createSubEquipment(@RequestBody SubEquipment subEquipment) {
        try {
            SubEquipment newSubEquipment = subEquipmentService.saveSubEquipment(subEquipment);
            logService.logApiCall("Successfully created sub equipment");
            LogUtil.logCreate(SubEquipment.class, newSubEquipment);
            return ResponseUtil.createResponse(HttpStatus.CREATED, SubEquipment.class, newSubEquipment);
        }catch (CustomException e) {
            logService.logError("Error creating sub equipment");
            LogUtil.logErrorCreate(SubEquipment.class, e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, SubEquipment.class, e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateSubEquipment(@PathVariable Integer id, @RequestBody SubEquipment subEquipment) {
        try {
            SubEquipment existingSubEquipment = subEquipmentService.getSubEquipmentById(id);
            existingSubEquipment.setActive(true);
            existingSubEquipment.setSubEquipmentDescription(subEquipment.getSubEquipmentDescription());
            existingSubEquipment.setUpdatedAt(new Timestamp(System.currentTimeMillis())); // Set updated_At
            SubEquipment updatedSubEquipment = subEquipmentService.saveSubEquipment(existingSubEquipment);
            logService.logApiCall("Successfully updated sub equipment with id: " + id);
            LogUtil.logUpdate(SubEquipment.class, id);
            return ResponseUtil.createResponse(HttpStatus.OK, SubEquipment.class, updatedSubEquipment);
        }catch (CustomException e) {
            logService.logError("Error updating sub equipment with id: " + id);
            LogUtil.logErrorUpdate(SubEquipment.class, id, e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, SubEquipment.class, e);
        }
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<Map<String, Object>> deactivateSubEquipment(@PathVariable Integer id) {
        try {
            SubEquipment existingSubEquipment = subEquipmentService.getSubEquipmentById(id);
            if (existingSubEquipment == null) {
                throw new CustomException("Sub equipment with id: " + id + " already deactivated");
            }
            existingSubEquipment.setActive(false);
            existingSubEquipment.setUpdatedAt(new Timestamp(System.currentTimeMillis())); // Set updated_At
            SubEquipment deactivatedSubEquipment = subEquipmentService.saveSubEquipment(existingSubEquipment);
            logService.logApiCall("Successfully deactivated sub equipment with id: " + id);
            LogUtil.logDelete(SubEquipment.class, id);
            return ResponseUtil.createResponse(HttpStatus.OK, SubEquipment.class, deactivatedSubEquipment);
        }catch (CustomException e) {
            logService.logError("Error deactivating sub equipment with id: " + id);
            LogUtil.logErrorDelete(SubEquipment.class, id, e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, SubEquipment.class, e);
        }
    }
}