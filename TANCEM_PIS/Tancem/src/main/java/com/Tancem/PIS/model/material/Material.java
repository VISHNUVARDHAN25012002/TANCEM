package com.Tancem.PIS.model.material;

import com.Tancem.PIS.model.materialType.MaterialType;
import com.Tancem.PIS.model.source.Source;
import com.Tancem.PIS.model.status.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "material_seq")
    @SequenceGenerator(name = "material_seq", sequenceName = "material_sequence", allocationSize = 1)
    private int id;

    private String materialName;

    private String glCode;

    private Timestamp createdAt;

    private Timestamp updatedAt;
    
    @ManyToOne
    @JoinColumn(name = "Material_Type")
    private MaterialType materialType;

    @ManyToOne
    @JoinColumn(name = "source_id")
    private Source source;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @Column(name="in_Active",nullable = false)
    private boolean inActive=true;

}
