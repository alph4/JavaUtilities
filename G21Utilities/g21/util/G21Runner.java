package g21.util;

import java.io.IOException;

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
	 * This method executes the provided command on the respective command line of the OS
	 * @param command command to be executed, e.g. "ping 127.0.0.1"
	 */
	public static void exec(String command) {
		
		String windowsPrefix = "cmd /c ";
		
		if (isWindowsSystem()) {
			//Windows System Command
			try {
				Runtime.getRuntime().exec(windowsPrefix + command);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} else if (isUnixSystem()) {
			//For Unix Systems like Linux
			try {
				Runtime.getRuntime().exec(command);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (isMacSystem()) {
			//For Mac Systems
			try {
				Runtime.getRuntime().exec(command);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Unknown System.");
			System.exit(1); //Maybe you want another behaviour here
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


