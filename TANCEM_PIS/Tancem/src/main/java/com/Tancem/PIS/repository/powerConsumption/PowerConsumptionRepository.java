package com.Tancem.PIS.repository.powerConsumption;


import com.Tancem.PIS.model.powerConsumption.PowerConsumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PowerConsumptionRepository extends JpaRepository<PowerConsumption, Integer> {
}

