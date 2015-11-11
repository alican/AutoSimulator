import cars.AutonomCar;
import models.Bench;

import java.io.IOException;
import java.util.Scanner;


public class Main {

    static final String addr = "localhost";
    static final int port = 9997;


    static int cars = 100;



    public static void main(String[] args){
        Scanner user_input = new Scanner( System.in );

        AutonomCar.port = port;
        AutonomCar.address = addr;
        Bench.setInfo(addr, port);
        try {


            AutoSimulator autoSimulator = new AutoSimulator(cars);
            autoSimulator.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bench bench = Bench.getInstance();

        user_input.next( );
        System.out.println(bench.results());


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
