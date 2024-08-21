package com.Tancem.PIS.serviceImpl.materialType;

import com.Tancem.PIS.exceptions.CustomException;
import com.Tancem.PIS.model.materialType.MaterialType;

import com.Tancem.PIS.repository.materialType.MaterialTypeRepository;
import com.Tancem.PIS.service.materialType.MaterialTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class MaterialTypeServiceImpl implements MaterialTypeService {

    @Autowired
    private MaterialTypeRepository materialTypeRepository;

    @Override
    public List<MaterialType> getAllMaterialTypes() throws CustomException {
        try {
            return materialTypeRepository.findAll();
        } catch (Exception e) {
            throw new CustomException("Error retrieving all material types", e);
        }
    }

    @Override
    public MaterialType getMaterialTypeById(Integer id) throws CustomException {
        try {
            Optional<MaterialType> materialTypeOptional = materialTypeRepository.findById(id);
            if (materialTypeOptional.isPresent()) {
                return materialTypeOptional.get();
            } else {
                throw new CustomException("Material Type with ID " + id + " not found");
            }
        } catch (Exception e) {
            throw new CustomException("Error retrieving material type with ID: " + id, e);
        }
    }

    @Override
    public MaterialType saveMaterialType(MaterialType materialType) throws CustomException {
        try {
            return materialTypeRepository.save(materialType);
        } catch (Exception e) {
            throw new CustomException("Error saving material type", e);
        }
    }

    @Override
    public MaterialType deactivateMaterialType(Integer id) throws CustomException {
        try {
            // Retrieve the existing MaterialType by ID
            MaterialType materialType = getMaterialTypeById(id);

            // Set isActive to false (or 0) to deactivate the MaterialType
            materialType.setActive(false); // Assuming isActive is a Boolean field; change to 0 if it's an Integer
            materialType.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            // Save the updated MaterialType
            materialTypeRepository.save(materialType);
        } catch (Exception e) {
            throw new CustomException("Error deactivating material type with ID: " + id, e);
        }
        return null;
    }
}
