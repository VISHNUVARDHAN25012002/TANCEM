package com.Tancem.PIS.model.stoppageDetails;

import com.Tancem.PIS.model.equipment.Equipment;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "stoppage_details")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StoppageDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //@Column(name = "transaction_date")
    private LocalDate transactionDate;

    //@Column(name = "stoppage_hours")
    private LocalTime stoppageHours;

    //@Column(name = "total_hours")
    private LocalTime totalHours;

    //@Column(name = "hours")
    private LocalTime hours;

    //@Column(name = "no_of_stoppages")
    private int noOfStoppages;

    //@Column(name = "remarks")
    private String remarks;

    private boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "equipment_id", nullable = false)
    @JsonBackReference
    private Equipment equipment;
}
