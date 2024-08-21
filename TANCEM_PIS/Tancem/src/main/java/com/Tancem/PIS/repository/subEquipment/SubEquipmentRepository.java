package com.Tancem.PIS.repository.subEquipment;

import com.Tancem.PIS.model.subEquipment.SubEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubEquipmentRepository extends JpaRepository<SubEquipment, Integer> {
}

