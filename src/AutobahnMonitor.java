import cars.AutonomCar;
import cars.CarDataPackage;
import communication.EchoService;
import models.Bench;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.HashMap;


public class AutobahnMonitor extends Thread {

    static long counter;

    static Bench bench;
    static XmlRpcClient client;


    HashMap<String, AutonomCar> registeredCars;

    public AutobahnMonitor(int carCapacity) {
        this.registeredCars = new HashMap<>(carCapacity);
    }


    public void registerCar(AutonomCar car) {
        registeredCars.put(car.getCarId(), car);
    }

    public static void main(String[] args) {

        System.out.println("Monitor wurde gestartet...");
        bench = Bench.getInstance();

        MonitorServer server = new MonitorServer();
        server.start();


    }

    private static void init_xmprpc() {

        try {
            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
            config.setServerURL(new URL("http://127.0.0.1:8080/xmlrpc"));
            client = new XmlRpcClient();
            client.setConfig(config);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public static class MonitorServer extends Thread {

        public void startServer() throws Exception {
            int port = 9997;
            ServerSocket listenSocket = new ServerSocket(port);
            System.out.println("Multithreaded Server starts on Port " + port);
            while (true) {
                Socket client = listenSocket.accept();
                System.out.println("Connection with: " +     // Output connection
                        client.getRemoteSocketAddress());   // (Client) address
                new EchoService(client).start();
            }

        }

        public void startServerUDP() {

            try {

                DatagramSocket socket = new DatagramSocket(9997);
                byte[] incomingData = new byte[1024];


                while (true) {
                    // Auf Anfrage warten

                    DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
                    socket.receive(incomingPacket);
                    Bench.BenchTest test = bench.start();

                    byte[] data = incomingPacket.getData();
                    ByteArrayInputStream in = new ByteArrayInputStream(data);
                    ObjectInputStream is = new ObjectInputStream(in);
                    try {
                        CarDataPackage carDataPackage = (CarDataPackage) is.readObject();
                        System.out.println(++counter + " CarDataPackage received = " + carDataPackage.getId());
                        test.stop();
                    } catch (ClassNotFoundException e) {
                        bench.isFailed();
                        e.printStackTrace();
                    }
                    if (counter == 1000) {
                        System.out.println(bench.results());
                    }


                    // Daten an NavigationDienst schicken


                }
            } catch (SocketException e) {
                bench.isFailed();
                e.printStackTrace();
            } catch (IOException e) {
                bench.isFailed();
                e.printStackTrace();
            }


        }

        public void run() {
            try {
                init_xmprpc();
                startServerUDP();
                //startServer();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


}
