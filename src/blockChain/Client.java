package blockChain;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Vector;

public class Client extends Thread {
    BlockChain bc = BlockChain.getInstance();
    MulticastSocket socket = null;
    public InetAddress address = InetAddress.getByName("224.0.0.1");
    public static int PORT = 8888;

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
                System.out.print("message: ");
                System.out.println(new String(buffer));

                String received = new String(packet.getData(), 0, packet.getLength());
                if (received.equals("Hi")) {
                    String hostInfo="NewsGroup|224.0.0.1";
                    byte[] buf2=hostInfo.getBytes();
                    DatagramPacket hostPacket = new DatagramPacket(buf2, buf2.length, packet.getAddress(), 8888);
                    DatagramSocket datagramSocket = new DatagramSocket();
                    datagramSocket.send(hostPacket);
                }

                else if (received.equals("Chain")) {
                    //put all blocks in one string to send it for the server who request the chain
                    String concatenateAllBlocks = "PeerChain|";

                    for (int i = 0; i < bc.blocks.size(); i++) {
                        concatenateAllBlocks += bc.blocks.get(i).getTimeStamp() + "-";
                        concatenateAllBlocks += bc.blocks.get(i).getData() + "-";
                        concatenateAllBlocks += bc.blocks.get(i).getHash() + "-";
                        concatenateAllBlocks += bc.blocks.get(i).getPreviousHash() + "-";
                        concatenateAllBlocks += bc.blocks.get(i).getNonce() + "|";
                        byte[] ReplyToTheSender = concatenateAllBlocks.getBytes();
                        DatagramSocket serverSocket = new DatagramSocket();
                        //InetAddress addr = packet.getAddress();
                        InetAddress addr = InetAddress.getByName("224.0.0.1");
                        DatagramPacket msgPacket = new DatagramPacket(ReplyToTheSender, ReplyToTheSender.length, addr, 8888);
                        serverSocket.send(msgPacket);
                    }
                }
                else {
                    String[] splited = received.split("\\|");
                    if (splited[0].equals("Block")) {
                        Block block = new Block();
                        block.setTimeStamp(Long.parseLong(splited[1]));
                        block.setData(splited[2]);
                        block.setHash(splited[3]);
                        block.setPreviousHash(splited[4]);
                        block.setNonce(Integer.parseInt(splited[5]));
                        BlockChain bc = BlockChain.getInstance();
                        //if(block.isBlockValid(block))
                        bc.addReceivedBlock(block);

                    } else if (splited[0].equals("PeerChain")) {
                        //System.out.println("hello2");
                        Vector<Block> newblock = new Vector<>();
                        if (splited.length - 1 > bc.blocks.size()) {
                            for (int i = 1; i < splited.length; i++) {
                                String[] spllited = splited[i].split("-");//data for each block

                                Block b = new Block();
                                b.setTimeStamp(Long.parseLong(spllited[0]));
                                b.setData(spllited[1]);
                                b.setHash(spllited[2]);
                                b.setPreviousHash(spllited[3]);
                                b.setNonce(Integer.parseInt(spllited[4]));
                                newblock.add(b);

                            }
                            bc.blocks = newblock;
                        }
                    }// end of peerchain
                    else if(splited[0].equals("NewsGroup")){
                        address = InetAddress.getByName(splited[1]);
                        socket.joinGroup(address);
                        socket.setReuseAddress(true);
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