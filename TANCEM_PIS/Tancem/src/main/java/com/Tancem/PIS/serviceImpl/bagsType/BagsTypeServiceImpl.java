package com.Tancem.PIS.serviceImpl.bagsType;

import com.Tancem.PIS.exceptions.CustomException;
import com.Tancem.PIS.exceptions.ResourceNotFoundException;
import com.Tancem.PIS.model.bagsType.BagsType;
import com.Tancem.PIS.repository.bagsType.BagsTypeRepository;
import com.Tancem.PIS.service.bagsType.BagsTypeService;
import com.Tancem.PIS.service.logService.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BagsTypeServiceImpl implements BagsTypeService {


    @Autowired
    private BagsTypeRepository bagsTypeRepository;
    @Autowired
    private LogService logService;

    @Override
    public List<BagsType> getAllBagTypes() {

        try {
            // Fetch all BagsType objects from the database
            List<BagsType> bagTypes = bagsTypeRepository.findAll().stream()
                    .filter(BagsType::isInActive) // Only return active bag types
                    .collect(Collectors.toList());

            // Log the successful database operation
            logService.logDbOperation("Fetched all bag types");

            // Return the list of bag types
            return bagTypes;
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }


    @Override
    public BagsType getBagTypeById(int id) {
        try {
            Optional<BagsType> bagsType = bagsTypeRepository.findById(id);
            if (bagsType.isPresent()) {
                logService.logDbOperation("Retrieved BagsType with ID: " + id);
                return bagsType.get();
            } else {
                throw new CustomException("Bag type with id " + id + " not found");
            }
        } catch (CustomException e) {
            throw new CustomException("Error in getting bag type with id " + id);
        }


    }


//    @Override
//    public BagsType saveBagType(BagsType bagType) {
//        try {
//            return bagsTypeRepository.save(bagType);
//        } catch (Exception e) {
//            throw new CustomException(e.getMessage());
//        }
//    }
@Override
public BagsType saveBagType(BagsType bagType) {
    try {
        // Save the BagsType to the repository
        BagsType savedBagType = bagsTypeRepository.save(bagType);

        // Log the database operation
        logService.logDbOperation("Saved bag type with ID: " + savedBagType.getId());

        // Return the saved BagsType
        return savedBagType;
    } catch (Exception e) {
        // Log the exception and rethrow as a CustomException
        logService.logError("Failed to save bag type: " + e.getMessage());
        throw new CustomException(e.getMessage());
    }
}


    @Override
    public BagsType updateBagType(int id, BagsType bagType) {
        try {
            BagsType existingBagType = bagsTypeRepository.findById(id)
                    .orElseThrow(() -> new CustomException("Bag type not found with id: " + id));

            existingBagType.setTypes_Of_Bags(bagType.getTypes_Of_Bags());
            existingBagType.setCreatedAt(bagType.getCreatedAt());
            existingBagType.setUpdatedAt(bagType.getUpdatedAt());

            // Save the updated BagsType
            BagsType updatedBagType = bagsTypeRepository.save(existingBagType);

            // Log the successful update operation
            logService.logDbOperation("Updated BagsType with ID: " + id);

            return updatedBagType;
        } catch (CustomException e) {
            throw new CustomException("ID not found." + id);
        }
    }

    @Override
    public BagsType deactivateBagType(int id) {
        try {
            BagsType bagsType = bagsTypeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Bag type not found with id: " + id));

            bagsType.setInActive(false);
            bagsTypeRepository.save(bagsType);
            logService.logDbOperation("Deactivated BagsType with ID: " + id);

            return bagsType;
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Unexpected error deactivating BagType with ID: " + id);
        }

    }


}
