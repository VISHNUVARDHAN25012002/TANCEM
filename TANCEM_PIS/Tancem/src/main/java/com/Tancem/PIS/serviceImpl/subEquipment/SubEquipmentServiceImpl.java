package com.Tancem.PIS.serviceImpl.subEquipment;


import com.Tancem.PIS.exceptions.CustomException;
import com.Tancem.PIS.model.subEquipment.SubEquipment;
import com.Tancem.PIS.repository.subEquipment.SubEquipmentRepository;
import com.Tancem.PIS.service.subEquipment.SubEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;


@Service
public class SubEquipmentServiceImpl implements SubEquipmentService {

    @Autowired
    private SubEquipmentRepository subEquipmentRepository;

    @Override
    public List<SubEquipment> getAllSubEquipments() {
        try {
            return subEquipmentRepository.findAll();
        }catch (Exception e){
            throw new CustomException("Something went wrong");
        }
    }

    @Override
    public SubEquipment getSubEquipmentById(Integer id) {
        try {
            return subEquipmentRepository.findById(id).orElse(null);
        }catch (Exception e){
            throw new CustomException("Something went wrong");
        }
    }

    @Override
    public SubEquipment saveSubEquipment(SubEquipment subEquipment) {
        try {
            subEquipment.setCreatedAt(new Timestamp(System.currentTimeMillis()));// Set updated_At
            subEquipment.setUpdatedAt(new Timestamp(System.currentTimeMillis())); // Set updated_At
            return subEquipmentRepository.save(subEquipment);
         }catch (Exception e){
        throw new CustomException("Something went wrong");
        }

    }

    @Override
    public SubEquipment updateSubEquipment(Integer id, SubEquipment updatedSubEquipment) {
        try{
        SubEquipment existingSubEquipment = getSubEquipmentById(id);
            existingSubEquipment.setSubEquipmentDescription(updatedSubEquipment.getSubEquipmentDescription());
            // updated_At will be set automatically
            return subEquipmentRepository.save(existingSubEquipment);
        }catch (Exception e){}
             throw new CustomException("Something went wrong");
    }

    @Override
    public SubEquipment deactivateSubEquipment(Integer id) {
        try{
        SubEquipment existingSubEquipment = getSubEquipmentById(id);
            return subEquipmentRepository.save(existingSubEquipment);
        }catch (Exception e){
            throw new CustomException("Something went wrong");
        }
    }
}


