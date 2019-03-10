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
    }

    public void peerdiscover()throws IOException {
        System.out.println("here1");
          server.discovery();
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
    public void updateBlockChain() throws UnknownHostException{
        server.requestBlockChain();

    }
}
