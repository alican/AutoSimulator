import cars.AutonomCar;

import java.io.IOException;
import java.util.ArrayList;

public class AutoSimulator {

    ArrayList<AutonomCar> autonomCarArrayList;

    public AutoSimulator() throws IOException {
        autonomCarArrayList = new ArrayList<>(10);
    }

    void start(){
        addCars(5000);

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
