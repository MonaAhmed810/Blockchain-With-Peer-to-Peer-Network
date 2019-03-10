package blockChain;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class Server {
    public String INET_ADDR = "224.0.0.1";
    public int PORT = 8888;
  
    public void run(String msg) throws UnknownHostException {
    	System.out.print(msg);
        InetAddress addr = InetAddress.getByName(INET_ADDR);

        // Open a new DatagramSocket, which will be used to send the data.
        try (DatagramSocket serverSocket = new DatagramSocket()) {

            // Create a packet that will contain the data
            // (in the form of bytes) and send it.
            DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(),
                    msg.getBytes().length, addr, PORT);
            serverSocket.send(msgPacket);
            //System.out.println("in run");
            System.out.println("Server sent packet with msg: " + msg);


        } catch (IOException ex) {
            ex.printStackTrace();
        } 
    	
    }//end fun run
     
    public void preparemessage(Block block) throws UnknownHostException {
        String bLock = "Block|";
        bLock += block.getTimeStamp();
        bLock += "|";
        bLock += block.getData();
        bLock += "|";
        bLock += block.getHash();
        bLock += "|";
        bLock += block.getPreviousHash();
        bLock += "|";
        bLock += block.getNonce();
        bLock += "|";
        run(bLock);

    }

    public void discovery() throws IOException {
        DatagramSocket socket = null;
        socket = new DatagramSocket();
        socket.setBroadcast(true);

        String broadcastMessage = "Hi";
        byte[] buffer = broadcastMessage.getBytes();
        InetAddress  broadcastIP = InetAddress.getByName("225.225.225.225");
        DatagramPacket packet= new DatagramPacket(buffer, buffer.length, broadcastIP, 8888);
        socket.send(packet);
    }

    public void requestBlockChain() throws UnknownHostException {
        String requestChain = "Chain";
        run(requestChain);
    }
}

