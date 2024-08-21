package com.Tancem.PIS.controller.materialType;

import com.Tancem.PIS.exceptions.CustomException;
import com.Tancem.PIS.model.materialType.MaterialType;
import com.Tancem.PIS.service.materialType.MaterialTypeService;
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
@RequestMapping("/tancem/pis/materialTypes")
public class MaterialTypeController {

    @Autowired
    private MaterialTypeService materialTypeService;

    @Autowired
    private LogService logService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllMaterialTypes() {
        try {
            List<MaterialType> materialTypes = materialTypeService.getAllMaterialTypes();
            logService.logApiCall("Successfully retrieved all material types");
            LogUtil.logRetrieveAll(MaterialType.class); // Log successful retrieval
            return ResponseUtil.createResponse(HttpStatus.OK, MaterialType.class, materialTypes);
        } catch (CustomException e) {
            logService.logError("Failed to retrieve all material types");
            LogUtil.logErrorRetrieveAll(MaterialType.class, e.getMessage()); // Log error during retrieval
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, MaterialType.class, e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getMaterialTypeById(@PathVariable Integer id) {
        try {
            MaterialType materialType = materialTypeService.getMaterialTypeById(id);
            logService.logApiCall("Successfully retrieved material type with ID: " + id);
            LogUtil.logRetrieveById(MaterialType.class, id); // Log successful retrieval by ID
            return ResponseUtil.createResponse(HttpStatus.OK, MaterialType.class, materialType);
        } catch (CustomException e) {
            logService.logError("Failed to retrieve material type with ID: " + id);
            LogUtil.logErrorRetrieveById(MaterialType.class, id, e.getMessage()); // Log error during retrieval by ID
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, MaterialType.class, e);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createMaterialType(@RequestBody MaterialType materialType) {
        try {
            materialType.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            materialType.setActive(true);
            MaterialType savedMaterialType = materialTypeService.saveMaterialType(materialType);
            logService.logApiCall("Successfully created material type with ID: " + savedMaterialType.getId());
            LogUtil.logCreate(MaterialType.class, savedMaterialType.getId()); // Log successful creation
            return ResponseUtil.createResponse(HttpStatus.CREATED, MaterialType.class, savedMaterialType);
        } catch (CustomException e) {
            logService.logError("Failed to create material type");
            LogUtil.logErrorCreate(MaterialType.class, e.getMessage()); // Log error during creation
            return ResponseUtil.createErrorResponse(HttpStatus.BAD_REQUEST, MaterialType.class, e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateMaterialType(@PathVariable Integer id, @RequestBody MaterialType materialType) {
        try {
            materialType.setId(id);
            materialType.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            materialType.setActive(true);
            MaterialType updatedMaterialType = materialTypeService.saveMaterialType(materialType);
            logService.logApiCall("Successfully updated material type with ID: " + id);
            LogUtil.logUpdate(MaterialType.class, id); // Log successful update
            return ResponseUtil.createResponse(HttpStatus.OK, MaterialType.class, updatedMaterialType);
        } catch (CustomException e) {
            logService.logError("Failed to update material type with ID: " + id);
            LogUtil.logErrorUpdate(MaterialType.class, id, e.getMessage()); // Log error during update
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, MaterialType.class, e);
        }
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<Map<String, Object>> deactivateMaterialType(@PathVariable Integer id) {
        try {
            MaterialType materialType = materialTypeService.getMaterialTypeById(id);
            if (materialType == null) {
                throw new CustomException("Material type not found");
            }
            materialType.setActive(false);
            materialType.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            MaterialType updatedMaterialType = materialTypeService.saveMaterialType(materialType);
            logService.logApiCall("Successfully deactivated material type with ID: " + id);
            LogUtil.logDelete(MaterialType.class, id); // Log successful deactivation
            return ResponseUtil.createResponse(HttpStatus.OK, MaterialType.class, updatedMaterialType);
        } catch (CustomException e) {
            logService.logError("Failed to deactivate material type with ID: " + id);
            LogUtil.logErrorDelete(MaterialType.class, id, e.getMessage()); // Log error during deactivation
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, MaterialType.class, e);
        }
    }
}
