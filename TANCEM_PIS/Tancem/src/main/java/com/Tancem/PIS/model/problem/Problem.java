package com.Tancem.PIS.model.problem;

import com.Tancem.PIS.model.plantDepartment.PlantDepartment;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "problem")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //@Column(name = "problem", nullable = false, length = 255)
    private String problem;

    @ManyToOne
    @JoinColumn(name = "plant_department_id", nullable = false)
    @JsonBackReference
    private PlantDepartment plantDepartment;

    @CreatedDate // Automatically sets created_At to the current time when the record is created.
    @Column(updatable = false) // The creation date should not be updated after it is set.
    private Timestamp createdAt;

    @LastModifiedDate // Automatically updates the updated_At timestamp whenever the record is updated.
    private Timestamp updatedAt;

    private boolean isActive = true;

}
