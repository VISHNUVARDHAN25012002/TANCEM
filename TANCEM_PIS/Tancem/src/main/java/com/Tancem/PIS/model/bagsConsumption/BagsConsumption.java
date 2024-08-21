package com.Tancem.PIS.model.bagsConsumption;


import com.Tancem.PIS.model.bagsType.BagsType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BagsConsumption {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bags_consumption_seq")
    @SequenceGenerator(name = "bags_consumption_seq", sequenceName = "bags_consumption_seq", allocationSize = 0)
    private int id;



    private Date transaction_Date;


   @Column(nullable = false)
    private int opc_Bags;


    @Column(nullable = false)
    private int ppc_Bags;


    @Column(nullable = false)
    private int src_Bag;


    @Column(nullable = false)
    private int no_Of_Export_Bag;


    @Column(nullable = false)
    private int no_Of_Depot_Bags;


    @Column(nullable = false)
    private int no_Of_Brust_Opc_Bags;


    @Column(nullable = false)
    private int no_Of_Brust_Ppc_Bags;


    @Column(nullable = false)
    private int no_Of_Brust_Src_Bags;


    @Column(nullable = false)
    private int transfer_To_Other_Plants;

    @Column(name="in_Active",nullable = false)
    private boolean in_Active=true;

    @ManyToOne
    @JoinColumn(name = "bags_Type_id", nullable = false)
    private BagsType bagsType;


}