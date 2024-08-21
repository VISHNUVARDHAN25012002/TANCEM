package com.Tancem.PIS.model.subEquipment;

import com.Tancem.PIS.model.equipment.Equipment;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "sub_equipment")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubEquipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //@Column(name = "sub_equipment_description", nullable = false, length = 100)
    private String subEquipmentDescription;

    @CreatedDate // Automatically sets created_At to the current time when the record is created.
    @Column(updatable = false) // The creation date should not be updated after it is set.
    private Timestamp createdAt;

    @LastModifiedDate // Automatically updates the updated_At timestamp whenever the record is updated.
    private Timestamp updatedAt;

    private boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "equipment_id", nullable = false)
    @JsonBackReference
    private Equipment equipment;

}
