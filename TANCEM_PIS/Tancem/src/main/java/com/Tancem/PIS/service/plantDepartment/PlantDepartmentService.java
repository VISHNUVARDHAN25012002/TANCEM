package com.Tancem.PIS.service.plantDepartment;

import com.Tancem.PIS.model.plantDepartment.PlantDepartment;


import java.util.List;

public interface PlantDepartmentService {
    List<PlantDepartment> getAllPlantDepartments();
    PlantDepartment getPlantDepartmentById(Integer id);
    PlantDepartment savePlantDepartment(PlantDepartment plantDepartment);
    PlantDepartment deactivatePlantDepartment(Integer id);
}
