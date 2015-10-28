package communication;

// 22.10. 10

/**
 * @author Peter Altenberd
 * (Translated into English by Ronald Moore)
 * Computer Science Dept.                   Fachbereich Informatik
 * Darmstadt Univ. of Applied Sciences      Hochschule Darmstadt
 */

import cars.CarDataPackage;

import java.io.*;
import java.net.*;

public class EchoService extends Thread {
    Socket client;

    public EchoService(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        String line;
        BufferedReader fromClient;
        InputStream in;
        ObjectInputStream inputStream;
        ObjectOutputStream toClient;
        boolean verbunden = true;

        System.out.println("Thread started: " + this); // Display Thread-ID

        try {

            inputStream = new ObjectInputStream(client.getInputStream());

            fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));


            toClient = new ObjectOutputStream(client.getOutputStream()); // TO Client
            while (verbunden) {     // repeat as long as connection exists
                //line = fromClient.readLine();              // Read Request
                CarDataPackage dataPackage = (CarDataPackage) inputStream.readObject();
                System.out.println("Received: " + dataPackage.getId());
               // if (line.equals(".")) verbunden = false;   // Break Conneciton?
                //else
                toClient.writeBytes(dataPackage.getId().toUpperCase() + '\n'); // Response
            }
            fromClient.close();
            toClient.close();
            client.close(); // End
            System.out.println("Thread ended: " + this);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
