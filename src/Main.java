import cars.AutonomCar;

import java.io.IOException;


public class Main {

    public static void main(String[] args){
        try {
            AutoSimulator autoSimulator = new AutoSimulator();
        } catch (IOException e) {
            e.printStackTrace();
        }



        /*
        String carClassName = "cars.AutonomCar";
        Class<?> CarClass = Class.forName(carClassName);
        Object car = CarClass.newInstance();

        String methodName = "";

        methodName = "printId";
        Method printIdMethod = car.getClass().getMethod(methodName);
        printIdMethod.invoke(car);
        */
    }

}
