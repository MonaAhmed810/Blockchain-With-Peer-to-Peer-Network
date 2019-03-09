package blockChain;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Server   {
    public String INET_ADDR = "224.0.0.1";
    public int PORT = 8888;


    public void run(String msg) throws UnknownHostException {

        // Get the address that we are going to connect to.
        InetAddress addr = InetAddress.getByName(INET_ADDR);
        System.out.println("here1");


        // Open a new DatagramSocket, which will be used to send the data.
        try (DatagramSocket serverSocket = new DatagramSocket()) {

            // Create a packet that will contain the data
            // (in the form of bytes) and send it.
            DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(),
                    msg.getBytes().length, addr, PORT);
            serverSocket.send(msgPacket);

            System.out.println("Server sent packet with msg: " + msg);


        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }//end fun run

    public void preparemessage(Block block) throws UnknownHostException {
        System.out.println("here4");

        String bLock ="Block|";
        bLock+=block.getTimeStamp();
        bLock+="|";
        bLock+=block.getData();
        bLock+="|";
        bLock+=block.getHash();
        bLock+="|";
        bLock+=block.getPreviousHash();
        bLock+="|";
        bLock+=block.getNonce();
        bLock+="|";
        run(bLock);

    }

}
