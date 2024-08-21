package com.Tancem.PIS.repository.equipmentOutputMaterial;


import com.Tancem.PIS.model.equipmentOutputMaterial.EquipmentOutputMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentOutputMaterialRepository extends JpaRepository<EquipmentOutputMaterial, Integer> {
}
