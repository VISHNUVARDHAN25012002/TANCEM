package com.Tancem.PIS.serviceImpl.strength;

import com.Tancem.PIS.exceptions.CustomException;
import com.Tancem.PIS.exceptions.ResourceNotFoundException;
import com.Tancem.PIS.model.strength.Strength;
import com.Tancem.PIS.repository.strength.StrengthRepository;
import com.Tancem.PIS.service.strength.StrengthService;
import com.Tancem.PIS.service.logService.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StrengthServiceImpl implements StrengthService {
    @Autowired
    private StrengthRepository strengthRepository;

    @Autowired
    private LogService logService;

    @Override
    public Strength saveStrength(Strength strength) {
        try {
            Strength savedStrength = strengthRepository.save(strength);
            logService.logDbOperation("Saved strength with ID: " + savedStrength.getId());
            return savedStrength;
        } catch (Exception e) {
            logService.logError("Failed to save strength: " + e.getMessage());
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    public List<Strength> getAllStrengths() {
        try {
            List<Strength> strengths = strengthRepository.findAll();
            logService.logDbOperation("Fetched all strengths");
            return strengths;
        } catch (Exception e) {
            logService.logError("Error fetching all strengths: " + e.getMessage());
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    public Strength getStrengthById(int id) {
        try {
            Optional<Strength> strength = strengthRepository.findById(id);
            if (strength.isPresent()) {
                logService.logDbOperation("Retrieved strength with ID: " + id);
                return strength.get();
            } else {
                throw new CustomException("Strength with id " + id + " not found");
            }
        } catch (CustomException e) {
            logService.logError("Error retrieving strength with ID: " + id + ". " + e.getMessage());
            throw new CustomException("Error in getting strength with id " + id);
        }
    }

    @Override
    public Strength updateStrength(int id, Strength strength) {
        try {
            Strength existingStrength = strengthRepository.findById(id)
                    .orElseThrow(() -> new CustomException("Strength not found with id: " + id));

            existingStrength.setTransactionDate(strength.getTransactionDate());
            existingStrength.setSampleDate(strength.getSampleDate());
            existingStrength.setDay1(strength.getDay1());
            existingStrength.setDay3(strength.getDay3());
            existingStrength.setDay7(strength.getDay7());
            existingStrength.setDay28(strength.getDay28());
            existingStrength.setExpansion(strength.getExpansion());
            existingStrength.setMaterial(strength.getMaterial());
            existingStrength.setCreatedAt(strength.getCreatedAt());
            existingStrength.setUpdatedAt(strength.getUpdatedAt());

            Strength updatedStrength = strengthRepository.save(existingStrength);
            logService.logDbOperation("Updated strength with ID: " + id);

            return updatedStrength;
        } catch (CustomException e) {
            logService.logError("Failed to update strength with ID: " + id + ". " + e.getMessage());
            throw new CustomException("ID not found: " + id);
        }
    }

    @Override
    public Strength deactivateStrength(int id) {
        try {
            // Find the existing Strength by ID
            Strength strength = strengthRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Strength not found with id: " + id));

            // Set the active flag to false (assuming there is a boolean field 'active')
            strength.setInActive(false);

            // Save the deactivated Strength
            Strength deactivatedStrength = strengthRepository.save(strength);

            // Log the successful deactivation
            logService.logDbOperation("Deactivated strength with ID: " + id);

            return deactivatedStrength;
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Unexpected error deactivating strength with ID: " + id);
        }
    }

}
