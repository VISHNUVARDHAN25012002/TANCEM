package com.Tancem.PIS.serviceImpl.quality;

import com.Tancem.PIS.exceptions.CustomException;
import com.Tancem.PIS.model.quality.Quality;
import com.Tancem.PIS.repository.quality.QualityRepository;
import com.Tancem.PIS.service.quality.QualityService;
import com.Tancem.PIS.service.logService.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QualityServiceImpl implements QualityService {
    @Autowired
    private QualityRepository qualityRepository;

    @Autowired
    private LogService logService;

    @Override
    public Quality createQuality(Quality quality) {
        try {
            // Save the Quality to the repository
            Quality createdQuality = qualityRepository.save(quality);

            // Log the successful creation
            logService.logDbOperation("Created Quality with ID: " + createdQuality.getId());

            return createdQuality;
        } catch (Exception e) {
            throw new CustomException("Error creating Quality.", e);
        }
    }

    @Override
    public Quality getQualityById(Integer id) {
        try {
            Quality quality = qualityRepository.findById(id)
                    .orElseThrow(() -> {
                        String message = "Quality not found with id: " + id;
                        return new CustomException(message);
                    });

            // Log the successful retrieval
            logService.logDbOperation("Retrieved Quality with ID: " + id);
            return quality;
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Error retrieving Quality.", e);
        }
    }

    @Override
    public List<Quality> getAllQualities() {
        try {
            List<Quality> qualities = qualityRepository.findAll();
            logService.logDbOperation("Retrieved all Quality entities, count: " + qualities.size());
            return qualities;
        } catch (Exception e) {
            throw new CustomException("Error retrieving all Qualities.", e);
        }
    }

    @Override
    public Quality updateQuality(Quality quality) {
        try {
            // Check if the Quality exists before updating
            if (!qualityRepository.existsById(quality.getId())) {
                String message = "Quality not found with id: " + quality.getId();
                throw new CustomException(message);
            }

            // Save the updated Quality
            Quality updatedQuality = qualityRepository.save(quality);

            // Log the successful update
            logService.logDbOperation("Updated Quality with ID: " + quality.getId());
            return updatedQuality;
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Error updating Quality.", e);
        }
    }

    @Override
    public Quality deactivateQuality(Integer id) {
        try {
            Quality quality = qualityRepository.findById(id)
                    .orElseThrow(() -> {
                        String message = "Quality not found with id: " + id;
                        return new CustomException(message);
                    });

            // Set the 'inActive' flag to false
            quality.setInActive(false);

            // Save the deactivated Quality
            Quality deactivatedQuality = qualityRepository.save(quality);

            // Log the successful deactivation
            logService.logDbOperation("Deactivated Quality with ID: " + id);
            return deactivatedQuality;
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Error deactivating Quality.", e);
        }
    }
}
