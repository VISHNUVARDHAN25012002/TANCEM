package com.Tancem.PIS.repository.analysis;

import com.Tancem.PIS.model.analysis.Analysis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalysisRepository extends JpaRepository<Analysis, Integer> {
}
