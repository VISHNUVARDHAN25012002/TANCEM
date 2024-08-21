package com.Tancem.PIS.model.adjustmentType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AdjustmentType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adjustment_type_seq")
    @SequenceGenerator(name = "adjustment_type_seq", sequenceName = "adjustment_type_sequence", allocationSize = 0)
    private int id;

    @Column( nullable = false)
    private String type;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(name="in_Active",nullable = false)
    private boolean inActive=true; //Default value will be true


}
