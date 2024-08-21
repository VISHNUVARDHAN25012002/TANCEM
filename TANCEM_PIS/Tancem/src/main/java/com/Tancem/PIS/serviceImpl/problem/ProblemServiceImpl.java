package com.Tancem.PIS.serviceImpl.problem;


import com.Tancem.PIS.exceptions.CustomException;
import com.Tancem.PIS.model.problem.Problem;
import com.Tancem.PIS.repository.problem.ProblemRepository;
import com.Tancem.PIS.service.problem.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemServiceImpl implements ProblemService {

    @Autowired
    private ProblemRepository problemRepository;

    @Override
    public List<Problem> getAllProblems() {
        try {
            return problemRepository.findAll();
        }catch (Exception e){
            throw new CustomException("Something went wrong");
        }
    }

    @Override
    public Problem getProblemById(Integer id) {
        try {
            return problemRepository.findById(id).orElse(null);
        }catch (Exception e){
            throw new CustomException("Something went wrong");
        }
    }

    @Override
    public Problem saveProblem(Problem problem) {
        try {
            return problemRepository.save(problem);
        }catch (Exception e){
            throw new CustomException("Something went wrong");
        }
    }

    @Override
    public Problem deactivateProblem(Integer id) {
        try{
        Problem problem = getProblemById(id);
            return saveProblem(problem);
        }catch (Exception e){
            throw new CustomException("Something went wrong");
        }
    }
}
