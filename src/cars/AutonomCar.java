package cars;


import communication.UserInterface;

import java.io.*;
import java.net.Socket;

public class AutonomCar extends CarBaseClass {

    static Socket socket;
    static BufferedReader fromServer;
    static ObjectOutputStream toServer;
    static UserInterface user = new UserInterface();

    public AutonomCar() {
        start();
    }

    public void printId(){
        System.out.println("My id is: " + getCarId());
    }

    private void startClient() throws Exception{

        socket = new Socket("localhost", 9998);
        toServer = new ObjectOutputStream(     // Datastream FROM Server
                socket.getOutputStream());
        fromServer = new BufferedReader(     // Datastream TO Server
                new InputStreamReader(socket.getInputStream()));

        for(int i = 0; i < 10; i++) {
            try {
                sleep((int)(Math.random()*10000));
            }
            catch(InterruptedException e) {

            }
            sendRequest();
        }
        /*
        while (sendRequest()) {              // Send requests while connected
            receiveResponse();                 // Process server's answer
        }
        */
        socket.close();
        toServer.close();
        fromServer.close();
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
        user.output("Server answers: " +
                new String(fromServer.readLine()) + '\n');
    }

    public void run(){

        try {
            startClient();
        } catch (Exception e) {
           // e.printStackTrace();
        }

    }

}
