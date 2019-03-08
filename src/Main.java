import blockChain.*;
import com.google.gson.GsonBuilder;

public class Main {

    public static void main(String[] args) {
        // Test the blockChain
        BlockChain blockChain = BlockChain.getInstance();

        String news1 = "Mona travels to Alex";
        String news2 = "Yosra travels to Aswan";
        String news3 = "Nour travels to Aswan";

        System.out.println("Trying to Mine block 1... ");
        blockChain.addBlock(news1);
        System.out.println("Trying to Mine block 2... ");
        blockChain.addBlock(news2);
        System.out.println("Trying to Mine block 3... ");
        blockChain.addBlock(news3);

        System.out.println("\nBlockChain is Valid: " + blockChain.isChainValid());

        System.out.println("\nThe Block Chain: ");
        String blockChainJSON = new GsonBuilder().setPrettyPrinting().create().toJson(blockChain);
        System.out.println(blockChainJSON);
    }
}
