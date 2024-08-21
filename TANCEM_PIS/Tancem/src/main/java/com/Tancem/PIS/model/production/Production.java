package com.Tancem.PIS.model.production;

import com.Tancem.PIS.model.equipmentOutputMaterial.EquipmentOutputMaterial;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "production")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Production {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //@Column(name = "transaction_date")
    @Temporal(TemporalType.DATE)
    private Date transactionDate;

    //@Column(name = "running_hours")
    private Float runningHours;

    //@Column(name = "output", length = 50)
    private String output;

    //@Column(name = "fuel_consumption", length = 50)
    private String fuelConsumption;

    //@Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;

    private boolean isAction = true;

    @OneToMany(mappedBy = "production", cascade = CascadeType.ALL)
    private List<EquipmentOutputMaterial> equipmentOutputMaterials;
}
