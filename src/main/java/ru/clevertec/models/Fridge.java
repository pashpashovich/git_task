package ru.clevertec.models;

public class Fridge extends ElectricalAppliance {
    private double temperature; // температура холодильника
    public Fridge(String name, double power, double temperature) { // конструктор
        super(name, power);
        this.temperature=temperature;
    }

    @Override
    public void turnOn() { // включаем

        System.out.println("Включаем холодильник: " + getName());
    }

    @Override
    public void turnOff() { // выключаем

        System.out.println("Выключаем холодильник: " + getName());
    }

    @Override
    public String toString() { // инфа об объекте

        return super.toString() + ", Температура: " + temperature;
    }
}

