package com.Tancem.PIS.serviceImpl.equipmentGroup;

import com.Tancem.PIS.exceptions.CustomException;
import com.Tancem.PIS.model.equipmentGroup.EquipmentGroup;
import com.Tancem.PIS.repository.equipmentGroup.EquipmentGroupRepository;
import com.Tancem.PIS.service.equipmentGroup.EquipmentGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class EquipmentGroupServiceImpl implements EquipmentGroupService {

    @Autowired
    private EquipmentGroupRepository repository;

    @Override
    public List<EquipmentGroup> findAll() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new CustomException("Error retrieving all equipment groups", e);
        }
    }

    @Override
    public EquipmentGroup findById(Integer id) {
        try {
            return repository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new CustomException("Error retrieving equipment group by ID", e);
        }
    }

    @Override
    public EquipmentGroup save(EquipmentGroup equipmentGroup) {
        try {
            if (equipmentGroup.getCreatedAt() == null) {
                equipmentGroup.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            }
            equipmentGroup.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            return repository.save(equipmentGroup);
        } catch (Exception e) {
            throw new CustomException("Error saving equipment group", e);
        }
    }

    @Override
    public EquipmentGroup deactivateEquipmentGroup(Integer id) {
        try {
            Optional<EquipmentGroup> optionalGroup = repository.findById(id);
            EquipmentGroup equipmentGroup = optionalGroup.get();
            equipmentGroup.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            return repository.save(equipmentGroup);
        } catch (Exception e) {
            throw new CustomException("Error deactivating equipment group", e);
        }
    }
}

