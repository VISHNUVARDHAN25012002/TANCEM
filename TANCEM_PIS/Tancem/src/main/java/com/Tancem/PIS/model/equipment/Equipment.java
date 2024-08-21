package com.Tancem.PIS.model.equipment;

import com.Tancem.PIS.model.equipmentGroup.EquipmentGroup;
import com.Tancem.PIS.model.powerConsumption.PowerConsumption;
import com.Tancem.PIS.model.stoppageDetails.StoppageDetails;
import com.Tancem.PIS.model.subEquipment.SubEquipment;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

        // This is an entity class mapped to the equipment table in the database.

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "equipment")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //@Column(name = "equipment_Description")
    private String equipmentDescription;

    private boolean isActive = true;

    @CreatedDate // Automatically sets created_At to the current time when the record is created.
    @Column(updatable = false) // The creation date should not be updated after it is set.
    private Timestamp createdAt; // Timestamps for creation and last update.

    @LastModifiedDate // Automatically updates the updated_At timestamp whenever the record is updated.
    private Timestamp updatedAt; // Timestamps for creation and last update.

    @ManyToOne // Define relationships between entities.
    @JoinColumn(name = "equipment_group_id", nullable = false)
    @JsonBackReference // avoid infinite recursion in bidirectional relationships.
    private EquipmentGroup equipmentGroup;

    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL)
    private Set<SubEquipment> subEquipments = new HashSet<>();

    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL)
    private Set<StoppageDetails> stoppageDetails = new HashSet<>();

    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL)
    private Set<PowerConsumption> powerConsumptions = new HashSet<>(); // Add this line to manage the one-to-many relationship

}

// CascadeType.All - This means that all operations performed on the parent entity will be automatically applied to its associated child entities.

