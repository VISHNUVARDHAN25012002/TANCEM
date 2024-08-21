package com.Tancem.PIS.model.plantDepartment;

import com.Tancem.PIS.model.problem.Problem;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "plant_department")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlantDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Assuming auto-generated ID
    private int id;

    //@Column(name = "plant_department_description", nullable = false, length = 100)
    private String plantDepartmentDescription;

    @CreatedDate // Automatically sets created_At to the current time when the record is created.
    @Column(updatable = false) // The creation date should not be updated after it is set.
    private Timestamp createdAt;

    @LastModifiedDate // Automatically updates the updated_At timestamp whenever the record is updated.
    private Timestamp updatedAt;

    private boolean isActive = true;

    @OneToMany(mappedBy = "plantDepartment", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Problem> problems;
}
