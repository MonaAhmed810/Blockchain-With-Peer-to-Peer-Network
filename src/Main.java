import blockChain.*;
import com.google.gson.GsonBuilder;

public class Main {

    public static void main(String[] args) {
        // Test the blockChain
        Block.setDifficulty(1);
        BlockChain blockChain = new BlockChain();

        Transaction transaction1 = new Transaction("Mona", "Yosra", 10);
        Transaction transaction2 = new Transaction("Yosra", "Nour", 15);
        Transaction transaction3 = new Transaction("Nour", "Mona", 5);

        System.out.println("Trying to Mine block 1... ");
        blockChain.addBlock(transaction1);
        System.out.println("Trying to Mine block 2... ");
        blockChain.addBlock(transaction2);
        System.out.println("Trying to Mine block 3... ");
        blockChain.addBlock(transaction3);

        System.out.println("\nBlockChain is Valid: " + blockChain.isChainValid());

        System.out.println("\nThe Block Chain: ");
        String blockChainJSON = new GsonBuilder().setPrettyPrinting().create().toJson(blockChain);
        System.out.println(blockChainJSON);
    }
}
