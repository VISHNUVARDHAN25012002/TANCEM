package com.Tancem.PIS.service.adjustments;

import com.Tancem.PIS.model.adjustments.Adjustments;

import java.util.List;

public interface AdjustmentsService {
    List<Adjustments> getAllAdjustments();
    Adjustments getAdjustmentById(int id);
    Adjustments createAdjustment(Adjustments adjustment);
    Adjustments updateAdjustment(int id, Adjustments adjustment);
    Adjustments deactivateAdjustments(int id);
}
