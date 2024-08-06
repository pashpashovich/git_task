package ru.clevertec.functionality;

import ru.clevertec.models.ElectricalAppliance;

import java.io.*;
import java.util.ArrayList;

public class Serialization {
    public void serializator(ArrayList<ElectricalAppliance> arrayList) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream("Electrical appliances"); ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(arrayList);
            System.out.println("Данные записаны в файл!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ElectricalAppliance> deserializator() {
        try(FileInputStream fileInputStream = new FileInputStream("Electrical appliances");
            ObjectInputStream objectInputStream= new ObjectInputStream(fileInputStream)
        ) {
            System.out.println("Данные загружены из файла!");
            return (ArrayList<ElectricalAppliance>) objectInputStream.readObject();
        } catch (FileNotFoundException ex) {
            System.out.println("Файл не найден");
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        return new ArrayList<>();
    }
}

