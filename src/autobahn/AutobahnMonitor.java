package autobahn;

import cars.AutonomCar;
import communication.EchoService;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;


public class AutobahnMonitor extends Thread{

    HashMap<String, AutonomCar> registeredCars;

    public AutobahnMonitor(int carCapacity) {
        this.registeredCars = new HashMap<>(carCapacity);
    }


    public void registerCar(AutonomCar car){
        registeredCars.put(car.getCarId(), car);
    }

    public static void main(String[] args) {


        MonitorServer server = new MonitorServer();
        server.start();


    }

    public static class MonitorServer extends Thread{

        public void startServer() throws Exception{
            int port = 9998;
            ServerSocket listenSocket = new ServerSocket(port);
            System.out.println("Multithreaded Server starts on Port " + port);
            while (true) {
                Socket client = listenSocket.accept();
                System.out.println("Connection with: " +     // Output connection
                        client.getRemoteSocketAddress());   // (Client) address
                new EchoService(client).start();
            }

        }

        public void run(){
            try {
                startServer();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }







}
