package blockChain;

import java.util.Date;

public class Block {
    private static int difficulty = 5;
    private long timeStamp;
    private String data;
    private String hash;
    private String previousHash;
    private int nonce;

    
    public Block() {}
      public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.nonce = 0;
        this.hash = calculateValidHash();
        System.out.println("Block Mined!!! : " + hash);

    }
    public static int getDifficulty() {
		return difficulty;
	}

	public static void setDifficulty(int difficulty) {
		Block.difficulty = difficulty;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getPreviousHash() {
		return previousHash;
	}

	public void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
	}

	public int getNonce() {
		return nonce;
	}

	public void setNonce(int nonce) {
		this.nonce = nonce;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	 public static void setDifficulty(int difficulty) {
        Block.difficulty = difficulty;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public static int getDifficulty() {
        return difficulty;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getData() {
        return data;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public int getNonce() {
        return nonce;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }

    public String getHash() {
        return hash;
    }

    private String calculateHash() {
        String tempHash = StringUtil.applySha256(
                previousHash + Long.toString(timeStamp) + Integer.toString(nonce) + data);
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
