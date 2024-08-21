package com.Tancem.PIS.service.status;

import com.Tancem.PIS.model.status.Status;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StatusService {
    List<Status> getAllStatuses();
    Status getStatusById(Integer id);
    Status saveStatus(Status status);
    void deleteStatus(Integer id);
}
