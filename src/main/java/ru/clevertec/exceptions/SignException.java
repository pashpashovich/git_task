package ru.clevertec.exceptions;

public class SignException extends Exception{
    private final double value;
    String message;

    public SignException(double value, String message) {
        this.value = value;
        this.message=message;
    }


    @Override
    public String getMessage() {
        return "Вы ввели отрицательное значение ";
    }

    @Override
    public String toString()
    {
        return getMessage()+message+":"+value;
    }
}
