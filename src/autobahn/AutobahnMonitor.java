package autobahn;

import cars.AutonomCar;
import cars.CarDataPackage;
import communication.EchoService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
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

        public void startServerUDP(){

            try{

                DatagramSocket socket = new DatagramSocket(9997);
                byte[] incomingData = new byte[1024];


                while(true) {
                    // Auf Anfrage warten


                    DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
                    socket.receive(incomingPacket);
                    byte[] data = incomingPacket.getData();
                    ByteArrayInputStream in = new ByteArrayInputStream(data);
                    ObjectInputStream is = new ObjectInputStream(in);
                    try {
                        CarDataPackage carDataPackage = (CarDataPackage) is.readObject();
                        System.out.println("CarDataPackage received = " + carDataPackage.getId());
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }


                }
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        public void run(){
            try {
                startServerUDP();
                //   startServer();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }







}
