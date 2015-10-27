package cars;


import cars.CarBaseClass;
import communication.UserInterface;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class AutonomCar extends CarBaseClass {

    static String line;
    static Socket socket;
    static BufferedReader fromServer;
    static DataOutputStream toServer;
    static UserInterface user = new UserInterface();

    public AutonomCar() {
        start();
    }

    public void printId(){
        System.out.println("My id is: " + getCarId());
    }


    private void startClient() throws Exception{

        socket = new Socket("localhost", 9998);
        toServer = new DataOutputStream(     // Datastream FROM Server
                socket.getOutputStream());
        fromServer = new BufferedReader(     // Datastream TO Server
                new InputStreamReader(socket.getInputStream()));

        for(int i = 0; i < 10; i++) {
            try {
                sleep((int)(Math.random()*2000));
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


    private boolean sendRequest() throws IOException {
        boolean holdTheLine = true;          // Connection exists


        toServer.writeBytes(getCarId() + '\n');
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
            e.printStackTrace();
        }

    }

}
