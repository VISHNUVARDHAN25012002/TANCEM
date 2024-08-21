package com.Tancem.PIS.serviceImpl.status;

import com.Tancem.PIS.repository.status.StatusRepository;
import com.Tancem.PIS.exceptions.CustomException;
import com.Tancem.PIS.model.status.Status;

import com.Tancem.PIS.service.status.StatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusServiceImpl implements StatusService {
    private static final Logger logger = LoggerFactory.getLogger(StatusServiceImpl.class);

    @Autowired
    private StatusRepository statusRepository;

    @Override
    public List<Status> getAllStatuses() {
        List<Status> statuses = statusRepository.findAll();
        logger.info("Retrieved all statuses");
        return statuses;
    }

    @Override
    public Status getStatusById(Integer id) {
        Optional<Status> status = statusRepository.findById(id);
        if (status.isPresent()) {
            logger.info("Retrieved status with id: {}", id);
            return status.get();
        } else {
            logger.error("Status not found with id: {}", id);
            throw new CustomException("Status not found");
        }
    }

    @Override
    public Status saveStatus(Status status) {
        Status savedStatus = statusRepository.save(status);
        logger.info("Saved status with id: {}", savedStatus.getId());
        return savedStatus;
    }

    @Override
    public void deleteStatus(Integer id) {
        statusRepository.deleteById(id);
        logger.info("Deleted status with id: {}", id);
    }
}
