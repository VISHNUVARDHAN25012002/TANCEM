package com.Tancem.PIS.model.powerConsumption;

import com.Tancem.PIS.model.equipment.Equipment;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "Power_Consumption")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PowerConsumption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //@Column(name = "transaction_Date")
    private Date transactionDate;

    //@Column(name = "units")
    private Integer units;

    private boolean isAction = true;

    @ManyToOne
    @JoinColumn(name = "equipment_id", nullable = false)
    @JsonBackReference
    private Equipment equipment; // This is the many-to-one side

}
