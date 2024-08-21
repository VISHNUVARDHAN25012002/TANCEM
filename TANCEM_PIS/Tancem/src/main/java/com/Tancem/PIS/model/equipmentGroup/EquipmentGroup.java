package com.Tancem.PIS.model.equipmentGroup;

import com.Tancem.PIS.model.equipment.Equipment;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "equipment_group")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EquipmentGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String equipmentGroup;

    @CreatedDate // Automatically sets created_At to the current time when the record is created.
    @Column(updatable = false) // The creation date should not be updated after it is set.
    private Timestamp createdAt;

    @LastModifiedDate // Automatically updates the updated_At timestamp whenever the record is updated.
    private Timestamp updatedAt;

    private boolean isActive = true;

    @OneToMany(mappedBy = "equipmentGroup", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Equipment> equipmentSet = new HashSet<>();
}
