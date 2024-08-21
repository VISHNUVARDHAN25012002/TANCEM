package com.Tancem.PIS.service.quality;

import com.Tancem.PIS.model.quality.Quality;

import java.util.List;

public interface QualityService {
    Quality createQuality(Quality quality);
    Quality getQualityById(Integer id);
    List<Quality> getAllQualities();
    Quality updateQuality(Quality quality);
    Quality deactivateQuality(Integer id);
}
