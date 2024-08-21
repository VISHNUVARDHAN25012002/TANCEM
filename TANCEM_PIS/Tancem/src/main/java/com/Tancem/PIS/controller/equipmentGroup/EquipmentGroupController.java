package com.Tancem.PIS.controller.equipmentGroup;

import com.Tancem.PIS.exceptions.CustomException;
import com.Tancem.PIS.model.equipmentGroup.EquipmentGroup;
import com.Tancem.PIS.service.equipmentGroup.EquipmentGroupService;
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
@RequestMapping("/tancem/pis/equipment_group")
public class EquipmentGroupController {

    @Autowired
    private EquipmentGroupService service;

    @Autowired
    private LogService logService;

    @GetMapping
    public ResponseEntity<Map<String,Object>> getAllEquipmentGroups() {
        try {
            List<EquipmentGroup> equipmentGroups = service.findAll();
            logService.logApiCall("Successfully retrieved all equipment groups");
            LogUtil.logRetrieveAll(EquipmentGroup.class); // Log successful retrieval
            return ResponseUtil.createResponse(HttpStatus.OK, EquipmentGroup.class, equipmentGroups);
        } catch (CustomException e) {
            logService.logError("Failed to retrieve all equipment groups");
            LogUtil.logErrorRetrieveAll(EquipmentGroup.class, e.getMessage()); // Log error during retrieval
            return ResponseUtil.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, EquipmentGroup.class, e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> getEquipmentGroupById(@PathVariable Integer id) {
        try {
            EquipmentGroup equipmentGroup = service.findById(id);
            logService.logApiCall("Successfully retrieved equipment group with ID: " + id);
            LogUtil.logRetrieveById(EquipmentGroup.class, id); // Log successful retrieval by ID
            return ResponseUtil.createResponse(HttpStatus.OK, EquipmentGroup.class, equipmentGroup);
        } catch (CustomException e) {
            logService.logError("Failed to retrieve equipment group with ID: " + id);
            LogUtil.logErrorRetrieveById(EquipmentGroup.class, id, e.getMessage()); // Log error during retrieval by ID
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, EquipmentGroup.class, e);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String,Object>> createEquipmentGroup(@RequestBody EquipmentGroup equipmentGroup) {
        try {
            EquipmentGroup newEquipmentGroup = service.save(equipmentGroup);
            logService.logApiCall("Successfully created equipment group");
            LogUtil.logCreate(EquipmentGroup.class, newEquipmentGroup); // Log successful creation
            return ResponseUtil.createResponse(HttpStatus.CREATED, EquipmentGroup.class, newEquipmentGroup);
        } catch (CustomException e) {
            logService.logError("Failed to create equipment group");
            LogUtil.logErrorCreate(EquipmentGroup.class, e.getMessage()); // Log error during creation
            return ResponseUtil.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, EquipmentGroup.class, e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String,Object>> updateEquipmentGroup(@PathVariable Integer id, @RequestBody EquipmentGroup equipmentGroup) {
        try {
            EquipmentGroup existingGroup = service.findById(id);
            existingGroup.setActive(true);
            existingGroup.setEquipmentGroup(equipmentGroup.getEquipmentGroup());
            existingGroup.setUpdatedAt(new Timestamp(System.currentTimeMillis())); // Set updated_At
            EquipmentGroup updatedEquipmentGroup = service.save(existingGroup);
            logService.logApiCall("Successfully updated equipment group with ID: " + id);
            LogUtil.logUpdate(EquipmentGroup.class, id); // Log successful update
            return ResponseUtil.createResponse(HttpStatus.OK, EquipmentGroup.class, updatedEquipmentGroup);
        } catch (CustomException e) {
            logService.logError("Failed to update equipment group with ID: " + id);
            LogUtil.logErrorUpdate(EquipmentGroup.class, id, e.getMessage()); // Log error during update
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, EquipmentGroup.class, e);
        }
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<Map<String,Object>> deactivateEquipmentGroup(@PathVariable Integer id) {
        try {
            EquipmentGroup existingGroup = service.findById(id);
            if (existingGroup == null) {
                throw new CustomException("Equipment group not found");
            }
            existingGroup.setActive(false);
            existingGroup.setUpdatedAt(new Timestamp(System.currentTimeMillis())); // Set updated_At
            EquipmentGroup deactivatedEquipmentGroup = service.save(existingGroup);
            logService.logApiCall("Successfully deactivated equipment group with ID: " + id);
            LogUtil.logDelete(EquipmentGroup.class, id); // Log successful deactivation
            return ResponseUtil.createResponse(HttpStatus.OK, EquipmentGroup.class, deactivatedEquipmentGroup);
        } catch (CustomException e) {
            logService.logError("Failed to deactivate equipment group with ID: " + id);
            LogUtil.logErrorDelete(EquipmentGroup.class, id, e.getMessage()); // Log error during deactivation
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, EquipmentGroup.class, e);
        }
    }
}
