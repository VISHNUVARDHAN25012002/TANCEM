package com.Tancem.PIS.serviceImpl.equipmentOutputMaterial;

import com.Tancem.PIS.exceptions.CustomException;
import com.Tancem.PIS.model.equipmentOutputMaterial.EquipmentOutputMaterial;
import com.Tancem.PIS.repository.equipmentOutputMaterial.EquipmentOutputMaterialRepository;
import com.Tancem.PIS.service.equipmentOutputMaterial.EquipmentOutputMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentOutputMaterialServiceImpl implements EquipmentOutputMaterialService {

    @Autowired
    private EquipmentOutputMaterialRepository equipmentOutputMaterialRepository;

    @Override
    public EquipmentOutputMaterial createEquipmentOutputMaterial(EquipmentOutputMaterial equipmentOutputMaterial) throws CustomException {
        try {
            return equipmentOutputMaterialRepository.save(equipmentOutputMaterial);
        } catch (Exception e) {
            throw new CustomException("Error creating equipment output material", e);
        }
    }

    @Override
    public EquipmentOutputMaterial getEquipmentOutputMaterialById(int id) throws CustomException {
        try {
            return equipmentOutputMaterialRepository.findById(id)
                    .orElseThrow(() -> new CustomException("EquipmentOutputMaterial not found with ID: " + id));
        } catch (Exception e) {
            throw new CustomException("Error retrieving equipment output material by ID", e);
        }
    }

    @Override
    public List<EquipmentOutputMaterial> getAllEquipmentOutputMaterials() throws CustomException {
        try {
            return equipmentOutputMaterialRepository.findAll();
        } catch (Exception e) {
            throw new CustomException("Error retrieving all equipment output materials", e);
        }
    }

    @Override
    public EquipmentOutputMaterial saveEquipmentOutputMaterial(EquipmentOutputMaterial equipmentOutputMaterial) throws CustomException {
        try {
            return equipmentOutputMaterialRepository.save(equipmentOutputMaterial);
        } catch (Exception e) {
            throw new CustomException("Error saving equipment output material", e);
        }
    }

    @Override
    public EquipmentOutputMaterial deactivateEquipmentOutputMaterial(Integer id) throws CustomException {
        try {
            EquipmentOutputMaterial material = getEquipmentOutputMaterialById(id);
            material.setActive(false); // Ensure the material is marked as inactive
            return saveEquipmentOutputMaterial(material);
        } catch (Exception e) {
            throw new CustomException("Error deactivating equipment output material", e);
        }
    }
}
