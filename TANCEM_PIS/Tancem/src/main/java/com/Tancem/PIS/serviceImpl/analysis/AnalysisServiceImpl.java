package com.Tancem.PIS.serviceImpl.analysis;

import com.Tancem.PIS.model.analysis.Analysis;
import com.Tancem.PIS.repository.analysis.AnalysisRepository;
import com.Tancem.PIS.service.analysis.AnalysisService;
import com.Tancem.PIS.service.logService.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnalysisServiceImpl implements AnalysisService {

    @Autowired
    private AnalysisRepository analysisRepository;

    @Autowired
    private LogService logService;

    @Override
    public Analysis saveAnalysis(Analysis analysis) {
        Analysis savedAnalysis = analysisRepository.save(analysis);
        logService.logDbOperation("Saved analysis with ID: " + savedAnalysis.getId());
        return savedAnalysis;
    }

    @Override
    public List<Analysis> getAllAnalyses() {
        List<Analysis> analyses = analysisRepository.findAll();
        logService.logDbOperation("Fetched all analyses");
        return analyses;
    }

    @Override
    public Analysis getAnalysisById(int id) {
        Analysis analysis = analysisRepository.findById(id).orElse(null);
        if (analysis != null) {
            logService.logDbOperation("Fetched analysis with ID: " + id);
        } else {
            logService.logDbOperation("Analysis with ID: " + id + " not found");
        }
        return analysis;
    }

    @Override
    public Analysis updateAnalysis(Analysis analysis) {
        Analysis updatedAnalysis = analysisRepository.save(analysis);
        logService.logDbOperation("Updated analysis with ID: " + updatedAnalysis.getId());
        return updatedAnalysis;
    }

    @Override
    public void deactivateAnalysis(int id) {
        Optional<Analysis> analysisOpt = analysisRepository.findById(id);
        if (analysisOpt.isPresent()) {
            Analysis analysis = analysisOpt.get();
            analysis.setActive(false);
            analysisRepository.save(analysis);
            logService.logDbOperation("Deactivated analysis with ID: " + id);
        } else {
            logService.logDbOperation("Analysis with ID: " + id + " not found for deactivation");
        }
    }
}
