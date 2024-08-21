package com.Tancem.PIS.repository.stoppageDetails;



import com.Tancem.PIS.model.stoppageDetails.StoppageDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoppageDetailsRepository extends JpaRepository<StoppageDetails, Integer> {
}
