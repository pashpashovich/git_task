package ru.clevertec.functionality;

import ru.clevertec.models.ElectricalAppliance;

@FunctionalInterface

interface AppliancesSelector {
    boolean select(ElectricalAppliance appliance);
}
