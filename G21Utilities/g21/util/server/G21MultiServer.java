package g21.util.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Class for a Multi-Client server, that opens a thread for each connection and can handle multiple clients.
 * 
 * @author Gram21 
 */
public class G21MultiServer implements Runnable {
	
	private int port;
	private Thread runningThread = null;
	private ServerSocket serverSocket = null;
	private boolean running = true;
	private static final int TIMEOUT = 120000; //in milliseconds. 120000 = 2 minutes
	
	/**
	 * Constructor of this mutli-client Server
	 * @param port Port, the server should listen to
	 */
	public G21MultiServer(int port) {
		this.port = port;
	}

	/**
	 * Main Method of this server, starting it and stopping it after some time.
	 * @param args Needs 1 argument, the port on which this server is listening
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
		
		//start server
		G21MultiServer mult_ivan_ = new G21MultiServer(port);
		new Thread(mult_ivan_).start();
		
		//close server after some time. if you don't want to stop this in this way
		//delete the following things and adapt the code the way you want to handle this
		try {
			Thread.sleep(TIMEOUT);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		System.out.println("Halting..");
		mult_ivan_.stop();	
	}
	
	@Override
	public void run() {
		synchronized(this) {
			this.runningThread = Thread.currentThread();
		}
		
		//open the socket
		try {
			this.serverSocket = new ServerSocket(this.port);
		} catch (IOException ioe) {
			throw new RuntimeException("Unable to open server with that port", ioe);
		}
		
		while(running) {
			Socket clientSocket = null;
			try {
				System.out.println("Waiting on port " + serverSocket.getLocalPort());
				clientSocket = this.serverSocket.accept();
			} catch(IOException ioe) {
				if (!running) {
					System.out.println("Server stopped.");
					return;
				}
				throw new RuntimeException("Cannot connect client", ioe);
			}
			new Thread(new G21MultiServerWorker(clientSocket, "G21MultiServer")).start();
		}
		System.out.println("Server stopped.");
	}
	
	
	
	/**
	 * Method to stop the server
	 */
	public synchronized void stop() {
		this.running = false;
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			throw new RuntimeException("Cannot close server", e);
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
