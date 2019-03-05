package blockChain;

public class Transaction {
    private String sourceName;
    private String destinationName;
    private int value;

    public Transaction(String sourceName, String destinationName, int value) {
        this.sourceName = sourceName;
        this.destinationName = destinationName;
        this.value = value;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
