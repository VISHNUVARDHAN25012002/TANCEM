package com.Tancem.PIS.repository.equipment;


import com.Tancem.PIS.model.equipment.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {
}


// Extends JpaRepository<Equipment, Integer>, providing CRUD operations for the Equipment entity.
//Spring Data JPA automatically provides implementations for common operations like findAll(), findById(), save(), etc.

