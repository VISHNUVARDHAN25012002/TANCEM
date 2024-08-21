package com.Tancem.PIS.model.quality;

import com.Tancem.PIS.model.material.Material;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Quality {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quality_seq")
    @SequenceGenerator(name = "quality_seq", sequenceName = "quality_id_seq", allocationSize = 1)
    private Integer id;

    private Time initialSettingTime;
    private Time finalSettingTime;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    private String blaine;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

    @Column(name="in_Active",nullable = false)
    private boolean inActive=true;
}
