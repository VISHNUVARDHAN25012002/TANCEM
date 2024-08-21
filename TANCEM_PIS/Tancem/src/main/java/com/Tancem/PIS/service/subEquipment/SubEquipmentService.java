package com.Tancem.PIS.service.subEquipment;


import com.Tancem.PIS.model.subEquipment.SubEquipment;

import java.util.List;

public interface SubEquipmentService {
    List<SubEquipment> getAllSubEquipments();
    SubEquipment getSubEquipmentById(Integer id);
    SubEquipment saveSubEquipment(SubEquipment subEquipment);
    SubEquipment updateSubEquipment(Integer id, SubEquipment subEquipment);
    SubEquipment deactivateSubEquipment(Integer id);
}

