
import blockChain.BlockChain;
import blockChain.Peer;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        BlockChain blockChain = BlockChain.getInstance();

//        String news1 = "Mona travels to Alex";
//        String news2 = "Yosra travels to Aswan";
//        String news3 = "Nour travels to Aswan";
//        System.out.println("Trying to Mine block 1... ");
//        blockChain.createBlock(news1);
//        System.out.println("Trying to Mine block 2... ");
//        blockChain.createBlock(news2);
//        System.out.println("Trying to Mine block 3... ");
//        blockChain.createBlock(news3);
//        System.out.println("\nBlockChain is Valid: " + blockChain.isChainValid(blockChain.blocks));

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