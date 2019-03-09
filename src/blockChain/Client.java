package blockChain;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Client extends Thread {
    MulticastSocket socket = null;
    InetAddress address;

    Client() throws IOException {
        socket = new MulticastSocket(8888);
        address = InetAddress.getByName("224.0.0.1");
        socket.joinGroup(address);
        socket.setReuseAddress(true);

    }

    @Override
    public void run() {
        DatagramPacket packet;
        while (true) {
            byte[] buf = new byte[1000];
            packet = new DatagramPacket(buf, buf.length);
            try {
                byte[] buffer = new byte[1024];
                socket.receive(packet);
                System.out.println("Just received packet from " + packet.getSocketAddress());
                buffer = packet.getData();

                System.out.println(new String(buffer));

                DatagramPacket hostPacket = new DatagramPacket(InetAddress.getByName("localhost").getAddress(), InetAddress.getByName("localhost").getAddress().length, address, 8888);
                String received = new String(packet.getData(), 0, packet.getLength());
                if (received.equals("Hi")) {
                    DatagramSocket datagramSocket = new DatagramSocket();
                    datagramSocket.send(hostPacket);
                } else {
                    String[] splited = received.split("\\|");
                    if (splited[0].equals("Block")) {
                        Block block = new Block();
                        block.setTimeStamp(Long.parseLong(splited[1]));
                        block.setData(splited[2]);
                        block.setHash(splited[3]);
                        block.setPreviousHash(splited[4]);
                        block.setNonce(Integer.parseInt(splited[5]));
                        BlockChain bc = BlockChain.getInstance();
                        bc.addReceivedBlock(block);

                    } else if (splited[0].equals("Chain")) {

                    } else if (splited[0].equals("NewsBlockchain")) {

                    }

                }
                if (received.equals("No more news. Goodbye.")) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }//end of while(true)

        try {
            socket.leaveGroup(address);
        } catch (IOException e) {
            e.printStackTrace();
        }
        socket.close();
    }
}

