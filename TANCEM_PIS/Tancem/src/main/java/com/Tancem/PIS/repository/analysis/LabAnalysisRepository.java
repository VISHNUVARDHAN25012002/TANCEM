package com.Tancem.PIS.repository.analysis;

import com.Tancem.PIS.model.analysis.LabAnalysis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabAnalysisRepository extends JpaRepository<LabAnalysis, Integer> {
}
