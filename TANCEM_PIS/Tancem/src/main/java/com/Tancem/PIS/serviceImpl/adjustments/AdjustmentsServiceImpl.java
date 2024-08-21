package com.Tancem.PIS.serviceImpl.adjustments;
import com.Tancem.PIS.exceptions.CustomException;
import com.Tancem.PIS.exceptions.ResourceNotFoundException;
import com.Tancem.PIS.model.adjustments.Adjustments;
import com.Tancem.PIS.model.material.Material;
import com.Tancem.PIS.repository.adjustments.AdjustmentsRepository;
import com.Tancem.PIS.repository.material.MaterialRepository;
import com.Tancem.PIS.service.adjustments.AdjustmentsService;
import com.Tancem.PIS.service.logService.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AdjustmentsServiceImpl implements AdjustmentsService {

    @Autowired
    private AdjustmentsRepository adjustmentsRepository;

    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private LogService logService;

    @Override
    public List<Adjustments> getAllAdjustments() {
        try {
            List<Adjustments> allAdjustments = adjustmentsRepository.findAll();
            List<Adjustments> activeAdjustments = allAdjustments.stream()
                    .filter(Adjustments::isInActive)
                    .collect(Collectors.toList());

            logService.logDbOperation("Fetched all active Adjustments entries. Count: " + activeAdjustments.size());
            return activeAdjustments;
        } catch (Exception e) {
            throw new CustomException("Error fetching all Adjustments: " + e.getMessage());
        }
    }

    @Override
    public Adjustments getAdjustmentById(int id) {
        try {
            Optional<Adjustments> optionalAdjustment = adjustmentsRepository.findById(id);
            if (optionalAdjustment.isPresent()) {
                Adjustments adjustment = optionalAdjustment.get();
                if (!adjustment.isInActive()) {
                    throw new ResourceNotFoundException("Adjustment not available for inactive state");
                }
                logService.logDbOperation("Fetched Adjustment with ID: " + id);
                return adjustment;
            } else {
                throw new ResourceNotFoundException("Adjustment not found with id: " + id);
            }
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Unexpected error fetching Adjustment with ID: " + id);
        }
    }

//    @Override
//    public Adjustments createAdjustment(Adjustments adjustment) {
//        try {
//            Material material = adjustment.getMaterial();
//            Optional<Material> optionalMaterial = materialRepository.findById(material.getId());
//
//            if (optionalMaterial.isPresent()) {
//                Material existingMaterial = optionalMaterial.get();
//
//                if (!existingMaterial.isInActive()) {
//                    throw new CustomException("Cannot create Adjustment for inactive Material with id: " + material.getId());
//                }
//                Adjustments savedAdjustment = adjustmentsRepository.save(adjustment);
//
//                logService.logDbOperation("Saved Adjustment with ID: " + savedAdjustment.getId());
//                return savedAdjustment;
//            } else {
//                throw new CustomException("Material not found for id: " + material.getId());
//            }
//        } catch (CustomException e) {
//            throw new CustomException("Error saving Adjustment.", e);
//        }
//    }
    @Override
    public Adjustments createAdjustment(Adjustments adjustment) {
        try {
            Material material = adjustment.getMaterial();
    
            // No need to check if the material is present or inactive
    
            Adjustments savedAdjustment = adjustmentsRepository.save(adjustment);
            logService.logDbOperation("Saved Adjustment with ID: " + savedAdjustment.getId());
            return savedAdjustment;
        } catch (Exception e) {
            throw new CustomException("Error saving Adjustment.", e);
        }
    }

    @Override
    public Adjustments updateAdjustment(int id, Adjustments adjustment) {
        try {
            Adjustments existingAdjustment = adjustmentsRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Adjustment not found with id: " + id));

            Material material = materialRepository.findById(adjustment.getMaterial().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Material not found with id: " + adjustment.getMaterial().getId()));

            if (!material.isInActive()) {
                throw new ResourceNotFoundException("Cannot update Adjustment for inactive Material with id: " + material.getId());
            }

            // Update fields
            existingAdjustment.setFromPlantName(adjustment.getFromPlantName());
            existingAdjustment.setToPlantName(adjustment.getToPlantName());
            existingAdjustment.setTransactionDate(adjustment.getTransactionDate());
            existingAdjustment.setQuantity(adjustment.getQuantity());
            existingAdjustment.setRemarks(adjustment.getRemarks());
            existingAdjustment.setAdjustmentType(adjustment.getAdjustmentType());
            existingAdjustment.setMaterial(material);

            Adjustments updatedAdjustment = adjustmentsRepository.save(existingAdjustment);

            logService.logDbOperation("Updated Adjustment with ID: " + updatedAdjustment.getId());
            return updatedAdjustment;
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Unexpected error updating Adjustment with ID: " + id);
        }
    }


    @Override
    public Adjustments deactivateAdjustments(int id) {
        try {
            Optional<Adjustments> optionalAdjustment = adjustmentsRepository.findById(id);
            if (optionalAdjustment.isPresent()) {
                Adjustments adjustment = optionalAdjustment.get();
                adjustment.setInActive(false); // Set the 'inActive' field to false
                Adjustments updatedAdjustment = adjustmentsRepository.save(adjustment);

                // Log successful deactivation
                logService.logDbOperation("Deactivated Adjustment with ID: " + updatedAdjustment.getId());
                return updatedAdjustment;
            } else {
                throw new CustomException("Adjustment not found for id: " + id);
            }
        } catch (Exception e) {
            // Handle all exceptions by wrapping them in a CustomException
            throw new CustomException("Unexpected error deactivating Adjustment with ID: " + id, e);
        }

    }

}
