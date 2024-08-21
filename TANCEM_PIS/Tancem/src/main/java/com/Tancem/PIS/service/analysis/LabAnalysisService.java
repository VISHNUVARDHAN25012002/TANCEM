


//import com.Tancem.PIS.model.AnalysisModel.LabAnalysis;

package com.Tancem.PIS.service.analysis;

import com.Tancem.PIS.model.analysis.LabAnalysis;
import org.springframework.stereotype.Service;


import java.util.List;
//@Service
public interface LabAnalysisService {
    LabAnalysis saveLabAnalysis(LabAnalysis labAnalysis);
    List<LabAnalysis> getAllLabAnalyses();
    LabAnalysis getLabAnalysisById(int id);
    LabAnalysis updateLabAnalysis(LabAnalysis labAnalysis);
    void toggleActiveState(int id);
}
