package com.Tancem.PIS.service.logService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LogService {
    private static final Logger apiLogger = LoggerFactory.getLogger("com.Tancem.PIS.Service.logService.api");
    private static final Logger errorLogger = LoggerFactory.getLogger("com.Tancem.PIS.Service.logService.error");
    private static final Logger exceptionLogger = LoggerFactory.getLogger("com.Tancem.PIS.Service.logService.exception");
    private static final Logger dbLogger = LoggerFactory.getLogger("com.Tancem.PIS.Service.logService.db");

    public void logApiCall(String message) {
        apiLogger.info("API Call: {}", message);
    }

    public void logError(String message) {
        errorLogger.error("Error: {}", message);
    }

    public void logException(Exception e) {
        exceptionLogger.error("Exception: {}", e.getMessage(), e);
    }

    public void logDbOperation(String message) {
        dbLogger.info("DB Operation: {}", message);
    }
}
