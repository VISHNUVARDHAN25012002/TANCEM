package com.Tancem.PIS.service.material;


import com.Tancem.PIS.model.material.Material;

import java.util.List;

public interface MaterialService {
    List<Material> getAllMaterials();
    Material getMaterialById(Integer id);
    Material saveMaterial(Material material);
    void deleteMaterial(Integer id);
}