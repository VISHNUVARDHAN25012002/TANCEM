package com.Tancem.PIS.service.bagsConsumption;


import com.Tancem.PIS.model.bagsConsumption.BagsConsumption;

import java.util.List;

public interface BagsConsumptionService {
    List<BagsConsumption> getAllBags();
    BagsConsumption getBagById(int id);
    BagsConsumption saveBag(BagsConsumption bag);
    BagsConsumption updateBag(int id, BagsConsumption bag);
    BagsConsumption deactivateBagConsumption(int id);
}
