package com.Tancem.PIS.serviceImpl.stoppageDetails;

import com.Tancem.PIS.exceptions.CustomException;
import com.Tancem.PIS.model.stoppageDetails.StoppageDetails;
import com.Tancem.PIS.repository.stoppageDetails.StoppageDetailsRepository;
import com.Tancem.PIS.service.stoppageDetails.StoppageDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoppageDetailsServiceImpl implements StoppageDetailsService {

    @Autowired
    private StoppageDetailsRepository stoppageDetailsRepository;

    @Override
    public List<StoppageDetails> getAllStoppageDetails() {
        try {
            List<StoppageDetails> stoppageDetailsList = stoppageDetailsRepository.findAll();
            return stoppageDetailsList;
        }catch (Exception e){
            throw new CustomException("Something went wrong");
        }
    }

    @Override
    public StoppageDetails getStoppageDetailsById(Integer id) {
        try{
        Optional<StoppageDetails> stoppageDetails = stoppageDetailsRepository.findById(id);
            return stoppageDetails.get();
        } catch (Exception e){
            throw new CustomException("Something went wrong");
        }
    }

    @Override
    public StoppageDetails createStoppageDetails(StoppageDetails stoppageDetails) {
        try {
            StoppageDetails newStoppageDetails = stoppageDetailsRepository.save(stoppageDetails);
            return newStoppageDetails;
        }catch (Exception e){
            throw new CustomException("Something went wrong");
        }
    }

    @Override
    public StoppageDetails updateStoppageDetails(Integer id, StoppageDetails stoppageDetails) {
        try {
            StoppageDetails existingStoppageDetails = stoppageDetailsRepository.findById(id)
                    .orElseThrow(() -> new CustomException("Stoppage details with id " + id + " does not exist"));

            // Update the fields that are allowed to be modified
            existingStoppageDetails.setTransactionDate(stoppageDetails.getTransactionDate());
            existingStoppageDetails.setStoppageHours(stoppageDetails.getStoppageHours());
            existingStoppageDetails.setTotalHours(stoppageDetails.getTotalHours());
            existingStoppageDetails.setHours(stoppageDetails.getHours());
            existingStoppageDetails.setNoOfStoppages(stoppageDetails.getNoOfStoppages());
            existingStoppageDetails.setRemarks(stoppageDetails.getRemarks());
            existingStoppageDetails.setActive(true); // Reactivate the entity on update

            // Save the updated entity
            return stoppageDetailsRepository.save(existingStoppageDetails);
        } catch (Exception e) {
            throw new CustomException("Something went wrong while updating stoppage details", e);
        }
    }

    @Override
    public StoppageDetails deactivateStoppageDetails(Integer id) {
        try {
            StoppageDetails existingStoppageDetails = stoppageDetailsRepository.findById(id)
                    .orElseThrow(() -> new CustomException("Stoppage details with id " + id + " does not exist"));

            // Deactivate the entity
            existingStoppageDetails.setActive(false);

            // Save the deactivated entity
            return stoppageDetailsRepository.save(existingStoppageDetails);
        } catch (Exception e) {
            throw new CustomException("Something went wrong while deactivating stoppage details", e);
        }
    }
}