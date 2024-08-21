package com.Tancem.PIS.model.adjustments;

import com.Tancem.PIS.model.adjustmentType.AdjustmentType;
import com.Tancem.PIS.model.material.Material;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Adjustments {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adjustments_seq")
    @SequenceGenerator(name = "adjustments_seq", sequenceName = "adjustments_sequence", allocationSize = 1)
    private int id;

    @Column(nullable = false)
    private String fromPlantName;

    @Column(nullable = false)
    private String toPlantName;

    @Column(nullable = false)
    private LocalDate transactionDate;

    @Column(nullable = false)
    private int quantity;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "adjustment_type_id", nullable = false)
    private AdjustmentType adjustmentType;

    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @Column(name="in_Active",nullable = false)
    private boolean inActive=true;
}
