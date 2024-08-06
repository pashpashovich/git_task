package ru.clevertec.models;

public class Light extends ElectricalAppliance {
    public Light(String name, double power) { // конструктор
        super(name, power);
    }

    @Override
    public void turnOn() { // включаем
        System.out.println("Включаем лампочку: " + getName());
    }

    @Override
    public void turnOff() {

        System.out.println("Выключаем лампочку: " + getName());
    }
}


