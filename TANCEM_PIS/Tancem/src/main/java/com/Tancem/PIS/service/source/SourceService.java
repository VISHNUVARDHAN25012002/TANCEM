package com.Tancem.PIS.service.source;

import com.Tancem.PIS.model.source.Source;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SourceService {
    List<Source> getAllSources();
    Source getSourceById(Integer id);
    Source saveSource(Source source);
    void deleteSource(Integer id);
}
