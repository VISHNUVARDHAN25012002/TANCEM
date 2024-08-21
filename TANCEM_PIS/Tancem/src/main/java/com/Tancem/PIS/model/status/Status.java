package com.Tancem.PIS.model.status;

import com.Tancem.PIS.model.material.Material;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.sql.Timestamp;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Data
public class Status {
    @Id
    private int id;
    private String status;
    private Timestamp created_At;
    private Timestamp updated_At;

    @OneToMany(mappedBy = "status")
    private Set<Material> materials;
}
