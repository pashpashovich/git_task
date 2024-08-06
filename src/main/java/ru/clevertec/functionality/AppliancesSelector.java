package ru.clevertec.functionality;

import ru.clevertec.models.ElectricalAppliance;

@FunctionalInterface
public interface AppliancesSelector {
    boolean select(ElectricalAppliance appliance);
}
