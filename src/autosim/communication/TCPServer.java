package autosim.communication;

// 22. 10. 10

/**
 * @author Peter Altenberd
 * (Translated into English by Ronald Moore)
 * Computer Science Dept.                   Fachbereich Informatik
 * Darmstadt Univ. of Applied Sciences      Hochschule Darmstadt
 */

import java.io.*;
import java.net.*;

public class TCPServer {

    static String line;
    static BufferedReader fromClient;
    static DataOutputStream toClient;

    public static void main(String[] args) throws Exception {
        ServerSocket contactSocket = new ServerSocket(9999);
        while (true) {                            // Handle connection request
            Socket client = contactSocket.accept(); // creat autosim.communication socket
            System.out.println("Connection with: " + client.getRemoteSocketAddress());
            handleRequests(client);
        }
    }

    static void handleRequests(Socket s) {
        try {
            fromClient = new BufferedReader(        // Datastream FROM Client
                    new InputStreamReader(s.getInputStream()));
            toClient = new DataOutputStream(
                    s.getOutputStream());                 // Datastream TO Client
            while (receiveRequest()) {              // As long as connection exists
                sendResponse();
            }
            fromClient.close();
            toClient.close();
            s.close();
            System.out.println("Session ended, Server remains active");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static boolean receiveRequest() throws IOException {
        boolean holdTheLine = true;
        System.out.println("Received: " + (line = fromClient.readLine()));
        if (line.equals(".")) {                         // End of session?
            holdTheLine = false;
        }
        return holdTheLine;
    }

    static void sendResponse() throws IOException {
        toClient.writeBytes(line.toUpperCase() + '\n');  // Send answer
    }
}
