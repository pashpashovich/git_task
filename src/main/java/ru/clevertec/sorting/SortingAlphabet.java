package ru.clevertec.sorting;

import ru.clevertec.models.ElectricalAppliance;

import java.util.Comparator;
import java.util.List;

public class SortingAlphabet extends Thread {

    private final List<ElectricalAppliance> appliances;

    public synchronized void sort() {
        appliances.sort(Comparator.comparing(ElectricalAppliance::getName));
    }
    @Override
    public void run() {
        sort();
    }

    public SortingAlphabet(List<ElectricalAppliance> appliances) {
        this.appliances = appliances;
    }

    public List<ElectricalAppliance> getAppliances() {
        return appliances;
    }
}
