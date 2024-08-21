package com.Tancem.PIS.controller.problem;

import com.Tancem.PIS.exceptions.CustomException;
import com.Tancem.PIS.model.problem.Problem;
import com.Tancem.PIS.service.problem.ProblemService;
import com.Tancem.PIS.service.logService.LogService;
import com.Tancem.PIS.util.LogUtil;
import com.Tancem.PIS.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tancem/pis/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;
    @Autowired
    private LogService logService;

    @GetMapping
    public ResponseEntity<Map<String,Object>> getAllProblems() {
        try {
            List<Problem> problems = problemService.getAllProblems();
            logService.logApiCall("Successfully retrieved all problems from the database");
            LogUtil.logRetrieveAll(Problem.class);
            return ResponseUtil.createResponse(HttpStatus.OK, Problem.class, problems);
        }catch (CustomException e) {
            logService.logError("Error retrieving all problems from the database");
            LogUtil.logErrorRetrieveAll(Problem.class, e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND,Problem.class, e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> getProblemById(@PathVariable Integer id) {
       try {
           Problem problem = problemService.getProblemById(id);
           logService.logApiCall("Successfully retrieved problem from the database with id: " + id);
           LogUtil.logRetrieveById(Problem.class, id);
           return ResponseUtil.createResponse(HttpStatus.OK, Problem.class, problem);
       }catch (CustomException e) {
           logService.logError("Error retrieving problem from the database with id: " + id);
           LogUtil.logErrorRetrieveById(Problem.class, id, e.getMessage());
           return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND,Problem.class, e);
       }
    }

    @PostMapping
    public ResponseEntity<Map<String,Object>> createProblem(@RequestBody Problem problem) {
        try {
            Problem newProblem = problemService.saveProblem(problem);
            problem.setCreatedAt(new Timestamp(System.currentTimeMillis())); // Set updated_At
            logService.logApiCall("Successfully created problem");
            LogUtil.logCreate(Problem.class, problem);
            return ResponseUtil.createResponse(HttpStatus.CREATED, Problem.class, problem);
        }catch (CustomException e) {
            logService.logError("Error creating problem");
            LogUtil.logErrorCreate(Problem.class, e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND,Problem.class, e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String,Object>> updateProblem(@PathVariable Integer id, @RequestBody Problem problem) {
        try {
            Problem existingProblem = problemService.getProblemById(id);
            problem.setActive(true);
            problem.setId(id); // Ensure the ID is set for the update
            problem.setUpdatedAt(new Timestamp(System.currentTimeMillis())); // Set updated_At
            //Problem updatedProblem = problemService.saveProblem(problem);
            logService.logApiCall("Successfully updated problem with id: " + id);
            LogUtil.logUpdate(Problem.class, id);
            return ResponseUtil.createResponse(HttpStatus.OK, Problem.class, existingProblem);
        }catch (CustomException e) {
            logService.logError("Error updating problem with id: " + id);
            LogUtil.logErrorUpdate(Problem.class, id, e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND,Problem.class, e);
        }
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<Map<String,Object>> deactivateProblem(@PathVariable Integer id) {
        try {
            Problem problem = problemService.deactivateProblem(id);
            if (problem == null) {
                throw new CustomException("Problem not found");
            }
            problem.setActive(false);
            problem.setUpdatedAt(new Timestamp(System.currentTimeMillis())); // Set updated_At
            logService.logApiCall("Successfully deactivated problem with id: " + id);
            LogUtil.logDelete(Problem.class, id);
            return ResponseUtil.createResponse(HttpStatus.OK, Problem.class, problem);
        }catch (CustomException e) {
            logService.logError("Error deactivating problem with id: " + id);
            LogUtil.logErrorDelete(Problem.class, id, e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND,Problem.class, e);
        }
    }


}