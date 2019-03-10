package blockChain;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Server   {
	public String INET_ADDR = "224.0.0.1";
    public int PORT = 8888;
 
    
    public void run(String msg) throws UnknownHostException {
    	System.out.print(msg);
        InetAddress addr = InetAddress.getByName(INET_ADDR);
        try (DatagramSocket serverSocket = new DatagramSocket()) {
                DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(),
                        msg.getBytes().length, addr, PORT);
                
                serverSocket.send(msgPacket);     
        } 
        catch (IOException ex) {
            ex.printStackTrace();
        } 
             	
    	
    }//end fun run
     
    public void preparemessage(Block block) throws UnknownHostException {
    	
    	String bLock ="Block|";
    	bLock+=block.getTimeStamp();
    	bLock+="|";
    	bLock+=block.getData();
    	bLock+="|";
    	bLock+=block.getHash();
    	bLock+="|";
    	bLock+=block.getPreviousHash();
    	bLock+="|";
    	bLock+=block.getNonce();
    	bLock+="|";
    	run(bLock);
   
    }

	public void requestBlockChain() throws UnknownHostException {
	//	String requestChain="Chain";
		run("Chain");
		
	}
 
}