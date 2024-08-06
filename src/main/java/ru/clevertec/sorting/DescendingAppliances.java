package ru.clevertec.sorting;

import ru.clevertec.models.ElectricalAppliance;

import java.util.Comparator;
import java.util.List;

public class DescendingAppliances implements Runnable {
    private final List <ElectricalAppliance> appliances;

    public synchronized void sort() {
        appliances.sort(Comparator.comparingDouble(ElectricalAppliance::getPower).reversed());
    }
    @Override
    public void run() {
        sort();
    }

    public DescendingAppliances(List<ElectricalAppliance> appliances) {
        this.appliances = appliances;
    }

    public List<ElectricalAppliance> getAppliances() {
        return appliances;
    }
}
