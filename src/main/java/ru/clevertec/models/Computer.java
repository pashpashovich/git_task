package ru.clevertec.models;



public class Computer extends ElectricalAppliance {
    private final double screen_diagonal;
    public Computer(String name, double power, double screen_diagonal) {
        super(name, power);
        this.screen_diagonal=screen_diagonal;
    }

    @Override
    public void turnOn() { // включаем
        System.out.println("Включаем компьютер: " + getName());
    }

    @Override
    public void turnOff() {

        System.out.println("Выключаем компьютер: " + getName());
    }

    @Override
    public String toString() {

        return super.toString() + ", Диагональ экрана: " + screen_diagonal+" дюймов";
    }
}


