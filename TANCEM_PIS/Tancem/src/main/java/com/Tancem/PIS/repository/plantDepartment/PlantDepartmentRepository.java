package com.Tancem.PIS.repository.plantDepartment;

import com.Tancem.PIS.model.plantDepartment.PlantDepartment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantDepartmentRepository extends JpaRepository<PlantDepartment, Integer> {
}

