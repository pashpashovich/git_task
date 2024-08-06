package ru.clevertec.models;

public class TV extends ElectricalAppliance {
    private int channel; // кол-во каналов

    public TV(String name, double power, int channel) { // конструктор
        super(name, power);
        this.channel=channel;
    }

    public int getChannel()  // возвращает кол-во каналов (геттер)
    {
        return channel;
    }

    @Override
    public void turnOn() { // включаем

        System.out.println("Включаем телевизор: " + getName() + ", каналы: " + getChannel());
    }

    @Override
    public void turnOff() { // выключаем

        System.out.println("Выключаем телевизор: " + getName() + ", каналы: " + getChannel());
    }

    @Override
    public String toString() { // инфа об объекте

        return super.toString() + ", Кол-во каналов: " + channel;
    }
}

