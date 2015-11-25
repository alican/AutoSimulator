package cars;


import communication.UserInterface;
import models.Bench;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.io.*;
import java.net.*;

public class AutonomCar extends CarBaseClass {

    public static String address;
    public static int port;

    BufferedReader fromServer;
    ObjectOutputStream toServer;
    UserInterface user = new UserInterface();
    SocketAddress addr = new InetSocketAddress(address, port);
    XmlRpcClient client;

    static Bench bench;

    public AutonomCar() {
        bench = Bench.getInstance();
        start();

    }

    public void printId(){
        System.out.println("My id is: " + getCarId());
    }


    public void update(){
        xmlRpcRequest();
    }

    void xmlRpcRequest(){

        Object[] params = new Object[]{new Integer(3334), new Integer(9)};

        Integer result = null;
        try {

            result = (Integer) client.execute("Calculator.add", params);


        } catch (XmlRpcException e) {
            e.printStackTrace();
        }
        System.out.println("Add Result = " + result );

    }

    public void startUDPClient() throws Exception{
        for(int i = 0; i < 10; i++) {

            Bench.BenchTest test = bench.start();

            sendUDPRequest();

            test.stop();

            sleep((int)(Math.random()*1000));

        }
    }

    private void startTCPClient() throws Exception{


        try (Socket socket = new Socket()){
            socket.connect(addr);
            toServer = new ObjectOutputStream(socket.getOutputStream());
            for(int i = 0; i < 10; i++) {

                //fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Bench.BenchTest test = bench.start();

                sendTCPRequest();

                test.stop();

                sleep((int) (Math.random() * 100));

                // fromServer.close();
            }
        }finally {
            toServer.close();
        }
    }

    private void sendUDPRequest(){

        try{
            DatagramSocket Socket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName(address);


            byte[] incomingData = new byte[1024];

            CarDataPackage carDataPackage = getData();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outputStream);
            os.writeObject(carDataPackage);
            byte[] data = outputStream.toByteArray();
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, port);

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

    private boolean sendTCPRequest() throws IOException {
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
            //startTCPClient();
            init_xmprpc();
           // startUDPClient();
        } catch (Exception e) {
           // e.printStackTrace();
        }

        for(int i = 0; i < 10; i++) {
            update();

        }

        }

    private void init_xmprpc() {

        try {
            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
            config.setServerURL(new URL("http://127.0.0.1:8080/xmlrpc"));
            client = new XmlRpcClient();
            client.setConfig(config);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

}
