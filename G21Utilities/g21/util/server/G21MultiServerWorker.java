package g21.util.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * This is the Worker for the multi-client server. This worker handles one connection 
 * and reacts to input from the clients
 * 
 * @author Gram21 
 */
public class G21MultiServerWorker implements Runnable {
	private Socket clientSocket = null;
	private String serverText = null;

	/**
	 * Constructs a Worker for the multi-client server.
	 * @param clientSocket The servers client socket.
	 * @param serverText The server Text
	 */
	public G21MultiServerWorker(Socket clientSocket, String serverText) {
		this.clientSocket = clientSocket;
		this.serverText = serverText;
	}
	
	@Override
	public void run() {
		try {
			//after connected, get input stream and output stream
			DataInputStream in = new DataInputStream(clientSocket.getInputStream());
			DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
			
			//handle input
			boolean connected = true;
			while(connected) {
				String input = in.readUTF();
				System.out.println(">_" + input); //either if you want to print it out or for debugging. else comment it out
				// DO SOMETHING WITH INPUT like following
				switch(input) {
					case "quit" :
						out.writeUTF("Diconnecting from server! Goodbye! \n");
						connected = false;
						break;
					case "ping" :
						out.writeUTF("pong \n");
						break;
					default :
						out.writeUTF("Couldn't handle input! \n");		
				}
				
				//handle more output:
				//out.writeUTF("TEXT \n")
			}
			
			//disconnect this
			in.close();
			out.close();
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
	}
	
}
