package com.Tancem.PIS.service.strength;

import com.Tancem.PIS.model.strength.Strength;

import java.util.List;

public interface StrengthService {
    Strength saveStrength(Strength strength);

    List<Strength> getAllStrengths();

    Strength getStrengthById(int id);

    Strength updateStrength(int id, Strength strength);

    Strength deactivateStrength(int id);
}
