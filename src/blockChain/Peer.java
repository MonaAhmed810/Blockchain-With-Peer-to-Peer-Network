package blockChain;

import java.io.IOException;

public class Peer {
    private Client client;
    private Server server;

    public Peer() throws IOException {
        server = new Server();
        client = new Client();
        client.start();
    }

    public void peerDiscover() throws IOException {
        server.discovery();
    }

    public void terminate() {
        client.interrupt();
    }

    public void Block_to_server() throws IOException {
        BlockChain blockChain = BlockChain.getInstance();
        server.prepareMessage(blockChain.getLastBlock());
    }

    public void updateBlockChain() throws IOException {
        server.requestBlockChain();

    }
}
