import cars.AutonomCar;

import java.io.IOException;
import java.util.ArrayList;

import com.budhash.cliche.Command;
import com.budhash.cliche.ShellFactory;


public class AutoSimulator {

    ArrayList<AutonomCar> autonomCarArrayList;

    public AutoSimulator() throws IOException {
        autonomCarArrayList = new ArrayList<>(10);
    }

    public static void main(String[] args) throws IOException {
        ShellFactory.createConsoleShell("AutoSimulator", "Wilkommen bei AutoSimulator", new AutoSimulator())
                .commandLoop(); // and three.

    }

    @Command
    public void addCars(int count) {
        autonomCarArrayList.add(new AutonomCar());

        for (int i = 0; i < count; i++) {
            autonomCarArrayList.add(new AutonomCar());
        }
    }

    @Command
    public void printCars() {
        for (AutonomCar autonomCar : autonomCarArrayList) {
            System.out.println(autonomCar.getCarId());
        }
    }

    public void printMenu() {
        System.out.println("Menue: ");
        System.out.println();
    }
}
