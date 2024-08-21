package com.Tancem.PIS.service.bagsType;


import com.Tancem.PIS.model.bagsType.BagsType;

import java.util.List;

public interface BagsTypeService {
    List<BagsType> getAllBagTypes();
    BagsType getBagTypeById(int id);
    BagsType saveBagType(BagsType bagType);
    BagsType updateBagType(int id, BagsType bagType);
    BagsType deactivateBagType(int id);
}
