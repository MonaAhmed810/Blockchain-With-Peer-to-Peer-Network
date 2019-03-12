package blockChain;

import com.google.gson.GsonBuilder;

import java.util.Vector;

public class BlockChain {
    private static BlockChain blockChain;
    public Vector<Block> blocks;

    private BlockChain() {
        blocks = new Vector<>();
    }

    public static BlockChain getInstance() {
        if (blockChain == null)
            blockChain = new BlockChain();
        return blockChain;
    }

    private void createGenesisBlock(String data) {
        Block genesisBlock = new Block(data, "0");
        blocks.add(genesisBlock);
    }

    // BlockChain Mining
    // The process of adding a block to the chain
    public void createBlock(String data) {
        if (blocks.isEmpty())
            createGenesisBlock(data);
        else {
            Block lastBlock = blocks.lastElement();
            Block newBlock = new Block(data, lastBlock.getHash());
            blocks.add(newBlock);
        }
    }

    public Boolean isChainValid(Vector<Block> blocks) {
        if (!blocks.isEmpty()) {
            Block previousBlock = blocks.get(0);
            for (int i = 1; i < blocks.size(); i++) {
                if (!blocks.get(i).isBlockValid(previousBlock))
                    return false;
                previousBlock = blocks.get(i);
            }
        }
        return true;
    }

    public void addBlock(Block block) {
        blocks.add(block);
    }

    public Block getLastBlock() {
        return blocks.lastElement();
    }

    public int getSize() {
        return blocks.size();
    }

    public void print(){
        System.out.println("\nThe Block Chain: ");
        System.out.println("size: " + blockChain.getSize());
        String blockChainJSON = new GsonBuilder().setPrettyPrinting().create().toJson(blockChain);
        System.out.println(blockChainJSON);
    }

}
