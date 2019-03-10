import com.google.gson.GsonBuilder;
import blockChain.BlockChain;
import blockChain.Peer;
import java.util.Scanner;
import java.net.UnknownHostException;
public class Main {

    public static void main(String[] args) throws UnknownHostException{
        // Test the blockChain
        BlockChain blockChain = BlockChain.getInstance();

        String news1 = "Mona travels to Alex";
        String news2 = "Yosra travels to Aswan";
        String news3 = "Nour travels to Aswan";

        System.out.println("Trying to Mine block 1... ");
        blockChain.addBlock(news1);
       /* 
        System.out.println("Trying to Mine block 2... ");
        blockChain.addBlock(news2);
        System.out.println("Trying to Mine block 3... ");
        blockChain.addBlock(news3);
*/
        //System.out.println("\nBlockChain is Valid: " + blockChain.isChainValid());

        Peer peer = new Peer();
        Scanner in = new Scanner(System.in);
        System.out.println("Please select the operation you want to perform: ");
        int choice = 0;
        while (choice != 4) {
            System.out.println("1: to discover ");
            System.out.println("2: to add news");
            System.out.println("3: to update blockchain ");
            System.out.println("4: to terminate");
            choice = in.nextInt();
            switch (choice) {
                case 1: {
                    //peer.discover();
                    break;
                }
                case 2: {
                    System.out.println("please enter your news:");
                    String news = in.next();
                    blockChain.addBlock(news);
                    peer.Block_to_server();
                    break;
                }
                case 3: {
                    peer.updateBlockChain();
                    break;
                }
                case 4: {
                    peer.terminate();
                    
                    in.close();
                    break;
                }
            }
        }
        System.out.println("\nThe Block Chain: ");
        String blockChainJSON = new GsonBuilder().setPrettyPrinting().create().toJson(blockChain);
        System.out.println(blockChainJSON);
        blockChain.freeBlocks();
    }
}