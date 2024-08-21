package com.Tancem.PIS.controller.plantDepartment;


import com.Tancem.PIS.exceptions.CustomException;
import com.Tancem.PIS.model.plantDepartment.PlantDepartment;
import com.Tancem.PIS.service.plantDepartment.PlantDepartmentService;
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
@RequestMapping("/tancem/pis/plant_department")
public class PlantDepartmentController {

    @Autowired
    private PlantDepartmentService plantDepartmentService;

    @Autowired
    private LogService logService;

    @GetMapping
    public ResponseEntity<Map<String,Object>> getAllPlantDepartments() {
        try {
            List<PlantDepartment> plantDepartments = plantDepartmentService.getAllPlantDepartments();
            logService.logApiCall("Successfully retrieved all plant departments");
            LogUtil.logRetrieveAll(PlantDepartment.class);
            return ResponseUtil.createResponse(HttpStatus.OK, PlantDepartment.class, plantDepartments);
        }catch (CustomException e){
            logService.logError("Failed to retrieving all plant departments");
            LogUtil.logErrorRetrieveAll(PlantDepartment.class, e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, PlantDepartment.class,e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> getPlantDepartmentById(@PathVariable Integer id) {
        try {
            PlantDepartment plantDepartment = plantDepartmentService.getPlantDepartmentById(id);
            logService.logApiCall("Successfully retrieved plant department with id " + id);
            LogUtil.logRetrieveById(PlantDepartment.class, id);
            return ResponseUtil.createResponse(HttpStatus.OK, PlantDepartment.class, plantDepartment);
        }catch (CustomException e){
            logService.logError("Failed to retrieving plant department with id " + id);
            LogUtil.logErrorRetrieveById(PlantDepartment.class, id ,e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, PlantDepartment.class,e);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String,Object>> createPlantDepartment(@RequestBody PlantDepartment plantDepartment) {
        try {
            PlantDepartment newPlantDepartment = plantDepartmentService.savePlantDepartment(plantDepartment);
            plantDepartment.setActive(true);
            plantDepartment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            logService.logApiCall("Successfully created plant department with id " + newPlantDepartment.getId());
            LogUtil.logCreate(PlantDepartment.class, newPlantDepartment);
            return ResponseUtil.createResponse(HttpStatus.CREATED, PlantDepartment.class, newPlantDepartment);
        }catch (CustomException e){
            logService.logError("Failed to create plant department with id " + plantDepartment.getId());
            LogUtil.logErrorCreate(PlantDepartment.class,e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, PlantDepartment.class,e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String,Object>> updatePlantDepartment(
            @PathVariable Integer id,
            @RequestBody PlantDepartment plantDepartment) {
        try {
            PlantDepartment existingPlantDepartment = plantDepartmentService.getPlantDepartmentById(id);
            plantDepartment.setActive(true);
            plantDepartment.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            logService.logApiCall("Successfully updated plant department with id " + id);
            LogUtil.logUpdate(PlantDepartment.class, existingPlantDepartment);
            return ResponseUtil.createResponse(HttpStatus.OK, PlantDepartment.class, existingPlantDepartment);
        }catch (CustomException e){
            logService.logError("Failed to update plant department with id " + id);
            LogUtil.logErrorUpdate(PlantDepartment.class,id,e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, PlantDepartment.class,e);
        }
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<Map<String,Object>> deactivatePlantDepartment(@PathVariable Integer id) {
       try {
           PlantDepartment existingPlantDepartment = plantDepartmentService.getPlantDepartmentById(id);

           if (existingPlantDepartment == null) {
               throw new CustomException("Plant department with id " + id + " not found");
           }
           existingPlantDepartment.setActive(false);
           existingPlantDepartment.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
           logService.logApiCall("Successfully deactivated plant department with id " + id);
           LogUtil.logDelete(PlantDepartment.class, existingPlantDepartment);
           return ResponseUtil.createResponse(HttpStatus.OK, PlantDepartment.class, existingPlantDepartment);
       }catch (CustomException e){
           logService.logError("Failed to deactivate plant department with id " + id);
           LogUtil.logErrorDelete(PlantDepartment.class,id,e.getMessage());
           return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, PlantDepartment.class,e);
       }
    }

}