import cars.AutonomCar;

import java.io.IOException;
import java.util.ArrayList;

public class AutoSimulator {

    ArrayList<AutonomCar> autonomCarArrayList;

    int cars;
    public AutoSimulator(int cars) throws IOException {
        this.cars = cars;
        autonomCarArrayList = new ArrayList<>(cars);
    }

    void start(){
        addCars(cars);

    }

    public void addCars(int count) {
        autonomCarArrayList.add(new AutonomCar());

        for (int i = 0; i < count; i++) {
            autonomCarArrayList.add(new AutonomCar());
        }
    }

    public void printCars() {
        for (AutonomCar autonomCar : autonomCarArrayList) {
            System.out.println(autonomCar.getCarId());
        }
    }


}
