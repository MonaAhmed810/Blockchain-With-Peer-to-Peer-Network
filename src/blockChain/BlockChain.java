package blockChain;

import java.util.Vector;

public class BlockChain {
    private Vector<Block> blockChain;

    public BlockChain() {
        blockChain = new Vector<>();
    }

    private void createGenesisBlock(Transaction transaction) {
        Block genesisBlock = new Block(transaction, "0");
        blockChain.add(genesisBlock);
    }

    // BlockChain Mining
    // The process of adding a block to the chain
    public void addBlock(Transaction transaction) {
        if (blockChain.isEmpty())
            createGenesisBlock(transaction);
        else {
            Block lastBlock = blockChain.lastElement();
            Block newBlock = new Block(transaction, lastBlock.getHash());
            blockChain.add(newBlock);
        }
    }

    public Boolean isChainValid() {
        if (!blockChain.isEmpty()) {
            Block previousBlock = blockChain.get(0);
            for (int i = 1; i < blockChain.size(); i++) {
                if (!blockChain.get(i).isBlockValid(previousBlock))
                    return false;
                previousBlock = blockChain.get(i);
            }
        }
        return true;
    }
}
