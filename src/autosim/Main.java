package autosim;

import autosim.cars.AutonomCar;
import autosim.models.Bench;

import java.io.IOException;
import java.util.Scanner;


public class Main {

    public static void main(String[] args){

        String input;
        int cars;
        int protocol;


        Scanner user_input = new Scanner(System.in);

        System.out.print("Enter IP of Autobahn Monitor (default: localhost): ");

        input = user_input.nextLine();
        if (input.length() < 2){
            AutonomCar.address = "localhost";
            System.out.println("Default:" + AutonomCar.address);
        }


        System.out.print("Enter Port number of Autobahn Monitor (default: 9997)");

        input = user_input.nextLine();

        if (input.length() < 1){
            AutonomCar.port = 9997;
            System.out.println("Default:" + AutonomCar.port);

        }else{
            try{
                AutonomCar.port = Integer.parseInt(input) ;
            }catch (Exception e){
                AutonomCar.port = 9997;
                System.out.println("Default:" + AutonomCar.port);
            }
        }

        System.out.print("Enter number of autosim.cars (default: 10)");

        input = user_input.nextLine();

        if (input.length() < 1){
            cars = 10;
            System.out.println("Default:" + 10);
        }else{
            try{
                cars = Integer.parseInt(input) ;
            }catch (Exception e){
                cars = 10;
                System.out.println("Default:" + 10);
            }
        }

        System.out.print("Select protocol (default: UDP): \n 1) \t UDP \n 2) \t TCP\n");

        input = user_input.nextLine();

        if (!input.isEmpty()){

            try{
                protocol = Integer.parseInt(input);
                if (protocol == 1){
                    AutoSimulator.protocol = AutoSimulator.PROTOCOLS.UDP;

                }else {
                    AutoSimulator.protocol = AutoSimulator.PROTOCOLS.TCP;
                }
            }catch (Exception e){
                AutoSimulator.protocol = AutoSimulator.PROTOCOLS.UDP;
                System.out.println("Default: UDP");
            }

        }else {
            AutoSimulator.protocol = AutoSimulator.PROTOCOLS.UDP;
        }



        try {
            AutoSimulator autoSimulator = new AutoSimulator(cars);
            autoSimulator.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bench.setInfo(AutonomCar.address, AutonomCar.port);
        Bench bench = Bench.getInstance();

        input = user_input.next( );
        System.out.println(bench.results());


    }

}
