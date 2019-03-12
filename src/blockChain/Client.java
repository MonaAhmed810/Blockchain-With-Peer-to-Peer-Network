package blockChain;

import java.io.IOException;
import java.net.*;
import java.util.Vector;

public class Client extends Thread {
    private boolean groupJoined = false;
    private static int PORT = 8888;
    private String groupAddress = "224.0.0.1";
    BlockChain blockChain = BlockChain.getInstance();
    MulticastSocket socket;

    Client() throws IOException {
        socket = new MulticastSocket(PORT);
    }

    private void joinGroup() throws IOException {
        InetAddress address = InetAddress.getByName(groupAddress);
        if (!groupJoined) {
            socket.joinGroup(address);
            socket.setReuseAddress(true);
            groupJoined = true;
            System.out.println("join to News Group");
        }
    }

    @Override
    public void run() {
        DatagramPacket packet;
        try {
            while (true) {
                byte[] buffer = new byte[1000];
                packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Just received packet from " + packet.getSocketAddress() + " " + received);
                if (received.equals("HI")) {
                    System.out.println("Receive discovery message");
                    String hostInfo = "NewsGroup|224.0.0.1";
                    byte[] hostBuffer = hostInfo.getBytes();
                    DatagramPacket hostPacket = new DatagramPacket(hostBuffer, hostBuffer.length, packet.getAddress(), PORT);
                    DatagramSocket datagramSocket = new DatagramSocket();
                    datagramSocket.send(hostPacket);
                    System.out.println("SEND");
                } else if (received.equals("Request BlockChain")) {
                    String concatenateAllBlocks = "PeerChain|";
                    for (int i = 0; i < blockChain.blocks.size(); i++) {
                        concatenateAllBlocks += blockChain.blocks.get(i).getTimeStamp() + "-";
                        concatenateAllBlocks += blockChain.blocks.get(i).getData() + "-";
                        concatenateAllBlocks += blockChain.blocks.get(i).getHash() + "-";
                        concatenateAllBlocks += blockChain.blocks.get(i).getPreviousHash() + "-";
                        concatenateAllBlocks += blockChain.blocks.get(i).getNonce() + "|";
                    }
                    byte[] blockChainBuffer = concatenateAllBlocks.getBytes();
                    DatagramSocket serverSocket = new DatagramSocket();
                    InetAddress address = InetAddress.getByName(groupAddress);
                    DatagramPacket msgPacket = new DatagramPacket(blockChainBuffer, blockChainBuffer.length, address, PORT);
                    serverSocket.send(msgPacket);
                } else {
                    String[] splited = received.split("\\|");
                    if (splited[0].equals("Block")) {
                        Block block = new Block();
                        block.setTimeStamp(Long.parseLong(splited[1]));
                        block.setData(splited[2]);
                        block.setHash(splited[3]);
                        block.setPreviousHash(splited[4]);
                        block.setNonce(Integer.parseInt(splited[5]));
                        if (blockChain.getSize() == 0 || block.isBlockValid(blockChain.getLastBlock()))
                            blockChain.addBlock(block);

                    } else if (splited[0].equals("PeerChain")) {
                        Vector<Block> newblocks = new Vector<>();
                        if (splited.length - 1 > blockChain.blocks.size()) {
                            for (int i = 1; i < splited.length; i++) {
                                String[] spllited = splited[i].split("-");//data for each block
                                Block b = new Block();
                                b.setTimeStamp(Long.parseLong(spllited[0]));
                                b.setData(spllited[1]);
                                b.setHash(spllited[2]);
                                b.setPreviousHash(spllited[3]);
                                b.setNonce(Integer.parseInt(spllited[4]));
                                newblocks.add(b);
                            }
                            if (blockChain.isChainValid(newblocks))
                                blockChain.blocks = newblocks;
                        }
                    } else if (splited[0].equals("NewsGroup")) {
                        groupAddress = splited[1];
                        joinGroup();
                    }
                }
                if (received.equals("No more news. Goodbye.")) break;
            }
            socket.leaveGroup(InetAddress.getByName(groupAddress));
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

