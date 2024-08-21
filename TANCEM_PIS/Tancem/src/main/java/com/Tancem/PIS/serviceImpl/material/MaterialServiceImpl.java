package com.Tancem.PIS.serviceImpl.material;


import com.Tancem.PIS.exceptions.CustomException;

import com.Tancem.PIS.model.material.Material;
import com.Tancem.PIS.repository.material.MaterialRepository;
import com.Tancem.PIS.service.material.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Override
    public List<Material> getAllMaterials() throws CustomException {
        try {
            return materialRepository.findAll();
        } catch (Exception e) {
            throw new CustomException("Error retrieving all materials", e);
        }
    }

    @Override
    public Material getMaterialById(Integer id) throws CustomException {
        try {
            Optional<Material> material = materialRepository.findById(id);
            if (material.isPresent()) {
                return material.get();
            } else {
                throw new CustomException("Material not found");
            }
        } catch (Exception e) {
            throw new CustomException("Error retrieving material by ID", e);
        }
    }

    @Override
    public Material saveMaterial(Material material) throws CustomException {
        try {
            return materialRepository.save(material);
        } catch (Exception e) {
            throw new CustomException("Error saving material", e);
        }
    }

    @Override
    public void deleteMaterial(Integer id) throws CustomException {
        try {
            materialRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomException("Error deleting material", e);
        }
    }
}
