package com.Tancem.PIS.serviceImpl.equipment;

import com.Tancem.PIS.exceptions.CustomException;
import com.Tancem.PIS.model.equipment.Equipment;
import com.Tancem.PIS.repository.equipment.EquipmentRepository;
import com.Tancem.PIS.service.equipment.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Override
    public List<Equipment> getAllEquipments() throws CustomException {
        try {
            return equipmentRepository.findAll();
        } catch (Exception e) {
            throw new CustomException("Error retrieving all equipments", e);
        }
    }

    @Override
    public Equipment getEquipmentById(Integer id) throws CustomException {
        try {
            Optional<Equipment> equipment = equipmentRepository.findById(id);
            if (equipment.isPresent()) {
                return equipment.get();
            } else {
                throw new CustomException("Equipment not found");
            }
        } catch (Exception e) {
            throw new CustomException("Error retrieving equipment by ID", e);
        }
    }

    @Override
    public Equipment saveEquipment(Equipment equipment) throws CustomException {
        try {
            if (equipment.getCreatedAt() == null) {
                equipment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            }
          //  equipment.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            return equipmentRepository.save(equipment);
        } catch (Exception e) {
            throw new CustomException("Error saving equipment", e);
        }
    }

    @Override
    public Equipment deactivateEquipment(Integer id) throws CustomException {
        try {
            Equipment equipment = getEquipmentById(id);
            //equipment.setActive(false);
            return saveEquipment(equipment);
        } catch (Exception e) {
            throw new CustomException("Error deactivating equipment", e);
        }
    }
}