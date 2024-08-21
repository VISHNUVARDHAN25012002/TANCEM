package com.Tancem.PIS.controller.material;

import com.Tancem.PIS.exceptions.CustomException;
import com.Tancem.PIS.model.material.Material;
import com.Tancem.PIS.service.logService.LogService;
import com.Tancem.PIS.service.material.MaterialService;
import com.Tancem.PIS.util.LogUtil;
import com.Tancem.PIS.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tancem/pis/materials")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @Autowired
    private LogService logService;

    @GetMapping
    public ResponseEntity<?> getAllMaterials() {
        try {
            List<Material> materials = materialService.getAllMaterials();
            logService.logApiCall("Successfully retrieved all materials");
            LogUtil.logRetrieveAll(Material.class); // Log successful retrieval
            return ResponseUtil.createResponse(HttpStatus.OK, Material.class, materials);
        } catch (CustomException e) {
            logService.logError("Failed to retrieve all materials");
            LogUtil.logErrorRetrieveAll(Material.class, e.getMessage()); // Log error during retrieval
            return ResponseUtil.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, Material.class, e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMaterialById(@PathVariable Integer id) {
        try {
            Material material = materialService.getMaterialById(id);
            logService.logApiCall("Successfully retrieved material with ID: " + id);
            LogUtil.logRetrieveById(Material.class, id); // Log successful retrieval by ID
            return ResponseUtil.createResponse(HttpStatus.OK, Material.class, material);
        } catch (CustomException e) {
            logService.logError("Failed to retrieve material with ID: " + id);
            LogUtil.logErrorRetrieveById(Material.class, id, e.getMessage()); // Log error during retrieval by ID
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, Material.class, e);
        }
    }

    @PostMapping
    public ResponseEntity<?> createMaterial(@RequestBody Material material) {
        try {
            Material savedMaterial = materialService.saveMaterial(material);
            logService.logApiCall("Successfully created material with ID: " + savedMaterial.getId());
            LogUtil.logCreate(Material.class, savedMaterial.getId()); // Log successful clogService.logApiCall("Analysis created successfully with ID: " + savedMaterial.getId());
            return ResponseUtil.createResponse(HttpStatus.CREATED, Material.class, savedMaterial);
        } catch (CustomException e) {
            logService.logError("Failed to create material");
            LogUtil.logErrorCreate(Material.class, e.getMessage()); // Log error during creation
            return ResponseUtil.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, Material.class, e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMaterial(@PathVariable Integer id, @RequestBody Material material) {
        try {
            material.setId(id);
            Material updatedMaterial = materialService.saveMaterial(material);
            logService.logApiCall("Successfully updated material with ID: " + id);
            LogUtil.logUpdate(Material.class, id); // Log successful update
            return ResponseUtil.createResponse(HttpStatus.OK, Material.class, updatedMaterial);
        } catch (CustomException e) {
            logService.logError("Failed to update material with ID: " + id);
            LogUtil.logErrorUpdate(Material.class, id, e.getMessage()); // Log error during update
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, Material.class, e);
        }
    }

}