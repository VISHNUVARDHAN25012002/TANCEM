package com.Tancem.PIS.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {

    private static final Logger logger = LoggerFactory.getLogger(LogUtil.class);

    // General logging methods

    public static void logInfo(Class<?> entityClass, String message, Object... args) {
        Logger logger = LoggerFactory.getLogger(entityClass);
        logger.info(message, args);
    }

    public static void logError(Class<?> entityClass, String message, Object... args) {
        Logger logger = LoggerFactory.getLogger(entityClass); // Use entityClass instead of b
        logger.error(message, args);
    }

    // CRUD specific logging info methods

    public static void logRetrieveAll(Class<?> entityClass) {
        logInfo(entityClass, "Retrieved all {}s successfully", entityClass.getSimpleName());
    }

    public static void logRetrieveById(Class<?> entityClass, Object id) {
        logInfo(entityClass, "Retrieved {} with id: {}", entityClass.getSimpleName(), id);
    }

    public static void logCreate(Class<?> entityClass, Object id) {
        logInfo(entityClass, "Created new {} with id: {}", entityClass.getSimpleName(), id);
    }

    public static void logUpdate(Class<?> entityClass, Object id) {
        logInfo(entityClass, "Updated {} with id: {}", entityClass.getSimpleName(), id);
    }

    public static void logDelete(Class<?> entityClass, Object id) {
        logInfo(entityClass, "Deleted {} with id: {}", entityClass.getSimpleName(), id);
    }

    // Error logging methods

    public static void logErrorRetrieveAll(Class<?> entityClass, String errorMessage) {
        logError(entityClass, "Error retrieving {}s: {}", entityClass.getSimpleName(), errorMessage);
    }

    public static void logErrorRetrieveById(Class<?> entityClass, Object id, String errorMessage) {
        logError(entityClass, "Error retrieving {} with id {}: {}", entityClass.getSimpleName(), id, errorMessage);
    }

    public static void logErrorCreate(Class<?> entityClass, String errorMessage) {
        logError(entityClass, "Error creating {}: {}", entityClass.getSimpleName(), errorMessage);
    }

    public static void logErrorUpdate(Class<?> entityClass, Object id, String errorMessage) {
        logError(entityClass, "Error updating {} with id {}: {}", entityClass.getSimpleName(), id, errorMessage);
    }

    public static void logErrorDelete(Class<?> entityClass, Object id, String errorMessage) {
        logError(entityClass, "Error deleting {} with id {}: {}", entityClass.getSimpleName(), id, errorMessage);
    }
}
