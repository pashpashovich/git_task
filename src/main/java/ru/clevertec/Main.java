package ru.clevertec;

import ru.clevertec.models.ElectricalAppliance;

import java.util.*;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        boolean ex = true;
        ArrayList<ElectricalAppliance> arrayList = new ArrayList<>();
        ArrayList<ElectricalAppliance> arrayList2 = new ArrayList<>();
        Map<String, Predicate<ElectricalAppliance>> applied_filtres = new HashMap<>();
        List<ElectricalAppliance> arrayList3 = new ArrayList<>();
        List<String> filtres = new ArrayList<>();
        filtres.add("Мощность");
        filtres.add("Класс");
        Scanner scanner;
    }
}
