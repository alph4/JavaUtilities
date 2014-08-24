package g21.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public final class G21ReaderWriter {
	
	
	/**
	 * Reads out a locally saved file provided with the path and returns a String of it.
	 * After each read line "\r\n" is added to maintain layout.
	 * @param path path of the local document, which should be read
	 * @return read String of the file
	 */
	public static String G21ReadFileAsString(String path) {
		if (path == null) {
			throw new IllegalArgumentException("No path provided!");
	    }
		 
		File f = new File(path);
		if (!f.isFile()) {
			throw new IllegalArgumentException("The provided file does not exist or is no file.");
		}
		
		StringBuilder info = new StringBuilder();
		
		//finally start reading
		try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
		    String line = null; 
		    while ((line = reader.readLine()) != null) {
		    	info.append(line + "\r\n"); //Maybe want to change the line ending here!
		    }
		} catch (IOException e) {
			//You might want to throw another Exception here?
			System.out.println("Error in reading and writing the file.");
			e.printStackTrace();
			System.exit(1);
		}
		
		return info.toString();
	}
	

	/**
	 * Reads out a locally saved file provided with the path and returns an array of strings (for each line) of it.
	 * @param path path of the local document, which should be read
	 * @return read StringArray of the file
	 */
	public static String[] G21ReadFileAsStringArray(String path) {
		if (path == null) {
			throw new IllegalArgumentException("No path provided");
	    }
		 
		File f = new File(path);
		if (!f.isFile()) {
			throw new IllegalArgumentException("The provided file does not exist or is no file.");
		}
		
		List<String> list = new ArrayList<String>();
		
		//eventually start reading
		try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
		    String line = null; 
		    while ((line = reader.readLine()) != null) {
		    		list.add(line); 
		    }
		} catch (IOException e) {
			//You might want to throw another Exception here?
			System.out.println("Error in reading and writing the file.");
			e.printStackTrace();
			System.exit(1);
		}
		
		return list.toArray(new String[0]);
	}
	
	
	/**
	 * Reads out sourcecode of a website provided with the URL and returns it.
	 * @param url url of the site, that should be read
	 * @return sourcecode of the site
	 */
	public static String G21ReadSiteAsString(String url) {
       if (url == null) {
    	   throw new IllegalArgumentException("No URL provided");
       }
        
        URL realURL = null;
        try {
        	realURL = new URL(url);
        } catch (MalformedURLException e) {
        	throw new IllegalArgumentException("Error in creating URL! Provide a proper URL.");
        }
        
        InputStreamReader isr = null;
		try {
			isr = new InputStreamReader(realURL.openStream(), "UTF-8");
		} catch (IOException e) {
			//You might want to throw another Exception here?
			System.out.println("Error in creating InputStream.");
			e.printStackTrace();
			System.exit(1);
		}
		
 
        // read the site
        StringBuilder source = new StringBuilder();

        try (BufferedReader br = new BufferedReader(isr)) {
        	String line ="";
			while((line = br.readLine()) != null) {
				source.append(line + "\r\n");		    
			}
			
		    // close Reader
			br.close();
		    isr.close();	        
		} catch (IOException e) {
			//You might want to throw another Exception here?
			System.out.println("Error in reading and writing the site.");
			e.printStackTrace();
			System.exit(1);
		}
        return source.toString();
	}
	
	/**
	 * A simple writer to write data provided as String in the provided file. Overwriting files may cause problems, handle this beforehand by deleting those.
	 * @param file path of the local document, that should be written
	 * @param data data to be written
	 * @param append boolean if true, then data will be written to the end of the file rather than the beginning.
	 */
	public static void writeFile(String file, String data, boolean append) {
		if (file == null || data == null) {
			throw new IllegalArgumentException("Could not write file because of invalid arguments.");
		}
				
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, append))) {	
			bw.write(data);
			bw.close();
		} catch (IOException e) {
			//You might want to throw another Exception here?
			System.out.println("Error! Couldn't write file. _Ivan_ probably crashed it ;-)");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
}
