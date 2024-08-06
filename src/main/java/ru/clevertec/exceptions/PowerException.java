package ru.clevertec.exceptions;

public class PowerException extends Exception {
    private double power;
    public PowerException(double power, String message) {
        super(message);
        this.power = power;
    }


    @Override
    public String toString()
    {
        return getMessage()+power;
    }
}
