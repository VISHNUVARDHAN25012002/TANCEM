package com.Tancem.PIS.service.adjustmentType;

import com.Tancem.PIS.model.adjustmentType.AdjustmentType;

import java.util.List;

public interface AdjustmentTypeService {
    List<AdjustmentType> getAllAdjustmentTypes();
    AdjustmentType getAdjustmentTypeById(int id);
    AdjustmentType createAdjustmentType(AdjustmentType adjustmentType);
    AdjustmentType updateAdjustmentType(int id, AdjustmentType adjustmentType);
    AdjustmentType deactivateAdjustmentType(int id);
}
