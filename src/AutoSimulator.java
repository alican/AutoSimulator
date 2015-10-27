import cars.AutonomCar;
import cars.CarBaseClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alican on 27.10.2015.
 */
public class AutoSimulator {

    //HashMap<String, CarBaseClass> carList;
    ArrayList<AutonomCar> autonomCarArrayList;


    public AutoSimulator() {

       // carList = new HashMap<>(10);
        autonomCarArrayList = new ArrayList<>(10);

        for (int i = 0; i < 10; i++){
            autonomCarArrayList.add(new AutonomCar());

        }
    }
}
