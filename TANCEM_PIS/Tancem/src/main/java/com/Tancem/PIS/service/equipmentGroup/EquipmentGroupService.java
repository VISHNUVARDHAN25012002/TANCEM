package com.Tancem.PIS.service.equipmentGroup;


import com.Tancem.PIS.model.equipmentGroup.EquipmentGroup;

import java.util.List;

public interface EquipmentGroupService {
    List<EquipmentGroup> findAll();
    EquipmentGroup findById(Integer id);
    EquipmentGroup save(EquipmentGroup equipmentGroup);
    EquipmentGroup deactivateEquipmentGroup(Integer id);

}


