package com.Tancem.PIS.service.analysis;

import com.Tancem.PIS.model.analysis.Analysis;
import java.util.List;

public interface AnalysisService {
    Analysis saveAnalysis(Analysis analysis);
    List<Analysis> getAllAnalyses();
    Analysis getAnalysisById(int id);
    Analysis updateAnalysis(Analysis analysis);
    void deactivateAnalysis(int id);


}
