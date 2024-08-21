package com.Tancem.PIS.serviceImpl.bagsConsumption;

import com.Tancem.PIS.exceptions.CustomException;
import com.Tancem.PIS.exceptions.ResourceNotFoundException;
import com.Tancem.PIS.model.bagsConsumption.BagsConsumption;
import com.Tancem.PIS.model.bagsType.BagsType;
import com.Tancem.PIS.repository.bagsConsumption.BagsConsumptionRepository;
import com.Tancem.PIS.repository.bagsType.BagsTypeRepository;
import com.Tancem.PIS.service.bagsConsumption.BagsConsumptionService;
import com.Tancem.PIS.service.logService.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BagsConsumptionServiceImpl implements BagsConsumptionService {

    @Autowired
    private BagsConsumptionRepository bagsConsumptionRepository;

    @Autowired
    private BagsTypeRepository bagsTypeRepository;

    @Autowired
    private LogService logService;

    @Override
    public List<BagsConsumption> getAllBags() {
        try {
            List<BagsConsumption> allConsumptions = bagsConsumptionRepository.findAll();
            List<BagsConsumption> filteredConsumptions = allConsumptions.stream()
                    .filter(consumption -> consumption.getBagsType().isInActive())
                    .collect(Collectors.toList());

            logService.logDbOperation("Fetched all BagsConsumption entries. Count: " + filteredConsumptions.size());
            return filteredConsumptions;
        } catch (Exception e) {
            throw new CustomException("Error fetching all BagsConsumption: " + e.getMessage());
        }
    }

    @Override
    public BagsConsumption getBagById(int id) {
        try {
            BagsConsumption bagsConsumption = bagsConsumptionRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Bag not found with id: " + id));

            if (!bagsConsumption.getBagsType().isInActive()) {
                throw new ResourceNotFoundException("BagsConsumption not available for deactivated BagsType");
            }

            logService.logDbOperation("Fetched BagsConsumption with ID: " + id);
            return bagsConsumption;
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Unexpected error fetching BagsConsumption with ID: " + id, e);
        }
    }

    @Override
    public BagsConsumption saveBag(BagsConsumption bagsConsumption) {
        try {
            BagsType bagsType = bagsConsumption.getBagsType();
            BagsType existingBagsType = bagsTypeRepository.findById(bagsType.getId())
                    .orElseThrow(() -> new CustomException("BagsType not found for id: " + bagsType.getId()));

            if (!existingBagsType.isInActive()) {
                throw new CustomException("Cannot save BagsConsumption for inactive BagsType with id: " + bagsType.getId());
            }

            BagsConsumption savedBagsConsumption = bagsConsumptionRepository.save(bagsConsumption);
            logService.logDbOperation("Saved BagsConsumption with ID: " + savedBagsConsumption.getId());
            return savedBagsConsumption;
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Unexpected error saving BagsConsumption", e);
        }
    }

    @Override
    public BagsConsumption updateBag(int id, BagsConsumption bag) {
        try {
            BagsConsumption existingBag = bagsConsumptionRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Bag not found with id: " + id));

            BagsType bagsType = bagsTypeRepository.findById(bag.getBagsType().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("BagsType not found with id: " + bag.getBagsType().getId()));

            if (!bagsType.isInActive()) {
                throw new CustomException("Cannot update BagsConsumption for inactive BagsType with id: " + bagsType.getId());
            }

            existingBag.setTransaction_Date(bag.getTransaction_Date());
            existingBag.setOpc_Bags(bag.getOpc_Bags());
            existingBag.setPpc_Bags(bag.getPpc_Bags());
            existingBag.setSrc_Bag(bag.getSrc_Bag());
            existingBag.setNo_Of_Export_Bag(bag.getNo_Of_Export_Bag());
            existingBag.setNo_Of_Depot_Bags(bag.getNo_Of_Depot_Bags());
            existingBag.setNo_Of_Brust_Opc_Bags(bag.getNo_Of_Brust_Opc_Bags());
            existingBag.setNo_Of_Brust_Ppc_Bags(bag.getNo_Of_Brust_Ppc_Bags());
            existingBag.setNo_Of_Brust_Src_Bags(bag.getNo_Of_Brust_Src_Bags());
            existingBag.setTransfer_To_Other_Plants(bag.getTransfer_To_Other_Plants());
            existingBag.setBagsType(bagsType);

            BagsConsumption updatedBag = bagsConsumptionRepository.save(existingBag);
            logService.logDbOperation("Updated BagsConsumption with ID: " + updatedBag.getId());
            return updatedBag;
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Unexpected error updating BagsConsumption with ID: " + id, e);
        }
    }

    @Override
    public BagsConsumption deactivateBagConsumption(int id) {
        try {
            BagsConsumption bagsConsumption = bagsConsumptionRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Bag not found with id: " + id));

            bagsConsumption.setIn_Active(false);
            BagsConsumption updatedBagsConsumption = bagsConsumptionRepository.save(bagsConsumption);

            logService.logDbOperation("Deactivated BagsConsumption with ID: " + updatedBagsConsumption.getId());
            return updatedBagsConsumption;
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Unexpected error deactivating BagsConsumption with ID: " + id, e);
        }
    }
}
