package g21.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Client to connect to a server
 * 
 * @author Gram21 
 */
public class G21Client {
	
	/**
	 * Main Method, that starts the client
	 * @param args Needs 2 Arguments, the first one has to be the ServerName, the second one hast to be the port
	 */
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Invalid ammount of arguments. Need ServerName and Port! " 
					+ "Dont do it wrong again, _Ivan_! ;-)");
			return;
		}
		String serverName = args[0];
		int port = stringToInt(args[1]);
		if (port < 0) {
			System.out.println("Invalid port [2nd argument]");
			return;
		}
		try {
			//Connection
			System.out.println("Connecting to " + serverName + " on port " + port);
			Socket client = new Socket(serverName, port);
			System.out.println("Connected to " + client.getRemoteSocketAddress());
			
			//After connected, create stream for output to server
			DataOutputStream out = new DataOutputStream(client.getOutputStream());
			
			//write now to server. Here you can configure your behaviour for your client-server-interaction
			//this is just a simple ping pong example
			int c = 0;
			while(c < 1000) {
				System.out.println("Ping?");
				out.writeUTF("ping");
				c++;
				
				//get input from server
				DataInputStream in = new DataInputStream(client.getInputStream());
				String response = in.readUTF();
				System.out.println("> " + response);
				try {
				    Thread.sleep(1000);
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
			}
			
			
			
			
			//finnally close the client!
			out.writeUTF("quit");
			DataInputStream in = new DataInputStream(client.getInputStream());
			String response = in.readUTF();
			System.out.println(">_" + response);
			
			client.close();
			
		} catch(IOException ioe) {
			ioe.printStackTrace();
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
