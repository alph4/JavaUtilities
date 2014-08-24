package g21.util.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Server created to handle a single client. He listenes to a port and reacts to the input from the client.
 * 
 * @author Gram21 
 */
public class G21Server extends Thread {
	private ServerSocket serverSocket;
	private int port;
	
	private static final int SO_TIMEOUT = 120000; //in milliseconds. 120000 = 2 minutes
	
	/**
	 * Constructor of this server getting a port to listen to
	 * @param port Port the server should listen to
	 * @throws IOException Might throw IOException
	 */
	public G21Server(int port) throws IOException {
		if (port < 0) throw new IllegalArgumentException();
		
		this.port = port;
		this.serverSocket = new ServerSocket(this.port);
		serverSocket.setSoTimeout(SO_TIMEOUT);
	}
	
	/**
	 * Main method for using this server. If you want to have a separate main class, cut this out.
	 * @param args containing the port as first argument
	 */
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Need the port, the server should listen to, as argument! _Ivan_, you did it wrong! ;-)");
			return;
		}
		
		int port = stringToInt(args[0]);
		if (port < 0) {
			System.out.println("Error! Invalid Port!");
			return;
		}
		
		try {
			Thread t = new G21Server(port);
			t.start();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	
	@Override
	public void run() {
		while(true) {
			try {
				//Start server and wait for connection
				System.out.println("Waiting on port " + serverSocket.getLocalPort());
				Socket server = serverSocket.accept();
				System.out.println("Connection to " + server.getRemoteSocketAddress());
				
				//after connected, get input stream
				DataInputStream in = new DataInputStream(server.getInputStream());
				// response stream (if needed)
				DataOutputStream out = new DataOutputStream(server.getOutputStream());
				
				//handle input
				boolean connected = true;
				while(connected) {
					String input = in.readUTF();
					System.out.println(input); //either if you want to print it out or for debugging. else comment it out
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
				
				//finally close server (adapt when server closing is needed later or at another stage or time
				server.close();
				
				
			} catch (SocketTimeoutException ste) {
				System.out.println("Socket Timeout!");
				break;
			} catch (IOException ioe) {
				ioe.printStackTrace();
				break;
			}
		}
	}
	
	/**
	 * Casts a number as string to a valid port value
	 * @param str String to be cast
	 * @return int value of the String number
	 */
	private static int stringToInt(String str) {
		if (str == null || "".equals(str)) {
			return -1;
		}
		
		int returnValue = -1;
		
		try {
			returnValue = Integer.parseInt(str);
		
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		if (returnValue < 0 || returnValue > 99999) {
			return -1;
		}
		
		return returnValue;
	}
}
