package com.Tancem.PIS.service.equipmentOutputMaterial;


import com.Tancem.PIS.model.equipmentOutputMaterial.EquipmentOutputMaterial;

import java.util.List;

public interface EquipmentOutputMaterialService {
    EquipmentOutputMaterial createEquipmentOutputMaterial(EquipmentOutputMaterial equipmentOutputMaterial);
    EquipmentOutputMaterial getEquipmentOutputMaterialById(int id);
    List<EquipmentOutputMaterial> getAllEquipmentOutputMaterials();
    EquipmentOutputMaterial saveEquipmentOutputMaterial(EquipmentOutputMaterial equipmentOutputMaterial);
    EquipmentOutputMaterial deactivateEquipmentOutputMaterial(Integer id);
}
