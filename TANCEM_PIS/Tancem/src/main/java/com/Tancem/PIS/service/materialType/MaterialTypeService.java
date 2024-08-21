package com.Tancem.PIS.service.materialType;

import com.Tancem.PIS.exceptions.CustomException;
import com.Tancem.PIS.model.materialType.MaterialType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MaterialTypeService {
    List<MaterialType> getAllMaterialTypes() throws CustomException;

    MaterialType getMaterialTypeById(Integer id) throws CustomException;

    MaterialType saveMaterialType(MaterialType materialType) throws CustomException;

    MaterialType deactivateMaterialType(Integer id) throws CustomException;
}
