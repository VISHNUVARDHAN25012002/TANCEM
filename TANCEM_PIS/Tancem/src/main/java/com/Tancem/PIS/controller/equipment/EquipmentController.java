package com.Tancem.PIS.controller.equipment;

import com.Tancem.PIS.exceptions.CustomException;
import com.Tancem.PIS.model.equipment.Equipment;
import com.Tancem.PIS.service.equipment.EquipmentService;
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
@RequestMapping("/tancem/pis/equipment")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private LogService logService;

    @GetMapping
    public ResponseEntity<Map<String,Object>> getAllEquipments() {
        try {
            List<Equipment> equipments = equipmentService.getAllEquipments();
            logService.logApiCall("Successfully retrieved all equipments");
            LogUtil.logRetrieveAll(Equipment.class); // Log successful retrieval
            return ResponseUtil.createResponse(HttpStatus.OK, Equipment.class, equipments);
        } catch (CustomException e) {
            logService.logError("Failed to retrieve all equipments");
            LogUtil.logErrorRetrieveAll(Equipment.class, e.getMessage()); // Log error during retrieval
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, Equipment.class, e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> getEquipmentById(@PathVariable Integer id) {
        try {
            Equipment equipment = equipmentService.getEquipmentById(id);
            logService.logApiCall("Successfully retrieved equipment with ID: " + id);
            LogUtil.logRetrieveById(Equipment.class, id); // Log successful retrieval by ID
            return ResponseUtil.createResponse(HttpStatus.OK, Equipment.class, equipment);
        } catch (CustomException e) {
            logService.logError("Failed to retrieve equipment with ID: " + id);
            LogUtil.logErrorRetrieveById(Equipment.class, id, e.getMessage()); // Log error during retrieval by ID
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, Equipment.class, e);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String,Object>> createEquipment(@RequestBody Equipment equipment) {
        try {
            Equipment savedEquipment = equipmentService.saveEquipment(equipment);
            equipment.setActive(true);
            equipment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            logService.logApiCall("Successfully created equipment with ID: " + savedEquipment.getId());
            LogUtil.logCreate(Equipment.class, savedEquipment.getId()); // Log successful creation
            return ResponseUtil.createResponse(HttpStatus.CREATED, Equipment.class, savedEquipment);
        } catch (CustomException e) {
            logService.logError("Failed to create equipment");
            LogUtil.logErrorCreate(Equipment.class, e.getMessage()); // Log error during creation
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, Equipment.class, e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String,Object>> updateEquipment(@PathVariable Integer id, @RequestBody Equipment equipment) {
        try {
            equipment.setId(id);
            Equipment updatedEquipment = equipmentService.saveEquipment(equipment);
            equipment.setActive(true);
            equipment.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            logService.logApiCall("Successfully updated equipment with ID: " + id);
            LogUtil.logUpdate(Equipment.class, id); // Log successful update
            return ResponseUtil.createResponse(HttpStatus.OK, Equipment.class, updatedEquipment);
        } catch (CustomException e) {
            logService.logError("Failed to update equipment with ID: " + id);
            LogUtil.logErrorUpdate(Equipment.class, id, e.getMessage()); // Log error during update
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, Equipment.class, e);
        }
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<Map<String,Object>> deactivateEquipment(@PathVariable Integer id) {
        try {
            Equipment equipment = equipmentService.getEquipmentById(id);
            if (equipment == null) {
                throw new CustomException("Equipment not found");
            }
            equipment.setActive(false);
            equipment.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            Equipment updatedEquipment = equipmentService.saveEquipment(equipment);
            logService.logApiCall("Successfully deactivated equipment with ID: " + id);
            LogUtil.logDelete(Equipment.class, id); // Log successful update
            return ResponseUtil.createResponse(HttpStatus.OK, Equipment.class, updatedEquipment);
        } catch (CustomException e) {
            logService.logError("Failed to deactivate equipment with ID: " + id);
            LogUtil.logErrorDelete(Equipment.class, id, e.getMessage()); // Log error during update
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, Equipment.class, e);
        }
    }


}
