package com.Tancem.PIS.repository.problem;


import com.Tancem.PIS.model.problem.Problem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Integer> {
}
