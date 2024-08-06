package ru.clevertec.models;

import java.io.Serializable;

public abstract class ElectricalAppliance implements Serializable {
    private String name;
    private final double power;

    public ElectricalAppliance(String  name, double power) {
        this.name = name;
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public double getPower() {
        return power;
    }

    public abstract void turnOn();
    public abstract void turnOff();

    @Override
    public String toString() {

        return "Название электроприбора: " + name + ", Мощность: " + power + " ватт";
    }

    @Override
    public boolean equals(Object obj) {
        final ElectricalAppliance other = (ElectricalAppliance) obj;
        if ((obj == null) || (getClass() != obj.getClass()) || (!this.name.equals(other.name))) {
            return false;
        }
        return this.power == other.power;
    }
}


