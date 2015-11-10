package cars;


import communication.UserInterface;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class AutonomCar extends CarBaseClass {

    BufferedReader fromServer;
    ObjectOutputStream toServer;
    UserInterface user = new UserInterface();

    public AutonomCar() {
        start();
    }

    public void printId(){
        System.out.println("My id is: " + getCarId());
    }

    private void startClient() throws Exception{

        SocketAddress addr = new InetSocketAddress("localhost", 9998);

        try (Socket socket = new Socket()){
            socket.connect(addr);
            toServer = new ObjectOutputStream(socket.getOutputStream());
            for(int i = 0; i < 10; i++) {

                //fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                sendRequest();
                sleep((int)(Math.random()*100));

                // fromServer.close();
            }
        }finally {
            toServer.close();
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
            startClient();
        } catch (Exception e) {
           // e.printStackTrace();
        }

    }

}
