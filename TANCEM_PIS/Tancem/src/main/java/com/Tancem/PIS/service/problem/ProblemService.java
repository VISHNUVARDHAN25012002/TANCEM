package com.Tancem.PIS.service.problem;

import com.Tancem.PIS.model.problem.Problem;

import java.util.List;

public interface ProblemService {
    List<Problem> getAllProblems();
    Problem getProblemById(Integer id);
    Problem saveProblem(Problem problem);
    Problem deactivateProblem(Integer id);
}
