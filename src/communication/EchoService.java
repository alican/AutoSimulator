package communication;

// 22.10. 10

/**
 * @author Peter Altenberd
 * (Translated into English by Ronald Moore)
 * Computer Science Dept.                   Fachbereich Informatik
 * Darmstadt Univ. of Applied Sciences      Hochschule Darmstadt
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
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
        DataOutputStream toClient;
        boolean verbunden = true;

        System.out.println("Thread started: " + this); // Display Thread-ID

        try {
            fromClient = new BufferedReader              // Datastream FROM Client
                    (new InputStreamReader(client.getInputStream()));
            toClient = new DataOutputStream(client.getOutputStream()); // TO Client
            while (verbunden) {     // repeat as long as connection exists
                line = fromClient.readLine();              // Read Request
                System.out.println("Received: " + line);
                if (line.equals(".")) verbunden = false;   // Break Conneciton?
                else toClient.writeBytes(line.toUpperCase() + '\n'); // Response
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
