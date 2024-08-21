package com.Tancem.PIS.service.production;


import com.Tancem.PIS.model.production.Production;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductionService {
    Production saveProduction(Production production);
    List<Production> getAllProductions();
    Production getProductionById(Integer id);
    Production updateProduction(Integer id, Production production);
    Production deactivateProduction(Integer id);
}
