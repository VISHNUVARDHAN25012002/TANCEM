package com.Tancem.PIS.serviceImpl.plantDepartment;


import com.Tancem.PIS.exceptions.CustomException;
import com.Tancem.PIS.model.plantDepartment.PlantDepartment;
import com.Tancem.PIS.repository.plantDepartment.PlantDepartmentRepository;
import com.Tancem.PIS.service.plantDepartment.PlantDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PlantDepartmentServiceImpl implements PlantDepartmentService {

    @Autowired
    private PlantDepartmentRepository plantDepartmentRepository;

    @Override
    public List<PlantDepartment> getAllPlantDepartments() {
        try {
            return plantDepartmentRepository.findAll();
        }catch (Exception e){
            throw new CustomException("Error retrieving all PlantDepartments", e);
        }
    }

    @Override
    public PlantDepartment getPlantDepartmentById(Integer id) {
        try {
            return plantDepartmentRepository.findById(id).orElse(null);
        }catch (Exception e){
            throw new CustomException("Error retrieving PlantDepartment by id", e);
        }
    }

    @Override
    public PlantDepartment savePlantDepartment(PlantDepartment plantDepartment) {
        try {
            return plantDepartmentRepository.save(plantDepartment);
        }catch (Exception e){
            throw new CustomException("Error saving PlantDepartment", e);
        }
    }

    @Override
    public PlantDepartment deactivatePlantDepartment(Integer id) {
        try {
            PlantDepartment department = getPlantDepartmentById(id);
            if (department == null) {
                return savePlantDepartment(department);
            } else {
                throw new CustomException("PlantDepartment not found");
            }
        }catch (Exception e){
            throw new CustomException("Error deactivating PlantDepartment", e);
        }
    }
}