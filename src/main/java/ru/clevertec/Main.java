package ru.clevertec;

import ru.clevertec.exceptions.PowerException;
import ru.clevertec.exceptions.SignException;
import ru.clevertec.functionality.AppliancesSelector;
import ru.clevertec.functionality.PowerSocket;
import ru.clevertec.functionality.Serialization;
import ru.clevertec.models.*;
import ru.clevertec.sorting.DescendingAppliances;
import ru.clevertec.sorting.ReversedSortingAlphabet;
import ru.clevertec.sorting.SortingAlphabet;
import ru.clevertec.sorting.SortingAppliances;

import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* Домашние электроприборы. Определить иерархию электроприборов. Включить некоторые в розетку. Подсчитать потребляемую мощность. Провести сортировку приборов в квартире на основе мощности. Найти прибор в квартире, соответствующий заданному диапазону параметров.*/
public class Main {
    public static void main(String[] args) throws IOException {

        boolean ex = true; // переменная для выхода из цикла
        PowerSocket powerSocket = new PowerSocket(); // создание объекта розетка (для включения в сеть электроприборов)
        ArrayList<ElectricalAppliance> arrayList = new ArrayList<>(); // список из электрических приборов, которые существуют в квартире
        ArrayList<ElectricalAppliance> arrayList2 = new ArrayList<>(); // список для фильтрации
        Map<String, Predicate<ElectricalAppliance>> applied_filtres = new HashMap<>();
        List<ElectricalAppliance> arrayList3 = new ArrayList<>(); // список для сортировки
        List<String> filtres = new ArrayList<>();
        filtres.add("Мощность");
        filtres.add("Класс");
        Scanner scanner; // для ввода данных
        while (ex) { // бесконечный цикл
            System.out.println("------------------------------------------------------------------------------------------------\nВыберите действие:\n1.Вывести домашние электроприборы на экран\n2.Добавить домашний электроприбор\n3.Удалить домашний электроприбор\n4.Включить в розетку\n5.Выключить из розетки\n6.Вывести включённые в розетку электроприборы\n7.Отсортировать приборы на основе мощности\n8.Найти прибор по мощности\n9.Подсчитать потребляемую мощность включённых электроприборов\n10.Фильтрация\n11.Отменить фильтрацию\n12.Записать данные в файл\n13.Считать данные из файла\n14.Оставить электроприборы только с определённой мощностью\n15.Сортировка\n16.Отменить сортировку\nЛюбое другое число - выход из программы");
            int num = 0;
            boolean flag = true;
            while (flag) {
                try {
                    scanner = new Scanner(System.in); // для ввода данных
                    num = scanner.nextInt(); // выбор пользователя
                    flag = false;
                } catch (InputMismatchException e) { // полученный маркер не соответствует образец для ожидаемого типа
                    System.out.println("Неверный ввод! Попробуйте ещё раз)");
                }
            }
            switch (num) {
                case 1 -> { // Вывести домашние электроприборы на экран
                    try {
                        out(arrayList);
                    } catch (NullPointerException e) {
                        System.out.println("Ничего не найдено!");
                    }
                }
                case 2 -> { // Добавить домашний электроприбор
                    System.out.println("Выберите домашний электроприбор для добавления:\n1.Телевизор\n2.Лампочка\n3.Чайник\n4.Холодильник\n5.Компьютер");
                    int choice = 0;
                    boolean flag2 = true;
                    while (flag2) {
                        try {
                            scanner = new Scanner(System.in); // для ввода данных``
                            choice = scanner.nextInt(); // выбор пользователя
                            if (choice < 1 || choice > 5)
                                System.out.println("Такого прибора не существует...Попробуйте ещё раз");
                            else
                                flag2 = false;
                        } catch (InputMismatchException e) {
                            System.out.println("Неверный ввод! Попробуйте ещё раз)");
                        }
                    }
                    boolean flag10 = true;
                    while (flag10) {
                        try {
                            switch (choice) {
                                case 1 -> {
                                    TV tv = new TV(name(), getValue(0), getValueChannel()); // создаём объект tv
                                    arrayList.add(tv); // добавляем объект в список
                                    flag10 = false;
                                }
                                case 2 -> {
                                    Light bulb = new Light(name(), getValue(0));
                                    arrayList.add(bulb);
                                    flag10 = false;
                                }
                                case 3 -> {
                                    Kettle kettle = new Kettle(name(), getValue(0));
                                    arrayList.add(kettle);
                                    flag10 = false;
                                }
                                case 4 -> {
                                    Fridge fridge = new Fridge(name(), getValue(0), getValueTemperature());
                                    arrayList.add(fridge);
                                    flag10 = false;
                                }
                                case 5 -> {
                                    Computer computer = new Computer(name(), getValue(0), getValue(1));
                                    arrayList.add(computer);
                                    flag10 = false;
                                }
                            }
                        } catch (SignException ef) {
                            System.out.println(ef);
                        }
                    }
                }
                case 3 -> { // Удалить домашний электроприбор
                    out(arrayList); // вывод всех приборов
                    if (!arrayList.isEmpty()) {
                        int del = 0;
                        boolean flag3 = true;
                        while (flag3) {
                            try {
                                scanner = new Scanner(System.in);
                                System.out.println("Выберите электроприбор для удаления: ");
                                del = scanner.nextInt();
                                arrayList.remove(del - 1); // удаляем с индексом del-1
                                System.out.println("Прибор удалён!");
                                flag3 = false;
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("Такого элемента не существует...Попробуйте ещё раз");
                            } catch (InputMismatchException f) {
                                System.out.println("Неверный ввод...Попробуйте ещё раз");
                            }
                        }
                        if (!PowerSocket.appliances.isEmpty()) {
                            for (int i = 0; i < PowerSocket.appliances.size(); i++) // для удаления из включенных в розетку
                            {
                                ElectricalAppliance s = PowerSocket.appliances.get(i);
                                if (s.equals(arrayList.get(del - 1))) {
                                    PowerSocket.appliances.remove(s);
                                }
                            }
                        }
                    }
                }
                case 4 -> { // Включить в розетку
                    out(arrayList);
                    int ch;
                    boolean flag5 = true;
                    if (!arrayList.isEmpty()) {
                        while (flag5) {
                            try {

                                scanner = new Scanner(System.in);
                                System.out.println("Включить прибор в розетку: ");
                                ch = scanner.nextInt();
                                powerSocket.getTotalPower(arrayList.get(ch - 1).getPower());
                                powerSocket.plugIn(arrayList.get(ch - 1));
                                flag5 = false;
                            } catch (InputMismatchException e) {
                                System.out.println("Неверный ввод! Попробуйте ещё раз)");
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("Такого элемента не существует...Попробуйте ещё раз");
                            } catch (PowerException ef) {
                                System.out.println(ef);
                                flag5 = false;
                            }
                        }
                    }
                }
                case 5 -> { // Выключить из розетки
                    if (PowerSocket.appliances.isEmpty())
                        System.out.println("Нет включённых в розетку электроприборов(((");
                    else {
                        boolean flag6 = true;
                        int ch2 = 0;
                        while (flag6) {
                            try {
                                scanner = new Scanner(System.in);
                                System.out.println("Выключить из розетки:");
                                powerSocket.Out();
                                ch2 = scanner.nextInt();
                                powerSocket.unplug(PowerSocket.appliances.get(ch2 - 1));
                                flag6 = false;
                            } catch (InputMismatchException e) {
                                System.out.println("Неверный ввод! Попробуйте ещё раз)");
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("Такого элемента не существует...Попробуйте ещё раз");
                            }
                        }
                    }
                }
                case 6 -> // Вывести включённые в розетку электроприборы
                        powerSocket.Out();
                case 7 -> { // Отсортировать приборы на основе мощности
                    if (arrayList.isEmpty())
                        System.out.println("Электроприборы не найдены(");
                    else {
                        ArrayList<ElectricalAppliance> sortedAppliances = powerSocket.getSortedAppliancesByPower();
                        System.out.println("Отсортированные электроприборы по мощности:");
                        for (ElectricalAppliance sortedAppliance : sortedAppliances) {
                            System.out.println(sortedAppliance);
                        }
                    }
                }
                case 8 -> { // Найти прибор по мощности
                    if (arrayList.isEmpty())
                        System.out.println("Электроприборы не найдены(");
                    else {
                        ArrayList<ElectricalAppliance> arrayList_power; // список из электрических приборов, подпадающих по диапазон мощностей
                        boolean flag7 = true;
                        int minPower = 0;
                        while (flag7) {
                            scanner = new Scanner(System.in);
                            try {
                                System.out.print("Введите начало диапазона мощностей:");
                                minPower = scanner.nextInt();
                                flag7 = false;
                            } catch (InputMismatchException e) {
                                System.out.println("Неверный ввод...Попробуйте ещё раз)");
                            }
                        }
                        boolean flag8 = true;
                        int maxPower = 0;
                        while (flag8) {
                            scanner = new Scanner(System.in);
                            try {
                                System.out.print("Введите конец диапазона мощностей:");
                                maxPower = scanner.nextInt();
                                flag8 = false;
                            } catch (InputMismatchException e) {
                                System.out.println("Неверный ввод...Попробуйте ещё раз)");
                            }
                        }
                        arrayList_power = powerSocket.findApplianceByPowerRange(minPower, maxPower);
                        if (arrayList_power != null) {
                            System.out.println("Найденные приборы в заданном диапазоне (" + minPower + "-" + maxPower + "): ");
                            for (ElectricalAppliance appliance : arrayList_power) {
                                System.out.println(appliance);
                            }
                        } else {
                            System.out.println("Не найдены приборы в заданном диапазоне (" + minPower + "-" + maxPower + ").");
                        }
                    }
                }
                case 9 -> {
                    try {
                        System.out.println("Потребляемая мощность: " + powerSocket.getTotalPower(0) + " ватт");
                    } catch (PowerException g) {
                        System.out.println("Что-то пошло не так...");
                    }
                }
                default -> {
                    System.out.println("Выход из программы выполнен успешно!");
                    ex = false;
                }
                case 10 -> {
                    arrayList2 = new ArrayList<>(arrayList);
                    if (!arrayList.isEmpty()) {
                        List<Integer> chooses = new ArrayList<>();
                        int cont = 0, chose = 0;
                        while (true) {
                            try {
                                System.out.println("Выберите, по каким критериям проводить фильтрацию:\n1.По мощности\n2.По виду");
                                boolean flag3 = true;
                                while (flag3) {
                                    try {
                                        scanner = new Scanner(System.in);
                                        chose = scanner.nextInt();
                                    } catch (InputMismatchException e) {
                                        System.out.println("Неверный выбор( Повторите попытку!");
                                    }
                                    if (chooses.contains(chose))
                                        System.out.println("Вы уже выбирали данный критерий! Повторите попытку, пожалуйста)");
                                    else {
                                        chooses.add(chose);
                                        flag3 = false;
                                    }
                                }
                                if (chooses.size() == 2) {
                                    System.out.println("Все два критерия выбраны!");
                                    break;
                                }
                                boolean flag4 = true;
                                while (flag4) {
                                    try {
                                        System.out.println("Хотите ли добавить ещё критерий для фильтрации?\n1.Да\nДругое число - нет");
                                        scanner = new Scanner(System.in);
                                        cont = scanner.nextInt();
                                        flag4 = false;
                                    } catch (InputMismatchException e) {
                                        System.out.println("Неверный ввод(... Попробуйте ещё раз)");
                                    }
                                }
                                if (cont != 1) break;
                            } catch (InputMismatchException e) {
                                System.out.println("Неверный ввод(... Попробуйте ещё раз)");
                            }
                        }
                        double minPower = 0, maxPower = 0;
                        Class<?> o = null;
                        for (int x : chooses) {
                            switch (x) {
                                case 1 -> {
                                    System.out.println("Введите минимальную мощность электроприбора в ваттах: ");
                                    scanner = new Scanner(System.in);
                                    minPower = scanner.nextDouble();
                                    System.out.println("Введите максимальную мощность электроприбора в ваттах: ");
                                    maxPower = scanner.nextDouble();
                                }
                                case 2 -> {
                                    System.out.println("Выберите вид электроприборов, которые вы хотите оставить:\n1.Компьютеры\n2.Холодильники\n3.Чайники\n4.Телевизоры\n5.Лампочка");
                                    int choose;
                                    scanner = new Scanner(System.in);
                                    choose = scanner.nextInt();
                                    switch (choose) {
                                        case 1 -> o = Computer.class;
                                        case 2 -> o = Fridge.class;
                                        case 3 -> o = Kettle.class;
                                        case 4 -> o = TV.class;
                                        case 5 -> o = Light.class;
                                    }
                                }
                            }
                            arrayList = (ArrayList<ElectricalAppliance>) arrayList.stream()
                                    .filter(far(x, minPower, maxPower, o))
                                    .collect(Collectors.toList());
                            applied_filtres.put(filtres.get(x - 1), far(x, minPower, maxPower, o));
                            out(arrayList);
                        }
                    } else System.out.println("Вы не добавили ещё ни одного электроприбора");
                }
                case 11 -> {
                    if (!applied_filtres.isEmpty()) {
                        int action;
                        int i = 1;
                        System.out.println("Примененные фильтры:");
                        for (Map.Entry<String, Predicate<ElectricalAppliance>> t : applied_filtres.entrySet()) {
                            System.out.println(i + ". " + t.getKey());
                            i++;
                        }
                        System.out.print("Выберите фильтр, который хотите отменить: ");
                        while (true) {
                            try {
                                scanner = new Scanner(System.in);
                                action = scanner.nextInt();
                                if (action <= i) break;
                                else System.out.println("Неверный выбор... Повторите попытку");
                            } catch (InputMismatchException e) {
                                System.out.println("Неверный ввод... Повторите попытку");
                            }
                        }
                        int check = 0;
                        for (Map.Entry<String, Predicate<ElectricalAppliance>> t : applied_filtres.entrySet()) {
                            if (check == action - 1) {
                                applied_filtres.remove(t.getKey());
                                break;
                            }
                            check++;
                        }
                        if (!applied_filtres.isEmpty()) {
                            List<ElectricalAppliance> filteredList = new ArrayList<>();
                            for (Map.Entry<String, Predicate<ElectricalAppliance>> predicate : applied_filtres.entrySet()) {
                                filteredList = arrayList2.stream()
                                        .filter(predicate.getValue())
                                        .collect(Collectors.toList());
                            }
                            arrayList = new ArrayList<>(filteredList);
                        } else {
                            arrayList = new ArrayList<>(arrayList2);
                        }
                        System.out.println("Фильтрация успешно отменена");
                    } else System.out.println("Фильтры еще не были применены");
                }
                case 12 -> {
                    Serialization serializatorObj = new Serialization();
                    serializatorObj.serializator(arrayList);
                }
                case 13 -> {
                    Serialization deserializatorObj = new Serialization();
                    arrayList = deserializatorObj.deserializator();
                }
                case 14 -> {
                    double minPower2;
                    double maxPower2;
                    while (true) {
                        while (true) {
                            try {
                                scanner = new Scanner(System.in);
                                System.out.print("Введите минимальную мощность для ваших электроприборов: ");
                                minPower2 = scanner.nextInt();
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Неверный ввод... Повторите ошибку");
                            }
                        }
                        while (true) {
                            try {
                                scanner = new Scanner(System.in);
                                System.out.print("Введите максимальную мощность для ваших электроприборов: ");
                                maxPower2 = scanner.nextInt();
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Неверный ввод... Повторите ошибку");
                            }
                        }
                        if (minPower2 > maxPower2) {
                            System.out.println("Что-то пошло не так... Минимальное значение больше максимального");
                        } else break;
                    }
                    final double minPowerf = minPower2;
                    final double maxPowerf = maxPower2;
                    AppliancesSelector applianceSelecting = appliance -> (appliance.getPower() > minPowerf && appliance.getPower() < maxPowerf);
                    arrayList = filterAppliances(arrayList, applianceSelecting);
                }
                case 15 -> {
                    if (!arrayList.isEmpty()) {
                        List<Integer> chosing = new ArrayList<>();
                        List<Integer> chosing2 = new ArrayList<>();
                        int i = 0;
                        while (i < 2) {
                            System.out.println("Выберите вид сортировки:\n1.По мощности\n2.По алфавиту");
                            int chose2;
                            while (true) {
                                try {
                                    scanner = new Scanner(System.in);
                                    chose2 = scanner.nextInt();
                                    if (chose2 == 1 || chose2 == 2) {
                                        if (chosing.contains(chose2)) System.out.println("Вы уже выбрали данный вид");
                                        else {
                                            chosing.add(chose2);
                                            break;
                                        }
                                    } else System.out.println("Неверный выбор... Повторите попытку");
                                } catch (InputMismatchException e) {
                                    System.out.println("Неверный ввод( Повторите попытку!");
                                }
                            }
                            int chose = 0;
                            System.out.println("Выберите тип сортировки электроприборов:\n1.По возрастанию (по алфавиту)\n2.По убыванию мощности (в обратном порядке алфавита)");
                            while (true) {
                                try {
                                    scanner = new Scanner(System.in);
                                    chose = scanner.nextInt();
                                    if (chose == 1 || chose == 2) {
                                        chosing2.add(chose);
                                        break;
                                    } else System.out.println("Неверный выбор... Повторите попытку");
                                } catch (InputMismatchException e) {
                                    System.out.println("Неверный выбор( Повторите попытку!");
                                }
                            }
                            i++;
                            if (i == 1) {
                                System.out.println("Хотите добавить еще критерий для сортировки?\n1.Да\n2.Нет");
                                int choosing = scanner.nextInt();
                                if (choosing != 1) break;
                            }
                        }
                        arrayList3 = new ArrayList<>(arrayList);
                        if (i == 1) {
                            if (chosing2.get(0) == 1) {
                                if (chosing.get(0) == 1) {
                                    SortingAppliances thread = new SortingAppliances(arrayList);
                                    thread.start();
                                    try {
                                        thread.join();
                                        System.out.println("Результат сортировки: ");
                                        arrayList = (ArrayList<ElectricalAppliance>) thread.getAppliances();
                                        out(arrayList);
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                } else {
                                    SortingAlphabet thread = new SortingAlphabet(arrayList);
                                    thread.start();
                                    try {
                                        thread.join();
                                        System.out.println("Результат сортировки: ");
                                        arrayList = (ArrayList<ElectricalAppliance>) thread.getAppliances();
                                        out(arrayList);
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                }

                            } else {
                                if (chosing.get(0) == 1) {
                                    DescendingAppliances thread2 = new DescendingAppliances(arrayList);
                                    Thread myThread = new Thread(thread2);
                                    myThread.start();
                                    try {
                                        myThread.join();
                                        System.out.println("Результат сортировки: ");
                                        arrayList = (ArrayList<ElectricalAppliance>) thread2.getAppliances();
                                        out(arrayList);
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                } else {
                                    ReversedSortingAlphabet thread2 = new ReversedSortingAlphabet(arrayList);
                                    Thread myThread = new Thread(thread2);
                                    myThread.start();
                                    try {
                                        myThread.join();
                                        System.out.println("Результат сортировки: ");
                                        arrayList = (ArrayList<ElectricalAppliance>) thread2.getAppliances();
                                        out(arrayList);
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                        } else {
                            if (chosing2.get(0) == 1 && chosing2.get(1) == 1) {
                                SortingAppliances thread = new SortingAppliances(arrayList);
                                SortingAlphabet thread2 = new SortingAlphabet(arrayList);
                                thread.start();
                                thread2.start();
                                try {
                                    thread.join();
                                    System.out.println("Результат сортировки: ");
                                    arrayList = (ArrayList<ElectricalAppliance>) thread.getAppliances();
                                    out(arrayList);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                try {
                                    thread2.join();
                                    System.out.println("Результат сортировки: ");
                                    arrayList = (ArrayList<ElectricalAppliance>) thread2.getAppliances();
                                    out(arrayList);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            } else {
                                if (chosing2.get(0) == 2 && chosing2.get(1) == 2) {
                                    DescendingAppliances descendingAppliances = new DescendingAppliances(arrayList);
                                    Thread thread = new Thread(descendingAppliances);
                                    ReversedSortingAlphabet reversedSortingAlphabet = new ReversedSortingAlphabet(arrayList);
                                    Thread thread2 = new Thread(reversedSortingAlphabet);
                                    thread.start();
                                    try {
                                        thread.join();
                                        System.out.println("Результат сортировки: ");
                                        arrayList = (ArrayList<ElectricalAppliance>) descendingAppliances.getAppliances();
                                        out(arrayList);
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                    thread2.start();
                                    try {
                                        thread2.join();
                                        System.out.println("Результат сортировки: ");
                                        arrayList = (ArrayList<ElectricalAppliance>) reversedSortingAlphabet.getAppliances();
                                        out(arrayList);
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                } else {
                                    if (chosing2.get(0) == 1 && chosing2.get(1) == 2) {
                                        SortingAppliances sortingAppliances = new SortingAppliances(arrayList);
                                        Thread thread = new Thread(sortingAppliances);
                                        ReversedSortingAlphabet reversedSortingAlphabet = new ReversedSortingAlphabet(arrayList);
                                        Thread thread2 = new Thread(reversedSortingAlphabet);
                                        thread.start();
                                        try {
                                            thread.join();
                                            System.out.println("Результат сортировки: ");
                                            arrayList = (ArrayList<ElectricalAppliance>) sortingAppliances.getAppliances();
                                            out(arrayList);
                                        } catch (InterruptedException e) {
                                            throw new RuntimeException(e);
                                        }
                                        thread2.start();
                                        try {
                                            thread2.join();
                                            System.out.println("Результат сортировки: ");
                                            arrayList = (ArrayList<ElectricalAppliance>) reversedSortingAlphabet.getAppliances();
                                            out(arrayList);
                                        } catch (InterruptedException e) {
                                            throw new RuntimeException(e);
                                        }
                                    } else {
                                        if (chosing2.get(0) == 2 && chosing2.get(1) == 1) {
                                            DescendingAppliances descendingAppliances = new DescendingAppliances(arrayList);
                                            Thread thread = new Thread(descendingAppliances);
                                            SortingAlphabet sortingAlphabet = new SortingAlphabet(arrayList);
                                            Thread thread2 = new Thread(sortingAlphabet);
                                            thread.start();
                                            try {
                                                thread.join();
                                                System.out.println("Результат сортировки: ");
                                                arrayList = (ArrayList<ElectricalAppliance>) descendingAppliances.getAppliances();
                                                out(arrayList);
                                            } catch (InterruptedException e) {
                                                throw new RuntimeException(e);
                                            }
                                            thread2.start();
                                            try {
                                                thread2.join();
                                                System.out.println("Результат сортировки: ");
                                                arrayList = (ArrayList<ElectricalAppliance>) sortingAlphabet.getAppliances();
                                                out(arrayList);
                                            } catch (InterruptedException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    } else System.out.println("Вы не добавили ещё ни одного электроприбора");
                }
                case 16 -> {
                    int choice3 = 0;
                    if (!arrayList.isEmpty()) {
                        System.out.print("Если хотите отменить сортировку,то нажмите 1\nЕсли нет, то нажмите любую другую клавишу\n");
                        try {
                            scanner = new Scanner(System.in);
                            choice3 = scanner.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Неверный ввод... Повторите попытку");
                        }
                        if (choice3 == 1) {
                            arrayList = new ArrayList<>(arrayList3);
                            System.out.println("Сортировка успешно отменена!");
                        } else {
                            System.out.println("Все данные остались в неизменённом состоянии");
                        }
                    }
                }
            }

        }
    }

    private static String name() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название: ");
        return scanner.next();
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


}

