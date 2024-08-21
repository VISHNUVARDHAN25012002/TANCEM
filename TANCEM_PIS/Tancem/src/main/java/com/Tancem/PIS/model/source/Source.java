package com.Tancem.PIS.model.source;

import com.Tancem.PIS.model.material.Material;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Source {
    @Id
    private int id;

    private String Source;

    private Timestamp created_At;
    private Timestamp updated_At;


    @OneToMany(mappedBy = "source")
    private Set<Material> materials;


}
