package blockChain;

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
    public void addBlock(String data) {
        if (blocks.isEmpty())
            createGenesisBlock(data);
        else {
            Block lastBlock = blocks.lastElement();
            Block newBlock = new Block(data, lastBlock.getHash());
            blocks.add(newBlock);
        }
    }

    public Boolean isChainValid() {
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
    public void addReceivedBlock(Block block){
        blocks.add(block);
    }
    public Block getLastBlock() {
		return blocks.get(blocks.size()-1);
    }
    public void freeBlocks() {
    	blocks.clear();
    }
    
}
