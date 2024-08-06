package ru.clevertec.models;

public class Kettle extends ElectricalAppliance {
    public Kettle(String name, double power) { // конструктор
        super(name, power);
    }

    @Override
    public void turnOn() { // включаем
        System.out.println("Включаем чайник: " + getName());
    }

    @Override
    public void turnOff() {

        System.out.println("Выключаем чайник: " + getName());
    }
}
