package cars;


import communication.UserInterface;
import models.Bench;

import java.io.*;
import java.net.*;

public class AutonomCar extends CarBaseClass {

    public static String address = "localhost";
    public static int port = 9998;

    BufferedReader fromServer;
    ObjectOutputStream toServer;
    UserInterface user = new UserInterface();
    SocketAddress addr = new InetSocketAddress(address, port);


    static Bench bench;



    public AutonomCar() {
        bench = Bench.getInstance();
        start();

    }

    public void printId(){
        System.out.println("My id is: " + getCarId());
    }

    private void startClient() throws Exception{


        try (Socket socket = new Socket()){
            socket.connect(addr);
            toServer = new ObjectOutputStream(socket.getOutputStream());
            for(int i = 0; i < 10; i++) {

                //fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Bench.BenchTest test = bench.start();

                sendRequest();

                test.stop();

                sleep((int) (Math.random() * 100));

                // fromServer.close();
            }
        }finally {
            toServer.close();
        }
    }

    private void sendUDP(){

        try{
            DatagramSocket Socket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName("localhost");


            byte[] incomingData = new byte[1024];

            CarDataPackage carDataPackage = getData();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outputStream);
            os.writeObject(carDataPackage);
            byte[] data = outputStream.toByteArray();
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, 9997);

            Socket.send(sendPacket);
            System.out.println("Message sent from client");

        } catch (SocketException e) {
            bench.isFailed();
            e.printStackTrace();
        } catch (UnknownHostException e) {
            bench.isFailed();
            e.printStackTrace();
        } catch (IOException e) {
            bench.isFailed();
            e.printStackTrace();
        }


    }


    public CarDataPackage getData(){
        return new CarDataPackage(getCarId(), getSpeed(), getDestination(), getPosition());
    }

    private boolean sendRequest() throws IOException {
        boolean holdTheLine = true;          // Connection exists


        toServer.writeObject(getData());
        /*
        if (line.equals(".")) {              // Does the user want to end the session?
            holdTheLine = false;
        }
        */
        return holdTheLine;
    }

    private static void receiveResponse() throws IOException {
      //  user.output("Server answers: " +
        //        new String(fromServer.readLine()) + '\n');
    }

    public void run(){

        try {

            for(int i = 0; i < 10; i++) {

                //fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Bench.BenchTest test = bench.start();

                sendUDP();

                test.stop();

                sleep((int)(Math.random()*1000));

                // fromServer.close();
            }

           // startClient();
        } catch (Exception e) {
           // e.printStackTrace();
        }

    }

}
