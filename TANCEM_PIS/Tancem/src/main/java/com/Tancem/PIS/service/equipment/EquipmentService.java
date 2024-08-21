package com.Tancem.PIS.service.equipment;

import com.Tancem.PIS.model.equipment.Equipment;

import java.util.List;

// Declares methods to manage equipment data, such as getAllEquipments(), getEquipmentById(Integer id), and saveEquipment(Equipment equipment).

public interface EquipmentService {
    List<Equipment> getAllEquipments(); //  Retrieves all equipment records.

    Equipment getEquipmentById(Integer id); // Retrieves a single equipment record by its ID.

    Equipment saveEquipment(Equipment equipment); // Saves a new equipment record or updates an existing one. It sets the created_At or updated_At timestamp depending on whether it's a new or existing record.

    Equipment deactivateEquipment(Integer id);
}