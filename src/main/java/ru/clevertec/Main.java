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

    private static String name() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название: ");
        return scanner.next();
    }

    private static void out(ArrayList arrayList) {
        if (arrayList.isEmpty()) // если нет добавленных электроприборов
            System.out.println("Электроприборы не найдены(");
        else {
            System.out.println("Электроприборы:");
            for (int i = 0; i < arrayList.size(); i++) {  // выводим добавленные электроприборы
                System.out.println((i + 1) + "." + arrayList.get(i).getClass() + " " + arrayList.get(i));
            }
        }
    }
}
