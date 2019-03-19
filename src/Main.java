import blockChain.BlockChain;
import P2P.Peer;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        BlockChain blockChain = BlockChain.getInstance();
        Peer peer = new Peer();
        Scanner in = new Scanner(System.in);
        System.out.println("Please select the operation you want to perform: ");
        int choice = 0;
        while (choice != 5) {
            System.out.println("1: to discover ");
            System.out.println("2: to add news");
            System.out.println("3: to update blockchain ");
            System.out.println("4: to print");
            System.out.println("5: to terminate");
            choice = in.nextInt();
            switch (choice) {
                case 1: {
                    peer.peerDiscover();
                    break;
                }
                case 2: {
                    System.out.println("please enter your news:");
                    String news = in.next();
                    System.out.println("your news is: " + news);
                    blockChain.createBlock(news);
                    peer.Block_to_server();
                    break;
                }
                case 3: {
                    peer.updateBlockChain();
                    break;
                }
                case 4: {
                    blockChain.print();
                    break;
                }
                case 5: {
                    peer.terminate();
                    break;
                }
            }
        }
        in.close();
    }
}