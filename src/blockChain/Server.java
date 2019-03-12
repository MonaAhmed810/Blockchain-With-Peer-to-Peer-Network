package blockChain;

import java.io.IOException;
import java.net.*;

public class Server {
    private static String groupAddress = "224.0.0.1";
    private static int PORT = 8888;

    public void run(String msg) throws IOException {
        System.out.print(msg);
        InetAddress address = InetAddress.getByName(groupAddress);
        DatagramSocket serverSocket = new DatagramSocket();
        DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length, address, PORT);
        serverSocket.send(msgPacket);
        System.out.println("Server sent packet with msg: " + msg);
    }

    public void prepareMessage(Block block) throws IOException {
        String blockString = "Block|";
        blockString += block.getTimeStamp();
        blockString += "|";
        blockString += block.getData();
        blockString += "|";
        blockString += block.getHash();
        blockString += "|";
        blockString += block.getPreviousHash();
        blockString += "|";
        blockString += block.getNonce();
        blockString += "|";
        run(blockString);
    }

    public void discovery() throws IOException {
        DatagramSocket socket = new DatagramSocket();
        socket.setBroadcast(true);
        String broadcastMessage = "HI";
        byte[] buffer = broadcastMessage.getBytes();
        InetAddress broadcastIP = InetAddress.getByName("255.255.255.255");
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, broadcastIP, PORT);
        socket.send(packet);
    }

    public void requestBlockChain() throws IOException {
        String requestChain = "Request BlockChain";
        run(requestChain);
    }
}

