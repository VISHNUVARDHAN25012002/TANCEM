package com.Tancem.PIS.repository.equipmentGroup;


import com.Tancem.PIS.model.equipmentGroup.EquipmentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentGroupRepository extends JpaRepository<EquipmentGroup, Integer> {
}

