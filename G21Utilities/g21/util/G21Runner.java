package g21.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Runner Class to provide methods to start programs and run things on the command line (shell)
 * @author Gram21
 *
 */
public final class G21Runner {
	
	/**
	 * Constructor. Shouldnt be instanced.
	 */
	private G21Runner() {
		//keep private, no instances
	}
	
	/**
	 * This method executes the provided command on the respective command line of the OS. You can choose to give the output on your console via the boolean.
	 * Warning: You have to be careful what to execute here.
	 * @param command command to be executed, e.g. "ping 127.0.0.1"
	 * @param printOutput Boolean to print the output if set to true. When false, nothing will be printed
	 */
	public static void exec(String command, boolean printOutput) {
		
		String windowsPrefix = "cmd /c ";
		
		Process process = null;
		if (isWindowsSystem()) {
			//Windows System Command
			try {
				process = Runtime.getRuntime().exec(windowsPrefix + command);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} else if (isUnixSystem()) {
			//For Unix Systems like Linux distributions like _Ivan_'s Linux Mint
			try {
				process = Runtime.getRuntime().exec(command);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (isMacSystem()) {
			//For Mac Systems
			try {
				process = Runtime.getRuntime().exec(command);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Unknown System.");
			System.exit(1); //Maybe you want another behaviour here
		}
		
		//for printing purposes
		if(printOutput && process != null) {
			String line;
			Reader r = new InputStreamReader(process.getInputStream());
		    BufferedReader in = new BufferedReader(r);
		    try {
				while((line = in.readLine()) != null) System.out.println(line);
				in.close();
			} catch (IOException e) {
				//may want to handle this differently than the following:
				e.printStackTrace();
				System.exit(1);
			}
		    
		}

	}
	
	
	/**
	 * @return returns whether its a Windows system or not
	 */
	static boolean isWindowsSystem() {
		String osName = System.getProperty("os.name").toLowerCase();
		return (osName.indexOf("windows") >= 0);
	}
	
	/**
	 * @return  returns whether its a Unix (linux) system or not
	 */
	static boolean isUnixSystem() {
		String osName = System.getProperty("os.name").toLowerCase();
		return (osName.indexOf("nix") >= 0 || osName.indexOf("nux") >= 0);
	}
	
	/**
	 * @return  returns whether its a Mac system or not
	 */
	static boolean isMacSystem() {
		String osName = System.getProperty("os.name").toLowerCase();
		return (osName.indexOf("mac") >= 0);
	}
}


