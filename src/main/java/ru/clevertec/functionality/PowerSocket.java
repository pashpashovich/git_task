package ru.clevertec.functionality;

import ru.clevertec.exceptions.PowerException;
import ru.clevertec.models.ElectricalAppliance;

import java.util.ArrayList;
import java.util.Optional;

public class PowerSocket implements P_Socket {
    public static ArrayList<ElectricalAppliance> appliances;


    public PowerSocket() {

        appliances = new ArrayList<>();
    }

    public void Out()
    {
        if (appliances.isEmpty()) System.out.println("Нет приборов, включённых в сеть");
        else {
            for (int i = 1; i <= appliances.size(); i++) {
                System.out.println(i + "." + appliances.get(i-1));
            }
        }
    }

    @Override
    public void plugIn(ElectricalAppliance appliance) {
        int k=0;
        for (ElectricalAppliance electricalAppliance : appliances) {
            if (electricalAppliance == appliance) {
                System.out.println("Уже включён...");
                k = 1;
            }
        }
        if(k==0) {
            appliance.turnOn();
            appliances.add(appliance);
        }
    }

    @Override
    public void unplug(ElectricalAppliance appliance) {
        appliance.turnOff();
        appliances.remove(appliance);
    }

    public double getTotalPower(double addedPower) throws PowerException {
        double totalPower = addedPower;
        for (ElectricalAppliance appliance : appliances) {
            totalPower += appliance.getPower();
        }
        if (totalPower>3520) throw new PowerException(totalPower-addedPower,"Электрическая система не выдерживает нагрузки...Мощность на данный момент составляет ");
        return totalPower;
    }

    public ArrayList<ElectricalAppliance> getSortedAppliancesByPower() {
        ArrayList<ElectricalAppliance> sortedAppliances = new ArrayList<>(appliances);
        sortedAppliances.sort((o1, o2) -> (int) (o1.getPower() - o2.getPower()));
        return sortedAppliances;
    }

    public Optional<ArrayList<ElectricalAppliance>> findApplianceByPowerRange(int minPower, int maxPower) {
        ArrayList<ElectricalAppliance> powers=new ArrayList<>();
        for (ElectricalAppliance appliance : appliances) {
            double power = appliance.getPower();
            if (power >= minPower && power <= maxPower) {
                powers.add(appliance);
            }
        }
        if(powers.isEmpty()) return Optional.empty();
        else return Optional.of(powers);
    }
}
