package com.Tancem.PIS.model.strength;

import com.Tancem.PIS.model.material.Material;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Strength {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "strength_seq")
    @SequenceGenerator(name = "strength_seq", sequenceName = "Strength_id_seq", allocationSize = 1)
    private int id;


    private Date transactionDate;


    private Date sampleDate;


    private int day1;


    private int day3;


    private int day7;


    private int day28;


    private int expansion;

    @CreatedDate
    @Column(name = "created_at",nullable = false,updatable = false)
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @Column(name="in_Active",nullable = false)
    private boolean inActive=true;

    // Getters and Setters
    // Constructors

}
