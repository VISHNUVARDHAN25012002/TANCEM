package com.Tancem.PIS.serviceImpl.production;


import com.Tancem.PIS.exceptions.CustomException;
import com.Tancem.PIS.model.production.Production;
import com.Tancem.PIS.repository.production.ProductionRepository;
import com.Tancem.PIS.service.production.ProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductionServiceImpl implements ProductionService {

    @Autowired
    private ProductionRepository productionRepository;

    @Override
    public List<Production> getAllProductions() {
        return productionRepository.findAll();
    }

    @Override
    public Production getProductionById(Integer id) {
        try {
            return productionRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new CustomException("something went wrong");
        }
    }

    @Override
    public Production saveProduction(Production production) {
        try {
            return productionRepository.save(production);
        } catch (Exception e) {
            throw new CustomException("something went wrong");
        }
    }

    @Override
    public Production updateProduction(Integer id, Production production) {
        try {
            Production existingProduction = productionRepository.findById(id).orElseThrow(()
                    -> new CustomException("Production with id " + id + " does not exist"));
            existingProduction.setOutput(production.getOutput());
            // Set other fields as needed
            return productionRepository.save(existingProduction);
        } catch (Exception e) {
            throw new CustomException("Something went wrong", e);
        }
    }

    public Production deactivateProduction(Integer id) {
        try {
            Production existingProduction = productionRepository.findById(id).orElseThrow(()
                    -> new CustomException("Production with id " + id + " does not exist"));
            existingProduction.setAction(false); // Deactivate the production
            return productionRepository.save(existingProduction);
        } catch (Exception e) {
            throw new CustomException("Something went wrong while deactivating the production", e);
        }
    }
}
