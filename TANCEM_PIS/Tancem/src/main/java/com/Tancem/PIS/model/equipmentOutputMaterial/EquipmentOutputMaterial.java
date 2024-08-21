package com.Tancem.PIS.model.equipmentOutputMaterial;

import com.Tancem.PIS.model.production.Production;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "equipment_output_material")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EquipmentOutputMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //@Column(name = "output_material", columnDefinition = "TEXT")
    private String outputMaterial;

    @CreatedDate // Automatically sets created_At to the current time when the record is created.
    @Column(updatable = false) // The creation date should not be updated after it is set.
    private Timestamp createdAt;

    @LastModifiedDate // Automatically updates the updated_At timestamp whenever the record is updated.
    private Timestamp updatedAt;

    private boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "production_id")
    @JsonBackReference
    private Production production;

}
