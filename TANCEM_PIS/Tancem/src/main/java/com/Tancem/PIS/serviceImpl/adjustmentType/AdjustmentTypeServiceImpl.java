package com.Tancem.PIS.serviceImpl.adjustmentType;

import com.Tancem.PIS.exceptions.CustomException;
import com.Tancem.PIS.repository.adjustmentType.AdjustmentTypeRepository;
import com.Tancem.PIS.exceptions.ResourceNotFoundException;
import com.Tancem.PIS.model.adjustmentType.AdjustmentType;
import com.Tancem.PIS.service.adjustmentType.AdjustmentTypeService;
import com.Tancem.PIS.service.logService.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class AdjustmentTypeServiceImpl implements AdjustmentTypeService {


    @Autowired
    private AdjustmentTypeRepository adjustmentTypeRepository;
    @Autowired
    private LogService logService;

    @Override
    public List<AdjustmentType> getAllAdjustmentTypes() {
        try {
            // Fetch all AdjustmentType objects from the database
            List<AdjustmentType> adjustmentTypes = adjustmentTypeRepository.findAll().stream()
                    .filter(AdjustmentType::isInActive) // Only return active adjustment types
                    .collect(Collectors.toList());

            // Log the successful database operation
            logService.logDbOperation("Fetched all adjustment types");

            // Return the list of adjustment types
            return adjustmentTypes;
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    public AdjustmentType getAdjustmentTypeById(int id) {
        try {
            Optional<AdjustmentType> adjustmentType = adjustmentTypeRepository.findById(id);
            if (adjustmentType.isPresent()) {
                logService.logDbOperation("Retrieved AdjustmentType with ID: " + id);
                return adjustmentType.get();
            } else {
                throw new CustomException("Adjustment type with id " + id + " not found");
            }
        } catch (CustomException e) {
            throw new CustomException("Error in getting adjustment type with id " + id);
        }
    }

    @Override
    public AdjustmentType createAdjustmentType(AdjustmentType adjustmentType) {
        try {
            adjustmentType.setCreatedAt(LocalDateTime.now());
            adjustmentType.setUpdatedAt(LocalDateTime.now());

            // Save the AdjustmentType to the repository
            AdjustmentType savedAdjustmentType = adjustmentTypeRepository.save(adjustmentType);

            // Log the database operation
            logService.logDbOperation("Saved adjustment type with ID: " + savedAdjustmentType.getId());

            // Return the saved AdjustmentType
            return savedAdjustmentType;
        } catch (Exception e) {
            // Log the exception and rethrow as a CustomException
            logService.logError("Failed to save adjustment type: " + e.getMessage());
            throw new CustomException(e.getMessage());
        }
    }
    @Override
    public AdjustmentType updateAdjustmentType(int id, AdjustmentType adjustmentType) {
        try {
            // Find the existing AdjustmentType by ID
            AdjustmentType existingAdjustmentType = adjustmentTypeRepository.findById(id)
                    .orElseThrow(() -> new CustomException("Adjustment type not found with id: " + id));

            // Update the fields of the existing AdjustmentType
            existingAdjustmentType.setType(adjustmentType.getType());
            existingAdjustmentType.setUpdatedAt(LocalDateTime.now());

            // Save the updated AdjustmentType
            AdjustmentType updatedAdjustmentType = adjustmentTypeRepository.save(existingAdjustmentType);

            // Log the successful update operation
            logService.logDbOperation("Updated AdjustmentType with ID: " + id);

            return updatedAdjustmentType;
        } catch (CustomException e) {
            throw new CustomException("ID not found: " + id);
        }
    }



    @Override
    public AdjustmentType deactivateAdjustmentType(int id) {
        try {
            // Find the existing AdjustmentType by ID
            AdjustmentType adjustmentType = adjustmentTypeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("AdjustmentType not found with id: " + id));

            // Set the inactive flag to false
            adjustmentType.setInActive(false);

            // Save the deactivated AdjustmentType
            AdjustmentType deactivatedAdjustmentType = adjustmentTypeRepository.save(adjustmentType);

            // Log the successful deactivation
            logService.logDbOperation("Deactivated AdjustmentType with ID: " + id);

            return deactivatedAdjustmentType;
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Unexpected error deactivating AdjustmentType with ID: " + id);
        }
    }
}
