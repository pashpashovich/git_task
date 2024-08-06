package ru.clevertec.functionality;


import ru.clevertec.models.ElectricalAppliance;

public interface P_Socket {
    void plugIn(ElectricalAppliance appliance);
    void unplug(ElectricalAppliance appliance);
}

