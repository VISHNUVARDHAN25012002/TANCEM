package com.Tancem.PIS.service.stoppageDetails;


import com.Tancem.PIS.model.stoppageDetails.StoppageDetails;

import java.util.List;

public interface StoppageDetailsService {
    List<StoppageDetails> getAllStoppageDetails();
    StoppageDetails getStoppageDetailsById(Integer id);
    StoppageDetails createStoppageDetails(StoppageDetails stoppageDetails);
    StoppageDetails updateStoppageDetails(Integer id, StoppageDetails stoppageDetails);
    StoppageDetails deactivateStoppageDetails(Integer id);
}
