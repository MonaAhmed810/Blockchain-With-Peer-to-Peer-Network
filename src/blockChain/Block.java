package blockChain;

import java.util.Date;

public class Block {
    private static int difficulty;
    private long timeStamp;
    private Transaction transaction;
    private String hash;
    private String previousHash;
    private int nonce;

    public Block(Transaction transaction, String previousHash) {
        this.transaction = transaction;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.nonce = 0;
        this.hash = calculateValidHash();
        System.out.println("Block Mined!!! : " + hash);

    }

    public static void setDifficulty(int x) {
        difficulty = x;
    }

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    private String calculateHash() {
        String tempHash = StringUtil.applySha256(
                previousHash + Long.toString(timeStamp) + Integer.toString(nonce) + transaction);
        return tempHash;
    }

    private boolean isHashValid(String tempHash) {
        String target = StringUtil.getDifficultyString(difficulty);
        return tempHash.substring(0, difficulty).equals(target);
    }

    // Proof of work
    // its requirements : hashCode has a specific prefix ('0' * difficulty)
    private String calculateValidHash() {
        String calculatedHash = calculateHash();
        while (!isHashValid(calculatedHash)) {
            nonce++;
            calculatedHash = calculateHash();
        }
        return calculatedHash;
    }

    public boolean isBlockValid(Block previousBlock) {
        if (!hash.equals(calculateValidHash())) {
            System.out.println("Current Hash not valid");
            return false;
        }
        if (!previousHash.equals(previousBlock.getHash())) {
            System.out.println("Previous Hash not Valid");
            return false;
        }
        return true;
    }
}
