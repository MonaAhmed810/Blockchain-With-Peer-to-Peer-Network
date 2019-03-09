package blockChain;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Peer {
    private Client client;
    private Server server;

    public Peer() throws UnknownHostException {
        try {
            server = new Server();
            client = new Client();
        } catch (IOException e) {
            e.printStackTrace();
        }
        client .start();
        //  server.prepareMassage("Hi");
    }

    public void discover() {
        //   server.prepareMassage("Hi");
    }

    public void terminate() {
        client.interrupt();
    }

    public String getAddress() throws UnknownHostException {
        return InetAddress.getByName("localhost").toString();
    }

    public void Block_to_server() throws UnknownHostException {
        System.out.println("here3");

        BlockChain BC=BlockChain.getInstance();
        server.preparemessage(BC.getLastBlock());

    }
    public void updateBlockChain() {
        //TODO: make the peer get you the longest chain
        
    }
}
