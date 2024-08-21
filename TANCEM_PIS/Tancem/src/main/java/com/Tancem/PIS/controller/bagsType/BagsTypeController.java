package com.Tancem.PIS.controller.bagsType;

import com.Tancem.PIS.exceptions.CustomException;
import com.Tancem.PIS.exceptions.ResourceNotFoundException;
import com.Tancem.PIS.model.bagsType.BagsType;
import com.Tancem.PIS.model.material.Material;
import com.Tancem.PIS.service.bagsType.BagsTypeService;
import com.Tancem.PIS.service.logService.LogService;
import com.Tancem.PIS.util.LogUtil;
import com.Tancem.PIS.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("tancem/pis/BagsType")
public class BagsTypeController {
    @Autowired
    private BagsTypeService bagsTypeService;
    @Autowired
    private LogService logService;


    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllBagTypes() {

        try {
            List<BagsType> bagTypes = bagsTypeService.getAllBagTypes();
            LogUtil.logRetrieveAll(BagsType.class);
            return ResponseUtil.createResponse( HttpStatus.OK,BagsType.class,bagTypes);
        }
        catch (ResourceNotFoundException e) {
            LogUtil.logErrorRetrieveAll(Material.class,e.getMessage());
            return ResponseUtil.createErrorResponse( HttpStatus.NOT_FOUND,Material.class,e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getBagTypeById(@PathVariable int id) {
        try {
            BagsType bagsType = bagsTypeService.getBagTypeById(id);
            logService.logApiCall("Successfully retrieved BagType with ID: " + id);
            LogUtil.logRetrieveById(BagsType.class, id); // Log successful retrieval by ID
            return ResponseUtil.createResponse(HttpStatus.OK, BagsType.class, bagsType);
        }catch (CustomException e) {
            logService.logError("Failed to retrieve BagType with ID: " + id);
            LogUtil.logErrorRetrieveById(BagsType.class, id, e.getMessage()); // Log error during retrieval by ID
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, BagsType.class, e);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createBagType(@RequestBody BagsType bagType) {
        try {
            BagsType savedBagType = bagsTypeService.saveBagType(bagType);
            logService.logApiCall("BagType created successfully with ID: " + savedBagType.getId());
            LogUtil.logCreate(BagsType.class, savedBagType.getId());
            return ResponseUtil.createResponse(HttpStatus.CREATED, BagsType.class, savedBagType);
        } catch (CustomException e) {
            logService.logError("Failed to create BagType. " + e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.BAD_REQUEST, BagsType.class, e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateBagType(@PathVariable int id, @RequestBody BagsType bagType) {
        try {
            BagsType updatedBagType = bagsTypeService.updateBagType(id, bagType);
            logService.logApiCall("BagType updated successfully with ID: " + id);
            LogUtil.logUpdate(BagsType.class, id);
            return ResponseUtil.createResponse(HttpStatus.OK, BagsType.class, updatedBagType);
        } catch (CustomException e) {
            logService.logError("Failed to update BagType with ID: " + id + ". Not found.");
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, BagsType.class, e);
        }
    }



    @PutMapping("/deactivate/{id}")
    public ResponseEntity<String> deactivateBagType(@PathVariable int id) {
        try {
            bagsTypeService.deactivateBagType(id);
            logService.logApiCall("BagType deactivated successfully with ID: " + id);
            LogUtil.logUpdate(BagsType.class, id);
            return new ResponseEntity<>("BagType deactivated successfully", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            logService.logError("Failed to deactivate BagType with ID: " + id + ". Not found.");
            return new ResponseEntity<>("BagType not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logService.logError("Unexpected error while deactivating BagType with ID: " + id);
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}


