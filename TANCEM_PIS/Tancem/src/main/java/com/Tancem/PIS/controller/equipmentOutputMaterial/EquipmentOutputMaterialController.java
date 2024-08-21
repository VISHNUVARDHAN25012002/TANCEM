package com.Tancem.PIS.controller.equipmentOutputMaterial;

import com.Tancem.PIS.exceptions.CustomException;
import com.Tancem.PIS.model.equipmentOutputMaterial.EquipmentOutputMaterial;
import com.Tancem.PIS.service.equipmentOutputMaterial.EquipmentOutputMaterialService;
import com.Tancem.PIS.service.logService.LogService;
import com.Tancem.PIS.util.LogUtil;
import com.Tancem.PIS.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tancem/pis/equipment_output_materials")
public class EquipmentOutputMaterialController {

    private static final Logger log = LoggerFactory.getLogger(EquipmentOutputMaterialController.class);
    @Autowired
    private EquipmentOutputMaterialService equipmentOutputMaterialService;

    @Autowired
    private LogService logService;

    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> getEquipmentOutputMaterialById(@PathVariable int id) {
        try {
            EquipmentOutputMaterial material = equipmentOutputMaterialService.getEquipmentOutputMaterialById(id);
            logService.logApiCall("Fetching equipment group with ID: {}" + id);
            LogUtil.logRetrieveById(EquipmentOutputMaterial.class, id);
            return ResponseUtil.createResponse(HttpStatus.OK, EquipmentOutputMaterial.class, material);
        } catch (CustomException e) {
            logService.logError("Failed to retrieve equipment output material with ID: " + id);
            LogUtil.logErrorRetrieveById(EquipmentOutputMaterial.class, id, e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, EquipmentOutputMaterial.class, e);
        }
    }

    @GetMapping
    public ResponseEntity<Map<String,Object>> getAllEquipmentOutputMaterials() {
        try {
            List<EquipmentOutputMaterial> materials = equipmentOutputMaterialService.getAllEquipmentOutputMaterials();
            logService.logApiCall("Successfully retrieved all equipment output materials");
            LogUtil.logRetrieveAll(EquipmentOutputMaterial.class);
            return ResponseUtil.createResponse(HttpStatus.OK, EquipmentOutputMaterial.class, materials);
        } catch (CustomException e) {
            logService.logError("Failed to retrieve all equipment output materials");
            LogUtil.logErrorRetrieveAll(EquipmentOutputMaterial.class, e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, EquipmentOutputMaterial.class, e);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String,Object>> createEquipmentOutputMaterial(@RequestBody EquipmentOutputMaterial equipmentOutputMaterial) {
        try {
            EquipmentOutputMaterial createdMaterial = equipmentOutputMaterialService.createEquipmentOutputMaterial(equipmentOutputMaterial);
            equipmentOutputMaterial.setActive(true);
            equipmentOutputMaterial.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            logService.logApiCall("created equipment output material with ID: " + createdMaterial.getId());
            LogUtil.logCreate(EquipmentOutputMaterialController.class, createdMaterial.getId()); // Log successful creation
            return ResponseUtil.createResponse(HttpStatus.CREATED, EquipmentOutputMaterial.class, createdMaterial);
        } catch (CustomException e) {
            logService.logError("Failed to create Equipment Output Material");
            LogUtil.logErrorCreate(EquipmentOutputMaterial.class, e.getMessage()); // Log error during creation
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, EquipmentOutputMaterial.class, e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String,Object>> updateEquipmentOutputMaterial(
            @PathVariable Integer id,
            @RequestBody EquipmentOutputMaterial equipmentOutputMaterial) {
        try {
            EquipmentOutputMaterial existingMaterial = equipmentOutputMaterialService.getEquipmentOutputMaterialById(id);
            equipmentOutputMaterial.setActive(true);
            equipmentOutputMaterial.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            logService.logApiCall("updated equipment output material with ID: " + id);
            LogUtil.logUpdate(EquipmentOutputMaterial.class, existingMaterial);
            return ResponseUtil.createResponse(HttpStatus.OK, EquipmentOutputMaterial.class, existingMaterial);
        } catch (CustomException e) {
            logService.logError("Failed to update equipment output material with ID: " + id);
            LogUtil.logErrorUpdate(EquipmentOutputMaterial.class, id, e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, EquipmentOutputMaterial.class, e);
        }
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<Map<String,Object>> deactivateEquipmentOutputMaterial(@PathVariable Integer id) {
        try {
            EquipmentOutputMaterial existingMaterial = equipmentOutputMaterialService.getEquipmentOutputMaterialById(id);
            if (existingMaterial == null) {
                throw new CustomException("Equipment output material not found");
            } else {
                existingMaterial.setActive(false);
                existingMaterial.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            }
            logService.logApiCall("Successfully deactivated equipment output material with ID: " + id);
            LogUtil.logDelete(EquipmentOutputMaterial.class, existingMaterial);
            return ResponseUtil.createResponse(HttpStatus.OK, EquipmentOutputMaterial.class, existingMaterial);
        } catch (CustomException e) {
            logService.logError("Failed to deactivate equipment output material with ID: " + id);
            LogUtil.logErrorDelete(EquipmentOutputMaterial.class, id, e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, EquipmentOutputMaterial.class, e);
        }
    }
}
