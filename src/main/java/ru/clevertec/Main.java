package ru.clevertec;

import ru.clevertec.exceptions.SignException;
import ru.clevertec.functionality.AppliancesSelector;
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

    private static ArrayList<ElectricalAppliance> filterAppliances(ArrayList<ElectricalAppliance> array1, AppliancesSelector x) {
        ArrayList<ElectricalAppliance> filteredList = new ArrayList<>();
        for (ElectricalAppliance appliance : array1) {
            if (x.select(appliance)) {
                filteredList.add(appliance);
            }
        }
        return filteredList;
    }

    private static Predicate<ElectricalAppliance> far(int number, double minPower, double maxPower, Class<?> o) {
        switch (number) {
            case 1 -> {
                return (x -> x.getPower() > minPower && x.getPower() < maxPower);
            }
            case 2 -> {
                return (x -> x.getClass() == o);
            }

        }
        return null;
    }

    private static double getValueTemperature() {
        double value = 0;
        boolean flag = true;
        while (flag) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Введите температуру: ");
                value = scanner.nextDouble();
                flag = false;
            } catch (InputMismatchException e) {
                System.out.println("Неверный ввод! Попробуйте ещё раз)");
            }
        }
        return value;
    }

    private static int getValueChannel() throws SignException {
        int value = 0;
        boolean flag = true;
        while (flag) {
            try {
                boolean flag2 = true;
                while (flag2) {
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Введите количество каналов: ");
                    value = scanner.nextInt();
                    if (value < 0) throw new SignException(value, "каналов ");
                    flag2 = false;
                }
                flag = false;
            } catch (InputMismatchException e) {
                System.out.println("Неверный ввод! Попробуйте ещё раз)");
            }
        }
        return value;
    }

    private static double getValue(int i) throws SignException {
        double value = 0;
        boolean flag = true;
        while (flag) {
            try {
                String[] options = {"мощность", "диагональ экрана"};
                String[] options2 = {"мощности", "диагонали экрана"};
                System.out.println("Введите " + options[i] + ":");
                Scanner scanner = new Scanner(System.in);
                value = scanner.nextDouble();
                if (value < 0) throw new SignException(value, options2[i]);
                flag = false;

            } catch (InputMismatchException e) {
                System.out.println("Неверный ввод! Попробуйте ещё раз)");
            }
        }
        return value;
    }
}
