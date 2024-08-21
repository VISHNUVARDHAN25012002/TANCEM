package com.Tancem.PIS.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ResponseUtil {

    public static ResponseEntity<Map<String, Object>> createResponse(HttpStatus status, Class<?> entityClass, Object data) {
        String message = generateMessage(status, entityClass);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", status.value());
        response.put("message", message);
        response.put("data", data);
        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<Map<String, Object>> createErrorResponse(HttpStatus status, Class<?> entityClass, Exception e) {
        String message = generateMessage(status, entityClass);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", status.value());
        response.put("message", message);
        response.put("error", e.getMessage());
        return new ResponseEntity<>(response, status);
    }

    private static String generateMessage(HttpStatus status, Class<?> entityClass) {
        String entityName = entityClass.getSimpleName();
        switch (status) {
            case OK:
                return entityName + " retrieved successfully";
            case CREATED:
                return entityName + " created successfully";
            case INTERNAL_SERVER_ERROR:
                return "Error" + entityName + " operation";
            case NOT_FOUND:
                return entityName + " not found";
            case BAD_REQUEST:
                return "Bad request during " + entityName + " operation";
            default:
                return "Status: " + status;
        }
    }
}
